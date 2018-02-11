package ke.co.noel.hao.home;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ke.co.noel.hao.DetailsView;
import ke.co.noel.hao.R;
import ke.co.noel.hao.models.HaoModel;

/**
 * Created by root on 8/11/17.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeListViewHolder> {

    List<HaoModel> modelList;
    Context mContext;

    public HomeListAdapter(List<HaoModel> modelList, Context mContext) {
        this.modelList = modelList;
        this.mContext = mContext;
    }

    @Override
    public HomeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
        return new HomeListAdapter.HomeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeListViewHolder holder, int position) {
        HaoModel model = modelList.get(position);
        holder.txtTitle.setText(model.getName());
        holder.txtPrice.setText(model.getPrice());
        Picasso.with(mContext).load(R.drawable.hao_icon).into(holder.homeListImage);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class HomeListViewHolder extends RecyclerView.ViewHolder{

        ImageView homeListImage;
        CardView homeListCard;
        TextView txtPrice, txtTitle;

        public HomeListViewHolder(View itemView) {
            super(itemView);
            txtPrice = (TextView) itemView.findViewById(R.id.rec_txt_price);
            txtTitle = (TextView) itemView.findViewById(R.id.rec_txt_title);
            homeListImage = (ImageView) itemView.findViewById(R.id.home_list_image);
            homeListCard = (CardView) itemView.findViewById(R.id.home_list_card);
            homeListCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, DetailsView.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                }
            });
        }
    }
}
