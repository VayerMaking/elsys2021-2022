package com.example.springwebtestche;

public class Note {
    String id;
    String text;

    public Note(String text) {
        this.text = text;
    }
    public Note() {
        super();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

