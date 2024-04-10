package application;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import application.NurseView.Notification;
import application.NurseView.NotificationManager;


public class DoctorView extends Application {
	private static int patientID;
	private static String patient_Name;
	static boolean isEditMode = true;

    
	@Override
	public void start(Stage DoctorsViewScreen) {
		DoctorsViewScreen.setTitle("Doctor");
		
		// Load data from files
		NurseView.loadAppointmentsData();
		NurseView.loadVisitSumData();
		
		// set the Logo
		Image logoImage = new Image(getClass().getResourceAsStream("123.png"));
		
		ImageView logoView = new ImageView(logoImage);
		logoView.setFitHeight(100);
		logoView.setFitWidth(200);
		logoView.setPreserveRatio(true);

		String style = "-fx-background-color: transparent; -fx-text-fill: black;";
		String hoverStyle = "-fx-background-color: #D3D3D3; -fx-text-fill: black;";

		// all Label
		Label welcome = new Label("Welcome, Doctor!");
		Label appointmentDetails = new Label("Appointment Details");
		appointmentDetails.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		appointmentDetails.setPadding(new Insets(20, 0, 0, 20));
		
		Label newNofitication = new Label("New Notifications");
		newNofitication.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		newNofitication.setPadding(new Insets(20, 0, 0, 20));
		
		Label upComingAppoinment = new Label("Upcoming Appointment");
		upComingAppoinment.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		newNofitication.setPadding(new Insets(20, 0, 0, 20));
		
		Label patientVistiSummary = new Label("Patient Visit Summary");
		patientVistiSummary.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		
		Label message1 = new Label("Message");
		message1.setFont(Font.font("Arial", FontWeight.BOLD, 30));

		// all Button
		Button clinicalExamination = new Button("Clinical Examination");
		Button e_Prescribing = new Button("e_Prescribing");
		Button message = new Button("Message");
		Button medicalRecord = new Button("Medical Record");
		Button healthSummary = new Button("	> Health Summary");
		Button prescribedMedication = new Button("	> Prescribed Medication");
		Button immunization = new Button("	> Immunization");
		Button health_RelatedInformation = new Button("Health Related Information");
		Button contactInfo = new Button("	> Contact Info");
		Button insuranceInfo = new Button("	> Insurance Info");
		Button pharmacyInfo = new Button("	> Pharmacy Info");
		Button searchBtn = new Button("search");
		Button logoutBtn = new Button("Log out");
		searchBtn.setStyle("-fx-background-color: #87CEFA;");
		logoutBtn.setStyle("-fx-background-color: #87CEFA;");
		
		// Notification List View
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
		
		// Appointment List View
		ListView<String> upcomingAppointmentsListView = new ListView<>(NurseView.upcomingAppointments);
		upcomingAppointmentsListView.setPrefWidth(300);
		upcomingAppointmentsListView.setPrefHeight(240);
		upcomingAppointmentsListView.setStyle("-fx-background-color: #f0f0f0;");
		
		upcomingAppointmentsListView.setCellFactory(param -> new ListCell<String>() {
		    private ImageView imageView = new ImageView();
		    private Label label = new Label();

		    {
		        HBox hbox = new HBox(8); // space between elements
		        imageView.setFitHeight(40); // set the image size
		        imageView.setFitWidth(40);
		        hbox.getChildren().add(imageView);
		        hbox.getChildren().add(label);
		        hbox.setAlignment(Pos.CENTER_LEFT);
		        setGraphic(hbox);
		    }

		    @Override
		    protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText(null);
		            setGraphic(null);
		        } else {
		            label.setText(item);
		            InputStream is = getClass().getResourceAsStream("human.jpg"); 
		            if (is != null) {
		                imageView.setImage(new Image(is));
		            } else {
		                System.out.println("Image file not found");
		            }
		            setGraphic(label.getParent());
		        }
		    }
		});
		
		// Visit Summary List View
		ListView<String> visitSumListView = new ListView<>(NurseView.visitSum);
		visitSumListView.setPrefWidth(500);
		visitSumListView.setPrefHeight(490);
		visitSumListView.setStyle("-fx-background-color: #f0f0f0;");
		visitSumListView.setPadding(new Insets(20, 20, 20, 20));
		
        contactInfo.setVisible(false); // Initially hidden
        insuranceInfo.setVisible(false); // Initially hidden
        pharmacyInfo.setVisible(false); // Initially hidden

        VBox vbox = new VBox(5); // 5 is the spacing between elements
        vbox.getChildren().addAll(health_RelatedInformation, contactInfo, insuranceInfo, pharmacyInfo);
		
		// list all the buttons on the left side that need to be transparent, and turn
		// grey when the mouse move on it
		Button[] buttons = { clinicalExamination, e_Prescribing, message, medicalRecord, healthSummary,
				prescribedMedication, immunization, health_RelatedInformation, contactInfo, insuranceInfo,
				pharmacyInfo };
		for (Button button : buttons) {
			button.setStyle(style);
			button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
			button.setOnMouseExited(e -> button.setStyle(style));
			button.setPrefWidth(180);
			button.setAlignment(Pos.CENTER_LEFT);
			button.setFont(Font.font("Arial", 12));
			button.setDisable(true); 
		}

		TextField searchPatient = new TextField(); // Create a new TextField for search Patient
		searchPatient.setPromptText("Search patient");
		
		// Put all element together
		VBox blank = new VBox(10);
		blank.setPrefWidth(50);
		blank.setPrefHeight(30);

		VBox blank2 = new VBox(10);
		blank2.setPrefWidth(390);
		blank2.setPrefHeight(30);
		
		VBox medicalRecordOptions = new VBox(healthSummary, prescribedMedication, immunization);
		medicalRecordOptions.setVisible(false); // Initially hidden
		medicalRecordOptions.setSpacing(15);
		medicalRecordOptions.setVisible(false);
		medicalRecordOptions.setManaged(false);

		
		VBox leftMenuVBox = new VBox(10);
		leftMenuVBox.getChildren().addAll(logoView, blank, clinicalExamination, e_Prescribing, message, medicalRecord, medicalRecordOptions, health_RelatedInformation, contactInfo, insuranceInfo, pharmacyInfo);
		leftMenuVBox.setStyle("-fx-background-color: #FFFFFF;");
		leftMenuVBox.setMinWidth(180);
		leftMenuVBox.setSpacing(17.2);

		HBox topHBox = new HBox(10);
		topHBox.getChildren().addAll(searchPatient, searchBtn, blank2, welcome, logoutBtn);
		topHBox.setStyle("-fx-background-color: #f0f0f0;");
		topHBox.setPrefHeight(100);
		topHBox.setPadding(new Insets(20));
		topHBox.setAlignment(Pos.CENTER_LEFT);

		VBox centerVbox = new VBox();
		centerVbox.getChildren().addAll(appointmentDetails, visitSumListView);
		centerVbox.setStyle("-fx-background-color: #f0f0f0;");
		centerVbox.setPrefWidth(500);
		centerVbox.setPrefHeight(490);
		
		HBox centerHBox = new HBox(10);
		centerHBox.getChildren().add(centerVbox);

		HBox NotificationVbox = new HBox(10);
		NotificationVbox.setStyle("-fx-background-color: #f0f0f0;");
		NotificationVbox.setPrefWidth(300);
		NotificationVbox.setPrefHeight(200);
		NotificationVbox.getChildren().clear();
		NotificationVbox.getChildren().add(notificationListView);
		
		HBox appointmentVbox = new HBox(10);
		appointmentVbox.setStyle("-fx-background-color: #f0f0f0;");
		appointmentVbox.setPrefWidth(300);
		appointmentVbox.setPrefHeight(200);
		appointmentVbox.getChildren().clear();
		appointmentVbox.getChildren().add(upcomingAppointmentsListView);

		VBox NotificationVbox2 = new VBox(10);
		NotificationVbox2.getChildren().addAll(newNofitication, NotificationVbox);

		VBox appointmentVbox2 = new VBox(10);
		appointmentVbox2.getChildren().addAll(upComingAppoinment, appointmentVbox);

		VBox rightVbox = new VBox(10);
		rightVbox.getChildren().addAll(NotificationVbox2, appointmentVbox2);

		HBox finalHbox = new HBox(10);
		finalHbox.getChildren().addAll(centerVbox, rightVbox);

		VBox VBox1 = new VBox(10);
		VBox1.setStyle("-fx-background-color: #FFFFFF;");
		VBox1.getChildren().addAll(topHBox, patientVistiSummary, finalHbox);	
		

		logoView.setOnMouseClicked(event -> {
			System.out.println("Logo was clicked!");
			// Add more actions here
			finalHbox.getChildren().clear();
			finalHbox.getChildren().addAll(centerVbox, rightVbox);
			patientVistiSummary.setText("Patient Visit Summary");
		});
		
		clinicalExamination.setOnAction(event -> {
			System.out.println("Clinical Examination was clicked!");
			// Add more actions here
			examinationFunction(DoctorsViewScreen);
		});

		e_Prescribing.setOnAction(event -> {
			System.out.println("e_Prescribing was clicked!");
			// Add more actions here
			e_prescribingFunction(DoctorsViewScreen);
		});

		message.setOnAction(event -> {
			System.out.println("Message was clicked!");
			// Add more actions here
		});

		medicalRecord.setOnAction(event -> {
			System.out.println("Medical Record was clicked!");
			// Add more actions here
		    boolean isVisible = medicalRecordOptions.isVisible();
		    medicalRecordOptions.setVisible(!isVisible);
		    medicalRecordOptions.setManaged(!isVisible);
		});

		healthSummary.setOnAction(event -> {
			System.out.println("> Health Summary was clicked!");
			// Add more actions here
			HealthSummaryFunction(finalHbox, patientVistiSummary, patientID, patient_Name);
		});

		prescribedMedication.setOnAction(event -> {
			System.out.println("> Prescribed Medication was clicked!");
			// Add more actions here
			prescribingFunction(finalHbox, patientVistiSummary, patientID, patient_Name);
		});

		immunization.setOnAction(event -> {
			System.out.println("> Immunization was clicked!");
			// Add more actions here
			ImmunizationFunction(finalHbox, patientVistiSummary, patientID, patient_Name);
		});

		health_RelatedInformation.setOnAction(event -> {
			System.out.println("Health Related Information was clicked!");
			// Add more actions here
            boolean isVisible = contactInfo.isVisible();
            contactInfo.setVisible(!isVisible);
            insuranceInfo.setVisible(!isVisible);
            pharmacyInfo.setVisible(!isVisible);
		});

		contactInfo.setOnAction(event -> {
			System.out.println("> Contact Info was clicked!");
			contactInfoFuction(finalHbox, patientVistiSummary, patientID, patient_Name);
		});

		insuranceInfo.setOnAction(event -> {
			System.out.println("> Insurance Info was clicked!");
			insuranceInfoFuction(finalHbox, patientVistiSummary, patientID, patient_Name);
		});
		
		pharmacyInfo.setOnAction(event -> {
			System.out.println("> pharmacyInfo was clicked!");
			pharmacyInfoFuction(finalHbox, patientVistiSummary, patientID, patient_Name);
		});
		
		searchBtn.setOnAction(event -> {
			String searchTerm = searchPatient.getText();
			NurseView.searchPatientSummary(searchTerm);
			
		    System.out.println("searchBtn was clicked!");
		    List<Button> buttons1 = List.of(clinicalExamination, e_Prescribing, message, medicalRecord, healthSummary, prescribedMedication, immunization, health_RelatedInformation, contactInfo, insuranceInfo, pharmacyInfo);
		    // Parse the patient ID from the searchPatient TextField and save it to the patientID variable
		    patientID = Integer.parseInt(searchPatient.getText());
		    String filePath = patientID + "_visitSum.txt";
		    File file = new File(filePath);    
		    
		    try (Scanner scanner = new Scanner(file)) {
	            if (scanner.hasNextLine()) {
	                String firstLine = scanner.nextLine();
	                // Assuming the format "Name - ID"
	                String patientName1 = firstLine.split("-")[0]; // This splits the string into an array and takes the first element
	                patient_Name = patientName1;
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
		        showAlert("Invalid ID", "The ID you entered is not correct. Please check and try again.");
		    }
		});


		logoutBtn.setOnAction(event -> {
			System.out.println("logoutBtn was clicked!");
			// Add more actions here
			LogIn logInScreen = new LogIn();
			logInScreen.start(DoctorsViewScreen);
		});

		BorderPane mainLayout = new BorderPane();
		mainLayout.setLeft(leftMenuVBox);
		mainLayout.setCenter(VBox1);
		Scene scene = new Scene(mainLayout, 800, 600);
		DoctorsViewScreen.setScene(scene);
		DoctorsViewScreen.show();
		DoctorsViewScreen.setWidth(1050);
		DoctorsViewScreen.setHeight(710);
		DoctorsViewScreen.centerOnScreen();
	}	

	private void examinationFunction(Stage DoctorsViewScreen) {
		final Stage dialog = new Stage();
		dialog.setTitle("Clinical Examination");
		dialog.initOwner(DoctorsViewScreen);
		dialog.initModality(Modality.APPLICATION_MODAL);
		
		Button save = new Button("Save");
		save.setStyle("-fx-background-color: #87CEFA;");
		save.setOnMouseEntered(e -> save.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: black;"));
		save.setOnMouseExited(e -> save.setStyle("-fx-background-color: #87CEFA;"));
		save.setAlignment(Pos.CENTER);
		save.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		save.setPrefSize(200, 100);
		
		Label patientIdLabel = new Label("Patient ID: " + patientID);
		Label patientName = new Label("Patient Name: " + patient_Name);
		Label symptomLabel = new Label("Symptoms:");
		TextArea symptomTextArea = new TextArea();
		Label testResultLabel = new Label("Diagnosis:");
		TextArea testResultTextArea = new TextArea();
		Label prescriptionsLabel = new Label("Prescriptions/Medications:");
		TextArea prescriptionsTextArea = new TextArea();
		Label NotesLabel = new Label("Special Notes:");
		TextArea notesTextArea = new TextArea();
		Label[] labels = { patientIdLabel, patientName, symptomLabel, testResultLabel, prescriptionsLabel, NotesLabel };
		for (Label label : labels) {
			label.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		}
		TextArea[] textAreas = { symptomTextArea, testResultTextArea, prescriptionsTextArea, notesTextArea };
		for (TextArea textArea : textAreas) {
			textArea.setMinHeight(100);
		}

		HBox saveButtonBox = new HBox();
		saveButtonBox.setAlignment(Pos.CENTER); // This will align the save button to the right
		saveButtonBox.setPrefSize(200, 200);
		saveButtonBox.getChildren().add(save);
		saveButtonBox.setPadding(new Insets(10, 0, 10, 0));

		VBox vbox1 = new VBox(symptomLabel, symptomTextArea, testResultLabel, testResultTextArea, prescriptionsLabel,
				prescriptionsTextArea, NotesLabel, notesTextArea);
		vbox1.setSpacing(15);
		HBox hbox1 = new HBox(patientIdLabel);
		hbox1.setAlignment(Pos.TOP_CENTER);
		HBox hbox2 = new HBox(patientName);
		hbox2.setAlignment(Pos.TOP_CENTER);
		HBox patientIdHbox = new HBox(hbox2, hbox1);
		patientIdHbox.setAlignment(Pos.CENTER_LEFT);
		patientIdHbox.setSpacing(150);
		VBox vbox2 = new VBox(patientIdHbox, vbox1, saveButtonBox);
		vbox2.setPadding(new Insets(20));
		vbox2.setSpacing(30);

		save.setOnMouseClicked(event -> {
			System.out.println("save was clicked!");
			writeExaminationInfoToFile( symptomTextArea, testResultTextArea,
					prescriptionsTextArea, notesTextArea);

			symptomTextArea.clear();
			testResultTextArea.clear();
			prescriptionsTextArea.clear();
			notesTextArea.clear();
			
			NurseView.NotificationManager.addNotification("Clinical examination file created for patient " + patient_Name + "-" + patientID);
		});
		Scene dialogScene = new Scene(vbox2, 800, 750);
		dialog.setScene(dialogScene);
		dialog.showAndWait();
	}	
	
	private void e_prescribingFunction(Stage DoctorsViewScreen) {
		final Stage dialog = new Stage();
		dialog.setTitle("ePrescribing");
		dialog.initOwner(DoctorsViewScreen);
		dialog.initModality(Modality.APPLICATION_MODAL);
		
		String filePath = patientID + "_examination_info.txt";
        File file = new File(filePath);
        // Initialize variables to hold the file content
        String diagnosis = "";
        String specialNotes = "";
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Test Results: ")) {
                	diagnosis = line.substring("Test Results: ".length());
                }  else if (line.startsWith("Special Notes: ")) {
                    specialNotes = line.substring("Special Notes: ".length());
                }
            }     
        } catch (Exception e) {
            e.printStackTrace();
        }
		Label patientName = new Label("Patient Name: " + patient_Name);
		Label patientID1 = new Label("Patient ID: " + patientID);
		Label diagnosis1 = new Label("Diagnosis: " + diagnosis);
		Label notes = new Label("Notes: " + specialNotes);
		Label medicationstab = new Label("Medications:");
		Label medication = new Label("Medication");
		Label unit = new Label("Unit");
		Label amount = new Label("Amount");
		Label duration = new Label("Duration (# of days)");
		Label startDate = new Label("Start Date");
		Label advil = new Label("Advil");
		Label ibuprofen = new Label("Ibuprofen");
		Label tylenol = new Label("Tylenol");
		Label acetaninophen = new Label("Acetaninophen");
		Label claritin = new Label("Claritin");
		Label loratadine = new Label("Loratadine");
		Label benadryl = new Label("Benadryl");
		Label diphenhydramine = new Label("Diphenhydramine");
		Label lipitor = new Label("Lipitor");
		Label atorvastatin = new Label("Atorvastatin");
		Label norvasc = new Label("Norvasc");
		Label amlodipine = new Label("Amlodipine");
		Label zithromax = new Label("Zithromax");
		Label azithromycin = new Label("Azithromycin");
		Label cipro = new Label("Cipro");
		Label ciprofloxacin = new Label("Ciprofloxacin");
		Label prilosec = new Label("Prilosec");
		Label omeprozole = new Label("Omeprozole");
		Label flonase = new Label("Flonase");
		Label fluticasone = new Label("Fluticasone");
		ComboBox<String> comboBox1 = new ComboBox<>(FXCollections.observableArrayList("", "Packets", "Tablets", "Boxs"));
		ComboBox<String> comboBox2 = new ComboBox<>(FXCollections.observableArrayList("", "Packets", "Tablets", "Boxs"));
		ComboBox<String> comboBox3 = new ComboBox<>(FXCollections.observableArrayList("", "Packets", "Tablets", "Boxs"));
		ComboBox<String> comboBox4 = new ComboBox<>(FXCollections.observableArrayList("", "Packets", "Tablets", "Boxs"));
		ComboBox<String> comboBox5 = new ComboBox<>(FXCollections.observableArrayList("", "Packets", "Tablets", "Boxs"));
		ComboBox<String> comboBox6 = new ComboBox<>(FXCollections.observableArrayList("", "Packets", "Tablets", "Boxs"));
		ComboBox<String> comboBox7 = new ComboBox<>(FXCollections.observableArrayList("", "Packets", "Tablets", "Boxs"));
		ComboBox<String> comboBox8 = new ComboBox<>(FXCollections.observableArrayList("", "Packets", "Tablets", "Boxs"));
		ComboBox<String> comboBox9 = new ComboBox<>(FXCollections.observableArrayList("", "Packets", "Tablets", "Boxs"));
		ComboBox<String> comboBox10 = new ComboBox<>(FXCollections.observableArrayList("", "Packets", "Tablets", "Boxs"));
		TextField textfield1 = new TextField();
		TextField textfield2 = new TextField();
		TextField textfield3 = new TextField();
		TextField textfield4 = new TextField();
		TextField textfield5 = new TextField();
		TextField textfield6 = new TextField();
		TextField textfield7 = new TextField();
		TextField textfield8 = new TextField();
		TextField textfield9 = new TextField();
		TextField textfield10 = new TextField();
		TextField textfield11 = new TextField();
		TextField textfield12 = new TextField();
		TextField textfield13 = new TextField();
		TextField textfield14 = new TextField();
		TextField textfield15 = new TextField();
		TextField textfield16 = new TextField();
		TextField textfield17 = new TextField();
		TextField textfield18 = new TextField();
		TextField textfield19 = new TextField();
		TextField textfield20 = new TextField();
		DatePicker datePicker1 = new DatePicker();
		DatePicker datePicker2 = new DatePicker();
		DatePicker datePicker3 = new DatePicker();
		DatePicker datePicker4 = new DatePicker();
		DatePicker datePicker5 = new DatePicker();
		DatePicker datePicker6 = new DatePicker();
		DatePicker datePicker7 = new DatePicker();
		DatePicker datePicker8 = new DatePicker();
		DatePicker datePicker9 = new DatePicker();
		DatePicker datePicker10 = new DatePicker();
		
		Button send = new Button("Send to Pharmacy");
		send.setStyle("-fx-background-color: #87CEFA;");
		send.setOnMouseEntered(e -> send.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: black;"));
		send.setOnMouseExited(e -> send.setStyle("-fx-background-color: #87CEFA;"));
		send.setAlignment(Pos.CENTER);
		send.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		send.setMinHeight(50);
		send.setMinWidth(100);

		medicationstab.setFont(Font.font("Arial", FontWeight.BOLD, 17));

		GridPane gridPane = new GridPane();
		gridPane.setHgap(20); // Horizontal gap between columns
		gridPane.setVgap(20); // Vertical gap between rows
		gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10)); // Padding around the grid
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setHalignment(unit, HPos.CENTER);
		GridPane.setHalignment(amount, HPos.CENTER);
		GridPane.setHalignment(duration, HPos.CENTER);
		GridPane.setHalignment(startDate, HPos.CENTER);

		VBox vbox1 = new VBox(advil, ibuprofen);
		VBox vbox2 = new VBox(tylenol, acetaninophen);
		VBox vbox3 = new VBox(claritin, loratadine);
		VBox vbox4 = new VBox(benadryl, diphenhydramine);
		VBox vbox5 = new VBox(lipitor, atorvastatin);
		VBox vbox6 = new VBox(norvasc, amlodipine);
		VBox vbox7 = new VBox(zithromax, azithromycin);
		VBox vbox8 = new VBox(cipro, ciprofloxacin);
		VBox vbox9 = new VBox(prilosec, omeprozole);
		VBox vbox10 = new VBox(flonase, fluticasone);

		Label[] label = { advil, tylenol, claritin, benadryl, lipitor, norvasc, zithromax, cipro, prilosec, flonase };
		for (Label labels : label) {
			labels.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		}

		GridPane.setConstraints(medication, 0, 0); 
		gridPane.getChildren().add(medication);
		VBox[] vBoxes = { vbox1, vbox2, vbox3, vbox4, vbox5, vbox6, vbox7, vbox8, vbox9, vbox10 };
		for (int i = 0; i < vBoxes.length; i++) {
			GridPane.setConstraints(vBoxes[i], 0, i + 1); 
			gridPane.getChildren().add(vBoxes[i]);
		}

		GridPane.setConstraints(unit, 1, 0); 
		gridPane.getChildren().add(unit);
		ComboBox[] ComboBoxs = { comboBox1, comboBox2, comboBox3, comboBox4, comboBox5, comboBox6, comboBox7, comboBox8,
				comboBox9, comboBox10 };
		for (int i = 0; i < ComboBoxs.length; i++) {
			GridPane.setConstraints(ComboBoxs[i], 1, i + 1); 
			gridPane.getChildren().add(ComboBoxs[i]);
		}

		GridPane.setConstraints(amount, 2, 0); 
		gridPane.getChildren().add(amount);
		TextField[] TextFields = { textfield1, textfield2, textfield3, textfield4, textfield5, textfield6, textfield7,
				textfield8, textfield9, textfield10 };
		for (int i = 0; i < TextFields.length; i++) {
			GridPane.setConstraints(TextFields[i], 2, i + 1); 
			gridPane.getChildren().add(TextFields[i]);
		}

		GridPane.setConstraints(duration, 3, 0); 
		gridPane.getChildren().add(duration);
		TextField[] TextFields2 = { textfield11, textfield12, textfield13, textfield14, textfield15, textfield16,
				textfield17, textfield18, textfield19, textfield20 };
		for (int i = 0; i < TextFields2.length; i++) {
			GridPane.setConstraints(TextFields2[i], 3, i + 1); 
			gridPane.getChildren().add(TextFields2[i]);
		}

		GridPane.setConstraints(startDate, 4, 0); 
		gridPane.getChildren().add(startDate);
		DatePicker[] DatePickers = { datePicker1, datePicker2, datePicker3, datePicker4, datePicker5, datePicker6,
				datePicker7, datePicker8, datePicker9, datePicker10 };
		for (int i = 0; i < DatePickers.length; i++) {
			GridPane.setConstraints(DatePickers[i], 4, i + 1); 
			gridPane.getChildren().add(DatePickers[i]);
		}
		
		
		send.setOnMouseClicked(event -> {
			System.out.println("send was clicked!");
			writeMedicationInfoToFile(gridPane);
			clearUserInputs(gridPane);
			
			NurseView.NotificationManager.addNotification("Prescribed Medication updated for patient " + patient_Name + "-" + patientID);
		});

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(gridPane); // Set the GridPane as the content of the ScrollPane
		scrollPane.setFitToHeight(true);

		VBox userInfo = new VBox(patientName, patientID1, diagnosis1);

		HBox userHbox = new HBox(userInfo);
		userHbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 25;");
		userHbox.setMinWidth(450);
		userHbox.setPadding(new Insets(30));

		VBox noteVbox = new VBox(notes);
		noteVbox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 25;");
		noteVbox.setMinWidth(350);
		noteVbox.setPadding(new Insets(30));

		HBox topBox = new HBox(userHbox, noteVbox);
		topBox.setAlignment(Pos.CENTER);
		topBox.setSpacing(25);

		VBox medicationsVbox = new VBox(medicationstab);
		medicationsVbox.setAlignment(Pos.CENTER_LEFT);

		VBox medicationslistVBox = new VBox(scrollPane);
		medicationslistVBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 30 30 30 30;"); 
																																																	// corners
		HBox saveHbox = new HBox(send);
		saveHbox.setAlignment(Pos.CENTER_RIGHT);

		VBox bottomBox = new VBox(medicationsVbox, medicationslistVBox, saveHbox);
		bottomBox.setSpacing(20);

		VBox vbox = new VBox(topBox, bottomBox);
		vbox.setStyle("-fx-background-color: #DEDEDE;");
		vbox.setPadding(new Insets(30));
		vbox.setSpacing(30);

		Scene dialogScene = new Scene(vbox, 900, 650);
		dialog.setScene(dialogScene);
		dialog.showAndWait();		
	}
	
	
	public static void HealthSummaryFunction(HBox finalHbox, Label status, int patientID2, String name) {	
		String filePath = patientID2 + "_healthCheckin.txt"; // Adjust this to the correct file path
        File file = new File(filePath);

        // Variables to store the information
        double height0 = 0.0;
        double weight0 = 0.0;
        int bloodPressure0 = 0;
        int heartRate0 = 0;
        int oxygenSaturation0 = 0;
        double temperature0 = 0.0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Height:")) {
                    height0 = Double.parseDouble(line.split(":")[1].trim().split(" ")[0]);
                } else if (line.startsWith("Weight:")) {
                    weight0 = Double.parseDouble(line.split(":")[1].trim().split(" ")[0]);
                } else if (line.startsWith("Blood Pressure:")) {
                    bloodPressure0 = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Heart Rate:")) {
                    heartRate0 = Integer.parseInt(line.split(":")[1].trim().split(" ")[0]);
                } else if (line.startsWith("Oxygen Saturation:")) {
                    oxygenSaturation0 = Integer.parseInt(line.split(":")[1].trim().replace("%", ""));
                } else if (line.startsWith("Temperature:")) {
                    temperature0 = Double.parseDouble(line.split(":")[1].trim().split("�")[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String filePath2 = patientID2 + "_examination_info.txt";
        File file2 = new File(filePath2);
        // Initialize variables to hold the file content
        String symptoms0 = "";
        String testResults = "";
        String specialNotes = "";

        try (Scanner scanner = new Scanner(file2)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Symptoms: ")) {
                    symptoms0 = line.substring("Symptoms: ".length());
                } else if (line.startsWith("Test Results: ")) {
                    testResults = line.substring("Test Results: ".length());
                }  else if (line.startsWith("Special Notes: ")) {
                    specialNotes = line.substring("Special Notes: ".length());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		Label patientDetail = new Label("Patient Details");
		Label name0 = new Label("Name:");
		Label name1 = new Label(name);
		Label ID = new Label("Patient ID:");
		Label ID1 = new Label("" + patientID2);
		Label height = new Label("Height: ");
		Label height1 = new Label(""+ height0);
		Label weight = new Label("Weight:");
		Label weight1 = new Label("" + weight0);
		Label vitalSigns = new Label("Vital Signs");
		Label bloodpressure = new Label("Blood Pressure:");
		Label bloodpressure1 = new Label("" + bloodPressure0);
		Label heartRate = new Label("Heart Rate:");
		Label heartRate1 = new Label(""+ heartRate0);
		Label oxygenSaturation = new Label("Oxygen Saturation:");
		Label oxygenSaturation1 = new Label("" + oxygenSaturation0);
		Label temperature = new Label("Tempurature:");
		Label tempurature1 = new Label("" + temperature0);	
		Label medicalInformation = new Label("Medical Information");
		Label symptoms = new Label("Symptoms:");
		Label symptoms1 = new Label(""+ symptoms0);
		Label diagnosis = new Label("Diagnosis:");
		Label diagnosis1 = new Label(""+ testResults);
		Label medicalNotes = new Label("Medical Notes:");
		Label medicalNotes1 = new Label(""+ specialNotes);
		
		patientDetail.setFont(Font.font("Arial", FontWeight.BOLD, 21));
		vitalSigns.setFont(Font.font("Arial", FontWeight.BOLD, 21));
		medicalInformation.setFont(Font.font("Arial", FontWeight.BOLD, 21));

		VBox nameVbox = new VBox(name0,name1);
		VBox IDVbox = new VBox(ID,ID1);
		VBox heightVbox = new VBox(height,height1);
		VBox weightVbox = new VBox(weight,weight1);
		
		VBox bloodPressureVbox = new VBox(bloodpressure,bloodpressure1);
		VBox heartRateVbox = new VBox(heartRate,heartRate1);
		VBox oxygenSaturationVbox = new VBox(oxygenSaturation,oxygenSaturation1);
		VBox tempuratureVbox = new VBox(temperature,tempurature1);
		
		VBox symptomsVbox = new VBox(symptoms,symptoms1,diagnosis, diagnosis1,medicalNotes, medicalNotes1);

		HBox hbox1 = new HBox(nameVbox,IDVbox);
		HBox hbox2 = new HBox(heightVbox,weightVbox);
		VBox vbox1 = new VBox(patientDetail, hbox1, hbox2);
		
		HBox hbox3 = new HBox(bloodPressureVbox,heartRateVbox);
		HBox hbox4 = new HBox(oxygenSaturationVbox,tempuratureVbox);
		VBox vbox2 = new VBox(vitalSigns, hbox3, hbox4);
		
		VBox vbox3 = new VBox(medicalInformation, symptomsVbox);
		symptomsVbox.setSpacing(5);
		vbox1.setSpacing(16);
		vbox2.setSpacing(16);
		vbox3.setSpacing(16);
		
		VBox[] vboxs = { nameVbox, IDVbox,heightVbox,weightVbox,bloodPressureVbox,heartRateVbox,oxygenSaturationVbox,tempuratureVbox};
		for (VBox vbox : vboxs) {
			vbox.setMinWidth(400);
			vbox.setSpacing(5);
		}
		
		Label[] labels = {name0,ID,height,weight,bloodpressure, heartRate,oxygenSaturation,temperature,symptoms,diagnosis,medicalNotes };
		for (Label label : labels) {
			label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		}

		
		VBox outter = new VBox(vbox1, vbox2, vbox3);
		outter.setPadding(new Insets(20));
		outter.setSpacing(16);
		
		finalHbox.getChildren().clear();
		status.setText("Health Summary");
		finalHbox.getChildren().addAll(outter);		
	}
	
	public static void prescribingFunction(HBox finalHbox, Label status, int patientID, String patientname) {
		Label details = new Label("Patient Details");
		Label name = new Label("Name: ");
		Label patientName =  new Label(patientname);
		Label ID = new Label("Patient ID: ");
		Label patientid = new Label("" + patientID);
		Label medicationList =  new Label("Medication List:");
		
		medicationList.setFont(Font.font("Arial", FontWeight.BOLD, 18));	
		details.setFont(Font.font("Arial", FontWeight.BOLD, 18));	
		name.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		ID.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		HBox NameHbox = new HBox(name, patientName);
		HBox IDHbox = new HBox(ID, patientid);
		VBox patientDetailsVBox0 = new VBox(NameHbox, IDHbox); // may add date of birth and gender later
		VBox patientDetailsVBox1 = new VBox(details, patientDetailsVBox0);
		patientDetailsVBox1.setSpacing(10);
		patientDetailsVBox0.setStyle("-fx-border-color: gray; -fx-border-width: 0.5; -fx-padding: 10;");
		patientDetailsVBox0.setPrefWidth(760);
		
		VBox medicine = new VBox();
		medicine.setPrefHeight(330);
		medicine.setStyle("-fx-border-color: gray; -fx-border-width: 0.5; -fx-padding: 10;");
		
		VBox medicationVBox = new VBox(medicationList, medicine);
		medicationVBox.setSpacing(10);
		
		VBox vbox1 = new VBox(patientDetailsVBox1,medicationVBox);
		vbox1.setSpacing(30);		
		
		VBox outter = new VBox(vbox1);
		outter.setSpacing(16);
		outter.setPadding(new Insets (12) );
	    loadMedicationList(medicine, String.valueOf(patientID));

		finalHbox.getChildren().clear();
		status.setText("Prescribed Medication");
		finalHbox.getChildren().addAll(outter);
	}
	
	public static void ImmunizationFunction(HBox finalHbox, Label status, int patientID, String patient_name) {
		Label details = new Label("Patient Details");
		Label name = new Label("Name: ");
		Label patientName =  new Label(patient_name);
		//Label dateOfBirth = new Label("Date of Birth: " + date);
		//Label gender = new Label("Gender: " + gender);
		Label ID = new Label("Patient ID: ");
		Label patientid = new Label("" + patientID);
		Label immunizations = new Label("Immunizations Details");
		
		details.setFont(Font.font("Arial", FontWeight.BOLD, 18));	
		immunizations.setFont(Font.font("Arial", FontWeight.BOLD, 18));	
		name.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		ID.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		Button add = new Button("Add");
	    add.setOnAction(e -> addNewImmuzation());
		
		HBox NameHbox = new HBox(name, patientName);
		HBox IDHbox = new HBox(ID, patientid);
		VBox patientDetailsVBox0 = new VBox(NameHbox, IDHbox); // may add date of birth and gender later
		VBox patientDetailsVBox1 = new VBox(details, patientDetailsVBox0);
		patientDetailsVBox1.setSpacing(10);
		patientDetailsVBox0.setStyle("-fx-border-color: gray; -fx-border-width: 0.5; -fx-padding: 10;");
	
		HBox Hbox1 = new HBox(immunizations, add);
		Hbox1.setSpacing(530);
		Hbox1.setAlignment(Pos.CENTER_LEFT);
		VBox immunizationVBoxdetails = new VBox(); 
	    ScrollPane scrollPane = new ScrollPane(immunizationVBoxdetails);
	    scrollPane.setFitToWidth(true); // Ensure the VBox width fits within the ScrollPane
	    scrollPane.setPrefHeight(300);
	    scrollPane.setStyle("-fx-background: white; -fx-background-color: white; -fx-border-color: gray; -fx-border-width: 0.5;");
 
		VBox immunizationVBox = new VBox(Hbox1, scrollPane); 
		immunizationVBox.setSpacing(10);
		immunizationVBoxdetails.setStyle("-fx-border-color: white; -fx-border-width: 0.5; -fx-padding: 10;");
	
		VBox vbox1 = new VBox(patientDetailsVBox1, immunizationVBox);
		vbox1.setSpacing(30);
		vbox1.setPadding(new Insets(15));
		
		loadImmunizations(immunizationVBoxdetails, String.valueOf(patientID));

		VBox outter = new VBox(vbox1);
		outter.setSpacing(16);
		
		finalHbox.getChildren().clear();
		status.setText("Immunization");
		finalHbox.getChildren().addAll(outter);
	}
	
    
	public static void contactInfoFuction (HBox finalHbox, Label status, int patientID, String patient_name) {
		Label name = new Label("Full Name:");
		name.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label dateOfBirth = new Label("Date of Birth:");
		dateOfBirth.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label address = new Label("Address:");
		address.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label email = new Label("Email:");
		email.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label phoneNumber = new Label("Phone Number:");
		phoneNumber.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		
		TextField nameTextField = new TextField ();
		nameTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		nameTextField.setEditable(false);
		nameTextField.setMinWidth(300);
		
		TextField dateOfBirthTextField = new TextField ();
		dateOfBirthTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		dateOfBirthTextField.setEditable(false);
		
		TextField addressTextField = new TextField ();
		addressTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		addressTextField.setEditable(false);
		
		TextField emailTextField = new TextField ();
		emailTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		emailTextField.setEditable(false);

		TextField phoneNumberTextField = new TextField ();
		phoneNumberTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		phoneNumberTextField.setEditable(false);

		Button editSaveButton = new Button("Edit");
		HBox editHbox = new HBox(editSaveButton);
		editHbox.setAlignment(Pos.CENTER_RIGHT);
		editHbox.setPadding(new Insets(0,0,0,200));
		
		VBox leftVBox = new VBox(name,dateOfBirth,address,email,phoneNumber);
		leftVBox.setSpacing(31);
		leftVBox.setPadding(new Insets(5));

		VBox RightVBox = new VBox(nameTextField,dateOfBirthTextField,addressTextField,emailTextField,phoneNumberTextField);
		RightVBox.setSpacing(23.5);
		HBox infoHBox = new HBox(leftVBox,RightVBox);
		infoHBox.setSpacing(50);
		
		VBox outter = new VBox(editHbox,infoHBox);
		outter.setSpacing(16);
		outter.setPadding(new Insets(20));
		outter.setMinWidth(700);
		
		
		// if file exist, load info 
	    String fileName = patientID + "_ContactInfo.txt";
	    File file = new File(fileName);
	    if (file.exists()) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                if (line.startsWith("Full Name: ")) {
	                    nameTextField.setText(line.substring("Full Name: ".length()));
	                } else if (line.startsWith("Date of Birth: ")) {
	                    dateOfBirthTextField.setText(line.substring("Date of Birth: ".length()));
	                } else if (line.startsWith("Address: ")) {
	                    addressTextField.setText(line.substring("Address: ".length()));
	                } else if (line.startsWith("Email: ")) {
	                    emailTextField.setText(line.substring("Email: ".length()));
	                } else if (line.startsWith("Phone Number: ")) {
	                    phoneNumberTextField.setText(line.substring("Phone Number: ".length()));
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
		editSaveButton.setOnAction(e -> {
            if (isEditMode) {
                // Switch to Save mode
            	nameTextField.setEditable(true);
            	nameTextField.setStyle("");
            	dateOfBirthTextField.setEditable(true);
            	dateOfBirthTextField.setStyle("");
            	addressTextField.setEditable(true);
            	addressTextField.setStyle("");
            	phoneNumberTextField.setEditable(true);
            	phoneNumberTextField.setStyle("");
            	emailTextField.setEditable(true);
            	emailTextField.setStyle("");
                editSaveButton.setText("Save");
            } else {
                // Save information to file and switch back to Edit mode
            	nameTextField.setEditable(false);
        		nameTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	dateOfBirthTextField.setEditable(false);
            	dateOfBirthTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	addressTextField.setEditable(false);
            	addressTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	phoneNumberTextField.setEditable(false);
            	phoneNumberTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	emailTextField.setEditable(false);
            	emailTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                editSaveButton.setText("Edit");
        
        	    // if not save information into file
        	    try (FileWriter writer = new FileWriter(file, false)) { 
        	        writer.write("Full Name: " + nameTextField.getText() + "\n");
        	        writer.write("Date of Birth: " + dateOfBirthTextField.getText() + "\n");
        	        writer.write("Address: " + addressTextField.getText() + "\n");
        	        writer.write("Email: " + emailTextField.getText() + "\n");
        	        writer.write("Phone Number: " + phoneNumberTextField.getText() + "\n");
        	        
        	    } catch (IOException e1) {
        	        e1.printStackTrace();
        	    }
            }
            isEditMode = !isEditMode;
        });
		
		finalHbox.getChildren().clear();
		status.setText("Contact Information");
		finalHbox.getChildren().addAll(outter);
	}
	
	
	public static void insuranceInfoFuction (HBox finalHbox, Label status, int patientID, String patient_name) {
		Label insuranceProvider = new Label("Insurance Provider:");
		insuranceProvider.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label policyNumber = new Label("Policy Number:");
		policyNumber.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label groupNumber = new Label("Group Number:");
		groupNumber.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label policyHolder = new Label("Policy Holder:");
		policyHolder.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label effectiveDate = new Label("Effective Date:");
		effectiveDate.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label expirationDate = new Label("Expiration Date:");
		expirationDate.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label coverage = new Label("Coverage:");
		coverage.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label deductible = new Label("Deductible:");
		deductible.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label copay = new Label("Copay:");
		copay.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		
		TextField insuranceProviderTextField = new TextField ();
		insuranceProviderTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		insuranceProviderTextField.setEditable(false);
		insuranceProviderTextField.setMinWidth(300);
		
		TextField policyNumberTextField = new TextField ();
		policyNumberTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		policyNumberTextField.setEditable(false);
		
		TextField groupNumberTextField = new TextField ();
		groupNumberTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		groupNumberTextField.setEditable(false);
		
		TextField policyHolderTextField = new TextField ();
		policyHolderTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		policyHolderTextField.setEditable(false);
		
		TextField effectiveDateTextField = new TextField ();
		effectiveDateTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		effectiveDateTextField.setEditable(false);
		
		TextField expirationDateTextField = new TextField ();
		expirationDateTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		expirationDateTextField.setEditable(false);
		
		TextField coverageTextField = new TextField ();
		coverageTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		coverageTextField.setEditable(false);
		
		TextField deductibleTextField = new TextField ();
		deductibleTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		deductibleTextField.setEditable(false);

		TextField copayTextField = new TextField ();
		copayTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		copayTextField.setEditable(false);

		Button editSaveButton = new Button("Edit");
		HBox editHbox = new HBox(editSaveButton);
		editHbox.setAlignment(Pos.CENTER_RIGHT);
		editHbox.setPadding(new Insets(0,0,0,200));
		
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(10);
        grid.add(insuranceProvider, 0, 0);
        grid.add(insuranceProviderTextField, 1, 0);
        grid.add(policyNumber, 0, 1);
        grid.add(policyNumberTextField, 1, 1);
        grid.add(groupNumber, 0, 2);
        grid.add(groupNumberTextField, 1, 2);
        grid.add(policyHolder, 0, 3);
        grid.add(policyHolderTextField, 1, 3);
        grid.add(effectiveDate, 0, 4);
        grid.add(effectiveDateTextField, 1, 4);
        grid.add(expirationDate, 0, 5);
        grid.add(expirationDateTextField, 1, 5);
        grid.add(coverage, 0, 6);
        grid.add(coverageTextField, 1, 6);
        grid.add(deductible, 0, 7);
        grid.add(deductibleTextField, 1, 7);
        grid.add(copay, 0, 8);
        grid.add(copayTextField, 1, 8);
		VBox outter = new VBox(editHbox,grid);
		outter.setSpacing(16);
		outter.setPadding(new Insets(20));
		outter.setMinWidth(700);
		
	    String fileName = patientID + "_InsuranceInfo.txt";
	    File file = new File(fileName);

	    if (file.exists()) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                if (line.startsWith("Insurance Provider: ")) {
	                	insuranceProviderTextField.setText(line.substring("Insurance Provider: ".length()));
	                } else if (line.startsWith("Policy Number: ")) {
	                	policyNumberTextField.setText(line.substring("Policy Number: ".length()));
	                } else if (line.startsWith("Group Number: ")) {
	                	groupNumberTextField.setText(line.substring("Group Number: ".length()));
	                } else if (line.startsWith("Policy Holder: ")) {
	                	policyHolderTextField.setText(line.substring("Policy Holder: ".length()));
	                } else if (line.startsWith("Effective Date: ")) {
	                	effectiveDateTextField.setText(line.substring("Effective Date: ".length()));
	                } else if (line.startsWith("Expiration Date: ")) {
	                	expirationDateTextField.setText(line.substring("Expiration Date: ".length()));
	                } else if (line.startsWith("Coverage: ")) {
	                	coverageTextField.setText(line.substring("Coverage: ".length()));
	                } else if (line.startsWith("Deductible: ")) {
	                	deductibleTextField.setText(line.substring("Deductible: ".length()));
	                } else if (line.startsWith("Copay: ")) {
	                	copayTextField.setText(line.substring("Copay: ".length()));
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	    
		editSaveButton.setOnAction(e -> {
            if (isEditMode) {
                // Switch to Save mode
            	insuranceProviderTextField.setEditable(true);
            	insuranceProviderTextField.setStyle("");
            	policyNumberTextField.setEditable(true);
            	policyNumberTextField.setStyle("");
            	groupNumberTextField.setEditable(true);
            	groupNumberTextField.setStyle("");
            	policyHolderTextField.setEditable(true);
            	policyHolderTextField.setStyle("");
            	effectiveDateTextField.setEditable(true);
            	effectiveDateTextField.setStyle("");
            	expirationDateTextField.setEditable(true);
            	expirationDateTextField.setStyle("");
            	coverageTextField.setEditable(true);
            	coverageTextField.setStyle("");
            	deductibleTextField.setEditable(true);
            	deductibleTextField.setStyle("");
            	copayTextField.setEditable(true);
            	copayTextField.setStyle("");
                editSaveButton.setText("Save");
            } else {
                // Save information to file and switch back to Edit mode
            	insuranceProviderTextField.setEditable(false);
            	insuranceProviderTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	policyNumberTextField.setEditable(false);
            	policyNumberTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	groupNumberTextField.setEditable(false);
            	groupNumberTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	policyHolderTextField.setEditable(false);
            	policyHolderTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	effectiveDateTextField.setEditable(false);
            	effectiveDateTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	expirationDateTextField.setEditable(false);
            	expirationDateTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	coverageTextField.setEditable(false);
            	coverageTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	deductibleTextField.setEditable(false);
            	deductibleTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	copayTextField.setEditable(false);
            	copayTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); 	
                editSaveButton.setText("Edit");
        
        	    // save information into file
        	    try (FileWriter writer = new FileWriter(file, false)) { 
        	        writer.write("Insurance Provider: " + insuranceProviderTextField.getText() + "\n");
        	        writer.write("Policy Number: " + policyNumberTextField.getText() + "\n");
        	        writer.write("Group Number: " + groupNumberTextField.getText() + "\n");
        	        writer.write("Policy Holder: " + policyHolderTextField.getText() + "\n");
        	        writer.write("Effective Date: " + effectiveDateTextField.getText() + "\n");
        	        writer.write("Expiration Date: " + expirationDateTextField.getText() + "\n");
        	        writer.write("Coverage: " + coverageTextField.getText() + "\n");
        	        writer.write("Deductible: " + deductibleTextField.getText() + "\n");
        	        writer.write("Copay: " + copayTextField.getText() + "\n");
        	    } catch (IOException e1) {
        	        e1.printStackTrace();
        	    }
            }
            isEditMode = !isEditMode;
        });
		
		finalHbox.getChildren().clear();
		status.setText("Insurance Information");
		finalHbox.getChildren().addAll(outter);
	}
	
	
	
	public static void pharmacyInfoFuction (HBox finalHbox, Label status, int patientID, String patient_name) {
		Label pharmacyName = new Label("Pharmacy Name:");
		pharmacyName.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label pharmacyAddress = new Label("Pharmacy Address:");
		pharmacyAddress.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label pharmacyPhone = new Label("Pharmacy Phone Number:");
		pharmacyPhone.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label pharmacyEmail = new Label("Pharmacy Email:");
		pharmacyEmail.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		Label preferredPharmacist = new Label("Preferred Pharmacist:");
		preferredPharmacist.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		
		TextField pharmacyNameTextField = new TextField ();
		pharmacyNameTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		pharmacyNameTextField.setEditable(false);
		pharmacyNameTextField.setMinWidth(300);
		
		TextField pharmacyAddressTextField = new TextField ();
		pharmacyAddressTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		pharmacyAddressTextField.setEditable(false);
		
		TextField pharmacyPhoneTextField = new TextField ();
		pharmacyPhoneTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		pharmacyPhoneTextField.setEditable(false);
		
		TextField pharmacyEmailTextField = new TextField ();
		pharmacyEmailTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		pharmacyEmailTextField.setEditable(false);

		TextField preferredPharmacistTextField = new TextField ();
		preferredPharmacistTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		preferredPharmacistTextField.setEditable(false);

		Button editSaveButton = new Button("Edit");
		HBox editHbox = new HBox(editSaveButton);
		editHbox.setAlignment(Pos.CENTER_RIGHT);
		editHbox.setPadding(new Insets(0,0,0,200));
		
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(10);
        grid.add(pharmacyName, 0, 0);
        grid.add(pharmacyNameTextField, 1, 0);
        grid.add(pharmacyAddress, 0, 1);
        grid.add(pharmacyAddressTextField, 1, 1);
        grid.add(pharmacyPhone, 0, 2);
        grid.add(pharmacyPhoneTextField, 1, 2);
        grid.add(pharmacyEmail, 0, 3);
        grid.add(pharmacyEmailTextField, 1, 3);
        grid.add(preferredPharmacist, 0, 4);
        grid.add(preferredPharmacistTextField, 1, 4);
		
		VBox outter = new VBox(editHbox,grid);
		outter.setSpacing(16);
		outter.setPadding(new Insets(20));
		outter.setMinWidth(700);
		
		
		// if file exist, load info 
	    String fileName = patientID + "_PharmacyInfo.txt";
	    File file = new File(fileName);
	    
	    if (file.exists()) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                if (line.startsWith("Pharmacy Name: ")) {
	                	pharmacyNameTextField.setText(line.substring("Pharmacy Name: ".length()));
	                } else if (line.startsWith("Pharmacy Address: ")) {
	                	pharmacyAddressTextField.setText(line.substring("Pharmacy Address: ".length()));
	                } else if (line.startsWith("Pharmacy Phone Number: ")) {
	                	pharmacyPhoneTextField.setText(line.substring("Pharmacy Phone Number: ".length()));
	                } else if (line.startsWith("Pharmacy Email: ")) {
	                	pharmacyEmailTextField.setText(line.substring("Pharmacy Email: ".length()));
	                } else if (line.startsWith("Preferred Pharmacist: ")) {
	                	preferredPharmacistTextField.setText(line.substring("Preferred Pharmacist: ".length()));
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		editSaveButton.setOnAction(e -> {
            if (isEditMode) {
                // Switch to Save mode
            	pharmacyNameTextField.setEditable(true);
            	pharmacyNameTextField.setStyle("");
            	pharmacyAddressTextField.setEditable(true);
            	pharmacyAddressTextField.setStyle("");
            	pharmacyPhoneTextField.setEditable(true);
            	pharmacyPhoneTextField.setStyle("");
            	pharmacyEmailTextField.setEditable(true);
            	pharmacyEmailTextField.setStyle("");
            	preferredPharmacistTextField.setEditable(true);
            	preferredPharmacistTextField.setStyle("");
                editSaveButton.setText("Save");
            } else {
                // Save information to file and switch back to Edit mode
            	pharmacyNameTextField.setEditable(false);
            	pharmacyNameTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	pharmacyAddressTextField.setEditable(false);
            	pharmacyAddressTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	pharmacyPhoneTextField.setEditable(false);
            	pharmacyPhoneTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	pharmacyEmailTextField.setEditable(false);
            	pharmacyEmailTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            	preferredPharmacistTextField.setEditable(false);
            	preferredPharmacistTextField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                editSaveButton.setText("Edit");
        
        	    // if not save information into file
        	    try (FileWriter writer = new FileWriter(file, false)) { 
        	        writer.write("Pharmacy Name: " + pharmacyNameTextField.getText() + "\n");
        	        writer.write("Pharmacy Address: " + pharmacyAddressTextField.getText() + "\n");
        	        writer.write("Pharmacy Phone Number: " + pharmacyPhoneTextField.getText() + "\n");
        	        writer.write("Pharmacy Email: " + pharmacyEmailTextField.getText() + "\n");
        	        writer.write("Preferred Pharmacist: " + preferredPharmacistTextField.getText() + "\n");
        	        
        	    } catch (IOException e1) {
        	        e1.printStackTrace();
        	    }
            }
            isEditMode = !isEditMode;
        });
		
		finalHbox.getChildren().clear();
		status.setText("Pharmacy Information");
		finalHbox.getChildren().addAll(outter);
	}
	
	
	public static void clearUserInputs(GridPane gridPane) {
	    for (Node node : gridPane.getChildren()) {
	        if (node instanceof TextField) {
	            ((TextField) node).setText("");
	        } else if (node instanceof CheckBox) {
	            ((CheckBox) node).setSelected(false);
	        } else if (node instanceof ComboBox) {
	            ((ComboBox<?>) node).getSelectionModel().clearSelection();
	        } else if (node instanceof DatePicker) {
	        	((DatePicker) node).setValue(null);	        }
	    }
	}
	
	public static void addNewImmuzation() {
	    Stage window = new Stage();
	    window.initModality(Modality.APPLICATION_MODAL); // Block input events to other windows
	    window.setTitle("Add New Immunization");
	    window.setMinWidth(500);
	    window.setMinHeight(400);

	    // Create labels and text fields
	    Label patientName = new Label("Enter new immunization details for patient " + patient_Name + "( ID: " + patientID +" )");
	    Label vaccineNameLabel = new Label("Vaccine Name:");
	    TextField vaccineNameInput = new TextField();
	    vaccineNameInput.setPromptText("Enter vaccine name");
	    vaccineNameInput.setMinWidth(320);

	    Label dateLabel = new Label("Date Administered:");
	    DatePicker datePicker = new DatePicker();
	    datePicker.setPromptText("mm/dd/yyyy");
	    
	    Label notesLabel = new Label("Notes:");
	    TextField notesInput = new TextField();
	    notesInput.setMinHeight(150);
	    

	    Button submitButton = new Button("Submit");
	    submitButton.setOnAction(e -> {	 
		    String vaccineName = vaccineNameInput.getText();
		    String dateAdministered = datePicker.getValue() != null ? datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) : "";
		    String notes = notesInput.getText();
		    writeImmunizationInfoToFile(String.valueOf(patientID), vaccineName, dateAdministered, notes);
	        window.close();
	        
	        NurseView.NotificationManager.addNotification("Immunization file updated for patient " + patientName);
	    });

	    VBox vaccineNameVBox = new VBox(vaccineNameLabel, vaccineNameInput);
	    VBox dateVBox = new VBox(dateLabel, datePicker);
	    VBox notesVBox = new VBox(notesLabel, notesInput);
	    vaccineNameVBox.setSpacing(8);
	    dateVBox.setSpacing(8);
	    notesVBox.setSpacing(8);
	    
	    HBox Hbox = new HBox(vaccineNameVBox,dateVBox);
	    Hbox.setSpacing(30);
	    
	    HBox submitHbox = new HBox(submitButton);
	    submitHbox.setAlignment(Pos.CENTER);
	    
	    VBox layout = new VBox(10);
	    layout.getChildren().addAll(patientName, Hbox, notesVBox, submitHbox);
	    layout.setAlignment(Pos.CENTER_LEFT);
	    layout.setPadding(new Insets(10));
	    layout.setSpacing(20);

	    Scene scene = new Scene(layout);
	    window.setScene(scene);
	    window.showAndWait(); // Show the window and wait for it to be closed before returning to the caller
	}

	public static void writeMedicationInfoToFile(GridPane gridPane) {
	    File file = new File(patientID + "_ePrescribing.txt");

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	        for (int row = 1; row <= gridPane.getRowCount(); row++) {
	            String medicationName = "";
	            String unitDetail = "";
	            String amountDetail = "";
	            String durationDetail = "";
	            String startDateDetail = "";

	            for (Node node : gridPane.getChildren()) {
	                Integer rowIndex = GridPane.getRowIndex(node);
	                Integer columnIndex = GridPane.getColumnIndex(node);

	                if (rowIndex != null && rowIndex == row) {
	                    if (node instanceof VBox) {
	                        VBox vBox = (VBox) node;
	                        medicationName = ((Label) vBox.getChildren().get(0)).getText(); // Assuming first child is the Label
	                    } else if (node instanceof ComboBox) {
	                        ComboBox<?> comboBox = (ComboBox<?>) node;
	                        String unit = comboBox.getValue() != null ? comboBox.getValue().toString() : "";
	                        if (!unit.isEmpty()) unitDetail =  " " + unit;
	                    } else if (node instanceof TextField) {
	                        TextField textField = (TextField) node;
	                        if (!textField.getText().isEmpty()) {
	                            if (columnIndex != null) {
	                                if (columnIndex == 2) { // Assuming Amount is in Column 2
	                                    amountDetail = ", Amount: " + textField.getText();
	                                } else if (columnIndex == 3) { // Assuming Duration is in Column 3
	                                    durationDetail = "Duration: " + textField.getText() + " days";
	                                }
	                            }
	                        }
	                    } else if (node instanceof DatePicker) {
	                        DatePicker datePicker = (DatePicker) node;
	                        if (datePicker.getValue() != null) {
	                            startDateDetail = "Start date: " + datePicker.getValue().format(DateTimeFormatter.ofPattern("M/d/yyyy"));
	                        }
	                    }
	                }
	            }
	            String details = Stream.of( amountDetail + unitDetail, durationDetail, startDateDetail)
	                .filter(detail -> !detail.isEmpty())
	                .collect(Collectors.joining(", "));
	                
	            if (!details.isEmpty()) {
	                writer.write(medicationName + details + "\n");
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}



	public static void writeExaminationInfoToFile( TextArea symptoms, TextArea testResult,
			TextArea prescription, TextArea notes) {
		String filePath = patientID + "_examination_info.txt";
		File file = new File(filePath);
		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println("Patient ID: " + patientID);
			writer.println("Patient Name: " + patient_Name);
			writer.println("Symptoms: " + symptoms.getText());
			writer.println("Test Results: " + testResult.getText());
			writer.println("Prescriptions/Medications: " + prescription.getText());
			writer.println("Special Notes: " + notes.getText());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void showAlert(String title, String content) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(content);
	    alert.showAndWait();
	}
	
	public static void writeImmunizationInfoToFile(String patientID, String vaccineName, String dateAdministered, String notes) {
	    String fileName = patientID + "_immunizationInfo.txt";
	    File file = new File(fileName);
	    
	    try (FileWriter writer = new FileWriter(file, true)) { 
	        writer.write("Vaccine Name: " + vaccineName + "\n");
	        writer.write("Date Administered: " + dateAdministered + "\n");
	        writer.write("Notes: " + notes + "\n");
	        writer.write("----------------------------------------\n\n"); // For separation between entries
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public static void loadImmunizations(VBox medicine, String patientID) {
	    String fileName = patientID + "_immunizationInfo.txt"; // Construct the file name
	    File file = new File(fileName);

	    try (Scanner scanner = new Scanner(file)) {
	        StringBuilder entryBuilder = new StringBuilder();
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            // Check for the separator line to process and display the current entry
	            if (line.startsWith("--------------------------------")) {
	                displayImmunizationEntry(entryBuilder.toString().trim(), medicine);
	                entryBuilder = new StringBuilder(); // Reset for the next entry
	            } else {
	                // Accumulate the lines for a single entry
	                entryBuilder.append(line).append("\n");
	            }
	        }
	        // Process and display the last entry if there's no separator at the end of the file
	        if (!entryBuilder.toString().isEmpty()) {
	            displayImmunizationEntry(entryBuilder.toString().trim(), medicine);
	        }
	    } catch (FileNotFoundException e) {
	        medicine.getChildren().add(new Label("No immunization records found."));
	        System.out.println("File not found: " + fileName);
	    } catch (Exception e) {
	        medicine.getChildren().add(new Label("Error loading immunization records."));
	        e.printStackTrace();
	    }
	}

	public static void displayImmunizationEntry(String entry, VBox medicine) {
	    String[] lines = entry.split("\n");
	    if (lines.length >= 3) {
	        // Extract and prepare each part of the entry
	        String vaccineName = lines[0].replace("Vaccine Name: ", "");
	        String dateAdministered = lines[1].replace("Date Administered: ", "");
	        String notes = lines[2].replace("Notes: ", "");

	        // Create and style labels
	        Label vaccineNameLabel = new Label("Vaccine Name: " + vaccineName);
	        vaccineNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

	        Label dateAdministeredLabel = new Label("Date Administered: " + dateAdministered);
	        Label notesLabel = new Label("Notes: " + notes);

	        // Add to VBox for this entry
	        VBox entryBox = new VBox(vaccineNameLabel, dateAdministeredLabel, notesLabel);
	        entryBox.setStyle("-fx-padding: 5; -fx-border-color: lightgray; -fx-border-width: 0.5; -fx-background-color: white;");
	        entryBox.setSpacing(5);

	        // Add the entry to the main VBox
	        medicine.getChildren().add(entryBox);
	    }
	}
	
	public static void loadMedicationList(VBox medicine, String patientID) {
	    String fileName = patientID + "_ePrescribing.txt"; // Construct the file name
	    File file = new File(fileName);

	    try (Scanner scanner = new Scanner(file)) {
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            // Assuming the line format is consistent and contains four main parts separated by commas
	            String[] parts = line.split(", ");
	            if (parts.length == 4) { // Ensure there are exactly four parts
	                String medicationName = parts[0]; // "Medication Name"
	                String amount = parts[1]; // "Amount: x units"
	                String duration = parts[2]; // "Duration: y days"
	                String startDate = parts[3]; // "Start date: z"

	                // Create a Label for medicationName and set its font to bold
	                Label medicationNameLabel = new Label(medicationName);
	                medicationNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

	                // Create Labels for amount, duration, and startDate without bold
	                Label amountLabel = new Label(amount);
	                Label durationLabel = new Label(duration);
	                Label startDateLabel = new Label(startDate);

	                // Create a VBox for each medication entry to align its details vertically
	                VBox entry = new VBox(medicationNameLabel, amountLabel, durationLabel, startDateLabel);
	                entry.setStyle("-fx-padding: 5; -fx-border-color: lightgray; -fx-border-width: 0.5;");
	                entry.setSpacing(5); // Set spacing between labels

	                // Add the entry to the medicine VBox
	                medicine.getChildren().add(entry);
	            }
	        }
	    }catch (FileNotFoundException e) {
	        medicine.getChildren().add(new Label("No medication records found."));
	        System.out.println("File not found: " + fileName);
	    } catch (Exception e) {
	        medicine.getChildren().add(new Label("Error loading medication records."));
	        e.printStackTrace();
	    }
	}

	
	public static void main(String[] args) {
		NotificationManager.loadNotificationsFromFile();
		launch(args);
	}
}