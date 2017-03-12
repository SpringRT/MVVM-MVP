package com.a11.mvvm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.a11.mvvm.R;
import com.a11.mvvm.adapter.MyAdapter;
import com.a11.mvvm.model.User;
import com.a11.mvvm.viewmodel.EditViewModel;
import com.a11.mvvm.viewmodel.IViewModel;
import com.a11.mvvm.viewmodel.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private IViewModel viewModel;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditActivity.class));
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        users = new ArrayList<>();
        viewModel = new MainViewModel(users, getApplicationContext().getSharedPreferences("com.a11.mvp.USERS", MODE_PRIVATE));
        setData();
    }

    private void setData() {
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(new MyAdapter(users, recycler, this));
    }

    public void onItemClick(int position){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void onClickDelete(int position){
        users.remove(position);
        viewModel.notifyDataSetChanged();
    }
}
