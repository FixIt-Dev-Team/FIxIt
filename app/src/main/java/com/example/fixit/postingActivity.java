package com.example.fixit;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import java.util.ArrayList;

public class postingActivity extends AppCompatActivity {

    ArrayList<String> urilist = new ArrayList<>();
    imgUrl URLlist = new imgUrl();

    RecyclerView recyclerView;
    MultiImageAdapter adapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting);
        verifyStoragePermissions(this);

        Button img_button = findViewById(R.id.img_button);
        Button post_button = findViewById(R.id.Post_button);

        img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2222);
            }
        });

        recyclerView = findViewById(R.id.recyclerView_img);

        DatabaseReference databaseReference = database.getReference();

        ActionBar bar = getSupportActionBar();
        //bar.hide();

        EditText Post_Name = findViewById(R.id.Post_Name);
        EditText Type_Post = findViewById(R.id.Type_Post);
        Button Post_button = findViewById(R.id.Post_button);

        Post_button.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String post_name = Post_Name.getText().toString();
                String content = Type_Post.getText().toString();

                post_data post = new post_data();
                post.setPosting_doc(content);
                post.setTitle(post_name);
                post.setUid(user.getUid());
                post.setUser_name(user.getDisplayName());

                contents list = new contents();

                DatabaseReference postsRef = databaseReference.child("posts");
                DatabaseReference post_img = databaseReference.child("img");
                DatabaseReference post_list = databaseReference.child("list");

                DatabaseReference newPostRef = postsRef.push();
                String postId = newPostRef.getKey();
                DatabaseReference newPostImgRef = post_img.push();
                String ImgId = newPostImgRef.getKey();
                post.setID(postId);
                post.setImgID(ImgId);
                URLlist.setImgID(ImgId);

                list.setID(postId);
                list.setImgID(ImgId);
                list.setTitle(post_name);

                post_list.push().setValue(list);

                newPostRef.setValue(post);
                uploadSingleImg(urilist,newPostImgRef);

                Intent intent_post = new Intent(getApplicationContext(), communityActivity.class);
                startActivity(intent_post);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null){
            Toast.makeText(getApplicationContext(),"선택하신 이미지가 없습니다.",Toast.LENGTH_SHORT).show();
        }else{
            if(data.getClipData() == null){

                Uri imageUri = data.getData();
                urilist.add(imageUri.toString());

                adapter = new MultiImageAdapter(urilist, getApplicationContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,true));

            }else{
                ClipData clipData = data.getClipData();
                if(clipData.getItemCount() > 5){
                    Toast.makeText(getApplicationContext(),"이미지는 최대 5장 까지 첨부 가능합니다.",Toast.LENGTH_SHORT).show();
                }else{

                    for(int i=0;i<clipData.getItemCount();i++){

                        Uri imageUri = clipData.getItemAt(i).getUri();
                        try{

                            urilist.add(
                                    imageUri.toString()
                            );

                        }catch(Exception e){
                            Log.e("File","File select error",e);
                        }
                    }

                    adapter = new MultiImageAdapter(urilist, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,true));

                }
            }
        }

    }

    private String getRealPathFromUri(Uri uri)
    {
        String[] proj=  {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String url = cursor.getString(columnIndex);
        cursor.close();
        return  url;
    }

    private void uploadSingleImg(ArrayList<String> uri,DatabaseReference newPostImgRef){
        for(int i=0;i<uri.size();i++){
            try{

                String url = getRealPathFromUri(Uri.parse(uri.get(i)));

                StorageReference storageReference = storage.getReference();

                Uri file = Uri.fromFile(new File(url));
                final StorageReference riversRef = storageReference.child("images/"+file.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(file);

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful()){

                            throw task.getException();
                        }
                        return riversRef.getDownloadUrl();
                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Log.d("upload","clear!");
                            Uri downloadUrl = task.getResult();
                            URLlist.setipnut(downloadUrl.toString());
                            newPostImgRef.setValue(URLlist);
                        }else{
                            Toast.makeText(getApplicationContext(),"업로드 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }catch(Exception e){
                Log.e("UPFILE","upload uri fail",e);
            }
        }

    }
}
