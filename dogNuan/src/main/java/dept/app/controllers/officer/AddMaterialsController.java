package dept.app.controllers.officer;

import dept.app.models.*;
import dept.app.services.Datasource;
import dept.app.services.FXRouter;
import dept.app.services.MaterialListFileDatasource;
import dept.app.services.UserFileDatasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddMaterialsController {
    @FXML
    private TextField addMeterialsNameTextField;
    @FXML
    private TextField addMaterialsIdTextField;
    @FXML
    private TextField addMaterialsCatagoryTextField;
    @FXML
    private TextField addMaterialsAmountTextField;
    @FXML
    private Label errorLabel;

    private Datasource<MaterialList> allMaterial;
    private MaterialList materialList;
    private Material material;
    private UserList userList;

    @FXML
    public void initialize() {
        errorLabel.setText("");
        allMaterial = new MaterialListFileDatasource("data", "Materials.csv");
        materialList = allMaterial.readData();
    }
    @FXML
    public void onBackButtonClick(ActionEvent event) {
        try {
            FXRouter.goTo("officer_page");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addMaterialsButtonClick() {
        String name = addMeterialsNameTextField.getText().trim();
        String id = addMaterialsIdTextField.getText().trim();
        String category = addMaterialsCatagoryTextField.getText().trim();
        String characters = "^[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789]+$";
        String numbers = "^[0123456789]+$";
        String remainders = addMaterialsAmountTextField.getText();
//        List<String> CategoryArray = new ArrayList<>();
//        for (Material material : materialList.getMaterials()) {
//            if (!material.getCategory().equals("")) {
//                CategoryArray.add(material.getCategory());
//            }
//        }
//        List<String> uniqueCategory = new ArrayList<>();
//        for (String Category : CategoryArray) {
//            if (!uniqueCategory.contains(Category)) {
//                uniqueCategory.add(Category);
//            }
//        }
        if (!name.equals("") && !id.equals("") && !category.equals("") && !addMaterialsAmountTextField.getText().equals("")) {
            if(materialList.findMaterialByName(name) == null) {
                if(materialList.findMaterialById(id) == null){
                    if(name.matches(characters)) {
                        if(id.matches(numbers)) {
                            if (id.length() == 4) {
                                if(remainders.matches(numbers)) {
//                                    for (int i = 0; i < uniqueCategory.size(); i++) {
//                                        if (category.equals(uniqueCategory.get(i))) {
                                            try {
                                                int remainder = Integer.parseInt(remainders);
                                                Material material = new Material(category, name, remainder, id);
                                                materialList.addNewMaterial(material);
                                                allMaterial.writeData(materialList);
                                                FXRouter.goTo("add_materials");
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
//                                        } else errorLabel.setText("This category does not exist.");
//                                    }
                                }else errorLabel.setText("Remainder must be number.");
                            } else errorLabel.setText("ID just need 4 number.");
                        }else errorLabel.setText("ID must be number.");
                    }else errorLabel.setText("Unable to enter special characters.");
                }else errorLabel.setText("ID is already used.");
            }else errorLabel.setText("Name is already used.");
        }else errorLabel.setText("Please Enter Your Information.");
    }
}
