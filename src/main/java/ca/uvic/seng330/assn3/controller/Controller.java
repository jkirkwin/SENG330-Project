package ca.uvic.seng330.assn3.controller;

import ca.uvic.seng330.assn3.model.AccessLevel;
import ca.uvic.seng330.assn3.model.Hub;
import ca.uvic.seng330.assn3.model.UserAccount;
import ca.uvic.seng330.assn3.view.Client;
import ca.uvic.seng330.assn3.view.HubSceneBuilder;
import ca.uvic.seng330.assn3.view.LoginSceneBuilder;
import ca.uvic.seng330.assn3.view.SceneBuilder;
import java.util.Stack;
import javafx.scene.control.Alert.AlertType;

public class Controller {

  private final Hub hub;
  private final Client client;
  private UserAccount activeUser;
  private final Stack<ViewType> views;
  private final SceneBuilder loginBuilder;

  /*
   * @pre hub != null
   * @pre client != null
   */
  public Controller(Hub hub, Client client) {
    assert hub != null;
    assert client != null;

    this.loginBuilder = new LoginSceneBuilder(this, "Close");
    this.activeUser = null;
    this.client = client;
    this.client.setController(this);
    this.hub = hub;
    this.views = new Stack<ViewType>();

    // Load and display login screen
    views.push(ViewType.LOGIN);
    client.setTitle(ViewType.LOGIN.toString());
    client.setView(loginBuilder);
  }

  public void update(Object arg) {
    // TODO
    // this is the mega-handler to be used to delegate action to
    // the appropriate function to update the view and/or model
    // once we've got this thing functional we can see if there is
    // an easy way to re-factor it into something less god-function-esque.

    // Will need to add some argument that tells us about the button that was pressed/
    // the radio item selected/the text entered in a field.

    // Set active user on whenever an account successfully logs in, and remove it
    // whenever they log out

    // Way better idea: have a controller for each style of view we have and hold one
    // of each in this main controller

    // For now we're just going to split up handlers and see how that works out. Later on
    // we can combine some, and/or package them into various classes
  }

  public void handleBackClick() {
    if (views.peek() == ViewType.LOGIN) {
      // close window
      client.close();
    } else if (views.peek() == ViewType.HUB_BASIC || views.peek() == ViewType.HUB_ADMIN) {
      // log out
      client.setView(loginBuilder);
      views.pop();
      this.activeUser = null;
    } else {
      views.pop();
      client.setTitle(views.peek().toString());

      // TODO generate appropriate builder based on the ViewType now on the top of the stack
      client.setView(findBuilder(views.peek()));
    }

    System.out.println("Back"); // Test
  }

  private SceneBuilder findBuilder(ViewType view) {
    // TODO generate the appropriate SceneBuilder based on for the ViewType
    switch (view) {
      case LOGIN:
        return loginBuilder;

      case CREATE_DEVICE:
        break;

      case HUB_ADMIN:
        // TODO
        views.push(ViewType.HUB_ADMIN);
        client.setTitle(ViewType.HUB_ADMIN.toString());
        return new HubSceneBuilder(this, "Back", true);

      case HUB_BASIC:
        // TODO
        views.push(ViewType.HUB_BASIC);
        client.setTitle(ViewType.HUB_BASIC.toString());
        return new HubSceneBuilder(this, "Back", false);

      case MANAGE_DEVICES:
        // TODO
        break;

      case MANAGE_NOTIFICATIONS:
        // TODO
        break;

      case MANAGE_USERS:
        // TODO
        break;

      case SELECT_NOTIFICATIONS:
        // TODO
        break;

      default:
        System.out.println("No case in controller.findBuilder() for viewType " + view);
        break;
    }
    return null;
  }

  public void handleLoginClick(String username, String password) {
    /* TODO
     * Log in if valid
     */
    if (hub.isUser(username)) {
      System.out.println("Logged in"); // Testing
      if (hub.getUser(username, password).isAdmin()) {
        client.setView(findBuilder(ViewType.HUB_ADMIN));
      } else {
        client.setView(findBuilder(ViewType.HUB_BASIC));
      }
    } else {
      client.alertUser(
          AlertType.INFORMATION,
          "Failure",
          "Account does not exist",
          "Username \"" + username + "\" is not in use. Please try a different one.");
    }
  }

  public void handleNewUser(String username, String password) {
    if (acceptableInputs(username, password)) {
      if (!hub.isUser(username)) {
        hub.register(new UserAccount(this.hub, AccessLevel.BASIC, username, password));
        client.alertUser(AlertType.INFORMATION, "Success", "Success", "User Account Created.");
      } else {
        client.alertUser(
            AlertType.INFORMATION,
            "Failure",
            "Username Unavailable",
            "Username \"" + username + "\" is already in use. Please try a different one.");
      }
    }
  }

  public void handleNewAdmin(String username, String password) {
    System.out.println("New Admin"); // Testing
    if (acceptableInputs(username, password)) {
      if (!hub.isUser(username)) {
        hub.register(new UserAccount(this.hub, AccessLevel.ADMIN, username, password));
        client.alertUser(AlertType.INFORMATION, "Success", "Success", "Admin Account Created.");
      } else {
        client.alertUser(
            AlertType.INFORMATION,
            "Failure",
            "Username Unavailable",
            "Username \"" + username + "\" is already in use. Please try a different one.");
      }
    }
  }

  private boolean acceptableInputs(String username, String password) {
    if (username.toLowerCase() == "username" || username.isEmpty()) {
      client.alertUser(
          AlertType.INFORMATION,
          "Failure",
          "Username Unaccepted",
          "Username is not allowed to be \"username\" or left blank. Please try again");
      return false;
    } else if (password.toLowerCase() == "password" || password.isEmpty()) {
      client.alertUser(
          AlertType.INFORMATION,
          "Failure",
          "Password Unaccepted",
          "Password is not allowed to be \"password\" or left blank. Please try again");
      return false;
    }
    return true;
  }

  public void handleAdminManageUsersClick() {
    System.out.println("Manage Users");
  }

  public void handleAdminManageDevicesClick() {
    System.out.println("Manage Devices");
  }

  public void handleAdminManageNotificationsClick() {
    System.out.println("Manage Notifications");
  }
}