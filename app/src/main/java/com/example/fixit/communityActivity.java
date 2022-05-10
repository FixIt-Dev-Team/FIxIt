package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        //Todo: DB로 부터 data가져오기
        title_list.add(new contents("승현", "디자인 학과 학생", "hi"));//data 추가
        title_list.add(new contents("승현", "찾습니다", "hi"));//data 추가
        title_list.add(new contents("승현", "저의 부족한 디자인 실력을 보충해주세요", "hi"));//data 추가
        title_list.add(new contents("승현", "git 알려줄사람 구합니다", "hi"));//data 추가


        list = (ListView) findViewById(R.id.list_view);
        Adapter = new listAdapter(this, title_list);
        list.setAdapter(Adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), contentActivity.class);

                contents content = (contents) parent.getItemAtPosition(position);

                //bundle에 넣어서 content activity에 전달
                Bundle bundle = new Bundle();
                bundle.putString("title", content.getTitle());
                bundle.putString("content", content.getContent());

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });


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
