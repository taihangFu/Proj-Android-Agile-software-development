package comp4920.pudgetplanner.model;

/**
 * Created by Zaw on 12/10/2015.
 */
public class income {
    String name;
    int amount;
    String category;
    String date;


    public income(){
        name = "";
        amount = 0;
        category = "";
        date = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
