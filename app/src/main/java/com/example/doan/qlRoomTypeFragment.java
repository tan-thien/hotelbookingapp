package com.example.doan;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link qlRoomTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class qlRoomTypeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public qlRoomTypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment qlRoomTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static qlRoomTypeFragment newInstance(String param1, String param2) {
        qlRoomTypeFragment fragment = new qlRoomTypeFragment();
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

    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<roomtypedata> dataList;
    roomtypeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_ql_room_type, container, false);

        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerView);

        //searchView.clearFocus();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.item);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new roomtypeAdapter(dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("RoomTypes");
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    roomtypedata dataService = itemSnapshot.getValue(roomtypedata.class);

                    dataService.setKey(itemSnapshot.getKey());
                    dataList.add(dataService);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = null;
                newFragment = new uploadRoomtypeFragment();
                loadFragment(newFragment);
            }
        });

            return view;
        }
    private void loadFragment(Fragment newFragment) {
        // Kiểm tra xem newFragment có tồn tại không
        if (newFragment != null) {
            // Tạo một FragmentTransaction
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            // Thay đổi Fragment hiện tại trong container bằng newFragment
            transaction.replace(R.id.frame_fragment, newFragment);
            // Thêm transaction vào ngăn xếp
            transaction.addToBackStack(null);
            // Chấp nhận các thay đổi
            transaction.commit();
        }
    }


}
