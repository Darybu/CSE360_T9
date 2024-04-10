package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.PasswordField;

import java.io.*;

public class SignUp extends Application {
	@Override
	public void start(Stage stage) {
		// allows brand image to show
		Image logoImage = new Image(getClass().getResourceAsStream("123.png"));
	    ImageView logoView = new ImageView(logoImage);
	    logoView.setFitHeight(75);
	    logoView.setFitWidth(200);
	    logoView.setPreserveRatio(true);
		
	    // creates sign up label and HBox, styles label and pads box
		Label signUp = new Label("Sign Up");
		signUp.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");
		HBox signUpBox = new HBox(signUp);
		signUpBox.setPadding(new Insets(0, 0, 0, 375));
		
		// creates details label and HBox, styles label, and pads box
		Label detailProvision = new Label("Please provide your details to continue");
		detailProvision.setStyle("-fx-font-size: 12px");
		HBox detailProvisionBox = new HBox(detailProvision);
		detailProvisionBox.setPadding(new Insets(0, 0, 0, 325));
		
		// creates VBox for sign up and details 
		VBox signUpAndDetailBox = new VBox();
		signUpAndDetailBox.getChildren().addAll(signUpBox, detailProvisionBox);
		
		// 'creates account type label'
		Label accountTypeLabel = new Label("Account Type:");
		
		// creates combo box for the 3 account types, sets width of box, adds patient, doctor,
		// and nurse as options, sets default text to "choose", and styles combo box
		ComboBox accountTypes = new ComboBox();
		accountTypes.setPrefWidth(100);
		accountTypes.getItems().addAll("Patient", "Nurse", "Doctor");
		accountTypes.setValue("Choose");
		accountTypes.setStyle("-fx-background-radius: 20px; -fx-background-color: " + toRGBCode(Color.rgb(173, 216, 230)));
		
		// creates v'erification code' label
		Label verificationCodeLabel = new Label("Verification Code:");
		
		// creates text field for verification code, sets text inside, and sets width of field
		TextField verificationCodeField = new TextField();
		verificationCodeField.setPromptText("Leave Empty If Patient.");
		verificationCodeField.setPrefWidth(200);
		
		// creates HBox for account type label and combo box, and verification code label,
		// sets spacing between these elements, and pads box to right
		HBox accTypeAndCode = new HBox();
		accTypeAndCode.getChildren().addAll(accountTypeLabel, accountTypes, verificationCodeLabel, verificationCodeField);
		accTypeAndCode.setSpacing(30);
		accTypeAndCode.setPadding(new Insets(0, 0, 0, 145));
		
		// creates labels for first and last name, and date of birth
		Label firstNameLabel = new Label("First Name:");
		Label lastNameLabel = new Label("Last Name:");
		Label dateOfBirthLabel = new Label("Date of Birth");
		
		// creates HBox for 3 above labels, sets box to the center of screen, sets spacing
		// between labels, and pads box
		HBox namesAndBirthdateLabelsBox = new HBox();
		namesAndBirthdateLabelsBox.getChildren().addAll(firstNameLabel, lastNameLabel, dateOfBirthLabel);
		namesAndBirthdateLabelsBox.setAlignment(Pos.CENTER);
		namesAndBirthdateLabelsBox.setSpacing(120);
		namesAndBirthdateLabelsBox.setPadding(new Insets(0, 225, 0, 100));
		
		// creates first name field
		TextField firstNameField = new TextField();
		
		// creates last name field
		TextField lastNameField = new TextField();
		
		// creates combo box for months, sets default text to month, utilizes a loop
		// so that 1-12 appear as values in the combo box, and styles combo box
		ComboBox months = new ComboBox();
		months.setStyle("-fx-background-radius: 20px; -fx-background-color: " + toRGBCode(Color.rgb(173, 216, 230)));
		months.setValue("Month");
		int minMonth = 1;
		int maxMonth = 12;
		for (int i = minMonth; i <= maxMonth; i++) {
			months.getItems().add(i);
		}
		months.getItems().addAll();	
		
		// creates combo box for days, sets default text to day, utilizes a loop
		// so that 1-31 appear as values in the combo box, and styles combo box
		ComboBox days = new ComboBox();
		days.setStyle("-fx-background-radius: 20px; -fx-background-color: " + toRGBCode(Color.rgb(173, 216, 230)));
		days.setValue("Day");
		int minDay = 1;
		int maxDay = 31;
		for (int i = minDay; i <= maxDay; i++) {
			days.getItems().add(i);
		}
		days.getItems().addAll();
		
		// creates combo box for years, sets default text to year, utilizes a loop
		// so that 1930-2023 appear as values in the combo box, and styles combo box
		ComboBox years = new ComboBox();
		years.setStyle("-fx-background-radius: 20px; -fx-background-color: " + toRGBCode(Color.rgb(173, 216, 230)));
		years.setValue("Year");
		int minYear = 1930;
		int maxYear = 2023;
		for (int i = minYear; i <= maxYear; i++) {
			years.getItems().add(i);
		}
		years.getItems().addAll();

		// creates HBox for name and DOB, sets box to center of screen, sets spacing
		// between elements, and pads box to right
		HBox nameAndDateOfBirthBox = new HBox();
		nameAndDateOfBirthBox.getChildren().addAll(firstNameField, lastNameField, months, days, years);
		nameAndDateOfBirthBox.setAlignment(Pos.CENTER);
		nameAndDateOfBirthBox.setSpacing(25);
		nameAndDateOfBirthBox.setPadding(new Insets(0, 0, 0, 75));

		// creates username label
		Label usernameLabel = new Label("Username:");
		
		// creates username label box, sets box to center of screen, and pads box
		HBox usernameLabelBox = new HBox(usernameLabel);
		usernameLabelBox.setAlignment(Pos.CENTER);
		usernameLabelBox.setPadding(new Insets(0, 615, 0, 115));
		
		// creates username field and sets width of it
		TextField usernameField = new TextField();
		usernameField.setPrefWidth(300);
		
		// creates a label for each username criteria and styles each label
		Label usernameCriteria1 = new Label("* Must be between 5 and 16 characters long,");
		usernameCriteria1.setStyle("-fx-text-fill: " + toRGBCode(Color.rgb(150, 150, 150)));
		Label usernameCriteria2 = new Label("* Minimum of 1 uppercase letter,");
		usernameCriteria2.setStyle("-fx-text-fill: " + toRGBCode(Color.rgb(150, 150, 150)));
		Label usernameCriteria3 = new Label("* Minimum of 1 numeric character");
		usernameCriteria3.setStyle("-fx-text-fill: " + toRGBCode(Color.rgb(150, 150, 150)));
		
		// creates VBox to assign the above labels, and sets box to center of screen
		VBox usernameCriteriaBox = new VBox();
		usernameCriteriaBox.getChildren().addAll(usernameCriteria1, usernameCriteria2, usernameCriteria3);
		usernameCriteriaBox.setAlignment(Pos.CENTER);
		
		// creates HBox for username field and criteria box, pads box to right, and sets
		// spacing between elements
		HBox usernameBox = new HBox();
		usernameBox.getChildren().addAll(usernameField, usernameCriteriaBox);
		usernameBox.setPadding(new Insets(0, 0, 0, 135));
		usernameBox.setSpacing(30);
		
		// creates password and 'password confirmation' label
		Label passwordLabel = new Label("Create your password");
		Label passwordConfirmationLabel = new Label("Confirm your password");
		
		// creates HBox for the 2 aabove labels, pads box to right, sets spacing between
		// elements
		HBox passwordLabelBox = new HBox();
		passwordLabelBox.getChildren().addAll(passwordLabel, passwordConfirmationLabel);
		passwordLabelBox.setPadding(new Insets(0, 0, 0, 135));
		passwordLabelBox.setSpacing(100);
		
		// creates password and 'password confirmation' fields, and sets width of each
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefWidth(200);
		PasswordField passwordConfirmationField = new PasswordField();
		passwordConfirmationField.setPrefWidth(200);
		
		// creates one label for each password criteria and styles each label
		Label passwordCriteria1 = new Label("* Must be between 5 and 16 characters long,");
		passwordCriteria1.setStyle("-fx-text-fill: " + toRGBCode(Color.rgb(150, 150, 150)));
		Label passwordCriteria2 = new Label("* Minimum of 1 uppercase letter,");
		passwordCriteria2.setStyle("-fx-text-fill: " + toRGBCode(Color.rgb(150, 150, 150)));
		Label passwordCriteria3 = new Label("* Minimum of 1 numeric character,");
		passwordCriteria3.setStyle("-fx-text-fill: " + toRGBCode(Color.rgb(150, 150, 150)));
		Label passwordCriteria4 = new Label("* Minimum of 1 special character");
		passwordCriteria4.setStyle("-fx-text-fill: " + toRGBCode(Color.rgb(150, 150, 150)));
		
		// create VBox for the password criteria 
		VBox passwordCriteriaBox = new VBox();
		passwordCriteriaBox.getChildren().addAll(passwordCriteria1, passwordCriteria2, passwordCriteria3, passwordCriteria4);
		
		// creates HBox for password and password confirmation fields and password criteria,
		// sets spacing between elements, and pads box to right
		HBox passwordBox = new HBox();
		passwordBox.getChildren().addAll(passwordField, passwordConfirmationField, passwordCriteriaBox);
		passwordBox.setSpacing(20);
		passwordBox.setPadding(new Insets(0, 0, 0, 135));
		
		// creates 'create account' button, pads it,and styles button
		Button createAccount = new Button("Create Account");
		createAccount.setPadding(new Insets(5, 75, 5, 75));
		createAccount.setStyle("-fx-background-color: " + toRGBCode(Color.rgb(173, 216, 230)));
		
		// creates HBox for above button
		HBox createAccountBox = new HBox(createAccount);
		
		// creates 'already has account' label and styles label
		Label alrdyHasAccount = new Label("Already have an account?");
		alrdyHasAccount.setStyle("-fx-font-size: 12px");
		
		// creates 'log in' button and styles button
		Button logIn = new Button("Log in");
		logIn.setStyle("-fx-background-color: " + toRGBCode(Color.rgb(134, 154, 176)));

		// creates HBox for above label and button, sets spacing between these elements,
		// and pads the box
		HBox alrdyHasAccountBox = new HBox();
		alrdyHasAccountBox.getChildren().addAll(alrdyHasAccount, logIn);
		alrdyHasAccountBox.setSpacing(10);
		alrdyHasAccountBox.setPadding(new Insets(10, 0, 0, 20));
		
		// creates VBox for 'create account' box and 'already has account' box, and pads box
		VBox accountBox = new VBox();
		accountBox.getChildren().addAll(createAccountBox, alrdyHasAccountBox);
		accountBox.setPadding(new Insets(-40, 0, 0, 300));
		
		// creates grid pane and sets background color
		GridPane signUpScreen = new GridPane();
		signUpScreen.setStyle("-fx-background-color: white");
		
		// centers grid pane and sets the horizontal and vertical distances between elements
		// in grid pane
		signUpScreen.setAlignment(Pos.CENTER);
		signUpScreen.setHgap(10);
		signUpScreen.setVgap(20);
		
		// creates and assigns an element to a row in the grid pane
		signUpScreen.addRow(0, logoView);
		signUpScreen.addRow(1, signUpAndDetailBox);
		signUpScreen.addRow(3, accTypeAndCode);
		signUpScreen.addRow(4, namesAndBirthdateLabelsBox);
		signUpScreen.addRow(5, nameAndDateOfBirthBox);
		signUpScreen.addRow(6, usernameLabelBox);
		signUpScreen.addRow(7, usernameBox);
		signUpScreen.addRow(8, passwordLabelBox);
		signUpScreen.addRow(9, passwordBox);
		signUpScreen.addRow(10, accountBox);
		
		// creates 'empty field' message, pads it to right, adds it to grid pane,
		// and makes it invisible
		Label emptyFieldMessage = new Label("At least one field is empty.");
		emptyFieldMessage.setPadding(new Insets(0, 0, 0, 340));
		signUpScreen.addRow(11, emptyFieldMessage);
		emptyFieldMessage.setVisible(false);
		
		// creates 'input mismatch' message, pads it to right, adds it to grid pane,
		// and makes it invisible
		Label inputMatchMessage = new Label("Username/Password Confirmation match found.");
		inputMatchMessage.setPadding(new Insets(0, 0, 0, 290));
		signUpScreen.addRow(12, inputMatchMessage);
		inputMatchMessage.setVisible(false);
		
		// creates 'incorrect code' message, pads it to right, adds it to grid pane,
		// and makes it invisible
		Label incorrectCodeMessage = new Label("Incorrect Verification Code.");
		incorrectCodeMessage.setPadding(new Insets(0, 0, 0, 340));
		signUpScreen.addRow(13, incorrectCodeMessage);
		incorrectCodeMessage.setVisible(false);
		
		// creates 'username criteria mismatch' message, pads it to right, adds it to grid pane,
		// and makes it invisible
		Label usernameCriteriaMismatchMessage = new Label("At least one criteria for username is unmet.");
		usernameCriteriaMismatchMessage.setPadding(new Insets(0, 0, 0, 300));
		signUpScreen.addRow(14, usernameCriteriaMismatchMessage);
		usernameCriteriaMismatchMessage.setVisible(false);
		
		// creates 'password criteria mismatch' message, pads it to right, adds it to grid pane,
		// and makes it invisible
		Label passwordCriteriaMismatchMessage = new Label("At least one criteria for password is unmet.");
		passwordCriteriaMismatchMessage.setPadding(new Insets(0, 0, 0, 300));
		signUpScreen.addRow(15, passwordCriteriaMismatchMessage);
		passwordCriteriaMismatchMessage.setVisible(false);
		
		// creates scene with specified length x width, and sets border color and width of scene
		Scene scene = new Scene(signUpScreen, 900, 775);
		scene.getRoot().setStyle("-fx-background-color: white; -fx-border-color: " + toRGBCode(Color.rgb(200, 200, 200)) + "; -fx-border-width: 10px");
		
		// sets scene to stage
		stage.setScene(scene);
		
		// shows scene on stage
		stage.show();
		
		// when 'create account' button is clicked
		createAccount.setOnAction(event -> {
			// make error messages invisible
			emptyFieldMessage.setVisible(false);
			inputMatchMessage.setVisible(false);
			incorrectCodeMessage.setVisible(false);
			usernameCriteriaMismatchMessage.setVisible(false);
			// get all values that user can input
			String accountType = accountTypes.getValue().toString();
			String verificationCode = verificationCodeField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String day = days.getValue().toString();
			String month = months.getValue().toString();
			String year = years.getValue().toString();
			String username = usernameField.getText();
			String password = passwordField.getText();
			String passwordConfirmation = passwordConfirmationField.getText();
			// creates utility boolean values
			boolean emptyField = false;
			boolean validUsername = false;
			boolean validPasswordConfirmation = false;
			boolean inputMatch = false;
			boolean incorrectCode = false;
			
			// if account type input is equal to doctor and verification code entered is
			// that of doctor then skip
			if(accountType.equals("Doctor") &&  
			   verificationCode.equals("380495")) {
				// if account type input is equal to nurse and verification code entered is
				// that of nurse then skip	
			} else if (accountType.equals("Nurse") && 
					   verificationCode.equals("298451")) {
				// if account type input is equal to patient and verification code was not entered
				// then skip
			} else if (accountType.equals("Patient") && verificationCode.isEmpty()) {
					   verificationCode = "000000";
			} else {
				// any failure to match above results in an incorrect code error message display
				incorrectCode = true;
				incorrectCodeMessage.setVisible(true);
			}
			
			// if any field did not have an input trigger empty field message
			if (accountType.isEmpty() ||
				verificationCode.isEmpty() ||
				firstName.isEmpty() ||
				lastName.isEmpty() ||
				day.isEmpty() ||
				month.isEmpty() ||
				year.isEmpty() ||
				username.isEmpty() ||
				password.isEmpty() ||
				passwordConfirmation.isEmpty()) {
					emptyField = true;
					emptyFieldMessage.setVisible(true);
				}
			
			// checks criteria of inputted username; will display error message if not matching
			if (username.length() >= 5 &&
				username.length() <= 16 &&
				username.matches(".*[A-Z].*") &&
				username.matches(".*\\d.*")) {
					validUsername = true;
			} else {
				validUsername = false;
				usernameCriteriaMismatchMessage.setVisible(true);
			}
			
			// checks criteria of inputted password confirmation, as well as equality
			// of password field; will display error message if not matching
			if (passwordConfirmation.length() >= 5 &&
				passwordConfirmation.length() <= 16 &&
				passwordConfirmation.matches(".*[A-Z].*") &&
				passwordConfirmation.matches(".*\\d.*") &&
				passwordConfirmation.matches(".*[!@#$%^&*()].*") &&
				password.equals(passwordConfirmation)) {
					validPasswordConfirmation = true;
			} else {
				validPasswordConfirmation = false;
				passwordCriteriaMismatchMessage.setVisible(true);
			}
			
			// if no problems occurred above
			if (!emptyField && validUsername && validPasswordConfirmation && !incorrectCode) {
				// directory path to my new-eclipse-workspace folder
				File directory = new File("");
				// assigns files ending with ".txt" to array
				File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));
				// checks that files contains at least one file 
				if (files != null) {
					// for-each loop looping through files
					for (File file : files) {
						// tries reading a file
						try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
							String line;
							// while the file contains text
							while ((line = reader.readLine()) != null) {
								// split line by comma into an array
								String[] fields = line.split(",");
								// assigns values
								
								
								// if inputed username or password confirmation matches
								// what was found in a file, then the input match error
								// message will display
								if (fields.length > 7 && fields[7].trim().equals(username)) {
										inputMatchMessage.setVisible(true);
										inputMatch = true;
										reader.close();
										break;
								} 
							}
							// catches exceptions
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			
			// if input match is false execute code below	
				if (!inputMatch) {
					try (BufferedWriter writer = new BufferedWriter(
							new FileWriter(username + "_" + accountType + "_LoginInfo"+ ".txt"))) {
						// since no match was found a new file will be created personal for the user
						// and every input will be written to it
						writer.write(accountType + "\n" + verificationCode + "\n" + firstName + "\n" + lastName + "\n"
								+ month + "\n" + day + "\n" + year + "\n" + username + "\n" + password + "\n"
								+ passwordConfirmation + "\r\n");
						writer.close();
						// returns the user to log in since account creation was successful
						LogIn returnBack = new LogIn();
						returnBack.start(stage);

						// catches exceptions
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				
				
			}
			// resets booleans
			emptyField = false;
			validUsername = false;
			validPasswordConfirmation = false;
			incorrectCode = false;
			inputMatch = false;
		});
		
		// when 'log in' button is clicked take user to 'log in' page
		logIn.setOnAction(event -> {
			LogIn logInScreen = new LogIn();
			logInScreen.start(stage);
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// utility method to create custom color
	private static String toRGBCode(Color color) {
		return String.format("#%02X%02X%02X",
				(int) (color.getRed() * 255),
				(int) (color.getBlue() * 255),
				(int) (color.getGreen() * 255));
	}
}
