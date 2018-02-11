package ke.co.noel.hao.booking;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ke.co.noel.hao.R;
import ke.co.noel.hao.customfonts.MyEditText;
import ke.co.noel.hao.models.BookingDetailsModel;
import ke.co.noel.hao.models.Example;
import ke.co.noel.hao.models.HaoModel;
import ke.co.noel.hao.models.NotiApiModel;
import ke.co.noel.hao.push.PushAPI;
import ke.co.noel.hao.slider.SlidePagerAdapter;
import noel.co.ke.toaster.Toaster;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookingActivity extends AppCompatActivity {

    Button btnBook;
    ProgressDialog mProgressDialog;
    MyEditText userPhoneNumber, userEmergencyContact, userStayLength, userSpecialRequirements;
    RadioButton singleType, doubleType;

    Map<String, Object> childUpdates;
    String key, motelName;
    private DatabaseReference mDatabase, mInstanceIdRef, mUserRef;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String notificationPushId = "";
    private HaoModel model;


    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("Please Wait");
        mProgressDialog.setMessage("Sending...");
        mProgressDialog.setCancelable(false);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        key = getIntent().getExtras().getString("DB_KEY");
        motelName = getIntent().getExtras().getString("MOTEL_NAME");

        getSupportActionBar().setSubtitle(motelName);

        mInstanceIdRef = FirebaseDatabase.getInstance().getReference().child("motels").child(key);
        mInstanceIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    Log.i("PARENTNODE", dataSnapshot.getKey());
                    model = dataSnapshot.getValue(HaoModel.class);
                    mProgressDialog.show();
                    Log.i("THIS METHOD","WORKSS");
                    populateData(model);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userPhoneNumber = (MyEditText) findViewById(R.id.user_phone_number);
        userEmergencyContact = (MyEditText) findViewById(R.id.user_emergency_contact);
        userStayLength = (MyEditText) findViewById(R.id.user_stay_length);
        userSpecialRequirements = (MyEditText) findViewById(R.id.user_special_requirement);

        singleType = (RadioButton) findViewById(R.id.type_single);
        doubleType = (RadioButton) findViewById(R.id.type_double);

        singleType.setSelected(true);

        btnBook = (Button) findViewById(R.id.btn_book_motel);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mProgressDialog.show();
//                TODO emergencyContact: Baby what is you doing?

                if (currentUser == null ||
                        userPhoneNumber.getText().toString().isEmpty() ||
                        userStayLength.getText().toString().isEmpty() ||
                        userSpecialRequirements.getText().toString().isEmpty()){
                    Toaster.makeText(BookingActivity.this, "Please make sure you have filled all the fields", Toast.LENGTH_LONG, Toaster.ERROR).show();
                }else {
                    BookingDetailsModel bookMeIn = new BookingDetailsModel(currentUser.getDisplayName(), currentUser.getEmail(), currentUser.getUid(), userPhoneNumber.getText().toString(), singleType.isChecked() ? "Single" : "Double", userStayLength.getText().toString(), userSpecialRequirements.getText().toString());
                    Map<String, Object> postValues = bookMeIn.toMap();
                    childUpdates = new HashMap<>();
                    childUpdates.put("/motels/" + key + "/bookings", postValues);
                    mDatabase.updateChildren(childUpdates);
                    Toaster.makeText(BookingActivity.this, "Booked Successfully", Toast.LENGTH_SHORT, Toaster.SUCCESS).show();

                    notifyMotel();
                }

            }
        });
    }

    private void populateData(HaoModel model) {

        String modelUID = model.getUid();

        mUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(modelUID);

        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null){
                    String token = (String) dataSnapshot.child("instanceID").getValue();
                    notificationPushId = token;

                }


                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void notifyMotel() {

        String url = "https://api.hao.co.ke/";


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();



        if (!notificationPushId.isEmpty()) {
            PushAPI service = retrofit.create(PushAPI.class);
            String msg = getString(R.string.new_booking_request, motelName, FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            Call<NotiApiModel> call = service.pushToDevice(msg, notificationPushId);
            Log.i("NotificationId", notificationPushId);
            call.enqueue(new Callback<NotiApiModel>() {
                @Override
                public void onResponse(Call<NotiApiModel> call, Response<NotiApiModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().success == 1) {
                            Toaster.makeText(BookingActivity.this, "Booking successful", Toast.LENGTH_LONG, Toaster.SUCCESS).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<NotiApiModel> call, Throwable t) {

                }
            });

        }


    }


}
