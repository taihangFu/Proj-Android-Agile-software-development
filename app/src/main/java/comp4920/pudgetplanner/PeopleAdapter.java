package comp4920.pudgetplanner;

/**
 * Created by Seansdf on 10/17/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

    public class PeopleAdapter extends BaseAdapter {

        // Declare Variables
        Context mContext;
        LayoutInflater inflater;
        private List<User> userList = null;
        private ArrayList<User> arraylist;
        private String name;
        private String accountID;
        public PeopleAdapter(Context context,
                               List<User> userList,String name,String accountID) {
            mContext = context;
            this.userList = userList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<User>();
            this.arraylist.addAll(userList);
            this.name = name;
            this.accountID = accountID;
        }

        public class ViewHolder {
            //TextView rank;
            TextView user;
            //TextView population;
            //ImageView flag;
        }

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public User getItem(int position) {
            return userList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.peopleview_item, null);
                // Locate the TextViews in listview_item.xml
                //holder.rank = (TextView) view.findViewById(R.id.rank);
                holder.user = (TextView) view.findViewById(R.id.user);
               // holder.population = (TextView) view.findViewById(R.id.population);
                // Locate the ImageView in listview_item.xml
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            //holder.rank.setText(worldpopulationlist.get(position).getRank());
            holder.user.setText(userList.get(position).getUsernamee());
            //holder.population.setText(worldpopulationlist.get(position)
                //    .getPopulation());
            // Set the results into ImageView

            // Listen for ListView Item Click
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // Send single item click data to SingleItemView Class
                    Intent intent = new Intent(mContext, SinglePersonView.class);
                    // Pass all data rank
//                    intent.putExtra("rank",
//                            (worldpopulationlist.get(position).getRank()));
                    // Pass all data country
                    intent.putExtra("user",
                            (userList.get(position).getUsernamee()));
                    intent.putExtra("accountID", accountID);
                    intent.putExtra("name", name);
                    // Pass all data population
//                    intent.putExtra("population",
//                            (worldpopulationlist.get(position).getPopulation()));
                    // Pass all data flag

                    // Start SingleItemView Class
                    mContext.startActivity(intent);
                }
            });

            return view;
        }

        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            userList.clear();
            if (charText.length() == 0) {
                userList.addAll(arraylist);
            } else {
                for (User user : arraylist) {
                    if (user.getUsernamee().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        userList.add(user);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }


