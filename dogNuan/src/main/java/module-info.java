module dept.app.dognuan {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports dept.app.controllers;
    opens dept.app.controllers to javafx.fxml;
    exports dept.app;
    opens dept.app to javafx.fxml;
    exports dept.app.models;
    opens dept.app.models to javafx.fxml;
    exports dept.app.controllers.home;
    opens dept.app.controllers.home to javafx.fxml;
    exports dept.app.controllers.admin;
    opens dept.app.controllers.admin to javafx.fxml;
    exports dept.app.controllers.officer;
    opens dept.app.controllers.officer to javafx.fxml;
    exports dept.app.controllers.user;
    opens dept.app.controllers.user to javafx.fxml;
    exports dept.app.services;
    opens dept.app.services to javafx.fxml;
}