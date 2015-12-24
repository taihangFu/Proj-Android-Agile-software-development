package comp4920.pudgetplanner;

/**
 * Created by Seansdf on 10/10/2015.
 */
import android.app.Application;
import com.parse.Parse;
import com.parse.ParseInstallation;

public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "lNYZ1XBsZ3QjTVUAdAl0Lv8no0jybZfUbDWYFFN8", "hviqu3KyYy4tXmAdscBAU3drRodVyuYhNwWJWxLb");
        //Parse.initialize(this, "jkS9EcGrDrUD7FQ2OSd5VA2XguU6ft5gIeAMB25I", "GmmaQEqMLfs6vAOJgG7XDzYcI5DSIzVHJwMzudvJ");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}