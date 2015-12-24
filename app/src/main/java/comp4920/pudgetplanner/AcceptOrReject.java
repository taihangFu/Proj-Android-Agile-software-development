package comp4920.pudgetplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Zaw on 23/10/2015.
 */


public class AcceptOrReject extends AppCompatActivity implements View.OnClickListener {

    private Button btnAccept;
    private Button btnReject;
    private String accountID;
    private Toolbar toolbar;
    private TextView tvMessage;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_reject);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);
        btnReject = (Button) findViewById(R.id.btnReject);
        btnReject.setOnClickListener(this);
        Intent i = getIntent();

        accountID = i.getStringExtra("accountID");
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        tvMessage.setText(i.getStringExtra("message"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAccept:

                ParseQuery<ParseObject> query = ParseQuery.getQuery("budgetAccount");
                query.whereEqualTo("objectId", accountID);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> accountList, com.parse.ParseException e) {
                        if (e == null) {
                            if (accountList.size() > 0) {
                                for (int i = 0; i < accountList.size(); i++) {
                                    ParseObject p = accountList.get(i);
                                    ParseUser currentUser = ParseUser.getCurrentUser();
                                    p.add("username",currentUser);
                                    p.saveInBackground();
                                }
                            }
                        } else {

                        }
                    }
                });
                Toast.makeText(getApplicationContext(),
                        "Accepted!",
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent(AcceptOrReject.this, LoginSuccessful.class);
                startActivity(i);
                break;
            case R.id.btnReject:
                Toast.makeText(getApplicationContext(),
                        "Rejected!",
                        Toast.LENGTH_LONG).show();
                i = new Intent(AcceptOrReject.this, LoginSuccessful.class);
                startActivity(i);
                break;
        }
    }
}