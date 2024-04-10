package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*;

import java.io.*;


public class LogIn extends Application{
	File directory = new File("/Users/tuannguyen/ASUworkspace/PediatricOfficeProgram");  // fix this !!!
	public static String userName = "";
	
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
		logInLabel.setStyle("-fx-font-size: 34px; -fx-font-weight: bold;");
		
		// creates log in label and HBox, styles label and pads box to the right
		Label details = new Label("Enter your details to continue");
		details.setFont(Font.font("Arial", 12));

		// creates username label and HBox, styles label and pads box to the right
		Label usernameLabel = new Label("Username:");
		usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		
		// creates username text field, increases width of text field, creates HBox and 
		// pads box to the right
		TextField usernameField = new TextField();
		usernameField.setPrefWidth(430);
		usernameField.setPromptText("Enter your username");
		
		// creates password label and HBox, styles label, and pads box to the right
		Label passwordLabel = new Label("Password:");
		passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		
		// creates password password field, increases width of password field, creates 
		// HBox and pads box to the right
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefWidth(430);
		passwordField.setPromptText("Enter your password");
		// creates 'log in as' label and HBox and pads box to the right
		Label logInAsLabel = new Label("Login as:");
		logInAsLabel.setStyle("-fx-font-size: 14px;");
		
		// creates 'empty field message' label, pads it to the right, adds it to the grid pane,
		// and sets it to be invisible
		Label emptyFieldMessage = new Label("At least one field is empty.");
		emptyFieldMessage.setFont(Font.font("Arial",  16));	
		emptyFieldMessage.setVisible(false);
		emptyFieldMessage.setStyle("-fx-text-fill: red;");
		// creates 'incorrect input message' label, pads it to the right, adds it to the grid pane,
		// and sets it to be invisible
		Label incorrectInputMessage = new Label("Username/Password/Account Type is incorrect.");
		incorrectInputMessage.setVisible(false);
		incorrectInputMessage.setFont(Font.font("Arial",  16));
		incorrectInputMessage.setStyle("-fx-text-fill: red;");

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

		
		// creates log in button, pads the button internally, and creates an HBox, styles the button
		// and pads box to the right
		Button logInButton = new Button("Log in");
		logInButton.setPrefSize(300, 40);
		logInButton.setFont(Font.font("Arial", 15));
		logInButton.setStyle("-fx-background-radius: 10px; -fx-background-color: #87CEFA;");

		
		// creates sign up button, pads the button internally, and creates an HBox, styles the button
		// and pads box to the right
		Button signUp = new Button("Sign up");
		signUp.setPrefSize(300, 40);
		signUp.setFont(Font.font("Arial", 15));
		signUp.setStyle("-fx-background-radius: 10px; -fx-background-color: #1E90FF;");

		
		HBox logo = new HBox(logoView);
		logo.setAlignment(Pos.TOP_LEFT);
		
		HBox loginHbox = new HBox(logInLabel);
		loginHbox.setAlignment(Pos.CENTER);
		
		HBox detailsHBox = new HBox(details);
		detailsHBox.setAlignment(Pos.CENTER);
		
		HBox loginasHbox = new HBox(logInAsLabel);
		
		HBox radioButtons = new HBox(doctor, nurse, patient);
		radioButtons.setAlignment(Pos.CENTER);
		radioButtons.setSpacing(20);
		
		VBox userNameVbox = new VBox(usernameLabel,usernameField);
		userNameVbox.setSpacing(5);
		VBox passwordVbox = new VBox(passwordLabel,passwordField);
		passwordVbox.setSpacing(5);
		
		VBox error = new VBox(emptyFieldMessage,incorrectInputMessage);
		error.setAlignment(Pos.CENTER);
		error.setSpacing(10);
		HBox errorHbox = new HBox(error);
		errorHbox.setAlignment(Pos.CENTER);
		
		VBox center = new VBox(loginHbox,detailsHBox,userNameVbox,passwordVbox,loginasHbox,radioButtons,logInButton,signUp, errorHbox);
		center.setMaxWidth(600);
		center.setSpacing(20);
		center.setAlignment(Pos.CENTER);
		
		HBox center2 = new HBox(center);
		center2.setAlignment(Pos.CENTER);
		

		
		VBox vbox1 = new VBox(logo,center2);

		
		Scene scene = new Scene(vbox1, 1020, 680);
		scene.getRoot().setStyle("-fx-background-color: white; -fx-border-color: " + toRGBCode(Color.rgb(200, 200, 200)) + "; -fx-border-width: 10px");
		
		// sets the scene of the stage 
		stage.setScene(scene);
		
		// shows the scene on the stage
		stage.show();
		

		
		
		
		
		
		// when log in button is clicked
		logInButton.setOnAction(event -> {
			// clear messages
			emptyFieldMessage.setVisible(false);
			incorrectInputMessage.setVisible(false);
			// gets  values from text fields
			String username = usernameField.getText();
			userName = username;
			System.out.println("Username" + userName);
			
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
									PatientView paitentView = new PatientView(userName);
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
