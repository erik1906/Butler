package com.company.shidoris.butler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import com.company.shidoris.butler.apis.APIMaps;
import com.company.shidoris.butler.apis.MapsInterface;
import com.company.shidoris.butler.model.MapsDirection.DirectionRes;
import com.company.shidoris.butler.model.MapsDirection.Routes;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;
import static com.google.android.gms.maps.model.JointType.ROUND;


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

    private GoogleMap mMap;

    private MapView mMapView;
    private MapsInterface mapsInterface;


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
        mapsInterface = APIMaps.getDirectionApi().create(MapsInterface.class);
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("requestsdata");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mapView(rootView);

                    Request data= dataSnapshot.getValue(Request.class);

                    String datas= data.getStatus();
                    Toast.makeText(getContext(), datas,
                            Toast.LENGTH_SHORT).show();

                }
                else
                {
                    showInputDialog();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
            FloatingActionButton btn_cancel = (FloatingActionButton)view.findViewById(R.id.btn_delete);
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Holaasadasa");
                    showInputDialog();
                    //cancelDialog();
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


        final ArrayAdapter<String >adapter= new ArrayAdapter<String>(promptView.getContext(),android.R.layout.simple_expandable_list_item_1,listaProductos);
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
                        Intent intent = new Intent(getContext(), MapsActivity.class);
                        intent.putExtra("array",listaProductos);
                        startActivity(intent);
                        dialog.dismiss();
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
    private void getPoints(final String fromLat, final String fromLong, final String toLat, final String toLong, final String butlerLat, final String butlerLong) {
        Log.d("update",butlerLat+butlerLong+toLat+toLong+"via:"+fromLat+fromLong);
        Call<DirectionRes> mapsCall = mapsInterface.getDirectionPlace(butlerLat+","+butlerLong,toLat+","+toLong,"via:"+fromLat+","+fromLong, "AIzaSyC5VRkgiAoBNPijqz73FH4Glp12UvhqPX8");
        mapsCall.enqueue(new Callback<DirectionRes>() {
            @Override
            public void onResponse(Call<DirectionRes> call, Response<DirectionRes> response) {
                final Routes[] res = response.body().getRoutes();
                LatLng to=new LatLng(Double.valueOf(toLat),Double.valueOf(toLong));
                LatLng from=new LatLng(Double.valueOf(fromLat),Double.valueOf(fromLong));
                LatLng butler=new LatLng(Double.valueOf(butlerLat),Double.valueOf(butlerLong));
                updateUI(res[0].getOverviewPolyline().getPoints(),to,from,butler);

                Routes routes[] = response.body().getRoutes();
                String duration = ""+ routes[0].getLegs()[0].getDuration().getText();
                Log.i("Duration", duration);
            }

            @Override
            public void onFailure(Call<DirectionRes> call, Throwable t) {

            }
        });
    }

    private void updateUI(String points, LatLng to, LatLng from, LatLng butle) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(to)      // Sets the center of the map to location user
                .zoom(14)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().draggable(false).position(to).title("To here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.addMarker(new MarkerOptions().draggable(false).position(from).title("From here"));
        mMap.addMarker(new MarkerOptions().draggable(false).position(butle).title("Butler"));
        List<LatLng> decodedPath = PolyUtil.decode(points);
        mMap.addPolyline(new PolylineOptions().width(7).jointType(ROUND).color(Color.RED).addAll(decodedPath));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mDatabase.child("userId").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get BoardContent value
                        UserData userData = dataSnapshot.getValue(UserData.class);

                        if (userData == null || userData.getCurrentRequest()== null) {
                            // Note is null, error out

                        } else {
                            String requestId = userData.getCurrentRequest();
                            mDatabase.child("requestsdata").child(requestId).addValueEventListener(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // Get BoardContent value
                                            Request request = dataSnapshot.getValue(Request.class);

                                            if(request.getStatus().equals("Accepted")) {

                                                getPoints(request.getPickupLat(),
                                                        request.getPickupLong(),
                                                        request.getDeliverLat(),
                                                        request.getDeliverLong(),
                                                        request.getButlerLat(),
                                                        request.getButlerLong());
                                            }else if(request.getStatus().equals("buy")) {
                                                /*getPoints(request.getPickupLat(),
                                                        request.getPickupLong(),
                                                        request.getDeliverLat(),
                                                        request.getDeliverLong());*/
                                            }else{

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.w(TAG, "getPost:onCancelled", databaseError.toException());

                                        }
                                    });

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getPost:onCancelled", databaseError.toException());

                    }
                });

    }

}
