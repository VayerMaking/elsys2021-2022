package com.example.springwebtestche;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class NoteController {
    ArrayList<Note> notes = new ArrayList<>();

    @PostMapping("/notes")
    @ResponseBody
    public Object postNote(@RequestBody String json){
        ObjectMapper objectMapper = new ObjectMapper();
        Note note = null;
        try {
            note = objectMapper.readValue(json, Note.class);
            note.id = createID();
            notes.add(note);
        } catch (JsonProcessingException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(note.id, HttpStatus.CREATED);
    }

    @GetMapping("/notes/{id}")
    @ResponseBody
    public Object getNote(@PathVariable String id) {
        if (notes == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            for (Note note : notes) {
                if (note.id.equals(id)) {
                    HashMap<String, String> responseNote = new HashMap<>();
                    responseNote.put("id", note.id);
                    responseNote.put("text", note.text);
                    //System.out.println(note);
                    return new ResponseEntity<>(responseNote, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PutMapping("/notes/{id}")
//    @ResponseBody
//    public Object putNote(@PathVariable String id, @RequestBody String json){
//        if (notes == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } else {
//            for (Note note : notes) {
//                if (note.id.equals(id)) {
//                    note.text = json.text;
//                    HashMap<String, String> responseNote = new HashMap<>();
//                    responseNote.put("id", note.id);
//                    responseNote.put("text", note.text);
//                    System.out.println(note);
//                    return new ResponseEntity<>(responseNote, HttpStatus.OK);
//                } else {
//                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//                }
//            }
//
//
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//    }
    @DeleteMapping("/notes/{id}")
    public Object delNote(@PathVariable String id){
        if (notes == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            for (Note note : notes) {
                if (note.id.equals(id)) {
                    notes.remove(note);
                    //System.out.println(note);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    String createID(){
        return UUID.randomUUID().toString();
    }


}
