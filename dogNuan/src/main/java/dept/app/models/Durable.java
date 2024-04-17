package dept.app.models;

import javafx.scene.image.Image;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Durable extends Object{
    private String datepurchase;
    private String status;
    private String location;
    private String borrower;
    private String borrowdate;
    private String imagePath;

    public Durable(String category, String id, String name, String status, String imagePath, String location, String datepurchase, String borrower, String borrowdate) {
        super(category, name, id);
        this.datepurchase = datepurchase;
        this.status = status;
        this.location = location;
        this.borrower = borrower;
        this.borrowdate = borrowdate;
        this.imagePath = imagePath;
    }

    public String getDatepurchase() {
        return datepurchase;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public String getBorrower() {
        return borrower;
    }

    public String getBorrowdate() {
        return borrowdate;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }


    public String setDatepurchase() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.datepurchase = dateTime.format(formatter);
        return datepurchase;
    }

    public String setBorrowdate() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.borrowdate = dateTime.format(formatter);
        return borrowdate;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addBorrower(String borrower) {
        if (borrower != null) {
            this.borrower = borrower;
        }
    }

    public boolean isBorrower(String borrower) {
        return this.borrower.equals(borrower);
    }

    public boolean isLocation(String location) {
        return this.location.equals(location);
    }

    @Override
    public String toString() {
        return "Durable{" +
                "datepurchase='" + datepurchase + '\'' +
                ", status='" + status + '\'' +
                ", location='" + location + '\'' +
                ", borrower='" + borrower + '\'' +
                ", borrowdate='" + borrowdate + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
