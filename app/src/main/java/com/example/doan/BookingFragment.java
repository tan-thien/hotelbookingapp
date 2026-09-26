package com.example.doan;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingFragment newInstance(String param1, String param2) {
        BookingFragment fragment = new BookingFragment();
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
    TextView edt1, edt2;
    Button btnthanhtoan;
    ImageButton imageAvatar;
    TextView tvname,tvgia,tvtien;
    EditText edtvoucher;
    DatabaseReference mDatabase;
    String imageUrl = "";
    String key = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        // Ánh xạ TextView
        edt1 = view.findViewById(R.id.edtdate1);
// Ánh xạ TextView
        edt2 = view.findViewById(R.id.edtdate2);

        imageAvatar = view.findViewById(R.id.imageAvatar);
        tvname = view.findViewById(R.id.tvName);
        tvgia = view.findViewById(R.id.tvGia);
        tvtien = view.findViewById(R.id.tvtien);
        edtvoucher = view.findViewById(R.id.edtvoucher);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getArguments();
        if (bundle != null){
            tvname.setText(bundle.getString("Title"));
            Double price = bundle.getDouble("Price");
            String priceString = String.format(Locale.getDefault(),"%.3f", price);
            tvgia.setText(priceString);
            tvtien.setText(priceString);

            //detailPrice.setText(bundle.getString("Price"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");

            Glide.with(requireContext()).load(bundle.getString("Image")).into(imageAvatar);
        }


        // Thiết lập sự kiện click cho Button để hiển thị DatePickerDialog
        Button datePickerButton = view.findViewById(R.id.btncalendar1);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog1();
            }
        });
        // Thiết lập sự kiện click cho Button để hiển thị DatePickerDialog
        Button dateButton = view.findViewById(R.id.btncalendar2);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnthanhtoan = view.findViewById(R.id.btnthanhtoan);
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt1.getText().toString().trim().isEmpty() ||edt2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please input ", Toast.LENGTH_SHORT).show();

                }
                else {

                    // Lấy thông tin đặt phòng
                    String date1 = edt1.getText().toString();
                    String date2 = edt2.getText().toString();
                    // Lưu thông tin đặt phòng lên Firebase
                    saveBookingInfo(date1, date2);
                    Fragment newFragment = new ThanhToanFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame_fragment, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }



            }
        });

        return view;
    }
    private void saveBookingInfo(String date1, String date2) {
        // Lấy thông tin phòng từ bundle hoặc các trường dữ liệu khác
        Bundle bundle = getArguments();
        String roomName = bundle.getString("Title");
        Double roomPrice = bundle.getDouble("Price");

        // Tạo một object chứa thông tin đặt phòng
        HashMap<String, Object> bookingInfo = new HashMap<>();
        bookingInfo.put("start_date", date1);
        bookingInfo.put("end_date", date2);
        bookingInfo.put("room_name", roomName);
        bookingInfo.put("room_price", roomPrice);

        // Lưu object này lên Firebase
        mDatabase.child("bookings").push().setValue(bookingInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Lưu dữ liệu thành công
                        Toast.makeText(getContext(), "Booking successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Lưu dữ liệu thất bại
                        Toast.makeText(getContext(), "Booking failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Hàm để hiển thị DatePickerDialog
    private void showDatePickerDialog1() {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Xử lý khi người dùng chọn một ngày
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        edt1.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }
    private void showDatePickerDialog() {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Xử lý khi người dùng chọn một ngày
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        edt2.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }


}