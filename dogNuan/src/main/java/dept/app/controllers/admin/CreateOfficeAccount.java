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
import javafx.scene.control.TextField;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateOfficeAccount {
    @FXML
    private Label errorLabel;
    @FXML
    private TextField addNameOffice;
    @FXML
    private TextField addUsernameOffice;
    @FXML
    private PasswordField addPasswordOffice;
    @FXML
    private PasswordField confirmPasswordOffice;
    @FXML
    private ImageView addImageOffice;

    private Datasource<UserList> allUser;
    private UserList userList;
    private User user;
    private String image = "images/default.jpg";

    @FXML
    public void initialize() {
        errorLabel.setText("");
        //Datasource<UserList> allUser = new UserFileDatasource("data", "user-list.csv");       /* เขียน csv ไม่ได้ */
        allUser = new UserFileDatasource("data", "User.csv");
        userList = allUser.readData();
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
    public void handleAddOfficerButton() {
        String name = addNameOffice.getText().trim();
        String username = addUsernameOffice.getText().trim();
        String password = addPasswordOffice.getText().trim();
        String checkPassword = confirmPasswordOffice.getText().trim();
        String pattern = "^[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_]+$";

        if (!addNameOffice.getText().trim().equals("") &&  !addUsernameOffice.getText().trim().equals("") && !addPasswordOffice.getText().trim().equals("") && !confirmPasswordOffice.getText().trim().equals("")) {
            if (userList.findAccountByUsername(username) == null) {
                if (username.length() >= 4 && username.length() <= 20) {                                                /* เช็คว่า username มีถึง 4 เเละไม่เกิน 20 ตัวมั้ย */
                    if (password.length() >= 8 && password.length() <= 16) {
                        if (password.equals(checkPassword)) {
                            if (name.matches(pattern) && username.matches(pattern) && password.matches(pattern)) {      /* เช็คว่าอักขระพิเศษมั้ย */
                                errorLabel.setText("");

                                LocalDateTime timeLogin = LocalDateTime.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String formattedTimeLogin = timeLogin.format(formatter);

                                User user = new User("officer", name, username, password, image, formattedTimeLogin);
                                userList.addNewAccount(user);
                                allUser.writeData(userList);                                                            /* ใช้เขียน csv */

                                try {
                                    FXRouter.goTo("admin_page");                                               /* สร้างเเล้วกลับไปหน้า admin */
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            } else errorLabel.setText("Unable to enter special characters.");
                        } else errorLabel.setText("Password didn't match.");
                    } else errorLabel.setText("Password : At least 8 characters and At most 16 characters.");
                } else errorLabel.setText("Username : At least 4 characters and At most 20 characters.");
            } else errorLabel.setText("Username is already used.");
        } else errorLabel.setText("Please Enter Your Information.");
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
                addImageOffice.setImage(new Image(target.toUri().toString()));
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
