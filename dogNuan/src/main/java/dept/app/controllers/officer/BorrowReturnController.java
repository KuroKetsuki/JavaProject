package dept.app.controllers.officer;

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
import java.util.ArrayList;
import java.util.List;

public class BorrowReturnController {
    @FXML
    private TableView<RequestBorrow> borrowTable;
    @FXML
    private TableView<RequestReturn> returnTable;
    @FXML
    private Label borrowerBLabel;
    @FXML
    private Label borrowerRLabel;
    @FXML
    private Label durableBLabel;
    @FXML
    private Label durableRLabel;
    @FXML
    private Label timeRLabel;
    @FXML
    private Label timeBLabel;

    private RequestBorrowList requestBorrowList;
    private RequestBorrow requestBorrow;
    private RequestReturnList requestReturnList;
    private RequestReturn requestReturn;
    private DurableList durableList;
    private Durable durable;
    private UserList userList;
    private User user;
    private Datasource<RequestBorrowList> allRequest;
    private Datasource<RequestReturnList> allReturn;
    private Datasource<UserList> allUser;
    private Datasource<DurableList> datasource;
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("officer_page");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize() {
        clearReturnInfo();
        clearBorrowInfo();
        datasource = new DurableListFileDatasource("data", "Durable_articles.csv");
        durableList = datasource.readData();

        allRequest = new BorrowListFileDatasource("data", "RequestBorrow.csv");
        requestBorrowList = allRequest.readData();

        allReturn = new ReturnListFileDatasource("data", "RequestReturn.csv");
        requestReturnList = allReturn.readData();

        allUser = new UserFileDatasource("data", "User.csv");
        userList = allUser.readData();

        String username = (String) FXRouter.getData();
        user = userList.findAccountByUsername(username);

        showBorrowTable(requestBorrowList);
        showReturnTable(requestReturnList);
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
        returnTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RequestReturn>() {
            @Override
            public void changed(ObservableValue<? extends RequestReturn> observable, RequestReturn oldValue1, RequestReturn newValue1) {
                if (newValue1 == null) {
                    clearReturnInfo();
                    requestReturn = null;
                } else {
                    showReturnInfo(newValue1);
                    requestReturn = newValue1;
                }
            }
        });
//        durable = (Durable) FXRouter.getData();
//        String durableId = durable.getId();
//        durable = durableList.findDurableById(durableId);
    }

    private void showBorrowInfo(RequestBorrow requestBorrow) {
        borrowerBLabel.setText(requestBorrow.getNameBorrower());
        durableBLabel.setText(requestBorrow.getNameDurable());
        timeBLabel.setText(requestBorrow.getTimeRequest());
    }
    private void clearBorrowInfo() {
        borrowerBLabel.setText("-");
        durableBLabel.setText("-");
        timeBLabel.setText("-");
    }
    private void showReturnInfo(RequestReturn requestReturn) {
        borrowerRLabel.setText(requestReturn.getNameBorrower());
        durableRLabel.setText(requestReturn.getNameDurable());
        timeRLabel.setText(requestReturn.getTimeRequest());
    }
    private void clearReturnInfo() {
        borrowerRLabel.setText("-");
        durableRLabel.setText("-");
        timeRLabel.setText("-");
    }
    @FXML
    void onPermitBorrow() {
        String location = requestBorrow.getLocation();
        String durableNameOfDurable = String.valueOf(durableList.findDurableByName(durableBLabel.getText().trim()).getBorrower());
        String durableRequest = requestBorrow.getNameDurable();
        String nameBorrowerOfBorrow = requestBorrow.getNameBorrower();
        String permit = requestBorrow.getPermission();
        if (requestBorrow != null) {
            if (permit.equals("Waiting for approval")) {
                try {
                    requestBorrow.setPermission("Approved");
                    if(durableNameOfDurable.equals("In progress")) {
                        durableList.findDurableByName(durableBLabel.getText().trim()).setBorrower(nameBorrowerOfBorrow);
                        durableList.findDurableByName(durableBLabel.getText().trim()).setLocation(location);
                        durableList.findDurableByName(durableBLabel.getText().trim()).setBorrowdate();
                    }
                    showBorrowTable(requestBorrowList);
                    showReturnTable(requestReturnList);
                    datasource.writeData(durableList);
                    allRequest.writeData(requestBorrowList);
                    FXRouter.goTo("borrow_return");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    void onPermitReturn() {
        String durableNameOfDurable = String.valueOf(durableList.findDurableByName(durableRLabel.getText().trim()).getBorrower());
        String nameBorrowerOfReturn = requestReturn.getNameBorrower();
        String nameUser = user.getName();
        String permit = requestReturn.getPermission();
//        String takePermit = requestBorrowList.findBorrowByDurable(durableRLabel.getText().trim()).getPermission();
//        String takeDurable = requestBorrowList.findBorrowByDurable(durableRLabel.getText().trim()).getNameDurable();

        if (requestReturn != null) {
            if (permit.equals("Waiting for approval")) {
                try {
                    requestReturn.setPermission("Approved");
//                    if(takeDurable.equals(nameBorrowerOfReturn)||takePermit.equals("In progress")){
//                        requestBorrowList.findBorrowByDurable(takePermit.trim()).setPermission("Returned");
//                    }
                    requestBorrowList.findBorrowByTime(timeRLabel.getText().trim()).setPermission("Returned");
                    if(durableNameOfDurable.equals(nameBorrowerOfReturn)) {
                        durableList.findDurableByName(durableRLabel.getText().trim()).setBorrower(nameUser);
                        durableList.findDurableByName(durableRLabel.getText().trim()).setLocation("Central");
                        durableList.findDurableByName(durableRLabel.getText().trim()).setBorrowdate();
                    }
                    showBorrowTable(requestBorrowList);
                    showReturnTable(requestReturnList);
                    datasource.writeData(durableList);
                    allRequest.writeData(requestBorrowList);
                    allReturn.writeData(requestReturnList);
                    FXRouter.goTo("borrow_return");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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

        List<RequestBorrow> filteredRequestBorrowList = new ArrayList<>();
        for (RequestBorrow requestBorrow : requestBorrowList.getRequestBorrows()) {
            if (requestBorrow.getPermission().equals("Waiting for approval")) {
                filteredRequestBorrowList.add(requestBorrow);
            }
        }
        borrowTable.getItems().clear();
        borrowTable.getItems().addAll(filteredRequestBorrowList);
        // ใส่ข้อมูล Student ทั้งหมดจาก studentList ไปแสดงใน TableView
//        for (RequestBorrow: requestBorrowList.getRequests()) {
//            borrowTable.getItems().add(requestBorrow);
//        }
    }
    private void showReturnTable(RequestReturnList requestReturnList) {
        TableColumn<RequestReturn, String> borrowerColumn = new TableColumn<>("Borrower Name");
        borrowerColumn.setCellValueFactory(new PropertyValueFactory<>("nameBorrower"));

        TableColumn<RequestReturn, String> durableColumn = new TableColumn<>("Durables Name");
        durableColumn.setCellValueFactory(new PropertyValueFactory<>("nameDurable"));

        TableColumn<RequestReturn, String> locationColumn = new TableColumn<>("Storage Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<RequestReturn, String> timeColumn = new TableColumn<>("Time Request");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeRequest"));

        TableColumn<RequestReturn, String> permissionColumn = new TableColumn<>("Permission");
        permissionColumn.setCellValueFactory(new PropertyValueFactory<>("permission"));

        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        returnTable.getColumns().clear();
        returnTable.getColumns().add(borrowerColumn);
        returnTable.getColumns().add(durableColumn);
        returnTable.getColumns().add(locationColumn);
        returnTable.getColumns().add(timeColumn);
        returnTable.getColumns().add(permissionColumn);

        List<RequestReturn> filteredRequestReturnList = new ArrayList<>();
        for (RequestReturn requestReturn : requestReturnList.getReturns()) {
            if (requestReturn.getPermission().equals("Waiting for approval")) {
                filteredRequestReturnList.add(requestReturn);
            }
        }
        returnTable.getItems().clear();
        returnTable.getItems().addAll(filteredRequestReturnList);
    }
}
