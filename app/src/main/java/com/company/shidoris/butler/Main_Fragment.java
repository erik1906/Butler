package com.company.shidoris.butler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.shidoris.butler.model.Request;
import com.company.shidoris.butler.model.RequestsData;
import com.company.shidoris.butler.model.UserData;
import com.company.shidoris.butler.utils.Constants;
import com.company.shidoris.butler.utils.DatabaseCUD;
import com.company.shidoris.butler.utils.DummyData;
import com.company.shidoris.butler.utils.FetchAddressIntentService;
import com.company.shidoris.butler.utils.PermissionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Main_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Main_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Main_Fragment extends Fragment implements OnMapReadyCallback
{

GoogleMap mGoogleMap;
MapView mMapView;
    FloatingActionButton btn_cancel,btn_add;

    private DatabaseReference mDatabase;



             // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Main_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Main_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Main_Fragment newInstance(String param1, String param2) {
        Main_Fragment fragment = new Main_Fragment();
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View rootView= inflater.inflate(R.layout.fragment_main, container, false);



        /*
        * Button ADD
        * */

        //mDatabase = FirebaseDatabase.getInstance().getReference();
        //DatabaseCUD.newRequest(mDatabase, new Request("Deliver","12/12/2013", "1234341234","124123423","12342342","2423412344", DummyData.getProductsDummy()));
        btn_cancel = (FloatingActionButton)rootView.findViewById(R.id.btn_delete);
        btn_add = (FloatingActionButton)rootView.findViewById(R.id.btn_add);




        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("userId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.getValue(UserData.class);

                if (userData == null || userData.getCurrentRequest()== null) {
                    // Note is null, error out
                    showInputDialog();
                    btn_cancel.setVisibility(View.GONE);
                    btn_add.setVisibility(View.VISIBLE);
                    Log.d("Current","you can only have one requestat the time");
                    btn_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showInputDialog();
                        }
                    });


                } else {
                    mapView(rootView);
                    btn_cancel.setVisibility(View.VISIBLE);
                    //cancelDialog();
                    //Send the data to add the note ot the database.

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getPost:onCancelled", databaseError.toException());
            }
        });
        return rootView;
    }

private void mapView(View view){

        mMapView =(MapView)view.findViewById(R.id.map);
        if(mMapView!=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //showInputDialog();
                    cancelDialog();
                }
            });


        }

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

    @Override
    public void onMapReady(GoogleMap map) {


MapsInitializer.initialize(getContext());

mGoogleMap=map;
    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    map.addMarker(new MarkerOptions().position(new LatLng(19.2680651,-99.706783)).title("ITESM"));
    CameraPosition position = CameraPosition.builder().target(new LatLng(19.2680651,-99.706783)).zoom(16).bearing(0).tilt(45).build();
    map.moveCamera(CameraUpdateFactory.newCameraPosition(position));

    }





    private void showInputDialog() {


    final ArrayList<String >listaProductos = new ArrayList<String>();

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.alert_dialog_delivery, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Delivery Request");
        ListView lvproducto = (ListView)promptView.findViewById(R.id.lv_productos);
        final EditText editText = (EditText) promptView.findViewById(R.id.txtProd);
        Button btn_addP =(Button)promptView.findViewById(R.id.btn_agregar);


       final ArrayAdapter<String>adapter= new ArrayAdapter<String>(promptView.getContext(),android.R.layout.simple_expandable_list_item_1,listaProductos);
       lvproducto.setAdapter(adapter);

        btn_addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String prod= editText.getText().toString();
               // Log.d("HOLALALALALALA",prod);
                listaProductos.add(prod);
                adapter.notifyDataSetChanged();
            }
        });

        // final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // resultText.setText("Hello, " + editText.getText());
                        //mDatabase = FirebaseDatabase.getInstance().getReference();
                        //DatabaseCUD.newRequest(mDatabase, new Request("Deliver","12/12/2013", "1234341234","124123423","12342342","2423412344", DummyData.getProductsDummy()));
                        //Intent intent = new Intent(getContext(), MapsActivity.class);
                        //startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private void cancelDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
       // View promptView = layoutInflater.inflate(R.layout.alert_dialog_delivery, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        //alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Seguro que deseas cancelar el pedido?");
        // final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // resultText.setText("Hello, " + editText.getText());
                        //mDatabase = FirebaseDatabase.getInstance().getReference();
                        //DatabaseCUD.newRequest(mDatabase, new Request("Deliver","12/12/2013", "1234341234","124123423","12342342","2423412344", DummyData.getProductsDummy()));
                       // Intent intent = new Intent(getContext(), MapsActivity.class);
                        //startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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


         }
