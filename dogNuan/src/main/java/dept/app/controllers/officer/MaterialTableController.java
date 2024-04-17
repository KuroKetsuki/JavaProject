package dept.app.controllers.officer;

import dept.app.models.Material;
import dept.app.models.MaterialList;
import dept.app.services.Datasource;
import dept.app.services.FXRouter;
import dept.app.services.MaterialListFileDatasource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class MaterialTableController {
    @FXML private TableView materialTableView;

    private MaterialList materialList;
    private Datasource<MaterialList> datasource;

    @FXML
    public void initialize() {
        datasource = new MaterialListFileDatasource("data", "Materials.csv");
        materialList = datasource.readData();
        showTable(materialList);

        materialTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Material>() {
            @Override
            public void changed(ObservableValue observable, Material oldValue, Material newValue) {
                if (newValue != null) {
                    try {
                        // FXRouter.goTo สามารถส่งข้อมูลไปยังหน้าที่ต้องการได้ โดยกำหนดเป็น parameter ที่สอง
                        FXRouter.goTo("officer_materialdetail", newValue.getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void showTable(MaterialList materialList) {
        // กำหนด column ให้มี title ว่า Category และใช้ค่าจาก attribute id ของ object Durable
        TableColumn<Material, String> categoryColumn = new TableColumn<>("Category Of Materials");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Material, String> nameColumn = new TableColumn<>("Materials Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Material, Integer> remainderColumn = new TableColumn<>("Remainder");
        remainderColumn.setCellValueFactory(new PropertyValueFactory<>("remainder"));

        materialTableView.getColumns().clear();
        materialTableView.getColumns().add(categoryColumn);
        materialTableView.getColumns().add(nameColumn);
        materialTableView.getColumns().add(remainderColumn);

        materialTableView.getItems().clear();
        for (Material material: materialList.getMaterials()) {
            materialTableView.getItems().add(material);
        }
    }
    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("officer_page");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}