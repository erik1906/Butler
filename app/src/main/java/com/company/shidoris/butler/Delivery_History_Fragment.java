package com.company.shidoris.butler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.shidoris.butler.adapters.RecyclerViewDeliveryHistoryAdapter;
import com.company.shidoris.butler.model.Request;
import com.company.shidoris.butler.model.RequestsData;
import com.company.shidoris.butler.model.view.DeliveryRegister;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Delivery_History_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Delivery_History_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Delivery_History_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView deliveryHistory;
    private ArrayList<DeliveryRegister> registers;

    private DatabaseReference database;

    public Delivery_History_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Delivery_History_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Delivery_History_Fragment newInstance(String param1, String param2) {
        Delivery_History_Fragment fragment = new Delivery_History_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        registers = new ArrayList<>();
    }
    private static final String[] TABLE_HEADERS = { "Delivery", "Date", "Amount" };
    private static final String[][] DATA_TO_SHOW = { { "Delivery 1", "12/12/17", "$500" },{ "Delivery 2", "12/12/17", "$500" },
            { "Delivery 3", "08/12/17", "$200" }, { "Delivery 4", "11/12/17", "$1,500" },};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_delivery__history, container, false);


        registers.add(new DeliveryRegister("12/12/17","Accepted" , "$500"));
        registers.add(new DeliveryRegister("12/12/17", "Deliver", "$500"));
        registers.add(new DeliveryRegister("08/12/17", "Accepted", "$200"));

        RecyclerViewDeliveryHistoryAdapter adapter = new RecyclerViewDeliveryHistoryAdapter(registers);

        deliveryHistory = rootView.findViewById(R.id.delivery_history_list);
        deliveryHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        deliveryHistory.setAdapter(adapter);

        initDatabase();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void initDatabase(){
        database = FirebaseDatabase.getInstance().getReference();

        ValueEventListener reqListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                System.out.println("--------------------Hola ---------------------");

                RequestsData data = dataSnapshot.getValue(RequestsData.class);
                ArrayList<String> avaRequests = new ArrayList<>();
                ArrayList<String> accRequests = new ArrayList<>();
                Map<String, Request> map = data.getRequestsdata();

                refreshRecyclerView(map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database.addValueEventListener(reqListener);
    }

    public void refreshRecyclerView(Map<String, Request> items){

        registers.clear();

        for (Map.Entry<String, Request> entry : items.entrySet()){
            Request req = entry.getValue();
            registers.add(new DeliveryRegister(req.getDate(), req.getStatus(), "$1000"));
        }

        RecyclerViewDeliveryHistoryAdapter adapter = new RecyclerViewDeliveryHistoryAdapter(registers);
        deliveryHistory.setAdapter(adapter);
    }

}
