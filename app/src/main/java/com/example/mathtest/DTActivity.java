package com.example.mathtest;

import android.content.DialogInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class DTActivity extends AppCompatActivity {
    TextView textView;  //算式的显示;
    EditText editText;  //答案输入框;
    Button lastButton;   //问题页面的确定按钮;
    Button nextButton;  //问题页面的下一题的按钮;
    Toolbar toolbar;    //该活动中的标题栏;
    int grade ; //年级;
    int result = 0 ; // 答案;
    int count = 1 ; //当前的做题数
    private int NumberOfQuestions = 5; //题目的总数目;
    private int NumberOfRight = 0; //题目正确的数目;

    public ArrayList<TM> questionArrayList = new ArrayList<>();    //题目和正确答案列表;
    public ArrayList<TM> errorQuestionList = new ArrayList<>();   //错误的题目保存的列表；

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtjm);
        grade = getIntent().getIntExtra("level",0);
        textView = findViewById(R.id.math_questions);
        editText = findViewById(R.id.answer);
        lastButton = findViewById(R.id.yes_button);
        nextButton = findViewById(R.id.nextquestion_button);
        toolbar = findViewById(R.id.toobar_questions);
        setSupportActionBar(toolbar);
        toolbar.setTitle("答题 ("+  count +"/"+NumberOfQuestions+ ")");
        questions(grade,textView,editText,nextButton,lastButton);
    }

    //答题时按返回键退出的提示
    @Override
    public boolean onKeyDown(int keyCode , KeyEvent event){
        //KeyCode 按键监听    KeyEvent按键事件
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //创建返回的对话框
            AlertDialog.Builder isReturn = new AlertDialog.Builder(this);
            //设置标题
            isReturn.setTitle("消息提醒");
            //设置对话框的消息
            isReturn.setMessage("请确认你的退出");
            //设置按钮并进行监听
            isReturn.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DTActivity.this.finish();
                }
            });
            isReturn.setNegativeButton("取消", null);
            isReturn.show();
        }
        return false ;
    }

    //出题函数
    private void questions(final int grade,final TextView textView,final EditText editText,
                           final Button nextButton,final Button yesButton){
        //grade年级  textView题目文本框    editText答案输入框
        //nextButton下一题的按钮      lastButton确定答案的按钮
        ErrorLoad();
        mathQuestionsCreate(grade);
        textView.setText(questionArrayList.get(count-1).getQuestion());
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().equals("")){
                    questionArrayList.get(count-1).setUseranswer(editText.getText().toString());
                    questionArrayList.get(count-1).setIsDone(1);
                    if (count != NumberOfQuestions){
                        count ++ ;
                        if (count == NumberOfQuestions ){
                            nextButton.setText("提交");
                        }
                        textView.setText(questionArrayList.get(count-1).getQuestion());
                        if (questionArrayList.get(count-1).getIsDone() == 0){
                            editText.setText("");
                        }
                        else {
                            editText.setText(questionArrayList.get(count - 1).getUseranswer());
                        }
                        toolbar.setTitle("答题 ("+  count + ")");
                    }
                    else {
                        answerCheck();
                        //创建返回的对话框
                        AlertDialog.Builder isReturn = new AlertDialog.Builder(DTActivity.this);
                        //设置标题
                        isReturn.setTitle("本次答题的最终成绩");
                        //设置对话框的消息
                        isReturn.setMessage("正确答题数目：" + NumberOfRight + "\n" + "错误答题数目:" + (NumberOfQuestions - NumberOfRight) + "\n" + "总数目：" + NumberOfQuestions + "\n" +"获取金币数：" + NumberOfRight*5 );
                        //设置弹出的对话框不会因为点击别的地方而退出。
                        isReturn.setCancelable(false);
                        //设置按钮并进行监听
                        isReturn.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DTActivity.this.finish();
                                }
                        });
                        isReturn.show();
                        //增加金币数，每对5道题5个金币
                        GoldCoins.getInstance().AddCoins(NumberOfRight*5);
                    }
                }
                else {  //未回答时的响应
                    Toast.makeText(DTActivity.this,"请先回答本题哦", Toast.LENGTH_SHORT).show();
                }

            }
        });
        lastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 1){
                    Toast.makeText(DTActivity.this , "已经到第一题了", Toast.LENGTH_SHORT).show();
                }
                else {
                    count--;
                    textView.setText(questionArrayList.get(count-1).getQuestion());
                    editText.setText(questionArrayList.get(count-1).getUseranswer());
                    toolbar.setTitle("答题 ("+  count + ")");
                }

            }
        });
    }


    //0到max范围内生成随机数
    public int rCreate( int max){
        Random random = new Random();
        return  random.nextInt(max);
    }

    //算式列表的产生
    public void  mathQuestionsCreate( int grade){
        int max;
        int i = 0;
        while(i<NumberOfQuestions){
            TM tm = new TM();
            switch(grade){
                case 1 :
                    max = 10;
                    switch(rCreate(2)%2){
                        case 0 :
                            tm.setQuestion(CreateAddQuestions(max));
                            tm.setAnswer(String.valueOf(result));
                            tm.setIsDone(0);
                            questionArrayList.add(i ,tm);
                            break;
                        case 1:
                            tm.setQuestion(CreateSubQuestions(max));
                            tm.setAnswer(String.valueOf(result));
                            tm.setIsDone(0);
                            questionArrayList.add(i ,tm);
                            break;
                    }
                    break;
                case 2 :
                    switch(rCreate(2)%2){
                        case 0 :
                            tm.setQuestion(CreateBaseMultyQuestions());
                            tm.setAnswer(String.valueOf(result));
                            tm.setIsDone(0);
                            questionArrayList.add(i ,tm);
                            break;
                        case 1:
                            tm.setQuestion(CreateBaseDiviQuestions());
                            tm.setAnswer(String.valueOf(result));
                            tm.setIsDone(0);
                            questionArrayList.add(i ,tm);
                            break;
                    }
                    break;

                case 3:
                    tm.setQuestion(CreateArithQuestions());
                    tm.setAnswer(String.valueOf(result));
                    tm.setIsDone(0);
                    questionArrayList.add(i ,tm);
                    break;
                default:
                    break;
            }
            i++;
        }
    }

    //生成一个加法的算式，并输出到屏幕上,并返回结果
    private String CreateAddQuestions (int max){
        int number = 0 ;
        result = 0;
        number = rCreate(max);
        result += number ;
        String math_questions = String.valueOf(number);
        math_questions += " + ";
        number = rCreate(max);
        math_questions += String.valueOf(number);
        math_questions += " = ";
        result += number;
        return math_questions;
    }
    //生成一个减法的算式，并输出到屏幕上,并返回结果
    private String CreateSubQuestions (int max){
        int numberFirst,numberSecond;
        String math_questions;
        numberFirst = rCreate(max);
        numberSecond = rCreate(max);
        if (numberFirst >= numberSecond){
            math_questions = String.valueOf(numberFirst) + " - " +  String.valueOf(numberSecond) + " = ";
            result = numberFirst - numberSecond ;
        }
        else {
            math_questions = String.valueOf(numberSecond) + " - " +  String.valueOf(numberFirst) + " = ";
            result = numberSecond - numberFirst ;
        }
        return math_questions;
    }

    //生成一个九九乘法表中的算式，并输出到屏幕上，返回结果
    private String CreateBaseMultyQuestions (){
        int numberFirst ,numberSecond ;
        String math_questions;
        numberFirst = rCreate(9);
        numberSecond = rCreate(9);
        math_questions = String.valueOf(numberFirst) + "×" + String.valueOf(numberSecond) + " = " ;
        result = numberFirst * numberSecond ;
       return math_questions;
    }
    //生成一个结果为整数的除法算式，并输出到屏幕上，返回结果
    private String CreateBaseDiviQuestions(){
        int numberFirst ,numberSecond ;
        String math_questions;
        result=rCreate(15)+1;
        numberSecond = rCreate(15)+1;
        numberFirst = result*numberSecond;
        math_questions = String.valueOf(numberFirst) + "÷" + String.valueOf(numberSecond) + " = " ;
        return math_questions;
    }
    //生成一个四则运算公式,并输出到屏幕上，返回结果
    private  String CreateArithQuestions()
    {
        BinaryTree bTree;
        String math_questions;
        Random random=new Random();
        int vim=random.nextInt(3)+1;
        bTree = new BinaryTree(vim);
        bTree.createBTree();
        String res = bTree.CalAndVal();
        result=Integer.parseInt(res);
        while(result<0)//过滤负数，小学3年级没学
        {
            bTree = new BinaryTree(vim);
            bTree.createBTree();
            res = bTree.CalAndVal();
            result=Integer.parseInt(res);
        }
        math_questions=bTree.toString();
        math_questions+="=";
        return math_questions;

    }
    //答案校对，result为结果，EditText为输入答案的框,yesButton为确定答案的按钮
    private void answerCheck(){
        count = 0;
        while(count < NumberOfQuestions) {
            if (questionArrayList.get(count).getUseranswer().equals(questionArrayList.get(count).getAnswer())){
                NumberOfRight ++ ;
            }
            else {
                errorQuestionList.add(questionArrayList.get(count));
            }
            count ++ ;
        }
        ErrorSave();//保存错题到文件中去
    }
    //错题保存至文件
    private void ErrorSave(){
        BufferedWriter writer = null;
        File file = new File(this.getFilesDir().getPath() + "/Error.json");
        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        String str = JSONArray.toJSONString(errorQuestionList);
        System.out.println(str);
        try{
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), Charset.forName("UTF-8")));
            writer.write(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer!=null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件写入成功！");
        Log.d("TAG", "file=" + file.getAbsolutePath());
    }

    //读取错题列表
    private void ErrorLoad (){
        String path = this.getFilesDir().getPath() + "/Error.json";
        File file = new File(path);
        BufferedReader reader = null ;
        String errorload = "";
        //判断有无文件存在
        if (!file.exists()){
           return ;
        }
        //读取文件存放在String errorload中
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine())!=null){
                errorload += tempString;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //将String errorload 转为列表
        System.out.println(errorload);
        errorQuestionList  = JSON.parseObject(errorload, new TypeReference<ArrayList<TM>>(){});
    }
}