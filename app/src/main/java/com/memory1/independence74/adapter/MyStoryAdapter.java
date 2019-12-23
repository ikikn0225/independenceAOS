package com.memory1.independence74.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.R;
import com.memory1.independence74.data.MyStoryData;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class MyStoryAdapter extends FirestoreRecyclerAdapter<MyStoryData, MyStoryAdapter.MyStoryHolder> {
//        extends RecyclerView.Adapter<MyStoryAdapter.MyViewHolder> {


    private OnItemVertClickListener listener;
    private OnItemMyStoryClickListener listener1;

    public MyStoryAdapter(@NonNull FirestoreRecyclerOptions<MyStoryData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyStoryHolder holder, int position, @NonNull MyStoryData model) {

        holder.mystroy_user_name.setText(model.getName());
        holder.mystory_user_date.setText(model.getDate());
        holder.mystroy_user_name2.setText(model.getLogined_name());
        holder.mystory_user_text.setText(model.getLogined_text());
        Picasso.get().load(model.getText_picture()).into(holder.mystory_main_image);
        Picasso.get().load(model.getUser_picture()).into(holder.mystory_user_image);
    }

    @NonNull
    @Override
    public MyStoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_inde_story_item_layout
                , parent, false);

        return new MyStoryHolder(v);
    }


    class MyStoryHolder extends RecyclerView.ViewHolder {

        TextView mystroy_user_name;
        TextView mystory_user_date;
        TextView mystroy_user_name2;
        TextView mystory_user_text;
        ImageView mystory_user_image;
        ImageView mystory_main_image;

        TextView reply_more_btn;

        ImageView mystory_more_vert;

        public MyStoryHolder(@NonNull View itemView) {
            super(itemView);

            mystroy_user_name = itemView.findViewById(R.id.mystroy_user_name);
            mystory_user_date = itemView.findViewById(R.id.mystory_user_date);
            mystroy_user_name2 = itemView.findViewById(R.id.mystroy_user_name2);
            mystory_user_text = itemView.findViewById(R.id.mystory_user_text);
            mystory_user_image = itemView.findViewById(R.id.mystory_user_image);
            mystory_main_image = itemView.findViewById(R.id.mystory_main_image);
            reply_more_btn = itemView.findViewById(R.id.reply_more_btn);

            mystory_more_vert = itemView.findViewById(R.id.mystory_more_vert);

            mystory_more_vert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

            reply_more_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener1 != null && position != RecyclerView.NO_POSITION) {
                        listener1.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemMyStoryClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemMyStoryClickListener(OnItemMyStoryClickListener listener) {
        this.listener1 = listener;
    }

    public interface OnItemVertClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemVertClickListener(OnItemVertClickListener listener) {
        this.listener = listener;
    }

    //리사이클러뷰, 파베 디비 삭제
    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }


//    private Context mContext;
//    private ArrayList<MyStoryData> mMyStoryData;
//    private OnItemMyStoryClickListener listener1;
//    private OnItemMoreVertClickListener listener2;
//    private int position1;
//    private Bitmap bitmap;
//
//    private String user_text;
//
//    public MyStoryAdapter(Context mContext, ArrayList<MyStoryData> mMyStoryData) {
//        this.mContext = mContext;
//        this.mMyStoryData = mMyStoryData;
//    }
//
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
//        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
//        view = layoutInflater.inflate(R.layout.my_inde_story_item_layout, parent, false);
//
//        return new MyViewHolder(view, listener1, listener2);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyStoryAdapter.MyViewHolder holder, final int position) {
//        this.position1 = position;
//
//        holder.mystroy_user_name.setText(mMyStoryData.get(position).getName());
//        holder.mystory_user_date.setText(mMyStoryData.get(position).getDate());
//        holder.mystroy_user_name2.setText(mMyStoryData.get(position).getLogined_name());
//        holder.mystory_user_text.setText(mMyStoryData.get(position).getLogined_text());
//        holder.mystory_main_image.setImageBitmap(StringToBitMap(mMyStoryData.get(position).getText_picture()));
//        holder.mystory_user_image.setImageBitmap(StringToBitMap(mMyStoryData.get(position).getUser_picture()));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mMyStoryData.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView mystroy_user_name;
//        TextView mystory_user_date;
//        TextView mystroy_user_name2;
//        TextView mystory_user_text;
//        ImageView mystory_user_image;
//        ImageView mystory_main_image;
//        ImageView mystory_more_vert;
//
//        public MyViewHolder(@NonNull final View itemView, final OnItemMyStoryClickListener listener, final OnItemMoreVertClickListener listener2) {
//            super(itemView);
//
//            mystroy_user_name = itemView.findViewById(R.id.mystroy_user_name);
//            mystory_user_date = itemView.findViewById(R.id.mystory_user_date);
//            mystroy_user_name2 = itemView.findViewById(R.id.mystroy_user_name2);
//            mystory_user_text = itemView.findViewById(R.id.mystory_user_text);
//            mystory_user_image = itemView.findViewById(R.id.mystory_user_image);
//            mystory_main_image = itemView.findViewById(R.id.mystory_main_image);
//            mystory_more_vert = itemView.findViewById(R.id.mystory_more_vert);
//
//            mystory_user_text.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(position);
//                    }
//                }
//            });
//
//            mystory_more_vert.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (listener2 != null && position != RecyclerView.NO_POSITION) {
//                        position1 = getAdapterPosition();
//                        listener2.onItemClick(mMyStoryData.get(position1));
//                    }
//                }
//            });
//
//
//        }
//    }
//
//    public int get_position() {
//
//        return position1;
//    }
//
//    //아이템 삭제
////    public void removeItem(final int position) {
////        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
////        alertDialog.setTitle("게시물 삭제");
////        alertDialog.setMessage("해당 게시물을 정말 삭제 하시겠습니까?");
////
////        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialogInterface, int i) {
////                //삭제 확인 클릭 시
////                //"public"의 해당 포지션 삭제, "(position)" 해당 포지션의 댓글도 삭제
////                SharedPreferences sharedPreferences1 = mContext.getSharedPreferences("public", MODE_PRIVATE);
////                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
////
////                //user_text 값을 키값으로 설정하기위해 해당 데이터 받아오기
////                String share1 = sharedPreferences1.getString("public", null);
////                try {
////                    JSONArray jsonArray1 = new JSONArray(share1);
////                    JSONObject jsonObject = jsonArray1.getJSONObject(position);
////                    user_text = jsonObject.getString("user_text");
////
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////
////                //선택한 position의 값을 키 값으로 하는 객체 삭제 로직
////                String public_key = sharedPreferences1.getString("public", null);
////
////                try {
////                    JSONArray jsonArray = new JSONArray(public_key);
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////                        //쉐어드의 해당 포지션 데이터 삭제
//////                        jsonArray.remove(position);
////                        //"position" 해당 포지션의 댓글도 삭제
//////                        editor1.putString(user_text, null);
//////                        editor1.apply();
////
////                        mMyStoryData.remove(position);
////                        save_data();
////                        notifyItemRemoved(position);
////                        editor1.putString(user_text, null);
////                        editor1.apply();
////                    }
////
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////                dialogInterface.dismiss();
////                notifyDataSetChanged();
////            }
////        });
////        alertDialog.show();
////
////    }
//
//
//    public interface OnItemMyStoryClickListener {
//        void onItemClick(int position);
//    }
//
//    public void setOnItemMyStoryClickListener(OnItemMyStoryClickListener listener) {
//        listener1 = listener;
//    }
//
//    public interface OnItemMoreVertClickListener {
//        void onItemClick(MyStoryData data);
//    }
//
//    public void setOnItemMoreVertClickListener(OnItemMoreVertClickListener listener) {
//        listener2 = listener;
//    }
//
//    //데이터 저장
////    private void save_data() {
////        SharedPreferences sharedPreferences = mContext.getSharedPreferences("public", MODE_PRIVATE);
////        SharedPreferences.Editor editor = sharedPreferences.edit();
////
////        try {
////            JSONArray jsonArray = new JSONArray();
////            for(int i = 0; i < mMyStoryData.size(); i++) {
////                JSONObject jsonObject = new JSONObject();
////                jsonObject.put("user_image", mMyStoryData.get(i).getUser_picture());
////                jsonObject.put("user_name", mMyStoryData.get(i).getName());
////                jsonObject.put("user_date", mMyStoryData.get(i).getDate());
////                jsonObject.put("user_text_image", mMyStoryData.get(i).getText_picture());
////                jsonObject.put("user_name2", mMyStoryData.get(i).getLogined_name());
////                jsonObject.put("user_text", mMyStoryData.get(i).getLogined_text());
////                jsonArray.put(jsonObject);
////            }
////
////            editor.putString("public", jsonArray.toString());
////            editor.apply();
////
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////
////    }
//
//    private Bitmap StringToBitMap(String encodedString) {
//        try {
//            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
//            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//            return bitmap;
//        } catch (Exception e) {
//            e.getMessage();
//            return null;
//        }
//    }
}


