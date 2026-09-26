package com.example.doan;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
 * Use the {@link qlserviceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class qlserviceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public qlserviceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment qlserviceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static qlserviceFragment newInstance(String param1, String param2) {
        qlserviceFragment fragment = new qlserviceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    // bắt đầu ----------------------------------------------
    //-------------------------------------------------
    //------------------------------------------------
    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<DataService> dataList;
    AdapterService adapter;
    SearchView searchView;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qlservice, container, false);

        // Tìm và gán giá trị cho FloatingActionButton từ View
        fab = view.findViewById(R.id.fabService);
        recyclerView = view.findViewById(R.id.recyclerViewSer);

        searchView = view.findViewById(R.id.searchService);
        searchView.clearFocus();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.item);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new AdapterService(getContext(), dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Hotel Service");
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataService dataService = itemSnapshot.getValue(DataService.class);

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


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = null;
                newFragment = new uploadServiceFragment();
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
    public void searchList(String text){
        ArrayList<DataService> searchList = new ArrayList<>();
        for (DataService dataService: dataList){
            if (dataService.getNameSer().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataService);
            }
        }
        adapter.searchDataList(searchList);
    }
}