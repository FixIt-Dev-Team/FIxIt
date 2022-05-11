package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class contentActivity extends AppCompatActivity {
    String title_data;
    String content_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        TextView title = findViewById(R.id.title);
        TextView content = findViewById(R.id.content);
        Button PostList = findViewById(R.id.PostList);

        //intent-bundle을 이용해서 data 가져오기
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        title_data = bundle.getString("title");
        content_data = bundle.getString("content");

        //TextView에 setting
        title.setText(title_data);
        content.setText(content_data);

        PostList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), communityActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
