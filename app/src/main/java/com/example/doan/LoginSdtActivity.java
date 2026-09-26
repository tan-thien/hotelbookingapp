package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.core.Tag;

import java.util.concurrent.TimeUnit;

public class LoginSdtActivity extends AppCompatActivity {

    private EditText editTextPhoneNumber;
    private Button buttonVerifyPhoneNumber;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sdt);

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonVerifyPhoneNumber = findViewById(R.id.buttonVerifyPhoneNumber);

        buttonVerifyPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                if (!phoneNumber.isEmpty()) {
                    sendVerificationCode(phoneNumber);
                } else {
                    Toast.makeText(LoginSdtActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Khởi tạo mCallbacks để theo dõi trạng thái của quá trình xác thực
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // Xác thực số điện thoại tự động thành công, không cần xử lý ở đây
                Log.d(TAG, "onVerificationCompleted: ");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // Xác thực số điện thoại thất bại, hiển thị thông báo lỗi
                Toast.makeText(LoginSdtActivity.this, "Xác thực số điện thoại thất bại", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onVerificationFailed: ", e);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                // Mã OTP đã được gửi đến số điện thoại, tiến hành chuyển sang OTPVerificationActivity
                Intent otpIntent = new Intent(LoginSdtActivity.this, OTPVerificationActivity.class);
                otpIntent.putExtra("verificationId", verificationId);
                otpIntent.putExtra("phoneNumber", editTextPhoneNumber.getText().toString().trim());
                startActivity(otpIntent);
            }
        };
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60L,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }
    private static final String TAG = "LoginSdtActivity";

}
