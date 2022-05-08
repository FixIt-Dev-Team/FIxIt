package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class welcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        Button test = findViewById(R.id.welcome_test);
        Button enter = findViewById(R.id.welcome_enter);
        Button logoutBtn;

        mAuth = FirebaseAuth.getInstance();

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_test = new Intent(getApplicationContext(), MainActivity.class);//intent 수정 필요, class 만들어서 넣기
                startActivity(intent_test);
            }
        });


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_enter = new Intent(getApplicationContext(), MainActivity.class);//intent 수정 필요, class 만들어서 넣기
                startActivity(intent_enter);
            }
        });
    }

    /**
     * 로그아웃 메서드
     * */
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * 회원탈퇴 메서드
     * */
    private void revokeAccess() {
        mAuth.getCurrentUser().delete();
    }

}
