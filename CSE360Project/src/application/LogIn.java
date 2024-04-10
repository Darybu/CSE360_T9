package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*;
import javafx.scene.control.PasswordField;

import java.io.*;


public class LogIn extends Application{
	File directory = new File("/Users/dbuyer/new-eclipse-workspace/PediatricOfficeProgramCopy");  // fix this !!!

	@Override
	public void start(Stage stage) {
		// allows brand image to show
		Image logoImage = new Image(getClass().getResourceAsStream("123.png"));
	    ImageView logoView = new ImageView(logoImage);
	    logoView.setFitHeight(100);
	    logoView.setFitWidth(200);
	    logoView.setPreserveRatio(true);
		
	    // creates log in label and pads it to the right and styles label
		Label logInLabel = new Label("Log in");
		logInLabel.setPadding(new Insets(0, 0, 0, 250));
		logInLabel.setStyle("-fx-font-size: 26px; -fx-text-fill: blue");
		
		// creates log in label and HBox, styles label and pads box to the right
		Label details = new Label("Enter your details to continue");
		details.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-font-smoothing-type: lcd; -fx-text-fill: black");
		HBox detailsBox = new HBox(details);
		detailsBox.setPadding(new Insets(0, 0, 0, 210));
		
		// creates username label and HBox, styles label and pads box to the right
		Label usernameLabel = new Label("Enter your username");
		usernameLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-font-smoothing-type: lcd; -fx-text-fill: black");
		HBox usernameLabelBox = new HBox(usernameLabel);
		usernameLabelBox.setPadding(new Insets(0, 0, 0, 175));
		
		// creates username text field, increases width of text field, creates HBox and 
		// pads box to the right
		TextField usernameField = new TextField();
		usernameField.setPrefWidth(230);
		HBox usernameFieldBox = new HBox(usernameField);
		usernameFieldBox.setPadding(new Insets(0, 0, 0, 175));
		
		// creates password label and HBox, styles label, and pads box to the right
		Label passwordLabel = new Label("Enter your password");
		passwordLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-font-smoothing-type: lcd; -fx-text-fill: black");
		HBox passwordLabelBox = new HBox(passwordLabel);
		passwordLabelBox.setPadding(new Insets(0, 0, 0, 175));
		
		// creates password password field, increases width of password field, creates 
		// HBox and pads box to the right
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefWidth(230);
		HBox passwordFieldBox = new HBox(passwordField);
		passwordFieldBox.setPadding(new Insets(0, 0, 0, 175));
		
		// creates 'log in as' label and HBox and pads box to the right
		Label logInAsLabel = new Label("Login as:");
		logInAsLabel.setStyle("-fx-font-size: 10px;");
		HBox logInAsBox = new HBox(logInAsLabel);
		logInAsBox.setPadding(new Insets(0, 0, 0, 175));
		
		// creates a radio button for each account type
		RadioButton doctor = new RadioButton("Doctor");
		RadioButton nurse = new RadioButton("Nurse");
		RadioButton patient = new RadioButton("Patient");
		
		// creates toggle group to assign for each radio button to be assigned to
		ToggleGroup toggleGroup = new ToggleGroup();
		doctor.setToggleGroup(toggleGroup);
		nurse.setToggleGroup(toggleGroup);
		patient.setToggleGroup(toggleGroup);
		
		// creates HBox for radio buttons, sets spacing between buttons in box, and
		// pads box to the right
		HBox radioButtons = new HBox();
		radioButtons.getChildren().addAll(doctor, nurse, patient);
		radioButtons.setSpacing(30);
		radioButtons.setPadding(new Insets(0, 0, 0, 175));
		
		// creates log in button, pads the button internally, and creates an HBox, styles the button
		// and pads box to the right
		Button logInButton = new Button("Log in");
		logInButton.setStyle("-fx-padding: 0px 95px 0px 101px; -fx-background-color: " + toRGBCode(Color.rgb(134, 154, 176)));
		HBox logInButtonBox = new HBox(logInButton);
		logInButtonBox.setPadding(new Insets(0, 0, 0, 175));
		
		// creates sign up button, pads the button internally, and creates an HBox, styles the button
		// and pads box to the right
		Button signUp = new Button("Sign up");
		signUp.setStyle("-fx-padding: 0px 95px 0px 97px; -fx-background-color: " + toRGBCode(Color.rgb(173, 216, 230)));
		HBox signUpBox = new HBox(signUp);
		signUpBox.setPadding(new Insets(0, 0, 0, 175));
		
		// creates grid pane									
		GridPane logInScreen = new GridPane();
		
		// sets horizontal and vertical distance between elements in grid pane
		logInScreen.setHgap(10);
		logInScreen.setVgap(10);

		// creates rows in a grid pane and assigns an element per row 
		logInScreen.addRow(0, logoView);
		logInScreen.addRow(1, logInLabel);
		logInScreen.addRow(2, detailsBox);
		logInScreen.addRow(3, usernameLabelBox);
		logInScreen.addRow(4, usernameFieldBox);
		logInScreen.addRow(5, passwordLabelBox);
		logInScreen.addRow(6, passwordFieldBox);
		logInScreen.addRow(7, logInAsBox);
		logInScreen.addRow(8, radioButtons);
		logInScreen.addRow(9, logInButtonBox);
		logInScreen.addRow(10, signUpBox);
		
		// creates scene with specified length x width and assigns grid pane to it, 
		// changes background color of scene, calls utility method to color the border 
		// differently and set the border's width
		Scene scene = new Scene(logInScreen, 650, 500);
		scene.getRoot().setStyle("-fx-background-color: white; -fx-border-color: " + toRGBCode(Color.rgb(200, 200, 200)) + "; -fx-border-width: 10px");
		
		// sets the scene of the stage 
		stage.setScene(scene);
		
		// shows the scene on the stage
		stage.show();
		
		// creates 'empty field message' label, pads it to the right, adds it to the grid pane,
		// and sets it to be invisible
		Label emptyFieldMessage = new Label("At least one field is empty.");
		emptyFieldMessage.setPadding(new Insets(0, 0, 0, 225));
		logInScreen.addRow(11, emptyFieldMessage);
		emptyFieldMessage.setVisible(false);
		
		// creates 'incorrect input message' label, pads it to the right, adds it to the grid pane,
		// and sets it to be invisible
		Label incorrectInputMessage = new Label("Username/Password/Account Type is incorrect.");
		incorrectInputMessage.setPadding(new Insets(0, 0, 0, 175));
		logInScreen.addRow(12, incorrectInputMessage);
		incorrectInputMessage.setVisible(false);
		
		// when log in button is clicked
		logInButton.setOnAction(event -> {
			// clear messages
			emptyFieldMessage.setVisible(false);
			incorrectInputMessage.setVisible(false);
			// gets  values from text fields
			String username = usernameField.getText();
			String password = passwordField.getText();
			// other variables
			String accountType = "";
			boolean emptyField = false;
			
			// these 3 ifs check if the radio button selected is either doctor, nurse, or patient,
			// and if none of them are selected, then the field is not selected and shows the 
			// error message
			if (doctor.isSelected()) {
				accountType = "Doctor";
			}
			
			else if (nurse.isSelected()) {
				accountType = "Nurse";
			}
			
			else if (patient.isSelected()) {
				accountType = "Patient";
			}
			
			else {
				emptyField = true;
				emptyFieldMessage.setVisible(true);
			}
			
			// checks if empty field is false
			if (!emptyField) {
				// directory path to my new-eclipse-workspace folder
				// assigns files ending with ".txt" to array
				File[] files = directory.listFiles((dir, name) -> name.endsWith("_LoginInfo.txt"));
				// checks that files contains at least one file 
				
				if (files != null) {
					// for-each loop looping through files
					for (File file : files) {
						// tries reading a file
						try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
							String accountType1 = reader.readLine();
		                    String verificationCode = reader.readLine(); // Not used for login validation but necessary for reading sequence
		                    String firstName = reader.readLine(); // Not used for login validation but necessary for reading sequence
		                    String lastName = reader.readLine(); // Not used for login validation but necessary for reading sequence
		                    String month = reader.readLine(); // Not used for login validation but necessary for reading sequence
		                    String day = reader.readLine(); // Not used for login validation but necessary for reading sequence
		                    String year = reader.readLine(); // Not used for login validation but necessary for reading sequence
		                    String username1 = reader.readLine();
		                    String password1 = reader.readLine();
		                    String passwordConfirmation = reader.readLine();
									
							
							// if username, password, and account type from the file match what
							// was read-in from the user, then check which account type was selected;
							if (username1.equals(username) &&
								password1.equals(password) &&
								accountType1.equals(accountType)) {
								
								// if account type is equal to one of these, then the user will be taken
								// to that page
								if (accountType.equals("Patient") && verificationCode.equals("000000")) {
									//!!!!!! NEEDS PATIENT CLASS INSTANTIATION AND START METHOD CALL !!!!!!
									PatientView paitentView = new PatientView();
									paitentView.start(stage);
								} else if (accountType.equals("Nurse") && verificationCode.equals("298451")) {
									//!!!!!! NEEDS NURSE CLASS INSTANTIATION AND START METHOD CALL !!!!!!
									NurseView nurseView = new NurseView();
									nurseView.start(stage);
								} else if (accountType.equals("Doctor") && verificationCode.equals("380495")) {
									//!!!!!! NEEDS DOCTOR CLASS INSTANTIATION AND START METHOD CALL !!!!!!
									DoctorView doctorView = new DoctorView();
									doctorView.start(stage);
								}
						}
					
					
					// close file reading
					reader.close();
					
					// catches an exception
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// if none of the other logic worked, then there was an issue with input,
				// so this message will become visible
				incorrectInputMessage.setVisible(true);
			}
		}
	}	
			// reset 'empty field variable'
			emptyField = false;
		});
		
		signUp.setOnAction(event -> {
			SignUp signUpPage = new SignUp();
			signUpPage.start(stage);
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// utility method that allowed a custom color to be generated
	private static String toRGBCode(Color color) {
		return String.format("#%02X%02X%02X",
				(int) (color.getRed() * 255),
				(int) (color.getBlue() * 255),
				(int) (color.getGreen() * 255));
	}
}
