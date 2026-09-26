package com.example.doan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doan.DataClass;
import com.example.doan.DetailRoomFragment;
import com.example.doan.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class adapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public adapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(context).load(dataList.get(position).getRoomImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getRoomName());
        holder.recDesc.setText(dataList.get(position).getRoomDesc());
        //holder.recPrice.setText(dataList.get(position).getRoomPrice());
        Double price = dataList.get(position).getRoomPrice();
        String priceString = String.format(Locale.getDefault(),"%.3f",price);
        holder.recPrice.setText(priceString);

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailRCusFragment detailRCusFragment = new detailRCusFragment();
                Bundle args = new Bundle();
                args.putString("Image", dataList.get(holder.getAdapterPosition()).getRoomImage());
                args.putString("Description", dataList.get(holder.getAdapterPosition()).getRoomDesc());
                args.putString("Title", dataList.get(holder.getAdapterPosition()).getRoomName());
                args.putString("TypeRoom",dataList.get(holder.getAdapterPosition()).getRoomTyperoom());
                args.putDouble("Price", dataList.get(holder.getAdapterPosition()).getRoomPrice());
                args.putString("Key",dataList.get(holder.getAdapterPosition()).getKey());
                detailRCusFragment.setArguments(args);

                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_fragment, detailRCusFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }


}
class ViewHolder extends RecyclerView.ViewHolder {

    ImageView recImage;
    TextView recTitle, recDesc, recPrice;
    CardView recCard;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        recPrice = itemView.findViewById(R.id.recPrice);

        recTitle = itemView.findViewById(R.id.recTitle);
    }
}

