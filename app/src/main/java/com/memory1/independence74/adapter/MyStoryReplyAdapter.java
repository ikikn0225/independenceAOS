package com.memory1.independence74.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.R;
import com.memory1.independence74.data.MyStoryReplyData;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class MyStoryReplyAdapter extends FirestoreRecyclerAdapter<MyStoryReplyData, MyStoryReplyAdapter.MyStoryReplyHolder> {


    public MyStoryReplyAdapter(@NonNull FirestoreRecyclerOptions<MyStoryReplyData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyStoryReplyHolder holder, int position, @NonNull MyStoryReplyData model) {
        holder.mystory_user_text.setText(model.getUser_text());
        holder.mystroy_user_name.setText(model.getUser_name());
        Picasso.get().load(model.getUser_image()).into(holder.mystory_user_image);
    }

    @NonNull
    @Override
    public MyStoryReplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_inde_story_reply_item_layout
                , parent, false);

        return new MyStoryReplyHolder(v);
    }

    class MyStoryReplyHolder extends RecyclerView.ViewHolder {
        TextView mystroy_user_name;
        TextView mystory_user_text;
        ImageView mystory_user_image;

        public MyStoryReplyHolder(@NonNull View itemView) {
            super(itemView);

            mystroy_user_name = itemView.findViewById(R.id.mystory_reply_user_name);
            mystory_user_text = itemView.findViewById(R.id.mystory_reply_user_text);
            mystory_user_image = itemView.findViewById(R.id.mystory_reply_user_image);
        }
    }
//    private Context mContext;
//    private ArrayList<MyStoryReplyData> mMyStoryReplyData;
//
//    public MyStoryReplyAdapter(Context mContext, ArrayList<MyStoryReplyData> mMyStoryReplyData) {
//        this.mContext = mContext;
//        this.mMyStoryReplyData = mMyStoryReplyData;
//    }
//
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
//        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
//        view = layoutInflater.inflate(R.layout.my_inde_story_reply_item_layout, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyStoryReplyAdapter.MyViewHolder holder, final int position) {
//
//        holder.mystory_user_text.setText(mMyStoryReplyData.get(position).getUser_text());
//        holder.mystroy_user_name.setText(mMyStoryReplyData.get(position).getUser_name());
//        holder.mystory_user_image.setImageURI(mMyStoryReplyData.get(position).getUser_image());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mMyStoryReplyData.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView mystroy_user_name;
//        TextView mystory_user_text;
//        ImageView mystory_user_image;
//
//        public MyViewHolder(@NonNull final View itemView) {
//            super(itemView);
//
//            mystroy_user_name = itemView.findViewById(R.id.mystory_reply_user_name);
//            mystory_user_text = itemView.findViewById(R.id.mystory_reply_user_text);
//            mystory_user_image = itemView.findViewById(R.id.mystory_reply_user_image);
//
//
//        }
//    }


}

