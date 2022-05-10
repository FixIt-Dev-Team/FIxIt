package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class postingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting);

        ActionBar bar = getSupportActionBar();
        bar.hide();

        EditText Post_Name = findViewById(R.id.Post_Name);
        EditText Type_Post = findViewById(R.id.Type_Post);
        Button Post_button = findViewById(R.id.Post_button);

        Post_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = Post_Name.getText().toString();
                String content = Type_Post.getText().toString();

                // Todo: DB에 Post data 저장

                Intent intent_post = new Intent(getApplicationContext(), communityActivity.class);
                startActivity(intent_post);
                finish();
            }
        });

    }
}
