package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        Button logoutBtn = findViewById((R.id.logoutButton));

        mAuth = FirebaseAuth.getInstance();

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_test = new Intent(getApplicationContext(), testActivity.class);
                startActivity(intent_test);
            }
        });


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_enter = new Intent(getApplicationContext(), communityActivity.class);
                startActivity(intent_enter);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                finishAffinity();
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
