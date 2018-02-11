package ke.co.noel.hao.bookingrequests;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ke.co.noel.hao.R;
import ke.co.noel.hao.models.BookingRequestModel;

/**
 * Created by HP on 12/12/2017.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder> {

    ArrayList<BookingRequestModel> bookingRequestList = new ArrayList<>();

    public BookingAdapter(ArrayList<BookingRequestModel> bookingRequestList) {
        this.bookingRequestList = bookingRequestList;
    }

    @Override
    public BookingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);
        return new BookingHolder(view);
    }

    @Override
    public void onBindViewHolder(BookingHolder holder, int position) {
        BookingRequestModel model = bookingRequestList.get(position);

        holder.userName.setText("Name: " + model.getUserName() + "\n");
        holder.email.setText("Email: " + model.getEmail() + "\n");
        holder.specialRequirements.setText("Special Requirements\n" + model.getSpecialRequirements());
        holder.phoneNumber.setText("Phone: " + model.getPhoneNumber() + "\n");
        holder.roomType.setText("Room type\n" + model.getRoomType() + "\n");
        holder.stayLength.setText("Length of stay\n" + model.getStayLength() + "day(s).\n");

    }

    @Override
    public int getItemCount() {
        return bookingRequestList.size();
    }

    class BookingHolder extends RecyclerView.ViewHolder{

        TextView userName, email, phoneNumber, roomType, stayLength, specialRequirements;

        public BookingHolder(View itemView) {
            super(itemView);


            userName = itemView.findViewById(R.id.requester_name);
            email = itemView.findViewById(R.id.requester_email);
            phoneNumber = itemView.findViewById(R.id.requester_phone);
            roomType = itemView.findViewById(R.id.room_type);
            stayLength = itemView.findViewById(R.id.request_days);
            specialRequirements = itemView.findViewById(R.id.special_requirements);

        }
    }
}
