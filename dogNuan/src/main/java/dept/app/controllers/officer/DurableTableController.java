package dept.app.controllers.officer;

import dept.app.models.Durable;
import dept.app.models.DurableList;
import dept.app.models.User;
import dept.app.models.UserList;
import dept.app.services.Datasource;
import dept.app.services.DurableListFileDatasource;
import dept.app.services.FXRouter;
import dept.app.services.UserFileDatasource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class DurableTableController {
    @FXML private TableView durableTableView;
    @FXML
    private TextField textSearch;

    private DurableList durableList;
    private Durable durable;
    private Datasource<DurableList> datasource;
    @FXML
    public void initialize() {
        datasource = new DurableListFileDatasource("data", "Durable_articles.csv");
        durableList = datasource.readData();
        showTable(durableList,"");
        durableTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Durable>() {
            @Override
            public void changed(ObservableValue observable, Durable oldValue, Durable newValue) {
                if (newValue != null) {
                    try {
                        // FXRouter.goTo สามารถส่งข้อมูลไปยังหน้าที่ต้องการได้ โดยกำหนดเป็น parameter ที่สอง
                        FXRouter.goTo("officer_durabledetail", newValue);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
    @FXML
    void searchButtonClick()throws IOException {
        String text = textSearch.getText();
        showTable(durableList, text);
    }
    private void showTable(DurableList durableList,String text) {
        // กำหนด column ให้มี title ว่า Category และใช้ค่าจาก attribute id ของ object Durable
        TableColumn<Durable, String> categoryColumn = new TableColumn<>("Category Of Durables");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        // กำหนด column ให้มี title ว่า ID และใช้ค่าจาก attribute id ของ object Durable
        TableColumn<Durable, String> idColumn = new TableColumn<>("Durables ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        // กำหนด column ให้มี title ว่า Name และใช้ค่าจาก attribute name ของ object Durable
        TableColumn<Durable, String> nameColumn = new TableColumn<>("Durables Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Durable, String> datepurchaseColumn = new TableColumn<>("Date Of Purchase");
        datepurchaseColumn.setCellValueFactory(new PropertyValueFactory<>("datepurchase"));

        TableColumn<Durable, String> statusColumn = new TableColumn<>("Durables Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Durable, String> locationColumn = new TableColumn<>("Storage Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Durable, String> borrowerColumn = new TableColumn<>("BorrowerName");
        borrowerColumn.setCellValueFactory(new PropertyValueFactory<>("borrower"));

        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        durableTableView.getColumns().clear();
        durableTableView.getColumns().add(categoryColumn);
        durableTableView.getColumns().add(idColumn);
        durableTableView.getColumns().add(nameColumn);
        durableTableView.getColumns().add(datepurchaseColumn);
        durableTableView.getColumns().add(statusColumn);
        durableTableView.getColumns().add(locationColumn);
        durableTableView.getColumns().add(borrowerColumn);

        durableTableView.getItems().clear();

        if(durableList.isCategoryByText(text)){
            for (Durable durable: durableList.getDurables()) {
                if(durable.getCategory().equals(text)) {
                    durableTableView.getItems().add(durable);
                }
            }
        }
        else if (durableList.isNameByText(text)) {
            for (Durable durable: durableList.getDurables()) {
                if(durable.getName().equals(text)) {
                    durableTableView.getItems().add(durable);
                }
            }
        }
        else if (durableList.isBorrowerByText(text)) {
            for (Durable durable: durableList.getDurables()) {
                if(durable.getBorrower().equals(text)) {
                    durableTableView.getItems().add(durable);
                }
            }
        }
        else if (durableList.isLocationByText(text)) {
            for (Durable durable: durableList.getDurables()) {
                if(durable.getLocation().equals(text)) {
                    durableTableView.getItems().add(durable);
                }
            }
        }
        else {
            for (Durable durable: durableList.getDurables()) {
                durableTableView.getItems().add(durable);
            }
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