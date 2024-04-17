package dept.app.controllers.officer;

import dept.app.models.Durable;
import dept.app.models.DurableList;
import dept.app.services.Datasource;
import dept.app.services.DurableListFileDatasource;
import dept.app.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class DurableDetail {
    @FXML
    private Label borrowerLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label dateborrowLabel;
    @FXML
    private Label datepurchaseLabel;
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

    private Datasource<DurableList> datasource;
    private DurableList durableList;
    private Durable durable;

    @FXML
    public void initialize() {
        datasource = new DurableListFileDatasource("data", "Durable_articles.csv");
        durableList = datasource.readData();

        // รับข้อมูล studentId จากหน้าอื่น ผ่าน method FXRouter.getData()
        // โดยจำเป็นต้อง casting data type ให้ตรงกับหน้าที่ส่งข้อมูล
        durable = (Durable) FXRouter.getData();
        String durableId = durable.getId();
        durable = durableList.findDurableById(durableId);

        showDurable(durable);

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
        //sampleImageView.setImage(new Image("file:" + user.getImagePath(), true));
        //picPath.setImage(new Image("file:src/main/resources/image/durables" + durable.getImagePath(), true));
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
            FXRouter.goTo("officer_durablelist");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
