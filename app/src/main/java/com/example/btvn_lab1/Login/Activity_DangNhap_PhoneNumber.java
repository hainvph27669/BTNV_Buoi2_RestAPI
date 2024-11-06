package com.example.btvn_lab1.Login;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btvn_lab1.R;
import com.example.btvn_lab1.Users.Activity_Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Activity_DangNhap_PhoneNumber extends AppCompatActivity {
    private EditText edtSdt, edtOTP;
    private Button btnSendOTP, btnLogin_Phone;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap_phone_number);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần UI
        edtSdt = findViewById(R.id.edtSdt);
        edtOTP = findViewById(R.id.edtOTP);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        btnLogin_Phone = findViewById(R.id.btnLogin_Phone);

        mAuth = FirebaseAuth.getInstance();

        // Cài đặt các callback
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                // Tự động điền OTP nếu xác thực hoàn tất
                edtOTP.setText(credential.getSmsCode());
                verifyOTP(credential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Activity_DangNhap_PhoneNumber.this, "Xác thực thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.w(TAG, "onVerificationFailed", e);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                Toast.makeText(Activity_DangNhap_PhoneNumber.this, "OTP đã được gửi", Toast.LENGTH_SHORT).show();
            }
        };

        // Gửi OTP khi nhấn nút
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = edtSdt.getText().toString().trim();
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(Activity_DangNhap_PhoneNumber.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }
                getOTP(phoneNumber);
            }
        });

        // Xác nhận OTP khi nhấn nút
        btnLogin_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edtOTP.getText().toString().trim();
                if (code.isEmpty()) {
                    Toast.makeText(Activity_DangNhap_PhoneNumber.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                    return;
                }
                verifyOTP(code);
            }
        });
    }

    private void getOTP(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+84"+phoneNumber) // Số điện thoại với mã quốc gia
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyOTP(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Activity_DangNhap_PhoneNumber.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Activity_DangNhap_PhoneNumber.this, Activity_Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Activity_DangNhap_PhoneNumber.this, "Mã OTP không hợp lệ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_DangNhap_PhoneNumber.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
