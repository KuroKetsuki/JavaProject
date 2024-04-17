package dept.app.controllers.home;

import dept.app.models.UserList;
import dept.app.services.Datasource;
import dept.app.services.FXRouter;
import dept.app.services.UserFileDatasource;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomeController {
    @FXML
    private Label errorLabel;
    @FXML
    private TextField getUsername;
    @FXML
    private PasswordField getPassword;
    @FXML
    private ComboBox themeChooser;

    private ArrayList<String> theme = new ArrayList<String>();
    private UserList userList;

    /* initialize ถ้าไม่มีฟังชั่นนี้ errorLable จะเเสดงตลอดเวลา
     * รวทถึงใช้ใส่คำสั่งต่างๆ โดยที่ไม่ต้องทำอะไรกับหน้านั้นก่อน */
    @FXML
    public void initialize() {
        errorLabel.setText("");
        //UserHardCodeDatasource allUser = new UserHardCodeDatasource();    ซากอารยธรรม
        //Datasource<UserList> allUser = new UserHardCodeDatasource();      ซากอารยธรรม
        Datasource<UserList> allUser = new UserFileDatasource("data", "User.csv");

        theme.add("Pastel");
        theme.add("Midnight");
        themeChooser.setItems(FXCollections.observableList(theme));

        userList = allUser.readData();
    }
    @FXML
    public void onInfoProducerButtonClick() {
        try {
            FXRouter.goTo("about_us");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onSuggestButtonClick() throws IOException {
        String fs = File.separator ;
        File pdf = new File(System.getProperty("user.dir")+ fs +"data"+ fs + "HowToUse.pdf");
        Desktop.getDesktop().open(pdf);
    }
    @FXML
    public void onLoginButtonClick() {
        if(userList.findAccountByUsername(getUsername.getText().trim()) != null){                                                        /* เช็คว่ามี Account รึป่าว */
            if (getPassword.getText().equals(userList.findAccountByUsername(getUsername.getText().trim()).getPassword())) {          /* เช็คว่า Password ถูกรึป่าว */
                if(userList.findAccountByUsername(getUsername.getText().trim()).getRole().equals("admin")) {                         /* เช็คว่าเป็น Admin รึป่าว */
                    errorLabel.setVisible(false);
                    try {
                        FXRouter.goTo("admin_page", getUsername.getText().trim());                                    /* ส่งข้อมูลข้ามหน้า */
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (userList.findAccountByUsername(getUsername.getText().trim()).getRole().equals("officer")) {
                    errorLabel.setVisible(false);
                    try {
                        FXRouter.goTo("officer_page", getUsername.getText().trim());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else  {
                    errorLabel.setVisible(false);
                    try {
                        FXRouter.goTo("user_page", getUsername.getText().trim());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                errorLabel.setText("Wrong Password");
                errorLabel.setVisible(true);
            }
        } else {
            errorLabel.setText("Please Enter Username");
            errorLabel.setVisible(true);
        }
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
    public void onCreateUserAccount() {
        try {
            FXRouter.goTo("create_user_account");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectCategoryComboBox(ActionEvent actionEvent) {
        String categorySelect = themeChooser.getSelectionModel().getSelectedItem().toString();
        if(categorySelect.equals("Pastel")){
            try{
                FXRouter.setPathCss("/dept/app/Theme/pastel.css");
                FXRouter.goTo("home", userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(categorySelect.equals("Midnight")) {
            try {
                FXRouter.setPathCss("/dept/app/Theme/dark.css");
                FXRouter.goTo("home", userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

