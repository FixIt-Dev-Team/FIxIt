package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class showChecklistActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmenMonitoroff fragmenMonitoroff;
    private FragmentCannotboot fragmentCannotboot;
    private FragmentLinemonitor fragmentLinemonitor;
    private FragmentNeedformat fragmentNeedformat;
    private FragmentSlowdown fragmentSlowdown;
    private FragmentTransaction transaction;

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist);
        textView = findViewById(R.id.textView);
        Button communityBtn = findViewById(R.id.test_community_btn);

        Intent intent = getIntent();
        String errorID = intent.getStringExtra("id");

        fragmentManager = getSupportFragmentManager();

        fragmenMonitoroff = new FragmenMonitoroff();
        fragmentCannotboot = new FragmentCannotboot();
        fragmentLinemonitor = new FragmentLinemonitor();
        fragmentNeedformat = new FragmentNeedformat();
        fragmentSlowdown = new FragmentSlowdown();

        switch (errorID){
            case "1":
                textView.setText("1. RAM 재장착\n2. 수리점 방문");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.video_fragment, fragmentCannotboot).commitAllowingStateLoss();
                break;

            case "2":
                textView.setText("1. 모니터 선 교체\n2. 모니터 교체\n3. 그래픽 카드 교체 \n4. 메인보드 교체");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.video_fragment, fragmenMonitoroff).commitAllowingStateLoss();
                break;

            case "3":
                textView.setText("1. 모니터 선 교체\n2. 모니터 교체\n3. 그래픽 카드 교체");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.video_fragment, fragmentLinemonitor).commitAllowingStateLoss();
                break;

            case "4":
                textView.setText("1. 해당 프로그램 재설치\n2. 윈도우 포멧\n3. BIOS 업데이트\n4. 수리점 방문");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.video_fragment, fragmentNeedformat).commitAllowingStateLoss();
                break;

            case "5":
                textView.setText("1. 윈도우 포멧");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.video_fragment, fragmentSlowdown).commitAllowingStateLoss();
                break;
        }

        communityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_enter = new Intent(getApplicationContext(), communityActivity.class);
                startActivity(intent_enter);
            }
        });
    }
}
