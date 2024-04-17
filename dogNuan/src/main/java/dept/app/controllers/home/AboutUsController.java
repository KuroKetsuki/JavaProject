package dept.app.controllers.home;

import dept.app.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class AboutUsController {
    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
