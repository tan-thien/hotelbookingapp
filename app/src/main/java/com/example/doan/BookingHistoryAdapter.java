package com.example.doan;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.BookingViewHolder> {

    private List<Booking> mBookingList;


    public BookingHistoryAdapter(List<Booking> bookingList) {
        this.mBookingList = bookingList;

    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = mBookingList.get(position);
//        holder.roomNameTextView.setText(mBookingList.get(position).getRoomName());
//        holder.startDateTextView.setText(mBookingList.get(position).getStartDate());;
//        holder.endDateTextView.setText(mBookingList.get(position).getEndDate());;
//        Double price = mBookingList.get(position).getPrice();
//        String priceString = String.format(Locale.getDefault(),"%.3f",price);
//        holder.priceTextView.setText(priceString);
        //Log.i("ABCD", booking.toString());
        holder.bind(booking);
    }

    @Override
    public int getItemCount() {
        return mBookingList.size();
    }



    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView roomNameTextView;
        TextView startDateTextView;
        TextView endDateTextView;
        TextView priceTextView;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNameTextView = itemView.findViewById(R.id.roomNameTextView);
            startDateTextView = itemView.findViewById(R.id.startDate);
            endDateTextView = itemView.findViewById(R.id.endDate);
            priceTextView = itemView.findViewById(R.id.price);
        }

        public void bind(Booking booking) {
            roomNameTextView.setText(booking.getRoom_name());
            startDateTextView.setText(booking.getStart_date());
            endDateTextView.setText(booking.getEnd_date());
            priceTextView.setText(String.valueOf(booking.getRoom_price()));
        }
    }
}

