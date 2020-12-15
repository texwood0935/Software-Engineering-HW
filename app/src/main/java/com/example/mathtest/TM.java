package com.example.mathtest;

public class TM {
    private String question;    //问题
    private String answer;      //标答
    private String useranswer;  //用户回答
    private int isDone;         //完成标志

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }
    public String getAnswer() {
        return answer;
    }
    public String getUseranswer(){
        return useranswer;
    }
    public String getQuestion(){
        return question;
    }
    public void setUseranswer(String useranswer){
        this.useranswer = useranswer;
    }
    public int getIsDone() {
        return isDone;
    }


}