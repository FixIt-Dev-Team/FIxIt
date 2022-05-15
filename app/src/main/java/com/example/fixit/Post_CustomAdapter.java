package com.example.fixit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Post_CustomAdapter extends RecyclerView.Adapter<Post_CustomAdapter.CustomViewHolder> {

    private ArrayList<contents> arrayList;
    private Context context;

    private String postID;

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
        //holder.img =

        holder.Title.setText(arrayList.get(position).getTitle());

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

                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });

        }

    }

}
