package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    Button btnback,btregister;
    EditText edname, edpass, edrepass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();

        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, Login.class);
                startActivity(intent);
            }
        });
        edname = findViewById(R.id.edname);
        edpass = findViewById(R.id.edpass);
        edrepass = findViewById(R.id.edrepass);

        edpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edrepass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        btregister = findViewById(R.id.btregister);
        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edname.getText().toString().trim().isEmpty() || edpass.getText().toString().trim().isEmpty()
                        || edrepass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(register.this, "Please input your username and password!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!edpass.getText().toString().equals(edrepass.getText().toString())) {
                    // Hiển thị thông báo lỗi nếu mật khẩu không khớp
                    Toast.makeText(register.this, "Passwords do not match!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                String email = edname.getText().toString();
                String pass = edpass.getText().toString();
                String repass = edrepass.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this,"Register successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(register.this, Login.class);
                            i.putExtra("username", edname.getText());
                            i.putExtra("password", edpass.getText());
                            i.putExtra("password", edrepass.getText());
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(register.this,"login unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

}