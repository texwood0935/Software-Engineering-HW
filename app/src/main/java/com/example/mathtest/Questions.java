package com.example.mathtest;

public class Questions {
    private String question;
    private String answer;
    private String useranswer;
    private int isDone;
    private int isLoved;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }

    public void setIsLoved(int isLoved) {
        this.isLoved = isLoved;
    }
}