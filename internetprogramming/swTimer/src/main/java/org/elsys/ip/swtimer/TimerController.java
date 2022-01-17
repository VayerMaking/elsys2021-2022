package org.elsys.ip.swtimer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class TimerController {
    ArrayList<Timer> timers = new ArrayList<>();
    @GetMapping("/timer/{id}")
    public Object getTimer(@PathVariable String id){
        if(timers == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            for (Timer timer : timers) {
                if (timer.id.equals(id)) {
                    long userHours = timer.hours;
                    long userMinutes = timer.minutes;
                    long userSeconds = timer.seconds;
                    long userTime = userHours * 3600000 + userMinutes * 60000 + userSeconds * 1000;
                    if(userTime > 0){
                        timer.setDone("no");
                    }
                    userTime = userTime - 1;
                    userHours = TimeUnit.MILLISECONDS.toHours(userTime);
                    userMinutes = TimeUnit.MILLISECONDS.toMinutes(userTime) % 60;
                    userSeconds = TimeUnit.MILLISECONDS.toSeconds(userTime) % 60;
                    String formattedTime = String.format("%02d:%02d:%02d", userHours, userMinutes, userSeconds);
                    timer.hours = Math.toIntExact(userHours);
                    timer.minutes = Math.toIntExact(userMinutes);
                    timer.seconds = Math.toIntExact(userSeconds);
                    timer.time = formattedTime;
                    HashMap<String, String> responseTimer = new HashMap<>();
                    responseTimer.put("id", timer.id);
                    responseTimer.put("name", timer.name);
                    responseTimer.put("time", timer.time);
                    responseTimer.put("done", timer.done);
                    return new ResponseEntity<>(responseTimer, HttpStatus.OK);
                    //return responseTimer;
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/timer")
    @ResponseBody
    public Object createTimer(@RequestBody String json){
        ObjectMapper objectMapper = new ObjectMapper();
        Timer timer = null;
        try {
            try {
                timer = objectMapper.readValue(json, Timer.class);
            }catch (InvalidFormatException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(timer.time != null){
                timer.setId(createID());
                String[] userTime = timer.time.split(":");
                try {
                    long userHours = Long.parseLong(userTime[0]);
                    long userMinutes = Long.parseLong(userTime[1]);
                    long userSeconds = Long.parseLong(userTime[2]);
                    timer.hours = Math.toIntExact(userHours);
                    timer.minutes = Math.toIntExact(userMinutes);
                    timer.seconds = Math.toIntExact(userSeconds);
                    String formattedTime = String.format("%02d:%02d:%02d", userHours, userMinutes, userSeconds);
                    timer.time = formattedTime;
                    timers.add(timer);
                } catch (NumberFormatException e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

            }else {
                timer.setId(createID());
                long userHours = timer.hours;
                long userMinutes = timer.minutes;
                //System.out.println(userMinutes);
                long userSeconds = timer.seconds;
                String formattedTime = String.format("%02d:%02d:%02d", userHours, userMinutes, userSeconds);
                timer.time = formattedTime;
                timers.add(timer);
            }
            HashMap<String, String> responseTimer = new HashMap<>();
            responseTimer.put("id", timer.id);
            responseTimer.put("name", timer.name);
            responseTimer.put("time", timer.time);
            return new ResponseEntity<>(responseTimer, HttpStatus.CREATED);
            //return responseTimer;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
//        Timer createdTimer = timer;
//        return new ResponseEntity < Timer > (createdTimer, HttpStatus.OK);
    }
    String createID(){
        return UUID.randomUUID().toString();
    }
}
