package com.example.doan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Use the {@link DetailServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailServiceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailServiceFragment newInstance(String param1, String param2) {
        DetailServiceFragment fragment = new DetailServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //----------------------------------------------------------------------
    //----------------------------------------------------------------------
    //----------------------------------------------------------------------
    //----------------------------------------------------------------------
    TextView detailDesc, detailTitle, detailPrice,detailTypeRoom;
    ImageView detailImage;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_service, container, false);
        // Inflate the layout for this fragment
        detailDesc = view.findViewById(R.id.detailDesc);
        detailImage = view.findViewById(R.id.detailImage);
        detailTitle = view.findViewById(R.id.detailTitle);
        deleteButton = view.findViewById(R.id.deleteButton);
        editButton = view.findViewById(R.id.editButton);
        detailPrice = view.findViewById(R.id.detailPrice);
        //detailTypeRoom = view.findViewById(R.id.detailTypeRoom);

        Bundle bundle = getArguments();
        if (bundle != null){
            detailDesc.setText(bundle.getString("Description1"));
            detailTitle.setText(bundle.getString("Title1"));

            Double price = bundle.getDouble("Price1");
            String priceString = String.format(Locale.getDefault(),"%.3f", price);
            detailPrice.setText(priceString);

            key = bundle.getString("Key1");
            imageUrl = bundle.getString("Image1");

            Glide.with(requireContext()).load(bundle.getString("Image1")).into(detailImage);
        }


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Hotel Service");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        Fragment newFragment = new qlserviceFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.frame_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                });
            }
        });
//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Tạo một instance mới của updateRoomFragment
//                updateRoomFragment fragment = new updateRoomFragment();
//
//                // Tạo một bundle để chuyển dữ liệu
//                Bundle bundle = new Bundle();
//                bundle.putString("Title", detailTitle.getText().toString());
//                bundle.putString("Description", detailDesc.getText().toString());
//                bundle.putString("Price",detailPrice.getText().toString());
//                bundle.putString("Image", imageUrl);
//                bundle.putString("Key", key);
//                fragment.setArguments(bundle);
//
//                // Thay thế fragment hiện tại bằng updateRoomFragment
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.frame_fragment, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });

        return view;
    }
}