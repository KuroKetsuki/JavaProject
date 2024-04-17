package dept.app.models;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class UserList {
    public ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public void addNewAccount(User user) {
        users.add(user);
    }

    public void addAccount(String role, String name, String username, String password, String imagePath) {
        LocalDateTime timeLogin = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        role = role.trim();
        name = name.trim();
        username = username.trim();
        password = password.trim();
        imagePath = imagePath.trim();
        String formattedTimeLogin = timeLogin.format(formatter);

        if (!name.equals("") && !username.equals("") && !password.equals("") && !role.equals("")) {
            User exist = findAccountByUsername(username);
            if (exist == null) {
                users.add(new User(role, name, username, password, imagePath, formattedTimeLogin));
            }
        }
    }

    public User findAccountByUsername(String username) {
        for (User user : users) {
            if (user.isUsername(username)) {
                return user;
            }
        }
        return null;
    }

    public User findUserByName(String name) {
        for (User user : users) {
            if (user.isName(name)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    /*public void sort(Comparator<User> cmp){
        Collections.sort(users, cmp);
    }*/
}
