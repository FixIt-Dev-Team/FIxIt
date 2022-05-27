package com.example.fixit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class Post_CustomAdapter extends RecyclerView.Adapter<Post_CustomAdapter.CustomViewHolder> {

    private ArrayList<contents> arrayList;
    private ArrayList<ArrayList<String>> Img_urls = new ArrayList<>();
    private Context context;

    private String postID;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public Post_CustomAdapter(ArrayList<contents> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        CustomViewHolder Holder = new CustomViewHolder(view);
        return Holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.Title.setText(arrayList.get(position).getTitle());

        String ImgID = arrayList.get(position).getImgID();

        DatabaseReference imgRefer = database.getReference("img");

        Query myImg = imgRefer.orderByChild("imgID").equalTo(ImgID);

        ArrayList<String> s_uri = new ArrayList<>();

        myImg.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                imgUrl urls = snapshot.getValue(imgUrl.class);

                ArrayList<String> s_uri = urls.getArr();

                Img_urls.add(s_uri);

                String Img_url = s_uri.get(0);

                Glide.with(holder.itemView.getContext())
                        .load(Img_url)
                        .override(150,150)
                        .into(holder.img);
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

    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView Title;

        public CustomViewHolder(@NonNull View itemView){
            super(itemView);
            this.img = itemView.findViewById(R.id.imageIcon);
            this.Title = itemView.findViewById(R.id.titleName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Context context = view.getContext();
                    Intent intent = new Intent(context, contentActivity.class);

                    int pos = getBindingAdapterPosition();
                    contents content = arrayList.get(pos);

                    //bundle에 넣어서 content activity에 전달
                    Bundle bundle = new Bundle();
                    bundle.putString("postID", content.getID());
                    bundle.putString("imgID",content.getImgID());
                    //bundle.putStringArrayList("Img_URLs",);

                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });

        }

    }

}
