package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
		
		Label signUp = new Label("Sign Up");
		signUp.setStyle("-fx-font-size: 29px; -fx-font-weight: bold;");

		Label detailProvision = new Label("Please provide your details to continue");
		Label accountTypeLabel = new Label("Account Type:");
		Label verificationCodeLabel = new Label("Verification Code:");
		Label firstNameLabel = new Label("First Name:");
		Label lastNameLabel = new Label("Last Name:");
		Label dateOfBirthLabel = new Label("Date of Birth");
		Label usernameLabel = new Label("Username:");
		Label usernameCriteria1 = new Label("* Must be between 5 and 16 characters long,");
		Label usernameCriteria2 = new Label("* Minimum of 1 uppercase letter,");
		Label usernameCriteria3 = new Label("* Minimum of 1 numeric character");	
		Label passwordLabel = new Label("Create your password");
		Label passwordConfirmationLabel = new Label("Confirm your password");
		Label passwordCriteria1 = new Label("* Must be between 5 and 16 characters long,");
		Label passwordCriteria2 = new Label("* Minimum of 1 uppercase letter,");
		Label passwordCriteria3 = new Label("* Minimum of 1 numeric character,");
		Label passwordCriteria4 = new Label("* Minimum of 1 special character");
		Label alrdyHasAccount = new Label("Already have an account?");
		Label emptyFieldMessage = new Label("At least one field is empty.");
		Label inputMatchMessage = new Label("Username/Password Confirmation match found.");
		Label incorrectCodeMessage = new Label("Incorrect Verification Code.");
		Label usernameCriteriaMismatchMessage = new Label("At least one criteria for username is unmet.");
		Label passwordCriteriaMismatchMessage = new Label("At least one criteria for password is unmet.");
		
		emptyFieldMessage.setVisible(false);
		inputMatchMessage.setVisible(false);
		incorrectCodeMessage.setVisible(false);
		usernameCriteriaMismatchMessage.setVisible(false);		
		passwordCriteriaMismatchMessage.setVisible(false);
		
		passwordCriteria1.setStyle("-fx-text-fill: #808080;");
		passwordCriteria2.setStyle("-fx-text-fill: #808080;");
		passwordCriteria3.setStyle("-fx-text-fill: #808080;");
		passwordCriteria4.setStyle("-fx-text-fill: #808080;");
		usernameCriteria1.setStyle("-fx-text-fill: #808080;");
		usernameCriteria2.setStyle("-fx-text-fill: #808080;");
		usernameCriteria3.setStyle("-fx-text-fill: #808080;");
		
		PasswordField passwordField = new PasswordField();
		PasswordField passwordConfirmationField = new PasswordField();

		TextField verificationCodeField = new TextField();
		verificationCodeField.setPromptText("Leave Empty If Patient.");
		verificationCodeField.setPrefWidth(200);

		TextField firstNameField = new TextField();

		TextField lastNameField = new TextField();
		
		TextField usernameField = new TextField();
		usernameField.setMinWidth(370);
		
		Button createAccount = new Button("Create Account");
		Button logIn = new Button("Log in");

		createAccount.setMinWidth(300);
		createAccount.setStyle("-fx-background-radius: 5px; -fx-background-color: #87CEFA;");
		logIn.setStyle("-fx-background-radius: 5px; -fx-background-color: #00BFFF;");
		
		// creates combo box for the 3 account types, sets width of box, adds patient, doctor,
		// and nurse as options, sets default text to "choose", and styles combo box
		ComboBox accountTypes = new ComboBox();
		accountTypes.setPrefWidth(100);
		accountTypes.getItems().addAll("Patient", "Nurse", "Doctor");
		accountTypes.setValue("Choose");
		accountTypes.setStyle("-fx-background-radius: 20px; -fx-background-color: #87CEFA;");

		
		ComboBox months = new ComboBox();
		months.setStyle("-fx-background-radius: 20px; -fx-background-color: #87CEFA;");
		months.setValue("Month");
		int minMonth = 1;
		int maxMonth = 12;
		for (int i = minMonth; i <= maxMonth; i++) {
			months.getItems().add(i);
		}
		months.getItems().addAll();	
		
		ComboBox days = new ComboBox();
		days.setStyle("-fx-background-radius: 20px; -fx-background-color: #87CEFA;");
		days.setValue("Day");
		int minDay = 1;
		int maxDay = 31;
		for (int i = minDay; i <= maxDay; i++) {
			days.getItems().add(i);
		}
		days.getItems().addAll();
		
		ComboBox years = new ComboBox();
		years.setStyle("-fx-background-radius: 20px; -fx-background-color: #87CEFA;");
		years.setValue("Year");
		int minYear = 1930;
		int maxYear = 2023;
		for (int i = minYear; i <= maxYear; i++) {
			years.getItems().add(i);
		}
		years.getItems().addAll();
		
		HBox logoHbox = new HBox(logoView);
		VBox signUpVbox = new VBox(signUp,detailProvision);
		HBox hbox1 = new HBox(signUpVbox);
		HBox accountTypeHbox = new HBox(accountTypeLabel, accountTypes);
		HBox codeHbox = new HBox(verificationCodeLabel, verificationCodeField);
		HBox hbox2 = new HBox(accountTypeHbox,codeHbox);
		VBox firstNameVbox = new VBox(firstNameLabel,firstNameField);
		VBox lastNameVbox = new VBox(lastNameLabel,lastNameField);
		HBox dateHbox = new HBox(months,days,years);
		VBox dobVbox = new VBox(dateOfBirthLabel, dateHbox);


		HBox hbox3 = new HBox(firstNameVbox,lastNameVbox,dobVbox);
		
		VBox usernameVbox = new VBox(usernameLabel,usernameField);

		VBox usernameNoteVbox = new VBox(usernameCriteria1,usernameCriteria2,usernameCriteria3);
		HBox hbox4 = new HBox(usernameVbox,usernameNoteVbox);
		
		VBox passwordVbox = new VBox (passwordLabel,passwordField);

		VBox passwordComfirmVbox = new VBox (passwordConfirmationLabel, passwordConfirmationField);
		
		VBox passwordNotes = new VBox(passwordCriteria1,passwordCriteria2,passwordCriteria3,passwordCriteria4);
		HBox hbox5 = new HBox(passwordVbox,passwordComfirmVbox,passwordNotes);
		
		HBox createAccountHbox = new HBox(createAccount);
		
		HBox loginHbox = new HBox(alrdyHasAccount, logIn);
		VBox errorMessageVbox = new VBox(emptyFieldMessage,inputMatchMessage,incorrectCodeMessage,usernameCriteriaMismatchMessage,passwordCriteriaMismatchMessage);
		HBox errorHbox = new HBox(errorMessageVbox);
		
		VBox center = new VBox(hbox2,hbox3,hbox4,hbox5);
		HBox center2 = new HBox(center);
		
		VBox bottom = new VBox(createAccountHbox,loginHbox,errorHbox);
		HBox bottom2 = new HBox(bottom);

		
		VBox VBox1 = new VBox(logoHbox,hbox1,center2,bottom2);
		VBox1.setSpacing(20);
			
		
		accountTypeHbox.setSpacing(30);
		codeHbox.setSpacing(30);
		firstNameVbox.setSpacing(5);
		lastNameVbox.setSpacing(5);
		dateHbox.setSpacing(30);
		dobVbox.setSpacing(5);
		hbox3.setSpacing(47);
		usernameVbox.setSpacing(5);
		hbox4.setSpacing(50);
		passwordVbox.setSpacing(5);
		passwordComfirmVbox.setSpacing(5);
		hbox5.setSpacing(50);
		loginHbox.setSpacing(20);
		center2.setPadding(new Insets(20));
		bottom.setSpacing(20);

	
		logoHbox.setAlignment(Pos.CENTER_LEFT);
		signUpVbox.setAlignment(Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		accountTypeHbox.setAlignment(Pos.CENTER);
		codeHbox.setAlignment(Pos.CENTER);
		center2.setAlignment(Pos.CENTER);
		center.setAlignment(Pos.CENTER);
		center.setSpacing(20);
		hbox2.setAlignment(Pos.CENTER_LEFT);
		hbox3.setAlignment(Pos.CENTER_LEFT);
		hbox4.setAlignment(Pos.CENTER_LEFT);
		hbox5.setAlignment(Pos.CENTER_LEFT);
		createAccountHbox.setAlignment(Pos.CENTER);
		loginHbox.setAlignment(Pos.CENTER);
		bottom2.setAlignment(Pos.CENTER);		
		hbox2.setSpacing(50);
				
		BorderPane mainLayout = new BorderPane();
		mainLayout.setCenter(VBox1);
		Scene scene = new Scene(mainLayout, 1020, 680);
		scene.getRoot().setStyle("-fx-background-color: white; -fx-border-color: " + toRGBCode(Color.rgb(200, 200, 200)) + "; -fx-border-width: 10px");
		
		stage.setScene(scene);	
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
