package dept.app.controllers.officer;

import dept.app.models.Durable;
import dept.app.models.DurableList;
import dept.app.models.User;
import dept.app.models.UserList;
import dept.app.services.Datasource;
import dept.app.services.DurableListFileDatasource;
import dept.app.services.FXRouter;
import dept.app.services.UserFileDatasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
import java.util.ArrayList;
import java.util.List;

public class AddDurablesController {
    @FXML
    private TextField addDurablesNameTextField;
    @FXML
    private TextField addDurablesCategoryTextField;
    @FXML
    private TextField addDurablesIdTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView showImage ;

    private Datasource<DurableList> alldurables;
    private DurableList durableList;
    private Durable durable;
    private Datasource<UserList> allUser;
    private UserList userList;
    private User user;
    private String datepurchase;
    private String borrowdate;
    private String image = "images/icon.png";

    @FXML
    public void initialize() {
        alldurables = new DurableListFileDatasource("data", "Durable_articles.csv");
        durableList = alldurables.readData();
        allUser = new UserFileDatasource("data", "User.csv");
        userList = allUser.readData();

        String username = (String) FXRouter.getData();
        user = userList.findAccountByUsername(username);
        errorLabel.setText("");
    }
    @FXML
    public void onBackButtonClick(ActionEvent event) {
        try {
            FXRouter.goTo("officer_page");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onAddButtonClick() {
        String name = addDurablesNameTextField.getText().trim();
        String id = addDurablesIdTextField.getText().trim();
        String category = addDurablesCategoryTextField.getText().trim();
        String characters = "^[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789]+$";
        String numbers = "^[0123456789]+$";
        String borrower = user.getName();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String datepurchase = time;
        String borrowdate = time;
//        List<String> CategoryArray = new ArrayList<>();
//        for (Durable durable : durableList.getDurables()) {
//            if (!durable.getCategory().equals("")) {
//                CategoryArray.add(durable.getCategory());
//            }
//        }
//        List<String> uniqueCategory = new ArrayList<>();
//        for (String Category : CategoryArray) {
//            if (!uniqueCategory.contains(Category)) {
//                uniqueCategory.add(Category);
//            }
//        }
        if (!name.equals("") && !id.equals("") && !category.equals("")) {
            if (durableList.findDurableById(id) == null) {
                if (name.matches(characters)) {
                    if(id.matches(numbers)) {
                        if (id.length() == 10) {
//                            for (int i = 0; i < uniqueCategory.size(); i++) {
//                                if (category.equals(uniqueCategory.get(i))) {
                                    try {
                                        Durable durable = new Durable(category, id, name, "Normal", image, "Central", datepurchase, borrower, borrowdate);
                                        durableList.addNewDurable(durable);
                                        alldurables.writeData(durableList);
                                        FXRouter.goTo("add_durables");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
//                                } else errorLabel.setText("This category does not exist.");
//                            }
                        } else errorLabel.setText("ID just need 10 number.");
                    }else errorLabel.setText("ID must be number.");
                }else errorLabel.setText("Unable to enter special characters.");
            }else errorLabel.setText("ID is already used.");
        }else errorLabel.setText("Please Enter Your Information.");
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
}
