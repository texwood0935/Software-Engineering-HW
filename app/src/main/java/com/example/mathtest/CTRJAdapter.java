package com.example.mathtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CTRJAdapter extends ArrayAdapter<TM> {
    private int resourceId;
    public CTRJAdapter(Context context, int resource, ArrayList<TM> objects) {
        super(context, resource,objects);
        resourceId =  resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TM question = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null ){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.question = view.findViewById(R.id.question);
            viewHolder.useranswer = view.findViewById(R.id.user_answer);
            viewHolder.rightanswer = view.findViewById(R.id.right_answer);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        String Qstring = question.getQuestion();
        String Ustring = "你的答案: " + question.getUseranswer();
        String Rstring = "正确答案: " + question.getAnswer();
        viewHolder.question.setText(Qstring);
        viewHolder.useranswer.setText(Ustring);
        viewHolder.rightanswer.setText(Rstring);
        return view;
    }

    class ViewHolder{
        TextView question;
        TextView useranswer;
        TextView rightanswer;
    }

}
