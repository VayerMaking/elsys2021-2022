package org.elsys.ip.quiz;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
    int points;
    ArrayList<Question> questions;

    public Quiz(ArrayList<Question> questions) {
        this.questions = questions;

        for (Question q: questions)
            {
            System.out.println("Question: " + q.question);
            System.out.println("Answers: " + q.answers);
            Scanner in = new Scanner(System.in);
            String userAnswer = in.nextLine();
            if (userAnswer.equals(q.correctAnswer)) {
                System.out.println("correct");
                points++;
                System.out.println("total points: " + points);
            } else {
                System.out.println("wrong");
                System.out.println("total points: " + points);
            }
        }
    }
}
