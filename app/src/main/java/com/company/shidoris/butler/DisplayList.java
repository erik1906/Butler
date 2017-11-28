package com.company.shidoris.butler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.company.shidoris.butler.model.Product;
import com.company.shidoris.butler.model.Request;
import com.company.shidoris.butler.model.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DisplayList extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView lv;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        lv = findViewById(R.id.lvProduct);
        id = getIntent().getStringExtra("id");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("requestsdata").child(id).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Request request = dataSnapshot.getValue(Request.class);

                        request.getProductList();
                        ArrayList<String> list = new ArrayList<>();
                        for (Product s: request.getProductList()){
                            list.add(s.getName());
                        }
                        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,list);
                        lv.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
