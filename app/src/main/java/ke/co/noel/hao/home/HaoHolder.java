package ke.co.noel.hao.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import ke.co.noel.hao.DetailsView;
import ke.co.noel.hao.R;

/**
 * Created by root on 10/1/17.
 */

public class HaoHolder extends RecyclerView.ViewHolder {

    ImageView homeListImage;
    CardView homeListCard;
    TextView txtPrice, txtTitle;
    Context mContext;
    String customTag;

    public HaoHolder(final View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        txtPrice = (TextView) itemView.findViewById(R.id.rec_txt_price);
        txtTitle = (TextView) itemView.findViewById(R.id.rec_txt_title);
        homeListImage = (ImageView) itemView.findViewById(R.id.home_list_image);
        homeListCard = (CardView) itemView.findViewById(R.id.home_list_card);


        homeListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailsView.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("DB_KEY", customTag);
                mContext.startActivity(i);
   //             Toast.makeText(mContext, "The Tag= " + customTag, Toast.LENGTH_SHORT).show();
            }
        });

    }

        public void setName(String name) {
            txtTitle.setText(name);
        }

        void setPrice(String message) {
            txtPrice.setText("KSH: " + message);
        }
        void setImage(String photo) {
            Picasso.with(mContext).load(photo).placeholder(R.drawable.hao_icon).into(homeListImage);
        }


    public void setCustomTag(String customTag) {
        this.customTag = customTag;
    }
}
