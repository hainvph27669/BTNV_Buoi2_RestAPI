package com.example.btvn_lab1.Users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btvn_lab1.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class Activity_Them_User extends AppCompatActivity {
    private EditText edtMaUser, edtHo, edtTen, edtNamSinh, edtQue;
    private Button btnSave, btnCancel;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtMaUser = findViewById(R.id.edtMaUser);
        edtHo = findViewById(R.id.edtHo);
        edtTen = findViewById(R.id.edtTen);
        edtNamSinh = findViewById(R.id.edtNamSinh);
        edtQue = findViewById(R.id.edtQueQuan);
        btnSave = findViewById(R.id.btnAddUser);
        btnCancel= findViewById(R.id.btnBack);


        db = FirebaseFirestore.getInstance();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserToFirestore();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Them_User.this, Activity_Home.class);
                startActivity(intent);
            }
        });


    }

    private void saveUserToFirestore() {
        String maUser = edtMaUser.getText().toString();
        String ho = edtHo.getText().toString();
        String ten = edtTen.getText().toString();
        String namSinh = edtNamSinh.getText().toString();
        String que = edtQue.getText().toString();

        User user = new User(maUser, ho, ten, namSinh, que);

        db.collection("users")
                .document(maUser)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Đã thêm người dùng", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Activity_Them_User.this, Activity_Home.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show());
    }
}