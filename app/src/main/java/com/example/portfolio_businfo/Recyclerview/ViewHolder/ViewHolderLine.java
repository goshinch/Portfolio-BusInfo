package com.example.portfolio_businfo.Recyclerview.ViewHolder;


import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Recyclerview.DataView;

public class ViewHolderLine extends DataView {
    TextView LINE_NAME;
    TextView DIR_UP_NAME;
    TextView DIR_DOWN_NAME;
    TextView FIRST_RUN_TIME;
    TextView LAST_RUN_TIME;
    TextView LINE_KIND;
    ImageView bus_color,direction;
    CheckBox checkBox;

    public ViewHolderLine(@NonNull View itemView) {
        super(itemView);
        LINE_NAME = itemView.findViewById(R.id.LINE_NAME);
        DIR_UP_NAME = itemView.findViewById(R.id.DIR_UP_NAME);
        DIR_DOWN_NAME = itemView.findViewById(R.id.DIR_DOWN_NAME);
        FIRST_RUN_TIME = itemView.findViewById(R.id.FIRST_RUN_TIME);
        LAST_RUN_TIME = itemView.findViewById(R.id.LAST_RUN_TIME);
        LINE_KIND = itemView.findViewById(R.id.LIKE_KIND);
        checkBox = itemView.findViewById(R.id.checkBox);
        bus_color = itemView.findViewById(R.id.bus_color);
        direction = itemView.findViewById(R.id.direction);

        one = ContextCompat.getColor(itemView.getContext(), R.color.one);
        two = ContextCompat.getColor(itemView.getContext(), R.color.two);
        three = ContextCompat.getColor(itemView.getContext(), R.color.three);
        four = ContextCompat.getColor(itemView.getContext(), R.color.four);
        five = ContextCompat.getColor(itemView.getContext(), R.color.five);
    }

    public CheckBox CheckBox(){
        return this.checkBox;
    }
    public void setCheckBox(boolean checked){
        this.checkBox.setSelected(checked);
    }
    public void setCheck(boolean check) {
        this.checkBox.setChecked(check);
    }
    public void setCheckBoxIsNull(){
        this.checkBox.setOnCheckedChangeListener(null);
    }


    public void setData(Bus_Line data){
        LINE_NAME.setText(data.getLINE_NAME());
        DIR_UP_NAME.setText(data.getDIR_UP_NAME());
        DIR_DOWN_NAME.setText(data.getDIR_DOWN_NAME());
        FIRST_RUN_TIME.setText(data.getFIRST_RUN_TIME());
        LAST_RUN_TIME.setText(data.getLAST_RUN_TIME());
        checkBox.setChecked(data.isCHECK());

        switch (data.getLIKE_KIND()){
            case "1":
                LINE_KIND.setText("급행간선");
                direction.setColorFilter(one);
                LINE_KIND.setTextColor(one);
                bus_color.setColorFilter(one);
                break;
            case "2":
                LINE_KIND.setText("간선");
                LINE_KIND.setTextColor(two);
                bus_color.setColorFilter(two);
                direction.setColorFilter(two);
                break;
            case "3":
                LINE_KIND.setText("지선");
                LINE_KIND.setTextColor(three);
                bus_color.setColorFilter(three);
                direction.setColorFilter(three);
                break;
            case "4":
                LINE_KIND.setText("마을버스");
                LINE_KIND.setTextColor(four);
                bus_color.setColorFilter(four);
                direction.setColorFilter(four);
                break;
            default:
                LINE_KIND.setText("공항버스");
                LINE_KIND.setTextColor(five);
                bus_color.setColorFilter(five);
                direction.setColorFilter(five);
                break;
        }
    }
}
