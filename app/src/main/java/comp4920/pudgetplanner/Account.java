package comp4920.pudgetplanner;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp4920.pudgetplanner.model.AccountModel;

/**
 * Created by michellephan on 30/09/2015.
 */
public class Account extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    Button btnAddAccount;
    TextView btnRefreshAccount;
    ListView listview;
    List<ParseObject> ob;

    ProgressDialog mProgressDialog;
    //ArrayAdapter<String> adapter;
    private TextView tvNetWorth;
    private HashMap<String,Integer> hmAccountType =  new HashMap<String,Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        hmSetup();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        tvNetWorth = (TextView) findViewById(R.id.tvNetWorthAmount);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigation_drawer drawer = (navigation_drawer)
                getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        btnAddAccount = (Button) findViewById(R.id.addAccount);
        btnAddAccount.setOnClickListener(this);
        btnRefreshAccount = (TextView) findViewById(R.id.btnRefresh);
        btnRefreshAccount.setOnClickListener(this);


        new RemoteDataTask().execute();
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
//                "budgetAccount");
//        ParseUser currentUser = ParseUser.getCurrentUser();
//        query.whereEqualTo("username", currentUser);
//        query.orderByDescending("createdAt");
//        try {
//            ob = query.find();
//        } catch (ParseException e) {
//            Log.e("Error   ------- ", e.getMessage());
//            e.printStackTrace();
//        }
//        listview = (ListView) findViewById(R.id.listViewAccount);
//
//        //"register" menu in listview
//        registerForContextMenu(listview);
//
//
//        AccountAdapter adapter = new AccountAdapter(this, generateData());
//
//        listview.setAdapter(adapter);
//        int netWorth = getNetWorth();
//        tvNetWorth.setText("$" + netWorth);
//        if (netWorth >= 0) {
//            tvNetWorth.setTextColor(Color.parseColor("#A4C639"));
//        } else if(netWorth < 0) {
//            tvNetWorth.setTextColor(Color.parseColor("#E32636"));
//        }
//        /**
//        adapter = new ArrayAdapter<String>(Account.this,
//                R.layout.listviewaccount_item);
//
//        for (ParseObject budgetAccount : ob) {
//            adapter.add((String) budgetAccount.get("accountName"));
//        }
//
//        listview.setAdapter(adapter);
//         **/
//
//
//
//        listview.setOnItemClickListener(new OnItemClickListener() {
//        @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // Send single item click data to SingleAccountView Class
//                Intent i = new Intent(Account.this,
//                        SingleAccountView.class);
//                // Pass data "name" followed by the position
//                i.putExtra("name", ob.get(position).getString("accountName")
//                        .toString());
//                i.putExtra("accountID",ob.get(position).getObjectId());
//                // Open SingleAccountView.java Activity
//                startActivity(i);
//            }
//        });
    }
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Account.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please wait a while");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "budgetAccount");
            ParseUser currentUser = ParseUser.getCurrentUser();
            query.whereEqualTo("username", currentUser);
            query.orderByDescending("createdAt");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error   ------- ", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            listview = (ListView) findViewById(R.id.listViewAccount);

            //"register" menu in listview
            registerForContextMenu(listview);


            AccountAdapter adapter = new AccountAdapter(Account.this, generateData());

            listview.setAdapter(adapter);
            mProgressDialog.dismiss();
            int netWorth = getNetWorth();
            tvNetWorth.setText("$" + netWorth);
            if (netWorth >= 0) {
                tvNetWorth.setTextColor(Color.parseColor("#A4C639"));
            } else if(netWorth < 0) {
                tvNetWorth.setTextColor(Color.parseColor("#E32636"));
            }
            /**
             adapter = new ArrayAdapter<String>(Account.this,
             R.layout.listviewaccount_item);

             for (ParseObject budgetAccount : ob) {
             adapter.add((String) budgetAccount.get("accountName"));
             }

             listview.setAdapter(adapter);
             **/



            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // Send single item click data to SingleAccountView Class
                    Intent i = new Intent(Account.this,
                            SingleAccountView.class);
                    // Pass data "name" followed by the position
                    i.putExtra("name", ob.get(position).getString("accountName")
                            .toString());
                    i.putExtra("accountID",ob.get(position).getObjectId());
                    // Open SingleAccountView.java Activity
                    startActivity(i);
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addAccount:

                Intent i = new Intent(Account.this, AddAccount.class);
                startActivity(i);
                break;
            case R.id.btnRefresh:
                Toast.makeText(getApplicationContext(),
                        "Refresh!",
                        Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
                break;
        }
    }

    private int getNetWorth(){
        int netWorth = 0;
        for (ParseObject budgetAccount : ob) {
            netWorth += budgetAccount.getInt("balance");
        }
        return netWorth;
    }
    private ArrayList<AccountModel> generateData(){
        ArrayList<AccountModel> model = new ArrayList<AccountModel>();

      for (ParseObject budgetAccount : ob) {
          model.add(new AccountModel(hmAccountType.get(budgetAccount.getString("iconPath")), (String) budgetAccount.get("accountName"),
                   budgetAccount.getInt("balance"), (String) budgetAccount.get("accountType")));
      }

        return model;
    }

    //for pop up menu when a item in listview is clicked

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listViewAccount) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.account_pop_up_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(Account.this,
                        EditAccount.class);
                i.putExtra("name", ob.get(info.position).getString("accountName")
                        .toString());
                i.putExtra("accountID",ob.get(info.position).getObjectId());
                startActivity(i);
                return true;
            case R.id.delete:
                AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                        //set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete?")

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code

                                ParseObject.createWithoutData("budgetAccount", ob.get(info.position).getObjectId()).deleteEventually();
                                finish();
                                startActivity(getIntent());
                                dialog.dismiss();
                            }

                        })

                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .create();
                         myQuittingDialogBox.show();

                return true;

            default:
                return super.onContextItemSelected(item);
        }


    }

    private void updateBalance(Integer balance){
        TextView textView = (TextView) findViewById(R.id.balance_amount);
        textView.setText(balance.toString());
        if (balance >= 0) {
            textView.setTextColor(Color.parseColor("#A4C639"));
        } else if(balance < 0) {
            textView.setTextColor(Color.parseColor("#E32636"));
        }
        return;
    }


    private void hmSetup(){
        hmAccountType.put("Asset",R.drawable.assets_icon);
        hmAccountType.put("Cash",R.drawable.cash_icon);
        hmAccountType.put("Cheque",R.drawable.cheque_icon);
        hmAccountType.put("Credit Card",R.drawable.creditcard_icon);

        hmAccountType.put("Debit Card",R.drawable.debitcard_icon);
        hmAccountType.put("Investing", R.drawable.investment_icon);
        hmAccountType.put("Loan",R.drawable.loan_icon);
        hmAccountType.put("Savings", R.drawable.savings_icon);
        hmAccountType.put("Others",R.drawable.others);

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            // super.onBackPressed();
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.logOut();
            this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}


