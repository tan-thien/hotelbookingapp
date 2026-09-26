package com.example.doan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detailRCusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detailRCusFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public detailRCusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment detailRCusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static detailRCusFragment newInstance(String param1, String param2) {
        detailRCusFragment fragment = new detailRCusFragment();
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
    TextView detailDesc, detailTitle, detailPrice,detailTypeRoom;
    ImageView detailImage;
    Button btnBooking;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_r_cus, container, false);
        // Inflate the layout for this fragment
        detailDesc = view.findViewById(R.id.detailDesc);
        detailImage = view.findViewById(R.id.detailImage);
        detailTitle = view.findViewById(R.id.detailTitle);
        detailPrice = view.findViewById(R.id.detailPrice);
        //detailTypeRoom = view.findViewById(R.id.detailTypeRoom);

        Bundle bundle = getArguments();
        if (bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            Double price = bundle.getDouble("Price");
            String priceString = String.format(Locale.getDefault(),"%.3f", price);
            detailPrice.setText(priceString);
            //detailPrice.setText(bundle.getString("Price"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");

            Glide.with(requireContext()).load(bundle.getString("Image")).into(detailImage);
        }
        btnBooking = view.findViewById(R.id.btnbooking);
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingFragment bookingFragment = new BookingFragment(); // Thay thế bằng fragment bạn muốn chuyển đến
                bookingFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_fragment, bookingFragment); // frame_fragment là id của container chứa fragment trong layout của bạn
                transaction.addToBackStack(null);
                transaction.commit();

                bundle.putString("Key", key); // Truyền key sang BookingFragment
                bundle.putString("Image", imageUrl); // Truyền imageUrl sang BookingFragment
                // Gắn Bundle vào Fragment mới
                bundle.putString("Title", detailTitle.getText().toString()); // Truyền title sang BookingFragment
                bundle.putDouble("Price", Double.parseDouble(detailPrice.getText().toString()));



            }
        });

        return view;
    }
}