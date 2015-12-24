package comp4920.pudgetplanner.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zaw on 11/10/2015.
 */
public class account {
    private String accountID;
    private String accountName;
    private ArrayList<String> users;
    private HashMap<String,HashMap<String,Integer>> incomes;
    private HashMap<String,HashMap<String,Integer>> expenses;

    public account(){
        accountID = "";
        accountName = "";
        users = new ArrayList<String>();
        incomes = new HashMap<String,HashMap<String,Integer>>();
        //username, <id - name of income, amount > - boolean incur - date

        expenses = new HashMap<String,HashMap<String,Integer>>();
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public HashMap<String, HashMap<String, Integer>> getIncomes() {
        return incomes;
    }

    public void setIncomes(HashMap<String, HashMap<String, Integer>> incomes) {
        this.incomes = incomes;
    }

    public HashMap<String, HashMap<String, Integer>> getExpenses() {
        return expenses;
    }

    public void setExpenses(HashMap<String, HashMap<String, Integer>> expenses) {
        this.expenses = expenses;
    }
}
