package com.company.shidoris.butler.utils;

import android.util.Log;

import com.company.shidoris.butler.model.Request;
import com.company.shidoris.butler.model.RequestsData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Erik on 17/11/2017.
 */

public class DatabaseCUD {

    public static final String REQUESTSDATA = "requestsdata";
    /**
     * Add new note to the selected board.
     *
     * @param mDatabase Database reference to the database.
     * @param request   Request object that is going to be add to the notes.
     */
    public static void newRequest(final DatabaseReference mDatabase, final Request request) {
        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get BoardContent value
                        RequestsData requestsData = dataSnapshot.getValue(RequestsData.class);

                        if (requestsData == null) {
                            // Note is null, error out
                            writeNewRequest(mDatabase, request, null );
                        } else {
                            //Send the data to add the note ot the database.
                            writeNewRequest(mDatabase, request, requestsData.getRequestsdata());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getPost:onCancelled", databaseError.toException());

                    }
                });
    }

    private static void writeNewRequest(DatabaseReference mDatabase, Request request, Map<String, Request> requestMap) {
        String key = mDatabase.child(REQUESTSDATA).push().getKey();
        if (requestMap == null) {
            requestMap = new HashMap();
            request.setId(key);
            requestMap.put(key, request);
        } else {
            request.setId(key);
            requestMap.put(key, request);
        }
        mDatabase.child(REQUESTSDATA).setValue(requestMap);
    }

}
