package comp4920.pudgetplanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by michellephan on 30/09/2015.
 */
public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText editTextPassword;
    private Button confirmButton;
    private EditText firstTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigation_drawer drawer = (navigation_drawer)
                getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        editTextPassword = (EditText) findViewById(R.id.editText2);
        firstTextPassword = (EditText) findViewById(R.id.editText);
        confirmButton = (Button) findViewById(R.id.button3);
        confirmButton.setOnClickListener(this);



    }
    @Override
    public void onClick(View v) {
        String password = firstTextPassword.getText().toString().trim();
        if (!isValidPW(password)) {
            firstTextPassword.setError("Password Cannot be empty");

        } else if (editTextPassword.getText().toString().trim().equals("") && firstTextPassword.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter a new password. ",
                    Toast.LENGTH_LONG).show();

        } else if (editTextPassword.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please confirm your new password. ",
                    Toast.LENGTH_LONG).show();

        }
        else if (editTextPassword.getText().toString().equals(firstTextPassword.getText().toString())) {

            ParseUser parseUser = ParseUser.getCurrentUser();
            parseUser.setPassword(editTextPassword.getText().toString());
            parseUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (null == e) {
                        // report about success
                        Toast.makeText(getApplicationContext(),
                                "Password Successfully changed!",
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ChangePassword.this, LoginSuccessful.class);
                        startActivity(i);
                    } else {
                        // report about error
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(),
                    "Two entered input fields are not the same.",
                    Toast.LENGTH_LONG).show();
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
    boolean doubleBackToExitPressedOnce = false;

    private boolean isValidPW(String password) {
        if(password.length() != 0) {
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}