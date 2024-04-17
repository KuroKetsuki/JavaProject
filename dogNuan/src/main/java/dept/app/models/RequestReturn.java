package dept.app.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestReturn {
    private String nameBorrower;
    private String nameDurable;
    private String location;
    private String timeRequest;
    private String permission;


    public RequestReturn(String nameBorrower, String nameDurable, String location, String timeRequest, String permission) {
        this.nameBorrower = nameBorrower;
        this.nameDurable = nameDurable;
        this.location = location;
        this.timeRequest = timeRequest;
        this.permission = permission;
    }
    public String getTimeRequest() { return timeRequest; }
    public String setTimeRequest() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timeRequest = dateTime.format(formatter);
        return timeRequest;
    }

    public String getNameBorrower() {
        return nameBorrower;
    }

    public String getNameDurable() { return  nameDurable; }

    public String getLocation() {
        return location;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isName(String name) {
        return this.nameBorrower.equals(name);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + nameBorrower + '\'' +
                ", nameDurable='" + nameDurable + '\'' +
                ", location='" + location + '\'' +
                ", timeRequest=" + timeRequest +
                ", permission='" + permission + '\'' +
                '}';
    }
}
