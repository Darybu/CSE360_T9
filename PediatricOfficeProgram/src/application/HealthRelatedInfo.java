package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

public class HealthRelatedInfo extends Application {
    @Override
    public void start(Stage primaryStage) {
        TreeItem<String> rootItem = new TreeItem<>("Health-Related Information");
        rootItem.setExpanded(true);

        TreeItem<String> contactInfoItem = new TreeItem<>(" > Contact Info");
        TreeItem<String> insuranceInfoItem = new TreeItem<>(" > Insurance Info");
        TreeItem<String> pharmacyInfoItem = new TreeItem<>(" > Pharmacy Info");

        rootItem.getChildren().addAll(contactInfoItem, insuranceInfoItem, pharmacyInfoItem);

        TreeView<String> tree = new TreeView<>(rootItem);

        tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getValue().contains("Contact Info")) {
                    showDetailsInSeparateWindow("Contact Information", "patient.txt");
                } else if (newValue.getValue().contains("Insurance Info")) {
                    showDetailsInSeparateWindow("Insurance Information", "insurance.txt");
                }else if (newValue.getValue().contains("Pharmacy Info")) {
                    showDetailsInSeparateWindow("Pharmacy Information", "pharmacy.txt");
                }
            }
        });

        VBox layout = new VBox(10, tree);
        layout.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(layout, 500, 400));
        primaryStage.show();
    }

    public static void showDetailsInSeparateWindow(String title, String fileName) {
        Stage detailsStage = new Stage();
        VBox layout = new VBox(10);
        TextArea detailsArea = new TextArea();
        
        
        String filePath = "/Users/tuannguyen/ASUworkspace/PediatricOfficeProgram/" + fileName;
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            detailsArea.setText(content);
        } catch (IOException e) {
            detailsArea.setText("Failed to load details.");
            e.printStackTrace();
        }

        detailsArea.setEditable(false); 
        Button editBtn = new Button("Edit");
        Button saveBtn = new Button("Save");
        
        editBtn.setStyle("-fx-font-size: 14px; -fx-min-width: 100px;");
        saveBtn.setStyle("-fx-font-size: 14px; -fx-min-width: 100px;");
        
        editBtn.setOnAction(event -> detailsArea.setEditable(true));
        saveBtn.setOnAction(event -> {
            try {
                Files.write(Paths.get(filePath), detailsArea.getText().getBytes());
                detailsArea.setEditable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        HBox buttonBox = new HBox(25, editBtn, saveBtn);
        layout.getChildren().addAll(detailsArea, buttonBox);
        
        Scene scene = new Scene(layout, 500, 300);
        detailsStage.setScene(scene);
        detailsStage.setTitle(title);
        detailsStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
