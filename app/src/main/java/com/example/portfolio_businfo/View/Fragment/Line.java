package com.example.portfolio_businfo.View.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.Activity.MainActivity;
import com.example.portfolio_businfo.ViewModel.Fragment.Fragment_Line_ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Line  extends Fragment {
    private RecyclerView mRecyclerView;
    private MainActivity mMainActivity;
    private TextView explanatory;
    private ImageView reader;
    private ArrayList<Bus_Line> arrayListdata;
    private EditText searchEditText;
    private Fragment_Line_ViewModel fragment_line_viewModel;
    private Bus_Database db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_line,container,false);
        fragment_line_viewModel = new ViewModelProvider(this).get(Fragment_Line_ViewModel.class);

        mRecyclerView = mView.findViewById(R.id.list_line);

        mMainActivity = (MainActivity) getActivity();
        searchEditText = mMainActivity.findViewById(R.id.search_text);

        explanatory = mView.findViewById(R.id.ex_line);
        reader = mView.findViewById(R.id.rd_line);

        db = Room.databaseBuilder(getContext(), Bus_Database.class, "BusData").build();

        searchEditText.setText("");
        SearchEditText();

        arrayListdata = new ArrayList<>();

        fragment_line_viewModel.getRecyclerView(mRecyclerView);

        fragment_line_viewModel.init(getContext(),db);

        fragment_line_viewModel.RecyclerInit();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        fragment_line_viewModel.RecyclerSet();

        fragment_line_viewModel.setArrayListdata().observe(getViewLifecycleOwner(), new Observer<List<Bus_Line>>() {
            @Override
            public void onChanged(List<Bus_Line> bus_lines) {
                fragment_line_viewModel.getmRecyclerViewAdapter().clear_Line();
                fragment_line_viewModel.getmRecyclerViewAdapter().ArrayListDataSet_Line((ArrayList<Bus_Line>) bus_lines);
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
                fragment_line_viewModel.SearchLineData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
