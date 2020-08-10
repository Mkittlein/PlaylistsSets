package PlaylistSets;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LoginController {

    private App app;

    //Variables declaradas en el FXML
    public VBox LogScreen;
    public TextArea LogText;
    public Button LogIn;




    public LoginController() {
    }


    public void LogIn(ActionEvent actionEvent) {
        LogScreen.getChildren().remove(LogText);
        LogScreen.getChildren().remove(LogIn);
        LogScreen.getChildren().add(new ProgressIndicator() );
        LogScreen.fillWidthProperty().setValue(true);
        LogScreen.setAlignment(Pos.CENTER);
        app= new App();
        try {
            app.iniciar();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
