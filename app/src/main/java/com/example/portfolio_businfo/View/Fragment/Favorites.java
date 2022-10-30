package com.example.portfolio_businfo.View.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.Activity.MainActivity;
import com.example.portfolio_businfo.ViewModel.Fragment.Fragment_Favorites_ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends Fragment {
    private Fragment_Favorites_ViewModel fragment_favorites_viewModel;
    private Button openANDclose;
    private RecyclerView mRecyclerView, mRecyclerView2;
    private Bus_Database db;
    private MainActivity mMainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_favorites,container,false);
        Log.d("TAG", "onCreateView: ");
        fragment_favorites_viewModel = new ViewModelProvider(this).get(Fragment_Favorites_ViewModel.class);
        mRecyclerView = mView.findViewById(R.id.list_favorites);
        mRecyclerView2 = mView.findViewById(R.id.list_favorites2);

        mMainActivity = (MainActivity) getActivity();

        openANDclose = mView.findViewById(R.id.OpenAndClose);

        db = Room.databaseBuilder(getContext(), Bus_Database.class, "BusData").build();

        fragment_favorites_viewModel.init(getContext(), db, mMainActivity);

        openANDclose.setOnClickListener(onClickListener);

        fragment_favorites_viewModel.getRecyclerView(mRecyclerView);
        fragment_favorites_viewModel.RecyclerInitStation();

        fragment_favorites_viewModel.getRecyclerView2(mRecyclerView2);
        fragment_favorites_viewModel.RecyclerInitLine();

        return mView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAG", "onPause: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG", "onResume: ");
        fragment_favorites_viewModel.setFavoritLists();
        FavoritesSelect();
    }

    private void FavoritesSelect() {
        fragment_favorites_viewModel.setStationListsData().observe(getViewLifecycleOwner(), new Observer<List<Bus_Station>>() {
            @Override
            public void onChanged(List<Bus_Station> bus_stations) {
                fragment_favorites_viewModel.getRecyclerViewAdapter().clear_F_Station();
                fragment_favorites_viewModel.getRecyclerViewAdapter().ArrayListDataSet_Favorit_S((ArrayList<Bus_Station>) bus_stations);
            }
        });
        fragment_favorites_viewModel.setLineListsData().observe(getViewLifecycleOwner(), new Observer<List<Bus_Line>>() {
            @Override
            public void onChanged(List<Bus_Line> bus_lines) {
                fragment_favorites_viewModel.getRecyclerViewAdapter2().clear_F_Line();
                fragment_favorites_viewModel.getRecyclerViewAdapter2().ArrayListDataSet_Favorit_L((ArrayList<Bus_Line>) bus_lines);
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fragment_favorites_viewModel.OpenANDClose(mRecyclerView, mRecyclerView2, openANDclose);
        }
    };


}
