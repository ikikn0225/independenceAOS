package com.memory1.independence74.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.R;
import com.memory1.independence74.data.IndeData2;

import java.util.ArrayList;
import java.util.List;

public class IndeAdapter2 extends RecyclerView.Adapter<IndeAdapter2.MyViewHolder> {

    private Context mContext;
    private ArrayList<IndeData2> mIndeData2;
    private ArrayList<IndeData2> mIndeDataFull;

    public IndeAdapter2(Context mContext, ArrayList<IndeData2> mIndeData2) {
        this.mContext = mContext;
        this.mIndeData2 = mIndeData2;
        mIndeDataFull = new ArrayList<>(mIndeData2);
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
    public void onBindViewHolder(@NonNull IndeAdapter2.MyViewHolder holder, final int position) {

        holder.inde_name.setText(mIndeData2.get(position).getName());
        holder.inde_work.setText(mIndeData2.get(position).getWork());
        holder.inde_picture.setImageResource(mIndeData2.get(position).getPicture());

        //해당 카드뷰 클릭 시
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //IndeData에서 URL값을 받아와서 인터넷을 실행한다.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mIndeData2.get(position).getInde_url()));
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
        return mIndeData2.size();
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
            ArrayList<IndeData2> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(mIndeDataFull);

            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(IndeData2 data : mIndeDataFull) {
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
            mIndeData2.clear();
            mIndeData2.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
