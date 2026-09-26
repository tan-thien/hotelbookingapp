package com.example.doan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class roomtypeAdapter extends RecyclerView.Adapter<roomtypeAdapter.TyperoomViewHolder> {
    private List<roomtypedata> mroomtypedataList;
    Context context;
    public roomtypeAdapter(List<roomtypedata> roomtypedataList) {
        this.mroomtypedataList = roomtypedataList;
    }
    @NonNull
    @Override
    public roomtypeAdapter.TyperoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.typeroom_item, parent, false);
        return new roomtypeAdapter.TyperoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TyperoomViewHolder holder, int position) {
        roomtypedata roomtype = mroomtypedataList.get(position);
//        holder.roomNameTextView.setText(mBookingList.get(position).getRoomName());
//        holder.startDateTextView.setText(mBookingList.get(position).getStartDate());;
//        holder.endDateTextView.setText(mBookingList.get(position).getEndDate());;
//        Double price = mBookingList.get(position).getPrice();
//        String priceString = String.format(Locale.getDefault(),"%.3f",price);
//        holder.priceTextView.setText(priceString);
        //Log.i("ABCD", booking.toString());
        holder.bind(roomtype);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String idToDelete = mroomtypedataList.get(position).getKey(); // Lấy ID của mục cần xóa
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("RoomTypes");

                    // Xóa tài liệu có ID tương ứng
                    databaseRef.child(idToDelete).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Xóa thành công, cập nhật RecyclerView
                                mroomtypedataList.remove(position); // Xóa item từ danh sách dữ liệu
                                notifyItemRemoved(position); // Cập nhật RecyclerView
                                Toast.makeText(context, "Đã xóa mục", Toast.LENGTH_SHORT).show();

                            } else {
                                // Xử lý lỗi nếu cần
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mroomtypedataList.size();
    }

    public class TyperoomViewHolder extends RecyclerView.ViewHolder {
        TextView typeroomName;
        TextView typeroomDesc;
        //CardView recCard;
        ImageButton btnDelete;

        public TyperoomViewHolder(@NonNull View itemView) {
            super(itemView);
            typeroomName = itemView.findViewById(R.id.recTitle);
            typeroomDesc = itemView.findViewById(R.id.recDesc);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }

        public void bind(roomtypedata roomtype) {
            typeroomName.setText(roomtype.getTyperoomName());
            typeroomDesc.setText(roomtype.getTyperoomDesc());


        }


    }

}
