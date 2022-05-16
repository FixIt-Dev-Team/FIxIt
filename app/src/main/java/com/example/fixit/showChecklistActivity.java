package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class showChecklistActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist);
        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        String errorID = intent.getStringExtra("id");

        switch (errorID){
            case "1":
                textView.setText("1. RAM 재장착\n2. 수리점 방문");
                break;

            case "2":
                textView.setText("1. 모니터 선 교체\n2. 모니터 교체\n3. 그래픽 카드 교체 \n4. 메인보드 교체");
                break;

            case "3":
                textView.setText("1. 모니터 선 교체\n2. 모니터 교체\n3. 그래픽 카드 교체");
                break;

            case "4":
                textView.setText("1. 해당 프로그램 재설치\n2. 윈도우 포멧\n3. BIOS 업데이트\n 4. 수리점 방문");
                break;

            case "5":
                textView.setText("1. 윈도우 포멧");
                break;
        }
    }
}
