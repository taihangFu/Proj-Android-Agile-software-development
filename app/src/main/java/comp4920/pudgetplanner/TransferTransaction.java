package comp4920.pudgetplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zaw on 16/10/2015.
 */
public class TransferTransaction extends AppCompatActivity implements View.OnClickListener {
    private Button btnTransfer;
    int position;
    String accountID;
    Spinner spinUser;
    String name;
    private Toolbar toolbar;
    List<ParseObject> ob = null;
    ArrayList<String> array_spinner = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_transaction);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnTransfer = (Button) findViewById(R.id.btnTransfer);
        btnTransfer.setOnClickListener(this);

        Intent i = getIntent();
        position = i.getIntExtra("position",0);
        accountID = i.getStringExtra("accountID");

        name = i.getStringExtra("name");


        spinUser= (Spinner) findViewById(R.id.transfer_spinner);//fetch the spinner from layout file

//        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        ParseUser currentUser = ParseUser.getCurrentUser();
//        query.whereNotEqualTo("username", currentUser.getString("username"));
//        query.whereNotEqualTo("emailVerified",false);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("budgetAccount");

        ParseUser currentUser = ParseUser.getCurrentUser();
        ArrayList<ParseUser> c = new ArrayList<ParseUser>();
        c.add(currentUser);
        query.whereNotEqualTo("objectId", accountID);
        query.whereContainedIn("username",c);
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error   ------- ", e.getMessage());
            e.printStackTrace();
        }
        //need spinner

        for (ParseObject account : ob) {
            array_spinner.add(account.getString("accountName"));
        }
//       array_spinner.add("HI");
//       array_spinner.add("HIIII");
//       array_spinner.add("HHJHKJH");




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, array_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinUser.setAdapter(adapter);


        spinUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        }
    JSONObject jObject = null;
    public void setJ(JSONObject obj){
        jObject = obj;
    }
    public JSONObject getJ(){
        return jObject;
    }
    @Override
    public void onClick(View v) {


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

                                int balance = p.getInt("balance");
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = jsonArrayTransaction.getJSONObject(jsonArrayTransaction.length() - 1 - position);
                                    balance = balance - jsonObject.getInt("amount");
                                    setJ(jsonObject);
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                                jsonArrayTransaction.remove(jsonArrayTransaction.length() - 1 - position);
                                p.put("transactions", jsonArrayTransaction);
                                p.put("balance", balance);
                                p.saveInBackground();

                                ParseObject pp = ob.get(spinUser.getSelectedItemPosition());
                                int balancebb = pp.getInt("balance");
                                try {
                                    balancebb = balancebb + jsonObject.getInt("amount");
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                                pp.add("transactions", jsonObject);
                                pp.put("balance", balancebb);

                                pp.saveInBackground();
                                Intent intent = new Intent(TransferTransaction.this, SingleAccountView.class);
                                intent.putExtra("accountID", accountID);
                                intent.putExtra("name", name);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),
                                        "Transferred Successfully!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
    }
}
