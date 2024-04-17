package dept.app.controllers.user;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class UserController {
    @FXML
    private ImageView sampleImageView;
    @FXML
    private Label roleUserLabel;
    @FXML
    private Label nameUserLabel;
    @FXML
    private Label usernameUserLabel;
    @FXML
    private Label timeUserLabel;

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
        roleUserLabel.setText(user.getRole());
        nameUserLabel.setText(user.getName());
        usernameUserLabel.setText(user.getUsername());
        timeUserLabel.setText(user.getTimeLogin());
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
    void onDurableArticlesList() {
        try {
            FXRouter.goTo("user_durablelist", user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onMyDurableArticlesList() {
        try {
            FXRouter.goTo("history_durable", user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onMyMaterialsList() {
        try {
            FXRouter.goTo("history_material", user.getUsername()); //, material.getId()
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onChangeProfile() {
        try {
            FXRouter.goTo("user_change_profile", user.getUsername());
            //FXRouter.goTo("user_change_profile"), user.getUsername();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

