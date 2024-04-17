package dept.app.services;

import dept.app.models.User;

import java.util.Comparator;

public class SortTimeUser implements Comparator<User> {
    private boolean time;
    public void setDescending(boolean isDescending) {
        this.time = isDescending;
    }
    /** true max to low | false low to max **/
    @Override
    public int compare(User account1, User account2) {
        return (account2.getTimeLogin().compareTo(account1.getTimeLogin())) * (time ? 1 : -1);
    }
}
