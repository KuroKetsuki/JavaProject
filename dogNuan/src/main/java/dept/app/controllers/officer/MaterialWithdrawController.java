package dept.app.controllers.officer;
import dept.app.models.*;
import dept.app.services.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class MaterialWithdrawController {
    @FXML
    private Label nameLabel;
    @FXML
    private TableView<User> userTable;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField withdrawText;
    @FXML
    private Label errorLabel;

    private Datasource<MaterialList> allMaterial;
    private MaterialList materialList;
    private Material material;
    private Datasource<MaterialWithdrawList> allBorrow;
    private MaterialWithdrawList materialWithdrawList;
    private MaterialWithdraw materialWithdraw;
    private Datasource<UserList> allUser;
    private UserList userList;
    private User user;

    public void onBackButtonClick() {
        try {
            FXRouter.goTo("officer_materialdetail");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize() {
        clearUserInfo();
        errorLabel.setText("");

        allMaterial = new MaterialListFileDatasource("data", "Materials.csv");
        materialList = allMaterial.readData();

        allBorrow = new MaterialWithdrawDatasource("data", "Materials_borrow.csv");
        materialWithdrawList = allBorrow.readData();

        allUser = new UserFileDatasource("data", "User.csv");
        userList = allUser.readData();

        String materialId = (String) FXRouter.getData();
        material = materialList.findMaterialById(materialId);

        showUserTable(userList);
        userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                if (newValue == null) {
                    clearUserInfo();
                    user = null;
                } else {
                    showUserInfo(newValue);
                    user = newValue;
                }
            }
        });
    }

    private void showUserInfo(User user) {
        nameLabel.setText(user.getName());
        usernameLabel.setText(user.getUsername());
    }
    private void clearUserInfo() {
        nameLabel.setText("-");
        usernameLabel.setText("-");
    }
    @FXML
    void withdrawButtonClick() {
//        String nameWithdraw = materialWithdraw.getName();
//        String nameUser = user.getName();
        String withdraws = withdrawText.getText().trim();
        String remainders = withdrawText.getText().trim();
        String idMaterial = material.getId();
        String allAmounts = String.valueOf(material.getRemainder());
        String nameUser = userList.findUserByName(nameLabel.getText().trim()).getName();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (withdraws.equals("")) {
            errorLabel.setText("Enter Amount");
            return;
        } try {
            int withdraw = Integer.parseInt(withdraws);
            int remainder = Integer.parseInt(remainders);
            int allAmount = Integer.parseInt(allAmounts);
            if (withdraw < 0) {
                errorLabel.setText("Remainder must be positive number");
                return;
            }
            errorLabel.setText("");
            if (withdraw > allAmount){
                errorLabel.setText("Not enough material");
                return;
            }
            materialList.DecreaseAmountToId(idMaterial, remainder);
            MaterialWithdraw materialWithdraw = new MaterialWithdraw(idMaterial, nameUser, time, withdraw);
            materialWithdrawList.addNewWithdraw(materialWithdraw);
            withdrawText.clear();
            allMaterial.writeData(materialList);
            allBorrow.writeData(materialWithdrawList);
            showUserInfo(user);
        } catch (NumberFormatException e) {
            errorLabel.setText("Remainder must be number");
        }
    }
    private void showUserTable(UserList userList) {
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        userTable.getColumns().clear();
        userTable.getColumns().add(nameColumn);
        userTable.getColumns().add(usernameColumn);

        List<User> filteredUserList = new ArrayList<>();
        for (User user : userList.getUsers()) {
            if (user.getRole().equals("user")) {
                filteredUserList.add(user);
            }
        }
        userTable.getItems().clear();
        userTable.getItems().addAll(filteredUserList);
    }
}
