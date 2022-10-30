package com.example.portfolio_businfo.Recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfolio_businfo.Model.Bus_Arrive_Info;
import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.Model.Bus_Line_checkbox;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.Model.Bus_Station_checkbox;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase;
import com.example.portfolio_businfo.Recyclerview.ViewHolder.ViewHolderArriveInfo;
import com.example.portfolio_businfo.Recyclerview.ViewHolder.ViewHolderLine;
import com.example.portfolio_businfo.Recyclerview.ViewHolder.ViewHolderLineStation;
import com.example.portfolio_businfo.Recyclerview.ViewHolder.ViewHolderStation;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<DataView> {
    public OnClickListener mOnClickListener = null;
    public OnCheckListener mOnCheckListener = null;
    public OnMapClickListener mOnMapClickListener = null;
    private SearchCase type;
    private Context context;
    int count;

    public void setContext(Context context) {
        this.context = context;
    }

    // 버스 노선 리스트
    private ArrayList<Bus_Line> busLineList = new ArrayList<>();

    public void ArrayListDataSet_Line(ArrayList<Bus_Line> busLineList) {
        Log.d("ArrayListDataSet_Line: ", String.valueOf(busLineList.size()));
        this.busLineList.addAll(busLineList);
        notifyDataSetChanged();
    }

    // 아이템 클릭
    public ArrayList<Bus_Line> ArrayListDataGet_Line() {
        return busLineList;
    }

    // 정류장 리스트
    private ArrayList<Bus_Station> busStationList = new ArrayList<>();

    public void ArrayListDataSet_Station(ArrayList<Bus_Station> busStationList) {
        Log.d("ArrayListDataSet_Station: ", String.valueOf(busStationList.size()));
        this.busStationList.addAll(busStationList);
        notifyDataSetChanged();
    }

    public ArrayList<Bus_Station> ArrayListDataGet_Station() {
        return busStationList;
    }

    // 노선_정류장 리스트
    private ArrayList<Bus_Line_Station> busLSList = new ArrayList<>();

    public boolean ArrayListDataSet_LS(ArrayList<Bus_Line_Station> busLSList) {
        Log.d("ArrayListDataSet_LS: ", String.valueOf(busLSList.size()));
        ls:for (Bus_Line_Station i : busLSList)
            if (i.getRETURN_FLAG() == "3") {
                busLSList.remove(i);
                break ls;
            }
        this.busLSList.addAll(busLSList);
        notifyDataSetChanged();

        return true;
    }

    public ArrayList<Bus_Line_Station> ArrayListDataGet_LS() {
        return busLSList;
    }

    // 도착 정보
    private ArrayList<Bus_Arrive_Info> busArriveList = new ArrayList<>();

    public void ArrayListDataSet_Arrive(ArrayList<Bus_Arrive_Info> busArriveList) {
        Log.d("ArrayListDataSet_Arrive: ", String.valueOf(busArriveList.size()));
        this.busArriveList = busArriveList;
        notifyDataSetChanged();
    }

    public ArrayList<Bus_Arrive_Info> ArrayListDataSet_Arrive() {
        return busArriveList;
    }

    // 즐겨찾기
    private ArrayList<Bus_Line> busFavoritList_L = new ArrayList<>();
    private ArrayList<Bus_Station> busFavoritList_S = new ArrayList<>();

    public void ArrayListDataSet_Favorit_L(ArrayList<Bus_Line> busLineList) {
        Log.d("ArrayListDataSet_Favorit_L: ", String.valueOf(busLineList.size()));
        this.busFavoritList_L.addAll(busLineList);
        notifyDataSetChanged();
    }

    public void ArrayListDataSet_Favorit_S(ArrayList<Bus_Station> busStationList) {
        this.busFavoritList_S.addAll(busStationList);
        notifyDataSetChanged();
    }

    public ArrayList<Bus_Line> ArrayListDataGet_Favorit_L() {
        return busFavoritList_L;
    }

    public ArrayList<Bus_Station> ArrayListDataGet_Favorit_S() {
        return busFavoritList_S;
    }

    // 아이템 클릭 리스너 생성자
    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void setOnCheckListener(OnCheckListener mOnCheckListener) {
        this.mOnCheckListener = mOnCheckListener;
    }

    public void setOnMapClickListener(OnMapClickListener mOnMapClickListener) {
        this.mOnMapClickListener = mOnMapClickListener;
    }

    public RecyclerViewAdapter(SearchCase type) {
        this.type = type;
    }

    @NonNull
    @Override
    public DataView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (type) {
            case BUS_LINE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item_line, parent, false);
                ViewHolderLine vLine = new ViewHolderLine(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vLine.getAdapterPosition() + 1;
                        if (po != RecyclerView.NO_POSITION)
                            if (mOnClickListener != null) mOnClickListener.onClick(v, po - 1);
                    }
                });
                itemView.findViewById(R.id.checkBox).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vLine.getAdapterPosition();
                        if (mOnCheckListener != null) mOnCheckListener.onCheck(po, busLineList.get(po).getLINE_ID(), !busLineList.get(po).isCHECK());
                    }
                });
                return vLine;
            case BUS_STATION:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_costom_item_station, parent, false);
                ViewHolderStation vStation = new ViewHolderStation(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vStation.getAdapterPosition() + 1;
                        if (po != RecyclerView.NO_POSITION)
                            if (mOnClickListener != null) mOnClickListener.onClick(v, po - 1);
                    }
                });
                itemView.findViewById(R.id.checkBox2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vStation.getAdapterPosition();
                        if (mOnCheckListener != null) mOnCheckListener.onCheck(po, busStationList.get(po).getBUSSTOP_ID(), !busStationList.get(po).isCHECK());
                    }
                });
                itemView.findViewById(R.id.prsnMap).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vStation.getAdapterPosition();
                        if (mOnMapClickListener != null) mOnMapClickListener.MapClick(busStationList.get(po).getBUSSTOP_ID());
                    }
                });
                return vStation;
            case BUS_LINE_STATION_INFORMATION:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_costom_item_line_station, parent, false);
                ViewHolderLineStation vLS = new ViewHolderLineStation(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vLS.getAdapterPosition() + 1;
                        Log.d("onClick: ", String.valueOf(po-1));
                        if (po != RecyclerView.NO_POSITION)
                            if (mOnClickListener != null) mOnClickListener.onClick(v, po - 1);
                    }
                });
                return vLS;
            case BUS_ARRIVE_INFORMATION:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_costiom_item_arrive, parent, false);
                ViewHolderArriveInfo vAI = new ViewHolderArriveInfo(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vAI.getAdapterPosition() + 1;
                        if (po != RecyclerView.NO_POSITION)
                            if (mOnClickListener != null) mOnClickListener.onClick(v, po - 1);
                    }
                });
                return vAI;
            case BUS_FAVORITES_LINE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item_line, parent, false);
                ViewHolderLine vFL = new ViewHolderLine(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vFL.getAdapterPosition() + 1;
                        if (po != RecyclerView.NO_POSITION)
                            if (mOnClickListener != null) mOnClickListener.onClick(v, po - 1);
                    }
                });
                itemView.findViewById(R.id.checkBox).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vFL.getAdapterPosition();
                        if (mOnCheckListener != null) mOnCheckListener.onCheck(po, busFavoritList_L.get(po).getLINE_ID(), !busFavoritList_L.get(po).isCHECK());
                    }
                });
                Log.d("ArrayListDataSet_Favorit_LCREATE: ", String.valueOf(this.busFavoritList_L));
                return vFL;
            case BUS_FAVORITES_STATION:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_costom_item_station, parent, false);
                ViewHolderStation vFS = new ViewHolderStation(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vFS.getAdapterPosition() + 1;
                        if (po != RecyclerView.NO_POSITION)
                            if (mOnClickListener != null) mOnClickListener.onClick(v, po - 1);
                    }
                });
                itemView.findViewById(R.id.checkBox2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vFS.getAdapterPosition();
                        if (po != RecyclerView.NO_POSITION)
                            if (mOnCheckListener != null) mOnCheckListener.onCheck(po, busFavoritList_S.get(po).getBUSSTOP_ID(), !busFavoritList_S.get(po).isCHECK());
                    }
                });
                itemView.findViewById(R.id.prsnMap).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = vFS.getAdapterPosition();
                        if (mOnMapClickListener != null) mOnMapClickListener.MapClick(busFavoritList_S.get(po).getBUSSTOP_ID());
                    }
                });
                return vFS;
        }
        return null;
    }

    @Override
    @Nullable
    public void onBindViewHolder(@NonNull DataView holder, int position) {
        try {
            switch (type) {
                case BUS_LINE:
                    ViewHolderLine mViewHolderLine = (ViewHolderLine) holder;
                    mViewHolderLine.setData(busLineList.get(position));
                    break;
                case BUS_STATION:
                    ViewHolderStation mViewHolderStation = (ViewHolderStation) holder;
                    mViewHolderStation.setData(busStationList.get(position));
                    break;
                case BUS_LINE_STATION_INFORMATION:
                    ViewHolderLineStation mViewHolderLineStation = (ViewHolderLineStation) holder;
                    mViewHolderLineStation.setData(busLSList.get(position));
                    mViewHolderLineStation.setfiledImge(Integer.parseInt(busLSList.get(position).getRETURN_FLAG()));
                    break;
                case BUS_ARRIVE_INFORMATION:
                    ViewHolderArriveInfo mViewHolderArriveInfo = (ViewHolderArriveInfo) holder;
                    mViewHolderArriveInfo.setData(busArriveList.get(position));
                    break;
                case BUS_FAVORITES_LINE:
                    ViewHolderLine mViewHolderFavorites_L = (ViewHolderLine) holder;
                    mViewHolderFavorites_L.setData(busFavoritList_L.get(position));
                    break;
                case BUS_FAVORITES_STATION:
                    ViewHolderStation mViewHolderFavorites_S = (ViewHolderStation) holder;
                    mViewHolderFavorites_S.setData(busFavoritList_S.get(position));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DataView holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads);
        else {
            switch (type) {
                case BUS_LINE:
                    ViewHolderLine mViewHolderLine = (ViewHolderLine) holder;
                    for (Object payload : payloads) {
                        for (Bus_Line_checkbox data : (ArrayList<Bus_Line_checkbox>) payload) {
                            if (busLineList.get(position).getLINE_ID() == data.getLINE_ID()) {
                                busLineList.get(position).setCHECK(data.isCHECK());
                                mViewHolderLine.setCheck(busLineList.get(position).isCHECK());
                            }
                        }
                    }
                    break;
                case BUS_STATION:
                    ViewHolderStation mViewHolderStation = (ViewHolderStation) holder;
                    for (Object payload : payloads) {
                        for (Bus_Station_checkbox data : (ArrayList<Bus_Station_checkbox>) payload) {
                            if (busStationList.get(position).getBUSSTOP_ID() == data.getBUSSTOP_ID()) {
                                busStationList.get(position).setCHECK(data.isCHECK());
                                mViewHolderStation.setCheck(busStationList.get(position).isCHECK());
                            }
                        }
                    }
                    break;
                case BUS_LINE_STATION_INFORMATION:
                    ViewHolderLineStation mViewHolderLineStation = (ViewHolderLineStation) holder;
                    for (int i=0; i<busLSList.size(); i++) {
                        busLSList.get(i).setExist(false);
                        mViewHolderLineStation.setArrive_Image(busLSList.get(position).isExist());
                        mViewHolderLineStation.setfiledImge(Integer.parseInt(busLSList.get(position).getRETURN_FLAG()));
                    }
                    for (Object payload : payloads) {
                        for (String data : (ArrayList<String>) payload) {
                            if (busLSList.get(position).getBUSSTOP_ID().equals(data)) {
                                busLSList.get(position).setExist(true);
                                mViewHolderLineStation.setArrive_Image(busLSList.get(position).isExist());
                            }
                        }
                    }
                    break;
                case BUS_ARRIVE_INFORMATION:
                    ViewHolderArriveInfo mViewHolderArriveInfo = (ViewHolderArriveInfo) holder;
                    busArriveList = new ArrayList<>();
                    for (Object payload : payloads) {
                        for (Bus_Arrive_Info data : (ArrayList<Bus_Arrive_Info>) payload) {
                            if (busArriveList.get(position).getLINE_ID() == data.getLINE_ID()) {
                                busArriveList.get(position).setREMAIN_MIN(data.getREMAIN_MIN());
                                mViewHolderArriveInfo.changeData(busArriveList.get(position).getREMAIN_MIN());
                            }
                        }
                    }
                    break;
                case BUS_FAVORITES_LINE:
                    ViewHolderLine mViewHolderFavorites_L = (ViewHolderLine) holder;
                    for (Object payload : payloads) {
                        for (Bus_Line_checkbox data : (ArrayList<Bus_Line_checkbox>) payload) {
                            if (busFavoritList_L.get(position).getLINE_ID() == data.getLINE_ID()) {
                                busFavoritList_L.get(position).setCHECK(data.isCHECK());
                                mViewHolderFavorites_L.setCheck(busFavoritList_L.get(position).isCHECK());
                            }
                        }
                    }
                    break;
                case BUS_FAVORITES_STATION:
                    ViewHolderStation mViewHolderFavorites_S = (ViewHolderStation) holder;
                    for (Object payload : payloads) {
                        for (Bus_Station_checkbox data : (ArrayList<Bus_Station_checkbox>) payload) {
                            if (busFavoritList_S.get(position).getBUSSTOP_ID() == data.getBUSSTOP_ID()) {
                                busFavoritList_S.get(position).setCHECK(data.isCHECK());
                                mViewHolderFavorites_S.setCheck(busFavoritList_S.get(position).isCHECK());
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        switch (type) {
            case BUS_LINE:
                count = busLineList.size();
                break;
            case BUS_STATION:
                count = busStationList.size();
                break;
            case BUS_LINE_STATION_INFORMATION:
                count = busLSList.size();
                break;
            case BUS_ARRIVE_INFORMATION:
                count = busArriveList.size();
                break;
            case BUS_FAVORITES_LINE:
                count = busFavoritList_L.size();
                break;
            case BUS_FAVORITES_STATION:
                count = busFavoritList_S.size();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type + "| count : " + count);
        }
        return count;
    }

    public void NotifyItemRemoved(int po) {
        notifyItemRemoved(po);
    }

    public void clear_Station() {
        int size = busStationList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                busStationList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    public void clear_Line() {
        int size = busLineList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                busLineList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    public void clear_Line_Station() {
        int size = busLSList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                busLSList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    public void clear_F_Station() {
        int size = busFavoritList_S.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                busFavoritList_S.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    public void clear_F_Line() {
        int size = busFavoritList_L.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                busFavoritList_L.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    public void clear_Arrive() {
        int size = busArriveList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                busArriveList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

}
