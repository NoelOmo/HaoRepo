package ke.co.noel.hao.onboarding;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import ke.co.noel.hao.R;
import ke.co.noel.hao.StartActivity;
import ke.co.noel.hao.authentication.LoginActivity;

public class OnboardingActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            startActivity(new Intent(OnboardingActivity.this, StartActivity.class));


        getNextButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.onboarding_bg)
                        .buttonsColor(R.color.onboarding_buttons)
                        .image(R.drawable.img_office)
                        .title("Welcome")
                        .description("We make it easy to find you motels in areas you've never been to.")

                        .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.onboarding_bg)
                .buttonsColor(R.color.onboarding_buttons)
                .image(R.drawable.img_office)
                .title("Dashboard")
                .description("Select a pin from the dashboard in order to see a full description of the motel.")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.onboarding_bg)
                .buttonsColor(R.color.onboarding_buttons)
                .image(R.drawable.img_material_intro)
                .title("Calculate Distance")
                .description("We calculate the distance from your current location to your motel of interest.")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.onboarding_bg)
                .buttonsColor(R.color.onboarding_buttons)
                .image(R.drawable.img_equipment)
                .title("Book Your Room")
                .description("Book your room before you even get to the motel. You'll find everything ready")
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();
//        Toast.makeText(this, "Hope you like it! :)", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(OnboardingActivity.this, StartActivity.class));
    }
}
