package dept.app.models;

import javafx.scene.image.Image;

import java.text.spi.DateFormatProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private String name;
    private String username;
    private String password;
    private String role;
    private String imagePath;
    private String timeLogin;
    private Image image;

    public User(String role, String name , String username, String password, String imagePath) {
        this.role = role;
        this.name = name;
        this.username = username;
        this.password = password;
        this.imagePath = imagePath;
        //imagePath = "/image/default.jpg";
    }

    public User(String role, String name , String username, String password, String imagePath, String timeLogin) {
        this.role = role;
        this.name = name;
        this.username = username;
        this.password = password;
        this.imagePath = imagePath;
        this.timeLogin = timeLogin;
        //imagePath = "/image/default.jpg";
    }

    public String getTimeLogin() {
        return this.timeLogin;
    }

    public String getRole() { return role; }

    public String getName() {return name; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //    public Image getImagePath() {
//        return new Image(getClass().getResource(imagePath).toString());
//        //return new Image(getClass().getResource("/image/default.jpg").toString());
//    }
    public String getImagePath(){
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTimeLogin() {
        LocalDateTime timeLogin = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timeLogin = timeLogin.format(formatter);
    }

    public Image getImage() {
        return new Image("file:" + imagePath);
    }

    /*public void setImage(Image image) {
        this.image = image;
    }*/

    /* ใช้เปลี่ยน password */
    public boolean changePassword(String password) {
        if (!password.trim().equals("")) {
            this.password = password.trim();
            return true;
        } else {
            return false;
        }
    }

    /* ใช้ตรวจสอบ name */
    public Boolean isName(String name) {
        return this.name.equals(name);
    }
    /* ใช้ตรวจสอบ username */
    public Boolean isUsername(String username) {
        return this.username.equals(username);
    }
    /* ใช้ตรวจสอบ password */
    public Boolean isPassword(String password) {
        return this.password.equals(password);
    }
}
