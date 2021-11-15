package org.elsys.ip.quiz;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class QuizApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting Quiz");
        ArrayList<Question> questions = new ArrayList<>();
        Question q1 = new Question("asdf?", "a - 1, b - 2, c - 3, d - 5", "c");
        Question q2 = new Question("qwerty?", "a - 1, b - 2, c - 3, d - 5", "c");
        Question q3 = new Question("2 + 2?", "a - 4, b - 2, c - 3, d - 5", "a");
        Question q4 = new Question("3 - 3?", "a - 1, b - 0, c - 3, d - 5", "b");
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);

        Quiz quiz = new Quiz(questions);
    }
}
