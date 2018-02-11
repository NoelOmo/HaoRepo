package ke.co.noel.hao.newmotel;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ke.co.noel.hao.R;
import ke.co.noel.hao.models.NewHaoImage;
import ke.co.noel.hao.models.NewHaoModel;
import noel.co.ke.toaster.Toaster;

public class NewMotelActivity extends AppCompatActivity implements NewMotelFragment.OnDetailsReceived{

    private NewMotelAdapter mSectionsPagerAdapter;
    private Button btnNext,btnBack, btnDone;

    String motelName, motelDescription, motelCounty, motelTown, motelPrice, motelRoomType;
    ArrayList<String> amenities = new ArrayList<>();

    private ViewPager mViewPager;

    private FirebaseStorage storage;
    private StorageReference storageRef,imageRef;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    UploadTask uploadTask;
    ProgressDialog progressDialog;

    String key;
    Map<String, Object> childUpdates;
    int i;
    ArrayList<String> images_urls;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toaster.makeText(getApplicationContext(), "Welcome " + currentUser.getDisplayName(), Toaster.LENGTH_SHORT, Toaster.INFO).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_motel);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        images_urls = new ArrayList<>();

        btnBack = (Button) findViewById(R.id.btn_back);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnDone = (Button) findViewById(R.id.btn_done);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new NewMotelAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(mViewPager);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.i("Position", String.valueOf(position));
                Log.i("Last", String.valueOf(mViewPager.getAdapter().getCount()));

            }

            @Override
            public void onPageSelected(int position) {
//                btnDone.setVisibility(position + 1 == mSectionsPagerAdapter.getCount() ? View.VISIBLE : View.GONE);
//                btnNext.setVisibility(position + 1 == mSectionsPagerAdapter.getCount() ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_motel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void updateDetails(String name, String description, String county, String price, String type, String town) {
        this.motelName = name;
        this.motelDescription = description;
        this.motelCounty = county;
        this.motelPrice = price;
        this.motelRoomType = type;
        this.motelTown = town;


    }

    @Override
    public void updateAmenities(ArrayList<String> amenities) {
        this.amenities = amenities;
    }

    @Override
    public void updateMapDetails(String longitude, String latitufe) {

    }

    @Override
    public void updateImages(Uri uri1, Uri uri2, Uri uri3, Uri uri4) {
        ArrayList<Uri> uriArrayList = new ArrayList<>();
        uriArrayList.add(uri1);
        uriArrayList.add(uri2);
        uriArrayList.add(uri3);
        uriArrayList.add(uri4);


        key = mDatabase.child("motels").push().getKey();

//        NewHaoModel model = new NewHaoModel(motelName, motelCounty, motelDescription, motelPrice, "UID", "36.8685834", "-1.2918387", images_urls);
//
//        Map<String, Object> postValues = model.toMap();
//
//          childUpdates = new HashMap<>();
//        childUpdates.put("/motels/" + key, postValues);

       for (i = 0; i < uriArrayList.size(); i++){
           progressDialog.setMessage("Uploading images ");
           progressDialog.show();
           imageRef = storageRef.child("motels/"+ key + "/img_" + i);
           uploadTask = imageRef.putFile(uriArrayList.get(i));
           uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                   double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                   progressDialog.incrementProgressBy((int) progress);
               }
           });
           uploadTask.addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toaster.makeText(getApplicationContext(), "There was an error in uploading!", Toaster.LENGTH_SHORT, Toaster.ERROR).show();
                   progressDialog.dismiss();
               }
           }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                   images_urls.add(String.valueOf(taskSnapshot.getDownloadUrl()));

                  if (images_urls.size() == 4 ){

                      NewHaoModel model = new NewHaoModel(motelName, motelCounty, motelDescription, motelPrice, currentUser.getUid(), "36.8685834", "-1.2918387", images_urls, amenities);

                      Map<String, Object> postValues = model.toMap();

                      childUpdates = new HashMap<>();
                      childUpdates.put("/motels/" + key, postValues);
                      mDatabase.updateChildren(childUpdates);
                  }

                   progressDialog.dismiss();

                   Toaster.makeText(getApplicationContext(), "Motel added Successfully", Toaster.LENGTH_SHORT, Toaster.SUCCESS).show();
                   finish();

               }
           });


       }


    }



}
