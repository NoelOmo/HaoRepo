package ke.co.noel.hao.bookingrequests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ke.co.noel.hao.R;
import ke.co.noel.hao.models.BookingDetailsModel;
import ke.co.noel.hao.models.BookingRequestModel;
import ke.co.noel.hao.models.HaoModel;

public class BookingRequestsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private BookingAdapter bookingAdapter;
    ArrayList<BookingRequestModel> bookingRequestList = new ArrayList<>();
    RecyclerView bookingRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_requests);

        getSupportActionBar().setTitle("Booking requests");

        user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("motels");

        bookingRV = findViewById(R.id.booking_requests_rv);
        bookingRV.setLayoutManager(new LinearLayoutManager(this));
        bookingAdapter = new BookingAdapter(bookingRequestList);
        bookingRV.setAdapter(bookingAdapter);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<BookingRequestModel> modelList = new ArrayList<>();
                BookingRequestModel model;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.hasChild("bookings") && snapshot.child("uid").getValue().equals(user.getUid())){
                       // model = dataSnapshot.child("bookings") .getValue(BookingRequestModel.class);
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
                populateRecyclerView(modelList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void populateRecyclerView(ArrayList<BookingRequestModel> modelList) {
        bookingRequestList.addAll(modelList);
        bookingAdapter.notifyDataSetChanged();
        getSupportActionBar().setSubtitle("You have " + modelList.size() + " request(s)");
    }


}
