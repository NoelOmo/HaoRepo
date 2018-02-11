package ke.co.noel.hao.customshapes;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import ke.co.noel.hao.R;

/**
 * Created by root on 8/28/17.
 */

public class MyTwitterLoginButton extends TwitterLoginButton {

    public MyTwitterLoginButton(Context context) {
        super(context);
    }

    public MyTwitterLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTwitterLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        if (isInEditMode()){
            return;
        }
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Light.ttf");
        setTypeface(tf);
        setBackgroundResource(R.drawable.rectfb);
        setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fb), null, null, null);
        setTextSize(15);
        setPadding(0, 0, 0, 0);
    }
}
