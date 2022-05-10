package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class communityActivity extends AppCompatActivity {
    listAdapter Adapter = null;
    ListView list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        ActionBar bar = getSupportActionBar();
        bar.hide();

        Button write = findViewById(R.id.Write);// community에 글 쓰는 기능
        Button previous = findViewById(R.id.Previous); // 이전 내용 가져오기
        Button next = findViewById(R.id.Next); // 다음 내용 가져오기
        ArrayList title_list = new ArrayList<contents>();// title 내용들

        title_list.add(new contents("hi"));//data 추가
        title_list.add(new contents("hello"));//data 추가
        title_list.add(new contents("please"));//data 추가
        title_list.add(new contents("data"));//data 추가


        list = (ListView) findViewById(R.id.list_view);
        Adapter = new listAdapter(this, title_list);
        list.setAdapter(Adapter);


        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_write = new Intent(getApplicationContext(), postingActivity.class);
                startActivity(intent_write);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: 최신에 작성된 글 보러 가기
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: 과거에 작성된 글 보러 가기
            }
        });

    }
}
