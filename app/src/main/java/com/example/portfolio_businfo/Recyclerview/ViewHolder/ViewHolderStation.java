package com.example.portfolio_businfo.Recyclerview.ViewHolder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Recyclerview.DataView;

public class ViewHolderStation extends DataView {
    TextView BUSSTOP_ID;
    TextView BUSSTOP_NAME;
    TextView NAME_E;
    TextView LONGITUDE;
    TextView LATITUDE;
    TextView ARS_ID;
    TextView NEXT_BUSSTOP;
    CheckBox checkBox;

    public ViewHolderStation(@NonNull View itemView) {
        super(itemView);
        BUSSTOP_NAME = itemView.findViewById(R.id.BUSSTOP_NAME);
        ARS_ID = itemView.findViewById(R.id.ARS_ID);
        NEXT_BUSSTOP = itemView.findViewById(R.id.NEXT_BUSSTOP);
        checkBox = itemView.findViewById(R.id.checkBox2);
    }

    public void setCheck(boolean check) {
        this.checkBox.setChecked(check);
    }

    public void setData(Bus_Station data){
        BUSSTOP_NAME.setText(data.getBUSSTOP_NAME());
        ARS_ID.setText(data.getARS_ID());
        NEXT_BUSSTOP.setText(data.getNEXT_BUSSTOP());
        checkBox.setChecked(data.isCHECK());
    }
}
