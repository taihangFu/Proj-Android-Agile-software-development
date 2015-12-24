package comp4920.pudgetplanner;

/**
 * Created by Seansdf on 10/12/2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import comp4920.pudgetplanner.model.AccountModel;

public class AccountAdapter extends ArrayAdapter<AccountModel> {
    private final Context context;
    private final ArrayList<AccountModel> modelsArrayList;

    public AccountAdapter(Account context, ArrayList<AccountModel> modelsArrayList) {
        super(context, R.layout.listviewaccount_item, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
//        if(!modelsArrayList.get(position).isGroupHeader()){
            rowView = inflater.inflate(R.layout.listviewaccount_item, parent, false);

            // 3. Get icon,accountName & balance, accoountType views from the rowView
            ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon);
            TextView accountNameView = (TextView) rowView.findViewById(R.id.accountName);
            TextView balanceView = (TextView) rowView.findViewById(R.id.balance);
            TextView accountTypeView = (TextView) rowView.findViewById(R.id.accountType);

            // 4. Set the text for textView
            imgView.setImageResource(modelsArrayList.get(position).getIcon());
            accountNameView.setText(modelsArrayList.get(position).getTitle());
            balanceView.setText(modelsArrayList.get(position).getBalance());
            accountTypeView.setText(modelsArrayList.get(position).getBalanceType());

            //dynamically change "balance" colour based on the amount
            int balance = Integer.parseInt(modelsArrayList.get(position).getBalance());

            if (balance >= 0) {
                balanceView.setTextColor(Color.parseColor("#A4C639"));
            } else if(balance < 0) {
                balanceView.setTextColor(Color.parseColor("#E32636"));
            }


//        }else{
//            rowView = inflater.inflate(R.layout.group_header_item, parent, false);
//            TextView titleView = (TextView) rowView.findViewById(R.id.header);
//            titleView.setText(modelsArrayList.get(position).getTitle());
//
//        }

        // 5. retrn rowView
        return rowView;
    }
}
