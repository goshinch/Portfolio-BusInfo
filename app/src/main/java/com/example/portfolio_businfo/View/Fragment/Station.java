package com.example.portfolio_businfo.View.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.Activity.MainActivity;
import com.example.portfolio_businfo.ViewModel.Fragment.Fragment_Station_ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Station extends Fragment {
    private RecyclerView mRecyclerView;
    private MainActivity mMainActivity;
    private EditText searchEditText;
    private TextView explanatory;
    private ImageView reader;
    private Bus_Database db;
    private Fragment_Station_ViewModel fragment_station_viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_stop,container,false);
        fragment_station_viewModel = new ViewModelProvider(this).get(Fragment_Station_ViewModel.class);

        mRecyclerView = mView.findViewById(R.id.list_stop);

        mMainActivity = (MainActivity) getActivity();
        searchEditText = mMainActivity.findViewById(R.id.search_text);


        explanatory = mView.findViewById(R.id.ex_station);
        reader = mView.findViewById(R.id.rd_station);

        searchEditText.setText("");
        SearchEditText();

        db = Room.databaseBuilder(getContext(), Bus_Database.class, "BusData")
                .build();

        fragment_station_viewModel.init(getContext(), mRecyclerView, db, mMainActivity);

        fragment_station_viewModel.RecyclerInit();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragment_station_viewModel.RecyclerSet();

        fragment_station_viewModel.setArrayListdata().observe(getViewLifecycleOwner(), new Observer<List<Bus_Station>>() {
            @Override
            public void onChanged(List<Bus_Station> bus_stations) {
                fragment_station_viewModel.getmRecyclerViewAdapter().clear_Station();
                fragment_station_viewModel.getmRecyclerViewAdapter().ArrayListDataSet_Station((ArrayList<Bus_Station>) bus_stations);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        searchEditText.getText().clear();
    }

    private void SearchEditText(){
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && count == 0) {
                    explanatory.setVisibility(View.VISIBLE);
                    reader.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
                else {
                    explanatory.setVisibility(View.GONE);
                    reader.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                fragment_station_viewModel.SearchStationData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
