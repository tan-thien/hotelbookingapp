package com.example.doan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongKeDoanhThuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongKeDoanhThuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongKeDoanhThuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongKeDoanhThuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongKeDoanhThuFragment newInstance(String param1, String param2) {
        ThongKeDoanhThuFragment fragment = new ThongKeDoanhThuFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke_doanh_thu, container, false);

        TextView textViewTotalRevenue = view.findViewById(R.id.textViewTotalRevenueValue);
        Button buttonRefresh = view.findViewById(R.id.buttonRefresh);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("bookings");

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        double totalRevenue = 0.0;

                        for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()) {
                            // Lấy giá và số lượng đã bán từ mỗi mục booking
                            double price = bookingSnapshot.child("room_price").getValue(Double.class);


                            // Tính tổng doanh thu từ mỗi mục booking
                            totalRevenue += price;
                        }

                        // Hiển thị tổng doanh thu lên TextView
                        textViewTotalRevenue.setText("Tổng doanh thu: " + totalRevenue);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý khi truy vấn bị hủy
                        Toast.makeText(getContext(), "Lỗi khi truy vấn dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }



}