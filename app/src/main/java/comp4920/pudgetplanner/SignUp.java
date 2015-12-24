package comp4920.pudgetplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUp extends AppCompatActivity implements View.OnClickListener{
    Button btnSingUp;
    EditText editTextUserPassword;
    EditText editTextName;
    EditText editTextUserEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSingUp = (Button) findViewById(R.id.Button02);
        editTextName = (EditText) findViewById(R.id.name);

        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.logOut();

        editTextUserPassword = (EditText) findViewById(R.id.password);
        editTextUserEmail = (EditText) findViewById(R.id.email);
        btnSingUp.setOnClickListener(this);
    }
    @Override
    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.Button02:
                String userName = editTextName.getText().toString().trim();
                String userEmail = editTextUserEmail.getText().toString().trim();
                String password = editTextUserPassword.getText().toString().trim();

                //HashMap<String, String> hmUsers = new HashMap<String,String>();
                //hmUsers = dbhelper.getUserDetails();

                boolean isUserExist = false;

                if (!isValid(userEmail)) {
                    editTextUserEmail.setError("Invalid Email");
                }else if(!isValidName(userName)){
                    editTextName.setError("Invalid Username");
                }else if (!isValidPW(password)) {
                    editTextUserPassword.setError("Password cannot be empty");
                }else {
                        // Create the ParseUser
                        ParseUser user = new ParseUser();
                        // Set core properties
                        user.setUsername(userName);
                        user.setPassword(password);
                        user.setEmail(userEmail);
                        // Invoke signUpInBackground
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Intent i = new Intent(SignUp.this, Login.class);
                                    Toast.makeText(getApplicationContext(),
                                            "Please verify email!",
                                            Toast.LENGTH_LONG).show();
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            e.getMessage(),
                                            Toast.LENGTH_LONG).show();
//                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUp.this);
//                                    alertDialogBuilder.setMessage(e.getMessage());
//                                    alertDialogBuilder.setPositiveButton("Ok", null);
//                                    alertDialogBuilder.show();
                                }
                            }
                        });
                    editTextUserEmail.setText("");
                    editTextName.setText("");
                    editTextUserPassword.setText("");
                }
                break;
        }
    }

    private boolean isValidName(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        return true;
    }

    private boolean isValid(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    //MINIMUM PASSWORD LENGTH = 9
    private boolean isValidPW(String password) {
        if(password.length() != 0) {
            return true;
        }
        return false;
    }
}