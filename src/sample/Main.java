package sample;

// Add Git to project: 05/28/2020.
//https://github.com/pomberts/projects.git
//
// Utility program created by: Sylvain Pombert.
// This program create list of Bluetooth radio
// can be paired. (Name & Address).

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static javafx.application.Platform.exit;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent eclose) {

                try {
                    exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //System.out.println("shutDownDeviceDiscovery() called...");
                //DeviceDiscovery.shutDownDeviceDiscovery();

            }
        });



        Scene scene = new Scene(root,350,500);
        //scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Pacifico");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Bluetooth Device Discovery");
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void DisplayCurrentThread (String location) {

        System.out.println("Inside " + location + "; use: " + Thread.currentThread().getName());
    }

    @Override
    public void stop() throws Exception {
        // Call at end of application

        System.out.println("Method stop() called...");
        DeviceDiscovery.shutDownDeviceDiscovery();
        System.out.println("shutDownDeviceDiscovery done...");

    }

    public static void main(String[] args) {
        //System.out.println("Method main called...");
        launch(args);
    }


}
