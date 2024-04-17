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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class OfficerController {
    @FXML
    private AnchorPane officerBackground;
    @FXML
    private Button officerBorrowReturn;
    @FXML
    private Button officerChangeProfile;
    @FXML
    private Button officerDurablesList;
    @FXML
    private Button officerLogOut;
    @FXML
    private Button officerMaterialsList;
    @FXML
    private Pane officerMenuPane;
    @FXML
    private ImageView sampleImageView;
    @FXML
    private Label roleOfficerLabel;
    @FXML
    private Label nameOfficerLabel;
    @FXML
    private Label usernameOfficerLabel;
    @FXML
    private Label timeOfficerLabel;

    private UserList userList;
    private User user;

    @FXML
    public void initialize() {
        Datasource<UserList> allUser = new UserFileDatasource("data", "User.csv");
        userList = allUser.readData();
        String username = (String) FXRouter.getData();
        user = userList.findAccountByUsername(username);

        user.setTimeLogin();
        allUser.writeData(userList);
        sampleImageView.setImage(new Image("file:" + user.getImagePath(), true));
        roleOfficerLabel.setText(user.getRole());
        nameOfficerLabel.setText(user.getName());
        usernameOfficerLabel.setText(user.getUsername());
        timeOfficerLabel.setText(user.getTimeLogin());
        //sampleImageView.setImage(new Image("file:src/main/resources/image/profile" + user.getImagePath(), true));

    }

    @FXML
    public void onLogoutButtonClick(){
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onBorrowReturnButtonClick() {
        try {
            FXRouter.goTo("borrow_return", user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onDurablesListButtonClick() {
        try {
            FXRouter.goTo("officer_durablelist");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onMaterialsListButtonClick() {
        try {
            FXRouter.goTo("officer_materiallist");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onProfileButtonClick(ActionEvent event) {
        try {
            FXRouter.goTo("officer_change_profile", user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAddDurablesPage() {
        try {
            FXRouter.goTo("add_durables", user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAddMaterialsPage() {
        try {
            FXRouter.goTo("add_materials");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
