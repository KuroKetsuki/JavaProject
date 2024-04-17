package dept.app.controllers.user;

import dept.app.models.*;
import dept.app.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DurableRequestBorrow {
    @FXML
    private Label borrowerLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label dateborrowLabel;
    @FXML
    private Label datepurchaseLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private ImageView picPath;
    @FXML
    private TextField addLocation;

    private Datasource<DurableList> datasource;
    private Datasource<RequestBorrowList> allRequest;
    private Datasource<UserList> allUser;
    private DurableList durableList;
    private Durable durable;
    private UserList userList;
    private User user;
    private RequestBorrowList requestBorrowList;
    private RequestBorrow requestBorrow;
    private List<String> filteredUserList;

    @FXML
    public void initialize() {
        datasource = new DurableListFileDatasource("data", "Durable_articles.csv");
        durableList = datasource.readData();

        allRequest = new BorrowListFileDatasource("data", "RequestBorrow.csv");
        requestBorrowList = allRequest.readData();

        allUser = new UserFileDatasource("data", "User.csv");
        userList = allUser.readData();

        durable = (Durable) FXRouter.getData();
        String durableId = durable.getId();
        durable = durableList.findDurableById(durableId);

        String username = (String) FXRouter.getData2();
        user = userList.findAccountByUsername(username);

//        user = (User) FXRouter.getData2();
//        String username = user.getUsername();
//        user = userList.findAccountByUsername(username);

        showDurable(durable);
        errorLabel.setText("");
    }

    private void showDurable(Durable newValue) {
        durable = newValue;
        categoryLabel.setText(durable.getCategory());
        idLabel.setText(durable.getId());
        nameLabel.setText(durable.getName());
        statusLabel.setText(durable.getStatus());
        locationLabel.setText(durable.getLocation());
        datepurchaseLabel.setText(durable.getDatepurchase());
        borrowerLabel.setText(durable.getBorrower());
        dateborrowLabel.setText(durable.getBorrowdate());
        picPath.setImage(new Image("file:" + durable.getImagePath(), true));
//        File picDir = new File(selectedDurable.getImagePath());
//        Image image = new Image(String.valueOf(picDir.toURI()), 500, 0, true, true);
//        picPath.setImage(image);
    }

    private void clearDurable() {
        categoryLabel.setText("");
        idLabel.setText("");
        nameLabel.setText("");
        statusLabel.setText("");
        locationLabel.setText("");
        datepurchaseLabel.setText("");
        borrowerLabel.setText("");
        dateborrowLabel.setText("");
        picPath.setImage(new Image(getClass().getResource("/image/default.jpg").toExternalForm()));
    }

    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("user_durablelist");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void handleRequestBorrowButton() {
        String status = durable.getStatus().trim();
        String borrower = durable.getBorrower().trim();
        String nameBorrower = user.getName().trim();
        String nameDurable = durable.getName().trim();
        String location = addLocation.getText().trim();
//        String timeRequest = requestBorrow.setTimeRequest();
        String timeRequest = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        filteredUserList = new ArrayList<>();
        for (User user : userList.getUsers()) {
            if (user.getRole().equals("officer")) {
                filteredUserList.add(user.getName());
            }
        }
        System.out.println(filteredUserList);
        if (!status.equals("Sold")) {
            if(!status.equals("Donated")){
                for (int i = 0; i < filteredUserList.size(); i++) {
                    if (borrower.equals(filteredUserList.get(i))) {
                        try {
                            RequestBorrow requestBorrow = new RequestBorrow(nameBorrower, nameDurable, location, timeRequest, "Waiting for approval");
                            requestBorrowList.addNewBorrow(requestBorrow);
                            durable.setBorrower("In progress");
                            durable.setBorrowdate();
                            datasource.writeData(durableList);
                            allRequest.writeData(requestBorrowList);
                            showDurable(durable);
                            FXRouter.goTo("user_borrowdurable");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else errorLabel.setText("Durable Article is already borrowed.");
                }
            } else errorLabel.setText("Item cannot be use.");
        } else errorLabel.setText("Item cannot be use.");
    }

}