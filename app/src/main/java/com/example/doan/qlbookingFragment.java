package com.example.doan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link qlbookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class qlbookingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public qlbookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment qlbookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static qlbookingFragment newInstance(String param1, String param2) {
        qlbookingFragment fragment = new qlbookingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    DatabaseReference mDatabase;
    RecyclerView mRecyclerView;
    BookingHistoryAdapter mAdapter;
    List<Booking> mBookingList;
    ValueEventListener eventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_qlbooking, container, false);
        // Khởi tạo Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("bookings");

        // Khởi tạo RecyclerView và Adapter
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBookingList = new ArrayList<>();


        mAdapter = new BookingHistoryAdapter(mBookingList);
        mRecyclerView.setAdapter(mAdapter);

        //mDatabase.v

//        // Lắng nghe sự kiện thay đổi dữ liệu trên Firebase Database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBookingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy thông tin đặt phòng từ dataSnapshot và thêm vào danh sách
                    Booking booking = snapshot.getValue(Booking.class);
                    Log.i("ABCDE", booking.toString());
                    mBookingList.add(booking);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load booking history: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}