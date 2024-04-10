package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NurseView extends Application {
	private static int patientID;
	private static String patient_name;

	public static ObservableList<String> upcomingAppointments = FXCollections.observableArrayList();
	public static ObservableList<String> notification = FXCollections.observableArrayList();
	public static ObservableList<String> visitSum = FXCollections.observableArrayList();
	public static List<String> patientSummaryList = new ArrayList<>();
	private Random random = new Random();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Nurse");
		HBox finalHbox = new HBox(10);
		
		// Load data from files
		loadAppointmentsData();
		loadVisitSumData();

		// Load the logo image
		Image logoImage = new Image(getClass().getResourceAsStream("123.png"));
		ImageView logoView = new ImageView(logoImage);
		logoView.setFitHeight(100);
		logoView.setFitWidth(200);
		logoView.setPreserveRatio(true);

		String style = "-fx-background-color: transparent; -fx-text-fill: black;";
		String hoverStyle = "-fx-background-color: #D3D3D3; -fx-text-fill: black;";

		// Header
		Text header = new Text("KidsHealth Pediatrics");
		header.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		Label welcome = new Label("Welcome, Nurse!");

		Label appointmentDetails = new Label("Appointment Details");
		appointmentDetails.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		appointmentDetails.setPadding(new Insets(20, 0, 0, 20));

		Label newNofitication = new Label("New Notifications");
		newNofitication.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		newNofitication.setPadding(new Insets(20, 0, 0, 20));

		ListView<Notification> notificationListView = new ListView<>(NotificationManager.getNotifications());
		notificationListView.setPrefWidth(300);
		notificationListView.setPrefHeight(240);
		notificationListView.setStyle("-fx-background-color: #f0f0f0;");
		
		notificationListView.setCellFactory(lv -> new ListCell<Notification>() {
		    private ImageView imageView = new ImageView();
		    {
		        imageView.setFitWidth(40);  // Set the image size as desired
		        imageView.setFitHeight(40);
		    }

		    @Override
		    protected void updateItem(Notification item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText(null);
		            setGraphic(null);
		        } else {
		            imageView.setImage(new Image(getClass().getResourceAsStream("human.jpg")));
		            setText(item.toString());
		            setGraphic(imageView);
		            setContentDisplay(ContentDisplay.LEFT);
		        }
		    }
		});

		Label upComingAppointment = new Label("Upcoming Appointment");
		upComingAppointment.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		upComingAppointment.setPadding(new Insets(20, 0, 0, 20));

		ListView<String> upcomingAppointmentsListView = new ListView<>(upcomingAppointments);
		upcomingAppointmentsListView.setPrefWidth(300);
		upcomingAppointmentsListView.setPrefHeight(240);
		upcomingAppointmentsListView.setStyle("-fx-background-color: #f0f0f0;");

		Label patientVistiSummary = new Label("Patient Visit Summary");
		patientVistiSummary.setFont(Font.font("Arial", FontWeight.BOLD, 30));

		ListView<String> visitSumListView = new ListView<>(visitSum);
		visitSumListView.setPrefWidth(500);
		visitSumListView.setPrefHeight(490);
		visitSumListView.setStyle("-fx-background-color: #f0f0f0;");
		visitSumListView.setPadding(new Insets(20, 20, 20, 20));

		// initialize all button
		Button healthCheckin = new Button("Health Check-in");
		Button message = new Button("Message");
		Button healthSummary = new Button("	> Health Summary");
		Button prescribedMedication = new Button("	> Prescribed Medication");
		Button immunization = new Button("	> Immunization");
		Button contactInfo = new Button("	> Contact Info");
		Button medicalRecord = new Button("Medical Record");
		Button insuranceInfo = new Button("	> Insurance Info");
		Button pharmacyInfo = new Button("	> Pharmacy Info");
		Button health_RelatedInformation = new Button("Health Related Information");
		Button searchBtn = new Button();
		Button addBtn = new Button();
		Button logoutBtn = new Button();
		
		medicalRecord.setDisable(true); 
		health_RelatedInformation.setDisable(true); 
		message.setDisable(true); 
		
		contactInfo.setVisible(false); // Initially hidden
		insuranceInfo.setVisible(false); // Initially hidden
		pharmacyInfo.setVisible(false); // Initially hidden
		
		VBox medicalRecordOptions = new VBox(healthSummary, prescribedMedication, immunization);
		medicalRecordOptions.setVisible(false); // Initially hidden
		medicalRecordOptions.setSpacing(15);
		medicalRecordOptions.setVisible(false);
		medicalRecordOptions.setManaged(false);
		
		addBtn.setText("Add");
		addBtn.setStyle("-fx-background-color: #87CEFA;");
		
		searchBtn.setText("search");
		searchBtn.setStyle("-fx-background-color: #87CEFA;");
		
		logoutBtn.setText("Log out");
		logoutBtn.setStyle("-fx-background-color: #87CEFA;");
		
		TextField searchPatient = new TextField();
		searchPatient.setPromptText("Search patient");		
		
		medicalRecord.setOnAction(event -> {
			System.out.println("Medical Record was clicked!");
			// Add more actions here
			boolean isVisible = medicalRecordOptions.isVisible();
			medicalRecordOptions.setVisible(!isVisible);
			medicalRecordOptions.setManaged(!isVisible);
		});
		
		healthCheckin.setOnAction(event -> {
			final Stage dialog = new Stage();
			dialog.setTitle("Health Check-in");
			dialog.initModality(Modality.APPLICATION_MODAL);
			// Create the form components
			Label nameLabel = new Label("Patient Name:");
			TextField nameField = new TextField();

			Label idLabel = new Label("Patient ID:");
			TextField idField = new TextField();

			Label heightLabel = new Label("Height (ft):");
			TextField heightField = new TextField();

			Label weightLabel = new Label("Weight (lbs):");
			TextField weightField = new TextField();

			Label bloodPressureLabel = new Label("Blood Pressure:");
			TextField bloodPressureField = new TextField();

			Label heartRateLabel = new Label("Heart Rate (bpm):");
			TextField heartRateField = new TextField();

			Label oxygenSaturationLabel = new Label("Oxygen Saturation (%):");
			TextField oxygenSaturationField = new TextField();

			Label temperatureLabel = new Label("Temperature (°F):");
			TextField temperatureField = new TextField();

			// Create a VBox to hold the form components
			VBox formBox = new VBox(10);
			formBox.setPadding(new Insets(20));
			formBox.getChildren().addAll(nameLabel, nameField, idLabel, idField, heightLabel, heightField, weightLabel,
					weightField, bloodPressureLabel, bloodPressureField, heartRateLabel, heartRateField,
					oxygenSaturationLabel, oxygenSaturationField, temperatureLabel, temperatureField);

			// Create the submit button
			Button submitButton = new Button("Submit");
			submitButton.setOnAction(submitEvent -> {
				// Get the values from the form fields
				String patientName = nameField.getText();
				String patientId = idField.getText();
				double height = Double.parseDouble(heightField.getText());
				double weight = Double.parseDouble(weightField.getText());
				String bloodPressure = bloodPressureField.getText();
				int heartRate = Integer.parseInt(heartRateField.getText());
				int oxygenSaturation = Integer.parseInt(oxygenSaturationField.getText());
				double temperature = Double.parseDouble(temperatureField.getText());

				String healthCheckInfo = String.format("%s - %s\n", patientName, patientId);
				healthCheckInfo += String.format("Height: %.2f ft\n", height);
				healthCheckInfo += String.format("Weight: %.2f lbs\n", weight);
				healthCheckInfo += String.format("Blood Pressure: %s\n", bloodPressure);
				healthCheckInfo += String.format("Heart Rate: %d bpm\n", heartRate);
				healthCheckInfo += String.format("Oxygen Saturation: %d%%\n", oxygenSaturation);
				healthCheckInfo += String.format("Temperature: %.2f°F\n", temperature);

				String fileName = patientId + "_healthCheckin.txt";
				saveFile(fileName, healthCheckInfo);
				
				NotificationManager.addNotification("Nurse updated health check-in!\non " + patientName + " - " + patientId);

				System.out.println("Submitted successfully");
				dialog.close();
				showAlert(Alert.AlertType.INFORMATION, dialog.getOwner(), "Submission Confirmation",
						"Submitted successfully!");
			});

			// Create a VBox to hold the form and submit button
			VBox rootBox = new VBox(20);
			rootBox.setAlignment(Pos.CENTER);
			rootBox.getChildren().addAll(formBox, submitButton);

			// Create a new scene with the form and display it in a new window
			Scene scene = new Scene(rootBox, 400, 600);
			dialog.setScene(scene);
			dialog.show();
		});
		
		logoutBtn.setOnAction(event -> {
			System.out.println("logoutBtn was clicked!");
			LogIn logInScreen = new LogIn();
			logInScreen.start(primaryStage);
		});
		
		message.setOnAction(event -> {
			System.out.println("Message was clicked!");
			// Add more actions here
		});

		healthSummary.setOnAction(event -> {
			System.out.println("> Health Summary was clicked!");
			// Add more actions here
			DoctorView.HealthSummaryFunction(finalHbox, patientVistiSummary, patientID, patient_name);
		});

		prescribedMedication.setOnAction(event -> {
			System.out.println("> Prescribed Medication was clicked!");
			DoctorView.prescribingFunction(finalHbox, patientVistiSummary, patientID, patient_name);
		});

		immunization.setOnAction(event -> {
			System.out.println("> Immunization was clicked!");
			DoctorView.ImmunizationFunction(finalHbox, patientVistiSummary, patientID, patient_name);
		});

		contactInfo.setOnAction(event -> {
			System.out.println("> Contact Info was clicked!");
			DoctorView.contactInfoFuction(finalHbox, patientVistiSummary, patientID, patient_name);
		});

		insuranceInfo.setOnAction(event -> {
			System.out.println("> Insurance Info was clicked!");
			DoctorView.insuranceInfoFuction(finalHbox, patientVistiSummary, patientID, patient_name);
		});
		
		pharmacyInfo.setOnAction(event -> {
			System.out.println("> pharmacyInfo was clicked!");
			DoctorView.pharmacyInfoFuction(finalHbox, patientVistiSummary, patientID, patient_name);
		});


		health_RelatedInformation.setOnAction(event -> {
			System.out.println("Health Related Information was clicked!");
			boolean isVisible = contactInfo.isVisible();
			contactInfo.setVisible(!isVisible);
			insuranceInfo.setVisible(!isVisible);
			pharmacyInfo.setVisible(!isVisible);
		});
		
		searchBtn.setOnAction(event -> {
			String searchTerm = searchPatient.getText();
			searchPatientSummary(searchTerm);
			
			
			
			System.out.println("searchBtn was clicked!");
		    List<Button> buttons1 = List.of(message, medicalRecord, healthSummary, prescribedMedication, immunization, health_RelatedInformation, contactInfo, insuranceInfo, pharmacyInfo);
		    // Parse the patient ID from the searchPatient TextField and save it to the patientID variable
		    patientID = Integer.parseInt(searchPatient.getText());
		    System.out.println("Patient ID: " + patientID);
		    String filePath = patientID + "_visitSum.txt";
		    File file = new File(filePath);    
		    
		    try (Scanner scanner = new Scanner(file)) {
	            if (scanner.hasNextLine()) {
	                String firstLine = scanner.nextLine();
	                // Assuming the format "Name - ID"
	                String patientName1 = firstLine.split("-")[0]; // This splits the string into an array and takes the first element
	                patient_name = patientName1;
	            }
	        } catch (FileNotFoundException e) {
	            System.out.println("File not found: " + filePath);
	            e.printStackTrace();
	        }    
		    
		    if (file.exists()) {
		        System.out.println("The file exists.");
		        buttons1.forEach(button -> button.setDisable(false));       
		    } else {
		        System.out.println("The file does not exist.");
		        buttons1.forEach(button -> button.setDisable(true));
		        showAlert2("Invalid ID", "The ID you entered is not correct. Please check and try again.");
		    }
		});
		
		
		

		addBtn.setOnAction(event -> {
			final Stage dialog = new Stage();
			dialog.setTitle("New Appointment");

			dialog.initOwner(primaryStage);
			dialog.initModality(Modality.APPLICATION_MODAL);

			Label timeLabel = new Label("Time:");
			timeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
			DatePicker datePicker = new DatePicker();

			Label hourLabel = new Label("Hour:");
			ComboBox<String> hourComboBox = new ComboBox<>();
			for (int hour = 0; hour < 24; hour++) {
				hourComboBox.getItems().add(String.format("%02d", hour)); // Add hours in "00" format
			}
			Label minuteLabel = new Label("Minute:");
			ComboBox<String> minuteComboBox = new ComboBox<>();
			for (int minute = 0; minute < 60; minute += 5) { // Increment by 5
				minuteComboBox.getItems().add(String.format("%02d", minute)); // Add minutes in "00" format
			}

			Label nameLabel = new Label("Patient Name:");
			nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
			TextField nameTextField = new TextField();

			Label patientIdLabel = new Label("Patient ID:");
			patientIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
			TextField patientIdTextField = new TextField();

			Label descriptionLabel = new Label("Description:");
			descriptionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
			TextArea descriptionTextArea = new TextArea();
			descriptionTextArea.setPromptText("Enter appointment details here");
			descriptionTextArea.setWrapText(true);

			Button submitBtn = new Button("Submit");
			submitBtn.setOnAction(e -> {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				String date = datePicker.getValue() != null ? datePicker.getValue().format(formatter) : "N/A";
				String hour = hourComboBox.getValue() != null ? hourComboBox.getValue() : "00";
				String minute = minuteComboBox.getValue() != null ? minuteComboBox.getValue() : "00";
				String name = nameTextField.getText().isEmpty() ? "Anonymous" : nameTextField.getText();
				String patientId = getPatientId(patientIdTextField.getText());
				String description = descriptionTextArea.getText().isEmpty() ? "No description"
						: descriptionTextArea.getText();

				String appointmentInfo = String.format("%s %s:%s\n%s\n%s - %s", date, hour, minute, description, name,
						patientId);

				String fileName = patientId + "_appointment.txt";
				saveFile(fileName, appointmentInfo);

				readAppointmentsFromFiles(fileName);
				
				String apptText = String.format("New Appointment Added!\n%s - %s", name, patientId);
				NotificationManager.addNotification(apptText);

				System.out.println("Submitted successfully");
				dialog.close();
				showAlert(Alert.AlertType.INFORMATION, dialog.getOwner(), "Submission Confirmation",
						"Submitted successfully!");
			});

			HBox timeSelectionBox = new HBox(10, datePicker, hourComboBox, minuteComboBox);
			HBox buttonsBox = new HBox(10, submitBtn);
			buttonsBox.setAlignment(Pos.CENTER);
			VBox dialogVbox = new VBox(20, timeLabel, timeSelectionBox, nameLabel, nameTextField, patientIdLabel,
					patientIdTextField, descriptionLabel, descriptionTextArea, buttonsBox);
			dialogVbox.setAlignment(Pos.CENTER_LEFT);
			dialogVbox.setPadding(new Insets(20));
			Scene dialogScene = new Scene(dialogVbox, 400, 500);
			dialog.setScene(dialogScene);
			dialog.showAndWait();
		});

		upcomingAppointmentsListView.setCellFactory(listView -> new ListCell<String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					HBox hbox = new HBox(10);

					ImageView iconView = new ImageView(new Image(getClass().getResourceAsStream("human.jpg")));
					iconView.setFitWidth(40);
					iconView.setFitHeight(40);

					hbox.getChildren().add(iconView); 

					Label appointmentLabel = new Label(item); // Display the appointment text

					Button doneButton = new Button("Done");
					doneButton.setOnAction(event -> {
						System.out.println("Done button clicked for appointment: " + item);
						openAppointmentDetailsDialog(); // Open the dialog for entering appointment details
						upcomingAppointments.remove(item);

						// Delete the corresponding file
						String fileNameToDelete = item.split(" - ")[1].split(":")[0].trim() + "_appointment.txt";
						if (deleteFile(fileNameToDelete)) {
							System.out.println("File deleted successfully: " + fileNameToDelete);
						} else {
							System.err.println("Error deleting file: " + fileNameToDelete);
						}
					});

					Button deleteButton = new Button("Delete");
					deleteButton.setOnAction(event -> {
						System.out.println("Delete button clicked for appointment: " + item);
						upcomingAppointments.remove(item); // Remove the appointment from the list

						// Delete the corresponding file
						String fileNameToDelete = item.split(" - ")[1].split(":")[0].trim() + "_appointment.txt";
						if (deleteFile(fileNameToDelete)) {
							System.out.println("File deleted successfully: " + fileNameToDelete);
						} else {
							System.err.println("Error deleting file: " + fileNameToDelete);
						}
					});

					hbox.getChildren().addAll(appointmentLabel, doneButton, deleteButton);
					setGraphic(hbox);
				}
			}
		});

		Button[] buttons = { healthCheckin, message, medicalRecord, healthSummary, prescribedMedication, immunization,
				health_RelatedInformation, contactInfo, insuranceInfo, pharmacyInfo };
		for (Button button : buttons) {
			button.setStyle(style);
			button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
			button.setOnMouseExited(e -> button.setStyle(style));
			button.setPrefWidth(200);
			button.setAlignment(Pos.CENTER_LEFT);
			button.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		}

		VBox logo = new VBox(10);
		logo.setPrefWidth(100);
		logo.setPrefHeight(100);
		logo.setStyle("-fx-background-color: #0000FF;");

		VBox blank = new VBox(10);
		blank.setPrefWidth(50);
		blank.setPrefHeight(30);

		VBox functionalTab = new VBox(10);
		functionalTab.getChildren().addAll(logoView, blank, healthCheckin, message, medicalRecord, medicalRecordOptions,
				health_RelatedInformation, contactInfo, insuranceInfo, pharmacyInfo);
		functionalTab.setStyle("-fx-background-color: #FFFFFF;");
		functionalTab.setMinWidth(180);
		functionalTab.setSpacing(17.2);

		VBox blank2 = new VBox(10);
		blank2.setPrefWidth(330);
		blank2.setPrefHeight(30);

		VBox centerVbox = new VBox();
		centerVbox.getChildren().addAll(appointmentDetails, visitSumListView);
		centerVbox.setStyle("-fx-background-color: #f0f0f0;");
		centerVbox.setPrefWidth(500);
		centerVbox.setPrefHeight(490);

		HBox SearchPanel = new HBox(10);
		SearchPanel.getChildren().addAll(searchPatient, searchBtn, addBtn, blank2, welcome, logoutBtn);
		SearchPanel.setPadding(new Insets(0, 0, 0, 20));
		SearchPanel.setStyle("-fx-background-color: #f0f0f0;");
		SearchPanel.setPrefHeight(100);
		SearchPanel.setAlignment(Pos.CENTER_LEFT);

		HBox centerHBox = new HBox(10);
		centerHBox.getChildren().add(centerVbox);

		HBox NotificationVbox = new HBox(10);
		NotificationVbox.getChildren().addAll(notificationListView);
		NotificationVbox.setStyle("-fx-background-color: #f0f0f0;");
		NotificationVbox.setPrefWidth(300);
		NotificationVbox.setPrefHeight(200);

		HBox appoinmentVbox = new HBox(10);
		appoinmentVbox.getChildren().addAll(upcomingAppointmentsListView);
		appoinmentVbox.setStyle("-fx-background-color: #f0f0f0;");
		appoinmentVbox.setPrefWidth(300);
		appoinmentVbox.setPrefHeight(200);

		VBox box1 = new VBox(10);
		box1.getChildren().addAll(newNofitication, NotificationVbox, upComingAppointment, appoinmentVbox);

		finalHbox.getChildren().addAll(centerVbox, box1);

		VBox rightVerticalBox = new VBox(10);
		rightVerticalBox.setStyle("-fx-background-color: #FFFFFF;");
		rightVerticalBox.getChildren().addAll(SearchPanel, patientVistiSummary, finalHbox);

		BorderPane mainLayout = new BorderPane();
		mainLayout.setLeft(functionalTab);
		mainLayout.setCenter(rightVerticalBox);

		
		logoView.setOnMouseClicked(event -> {
			System.out.println("Logo was clicked!");
			// Add more actions here
			finalHbox.getChildren().clear();
			finalHbox.getChildren().addAll(centerVbox, box1);
			patientVistiSummary.setText("Patient Visit Summary");
		});
		
		Scene scene = new Scene(mainLayout, 1200, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setWidth(1100);
		primaryStage.setHeight(750);
		primaryStage.centerOnScreen();

	}

	private void openAppointmentDetailsDialog() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Enter Appointment Details");
		dialog.setHeaderText("Please enter the appointment details:");

		ButtonType doneButtonType = new ButtonType("Done", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));

		Label nameLabel = new Label("Patient Name:");
		TextField nameTextField = new TextField();

		Label patientIdLabel = new Label("Patient ID:");
		TextField patientIdTextField = new TextField();

		Label apptTimeLabel = new Label("Appointment Time:");
		DatePicker datePicker = new DatePicker();

		Label hourLabel = new Label("Hour:");
		ComboBox<String> hourComboBox = new ComboBox<>();
		for (int hour = 0; hour < 24; hour++) {
			hourComboBox.getItems().add(String.format("%02d", hour)); // Add hours in "00" format
		}

		Label minuteLabel = new Label("Minute:");
		ComboBox<String> minuteComboBox = new ComboBox<>();
		for (int minute = 0; minute < 60; minute += 5) { // Increment by 5
			minuteComboBox.getItems().add(String.format("%02d", minute)); // Add minutes in "00" format
		}

		Label arrivalTimeLabel = new Label("Arrival Time:");
		DatePicker arrivalDatePicker = new DatePicker();

		Label arrivalHourLabel = new Label("Hour:");
		ComboBox<String> arrivalHourComboBox = new ComboBox<>();
		for (int hour = 0; hour < 24; hour++) {
			arrivalHourComboBox.getItems().add(String.format("%02d", hour)); // Add hours in "00" format
		}

		Label arrivalMinuteLabel = new Label("Minute:");
		ComboBox<String> arrivalMinuteComboBox = new ComboBox<>();
		for (int minute = 0; minute < 60; minute += 1) { // Increment by 1
			arrivalMinuteComboBox.getItems().add(String.format("%02d", minute)); // Add minutes in "00" format
		}

		Label visitReasonLabel = new Label("Visit Reason:");
		TextField visitReasonTextField = new TextField();

		Label patientAgeLabel = new Label("Patient Age:");
		TextField patientAgeTextField = new TextField();

		Label locationLabel = new Label("Location:");
		TextField locationTextField = new TextField();

		Label providerLabel = new Label("Provider:");
		TextField providerTextField = new TextField();

		Label apptNoteLabel = new Label("Appointment Note:");
		TextArea apptNoteTextArea = new TextArea();
		apptNoteTextArea.setPromptText("Enter appointment note here");
		apptNoteTextArea.setWrapText(true);

		grid.add(nameLabel, 0, 0);
		grid.add(nameTextField, 1, 0);
		grid.add(patientIdLabel, 0, 1);
		grid.add(patientIdTextField, 1, 1);
		grid.add(apptTimeLabel, 0, 2);
		grid.add(new HBox(10, datePicker, hourComboBox, minuteComboBox), 1, 2);
		grid.add(arrivalTimeLabel, 0, 3);
		grid.add(new HBox(10, arrivalDatePicker, arrivalHourComboBox, arrivalMinuteComboBox), 1, 3);
		grid.add(visitReasonLabel, 0, 4);
		grid.add(visitReasonTextField, 1, 4);
		grid.add(patientAgeLabel, 0, 5);
		grid.add(patientAgeTextField, 1, 5);
		grid.add(locationLabel, 0, 6);
		grid.add(locationTextField, 1, 6);
		grid.add(providerLabel, 0, 7);
		grid.add(providerTextField, 1, 7);
		grid.add(apptNoteLabel, 0, 8);
		grid.add(apptNoteTextArea, 1, 8);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == doneButtonType) {
				String apptTime = datePicker.getValue() != null
						? datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
						: "";
				String hour = hourComboBox.getValue() != null ? hourComboBox.getValue() : "00";
				String minute = minuteComboBox.getValue() != null ? minuteComboBox.getValue() : "00";
				String arrivalTime = arrivalDatePicker.getValue() != null
						? arrivalDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
						: "";
				String arrivalHour = arrivalHourComboBox.getValue() != null ? arrivalHourComboBox.getValue() : "00";
				String arrivalMinute = arrivalMinuteComboBox.getValue() != null ? arrivalMinuteComboBox.getValue()
						: "00";
				String patientName = nameTextField.getText().isEmpty() ? "Anonymous" : nameTextField.getText();
				String patientId = patientIdTextField.getText().isEmpty() ? "Unknown" : patientIdTextField.getText();
				String visitReason = visitReasonTextField.getText().isEmpty() ? "N/A" : visitReasonTextField.getText();
				String patientAge = patientAgeTextField.getText().isEmpty() ? "N/A" : patientAgeTextField.getText();
				String location = locationTextField.getText().isEmpty() ? "N/A" : locationTextField.getText();
				String provider = providerTextField.getText().isEmpty() ? "N/A" : providerTextField.getText();
				String apptNote = apptNoteTextArea.getText().isEmpty() ? "N/A" : apptNoteTextArea.getText();

				String visitSumInfo = String.format("%s - %s\n", patientName, patientId);
				visitSumInfo += String.format("Appointment Time: %s %s:%s\n", apptTime, hour, minute);
				visitSumInfo += String.format("Arrival Time: %s %s:%s\n", arrivalTime, arrivalHour, arrivalMinute);
				visitSumInfo += String.format("Visit Reason: %s\n", visitReason);
				visitSumInfo += String.format("Patient Age: %s\n", patientAge);
				visitSumInfo += String.format("Location: %s\n", location);
				visitSumInfo += String.format("Provider: %s\n", provider);
				visitSumInfo += String.format("Appointment Note: %s\n", apptNote);

				String fileName = patientId + "_visitSum.txt";
				saveFile(fileName, visitSumInfo);

				readVisitSumFromFiles(fileName);

				return new Pair<>(visitSumInfo, "");
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(pair -> {
			showAlert(Alert.AlertType.INFORMATION, null, "Appointment Saved",
					"Appointment details saved successfully in file: " + pair.getValue());
		});
	}

	private void saveFile(String fileName, String content) {
		File file = new File(fileName);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
			showAlert(Alert.AlertType.ERROR, null, "Error Saving Appointment",
					"An error occurred while saving the appointment.");
		}
	}

	private void readVisitSumFromFiles(String patientId) {

		File folder = new File(".");
		for (File file : folder.listFiles()) {
			if (file.isFile() && file.getName().startsWith(patientId) && file.getName().endsWith("_visitSum.txt")) {
				String visitSumInfo = readFile(file);
				if (visitSumInfo != null && !visitSumInfo.isEmpty()) {
					visitSum.add(visitSumInfo);
				}
			}
		}
	}

	private void readAppointmentsFromFiles(String patientId) {

		File folder = new File(".");
		for (File file : folder.listFiles()) {
			if (file.isFile() && file.getName().startsWith(patientId) && file.getName().endsWith("_appointment.txt")) {
				String appointmentInfo = readFile(file);
				if (appointmentInfo != null && !appointmentInfo.isEmpty()) {
					upcomingAppointments.add(appointmentInfo);
				}
			}
		}
	}

	public static String readFile(File file) {
		StringBuilder appointmentInfo = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				appointmentInfo.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			showAlert(Alert.AlertType.ERROR, null, "Error Reading Appointment",
					"An error occurred while reading the appointment file.");
		}
		return appointmentInfo.toString();
	}

	private boolean deleteFile(String fileName) {
		File fileToDelete = new File(fileName);
		if (fileToDelete.exists()) {
			if (fileToDelete.delete()) {
				System.out.println("File deleted successfully: " + fileName);
				return true;
			} else {
				System.err.println("Error deleting file: " + fileName);
				return false;
			}
		} else {
			System.err.println("File not found: " + fileName);
			return false;
		}
	}

	public static void loadAppointmentsData() {
		// Clear existing data
		upcomingAppointments.clear();

		File folder = new File(".");
		for (File file : folder.listFiles()) {
			if (file.isFile() && file.getName().endsWith("_appointment.txt")) {
				String appointmentInfo = readFile(file);
				if (appointmentInfo != null && !appointmentInfo.isEmpty()) {
					upcomingAppointments.add(appointmentInfo);
				}
			}
		}
	}

	public static void loadVisitSumData() {
		// Clear existing data
		visitSum.clear();

		File folder = new File(".");
		for (File file : folder.listFiles()) {
			if (file.isFile() && file.getName().endsWith("_visitSum.txt")) {
				String visitSumInfo = readFile(file);
				if (visitSumInfo != null && !visitSumInfo.isEmpty()) {
					visitSum.add(visitSumInfo);
				}
			}
		}
	}

	public static void searchPatientSummary(String searchTerm) {
		patientSummaryList.clear();

		File folder = new File(".");
		for (File file : folder.listFiles()) {
			if (file.isFile() && file.getName().endsWith("_visitSum.txt")) {
				String patientSummary = readFile(file);
				if (patientSummary != null && patientSummary.toLowerCase().contains(searchTerm.toLowerCase())) {
					patientSummaryList.add(patientSummary);
				}
			}
		}
		visitSum.clear();
		visitSum.addAll(patientSummaryList);
	}

	private String getPatientId(String enteredId) {
		if (enteredId.isEmpty()) {
			return generateRandomPatientId();
		} else {
			return enteredId;
		}
	}

	private String generateRandomPatientId() {
		int randomId = random.nextInt(90000) + 10000; // Generates a random number between 10000 and 99999
		return String.valueOf(randomId);
	}
	
	public static class Notification {
	    private String message;
	    private LocalDateTime timestamp;

	    public Notification(String message) {
	        this.message = message;
	        this.timestamp = LocalDateTime.now();  // Capture the creation time
	    }

	    public Notification(String message, LocalDateTime timestamp) {
	        this.message = message;
	        this.timestamp = timestamp;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public LocalDateTime getTimestamp() {
	        return timestamp;
	    }

	    public String getTimeAgo() {
	        Duration duration = Duration.between(timestamp, LocalDateTime.now());
	        if (duration.toDays() > 0) {
	            return duration.toDays() + "d ago";
	        } else if (duration.toHours() > 0) {
	            return duration.toHours() + "h ago";
	        } else if (duration.toMinutes() > 0) {
	            return duration.toMinutes() + "m ago";
	        } else {
	            return duration.toSeconds() + "s ago";
	        }
	    }

	    @Override
	    public String toString() {
	        return getMessage() + "\n" + getTimeAgo();
	    }
	}
	
	public class NotificationManager {
	    private static final ObservableList<Notification> notifications = FXCollections.observableArrayList();
	    private static final String FILE_NAME = "notifications.txt";
	    private static final String DELIMITER = "~~~";

	    public static ObservableList<Notification> getNotifications() {
	        return notifications;
	    }

	    public static void addNotification(String message) {
	        Notification newNotification = new Notification(message);
	        notifications.add(0, newNotification);
	        saveNotificationsToFile();
	    }

	    private static void saveNotificationsToFile() {
	        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(FILE_NAME)))) {
	            for (Notification notification : notifications) {
	                writer.println(notification.getMessage().replace("\n", "\\n") + DELIMITER + notification.getTimestamp());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void loadNotificationsFromFile() {
	        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_NAME))) {
	            String line;
	            notifications.clear();
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(DELIMITER);
	                if (parts.length == 2) {
	                    String message = parts[0].replace("\\n", "\n");
	                    LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
	                    Notification notification = new Notification(message, timestamp);
	                    notifications.add(notification);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	public static void showAlert2(String title, String content) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(content);
	    alert.showAndWait();
	}

	public static void main(String[] args) {
		NotificationManager.loadNotificationsFromFile();
		launch(args);
	}
}