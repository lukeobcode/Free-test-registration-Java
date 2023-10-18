package free.test.registration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author samfeatherston
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    public TextField firstNameTF, secondNameTF, emailTF, passwordTF, phoneTF, houseTF, postcodeTF, deliverHouseTF, deliverPostcodeTF, emailLogInTF, passwordLogInTF;
    public CheckBox over18CB;

    public class User {

        String firstName, secondName, email, password, phoneNumber, houseNumber, postcode, testHouseNumber, testPostcode;
        boolean over18, testResult;
        boolean smsPreference, emailPreference;

        public User(String firstName, String secondName, String email, String password, String phoneNumber, String houseNumber, String postcode, boolean over18) {
            this.firstName = firstName;
            this.secondName = secondName;
            this.email = email;
            this.password = password;
            this.phoneNumber = phoneNumber;
            this.houseNumber = houseNumber;
            this.postcode = postcode;
            this.over18 = over18;
        }

        @Override
        public String toString() {
            return "User{" + "firstName=" + firstName + ", secondName=" + secondName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", houseNumber=" + houseNumber + ", postcode=" + postcode + ", testHouseNumber=" + testHouseNumber + ", testPostcode=" + testPostcode + ", over18=" + over18 + ", smsPreference=" + smsPreference + ", emailPreference=" + emailPreference + '}';
        }

    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void submitDetails(ActionEvent event) throws IOException, SQLException {

        User newUser = new User(firstNameTF.getText(), secondNameTF.getText(), emailTF.getText(),
                passwordTF.getText(), phoneTF.getText(), houseTF.getText(), postcodeTF.getText(), over18CB.isSelected());

        String SESQL = "jdbc:derby://localhost:1527/SESQL";
        String user = "root";
        String password = "SE123456";
        Connection connection = DriverManager.getConnection(SESQL, user, password);

        String query = "INSERT INTO USERS (firstname, lastname, email, password, phoneNumber, houseNumber, postCode, over18)"
                + "VALUES (" + "'" + newUser.firstName + "'" + ""
                + ", '" + newUser.secondName + "'"
                + ", '" + newUser.email + "'"
                + ", '" + newUser.password + "'"
                + ", '" + newUser.phoneNumber + "'"
                + ", '" + newUser.houseNumber + "'"
                + ", '" + newUser.postcode + "'"
                + "," + newUser.over18 + ")";

        PreparedStatement DB_Update = connection.prepareStatement(query);
        DB_Update.executeUpdate();

        if ("".equals(firstNameTF.getText()) || "".equals(secondNameTF.getText()) || "".equals(emailTF.getText())
                || "".equals(passwordTF.getText()) || "".equals(phoneTF.getText()) || "".equals(houseTF.getText())
                || "".equals(postcodeTF.getText())) {
            System.out.println("missing");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(newUser.firstName + "Please ensure all boxes are completed");
            alert.showAndWait();
        } else {

            System.out.println(newUser.toString());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(newUser.firstName + " has signed up");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void LogIn(ActionEvent event) throws IOException, SQLException {

        String SESQL = "jdbc:derby://localhost:1527/SESQL";
        String user = "root";
        String password = "SE123456";
        Connection connection = DriverManager.getConnection(SESQL, user, password);

        String query = "SELECT EMAIL FROM USERS";
        PreparedStatement userLookup = connection.prepareStatement(query);
        ResultSet boolPhone = userLookup.executeQuery();

        if ("".equals(emailLogInTF.getText()) || "".equals(passwordLogInTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Please ensure both email and password are entered");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logged In");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void test(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("testApplication.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToTest(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("testApplication.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void returnToMenu(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public CheckBox symptomsCB, smsCB, emailCB;
    public TextArea confirmationDetails;

    @FXML
    private void applyForTest(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("applicationConfirmation.fxml"));

        if (symptomsCB.isSelected() && smsCB.isSelected() && emailCB.isSelected() == false) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("You will be contacted by SMS");
            alert.showAndWait();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        else if (symptomsCB.isSelected() && emailCB.isSelected() && smsCB.isSelected() == false) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("You will be contacted by email");
            alert.showAndWait();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        else if (symptomsCB.isSelected() && emailCB.isSelected() && smsCB.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("You will be contacted by SMS and email");
            alert.showAndWait();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("You can't apply for a test if you're not experiencing any symptoms");
            alert.showAndWait();
        }
    }

    @FXML
    public void request(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("requestInformation.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void goToCloseContactPage(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("closeContactInstruction.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String SESQL = "jdbc:derby://localhost:1527/SESQL";
        String user = "root";
        String password = "SE123456";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection Succesful");
            Connection connection = DriverManager.getConnection(SESQL, user, password);

        } catch (Exception noConnection) {
            System.out.println("Connection Failed");
            System.out.println(noConnection.getMessage());

        }

    }

}
