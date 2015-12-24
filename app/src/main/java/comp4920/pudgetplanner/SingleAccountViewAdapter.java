package comp4920.pudgetplanner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Seansdf on 10/14/2015.
 */
public class SingleAccountViewAdapter extends ArrayAdapter<SingleAccountViewModel> {
    private final Context context;
    private final ArrayList<SingleAccountViewModel> modelsArrayList;



    public SingleAccountViewAdapter(Context context, ArrayList<SingleAccountViewModel> modelsArrayList) {
        super(context, R.layout.single_account_view, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
       // if(!modelsArrayList.get(position).isGroupHeader()){
            rowView = inflater.inflate(R.layout.single_account_view_listview_item, parent, false);

            // 3. Get icon,accountName & balance, accoountType views from the rowView
            ImageView imgView = (ImageView) rowView.findViewById(R.id.single_icon);
            TextView transectionView = (TextView) rowView.findViewById(R.id.transection);
            TextView dateView = (TextView) rowView.findViewById(R.id.date);
            TextView amountView = (TextView) rowView.findViewById(R.id.amount);

            // 4. Set the text for textView
            imgView.setImageResource(modelsArrayList.get(position).getIcon());
            transectionView.setText(modelsArrayList.get(position).getTransection());
            dateView.setText(modelsArrayList.get(position).getDate());
            amountView.setText(modelsArrayList.get(position).getAmount());

            //dynamically change "balance" colour based on the amount
            int balance = Integer.parseInt(modelsArrayList.get(position).getAmount());

            if (balance >= 0) {
                amountView.setTextColor(Color.parseColor("#A4C639"));
            } else if(balance < 0) {
                amountView.setTextColor(Color.parseColor("#E32636"));
            }


        //}
//    else{
//            rowView = inflater.inflate(R.layout.group_header_item, parent, false);
//            TextView titleView = (TextView) rowView.findViewById(R.id.header);
//            titleView.setText(modelsArrayList.get(position).getTitle());
//
//        }

        // 5. retrn rowView
        return rowView;
    }

}
