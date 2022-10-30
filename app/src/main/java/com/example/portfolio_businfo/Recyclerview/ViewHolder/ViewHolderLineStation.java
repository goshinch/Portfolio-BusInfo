package com.example.portfolio_businfo.Recyclerview.ViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.Model.Bus_Line_Station_HolderData;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Recyclerview.DataView;

public class ViewHolderLineStation extends DataView {
    ImageView arrive_Image, line_state;
    TextView BUSSTOP_NAME, ARS_ID, line_state_text;

    public ViewHolderLineStation(@NonNull View itemView) {
        super(itemView);
        arrive_Image = itemView.findViewById(R.id.arrive_image);
        ARS_ID = itemView.findViewById(R.id.ARS_ID);
        BUSSTOP_NAME = itemView.findViewById(R.id.BUSSTOP_NAME);
        line_state = itemView.findViewById(R.id.lineState);
        line_state_text = itemView.findViewById(R.id.lineState_Text);
    }

    public void setData(Bus_Line_Station data) {
        BUSSTOP_NAME.setText(data.getBUSSTOP_NAME());
        ARS_ID.setText(data.getARS_ID());
    }

    public void setfiledImge(int RETURN_FLAG) {
        switch (RETURN_FLAG) {
            case 1:
                line_state.setVisibility(View.VISIBLE);
                line_state.setImageResource(R.drawable.ic_baseline_south_24);
                line_state_text.setText("");
                break;
            case 2:
                line_state.setVisibility(View.GONE);
                line_state_text.setText("출발!");
                break;
            case 3:
                    line_state.setVisibility(View.VISIBLE);
                    line_state.setImageResource(R.drawable.ic_baseline_refresh_24);
                    line_state_text.setText("");
                break;
            case 4:
                line_state.setVisibility(View.GONE);
                line_state_text.setText("종료!");
                break;
        }
    }

    public void setArrive_Image(boolean arrive) {
        if (arrive) {
            arrive_Image.setImageResource(R.drawable.arrive_bus_1920);
            line_state.setVisibility(View.GONE);
        } else {
            arrive_Image.setImageResource(R.drawable.bus_line1920);
            line_state.setVisibility(View.VISIBLE);
        }
    }
}
