package com.memory1.independence74.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.data.IndeData;
import com.memory1.independence74.R;

import java.util.ArrayList;
import java.util.List;

public class IndeAdapter extends RecyclerView.Adapter<IndeAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<IndeData> mIndeData;
    private ArrayList<IndeData> mIndeDataFull;

    public IndeAdapter  (Context mContext, ArrayList<IndeData> mIndeData) {
        this.mContext = mContext;
        this.mIndeData = mIndeData;
        mIndeDataFull = new ArrayList<>(mIndeData);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.inde_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndeAdapter.MyViewHolder holder, final int position) {

        holder.inde_name.setText(mIndeData.get(position).getName());
        holder.inde_work.setText(mIndeData.get(position).getWork());
        holder.inde_picture.setImageResource(mIndeData.get(position).getPicture());

        //해당 카드뷰 클릭 시
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //IndeData에서 URL값을 받아와서 인터넷을 실행한다.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mIndeData.get(position).getInde_url()));
                mContext.startActivity(intent);


                //인터넷 실행불가 시 디바이스 내부 데이터 넘겨줄때 사용할 데이터
//                Intent intent = new Intent(mContext, IndeDetail.class);
//
//                intent.putExtra("name", mIndeData.get(position).getName());
//                Log.e("name", mIndeData.get(position).getName());
//
//                intent.putExtra("work", mIndeData.get(position).getWork());
//                Log.e("work", mIndeData.get(position).getWork());
//
//                intent.putExtra("image", mIndeData.get(position).getPicture());
//                Log.e("image", "" + mIndeData.get(position).getPicture());
//
//                intent.putExtra("image2", mIndeData.get(position).getPicture2());
//                Log.e("image2", "" + mIndeData.get(position).getPicture2());
//
//                intent.putExtra("image3", mIndeData.get(position).getPicture3());
//                Log.e("image3", "" + mIndeData.get(position).getPicture3());
//
//                intent.putExtra("history", mIndeData.get(position).getHistory());
//                Log.e("history", mIndeData.get(position).getHistory());
//
//                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mIndeData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView inde_name;
        TextView inde_work;
        ImageView inde_picture;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            inde_name = itemView.findViewById(R.id.inde_name_put);
            inde_work = itemView.findViewById(R.id.inde_history_put);
            inde_picture = itemView.findViewById(R.id.inde_picture);
            cardView = itemView.findViewById(R.id.inde_cardview);
        }
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<IndeData> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(mIndeDataFull);

            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(IndeData data : mIndeDataFull) {
                    if(data.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(data);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mIndeData.clear();
            mIndeData.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
