package comp4920.pudgetplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import java.util.HashMap;


public class Login extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin,btnRegister;
    EditText editTextUserPassword;
    EditText editTextUserEmail;
    TextView textViewforgetPassword;
    HashMap<String,String> hmUsers = new HashMap<String,String>();

    @Override
    //When the user enters login22
    protected void onCreate(Bundle savedInstanceState) {
        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.signIn);
        editTextUserEmail = (EditText) findViewById(R.id.email);
        editTextUserPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.register);
        textViewforgetPassword = (TextView) findViewById(R.id.forgetPassword);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        textViewforgetPassword.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.signIn:
                String userEmail = editTextUserEmail.getText().toString().trim();
                String password = editTextUserPassword.getText().toString().trim();

                    ParseUser.logInInBackground(userEmail, password, new LogInCallback() {
                        public void done(ParseUser userEmail, ParseException e) {
                            if (userEmail != null) {

                                if (userEmail.getBoolean("emailVerified") == true) {
                                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                                    installation.put("username",ParseUser.getCurrentUser().getUsername());
                                    installation.saveInBackground();

                                    Intent i = new Intent(Login.this, LoginSuccessful.class);
                                    startActivity(i);
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Logged in",
                                            Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            "Please verify your email!",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {

                                Toast.makeText(getApplicationContext(),
                                        "Invalid username and password",
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                break;
            case R.id.register:
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
                break;
            case R.id.forgetPassword:
                i = new Intent(Login.this,PasswordRecovery.class);
                startActivity(i);
                break;
        }
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
        if(password.length() != 0 && password.length() > 1) {
            return true;
        }
        return false;
    }
}

