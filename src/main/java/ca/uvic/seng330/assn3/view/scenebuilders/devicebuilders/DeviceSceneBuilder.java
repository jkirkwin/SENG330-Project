package ca.uvic.seng330.assn3.view.scenebuilders.devicebuilders;

import ca.uvic.seng330.assn3.controller.Controller;
import ca.uvic.seng330.assn3.view.scenebuilders.SceneBuilder;

import java.util.UUID;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class DeviceSceneBuilder extends SceneBuilder {
  
  UUID deviceID;
  
  public DeviceSceneBuilder(Controller controller, String backText, UUID id) {
    super(controller, backText);
    this.deviceID = id;
  }

  @Override
  protected Node buildCommon() {
    GridPane container = new GridPane();
    container.add(super.buildCommon(), 0, 0);
    
    VBox vbox = new VBox(20);
    vbox.getChildren().add(new Label(getController().getLabel(deviceID)));

    HBox hbox = new HBox(30);
    hbox.getChildren().add(new Label("Device Status -->"));
    Button toggle = new Button(getController().getStatus(deviceID));
    toggle.setOnAction(event -> getController().toggleDevice(deviceID));
    hbox.getChildren().add(toggle);

    vbox.getChildren().add(hbox);
    hbox = new HBox(20);
    hbox.getChildren().add(vbox);
    
    container.add(hbox, 1, 1);
    return container;
  }
}