package ca.uvic.seng330.assn3.view.scenebuilders;

import ca.uvic.seng330.assn3.controller.Controller;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ManageDevicesBuilder extends SceneBuilder {

  public ManageDevicesBuilder(Controller controller, String backText) {
    super(controller, backText);
  }

  @Override
  protected Node buildSpecifics() {
    // TODO: re-factor out hub and this into new method somewhere
    HBox hbox = new HBox(10);

    VBox vbox = new VBox();
    vbox.getChildren().add(new Label("Click Device\nto Delete -->\n\n\n"));
    Button createDevice = new Button("New Device");
    createDevice.setOnAction(event -> getController().handleCreateDeviceClick());
    vbox.getChildren().add(createDevice);
    hbox.getChildren().add(vbox);

    ScrollPane layout = new ScrollPane();
    layout.setFitToHeight(true);
    layout.setFitToWidth(true);
    layout.setHbarPolicy(ScrollBarPolicy.NEVER);
    layout.setVbarPolicy(ScrollBarPolicy.ALWAYS);

    layout.setContent(hubDeleteList(new VBox(10), getController().getDeviceIDList()));
    hbox.getChildren().add(layout);

    return hbox;
  }
}