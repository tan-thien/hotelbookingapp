package com.example.doan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class AdapterSerCus  extends RecyclerView.Adapter<ServiceCusViewHolder>{
    private Context context;
    private List<DataService> dataList;

    public AdapterSerCus(Context context, List<DataService> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ServiceCusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false);
        return new ServiceCusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceCusViewHolder holder, int position) {
        //DataService dataService = dataList.get(position);

        Glide.with(context).load(dataList.get(position).getImageSer()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getNameSer());
        holder.recDesc.setText(dataList.get(position).getDescSer());
        //holder.recPrice.setText(dataList.get(position).getPriceSer());
        Double price = dataList.get(position).getPriceSer();
        String priceString = String.format(Locale.getDefault(),"%.3f", price);
        holder.recPrice.setText(priceString);


        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailSCusFragment detailSerCusFragment = new DetailSCusFragment();
                Bundle args = new Bundle();
                args.putString("Image1", dataList.get(holder.getAdapterPosition()).getImageSer());
                args.putString("Description1", dataList.get(holder.getAdapterPosition()).getDescSer());
                args.putString("Title1", dataList.get(holder.getAdapterPosition()).getNameSer());
                args.putDouble("Price1", dataList.get(holder.getAdapterPosition()).getPriceSer());
                args.putString("Key1", dataList.get(holder.getAdapterPosition()).getKey());
                detailSerCusFragment.setArguments(args);

                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_fragment, detailSerCusFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(List<DataService> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }

}
class ServiceCusViewHolder extends RecyclerView.ViewHolder {
    ImageView recImage;
    TextView recTitle, recDesc, recPrice;
    CardView recCard;

    public ServiceCusViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        recPrice = itemView.findViewById(R.id.recPrice);
        recTitle = itemView.findViewById(R.id.recTitle);
    }
}