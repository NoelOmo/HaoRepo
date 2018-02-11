package ke.co.noel.hao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ke.co.noel.hao.bookingrequests.BookingRequestsActivity;
import ke.co.noel.hao.home.HomeActivity;
import ke.co.noel.hao.models.BookingRequestModel;
import ke.co.noel.hao.newmotel.NewMotelActivity;

public class DashActivity extends AppCompatActivity {

    CardView addNewMotel, viewMap, stats, bookings;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    TextView bookingRequest;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("Please Wait");
        mProgressDialog.setMessage("Initializing...");
        mProgressDialog.setCancelable(false);



        user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("motels");

        bookingRequest = (TextView) findViewById(R.id.txt_booking_request);

        addNewMotel = (CardView) findViewById(R.id.add_new_motel);
        stats = (CardView) findViewById(R.id.stats);
        bookings = (CardView) findViewById(R.id.bookings);

        addNewMotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewMotelActivity.class));
            }
        });
        viewMap = (CardView) findViewById(R.id.view_map);
        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BookingRequestsActivity.class));
            }
        });


        mProgressDialog.show();


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<BookingRequestModel> modelList = new ArrayList<>();
                BookingRequestModel model;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.hasChild("bookings") && snapshot.child("uid").getValue().equals(user.getUid())){
                        model = new BookingRequestModel(
                                (String) snapshot.child("bookings").child("userName").getValue(),
                                (String) snapshot.child("bookings").child("email").getValue(),
                                (String) snapshot.child("bookings").child("userID").getValue(),
                                (String) snapshot.child("bookings").child("phoneNumber").getValue(),
                                (String) snapshot.child("bookings").child("roomType").getValue(),
                                (String) snapshot.child("bookings").child("stayLength").getValue(),
                                (String) snapshot.child("bookings").child("specialRequirements").getValue());
                        modelList.add(model);
                    }
                }
                bookingRequest.setText(String.valueOf(modelList.size()));
                mProgressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
