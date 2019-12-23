package com.memory1.independence74.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.R;
import com.memory1.independence74.data.MapIndeData;

import java.util.ArrayList;


public class MapAdapter extends RecyclerView.Adapter<MapAdapter.MapViewHoler> {
    private ArrayList<MapIndeData> mapIndeDataArrayList;
    private Context mContext;
    private OnItemClickListener mListener;
    private int position1;

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MapAdapter(ArrayList<MapIndeData> mapIndeDataArrayList, Context mContext) {
        this.mapIndeDataArrayList = mapIndeDataArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MapViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_inde_item_layout, parent, false);
        MapViewHoler mapViewHoler = new MapViewHoler(v, mListener);
        return mapViewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull MapViewHoler holder, int position) {
        final MapIndeData currentItem = mapIndeDataArrayList.get(position);

        holder.map_inde_name.setText(currentItem.getName());
        holder.map_inde_location.setText(currentItem.getLocation());
        holder.map_inde_distance.setText(currentItem.getDistance());
        holder.map_inde_picture.setImageResource(currentItem.getPicture());

        //홈페이지 버튼을 누를때
            holder.map_inde_homepage_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //홈페이지 버튼이 널값이 아니면
                    if(currentItem.getHomepage_url() != null) {
                        //IndeData에서 URL값을 받아와서 인터넷을 실행한다.
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.getHomepage_url()));
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "홈페이지가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            //레이아웃 클릭 시 구글 맵 위에 해당 마커로 이동
            //1. 해당 포지션 클릭 리스너
            //2. 클릭 시 구글 맵으로 이동(Map 클래스에서 메서드로 distance 데이터 중에 해당 포지션의 기념관 이름과 같은 마커를 줌한다.)


    }

    public int get_position() {

        return position1;
    }

    @Override
    public int getItemCount() {
        return mapIndeDataArrayList.size();
    }

    public static class MapViewHoler extends RecyclerView.ViewHolder {
        TextView map_inde_name;
        TextView map_inde_location;
        TextView map_inde_distance;
        ImageView map_inde_picture;
        Button map_inde_homepage_btn;

        public MapViewHoler(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            map_inde_name = itemView.findViewById(R.id.map_inde_name);
            map_inde_location = itemView.findViewById(R.id.map_inde_location);
            map_inde_distance = itemView.findViewById(R.id.map_inde_distance);
            map_inde_picture = itemView.findViewById(R.id.map_inde_image);
            map_inde_homepage_btn = itemView.findViewById(R.id.map_inde_btn_homepage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
