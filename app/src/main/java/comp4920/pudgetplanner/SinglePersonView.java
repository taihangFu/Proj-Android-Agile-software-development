package comp4920.pudgetplanner;

/**
 * Created by Seansdf on 10/17/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class SinglePersonView extends AppCompatActivity implements View.OnClickListener {
    TextView txtuser;
    String user;
    String name;
    String accountID;
    Button addUserToAccount;
    private Toolbar toolbar;
    private Button cancelButton;
    List<ParseUser> ob;
    List<ParseObject> objectsss;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.singlepersonview);

            toolbar= (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // Get the intent from ListViewAdapter
            Intent i = getIntent();
            // Get the results of rank
//            rank = i.getStringExtra("rank");
            // Get the results of country
            accountID = i.getStringExtra("accountID");
            user = i.getStringExtra("user");
            name = i.getStringExtra("name");
            // Get the results of population
//            population = i.getStringExtra("population");
            // Get the results of flag

            // Locate the TextViews in singleitemview.xml
//            txtrank = (TextView) findViewById(R.id.rank);
            txtuser = (TextView) findViewById(R.id.user);
//            txtpopulation = (TextView) findViewById(R.id.population);

            // Locate the ImageView in singleitemview.xml

            // Load the results into the TextViews
//            txtrank.setText(rank);
            txtuser.setText("Would you like to invite " + user +"?");
//            txtpopulation.setText(population);

            // Load the image into the ImageView
            addUserToAccount = (Button)findViewById(R.id.add_people_button);
            addUserToAccount.setOnClickListener(this);
            cancelButton = (Button) (findViewById(R.id.button8));
            cancelButton.setOnClickListener(this);
        }

        //add this user to the account
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button8:
                    Intent intent = new Intent(this, SearchPeople.class);
                    intent.putExtra("accountID", accountID);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    break;
                case R.id.add_people_button:
                    ParseUser pUser = null;
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", user);
                    try {
                        ob = query.find();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for (ParseUser u : ob) {
                         pUser = u;
                    }


                    ParseQuery<ParseObject> q = ParseQuery.getQuery("budgetAccount");
                    q.whereEqualTo("objectId", accountID);
                    q.whereEqualTo("username", pUser);
                    try {
                        objectsss = q.find();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(objectsss.size() != 0) {
                        Toast.makeText(getApplicationContext(),
                                "The person is already in the budget account!",
                                Toast.LENGTH_LONG).show();
                    }else {
                        ParsePush parsePush = new ParsePush();
                        ParseQuery pQuery = ParseInstallation.getQuery();
                        pQuery.whereEqualTo("username", user);
                        JSONObject data = new JSONObject();
                        try {
                            data.put("alert", "Hi " + user + ", " + ParseUser.getCurrentUser().getUsername() + " would like you to join " + name);
                            data.put("objectID", accountID);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        parsePush.setData(data);
                        parsePush.setQuery(pQuery);
                        //parsePush.setMessage(ParseUser.getCurrentUser().getUsername() + " would like you to join " + name);
                        // parsePush.sendMessageInBackground(ParseUser.getCurrentUser().getUsername() + " would like you to join " + name,pQuery);
                        parsePush.sendInBackground();
                        Toast.makeText(getApplicationContext(),
                                "Request Sent!",
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(this, SearchPeople.class);
                        i.putExtra("accountID", accountID);
                        i.putExtra("name", name);
                        startActivity(i);
                        break;
                    }
            }
        }
    }