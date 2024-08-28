package a5.cmpt213.client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HelloApplication extends Application {

    private TilePane tilePane;

    @Override
    public void start(Stage stage) throws Exception {
        VBox mainBox = new VBox();
        mainBox.setSpacing(10);
        mainBox.setPadding(new Insets(10));

        tilePane = new TilePane();
        tilePane.setPadding(new Insets(10));
        tilePane.setHgap(10);
        tilePane.setVgap(10);

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(event -> refreshCards());

        Button addButton = new Button("Add Tokimon");
        addButton.setOnAction(event -> openAddWindow());

        mainBox.getChildren().addAll(refreshButton, addButton, tilePane);

        Scene scene = new Scene(mainBox, 1050, 1050);
        stage.setScene(scene);
        stage.setTitle("Tokimon Cards");
        stage.show();

        refreshCards();
    }

    private void refreshCards() {
        tilePane.getChildren().clear();
        try {
            String apiUrl = "http://localhost:8080/api/tokimon/all";
            String jsonResponse = fetchJson(apiUrl);
            String[] jsonObjects = parseJson(jsonResponse);

            for (String jsonObject : jsonObjects) {
                VBox card = createCard(jsonObject);
                tilePane.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String fetchJson(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return response.toString();
    }

    private String[] parseJson(String jsonResponse) {
        jsonResponse = jsonResponse.replaceAll("\\[\\{|}\\]", "");
        String[] objects = jsonResponse.split("\\},\\{");
        for (int i = 0; i < objects.length; i++) {
            objects[i] = objects[i].replaceAll("[{}\"]", "");
        }
        return objects;
    }

    private VBox createCard(String jsonObject) {
        VBox card = new VBox();
        card.setSpacing(5);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        card.setOnMouseClicked(event -> openDetailsWindow(jsonObject));

        String[] fields = jsonObject.split(",");
        String name = getFieldValue(fields, "name");
        String type = getFieldValue(fields, "type");
        String url = getFieldValue(fields, "url");

        Label nameLabel = new Label("Name: " + name);
        Label typeLabel = new Label("Type: " + type);

        ImageView imageView = new ImageView();

        try {
            Image image = new Image(url);
            imageView.setImage(new Image(image.getUrl()));
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid URL: " + url);
            imageView.setImage(new Image("file:src/main/resources/placeholder.png"));
        }

        card.getChildren().addAll(nameLabel, typeLabel, imageView);
        return card;
    }

    private void openDetailsWindow(String jsonObject) {
        String[] fields = jsonObject.split(",");
        String name = getFieldValue(fields, "name");
        String type = getFieldValue(fields, "type");
        String hp = getFieldValue(fields, "hp");
        String rarity = getFieldValue(fields, "rarity");
        String url = getFieldValue(fields, "url");
        String id = getFieldValue(fields, "id");

        VBox detailsBox = new VBox();
        detailsBox.setSpacing(10);
        detailsBox.setPadding(new Insets(10));

        Label nameLabel = new Label("Name: " + name);
        Label typeLabel = new Label("Type: " + type);
        Label hpLabel = new Label("HP: " + hp);
        Label rarityLabel = new Label("Rarity: " + rarity);
        Label idLabel = new Label("ID: " + id);
        Label urlLabel = new Label("URL: " + url);

        ImageView imageView = new ImageView();

        try {
            Image image = new Image(url);
            imageView.setImage(new Image(image.getUrl()));
            imageView.setFitHeight(400);
            imageView.setPreserveRatio(true);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid URL: " + url);
            imageView.setImage(new Image("https://www.tokidoki.it/cdn/shop/products/Tokimon_9_Inch_Plush.jpg?v=1649452547"));
        }

        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> openEditWindow(jsonObject));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            deleteTokimon(id);
            ((Stage) deleteButton.getScene().getWindow()).close();
        });

        detailsBox.getChildren().addAll(nameLabel, typeLabel, hpLabel, rarityLabel, idLabel, urlLabel, imageView, editButton, deleteButton);

        Stage detailsStage = new Stage();
        detailsStage.setTitle(name);
        Scene detailsScene = new Scene(detailsBox, 800, 800);
        detailsStage.setScene(detailsScene);
        detailsStage.show();
    }

    private void openEditWindow(String jsonObject) {
        String[] fields = jsonObject.split(",");
        String name = getFieldValue(fields, "name");
        String type = getFieldValue(fields, "type");
        String hp = getFieldValue(fields, "hp");
        String rarity = getFieldValue(fields, "rarity");
        String url = getFieldValue(fields, "url");
        String id = getFieldValue(fields, "id");

        VBox editBox = new VBox();
        editBox.setSpacing(10);
        editBox.setPadding(new Insets(10));

        TextField nameField = new TextField(name);
        TextField typeField = new TextField(type);
        TextField hpField = new TextField(hp);
        TextField rarityField = new TextField(rarity);
        TextField urlField = new TextField(url);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                updateTokimon(id, nameField.getText(), typeField.getText(), hpField.getText(), rarityField.getText(), urlField.getText());
                refreshCards();
                ((Stage) saveButton.getScene().getWindow()).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        editBox.getChildren().addAll(new Label("Name:"), nameField,
                new Label("Type:"), typeField,
                new Label("HP:"), hpField,
                new Label("Rarity:"), rarityField,
                new Label("URL:"), urlField,
                saveButton);

        Stage editStage = new Stage();
        editStage.setTitle("Edit Tokimon");
        Scene editScene = new Scene(editBox, 800, 800);
        editStage.setScene(editScene);
        editStage.show();
    }

    private void openAddWindow() {
        VBox addBox = new VBox();
        addBox.setSpacing(10);
        addBox.setPadding(new Insets(10));

        TextField nameField = new TextField();
        TextField typeField = new TextField();
        TextField hpField = new TextField();
        TextField rarityField = new TextField();
        TextField urlField = new TextField();

        Button addButton = new Button("Add Tokimon");
        addButton.setOnAction(event -> {
            try {
                addNewTokimon(nameField.getText(), typeField.getText(), hpField.getText(), rarityField.getText(), urlField.getText());
                refreshCards();
                ((Stage) addButton.getScene().getWindow()).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addBox.getChildren().addAll(new Label("Name:"), nameField,
                new Label("Type:"), typeField,
                new Label("HP:"), hpField,
                new Label("Rarity:"), rarityField,
                new Label("URL:"), urlField,
                addButton);

        Stage addStage = new Stage();
        addStage.setTitle("Add Tokimon");
        Scene addScene = new Scene(addBox, 400, 400);
        addStage.setScene(addScene);
        addStage.show();
    }

    private String getFieldValue(String[] fields, String key) {
        for (String field : fields) {
            if (key.equals("url") && field.startsWith(key)) {
                return field.split(":")[1].trim() + ':' + field.split(":")[2].trim();
            }
            if (field.startsWith(key)) {
                return field.split(":")[1].trim();
            }
        }
        return "";
    }

    private void addNewTokimon(String name, String type, String hp, String rarity, String url) throws Exception {
        String apiUrl = "http://localhost:8080/api/tokimon/add";
        URL urlObj = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = String.format(
                "{\"name\": \"%s\", \"type\": \"%s\", \"hp\": %s, \"rarity\": \"%s\", \"url\": \"%s\"}",
                name, type, hp, rarity, url);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }

    private void deleteTokimon(String id) {
        try {
            String apiUrl = "http://localhost:8080/api/tokimon/" + id;
            URL urlObj = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("DELETE");
            connection.getResponseCode();
            refreshCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTokimon(String id, String name, String type, String hp, String rarity, String url) throws Exception {
        String apiUrl = "http://localhost:8080/api/tokimon/edit/" + id;
        URL urlObj = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = String.format(
                "{\"name\": \"%s\", \"type\": \"%s\", \"hp\": %s, \"rarity\": \"%s\", \"url\": \"%s\"}",
                name, type, hp, rarity, url);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
