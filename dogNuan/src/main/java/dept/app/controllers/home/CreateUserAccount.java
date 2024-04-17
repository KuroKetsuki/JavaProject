package dept.app.controllers.home;

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


public class CreateUserAccount {
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField createNameTextField;
    @FXML
    private PasswordField createPasswordField;
    @FXML
    private TextField createUsernameTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView showImage ;

    private Datasource<UserList> allUser;
    private UserList userList;
    private User user;
    private String image = "images/default.jpg";

    @FXML
    public void initialize() {
        errorLabel.setText("");
        //Datasource<UserList> allUser = new UserFileDatasource("data", "user-list.csv");
        allUser = new UserFileDatasource("data", "User.csv");
        userList = allUser.readData();
        //showImage.setImage(new Image(getClass().getResource("/data/images/default_profile_picture.png").toExternalForm()));

        // vv มีไว้เพื่อทดสอบเฉยๆ vv
        //user = userList.findAccountByUsername("Dome");
    }
    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                showImage.setImage(new Image(target.toUri().toString()));
                image = destDir + "/" + filename;
                //user.setImagePath(destDir + "/" + filename);
                //System.out.println(destDir + "/" + filename);
                allUser.writeData(userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleAddUserButton() {
        String name = createNameTextField.getText().trim();
        String username = createUsernameTextField.getText().trim();
        String password = createPasswordField.getText().trim();
        String confirm = confirmPasswordField.getText().trim();
        String characters = "^[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_]+$";
        //String specialCharacters = "[,.+×÷=/€£¥₩!@#$%^&*()-':;,?`~|<>{}[]]";
        //String imagePath = "/data/images/default_profile_picture.png";

        if (!createNameTextField.getText().equals("") && !createUsernameTextField.getText().equals("") && !createPasswordField.getText().equals("") && !confirmPasswordField.getText().equals("")) {
            if (userList.findAccountByUsername(username) == null) {
                if (createUsernameTextField.getText().length() >= 4 && username.length() <= 20) {
                    if (createPasswordField.getText().length() >= 8 && password.length() <= 16) {
                        if (createPasswordField.getText().equals(confirmPasswordField.getText())) {
                            if (name.matches(characters) && username.matches(characters) && password.matches(characters)) {
                                try {
                                    LocalDateTime timeLogin = LocalDateTime.now();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    String formattedTimeLogin = timeLogin.format(formatter);

                                    //userList.addAccount("user", name, username, password, image);
                                    User user = new User("user", name, username, password, image, formattedTimeLogin);
                                    userList.addNewAccount(user);
                                    allUser.writeData(userList);
                                    FXRouter.goTo("home");
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
}
