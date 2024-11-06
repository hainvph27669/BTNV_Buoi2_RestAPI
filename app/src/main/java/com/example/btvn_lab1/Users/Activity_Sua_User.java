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

public class Activity_Sua_User extends AppCompatActivity {
    private EditText edtMaUser, edtHo, edtTen, edtNamSinh, edtQue;
    private Button btnSuaUser, btnBack;
    private FirebaseFirestore db;
    private String maUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sua_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ánh xạ
        edtMaUser = findViewById(R.id.edtMaUser);
        edtHo = findViewById(R.id.edtHo);
        edtTen = findViewById(R.id.edtTen);
        edtNamSinh = findViewById(R.id.edtNamsinh);
        edtQue = findViewById(R.id.edtQue);
        btnSuaUser = findViewById(R.id.btnSuaUser);
        btnBack = findViewById(R.id.btnBack);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Nhận mã người dùng từ Intent để lấy dữ
        maUser = getIntent().getStringExtra("maUser");


        if (maUser != null) {
            // Lấy thông tin người dùng từ Firestore
            db.collection("users").document(maUser).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Lấy dữ liệu từ Firestore và điền vào các EditText
                            String ho = documentSnapshot.getString("ho");
                            String ten = documentSnapshot.getString("ten");
                            String namSinh = documentSnapshot.getString("namSinh");
                            String que = documentSnapshot.getString("que");

                            // Điền dữ liệu vào các EditText
                            edtMaUser.setText(maUser);  // Mã User không thay đổi, không cho phép chỉnh sửa
                            edtHo.setText(ho);
                            edtTen.setText(ten);
                            edtNamSinh.setText(namSinh);
                            edtQue.setText(que);
                        } else {
                            Toast.makeText(this, "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi lấy dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }



        btnSuaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ho = edtHo.getText().toString().trim();
                String ten = edtTen.getText().toString().trim();
                String namSinh = edtNamSinh.getText().toString().trim();
                String que = edtQue.getText().toString().trim();

                // Kiểm tra các trường không rỗng
                if (ho.isEmpty() || ten.isEmpty() || namSinh.isEmpty() || que.isEmpty()) {
                    Toast.makeText(Activity_Sua_User.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cập nhật thông tin vào Firestore
                db.collection("users").document(maUser)  // Dùng mã người dùng làm ID tài liệu
                        .update("ho", ho, "ten", ten, "namSinh", namSinh, "que", que)  // Chỉ cập nhật các trường thay đổi
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(Activity_Sua_User.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Activity_Sua_User.this, Activity_Home.class);
                            startActivity(intent);  // Quay lại Activity_Home sau khi sửa
                            finish();  // Quay lại Activity trước
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Activity_Sua_User.this, "Cập nhật thất bại! Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Sua_User.this, Activity_Home.class);
                startActivity(intent);
            }
        });




    }
}