import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Recomendador extends Application {

    private MusicRecommendationLogic logic; // CLASE DE MANEJO DE LOGICA DIFUSA

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logic = new MusicRecommendationLogic();

        // ELEMENTOS VISUALES
        Label moodLabel = new Label("Estado de ánimo:");
        moodLabel.setFont(Font.font(16));
        moodLabel.setTextFill(Color.BLUE);

        ComboBox<String> moodComboBox = new ComboBox<>();
        moodComboBox.getItems().addAll("Feliz", "Triste", "Relajado", "Energético");

        Label timeLabel = new Label("Hora:");
        timeLabel.setFont(Font.font(16));
        timeLabel.setTextFill(Color.BLUE);

        ComboBox<String> timeComboBox = new ComboBox<>();
        timeComboBox.getItems().addAll("Mañana", "Tarde", "Noche");

        Label genreLabel = new Label("Género musical:");
        genreLabel.setFont(Font.font(16));
        genreLabel.setTextFill(Color.BLUE);

        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll("Pop", "Rock", "Jazz", "Clásica");

        Button recommendButton = new Button("Recomendar música");
        recommendButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: white;");

        TextArea recommendationTextArea = new TextArea();
        recommendationTextArea.setStyle("-fx-background-color: lightgray;");
        recommendationTextArea.setEditable(false); // DESHABILITAR EL BOTON

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(moodLabel, 0, 0);
        gridPane.add(moodComboBox, 1, 0);
        gridPane.add(timeLabel, 0, 1);
        gridPane.add(timeComboBox, 1, 1);
        gridPane.add(genreLabel, 0, 2);
        gridPane.add(genreComboBox, 1, 2);
        gridPane.add(recommendButton, 0, 3, 2, 1);
        gridPane.add(recommendationTextArea, 0, 4, 2, 1);

        // BOTON PARA RECOMENDAR PLAYLIST
        recommendButton.setOnAction(event -> {
            String mood = moodComboBox.getValue();
            String time = timeComboBox.getValue();
            String genre = genreComboBox.getValue();

            // DESHABILITAR PARA EVITAR VARIOS CLICKS SIMULTANEOS
            recommendButton.setDisable(true);

            if (mood == null || time == null || genre == null) {
                recommendationTextArea.setText("Selecciona todas las opciones");
                recommendButton.setDisable(false); // REHABILITAR BOTON DESPUES DEL ERROR
                return;
            }

            // CONVERTIR LOS INPUTS DEL USUARIO A VALOR NUMERICO
            double moodValue = convertMoodValue(mood);
            double timeValue = convertTimeValue(time);
            double genreValue = convertGenreValue(genre);

            double recommendationValue = logic.getRecommendation(moodValue, timeValue, genreValue);
            String playlist = logic.getPlaylist(recommendationValue);

            recommendationTextArea.setText("Recomendación de música:\n" + playlist);

            recommendButton.setDisable(false);
        });

        // CREACION DE INTERFAZ
        Scene scene = new Scene(gridPane, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double convertMoodValue(String mood) {
        switch (mood) {
            case "Feliz":
                return 0.75; 
            case "Triste":
                return 0.25; 
            case "Relajado":
                return 0.5; 
            case "Energético":
                return 0.875; 
            default:
                return 0; 
        }
    }

    private double convertTimeValue(String time) {
        switch (time) {
            case "Mañana":
                return 5; 
            case "Tarde":
                return 14; 
            case "Noche":
                return 22; 
            default:
                return 0; 
        }
    }

    private double convertGenreValue(String genre) {
        switch (genre) {
            case "Pop":
                return 1.125; 
            case "Rock":
                return 1.875; 
            case "Jazz":
                return 2.625; 
            case "Clásica":
                return 3.375; 
            default:
                return 0; 
        }
    }
}