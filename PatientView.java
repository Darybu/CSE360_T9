package application;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class PatientView extends Application {
    // Fields for patient details
	
    private Properties properties = new Properties();
    private String name;
	private String dob;
	private String password;
	private String address;
	private String email;
	private String phoneNumber;
	
    // Declare text fields as instance variables
    private TextField fullNameField;
    private TextField dobField;
    private PasswordField passwordField;
	
    private TabPane tabPane = new TabPane();
    private Tab profileTab = new Tab("Profile");
    private Tab visitSummaryTab = new Tab("Visit Summary");
    private Tab messagesTab = new Tab("Messages");
    
    private VBox activationBox; 
    private boolean isAccountActivated = false;
 
    private String username;
    private ListView<Node> notificationsList;
    private String notificationFileName;

    public PatientView(String username) {
        this.username = username;
        this.notificationsList = new ListView<>();
        this.notificationFileName = username + "_notifications.txt";
        loadNotifications();
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	loadProperties(LogIn.userName);
    	loadActivationState(); 
        
       	TextField fullNameField = new TextField(name);
        TextField dobField = new TextField(dob);
        dobField.setText(dob);
        PasswordField passwordField = new PasswordField();
        passwordField.setText(password);
        TextField addressField = new TextField(address);
        addressField.setText(address);
        TextField emailField = new TextField(email); 
        emailField.setText(email);
        TextField phoneNumberField = new TextField(phoneNumber); 
        phoneNumberField.setText(phoneNumber);
           
        // Set text fields to be non-editable initially
        fullNameField.setEditable(false);
        dobField.setEditable(false);
        passwordField.setEditable(false);
        addressField.setEditable(false);
        emailField.setEditable(false);
        phoneNumberField.setEditable(false);
        
        primaryStage.setTitle("Patient Dashboard");
               
        Image logoImage = new Image(getClass().getResourceAsStream("123.png"));
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitHeight(100);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);
        
        Image userIconImage = new Image(getClass().getResourceAsStream("user_icon.png")); 
        ImageView userIconView = new ImageView(userIconImage);
        userIconView.setFitHeight(24); 
        userIconView.setFitWidth(24);
        userIconView.setPreserveRatio(true);
        
        // Header
        Text header = new Text("Welcome, Patient!");
        
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        HBox logoAndHeader = new HBox(logoView, header);
        logoAndHeader.setAlignment(Pos.CENTER_LEFT);
        logoAndHeader.setSpacing(10); 
        logoAndHeader.setPadding(new Insets(20,0,20,20)); 
        
        VBox visitSummariesBox = new VBox(10); 
        
        ScrollPane scrollPane = new ScrollPane(visitSummariesBox);
        scrollPane.setFitToWidth(true);
        
        // Tabs
        profileTab.setStyle("-fx-font-size: 12pt");
        profileTab.setDisable(!isAccountActivated);
        profileTab.setClosable(false);
        
        visitSummaryTab.setClosable(false);
        visitSummaryTab.setStyle("-fx-font-size: 12pt");
        
        messagesTab.setClosable(false);
        messagesTab.setDisable(!isAccountActivated);
        messagesTab.setStyle("-fx-font-size: 12pt");
        
        // Messages Tab setup
        messagesTab.setOnSelectionChanged(event -> {
            if (messagesTab.isSelected()) {
                showMessageWindow();
            }
        });
        
        tabPane.getTabs().addAll( profileTab, visitSummaryTab, messagesTab);
        
        visitSummaryTab.setContent(scrollPane);
        visitSummaryTab.setDisable(!isAccountActivated); 
        
        // Personal Details
       
        GridPane detailsGrid = new GridPane();
        detailsGrid.setVgap(10);
        detailsGrid.setHgap(10);
        detailsGrid.setPadding(new Insets(20, 20, 100, 20));
        detailsGrid.setAlignment(Pos.TOP_LEFT);
       
        Text personalDetailsTitle = new Text("Personal Details");
        personalDetailsTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        GridPane.setHalignment(personalDetailsTitle, HPos.LEFT);
        detailsGrid.add(personalDetailsTitle,0,0);;
        
        ImageView userIconView1 = new ImageView(new Image(getClass().getResourceAsStream("user_icon.png")));
        userIconView1.setFitHeight(75);
        userIconView1.setFitWidth(75);
        GridPane.setValignment(userIconView1, VPos.TOP); 
        detailsGrid.add(userIconView1,0,2);
        
        int row = 3; 

     // Full Name
     detailsGrid.add(new Label("Full Name"), 0, row);
     fullNameField.setPrefWidth(250);
     detailsGrid.add(fullNameField, 1, row);
     row++;

     // Date of Birth
     detailsGrid.add(new Label("Date of Birth"), 0, row);
     detailsGrid.add(dobField, 1, row);
     row++;

     // Address
     detailsGrid.add(new Label("Address"), 0, row);
     detailsGrid.add(addressField, 1, row);
     row++;

     // Email
     detailsGrid.add(new Label("Email"), 0, row);
     detailsGrid.add(emailField, 1, row);
     row++;

     // Phone Number
     detailsGrid.add(new Label("Phone Number"), 0, row);
     detailsGrid.add(phoneNumberField, 1, row);
     row++;

     // Password
     detailsGrid.add(new Label("Password"), 0, row);
     detailsGrid.add(passwordField, 1, row);
     row++;

     for (Node node : detailsGrid.getChildren()) {
    	    if (node instanceof Label) {
    	        GridPane.setHalignment(node, HPos.RIGHT);
    	    } else if (node instanceof TextField || node instanceof Button) {
    	        GridPane.setHalignment(node, HPos.LEFT);
    	    }
    	}
     	
		    VBox detailsContainer = new VBox(detailsGrid);
		    detailsContainer.setAlignment(Pos.TOP_LEFT);
		    
		    notificationsList.setPrefHeight(200);
		    VBox notificationsBox = new VBox(10, new Label("New Notifications"), notificationsList);
		    notificationsBox.setPadding(new Insets(10));
		    notificationsBox.setStyle("-fx-border-style: solid inside; " +
		                              "-fx-border-width: 1; " +
		                              "-fx-border-insets: 5; " +
		                              "-fx-border-radius: 5; " +
		                              "-fx-border-color: grey;");
		
		    
		    // Adding logout button
		    Button logoutButton = new Button("Log out");
		    logoutButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
		    logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white;"));
		    logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;"));
		    logoutButton.setOnAction(event -> {
		        System.out.println("Logout button clicked!"); 
		        LogIn logInScreen = new LogIn();
				logInScreen.start(primaryStage);
		        
		        
		    });
		    
		    // Read patient ID
		    String readID = readPatientId(username);
		    if (readID != null && !readID.trim().isEmpty()) {
		        loadVisitSummary(readID);
		    }
		    
		     // Edit button
		    Button editInfoButton = new Button("Edit Info");
		    detailsGrid.add(editInfoButton, 2, row);
		    row++;

		    editInfoButton.setOnAction(event -> {
		        addressField.setEditable(true);
		        emailField.setEditable(true);
		        phoneNumberField.setEditable(true);
		    });

		    Button saveButton = new Button("Save");
		    detailsGrid.add(saveButton, 2, row);
		    row++;

		    saveButton.setOnAction(event -> {
		    	String patientId = readPatientId(username);
		        
		    	String fileName = patientId + "_contactInfo.txt";
		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
		            writer.write("Full Name: " + fullNameField.getText());
		            writer.newLine();
		            writer.write("Date of Birth: " + dobField.getText());
		            writer.newLine();
		            writer.write("Address: " + addressField.getText());
		            writer.newLine();
		            writer.write("Email: " + emailField.getText());
		            writer.newLine();
		            writer.write("Phone Number: " + phoneNumberField.getText());
		            writer.newLine();
		            writer.write("Password: " + passwordField.getText());
		            writer.newLine();
		            
		            addNotificationMessage("Your Contact Info Updated Successfully!");
		            
		            showAlert1("Success", "Information saved successfully!");
		        } catch (IOException e) {
		            e.printStackTrace();
		            showAlert("Error", "An error occurred while saving the information.");
		        }
		    });

		    
		    HBox logoutBox = new HBox(5, userIconView, logoutButton);
		    logoutBox.setAlignment(Pos.CENTER_RIGHT);
		    logoutBox.setPadding(new Insets(20, 20, 20, 0));
		    HBox.setHgrow(logoutBox, Priority.ALWAYS);
		    
		    HBox topContent = new HBox();
		    topContent.setPadding(new Insets(10, 20, 10, 20));
		    topContent.setAlignment(Pos.CENTER_LEFT); 
		    topContent.getChildren().addAll(logoAndHeader, logoutBox);
		    
		    activationBox = new VBox(10); 
		    activationBox.setAlignment(Pos.CENTER);
		    activationBox.setPadding(new Insets(10, 10, 10, 10));
		    activationBox.setMaxWidth(200);
		    
		    activationBox.setVisible(!isAccountActivated);
		    
		    activationBox.getChildren().add(new GridPane() {{
		        setHgap(10);
		        setVgap(10);
		        
		        ColumnConstraints col1 = new ColumnConstraints();
		        col1.setPercentWidth(30); 
		        ColumnConstraints col2 = new ColumnConstraints();
		        col2.setPercentWidth(50); 
		        ColumnConstraints col3 = new ColumnConstraints();
		        col3.setPercentWidth(20); 
		        
		        getColumnConstraints().addAll(col1, col2, col3);
		        
		        add(new Text("Activate your account here:"), 0, 0);
		        TextField idField = new TextField();
		        idField.setPromptText("Enter ID");
		        add(idField, 1, 0);
		        Button activateButton = new Button("Activate");
		        activateButton.setOnAction(event -> activateAccount(idField.getText()));
		        add(activateButton, 2, 0);
		    }});

		    
		    BorderPane topContainer = new BorderPane();
		    topContainer.setLeft(logoAndHeader); 
		    topContainer.setRight(logoutBox);
		    topContainer.setPadding(new Insets(0, 20, 0, 0));
		    
		    // Assembling the main layout
		    BorderPane mainLayout = new BorderPane();
		    mainLayout.setStyle("-fx-background-color: white;");
		    mainLayout.setTop(topContainer);
		    mainLayout.setCenter(tabPane);
		    mainLayout.setRight(notificationsBox);
		    mainLayout.setLeft(detailsContainer);
		    mainLayout.setBottom(activationBox);
		    BorderPane.setAlignment(activationBox, Pos.CENTER);
		    profileTab.setContent(detailsGrid);

    // Scene and Stage setup
    Scene scene = new Scene(mainLayout, 1101, 720);
    primaryStage.setScene(scene);
    primaryStage.show();
    
    requestActivation();
}
 
    private void requestActivation() {
        if (!isAccountActivated) {
            showAlert("Activation Required", "Your account is not activated. Please activate your account.");
        }
    }
    
    public void activateAccount(String id) {
    	
        if (id == null || id.trim().isEmpty()) {
            showAlert("Activation Error", "No ID entered. Please enter your ID to activate the account.");
            visitSummaryTab.setDisable(true);
            messagesTab.setDisable(true);
            profileTab.setDisable(true);
            return; 
        }
        
        String fileName = id + "_visitSum.txt";
        File file = new File(fileName);
        
        if (file.exists()) {
            isAccountActivated = true;
            saveActivationState(true);
            
            messagesTab.setDisable(false);
            visitSummaryTab.setDisable(false);
            profileTab.setDisable(false);
            
            activationBox.setVisible(false);
            
            savePatientId(username, id);
            
            showAlert1("Activation Successful", "Your account has been activated successfully!");
            System.out.println("Activation successful.");
            
            addNotificationMessage("Activation Successful! Welcome!");

            loadVisitSummary(id);            
            
        } else {
            isAccountActivated = false;
            saveActivationState(false);
            
            profileTab.setDisable(true);
            visitSummaryTab.setDisable(true);
            messagesTab.setDisable(true);
            
            System.out.println("Activation failed. Incorrect ID.");
            showAlert("Activation Error", "Incorrect ID. Please enter the correct ID to activate the account.");
        }
          
    }
    
    public void loadVisitSummary(String id) {
        String fileName = id + "_visitSum.txt";
        File file = new File(fileName);
        
        if (file.exists()) {
            // Use StringBuilder to concatenate all lines of the file into a single string
            StringBuilder summaryBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    summaryBuilder.append(line).append("\n"); // Append each line and a space for separation
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to read visit summary file.");
                return;
            }
            
            // Create a list containing the entire file content as a single item
            ObservableList<String> visitSummaryItems = FXCollections.observableArrayList(summaryBuilder.toString());
            
            // Create a ListView and set it as the content of the visitSummaryTab
            ListView<String> visitSummaryListView = new ListView<>(visitSummaryItems);
            visitSummaryTab.setContent(visitSummaryListView);
        } else {
            showAlert("File Not Found", "Visit summary file not found.");
        }
    }
    
    private void saveActivationState(boolean activated) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(username + "_activation_state.txt"))) {
            writer.write(Boolean.toString(activated));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadActivationState() {
        File file = new File(username + "_activation_state.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                isAccountActivated = Boolean.parseBoolean(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void savePatientId(String username, String patientId) {
        String fileName = username + "_patientID.txt"; // File to store patient ID
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(patientId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readPatientId(String username) {
        String patientId = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(username + "_patientID.txt"))) {
            patientId = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patientId;
    }
    
    private void loadProperties(String username) {
		String ID = "00000";
		File file = new File(username + "_Patient_LoginInfo.txt");
		if (!file.exists()) {
			System.out.println("The file does not exist: " + file.getAbsolutePath());
			return;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			reader.readLine(); // Skip verification code
			reader.readLine(); // Skip unused line if necessary
			String firstName = reader.readLine().trim();
			String lastName = reader.readLine().trim();
			String month = reader.readLine().trim();
			String day = reader.readLine().trim();
			String year = reader.readLine().trim();
			reader.readLine(); // Skip username or another unused line
			String pass = reader.readLine().trim();

			name = firstName + " " + lastName;
			dob = month + "/" + day + "/" + year;
			password = pass;

		} catch (IOException e) {
			e.printStackTrace();
		}

		// get the ID
		File fileID = new File(username + "_PatientID.txt");
		if (!fileID.exists()) {
			System.out.println("The file does not exist: " + fileID.getAbsolutePath());
			return;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(fileID))) {
			ID = reader.readLine().trim();
			System.out.println("Patient ID after load file is " + ID);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// load Address, Email, Phone number from patient contact info file
		File file2 = new File(ID + "_ContactInfo.txt");
		if (!file2.exists()) {
		    System.out.println("The file does not exist: " + file2.getAbsolutePath());
		    return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file2))) { // Use file2 instead of fileID
		    String line;
		    while ((line = reader.readLine()) != null) {
		        System.out.println("reading the file: " + file2.getAbsolutePath());
		        if (line.startsWith("Address: ")) {
		            address = line.substring("Address: ".length());
		            System.out.println(address);
		        } else if (line.startsWith("Email: ")) {
		            email = line.substring("Email: ".length());
		            System.out.println(email);
		        } else if (line.startsWith("Phone Number: ")) {
		            phoneNumber = line.substring("Phone Number: ".length());
		            System.out.println(phoneNumber);
		        }
		        // Since the original file format includes Name and Date of Birth,
		        // you might want to add conditions to extract these values as well.
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}

	}
    
    // Method to add a message to the user-specific notification file and list
    public void addNotificationMessage(String message) {
        try (FileWriter fw = new FileWriter(notificationFileName, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(message);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadNotifications(); // Reload notifications to update the list view
    }

    private void loadNotifications() {
        ObservableList<Node> items = FXCollections.observableArrayList();
        String notificationFileName = username + "_notifications.txt";
        
        String patientId = readPatientId(username);
        String messageFileName = patientId + "_message.txt"; 

        try {
            // Check if the message file exists and add a notification for it
            File messageFile = new File(messageFileName);
            if (messageFile.exists()) {
                HBox hbox = new HBox(10); // 10 is the spacing between elements in the HBox
                Label messageLabel = new Label("You have a New Message!");
                Button viewButton = new Button("View");

                viewButton.setOnAction(e -> viewMessage());
                hbox.getChildren().addAll(messageLabel, viewButton);
                items.add(hbox);
            }

            // Load other notifications from the notification file
            BufferedReader reader = new BufferedReader(new FileReader(notificationFileName));
            String line;
            while ((line = reader.readLine()) != null) {
                Label messageLabel = new Label(line);
                HBox hbox = new HBox(messageLabel); // If you want other properties or buttons for each, add them here
                items.add(hbox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        notificationsList.getItems().setAll(items);
    }
    
    private void viewMessage() {
        Stage messageStage = new Stage();
        messageStage.setTitle("Message from the Office");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        String patientId = readPatientId(username);
        String messageFileName = patientId + "_message.txt"; // File name where messages are stored
        String messageContent = ""; // This will hold the message text
        try (BufferedReader reader = new BufferedReader(new FileReader(messageFileName))) {
            StringBuilder messageBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                messageBuilder.append(line).append("\n");
            }
            messageContent = messageBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextArea messageArea = new TextArea(messageContent);
        messageArea.setEditable(false);

        TextField replyField = new TextField();
        replyField.setPromptText("Type your reply...");

        Button sendReplyButton = new Button("Send Reply");
        sendReplyButton.setOnAction(e -> sendReply(replyField.getText(), messageStage));

        layout.getChildren().addAll(new Label("Message:"), messageArea, replyField, sendReplyButton);

        Scene scene = new Scene(layout, 400, 300);
        messageStage.setScene(scene);
        messageStage.show();
    }
    
    private void sendReply(String reply, Stage stage) {
        if (reply.isEmpty()) {
            showAlert("Error", "Reply cannot be empty.");
            return;
        }

        // Assuming readPatientId(username) is a method that retrieves the patient's ID
        String patientId = readPatientId(username);
        if (patientId == null || patientId.trim().isEmpty()) {
            showAlert("Error", "Invalid patient ID.");
            return;
        }

        String fileName = patientId + "_messageReply.txt";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String formattedDateTime = now.format(formatter);

        String content = reply + "\n" + formattedDateTime;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {  // Overwrite the file
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error Saving Reply", "An error occurred while saving the reply.");
            return;
        }

        System.out.println("Reply saved: " + reply);
        stage.close();
    }
    
    private void showMessageWindow() {
        Stage messageStage = new Stage();
        messageStage.setTitle("Send a Message to Doctor/Nurse");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Type your message here...");

        Button sendButton = new Button("Send Message");
        sendButton.setOnAction(e -> {
            String message = messageArea.getText();
            if (!message.trim().isEmpty()) {
                // Call a method to handle sending the message
                sendMessageToStaff(message);
                messageStage.close();
            } else {
                showAlert("Error", "Message cannot be empty.");
            }
        });

        layout.getChildren().addAll(new Label("Your Message:"), messageArea, sendButton);

        Scene scene = new Scene(layout, 300, 200);
        messageStage.setScene(scene);
        messageStage.show();
    }
    
    private void sendMessageToStaff(String message) {
        System.out.println("Message to Doctor/Nurse: " + message);

        String patientId = readPatientId(username);
        String fileName = patientId + "_messagesToStaff.txt";

        // Prepare the content to be written to the file, including a timestamp
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String formattedDateTime = now.format(formatter);
        String content = message + "\n" + formattedDateTime;

        // Overwrite the existing message in the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {  // false to overwrite the file
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error Saving Message", "An error occurred while saving the message.");
        }
    }
	
	private void showAlert(String title, String message) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
	private void showAlert1(String title, String message) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}	
    
public static void main(String[] args) {
    launch(args);
	}
}