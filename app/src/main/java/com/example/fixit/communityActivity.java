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

    /*public boolean load_contents(int start, int last){

        if(title_list.size() < start){
            return false;
        }

        if(start < 0){
            return false;
        }

        title_list_show.clear();

        for(int i=start;i < last && i < title_list.size();i++){
            title_list_show.add(0,title_list.get(i));
        }

        current_start = start;

        list.setAdapter(Adapter);

        return true;

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        ActionBar bar = getSupportActionBar();
        bar.hide();

        Button write = findViewById(R.id.Write);// community에 글 쓰는 기능
        //Button previous = findViewById(R.id.Previous); // 이전 내용 가져오기
        //Button next = findViewById(R.id.Next); // 다음 내용 가져오기

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


        /*
        Query myTopPostsList = dataRef.child("list")
                .orderByChild("time").limitToLast(100);

        myTopPostsList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("Query", "onChildAdded:" + snapshot.getKey());
                contents data = snapshot.getValue(com.example.fixit.contents.class);
                title_list.add(data);
                list_added++;
                Log.d("Query", "onChildAdded:" + data.getTitle());

                if(list_added <= 10){

                    title_list_show.add(0,data);
                    list.setAdapter(Adapter);

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), contentActivity.class);

                contents content = (contents) parent.getItemAtPosition(position);

                //bundle에 넣어서 content activity에 전달
                Bundle bundle = new Bundle();
                bundle.putString("postID", content.getID());
                bundle.putString("imgID",content.getImgID());

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
*/

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_write = new Intent(getApplicationContext(), postingActivity.class);
                startActivity(intent_write);
                finish();
            }
        });
        /*
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: 최신에 작성된 글 보러 가기
                if(load_contents(current_start - 10,current_start)){
                    Log.d("List", "Clear_pre");
                }else{
                    Toast.makeText(communityActivity.this, "이전 작성글이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: 과거에 작성된 글 보러 가기

                if(load_contents(current_start + 10, current_start+20)){
                    Log.d("List", "Clear_next");
                }else{
                    Toast.makeText(communityActivity.this, "다음 작성글이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */

    }
}
