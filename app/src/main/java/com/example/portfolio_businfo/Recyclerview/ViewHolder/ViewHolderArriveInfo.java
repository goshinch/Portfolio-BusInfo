package com.example.portfolio_businfo.Recyclerview.ViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.portfolio_businfo.Model.Bus_Arrive_Info;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Recyclerview.DataView;

public class ViewHolderArriveInfo extends DataView {
    TextView LINE_ID,
            LINE_NAME,
            BUS_ID,
            METRO_FLAG,
            CURR_STOP_ID,
            BUSSTOP_NAME,
            REMAIN_MIN,
            REMAIN_STOP,
            DIR_START,
            DIR_END,
            LOW_BUS,
            ENG_BUSSTOP_NAME,
            ARRIVE_FLAG,
            LINE_KIND;
    ImageView bus_color;

    public ViewHolderArriveInfo(@NonNull View itemView) {
        super(itemView);
        LINE_NAME = itemView.findViewById(R.id.arLINE_NAME);
        LINE_KIND = itemView.findViewById(R.id.arLINE_KIND);
        REMAIN_MIN = itemView.findViewById(R.id.arREMAIN_MIN);
        LOW_BUS = itemView.findViewById(R.id.arLOW_BUS);

        bus_color = itemView.findViewById(R.id.arBus_color);

        one = ContextCompat.getColor(itemView.getContext(), R.color.one);
        two = ContextCompat.getColor(itemView.getContext(), R.color.two);
        three = ContextCompat.getColor(itemView.getContext(), R.color.three);
        four = ContextCompat.getColor(itemView.getContext(), R.color.four);
        five = ContextCompat.getColor(itemView.getContext(), R.color.five);
    }

    public void setData(Bus_Arrive_Info data) {
        Log.d("setData: ",data.getLINE_KIND());
        LINE_NAME.setText(data.getLINE_NAME());
        REMAIN_MIN.setText(RemainMinSet(data.getREMAIN_MIN()));
        LOW_BUS.setText(LowBusSet(data.getLOW_BUS()));

        LineKind(data.getLINE_KIND());
    }

    private String RemainMinSet(String remain) {
        return "도착예정: "+remain+"분";
    }

    private String LowBusSet(String lowbus) {
        String set;
        switch (lowbus) {
            case "1":
                set = "저상 버스";
                break;
            case "0":
                set = "일반 버스";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + lowbus);
        }
        return set;
    }

    public void changeData(String REMAIN_MIN) {
        this.REMAIN_MIN.setText(REMAIN_MIN);
    }

    private void LineKind(String getLINE_KIND){
        switch (getLINE_KIND) {
            case "1":
                LINE_KIND.setText("급행간선");
                LINE_KIND.setTextColor(one);
                bus_color.setColorFilter(one);
                break;
            case "2":
                LINE_KIND.setText("간선");
                LINE_KIND.setTextColor(two);
                bus_color.setColorFilter(two);
                break;
            case "3":
                LINE_KIND.setText("지선");
                LINE_KIND.setTextColor(three);
                bus_color.setColorFilter(three);
                break;
            case "4":
                LINE_KIND.setText("마을버스");
                LINE_KIND.setTextColor(four);
                bus_color.setColorFilter(four);
                break;
            default:
                LINE_KIND.setText("공항버스");
                LINE_KIND.setTextColor(five);
                bus_color.setColorFilter(five);
                break;
        }
    }
}

