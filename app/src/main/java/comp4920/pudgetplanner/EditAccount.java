package comp4920.pudgetplanner;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by Zaw on 14/10/2015.
 */
public class EditAccount extends AppCompatActivity implements View.OnClickListener {
    EditText editTextAccountName;
    EditText editTextAccountType;
    EditText editTextStartingBalance;
    Button btnEditAccount;
    String name;
    String accountID;
    private ListView listView;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account);

        editTextAccountName = (EditText) findViewById(R.id.etAccountName);
        editTextAccountType = (EditText) findViewById(R.id.etAccountType);
        editTextStartingBalance = (EditText) findViewById(R.id.etStartingBalance);
        btnEditAccount = (Button) findViewById(R.id.btnEditAccount);
        btnEditAccount.setOnClickListener(this);
        editTextAccountType.setOnClickListener(this);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        accountID = i.getStringExtra("accountID");
        cancel = (Button) findViewById(R.id.button8);
        cancel.setOnClickListener(this);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("budgetAccount");
        query.whereEqualTo("objectId", accountID);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> accountList, com.parse.ParseException e) {
                if (e == null) {
                    if (accountList.size() > 0) {
                        for (int i = 0; i < accountList.size(); i++) {
                            ParseObject p = accountList.get(i);
                            editTextAccountName.setText(p.getString("accountName"));
                            editTextAccountType.setText(p.getString("accountType"));
                            editTextStartingBalance.setText(Integer.toString(p.getInt("startingBalance")));
                        }
                    }
                } else {

                }
            }
        });
    }


    private void searchIncomeExpense() {
        final Dialog dialog = new Dialog(EditAccount.this);
        dialog.setContentView(R.layout.income_expense_category);
        dialog.setTitle("Select Account Type");
        listView = (ListView) dialog.findViewById(R.id.list);
        String[] values;

        values = new String[]{"Asset", "Cash", "Cheque", "Credit Card", "Debit Card", "Investing",
                "Loan", "Others", "Savings"};

        dialog.show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView
                        .getItemAtPosition(position);
                editTextAccountType.setText(itemValue);
                // Show Alert
//                Toast.makeText(
//                        getApplicationContext(),
//                        "Position :" + itemPosition + "  ListItem : "
//                                + itemValue, Toast.LENGTH_LONG).show();
                dialog.cancel();

            }

        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button8:
                Intent i = new Intent(this, Account.class);
                startActivity(i);
                break;
            case R.id.etAccountType:
                searchIncomeExpense();
                break;
            case R.id.btnEditAccount:
                String accountName = editTextAccountName.getText().toString().trim();
                String accountType = editTextAccountType.getText().toString().trim();

                int startingBalance = 0;
                if (editTextStartingBalance.getText().toString().trim().equals("")) {
                    startingBalance = 0;
                } else {
                    startingBalance = Integer.parseInt(editTextStartingBalance.getText().toString().trim());
                }
                if (isValidName(accountName) && isValidName(accountType)) {
                    // Create a pointer to an object of class Point with id dlkj83d
                    ParseObject point = ParseObject.createWithoutData("budgetAccount", accountID);

// Set a new value on quantity
                    int balance = 0;
                    int oldBalance = 0;
                    try {
                        balance = point.fetchIfNeeded().getInt("balance");
                        oldBalance = point.fetchIfNeeded().getInt("startingBalance");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    balance = startingBalance - oldBalance + balance;

                    point.put("accountName", accountName);
                    point.put("accountType", accountType);
                    point.put("iconPath", accountType);
                    point.put("startingBalance", startingBalance);
                    point.put("balance", balance);
// Save
                    point.saveInBackground(new SaveCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(),
                                        "Edited!",
                                        Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditAccount.this, Account.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Error!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please check all input!",
                            Toast.LENGTH_LONG).show();
                }
                    break;

        }
    }
    private boolean isValidName(String s) {
        if (TextUtils.isEmpty(s)) {
            return false;
        }
        return true;
    }
}
