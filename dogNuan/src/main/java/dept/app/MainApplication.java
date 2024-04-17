package dept.app;

import dept.app.services.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Equipment borrow & return system");
        configRoute();
        FXRouter.goTo("home");
    }

    public static void configRoute()
    {
        String viewPath = "dept/app/views/";
        FXRouter.when("home", viewPath + "home.fxml");
        FXRouter.when("about_us", viewPath + "about_us.fxml");
        FXRouter.when("admin_page", viewPath + "admin_page.fxml");
        FXRouter.when("officer_page", viewPath + "officer_page.fxml");
        FXRouter.when("user_page", viewPath + "user_page.fxml");
        FXRouter.when("create_user_account", viewPath + "create_user_account.fxml");
        FXRouter.when("change_password", viewPath + "admin_change_profile.fxml");
        FXRouter.when("create_officer_account", viewPath + "create_officer_account.fxml");
        FXRouter.when("user_borrowdurable", viewPath + "user_borrowdurable.fxml");
        FXRouter.when("officer_change_profile", viewPath + "officer_change_profile.fxml");
        FXRouter.when("borrow_return", viewPath + "borrow_return.fxml");
        FXRouter.when("add_durables", viewPath + "add_durables.fxml");
        FXRouter.when("add_materials", viewPath + "add_materials.fxml");
        FXRouter.when("user_change_profile", viewPath + "user_change_profile.fxml");
        FXRouter.when("officer_durabledetail", viewPath + "officer_durabledetail.fxml");
        FXRouter.when("officer_durablelist", viewPath + "officer_durablelist.fxml");
        FXRouter.when("officer_materialdetail", viewPath + "officer_materialdetail.fxml");
        FXRouter.when("officer_materiallist", viewPath + "officer_materiallist.fxml");
        FXRouter.when("user_durablelist", viewPath + "user_durablelist.fxml");
        FXRouter.when("history_durable", viewPath + "history_durable.fxml");
        FXRouter.when("history_material", viewPath + "history_material.fxml");
        FXRouter.when("material_withdraw", viewPath + "material_withdraw.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}