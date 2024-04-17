package dept.app.controllers.admin;

import dept.app.models.Durable;
import dept.app.models.DurableList;
import dept.app.models.User;
import dept.app.models.UserList;
import dept.app.services.Datasource;
import dept.app.services.FXRouter;
import dept.app.services.SortTimeUser;
import dept.app.services.UserFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Collections;

public class AdminController {
    @FXML
    private Label roleAdminLabel;
    @FXML
    private Label nameAdminLabel;
    @FXML
    private Label usernameAdminLabel;
    @FXML
    private Label timeAdminLabel;
    @FXML
    private ImageView sampleImageView;
    @FXML
    private TableView adminTable;

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
        showTable(userList);

        sampleImageView.setImage(new Image("file:" + user.getImagePath(), true));
        roleAdminLabel.setText(user.getRole());
        nameAdminLabel.setText(user.getName());
        usernameAdminLabel.setText(user.getUsername());
        timeAdminLabel.setText(user.getTimeLogin());
        //sampleImageView.setImage(new Image("file:src/main/resources/image/profile" + user.getImagePath(), true));
        //testName.setText(user.getUsername());
        //timeLabel.setText(user.getTimeLogin());
    }

    @FXML
    public void onLogoutButtonClick() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* bug เเดก*/
    @FXML
    public void onChangePasswordButtonClick() {
        try {
            FXRouter.goTo("change_password", user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* bug เเดก สาเหตุเดียวกับไอตัวบน */
    @FXML
    public void onCreateOfficeAccountButtonClick() {
        try {
            FXRouter.goTo("create_officer_account");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onUserInSystemButtonClick() {
        try {
            FXRouter.goTo("user_in_system");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showTable(UserList userList) {
        //Image image = new Image("file:" + user.getImagePath());
        SortTimeUser sortTimeUser = new SortTimeUser();
        sortTimeUser.setDescending(true);
        Collections.sort(userList.getUsers(), sortTimeUser);

        TableColumn<User, Image> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

        imageColumn.setCellFactory(column -> new TableCell<User, Image>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                setGraphic(imageView);
            }

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    imageView.setImage(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
            }
        });

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> timeLoginColumn = new TableColumn<>("Time Login");
        timeLoginColumn.setCellValueFactory(new PropertyValueFactory<>("timeLogin"));

        adminTable.getColumns().clear();
        adminTable.getColumns().add(imageColumn);
        adminTable.getColumns().add(nameColumn);
        adminTable.getColumns().add(usernameColumn);
        adminTable.getColumns().add(timeLoginColumn);

        adminTable.getItems().clear();

        for (User user1: userList.getUsers()) {
            adminTable.getItems().add(user1);
        }
    }
}
