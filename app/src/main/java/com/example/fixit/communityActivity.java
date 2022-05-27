package com.example.fixit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class communityActivity extends AppCompatActivity {
    listAdapter Adapter = null;
    RecyclerView list = null;
    int list_added = 0;
    int current_start= 0;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<contents> arrayList;


    ArrayList title_list = new ArrayList<contents>();
    ArrayList title_list_show = new ArrayList<contents>();// title 내용들

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        ActionBar bar = getSupportActionBar();
        //bar.hide();

        Button write = findViewById(R.id.Write);// community에 글 쓰는 기능

        //Todo: DB로 부터 data가져오기

        list = (RecyclerView) findViewById(R.id.recyclerView);
        list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        Adapter = new listAdapter(this, title_list_show);

        DatabaseReference dataRef = database.getReference("list");

        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for(DataSnapshot datasnap : snapshot.getChildren()){
                    contents data = datasnap.getValue(contents.class);
                    arrayList.add(0,data);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Fraglike", "error"); // 에러문 출력
            }
        });

        adapter = new Post_CustomAdapter(arrayList
        , getApplicationContext());
        list.setAdapter(adapter);


        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_write = new Intent(getApplicationContext(), postingActivity.class);
                startActivity(intent_write);
                finish();
            }
        });

    }
}
