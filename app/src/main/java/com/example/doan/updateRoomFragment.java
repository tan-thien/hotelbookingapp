package com.example.doan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link updateRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class updateRoomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public updateRoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment updateRoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static updateRoomFragment newInstance(String param1, String param2) {
        updateRoomFragment fragment = new updateRoomFragment();
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
//---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    ImageView updateImage;
    Button updateButton;
    EditText updateDesc, updateTitle, updatePrice, updateTypeRoom;
    String title, desc, typeroom;
    Double price;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_room, container, false);

        updateButton = view.findViewById(R.id.updateButton);
        updateDesc = view.findViewById(R.id.updateDescRoom);
        updateImage = view.findViewById(R.id.updateImage);
        updatePrice = view.findViewById(R.id.updatePriceRoom);
        updateTitle = view.findViewById(R.id.updateNameRoom);
        updateTypeRoom=view.findViewById(R.id.updateTypeRoom);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);
                        } else {
                            Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getArguments();
        if (bundle != null){
            Glide.with(getContext()).load(bundle.getString("Image")).into(updateImage);
            updateTitle.setText(bundle.getString("Title"));
            //updateTypeRoom.setText(bundle.getString("TypeRoom"));
            updateDesc.setText(bundle.getString("Description"));
//            Double price1 = bundle.getDouble("Price");
//            String priceString = String.format(Locale.getDefault(),"%.3f", price1);
//            updatePrice.setText(priceString);
            updateTypeRoom.setText(bundle.getString("TypeRoom"));
            updatePrice.setText(bundle.getString("Price"));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("Image");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials").child(key);
        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();

            }
        });
        return view;
    }
    public void saveData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.item);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void updateData(){
        title = updateTitle.getText().toString().trim();
        desc = updateDesc.getText().toString().trim();
//        String priceString = updatePrice.getText().toString();
//        price = Double.parseDouble(priceString);
        Double price = Double.valueOf(updatePrice.getText().toString());
        typeroom = updateTypeRoom.getText().toString();
        DataClass dataClass = new DataClass(title, desc, price, typeroom, imageUrl);
        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                    reference.delete();
                    Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    //chuyển fragment này sang qlroomFragment
                    Fragment qlroomFragment = new qlroomFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Replace the current fragment with qlroomFragment
                    fragmentTransaction.replace(R.id.frame_fragment, qlroomFragment);
                    fragmentTransaction.addToBackStack(null); // Optional: Add this transaction to the back stack
                    fragmentTransaction.commit();

                    getParentFragmentManager().popBackStack();

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