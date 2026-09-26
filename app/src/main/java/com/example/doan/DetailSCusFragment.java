package com.example.doan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailSCusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailSCusFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailSCusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailSCusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailSCusFragment newInstance(String param1, String param2) {
        DetailSCusFragment fragment = new DetailSCusFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_s_cus, container, false);
        // Inflate the layout for this fragment
        detailDesc = view.findViewById(R.id.detailDesc);
        detailImage = view.findViewById(R.id.detailImage);
        detailTitle = view.findViewById(R.id.detailTitle);
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


        return  view;
    }
}