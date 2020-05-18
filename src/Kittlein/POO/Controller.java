package Kittlein.POO;

import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Controller {
    public App app;
    public Button LogIn;
    public TextArea LogText;
    public VBox LogScreen;

    public void LogIn(ActionEvent actionEvent) {
       LogScreen.getChildren().remove(LogText);
       LogScreen.getChildren().remove(LogIn);
       LogScreen.getChildren().add(new ProgressIndicator());
       app= new App();
        try {
            app.iniciar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
