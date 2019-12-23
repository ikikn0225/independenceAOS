package com.memory1.independence74.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.R;
import com.memory1.independence74.data.WianbooVictimData;

import java.util.List;

public class WianbooAdapter extends RecyclerView.Adapter<WianbooAdapter.MyViewHolder> {

    private Context mContext;
    private List<WianbooVictimData> mWianbooVictimData;

    public WianbooAdapter(Context mContext, List<WianbooVictimData> mWianbooVictimData) {
        this.mContext = mContext;
        this.mWianbooVictimData = mWianbooVictimData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.wianboo_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WianbooAdapter.MyViewHolder holder, final int position) {

        holder.wianboovictim_name.setText(mWianbooVictimData.get(position).getWianboovictim_name());
        holder.wianboovictim_year.setText(mWianbooVictimData.get(position).getWianboovictim_year());
        holder.wianboovictim_picture.setImageResource(mWianbooVictimData.get(position).getWianboovictim_picture());

    }

    @Override
    public int getItemCount() {
        return mWianbooVictimData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView wianboovictim_name;
        TextView wianboovictim_year;
        ImageView wianboovictim_picture;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            wianboovictim_name = itemView.findViewById(R.id.wianboo_name_put);
            wianboovictim_year = itemView.findViewById(R.id.wianboovictim_year);
            wianboovictim_picture = itemView.findViewById(R.id.wianboovictim_picture);
            cardView = itemView.findViewById(R.id.wianboovictim_cardview);
        }
    }
}
