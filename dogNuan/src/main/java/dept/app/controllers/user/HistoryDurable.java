package dept.app.controllers.user;

import dept.app.models.*;
import dept.app.services.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoryDurable {
    @FXML
    private TableView<RequestBorrow> borrowTable;
    @FXML
    private Label locationBLabel;
    @FXML
    private Label durableBLabel;
    @FXML
    private Label errorLabel;

    private RequestBorrowList requestBorrowList;
    private RequestBorrow requestBorrow;
    private RequestReturnList requestReturnList;
    private RequestReturn requestReturn;
    private UserList userList;
    private User user;
    private List<RequestBorrow> filteredRequestBorrowList;
    private Datasource<RequestBorrowList> allRequest;
    private Datasource<RequestReturnList> allReturn;
    private Datasource<UserList> allUser;
    public void initialize() {
        clearBorrowInfo();

        allRequest = new BorrowListFileDatasource("data", "RequestBorrow.csv");
        requestBorrowList = allRequest.readData();

        allReturn = new ReturnListFileDatasource("data", "RequestReturn.csv");
        requestReturnList = allReturn.readData();

        allUser = new UserFileDatasource("data", "User.csv");
        userList = allUser.readData();

        String username = (String) FXRouter.getData();
        user = userList.findAccountByUsername(username);

        showBorrowTable(requestBorrowList);
        borrowTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RequestBorrow>() {
            @Override
            public void changed(ObservableValue<? extends RequestBorrow> observable, RequestBorrow oldValue, RequestBorrow newValue) {
                if (newValue == null) {
                    clearBorrowInfo();
                    requestBorrow = null;
                } else {
                    showBorrowInfo(newValue);
                    requestBorrow = newValue;
                }
            }
        });
    }
    private void showBorrowInfo(RequestBorrow requestBorrow) {
        locationBLabel.setText(requestBorrow.getLocation());
        durableBLabel.setText(requestBorrow.getNameDurable());
    }
    private void clearBorrowInfo() {
        locationBLabel.setText("-");
        durableBLabel.setText("-");
    }
    private void showBorrowTable(RequestBorrowList requestBorrowList) {
        TableColumn<RequestBorrow, String> borrowerColumn = new TableColumn<>("Borrower Name");
        borrowerColumn.setCellValueFactory(new PropertyValueFactory<>("nameBorrower"));

        TableColumn<RequestBorrow, String> durableColumn = new TableColumn<>("Durables Name");
        durableColumn.setCellValueFactory(new PropertyValueFactory<>("nameDurable"));

        TableColumn<RequestBorrow, String> locationColumn = new TableColumn<>("Storage Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<RequestBorrow, String> timeColumn = new TableColumn<>("Time Request");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeRequest"));

        TableColumn<RequestBorrow, String> permissionColumn = new TableColumn<>("Permission");
        permissionColumn.setCellValueFactory(new PropertyValueFactory<>("permission"));

        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        borrowTable.getColumns().clear();
        borrowTable.getColumns().add(borrowerColumn);
        borrowTable.getColumns().add(durableColumn);
        borrowTable.getColumns().add(locationColumn);
        borrowTable.getColumns().add(timeColumn);
        borrowTable.getColumns().add(permissionColumn);

        filteredRequestBorrowList = new ArrayList<>();
        for (RequestBorrow requestBorrow : requestBorrowList.getRequestBorrows()) {
            if (requestBorrow.getNameBorrower().equals(user.getName())) {
                if (requestBorrow.getPermission().equals("Approved")) {
                    filteredRequestBorrowList.add(requestBorrow);
                } else if (requestBorrow.getPermission().equals("Returned")) {
                    filteredRequestBorrowList.add(requestBorrow);
                } else if (requestBorrow.getPermission().equals("In progress")) {
                    filteredRequestBorrowList.add(requestBorrow);
                }
            }
//            if (filteredRequestBorrowList == null) {
//                errorLabel.setText("You don't have a history of borrowing equipment.");
//            } else errorLabel.setText("");
        }
        borrowTable.getItems().clear();
        borrowTable.getItems().addAll(filteredRequestBorrowList);
        if (filteredRequestBorrowList == null) {
            errorLabel.setText("You don't have a history of borrowing equipment.");
        } else errorLabel.setText("");
    }
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("user_page");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onReturnButton() {
        String nameBorrower = requestBorrow.getNameBorrower().trim();
        String nameDurable = requestBorrow.getNameDurable().trim();
        String location = requestBorrow.getLocation().trim();
        String timeRequest = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String permit = requestBorrow.getPermission();

        if (requestBorrow != null) {
            if (permit.equals("Approved")) {
                try {
                    RequestReturn requestReturn = new RequestReturn(nameBorrower, nameDurable, location, timeRequest, "Waiting for approval");
                    requestReturnList.addNewReturn(requestReturn);
                    requestBorrow.setPermission("In progress");
                    requestBorrow.setTimeRequest();
                    showBorrowInfo(requestBorrow);
                    allRequest.writeData(requestBorrowList);
                    allReturn.writeData(requestReturnList);
                    showBorrowTable(requestBorrowList);
                    FXRouter.goTo("history_durable");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else errorLabel.setText("You can not return.");
        }
    }
}
