package org.elsys.ip.quiz;

public class Question {
    String question;
    String answers;
    String correctAnswer;

    public Question(String question, String answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}
