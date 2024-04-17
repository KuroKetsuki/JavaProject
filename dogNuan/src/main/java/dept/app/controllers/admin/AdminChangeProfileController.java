package dept.app.controllers.admin;

import dept.app.models.User;
import dept.app.models.UserList;
import dept.app.services.Datasource;
import dept.app.services.FXRouter;
import dept.app.services.UserFileDatasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class AdminChangeProfileController {
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField checkPasswordField;
    @FXML
    private ImageView addImageAdmin;

    private Datasource<UserList> allUser;
    private UserList userList;
    private User user;
    private String image;

    @FXML
    public void initialize() {
        //Datasource<UserList> allUser = new UserFileDatasource("data", "user-list.csv");
        allUser = new UserFileDatasource("data", "User.csv");

        userList = allUser.readData();
        String username = (String) FXRouter.getData();
        user = userList.findAccountByUsername(username);
        errorLabel.setText("");
    }

    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("admin_page");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onApplyButtonClick() {
        String oldPassword = oldPasswordField.getText().trim();
        String newPassword = newPasswordField.getText().trim();
        String checkPassword = checkPasswordField.getText().trim();
        String pattern = "^[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_]+$";

        if(image != null){
            try {
                user.setImagePath(image);
                allUser.writeData(userList);
                FXRouter.goTo("admin_page");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (user.getPassword().equals(oldPassword)) {
            if (!oldPassword.equals(newPassword)) {
                if (newPassword.equals(checkPassword)) {
                    if (newPassword.length() >= 8 && newPassword.length() <= 16) {
                        if (newPassword.matches(pattern)) {

                            user.changePassword(newPassword);
                            allUser.writeData(userList);
                            errorLabel.setText("");

                            try {
                                FXRouter.goTo("admin_page");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        } else errorLabel.setText("Unable to enter special characters.");
                    } else errorLabel.setText("Password : At least 8 characters and At most 16 characters.");
                } else errorLabel.setText("Password didn't match.");
            } else errorLabel.setText("Password is already use.");
        } else errorLabel.setText("Current password is wrong");
    }

    @FXML
    public void handleUploadButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                // SET NEW FILE PATH TO IMAGE
                addImageAdmin.setImage(new Image(target.toUri().toString()));
                image = destDir + "/" + filename;
                //user.setImagePath(destDir + "/" + filename);
                //System.out.println(destDir + "/" + filename);
                allUser.writeData(userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
