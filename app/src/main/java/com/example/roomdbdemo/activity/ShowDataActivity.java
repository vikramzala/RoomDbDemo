package com.example.roomdbdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.roomdbdemo.R;
import com.example.roomdbdemo.adapter.ShowDataListAdapter;
import com.example.roomdbdemo.db.AppDatabase;
import com.example.roomdbdemo.db.DataEntity;
import com.example.roomdbdemo.db.DataListViewModel;

import java.util.List;

public class ShowDataActivity extends AppCompatActivity implements ShowDataListAdapter.Callback {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnBack;
    private ShowDataListAdapter adapter;
    private DataListViewModel viewModel;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        database = AppDatabase.getDatabase(this);
        viewModel = ViewModelProviders.of(ShowDataActivity.this).get(DataListViewModel.class);
        initUI();

    }

    private void initUI() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewModel.getDatas().observe(ShowDataActivity.this, new Observer<List<DataEntity>>() {
            @Override
            public void onChanged(List<DataEntity> datas) {
                if (datas.size() > 0) {
                    adapter = new ShowDataListAdapter(ShowDataActivity.this, datas);
                    adapter.setCallback(ShowDataActivity.this);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

    }

    @Override
    public void onDeleteClick(DataEntity data) {
        database.noteDao().delete(data);
        adapter.addItems(database.noteDao().getAll());
    }
}