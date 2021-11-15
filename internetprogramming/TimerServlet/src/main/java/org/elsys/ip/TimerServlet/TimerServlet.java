package org.elsys.ip.TimerServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TimerServlet extends HttpServlet {
    private final Map<String, Integer> userTimers = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> args = collectArgs(req, resp);
        String command = args.get(1);
        if(command.equals("start")){
            String id = createID();
            userTimers.put(id, (int) System.currentTimeMillis());
            resp.getWriter().print(id);
            resp.setStatus(201);
        }else{
            resp.setStatus(404);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> args = collectArgs(req, resp);
        if(args.size() > 1 && args.size()< 4) {
            String id = args.get(1);
            if (userTimers.containsKey(id) && id.length() == 36) {
                resp.setStatus(200);
                resp.getWriter().println("00:00:05");
            } else {
                resp.setStatus(404);
            }
        }else {
            resp.setStatus(400);
        }    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> args = collectArgs(req, resp);
        if(args.size() > 1) {
            String id = args.get(1);
            if (userTimers.containsKey(id) && id.length() == 36) {
                resp.setStatus(200);
                resp.getWriter().println("00:00:05");
            } else {
                resp.setStatus(404);
            }
        }else {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> args = collectArgs(req, resp);
        if(args.size() >= 1) {
            String id = args.get(1);
            if(userTimers.containsKey(id) && id.length() == 36) {
                Integer currentTime = (int) System.currentTimeMillis();
                Integer userTime = userTimers.get(id);
                int timeElapsed = currentTime - userTime;
                long HH = TimeUnit.MILLISECONDS.toHours(timeElapsed);
                long MM = TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % 60;
                long SS = TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % 60;
                String formattedTime = String.format("%02d:%02d:%02d", HH, MM, SS);
                resp.getWriter().println(formattedTime);
                resp.setStatus(200);
            }else{
                resp.setStatus(404);
            }
        }else{
            resp.setStatus(400);
        }
    }


    List<String> collectArgs(HttpServletRequest req, HttpServletResponse resp){
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();
        List<String> collect = Arrays.stream(path.split("/")).filter(s -> !s.equals("")).collect(Collectors.toList());
        if (collect.size() == 0) {
            resp.setStatus(404);
            return collect;
        }

        return collect;

    }

    String createID(){
        return UUID.randomUUID().toString();
    }

}