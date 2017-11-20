package com.company.shidoris.butler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.shidoris.butler.model.Request;
import com.company.shidoris.butler.utils.DatabaseCUD;
import com.company.shidoris.butler.utils.DummyData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestDatabaseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);

//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        DatabaseCUD.newRequest(mDatabase, new Request("Deliver","12/12/2013", "1234341234","124123423","12342342","2423412344", DummyData.getProductsDummy()));
//        DatabaseCUD.finishCurrent(mDatabase);
//        DatabaseCUD.updateCurrent(mDatabase,"Going");

    }
}
