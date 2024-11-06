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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Dang_Ki extends AppCompatActivity {
        private FirebaseAuth mAuth;
        private Button btnDangKi;
        private EditText edtUserName, edtPassWord, edtPassWord2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ki);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //ánh xạ
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        edtPassWord2 = findViewById(R.id.edtPassWord2);
        btnDangKi = findViewById(R.id.btnDangKi2);


        mAuth= FirebaseAuth.getInstance();


        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUser = edtUserName.getText().toString();
                String sPassword = edtPassWord.getText().toString();
                String sPassword2 = edtPassWord2.getText().toString();


                if(sUser.isEmpty() || sPassword.isEmpty() || sPassword2.isEmpty()){
                    Toast.makeText(Activity_Dang_Ki.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                } else if(!sPassword.equals(sPassword2)){
                    Toast.makeText(Activity_Dang_Ki.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();

                }else {
                    dangkiFirebase(sUser, sPassword);

                }

            }
        });

    }

    private void dangkiFirebase (String user, String pass){
        mAuth.createUserWithEmailAndPassword(user , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    Toast.makeText(Activity_Dang_Ki.this, "Dang ky thanh cong: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Activity_Dang_Ki.this, Activity_DangNhap.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(Activity_Dang_Ki.this, "Authentication failed. " + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}