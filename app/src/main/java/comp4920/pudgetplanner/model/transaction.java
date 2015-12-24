package comp4920.pudgetplanner.model;

/**
 * Created by Zaw on 14/10/2015.
 */
public class transaction {
    String name;
    int amount;
    String category;
    String date;
    String type;

    public transaction(){
        name = "";
        amount = 0;
        category = "";
        date = "";
        type = "";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
