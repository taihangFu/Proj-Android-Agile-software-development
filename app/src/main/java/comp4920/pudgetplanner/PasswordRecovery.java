package comp4920.pudgetplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
 * Created by Zaw on 22/09/15.
 */
public class PasswordRecovery extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUserEmail;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.password_recovery);
        editTextUserEmail = (EditText) findViewById(R.id.email);
        submitButton = (Button) findViewById(R.id.Button02);
        submitButton.setOnClickListener(this);

    }
        @Override
        public void onClick(View view) {
        String userEmail = editTextUserEmail.getText().toString().trim();

        if (!isValid(userEmail)) {
            editTextUserEmail.setError("Invalid Email Format");
        } else {
            editTextUserEmail.setText("");
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.logOut();

            ParseUser.requestPasswordResetInBackground(userEmail, new RequestPasswordResetCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // An email was successfully sent with reset instructions.
                        Intent i = new Intent(PasswordRecovery.this, Login.class);
                        Toast.makeText(getApplicationContext(),
                                "A link to change password is successfully sent to your email!",
                                Toast.LENGTH_LONG).show();
                        startActivity(i);
                    } else {
                        // Something went wrong. Look at the ParseException to see what's up.
                        Toast.makeText(getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

            }
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_failed, menu);
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

    private boolean isValid(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


}