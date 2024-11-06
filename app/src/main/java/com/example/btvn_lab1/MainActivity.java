package com.example.btvn_lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btvn_lab1.Login.Activity_DangNhap;
import com.example.btvn_lab1.Login.Activity_DangNhap_PhoneNumber;
import com.example.btvn_lab1.Login.Activity_Dang_Ki;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button btnPhone, btnEmail, btnDangKi, btnDangNhap;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ánh xạ các button
        btnPhone = findViewById(R.id.btnLoginPhone);
        btnEmail = findViewById(R.id.btnLoginEmail);
        btnDangKi = findViewById(R.id.btnDangKi);
        btnDangNhap = findViewById(R.id.btnDangNhap);



        mAuth= FirebaseAuth.getInstance();
        String email = "haiviptb@fpt.edu.vn";
        String pass = "Haiviptb@123";

       /* mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    Toast.makeText(MainActivity.this, "Dang nhap thanh cong: " + user.getEmail(),
                            Toast.LENGTH_SHORT).show();
                }else{
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Dang nhap loi: " + task.getException(),
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

*/




        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_DangNhap_PhoneNumber.class);
                startActivity(intent);

            }
        });

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_Dang_Ki.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_DangNhap.class);
                startActivity(intent);
            }
        });
    }
}