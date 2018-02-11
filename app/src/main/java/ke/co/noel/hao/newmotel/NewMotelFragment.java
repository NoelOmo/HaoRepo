package ke.co.noel.hao.newmotel;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import ke.co.noel.hao.R;
import ke.co.noel.hao.models.AmenitiesModel;
import noel.co.ke.toaster.Toaster;

import static android.app.Activity.RESULT_OK;

/**
 * Created by HP on 11/5/2017.
 */

public class NewMotelFragment extends android.support.v4.app.Fragment implements android.location.LocationListener {

    private static final String ARG_SECTION_NUMBER = "section_number";


    private int PICK_IMAGE_REQUEST_ONE = 1;
    private int PICK_IMAGE_REQUEST_TWO = 2;
    private int PICK_IMAGE_REQUEST_THREE = 3;
    private int PICK_IMAGE_REQUEST_FOUR = 4;
    ImageView imgOne, imgTwo, imgThree, imgFour;
    CardView holderOne, holderTwo, holderThree, holderFour;
    Uri uri1, uri2, uri3, uri4;
    RecyclerView amenitiesRV;

    ArrayList<AmenitiesModel> amenitiesList = new ArrayList<>();
    AmenitiesModel singleAmenity;
    AmenitiesAdapter amenitiesAdapter;

    TextInputEditText motelName, motelDescription, motelCounty, motelTown, motelPrice, motelRoomType;

    private Button btnNext, btnBack, btnDone;
    private ViewPager mViewPager;
    OnDetailsReceived mCallBack;

    View rootView = null;

    MapView mMapView;
    private GoogleMap googleMap;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    LocationListener locationListener;

    public NewMotelFragment() {
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    public interface OnDetailsReceived {
        void updateDetails(String name, String description, String county, String price, String type, String town);

        void updateImages(Uri uri1, Uri uri2, Uri uri3, Uri uri4);

        void updateAmenities(ArrayList<String> amenities);

        void updateMapDetails(String longitude, String latitufe);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallBack = (OnDetailsReceived) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    public static NewMotelFragment newInstance(int sectionNumber) {
        NewMotelFragment fragment = new NewMotelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
            rootView = inflater.inflate(R.layout.fragment_new_motel, container, false);


            btnBack = (Button) rootView.findViewById(R.id.btn_back);
            btnNext = (Button) rootView.findViewById(R.id.btn_next);
            btnDone = (Button) rootView.findViewById(R.id.btn_done);


            mViewPager = (ViewPager) getActivity().findViewById(R.id.container);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Log.i("NAME", motelName);
                }
            });


        } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
            rootView = inflater.inflate(R.layout.fragment_new_motel_2, container, false);

            motelName = (TextInputEditText) rootView.findViewById(R.id.motel_name);
            motelCounty = (TextInputEditText) rootView.findViewById(R.id.motel_county);
            motelTown = (TextInputEditText) rootView.findViewById(R.id.motel_town);
            motelRoomType = (TextInputEditText) rootView.findViewById(R.id.motel_room_type);
            motelPrice = (TextInputEditText) rootView.findViewById(R.id.motel_price);
            motelDescription = (TextInputEditText) rootView.findViewById(R.id.motel_description);


            btnBack = (Button) rootView.findViewById(R.id.btn_back);
            btnNext = (Button) rootView.findViewById(R.id.btn_next);
            btnDone = (Button) rootView.findViewById(R.id.btn_done);


            mViewPager = (ViewPager) getActivity().findViewById(R.id.container);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (motelName.getText().toString().isEmpty() || motelDescription.getText().toString().isEmpty() || motelCounty.getText().toString().isEmpty() || motelPrice.getText().toString().isEmpty() || motelRoomType.getText().toString().isEmpty() || motelTown.getText().toString().isEmpty())
                        Toaster.makeText(getContext(), "Please fill in all the details ", Toast.LENGTH_LONG, Toaster.ERROR).show();
                    else {
                        mCallBack.updateDetails(motelName.getText().toString(), motelDescription.getText().toString(), motelCounty.getText().toString(), motelPrice.getText().toString(), motelRoomType.getText().toString(), motelTown.getText().toString());
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                    }
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                }
            });

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

        } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {

            final ArrayList<String> amenities = new ArrayList<>();

            rootView = inflater.inflate(R.layout.fragment_new_motel_4, container, false);
            amenitiesRV = rootView.findViewById(R.id.rv_amenities);

            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(rootView.getContext());
            flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

            amenitiesRV.setLayoutManager(flexboxLayoutManager);
            amenitiesAdapter = new AmenitiesAdapter(amenitiesList);
            amenitiesRV.setAdapter(amenitiesAdapter);

            if (amenitiesList.isEmpty())
                populateAmenitiesFlex();


            btnBack = (Button) rootView.findViewById(R.id.btn_back);
            btnNext = (Button) rootView.findViewById(R.id.btn_next);
            btnDone = (Button) rootView.findViewById(R.id.btn_done);


            mViewPager = (ViewPager) getActivity().findViewById(R.id.container);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    amenities.clear();
                    for (AmenitiesModel model : amenitiesList) {
                        if (model.isSelected()) {
                            amenities.add(model.getName());
                        }
                    }
                    if (!amenities.isEmpty()) {
                        mCallBack.updateAmenities(amenities);
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                    } else
                        Toaster.makeText(getContext(), "Please select at least one amenity ", Toast.LENGTH_LONG, Toaster.ERROR).show();

                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                }
            });

        } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {

            rootView = inflater.inflate(R.layout.new_motel_map_fragment, container, false);


            btnBack = (Button) rootView.findViewById(R.id.btn_back);
            btnNext = (Button) rootView.findViewById(R.id.btn_next);
            btnDone = (Button) rootView.findViewById(R.id.btn_done);


            mViewPager = (ViewPager) getActivity().findViewById(R.id.container);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                }
            });

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Log.i("NAME", motelName);
                }
            });

            mMapView = (MapView) rootView.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;

                    // For showing a move to my location button
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    googleMap.setMyLocationEnabled(true);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkLocationPermission();
                    }

                    // For dropping a marker at a point on the Map
//                    LatLng sydney = new LatLng(-34, 151);
//                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
//
//                    // For zooming automatically to the location of the marker
//                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }


            });

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //Place current location marker
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Selected Position");
                    markerOptions.snippet("Your motel will be added to this position");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    googleMap.addMarker(markerOptions);

                    Log.i("Location", "Calledd");

                    //move map camera
                    //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    //googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(17).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }
            };



        }else if (getArguments().getInt(ARG_SECTION_NUMBER) == 5){

            rootView = inflater.inflate(R.layout.fragment_new_motel_3, container, false);


            btnBack = (Button) rootView.findViewById(R.id.btn_back);
            btnNext = (Button) rootView.findViewById(R.id.btn_next);
            btnDone = (Button) rootView.findViewById(R.id.btn_done);


            mViewPager = (ViewPager) getActivity().findViewById(R.id.container);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                }
            });

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (uri1 != null || uri2 != null || uri3 != null || uri4 != null){
                        mCallBack.updateImages(uri1, uri2, uri3, uri4);
                    }

//                    mCallBack.updateImages(
//                            ((BitmapDrawable)imgOne.getDrawable()).getBitmap(),
//                            ((BitmapDrawable)imgTwo.getDrawable()).getBitmap(),
//                            ((BitmapDrawable)imgThree.getDrawable()).getBitmap(),
//                            ((BitmapDrawable)imgFour.getDrawable()).getBitmap()
//                    );
                }
            });



            imgOne = (ImageView) rootView.findViewById(R.id.image_one);
            imgTwo = (ImageView) rootView.findViewById(R.id.image_two);
            imgThree = (ImageView) rootView.findViewById(R.id.image_three);
            imgFour = (ImageView) rootView.findViewById(R.id.image_four);

            holderOne = (CardView) rootView.findViewById(R.id.image_holder_one);
            holderTwo = (CardView) rootView.findViewById(R.id.image_holder_two);
            holderThree = (CardView) rootView.findViewById(R.id.image_holder_three);
            holderFour = (CardView) rootView.findViewById(R.id.image_holder_four);

            holderOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_ONE);
                }
            });

            holderTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_TWO);
                }
            });

            holderThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_THREE);
                }
            });

            holderFour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_FOUR);
                }
            });
        }


        return rootView;
    }

    private void populateAmenitiesFlex() {
        singleAmenity = new AmenitiesModel("Wifi", R.drawable.ic_email_black_24dp, false);
        amenitiesList.add(singleAmenity);
        singleAmenity = new AmenitiesModel("Pool", R.drawable.ic_check_white_24px, false);
        amenitiesList.add(singleAmenity);
        singleAmenity = new AmenitiesModel("Bed and Breakfast", R.drawable.ic_directions_walk_black_24px, false);
        amenitiesList.add(singleAmenity);
        singleAmenity = new AmenitiesModel("Parking", R.drawable.ic_finish, false);
        amenitiesList.add(singleAmenity);
        singleAmenity = new AmenitiesModel("Restaurant", R.drawable.ic_info_white_24px, false);
        amenitiesList.add(singleAmenity);
        singleAmenity = new AmenitiesModel("Gym", R.drawable.ic_add_location_black_24px, false);
        amenitiesList.add(singleAmenity);
        singleAmenity = new AmenitiesModel("Hotel Bar", R.drawable.ic_announcement_black_24px, false);
        amenitiesList.add(singleAmenity);
        singleAmenity = new AmenitiesModel("Ample security", R.drawable.ic_email_white_24dp, false);
        amenitiesList.add(singleAmenity);
        singleAmenity = new AmenitiesModel("Pick up and Drop off", R.drawable.ic_graphic_eq_black_24px, false);
        amenitiesList.add(singleAmenity);


            amenitiesAdapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_ONE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri1 = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri1);
                imgOne.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == PICK_IMAGE_REQUEST_TWO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri2 = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri2);
                imgTwo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == PICK_IMAGE_REQUEST_THREE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri3 = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri3);
                imgThree.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == PICK_IMAGE_REQUEST_FOUR && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri4 = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri4);
                imgFour.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }



}
