package comp4920.pudgetplanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginSuccessful extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Button btnAddAccount;

    List<ParseObject> ob = null;
    Spinner spinUser;
    ArrayList<String> array_spinner = new ArrayList<String>();
    HashMap<Integer,JSONArray> hmTransactions = new HashMap<Integer,JSONArray>();
    int pos = 0;

    Button btnIncomeReport;
    Button btnExpenseReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_successful);

        btnIncomeReport = (Button) findViewById(R.id.btnIncomeReport);
        btnExpenseReport = (Button) findViewById(R.id.btnExpenseReport);
        btnIncomeReport.setOnClickListener(this);
        btnExpenseReport.setOnClickListener(this);

        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinUser= (Spinner) findViewById(R.id.spinner);//fetch the spinner from layout file

        navigation_drawer drawer = (navigation_drawer)
                getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("budgetAccount");

        ParseUser currentUser = ParseUser.getCurrentUser();
        ArrayList<ParseUser> c = new ArrayList<ParseUser>();
        c.add(currentUser);
        query.whereContainedIn("username",c);
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error   ------- ", e.getMessage());
            e.printStackTrace();
        }

        for(int i = 0 ; i < ob.size();i++){
            array_spinner.add(ob.get(i).getString("accountName"));
            hmTransactions.put(i, ob.get(i).getJSONArray("transactions"));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, array_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinUser.setAdapter(adapter);

        spinUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
              pos = position;
               // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

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

    public void showPieChart(String type){
        float[] yData = new float[0];
        String[] xData = new String[0];

        int total = 0;
        JSONArray jsonArrayTransaction = hmTransactions.get(pos);
        if(jsonArrayTransaction!=null){
            HashMap<String, Integer> hmCateg = new HashMap<String, Integer>();
            for (int j = 0; j < jsonArrayTransaction.length(); j++) {

                try {
                    JSONObject jsonObject = jsonArrayTransaction.getJSONObject(j);
                    if (jsonObject.getString("type").equals(type)) {
                        total += jsonObject.getInt("amount");
                        if (hmCateg.containsKey(jsonObject.getString("category"))) {
                            int oldAmount = hmCateg.get(jsonObject.getString("category"));
                            int newAmount = oldAmount + jsonObject.getInt("amount");
                            hmCateg.put(jsonObject.getString("category"), newAmount);
                        } else {
                            hmCateg.put(jsonObject.getString("category"), jsonObject.getInt("amount"));
                        }
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            //put items on the array that ned to be passed to PieChart.class
            yData = new float[hmCateg.size()];
            xData = new String[hmCateg.size()];
            int index = 0;

            for (Map.Entry<String, Integer> entry : hmCateg.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                xData[index] = entry.getKey();
                yData[index] = (float) (entry.getValue() * 1.0 / total);
                index++;
            }
        }
        // float[] yData = {5, 10, 15, 30, 20, 10, 10};
        // String[] xData = {"Sony", "Huawei", "LG", "Apple", "Samsung", "Test", "Test2"};
        //  if (position == 1) {
        //pass the Data array that need to be draw on PieChart
        Bundle b = new Bundle();
        b.putStringArray("xData", xData);
        b.putFloatArray("yData", yData);
        Intent i = new Intent(LoginSuccessful.this, PieChartSummary.class);
        i.putExtras(b);
        i.putExtra("total",total);
        startActivity(i);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIncomeReport:
                showPieChart("income");
              break;
            case R.id.btnExpenseReport:
                showPieChart("expense");
              break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
           // super.onBackPressed();
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
