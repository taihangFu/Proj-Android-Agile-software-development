package comp4920.pudgetplanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp4920.pudgetplanner.model.transaction;


/**
 * Created by Zaw on 12/10/2015.
 */
public class AddIncomeExpense extends AppCompatActivity implements OnClickListener {
    private RadioGroup radioIncomeExpenseGroup;
    private RadioButton radioIncomeExpenseButton;
    private EditText editTextName;
    private EditText editTextAmount;
    private EditText editTextCategory;
    private EditText editTextDate;
    private Button btnAdd;
    private String name;
    private String accountID;
    private ListView listView;
    private Toolbar toolbar;
    private Button cancelButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_income_expense);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        editTextName = (EditText) findViewById(R.id.etNameOfTransaction);
        editTextAmount = (EditText) findViewById(R.id.etAmount);
        editTextCategory = (EditText) findViewById(R.id.etCategory);
        editTextDate = (EditText) findViewById(R.id.etDate);
        radioIncomeExpenseGroup = (RadioGroup) findViewById(R.id.radioIncomeOrExpense);
        Intent i = getIntent();
        accountID = i.getStringExtra("accountID");
        // Get the name
        name = i.getStringExtra("name");


        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        editTextCategory.setOnClickListener(this);
        editTextDate.setOnClickListener(this);

        cancelButton = (Button) findViewById(R.id.button6);
        cancelButton.setOnClickListener(this);
    }


    private void searchIncomeExpense(String type) {
        final Dialog dialog = new Dialog(AddIncomeExpense.this);
        dialog.setContentView(R.layout.income_expense_category);
        dialog.setTitle("Select Category");
        listView = (ListView) dialog.findViewById(R.id.list);
        String[] values;
        if(type.equals("Income")){
             values = new String[]{"Bonus","Others", "Salary", "Saving Deposit", "Tax Refund"};
        }else {
            values = new String[]{"Auto", "Bank Charge", "Cash", "Charity", "Childcare", "Clothing",
                    "Credit Card Payment", "Eating Out", "Education", "Entertainment", "Gifts", "Groceries",
                    "Health And Fitness", "Household", "Home Repair", "Interest Exp", "Loan", "Medical",
                    "Misc", "Mortgage Payment", "Others", "Pets", "Rent", "Tax","Transport","Travel",
                     "Telephone","Utilities","Interent"};
        }
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
                editTextCategory.setText(itemValue);

                dialog.cancel();

            }

        });
    }
    @Override
    public void onClick(View view) {

            switch (view.getId()) {
                case R.id.button6:
                    Intent i = new Intent(this, SingleAccountView.class);
                    i.putExtra("accountID", accountID);
                    i.putExtra("name", name);
                    startActivity(i);
                    break;
                case R.id.etCategory:
                    int selectedId = radioIncomeExpenseGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioIncomeExpenseButton = (RadioButton) findViewById(selectedId);
                    if (radioIncomeExpenseButton.getText().equals("Income")) {
                        searchIncomeExpense("Income");
                    } else {

                        searchIncomeExpense("Expense");
                    }

                    break;
                case R.id.etDate:
                    Calendar mcurrentDate=Calendar.getInstance();
                   int mYear=mcurrentDate.get(Calendar.YEAR);
                    int  mMonth=mcurrentDate.get(Calendar.MONTH);
                    int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker=new DatePickerDialog(AddIncomeExpense.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                       editTextDate.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                },mYear, mMonth, mDay);
                    mDatePicker.setTitle("Select date");
                    mDatePicker.show();
                    break;
                case R.id.btnAdd:


                            // get selected radio button from radioGroup
                            selectedId = radioIncomeExpenseGroup.getCheckedRadioButtonId();

                            // find the radiobutton by returned id
                            radioIncomeExpenseButton = (RadioButton) findViewById(selectedId);

//                Toast.makeText(getApplicationContext(),
//                        radioIncomeExpenseButton.getText(),
//                        Toast.LENGTH_LONG).show();

                            String nameOfTransaction = editTextName.getText().toString().trim();
                            int amount = 0;
                            if (!editTextAmount.getText().toString().trim().equals("")) {
                                amount = Integer.parseInt(editTextAmount.getText().toString().trim());
                            }
                            String category = editTextCategory.getText().toString().trim();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = new Date();
                            try {
                                date = formatter.parse(editTextDate.getText().toString().trim());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            final transaction t = new transaction();
                            if (radioIncomeExpenseButton.getText().equals("Income")) {
                                t.setType("income");
                            } else {
                                t.setType("expense");
                            }

                            t.setName(nameOfTransaction);
                            t.setAmount(amount);
                            t.setCategory(category);
                            t.setDate(formatter.format(date));
                    if (isValidName(nameOfTransaction) && isValidName(category)&&
                            !editTextAmount.getText().toString().trim().equals("") && !editTextDate.getText().toString().trim().equals("")) {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("budgetAccount");
                        query.whereEqualTo("objectId", accountID);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> accountList, com.parse.ParseException e) {
                                if (e == null) {
                                    if (accountList.size() > 0) {
                                        for (int i = 0; i < accountList.size(); i++) {
                                            JSONObject transaction = new JSONObject();
                                            try {
                                                transaction.put("name", t.getName());
                                                transaction.put("amount", t.getAmount());
                                                transaction.put("category", t.getCategory());
                                                transaction.put("date", t.getDate());
                                                transaction.put("type", t.getType());
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }
                                            ParseObject p = accountList.get(i);
                                            int balance = p.getInt("balance");
                                            balance = balance + t.getAmount();
                                            p.add("transactions", transaction);
                                            p.put("balance", balance);
                                            p.saveEventually();
                                            Toast.makeText(getApplicationContext(),
                                                    "Added!",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                        Intent i = new Intent(AddIncomeExpense.this, SingleAccountView.class);
                                        i.putExtra("accountID", accountID);
                                        i.putExtra("name", name);
                                        startActivity(i);
                                    }
                                    // row of Object Id "U8mCwTHOaC"
                                } else {
                                    // error
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
