package ke.co.noel.hao.newmotel;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.flexbox.AlignSelf;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

import ke.co.noel.hao.R;
import ke.co.noel.hao.models.AmenitiesModel;

/**
 * Created by HP on 12/12/2017.
 */

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.AmenitiesViewHolder> {

    ArrayList<AmenitiesModel> amenitiesList = new ArrayList<>();

    public AmenitiesAdapter(ArrayList<AmenitiesModel> amenitiesList) {
        this.amenitiesList = amenitiesList;
    }

    @Override
    public AmenitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.amenity_item, parent, false);
        return new AmenitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AmenitiesViewHolder holder, int position) {
        final AmenitiesModel model = amenitiesList.get(position);
        Drawable image = holder.itemView.getContext().getResources().getDrawable(model.getIcon());
        holder.amenitiesButton.setText(model.getName());
        holder.amenitiesButton.setCompoundDrawables(image, null, null, null);
        ViewGroup.LayoutParams lp = holder.amenitiesButton.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setFlexGrow(1.0f);
            flexboxLp.setAlignSelf(AlignSelf.FLEX_END);
        }

        holder.amenitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.amenitiesButton.isChecked()){
                    holder.amenitiesButton.setBackgroundResource(R.drawable.rect_selected);
                    model.setSelected(true);
                }else {
                    holder.amenitiesButton.setBackgroundResource(R.drawable.rectfb);
                    model.setSelected(false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return amenitiesList.size();
    }

    class AmenitiesViewHolder extends RecyclerView.ViewHolder{

        CheckBox amenitiesButton;

        public AmenitiesViewHolder(View itemView) {
            super(itemView);
            amenitiesButton = itemView.findViewById(R.id.amenity_button);

        }
    }
}
