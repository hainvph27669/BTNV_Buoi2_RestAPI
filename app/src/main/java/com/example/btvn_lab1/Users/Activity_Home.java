package com.example.btvn_lab1.Users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btvn_lab1.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Activity_Home extends AppCompatActivity {
    private Button btnAdd;
    private ListView userListView;
    private UserAdapter adapter;
    private ArrayList<User> users;
//private DatabaseReference databaseReference;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAdd = findViewById(R.id.btnAdd);
        userListView = findViewById(R.id.lvUser);



        users = new ArrayList<>();
        adapter = new UserAdapter(this, users);
        userListView.setAdapter(adapter);


        db= FirebaseFirestore.getInstance();

        addDummyDataToFirestore();

        // Lấy danh sách người dùng từ Firestore và cập nhật ListView
        loadUsersFromFirestore();








        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Home.this, Activity_Them_User.class);
                startActivity(intent);

            }
        });

    }

    private void loadUsersFromFirestore() {
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        users.clear();  // Xóa danh sách hiện tại
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            user.setMaUser(document.getId()); // Lưu ID của tài liệu
                            users.add(user);
                        }
                        adapter.notifyDataSetChanged(); // Cập nhật adapter sau khi có dữ liệu mới
                    } else {
                        Toast.makeText(this, "Lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addDummyDataToFirestore() {
            User user1 = new User("001", "Nguyen", "An", "1995", "Hanoi");
            User user2 = new User("002", "Tran", "Binh", "1990", "Hue");
            User user3 = new User("003", "Le", "Cuong", "1992", "Danang");

            // Thêm vào Firestore (bạn có thể dùng add() hoặc set())
        db.collection("users").document(user1.getMaUser()).set(user1)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Dữ liệu mẫu đã được thêm!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi thêm dữ liệu!", Toast.LENGTH_SHORT).show());

        db.collection("users").document(user2.getMaUser()).set(user2)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Dữ liệu mẫu đã được thêm!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi thêm dữ liệu!", Toast.LENGTH_SHORT).show());

        db.collection("users").document(user3.getMaUser()).set(user3)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Dữ liệu mẫu đã được thêm!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi khi thêm dữ liệu!", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại danh sách khi quay lại từ Activity sửa thông tin
        loadUsersFromFirestore();
    }
}