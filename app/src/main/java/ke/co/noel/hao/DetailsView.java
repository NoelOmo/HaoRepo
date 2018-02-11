package ke.co.noel.hao;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ke.co.noel.hao.booking.BookingActivity;
import ke.co.noel.hao.models.HaoModel;
import ke.co.noel.hao.slider.SlidePagerAdapter;

public class DetailsView extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DatabaseReference mDatabase;
    private SlidePagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    String dBKey;
    private TextView motelDescription;
    private HaoModel model;
    List<String> imageURLs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        motelDescription = (TextView) findViewById(R.id.details_description);

        mViewPager = (ViewPager) findViewById(R.id.container);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("motels");

        dBKey = getIntent().getStringExtra("DB_KEY");

        mDatabase.child(dBKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    model = dataSnapshot.getValue(HaoModel.class);
                    populateData(model);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.action_distance:
                        openDistanceActivity();
                        return true;
                    case R.id.action_book:
                        openBookingActivity();
                        return true;

                }
                return false;
            }
        });

    }

    private void openBookingActivity() {
        Intent i = new Intent(this, BookingActivity.class);
        i.putExtra("DB_KEY", dBKey);
        if (getSupportActionBar().getSubtitle() != null)
            i.putExtra("MOTEL_NAME", getSupportActionBar().getTitle());
        startActivity(i);
    }

    private void populateData(HaoModel model) {

        FirebaseDatabase.getInstance().getReference().child("motels").child(dBKey).child("images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    imageURLs.add(String.valueOf(snapshot.getValue()));
                }

                mSectionsPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), imageURLs);
                mViewPager.setAdapter(mSectionsPagerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mViewPager.setAdapter(mSectionsPagerAdapter);

        getSupportActionBar().setTitle(model.getName());
        getSupportActionBar().setSubtitle(model.getLocation());
        motelDescription.setText(model.getDescription());
    }


    public void openDistanceActivity(){
        Intent i = new Intent(this, DistanceActivity.class);
        i.putExtra("DB_KEY", dBKey);
        startActivity(i);

    }
}
