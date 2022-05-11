package com.example.fixit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class contentActivity extends AppCompatActivity {
    String postID;
    String Title;
    String posting_doc;
    post_data data;
    imgUrl urls;
    String imgId;

    ArrayList<String> s_uri = new ArrayList<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        postID = bundle.getString("postID");
        imgId = bundle.getString("imgID");

        DatabaseReference dataRefer = database.getReference("posts");
        DatabaseReference imgRefer = database.getReference("img");

        TextView title = findViewById(R.id.title);
        TextView content = findViewById(R.id.content);
        Button PostList = findViewById(R.id.PostList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_content);

        Query myPost = dataRefer.orderByChild("id").equalTo(postID);

        Query myImg = imgRefer.orderByChild("imgID").equalTo(imgId);

        myImg.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                urls = snapshot.getValue(imgUrl.class);
                s_uri = urls.getArr();

                MultiImageAdapter adapter = new MultiImageAdapter(s_uri, getApplicationContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true));

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

        myPost.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                data = snapshot.getValue(post_data.class);
                imgId = data.getImgID();
                Title = data.getTitle();
                posting_doc = data.getPosting_doc();

                title.setText(Title);
                content.setText(posting_doc);

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
