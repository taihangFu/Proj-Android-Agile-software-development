package comp4920.pudgetplanner.model;

/**
 * Created by Seansdf on 10/12/2015.
 */
public class AccountModel {

        private int icon;
        private String title;
        private Integer balance;
        private String balanceType;

        //private boolean isGroupHeader = false;



    //        public AccountModel(String title) {
//            this(-1,title,null);
//            isGroupHeader = true;
//        }
        public AccountModel(int icon, String title, Integer balance, String balanceType) {
            super();
            this.icon = icon;
            this.title = title;
            this.balance = balance;
            this.balanceType = balanceType;
        }
        public int getIcon() {
            return icon;
        }
        public void setIcon(int icon) {
            this.icon = icon;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getBalance() {
            return balance.toString();
        }
        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public String getBalanceType() {
            return balanceType;
        }

        public void setBalanceType(String balanceType) {
            this.balanceType = balanceType;
        }


//        public boolean isGroupHeader() {
//            return isGroupHeader;
//        }
//        public void setGroupHeader(boolean isGroupHeader) {
//            this.isGroupHeader = isGroupHeader;
//        }





}
