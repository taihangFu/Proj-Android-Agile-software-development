package comp4920.pudgetplanner;

/**
 * Created by Seansdf on 10/14/2015.
 */
public class SingleAccountViewModel {

private int icon;
private String transection;
private String date;
private Integer amount;

    //private boolean isGroupHeader = false;



    //        public AccountModel(String title) {
//            this(-1,title,null);
//            isGroupHeader = true;
//        }

    public SingleAccountViewModel(int icon, String transection, String date, Integer amount) {
        super();
        this.icon = icon;
        this.transection = transection;
        this.date = date;
        this.amount = amount;
    }
    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getAmount() {
        return amount.toString();
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTransection() {
        return transection;
    }

    public void setTransection(String transection) {
        this.transection = transection;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    //        public boolean isGroupHeader() {
//            return isGroupHeader;
//        }
//        public void setGroupHeader(boolean isGroupHeader) {
//            this.isGroupHeader = isGroupHeader;
//        }

}



