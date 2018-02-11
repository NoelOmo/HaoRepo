package ke.co.noel.hao.slider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


import ke.co.noel.hao.R;

/**
 * Created by root on 10/5/17.
 */

public class SlideFragment extends Fragment {

    private static final String IMAGE_URL = "image_url";

    public SlideFragment() {
    }

    public static SlideFragment newInstance(String imageURL) {
        SlideFragment fragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, imageURL);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_slider, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.details_image);
        Picasso.with(getContext()).load(getArguments().getString(IMAGE_URL)).placeholder(R.drawable.hao_icon).into(imageView);

        return rootView;
    }
}
