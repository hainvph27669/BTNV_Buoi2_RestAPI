package com.example.btvn_lab1.Users;

import android.app.AlertDialog;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btvn_lab1.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> users;
    private FirebaseFirestore db;


    // Constructor
    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);  // Gọi constructor của ArrayAdapter
        this.context = context;
        this.users = users;
        db = FirebaseFirestore.getInstance();
    }

    // Phương thức getView để tạo mỗi item trong ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Nếu convertView là null, tạo mới layout cho item
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        }

        // Lấy thông tin người dùng tại vị trí hiện tại trong danh sách
        User user = getItem(position);

        // Tìm các view trong layout item
        TextView txtMaUser = convertView.findViewById(R.id.txtMaUser);
        TextView txtHo = convertView.findViewById(R.id.txtHo);
        TextView txtTen = convertView.findViewById(R.id.txtTen);
        TextView txtNamSinh = convertView.findViewById(R.id.txtNamSinh);
        TextView txtQue = convertView.findViewById(R.id.txtQue);
        ImageView imgDelete = convertView.findViewById(R.id.imgDelete);
        ImageView imgEdit = convertView.findViewById(R.id.imgEdit);

        // Gán dữ liệu cho các TextView
        txtMaUser.setText(user.getMaUser());
        txtHo.setText("Họ: " + user.getHo());
        txtTen.setText("Tên: " + user.getTen());
        txtNamSinh.setText("Năm sinh: " + user.getNamSinh());
        txtQue.setText("Quê quán: " + user.getQue());

        // Xử lý sự kiện click vào nút xóa và chỉnh sửa
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hiển thị một dialog để xác nhận xóa
                new AlertDialog.Builder(context)
                        .setTitle("Xóa người dùng")
                        .setMessage("Bạn có chắc chắn muốn xóa người dùng này?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            // Xóa người dùng khỏi Firestore
                            db.collection("users")
                                    .document(user.getMaUser())
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        // Sau khi xóa thành công, xóa người dùng khỏi danh sách và cập nhật lại
                                        users.remove(position);
                                        notifyDataSetChanged(); // Cập nhật lại ListView
                                    })
                                    .addOnFailureListener(e -> {
                                        // Thông báo lỗi khi xóa thất bại
                                        Toast.makeText(context, "Lỗi khi xóa người dùng!", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        imgEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, Activity_Sua_User.class);
            intent.putExtra("maUser", user.getMaUser());  // Truyền đối tượng người dùng
            context.startActivity(intent);


        });

        return convertView;
    }
}
