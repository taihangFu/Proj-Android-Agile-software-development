package comp4920.pudgetplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Seansdf on 10/20/2015.
 */
public class Receiver extends ParsePushBroadcastReceiver {
    String message;

    @Override
    public void onPushOpen(Context context, Intent intent) {
//        System.out.println("bbbbbbbbb fdsfsdf");
        JSONObject json = null;
//        try {
//            json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
//            System.out.println("bbbbbbbbb" + json.getString("objectID"));
//        } catch (JSONException e) {
//            System.out.println("bbbbbbbbb eeeeee");
//            e.printStackTrace();
//
//        }

        Bundle mBundle = intent.getExtras();
        String accountID = "";
        if (mBundle != null) {
            String mData = mBundle.getString("com.parse.Data");
            try {
                json = new JSONObject(mData);
                accountID = json.getString("objectID");
                message = json.getString("alert");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
        }


        Intent i = new Intent(context, AcceptOrReject.class);

//        try {
//            json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
//            i.putExtra("objectID", json.getString("objectID"));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }



        //i.putExtras(intent.getExtras());
        i.putExtra("accountID",accountID);
        i.putExtra("message",message);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}