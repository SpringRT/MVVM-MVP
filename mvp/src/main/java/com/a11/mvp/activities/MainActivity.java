package com.a11.mvp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.a11.mvp.R;
import com.a11.mvp.adapter.MyAdapter;
import com.a11.mvp.model.User;
import com.a11.mvp.presenter.Presenter;
import com.a11.mvp.view.MainView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView {

    Presenter presenter = Presenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    public void onResume(){
        super.onResume();
        presenter.notifyCreatedMain(this, getApplicationContext().getSharedPreferences("com.a11.mvp.USERS", MODE_PRIVATE));

    }

    @Override
    public void setData(ArrayList<User> data) {
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(new MyAdapter(data, recycler, this));
    }

    public void onItemClick(int position){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void onClickDelete(int position){
        presenter.deleteUser(position);
    }
}



