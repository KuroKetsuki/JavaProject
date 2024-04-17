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

public class MaterialDetail {
    @FXML
    private Label categoryLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label remainderLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<MaterialLog> materialsLogTable;
    @FXML
    private TableView<MaterialWithdraw> withdrawLogTable;
    @FXML
    private TextField addAmountTextField;

    private Datasource<MaterialList> datasource;
    private MaterialList materialList;
    private Material material;
    private Material selectedMaterial;
    private MaterialLog materialLog;
    private MaterialLogList materialLogList;
    private Datasource<MaterialLogList> datasource2;
    private MaterialWithdraw materialWithdraw;
    private MaterialWithdrawList materialWithdrawList;
    private Datasource<MaterialWithdrawList> datasource3;

    @FXML
    public void initialize() {
        errorLabel.setText("");
        datasource = new MaterialListFileDatasource("data", "Materials.csv");
        materialList = datasource.readData();

        datasource2 = new MaterialAmountAddDatasource("data", "Materials_log.csv");
        materialLogList = datasource2.readData();

        datasource3 = new MaterialWithdrawDatasource("data", "Materials_borrow.csv");
        materialWithdrawList = datasource3.readData();

        String materialId = (String) FXRouter.getData();
        material = materialList.findMaterialById(materialId);

        showMaterial(material);
        showAddAmount(materialLogList);
        showWithdraw(materialWithdrawList);
        materialsLogTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MaterialLog>() {
            @Override
            public void changed(ObservableValue<? extends MaterialLog> observable, MaterialLog oldValue, MaterialLog newValue) {
                if (newValue == null) {
                    materialLog = null;
                } else {
                    materialLog = newValue;
                }
            }
        });

        withdrawLogTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MaterialWithdraw>() {
            @Override
            public void changed(ObservableValue<? extends MaterialWithdraw> observable, MaterialWithdraw oldValue, MaterialWithdraw newValue) {
                if (newValue == null) {
                    materialWithdraw = null;
                } else {
                    materialWithdraw = newValue;
                }
            }

        });
    }


    private void showMaterial(Material material) {
        selectedMaterial = material;
        categoryLabel.setText(selectedMaterial.getCategory());
//        idLabel.getId();
        nameLabel.setText(selectedMaterial.getName());
        remainderLabel.setText(String.valueOf(material.getRemainder()));;
    }

    private void clearMaterial() {
        categoryLabel.setText("");
//        idLabel.setText("");
        nameLabel.setText("");
        remainderLabel.setText("");
    }

    private void showAddAmount(MaterialLogList materialLogList) {

        TableColumn<MaterialLog, String> idColumn = new TableColumn<>("MaterialsID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MaterialLog, String> dateaddColumn = new TableColumn<>("DateAddedMaterial");
        dateaddColumn.setCellValueFactory(new PropertyValueFactory<>("dateadd"));

        TableColumn<MaterialLog, Integer> amountColumn = new TableColumn<>("AmountAddedMaterial");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        materialsLogTable.getColumns().clear();
        materialsLogTable.getColumns().add(idColumn);
        materialsLogTable.getColumns().add(dateaddColumn);
        materialsLogTable.getColumns().add(amountColumn);

        materialsLogTable.getItems().clear();

        List<MaterialLog> filteredMaterialLogList = new ArrayList<>();
        for (MaterialLog materialLog : materialLogList.getMaterialLogs()) {
            if (materialLog.getId().equals(material.getId())) {
                filteredMaterialLogList.add(materialLog);
            }
        }
        materialsLogTable.getItems().clear();
        materialsLogTable.getItems().addAll(filteredMaterialLogList);
    }

    private void showWithdraw(MaterialWithdrawList materialWithdrawList) {
        TableColumn<MaterialWithdraw, String> idColumn = new TableColumn<>("MaterialsID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MaterialWithdraw, String> nameColumn = new TableColumn<>("RequisitionName");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<MaterialWithdraw, String> requisitiondateColumn = new TableColumn<>("Requisitiondate");
        requisitiondateColumn.setCellValueFactory(new PropertyValueFactory<>("requisitiondate"));

        TableColumn<MaterialWithdraw, Integer> amountColumn = new TableColumn<>("AmountRequistion");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        withdrawLogTable.getColumns().clear();
        withdrawLogTable.getColumns().add(idColumn);
        withdrawLogTable.getColumns().add(nameColumn);
        withdrawLogTable.getColumns().add(requisitiondateColumn);
        withdrawLogTable.getColumns().add(amountColumn);

        withdrawLogTable.getItems().clear();

        List<MaterialWithdraw> filteredMaterialWithdrawList = new ArrayList<>();
        for (MaterialWithdraw materialWithdraw : materialWithdrawList.getMaterialWithdraws()) {
            if (materialWithdraw.getId().equals(material.getId())) {
                filteredMaterialWithdrawList.add(materialWithdraw);
            }
        }

        withdrawLogTable.getItems().clear();
        withdrawLogTable.getItems().addAll(filteredMaterialWithdrawList);
    }

    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("officer_materiallist");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onGotoWithdraw() {
        try {
            FXRouter.goTo("material_withdraw", material.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void addMaterialButtonClick() {
        String remainders = addAmountTextField.getText().trim();
        String amounts = addAmountTextField.getText().trim();
        String idMaterial = material.getId();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String dateadd = time;
        if (remainders.equals("")) {
            errorLabel.setText("Enter Amount");
            return;
        } try {
            int remainder = Integer.parseInt(remainders);
            int amount = Integer.parseInt(amounts);
            if (remainder < 0) {
                errorLabel.setText("Remainder must be positive number");
                return;
            }
            errorLabel.setText("");
            materialList.addAmountToId(material.getId(), remainder);
            MaterialLog materialLog = new MaterialLog(idMaterial, dateadd, amount);
            materialLogList.addNewMaterialLog(materialLog);
            addAmountTextField.clear();
            datasource.writeData(materialList);
            datasource2.writeData(materialLogList);
            System.out.println(materialList);
            System.out.println(materialLogList);
            showMaterial(material);
        } catch (NumberFormatException e) {
            errorLabel.setText("Remainder must be number");
        }
    }
}
