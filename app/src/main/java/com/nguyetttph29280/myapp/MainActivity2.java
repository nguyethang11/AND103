package com.nguyetttph29280.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {
TextView tvKQ;
FirebaseFirestore database;
Context context = this;
String strKQ = "";
ToDo toDo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvKQ = findViewById(R.id.tvKQ);
        database = FirebaseFirestore.getInstance(); // khởi tạo database
        //insert();
        //update();
        //select();
        delete();
    }
    void insert()
    {
        String id = UUID.randomUUID().toString();
        toDo = new ToDo(id, "title 11","content 11"); //tạo đối tượng mới để insert
        database.collection("TODO")//truy cập đến bảng dữ liệu
                .document(id) // truy cập đến dòng dữ liệu
                .set(toDo.convertToHashMap()) // đưa dữ liệu vào dòng
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) { // thành công
                        Toast.makeText(context,"insert thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() { // thất bại
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"insert thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    void update()
    {
        String id = "cb1413d6-6586-4059-9247-d74684b30a44"; //coppy id vào đây
        toDo = new ToDo(id, "title 11 update", "content 11 update"); // nd cần update
        database.collection("TODO")// truy cập đến bảng DL
                .document(id) // lấy id
                .update(toDo.convertToHashMap()) // thực hiện update
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context,"update thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"update thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //xóa
    void delete()
    {
        String id = "cb1413d6-6586-4059-9247-d74684b30a44";
        database.collection("TODO")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context,"xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    ArrayList<ToDo> select()
    {
        ArrayList<ToDo> list = new ArrayList<>();
        database.collection("TODO")
                .get() // lấy về DL
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                    strKQ = "";
                    for(QueryDocumentSnapshot doc : task.getResult())
                        {
                            ToDo t = doc.toObject(ToDo.class); // chuyển dữ liệu đọc đc sang dạng todo
                            list.add(t);
                            strKQ +="id: "+ t.getId()+"\n";
                            strKQ +="title: "+ t.getTitle()+"\n";
                            strKQ +="conetent: "+ t.getContent()+"\n";
                        }
                    tvKQ.setText(strKQ);
                        }
                        else {
                            Toast.makeText(context,"select thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return list;
    }

}