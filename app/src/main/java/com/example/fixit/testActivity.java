package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class testActivity extends AppCompatActivity {
    RadioGroup group;
    Button button;
    String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        init();//변수 연동
        Intent intent=new Intent(testActivity.this, showChecklistActivity.class);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int errorID) {
                switch(errorID){
                    case R.id.check1:
                        option = "1";
                        break;

                    case R.id.check2:
                        option = "2";
                        break;

                    case R.id.check3:
                        option = "3";
                        break;

                    case R.id.check4:
                        option = "4";
                        break;

                    case R.id.check5:
                        option = "5";
                        break;

                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("id", option);
                startActivity(intent);
            }
        });

    }

    public void init(){
        group = findViewById(R.id.radioGroup);
        button = findViewById(R.id.checkbutton);
    }


}
