package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPVerificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editTextOTP;
    private Button buttonVerifyOTP;
    private String verificationId;
    private String phoneNumber; // Thêm biến phoneNumber

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        editTextOTP = findViewById(R.id.editTextOTP);
        buttonVerifyOTP = findViewById(R.id.buttonVerifyOTP);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            verificationId = intent.getStringExtra("verificationId");
            phoneNumber = intent.getStringExtra("phoneNumber");
        }

        buttonVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = editTextOTP.getText().toString().trim();
                if (!otp.isEmpty()) {
                    verifyOTP(otp);
                } else {
                    Toast.makeText(OTPVerificationActivity.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verifyOTP(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Xác thực thành công, lấy thông tin người dùng từ AuthResult
                            FirebaseUser user = task.getResult().getUser();
                            // Sử dụng số điện thoại đã được xác thực
                            String verifiedPhoneNumber = phoneNumber;
                            // Thực hiện các hành động khác sau khi xác thực thành công
                        } else {
                            // Xác thực thất bại, xử lý lỗi nếu cần thiết
                            Toast.makeText(OTPVerificationActivity.this, "Xác thực thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

