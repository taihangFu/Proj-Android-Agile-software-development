package comp4920.pudgetplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchPeople extends AppCompatActivity implements View.OnClickListener {

    // Declare Variables
    ListView list;
    PeopleAdapter adapter;
    EditText editsearch;
    String[] userList;
    String name;
String accountID;
    ArrayList<User> arraylist = new ArrayList<User>();
    List<ParseUser> ob;
    private Toolbar toolbar;
    private Button cancelButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_people_search);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        cancelButton = (Button) findViewById(R.id.button7);
        cancelButton.setOnClickListener(this);


        Intent i = getIntent();
        accountID = i.getStringExtra("accountID");
        name = i.getStringExtra("name");
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        query.whereEqualTo("emailVerified", true);
        try {
            ob = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (ParseUser u : ob) {
            User user = new User(u.getUsername());
            arraylist.add(user);
        }

        list = (ListView) findViewById(R.id.listview);
      
        // Pass results to ListViewAdapter Class
        adapter = new PeopleAdapter(this, arraylist,name,accountID);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (EditText) findViewById(R.id.search);

        // Capture Text in EditText
            editsearch.addTextChangedListener(new

            TextWatcher() {

                @Override
                public void afterTextChanged (Editable arg0){
                    // TODO Auto-generated method stub
                    String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

                @Override
                public void beforeTextChanged (CharSequence arg0,int arg1,
                int arg2, int arg3){
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged (CharSequence arg0,int arg1, int arg2,
                int arg3){
                    // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button7:
                Intent i = new Intent(this, SingleAccountView.class);
                i.putExtra("accountID", accountID);
                i.putExtra("name", name);
                startActivity(i);
                break;
        }
    }
}
