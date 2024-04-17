package dept.app.controllers.user;

import dept.app.models.*;
import dept.app.services.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryMaterial {
    @FXML
    private Label amountLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private TableView<MaterialWithdraw> withdrawTable;

    private Datasource<MaterialList> allMaterial;
    private MaterialList materialList;
    private Material material;
    private Datasource<MaterialWithdrawList> allBorrow;
    private MaterialWithdrawList materialWithdrawList;
    private MaterialWithdraw materialWithdraw;
    private UserList userList;
    private User user;
    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("user_page");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize() {
        clearBorrowInfo();
        errorLabel.setText("");

        Datasource<UserList> allUser = new UserFileDatasource("data", "User.csv");
        userList = allUser.readData();
        String username = (String) FXRouter.getData();
        user = userList.findAccountByUsername(username);

        allMaterial = new MaterialListFileDatasource("data", "Materials.csv");
        materialList = allMaterial.readData();

        allBorrow = new MaterialWithdrawDatasource("data", "Materials_borrow.csv");
        materialWithdrawList = allBorrow.readData();

        showBorrowTable(materialWithdrawList);
        withdrawTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MaterialWithdraw>() {
            @Override
            public void changed(ObservableValue<? extends MaterialWithdraw> observable, MaterialWithdraw oldValue, MaterialWithdraw newValue) {
                if (newValue == null) {
                    clearBorrowInfo();
                    materialWithdraw = null;
                } else {
                    showBorrowInfo(newValue);
                    materialWithdraw = newValue;
                }
            }
        });
    }
    private void showBorrowInfo(MaterialWithdraw materialWithdraw) {
        idLabel.setText(materialWithdraw.getId());
        nameLabel.setText(materialList.findMaterialById(idLabel.getText().trim()).getName());
        dateLabel.setText(materialWithdraw.getRequisitiondate());
        amountLabel.setText(String.valueOf(materialWithdraw.getAmount()));
    }
    private void clearBorrowInfo() {
        nameLabel.setText("-");
        idLabel.setText("-");
        dateLabel.setText("-");
        amountLabel.setText("-");
    }

    private void showBorrowTable(MaterialWithdrawList materialWithdrawList) {
        TableColumn<MaterialWithdraw, String> idColumn = new TableColumn<>("Material ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MaterialWithdraw, String> dateColumn = new TableColumn<>("Requisition Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("requisitiondate"));

        TableColumn<MaterialWithdraw, String> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        withdrawTable.getColumns().clear();
        withdrawTable.getColumns().add(idColumn);
        withdrawTable.getColumns().add(dateColumn);
        withdrawTable.getColumns().add(amountColumn);

        List<MaterialWithdraw> filteredBorrowList = new ArrayList<>();
        for (MaterialWithdraw materialWithdraw : materialWithdrawList.getMaterialWithdraws()) {
            if (materialWithdraw.getName().equals(user.getName())) {
                filteredBorrowList.add(materialWithdraw);
            }
        }
        withdrawTable.getItems().clear();
        withdrawTable.getItems().addAll(filteredBorrowList);
    }
}
