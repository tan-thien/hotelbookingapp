package com.example.doan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link uploadRoomtypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class uploadRoomtypeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public uploadRoomtypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment uploadRoomtypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static uploadRoomtypeFragment newInstance(String param1, String param2) {
        uploadRoomtypeFragment fragment = new uploadRoomtypeFragment();
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


    Button saveButton;
    EditText uploadName, uploadDesc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_upload_roomtype, container, false);


        uploadName = view.findViewById(R.id.uploadNameroomtype);
        uploadDesc = view.findViewById(R.id.uploadDescroomtype);

        saveButton = view.findViewById(R.id.saveButtonRT);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        return view;
    }

    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("RoomTypes");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.item);
        AlertDialog dialog = builder.create();
        dialog.show();
        uploadData();
        dialog.dismiss();
    }
    public void uploadData(){
        String title = uploadName.getText().toString();
        String desc = uploadDesc.getText().toString();

        roomtypedata dataService = new roomtypedata(title,desc);
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("RoomTypes").child(currentDate)
                .setValue(dataService).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

                            FragmentManager fragmentManager = getParentFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.frame_fragment, new qlRoomTypeFragment());
                            transaction.commit();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}