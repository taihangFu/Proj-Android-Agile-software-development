package comp4920.pudgetplanner;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Zaw on 12/10/2015.
 */
public class SingleAccountView extends AppCompatActivity implements View.OnClickListener{
    TextView txtname;
    String name;
    String accountID;
    Button btnAddIE;
    Button btnAddPeople;
    TextView btnRefreshAccount;
    ListView listview;
    private Toolbar toolbar;
    List<ParseObject> accountList;
    ProgressDialog mProgressDialog;

    private String array_spinner[];

    private HashMap<String,Integer> hmCategory =  new HashMap<String,Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the view from singleitemview.xml
        setContentView(R.layout.single_account_view);
        hmSetup();

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        btnRefreshAccount = (TextView) findViewById(R.id.btnRefresh);
        btnRefreshAccount.setOnClickListener(this);
        // Retrieve data from MainActivity on item click event
        Intent i = getIntent();

        // Get the name
        name = i.getStringExtra("name");
        accountID = i.getStringExtra("accountID");
        // Locate the TextView in singleitemview.xml
        txtname = (TextView) findViewById(R.id.name);

        // Load the text into the TextView
        txtname.setText("Account Name : " + name);

        btnAddIE = (Button) findViewById(R.id.btnAddIE);
        btnAddIE.setOnClickListener(this);

        btnAddPeople = (Button) findViewById(R.id.btnAddPeople);
        btnAddPeople.setOnClickListener(this);
        new RemoteDataTask().execute();
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("budgetAccount");
//        query.whereEqualTo("objectId", accountID);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> accountList, com.parse.ParseException e) {
//                if (e == null) {
//                    if (accountList.size() > 0) {
//                        for (int i = 0; i < accountList.size(); i++) {
//                            ParseObject p = accountList.get(i);
//                            if (p.getJSONArray("transactions") != null) {
//                                JSONArray jsonArrayTransaction = p.getJSONArray("transactions");
//                                if (jsonArrayTransaction.length() > 0) {
//                                    try {
//                                        ArrayList<SingleAccountViewModel> generateDataArray = generateData(jsonArrayTransaction);
//                                        listview = (ListView) findViewById(R.id.listView);
//                                        //"register" menu in listview
//                                        registerForContextMenu(listview);
//
//                                        SingleAccountViewAdapter adapter = new SingleAccountViewAdapter(getApplicationContext(), generateDataArray);
//                                        listview.setAdapter(adapter);
//
//                                        //add "starting balance" footer
//                                        View footer = getLayoutInflater().inflate(R.layout.starting_balance_footer, null);
//                                        listview.addFooterView(footer);
//                                        setStartingBalance(p.getInt("startingBalance"));
//                                    } catch (JSONException e1) {
//                                        e1.printStackTrace();
//                                    }
//                                }else{
//                                    listview = (ListView) findViewById(R.id.listView);
//                                    registerForContextMenu(listview);
//                                    SingleAccountViewAdapter adapter = new SingleAccountViewAdapter(getApplicationContext(), new ArrayList<SingleAccountViewModel>());
//                                    listview.setAdapter(adapter);
//                                    View footer = getLayoutInflater().inflate(R.layout.starting_balance_footer, null);
//                                    listview.addFooterView(footer);
//                                    setStartingBalance(p.getInt("startingBalance"));
//                                }
//                            }else{
//                                listview = (ListView) findViewById(R.id.listView);
//                                registerForContextMenu(listview);
//                                SingleAccountViewAdapter adapter = new SingleAccountViewAdapter(getApplicationContext(), new ArrayList<SingleAccountViewModel>());
//                                listview.setAdapter(adapter);
//                                View footer = getLayoutInflater().inflate(R.layout.starting_balance_footer, null);
//                                listview.addFooterView(footer);
//                                setStartingBalance(p.getInt("startingBalance"));
//                            }
//
//                        //updated the balance here
//                        updateBalance(p.getInt("balance"));
////
//                        }
////                    // row of Object Id "U8mCwTHOaC"
//                    } else {
////                    // error
////                    Toast.makeText(getApplicationContext(),
////                            "Errorrr!",
////                            Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });

    }
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SingleAccountView.this);
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
            ParseQuery<ParseObject> query = ParseQuery.getQuery("budgetAccount");
            query.whereEqualTo("objectId", accountID);
            try {
                accountList = query.find();
            } catch (ParseException e) {
                Log.e("Error   ------- ", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            for (int i = 0; i < accountList.size(); i++) {
                ParseObject p = accountList.get(i);
                if (p.getJSONArray("transactions") != null) {
                    JSONArray jsonArrayTransaction = p.getJSONArray("transactions");
                    if (jsonArrayTransaction.length() > 0) {
                        try {
                            ArrayList<SingleAccountViewModel> generateDataArray = generateData(jsonArrayTransaction);
                            listview = (ListView) findViewById(R.id.listView);
                            //"register" menu in listview
                            registerForContextMenu(listview);

                            SingleAccountViewAdapter adapter = new SingleAccountViewAdapter(getApplicationContext(), generateDataArray);
                            listview.setAdapter(adapter);

                            //add "starting balance" footer
                            View footer = getLayoutInflater().inflate(R.layout.starting_balance_footer, null);
                            listview.addFooterView(footer);
                            setStartingBalance(p.getInt("startingBalance"));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        listview = (ListView) findViewById(R.id.listView);
                        registerForContextMenu(listview);
                        SingleAccountViewAdapter adapter = new SingleAccountViewAdapter(getApplicationContext(), new ArrayList<SingleAccountViewModel>());
                        listview.setAdapter(adapter);
                        View footer = getLayoutInflater().inflate(R.layout.starting_balance_footer, null);
                        listview.addFooterView(footer);
                        setStartingBalance(p.getInt("startingBalance"));
                    }
                } else {
                    listview = (ListView) findViewById(R.id.listView);
                    registerForContextMenu(listview);
                    SingleAccountViewAdapter adapter = new SingleAccountViewAdapter(getApplicationContext(), new ArrayList<SingleAccountViewModel>());
                    listview.setAdapter(adapter);
                    View footer = getLayoutInflater().inflate(R.layout.starting_balance_footer, null);
                    listview.addFooterView(footer);
                    setStartingBalance(p.getInt("startingBalance"));
                }

                //updated the balance here
                updateBalance(p.getInt("balance"));
//
            }

        }
    }
    private ArrayList<SingleAccountViewModel> generateData(JSONArray jsonArrayTransaction) throws JSONException {
        ArrayList<SingleAccountViewModel> model = new ArrayList<SingleAccountViewModel>();

         for (int j = jsonArrayTransaction.length() - 1; j >= 0; j--) {
                try {
                    JSONObject jsonObject = jsonArrayTransaction.getJSONObject(j);

                    model.add(new SingleAccountViewModel(hmCategory.get(jsonObject.getString("category")),
                            jsonObject.getString("name"), jsonObject.getString("date"), jsonObject.getInt("amount")));

                    //start adding stuff to the model


                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

        return model;
    }

    @Override
    //add new income/expense
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRefresh:
                Toast.makeText(getApplicationContext(),
                        "Refresh!",
                        Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
                break;
            case R.id.btnAddIE:
                Intent i = new Intent(SingleAccountView.this, AddIncomeExpense.class);
                i.putExtra("accountID", accountID);
                i.putExtra("name",name);
                startActivity(i);
                break;
            case R.id.btnAddPeople:

                i = new Intent(SingleAccountView.this, SearchPeople.class);
                i.putExtra("accountID", accountID);
                i.putExtra("name",name);
                startActivity(i);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("budgetAccount");
        query.whereEqualTo("objectId", accountID);
        switch(item.getItemId()) {
            case R.id.transfer:
                Intent i = new Intent(SingleAccountView.this,
                        TransferTransaction.class);
                i.putExtra("position", position);
                i.putExtra("accountID",accountID);
                i.putExtra("name",name);
                startActivity(i);

                return true;
            case R.id.edit:
                 i = new Intent(SingleAccountView.this,
                        EditTransaction.class);
                i.putExtra("name", name);
                i.putExtra("accountID", accountID);
                i.putExtra("position",info.position);
                startActivity(i);
                return true;
            case R.id.delete:
                AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                        //set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete?")

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("budgetAccount");
                                query.whereEqualTo("objectId", accountID);
                                query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> accountList, com.parse.ParseException e) {
                                        if (e == null) {
                                            if (accountList.size() > 0) {
                                                for (int i = 0; i < accountList.size(); i++) {
                                                    ParseObject p = accountList.get(i);
                                                    if (p.getJSONArray("transactions") != null) {
                                                        JSONArray jsonArrayTransaction = p.getJSONArray("transactions");
                                                        System.out.println("aaaaaaa - " + jsonArrayTransaction.length() + " - " + position);
                                                        try {
                                                            System.out.println("aaaaaaa - " + jsonArrayTransaction.getJSONObject(jsonArrayTransaction.length() - 1 - position));
                                                        } catch (JSONException e1) {
                                                            e1.printStackTrace();
                                                        }
                                                        int balance = p.getInt("balance");
                                                        JSONObject jsonObject = null;
                                                        try {
                                                            jsonObject = jsonArrayTransaction.getJSONObject(jsonArrayTransaction.length() - 1 - position);
                                                            balance = balance - jsonObject.getInt("amount");
                                                        } catch (JSONException e1) {
                                                            e1.printStackTrace();
                                                        }
                                                        jsonArrayTransaction.remove(jsonArrayTransaction.length() - 1 - position);
                                                        p.put("transactions", jsonArrayTransaction);
                                                        p.put("balance", balance);
                                                        p.saveInBackground();
                                                        Toast.makeText(getApplicationContext(),
                                                                "Deleted!",
                                                                Toast.LENGTH_LONG).show();
                                                        finish();
                                                        Intent intent = getIntent();
                                                        intent.putExtra("accountID", accountID);
                                                        intent.putExtra("name", name);
                                                        startActivity(intent);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                });

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.single_account_view_pop_up_menu, menu);
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

    private void setStartingBalance(Integer startingBalance) {
        TextView textView = (TextView) findViewById(R.id.starting_balance);
        textView.setText(startingBalance.toString());
        if (startingBalance >= 0) {
            textView.setTextColor(Color.parseColor("#A4C639"));
        } else if(startingBalance < 0) {
            textView.setTextColor(Color.parseColor("#E32636"));
        }
    }
    private void hmSetup(){
        hmCategory.put("Bonus",R.drawable.bonus_icon);
        hmCategory.put("Salary",R.drawable.salary);
        hmCategory.put("Saving Deposit",R.drawable.savings_deposit_icon);
        hmCategory.put("Tax Refund",R.drawable.taxrefund_icon);

        hmCategory.put("Auto",R.drawable.auto);
        hmCategory.put("Bank Charge", R.drawable.bank_charge);
        hmCategory.put("Cash",R.drawable.cash);
        hmCategory.put("Charity",R.drawable.charity);
        hmCategory.put("Childcare",R.drawable.childcare);
        hmCategory.put("Clothing",R.drawable.clothing);
        hmCategory.put("Credit Card Payment",R.drawable.credit_card_payment);
        hmCategory.put("Eating Out",R.drawable.eating_out);
        hmCategory.put("Education",R.drawable.education);
        hmCategory.put("Entertainment",R.drawable.entertainment);
        hmCategory.put("Gifts",R.drawable.gifts);
        hmCategory.put("Groceries",R.drawable.groceries);
        hmCategory.put("Health And Fitness",R.drawable.health_and_fitness);
        hmCategory.put("Household",R.drawable.household);
        hmCategory.put("Home Repair",R.drawable.home_repair);
        hmCategory.put("Interent",R.drawable.interent);
        hmCategory.put("Interest Exp",R.drawable.interest_exp);
        hmCategory.put("Loan",R.drawable.loan);
        hmCategory.put("Medical",R.drawable.medical);
        hmCategory.put("Misc",R.drawable.misc);
        hmCategory.put("Mortgage Payment",R.drawable.mortgage_payment);
        hmCategory.put("Others",R.drawable.others);
        hmCategory.put("Pets",R.drawable.pets);
        hmCategory.put("Rent",R.drawable.rent);
        hmCategory.put("Tax",R.drawable.tax);
        hmCategory.put("Telephone",R.drawable.telephone);
        hmCategory.put("Transport",R.drawable.transport);
        hmCategory.put("Travel",R.drawable.travel);
        hmCategory.put("Utilities",R.drawable.utilities);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SingleAccountView.this, Account.class);
        startActivity(i);
        return;
    }

}