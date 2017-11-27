package com.company.shidoris.butler.utils;

import android.util.Log;

import com.company.shidoris.butler.model.Request;
import com.company.shidoris.butler.model.RequestsData;
import com.company.shidoris.butler.model.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Erik on 17/11/2017.
 */

public class DatabaseCUD {

    public static final String REQUESTSDATA = "requestsdata";
    public static final String CURRENT = "currentRequest";
    public static final String REQUESTIDS = "requestIds";

    /**
     * Add new note to the selected board.
     *
     * @param mDatabase Database reference to the database.
     * @param request   Request object that is going to be add to the request DB.
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
        setCurrent(mDatabase,request);
        mDatabase.child(REQUESTSDATA).setValue(requestMap);
    }

    /**
     * Add new note to the selected board.
     *
     * @param mDatabase Database reference to the database.
     * @param request   Request object that is going to be add to the .
     */
    public static void setCurrent(final DatabaseReference mDatabase, final Request request) {
        mDatabase.child("userId").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get BoardContent value
                        UserData userData = dataSnapshot.getValue(UserData.class);

                        if (userData == null || userData.getCurrentRequest()== null) {
                            // Note is null, error out
                            writeNewCurrent(mDatabase, request);

                        } else {
                            //Send the data to add the note ot the database.
                            writeNewCurrent(mDatabase, request);
                            Log.d("Current","you can only have one requestat the time");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getPost:onCancelled", databaseError.toException());

                    }
                });
    }

    private static void writeNewCurrent(DatabaseReference mDatabase, Request request) {

        mDatabase.child("userId").child(CURRENT).setValue(request.getId());
    }
    /**
     * Add new note to the selected board.
     *
     * @param mDatabase Database reference to the database.
     */
    /*public static void finishCurrent(final DatabaseReference mDatabase) {
        mDatabase.child("userId").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get BoardContent value
                        UserData userData = dataSnapshot.getValue(UserData.class);

                        if (userData == null || userData.getCurrentRequest() == null) {
                            // Note is null, error out
                            Log.e("Current", "No current request");

                        } else {
                            //Send the data to add the note ot the database.
                            String request = userData.getCurrentRequest();
                            //request.setStatus("Delivered");
                            mDatabase.child("userId").child(CURRENT).removeValue();

                            if (userData == null || userData.getRequestIds() == null) {
                                // Note is null, error out
                                writeFinishCurrent(mDatabase,request.getId(), new ArrayList<String>());
                            } else {

                                writeFinishCurrent(mDatabase,request.getId(), userData.getRequestIds());
                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getPost:onCancelled", databaseError.toException());

                    }
                });
    }*/


    private static void writeFinishCurrent(DatabaseReference mDatabase, String id, List<String> requestIds) {
        requestIds.add(id);
        mDatabase.child("userId").child(REQUESTIDS).setValue(requestIds);
    }


    /*public static void updateCurrent(final DatabaseReference mDatabase, final String status) {
        mDatabase.child("userId").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get BoardContent value
                        UserData userData = dataSnapshot.getValue(UserData.class);

                        if (userData == null || userData.getCurrentRequest()== null) {
                            // Note is null, error out
                            Log.e("Current", "No current request");
                        } else {

                            String request = userData.getCurrentRequest();
                            request.setStatus(status);
                            writeNewCurrent(mDatabase,request);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getPost:onCancelled", databaseError.toException());

                    }
                });
    }*/


}
