package com.example.mathtest;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Random;

public class QuestionsActivity extends AppCompatActivity {


    TextView textView;  //算式的显示;
    EditText editText;  //答案输入框;
    Button lastButton;   //问题页面的确定按钮;
    Button nextButton;  //问题页面的下一题的按钮;
    Button loveButton;  //收藏按钮
    Toolbar toolbar;    //该活动中的标题栏;
    int level; //年级;
    int result = 0; // 答案;
    int count = 1; //题目当前剩下的数目;
    private int NumberOfQuestions = 3; //题目的数目;
    private int NumberofRight = 0; //题目正确的数目;

    public ArrayList<Questions> questionArrayList = new ArrayList<>();    //题目和正确答案列表;

    //0到max范围内生成随机数
    public int rCreate(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    //算式列表的产生
    public void mathQuestionsCreate(int level) {
        int max;

        int i = 0;
        while (i < NumberOfQuestions) {
            Questions question = new Questions();
            switch (level) {
                case 1:
                    max = 10;
                    switch (rCreate(max) % 2) {
                        case 0:
                            question.setQuestion(CreateAddQuestions(max));
                            question.setAnswer(String.valueOf(result));
                            question.setIsDone(0);
                            question.setIsLoved(0);
                            questionArrayList.add(i, question);
                            break;
                        case 1:
                            question.setQuestion(CreateSubQuestions(max));
                            question.setAnswer(String.valueOf(result));
                            question.setIsDone(0);
                            question.setIsLoved(0);
                            questionArrayList.add(i, question);
                            break;
                    }
                    break;
                default:
                    break;
            }
            i++;
        }
    }

    //生成一个加法的算式，并输出到屏幕上,并返回结果
    private String CreateAddQuestions(int max) {
        int number = 0;
        result = 0;
        number = rCreate(max);
        result += number;
        String math_questions = String.valueOf(number);
        math_questions += " + ";
        number = rCreate(max);
        math_questions += String.valueOf(number);
        math_questions += " = ";
        result += number;
        return math_questions;
    }


    //生成一个减法的算式，并输出到屏幕上,并返回结果
    private String CreateSubQuestions(int max) {
        int numberFirst, numberSecond;
        String math_questions;
        numberFirst = rCreate(max);
        numberSecond = rCreate(max);
        if (numberFirst >= numberSecond) {
            math_questions = String.valueOf(numberFirst) + " - " + String.valueOf(numberSecond) + " = ";
            result = numberFirst - numberSecond;
        } else {
            math_questions = String.valueOf(numberSecond) + " - " + String.valueOf(numberFirst) + " = ";
            result = numberSecond - numberFirst;
        }
        return math_questions;
    }
}

