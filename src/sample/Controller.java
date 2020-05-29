package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label labelInfo;

    @FXML
    private ListView<String> listview;

    private static ObservableList<String> listViewDevice = FXCollections.observableArrayList();

    private  static  DeviceDiscovery deviceBluetooth = new DeviceDiscovery();

    @FXML
    protected void handleDoThatAction(ActionEvent e) {

        System.out.println("Do that");

    }

    public static void updateBluetoothDeviceLabel() {

        Platform.runLater(new Runnable() {

            @Override
            public void run()  {

                deviceBluetooth.getCurrentReading(listViewDevice);

            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("initialize ....");
        listview.setItems(listViewDevice);
        labelInfo.setText("Liste");
    }

}
