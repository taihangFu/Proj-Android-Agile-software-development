package comp4920.pudgetplanner;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Zaw on 11/10/2015.
 */
public class AddAccount extends AppCompatActivity implements View.OnClickListener {

    EditText editTextAccountName;
    EditText editTextAccountType;
    EditText editTextStartingBalance;
    Button btnAddAccount;
    Button cancelButton;
    private Toolbar toolbar;

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        editTextAccountName = (EditText) findViewById(R.id.etAccountName);
        editTextAccountType = (EditText) findViewById(R.id.etAccountType);
        editTextStartingBalance = (EditText) findViewById(R.id.etStartingBalance);
        btnAddAccount = (Button) findViewById(R.id.btnAddAccount);
        btnAddAccount.setOnClickListener(this);
        editTextAccountType.setOnClickListener(this);

        cancelButton = (Button) findViewById(R.id.button5);
        cancelButton.setOnClickListener(this);
    }

    private void searchIncomeExpense() {
        final Dialog dialog = new Dialog(AddAccount.this);
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
                dialog.cancel();

            }

        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etAccountType:
                searchIncomeExpense();
                break;
            case R.id.btnAddAccount:
                String accountName = editTextAccountName.getText().toString().trim();
                String accountType = editTextAccountType.getText().toString().trim();
                int startingBalance = 0;
                if (editTextStartingBalance.getText().toString().trim().equals("")) {
                    startingBalance = 0;
                } else {
                    startingBalance = Integer.parseInt(editTextStartingBalance.getText().toString().trim());
                }
                if (isValidName(accountName) && isValidName(accountType)) {
                    ParseObject account = new ParseObject("budgetAccount");
                    account.put("accountName", accountName);
                    account.put("accountType", accountType);
                    account.put("iconPath", accountType);
                    account.put("startingBalance", startingBalance);
                    account.put("balance", startingBalance);
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    if (currentUser != null) {
                        account.add("username", currentUser);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "No Current User!",
                                Toast.LENGTH_LONG).show();
                    }
                    account.saveInBackground();
                    Toast.makeText(getApplicationContext(),
                            "Added!",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AddAccount.this, Account.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please check all input!",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button5:
                Intent i = new Intent(AddAccount.this, Account.class);
                startActivity(i);
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
