package fr.iut.list_of_movies;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MoviesController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rang_view.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("_rang"));
        id_view.setCellValueFactory(new PropertyValueFactory<Movie, String>("_id"));
        name_view.setCellValueFactory(new PropertyValueFactory<Movie, String>("_name"));
        year_view.setCellValueFactory(new PropertyValueFactory<Movie, String>("_year"));
    }

    @FXML
    private Label show_add;
    @FXML
    private TableView table_view;
    @FXML
    private TableColumn rang_view;
    @FXML
    private TableColumn id_view;
    @FXML
    private TableColumn name_view;
    @FXML
    private TableColumn year_view;
    @FXML
    private Button import_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private GridPane grid_pane;
    @FXML
    private TextField rang_inp;
    @FXML
    private TextField id_inp;
    @FXML
    private TextField name_inp;
    @FXML
    private TextField year_inp;
    @FXML
    private Button add_btn;
    @FXML
    private Button clear_btn;
    @FXML
    private Label error_text;


    @FXML
    private void importCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        String path = fileChooser.showOpenDialog(import_btn.getScene().getWindow()).getAbsolutePath();
        List<List<String>> records = buffer(path);
        ObservableList<Movie> data = table_view.getItems();
        for (int i = 1; i < records.size(); i++) {
            List<String> record = records.get(i);
            Integer rang = Integer.parseInt(record.get(0));
            String id = record.get(1);
            String name = record.get(2);
            String year = record.get(3);
            Movie movie = new Movie(rang, id, name, year);
            data.add(movie);
        }
    }

    @FXML
    private void showAdd() {
        add_btn.setText("Ajouter");
        grid_pane.setVisible(true);
        clearForm();
    }

    @FXML
    private void addNew() {
        Integer rang = Integer.parseInt(rang_inp.getText());
        String id = id_inp.getText();
        String name = name_inp.getText();
        String year = year_inp.getText();

        addMovie(rang, id, name, year);
    }

    @FXML
    private void selectedMovie() {
        delete_btn.setVisible(true);
        grid_pane.setVisible(true);
        error_text.setText("");
        add_btn.setText("Update");
        Movie movie = (Movie) table_view.getSelectionModel().getSelectedItem();
        rang_inp.setText(movie.get_rang().toString());
        id_inp.setText(movie.get_id());
        name_inp.setText(movie.get_name());
        year_inp.setText(movie.get_year());
    }
    @FXML
    private void deleteMovie() {
        ObservableList<Movie> allMovies, selectedMovie;
        allMovies = table_view.getItems();
        selectedMovie = table_view.getSelectionModel().getSelectedItems();
        selectedMovie.forEach(allMovies::remove);
        delete_btn.setVisible(false);
        clearForm();
        grid_pane.setVisible(false);
        show_add.setVisible(true);
    }

    @FXML
    private void clearTable() {
        table_view.getItems().clear();
    }

    private void clearForm() {
        rang_inp.clear();
        id_inp.clear();
        name_inp.clear();
        year_inp.clear();

        error_text.setText("");
    }

    public void addMovie(Integer rang, String id, String name, String year) {
        if (add_btn.getText() == "Update") {
            Movie movie = (Movie) table_view.getSelectionModel().getSelectedItem();
            movie.set_rang(rang);
            movie.set_id(id);
            movie.set_name(name);
            movie.set_year(year);
            table_view.refresh();
            clearForm();
            grid_pane.setVisible(false);
            show_add.setVisible(true);
            return;
        }
        if (id.isEmpty() || name.isEmpty() || year.isEmpty()) {
            error_text.setText("All fields are required");
            return;
        };
        for (int i = 0; i < table_view.getItems().size(); i++) {
            Movie movie = (Movie) table_view.getItems().get(i);
            if (movie.get_rang().equals(rang)) {
                error_text.setText("Rang already exists");
                return;
            };
        }

        Movie movie = new Movie(rang, id, name, year);
        ObservableList<Movie> data = table_view.getItems();
        data.add(movie);
        table_view.setItems(data);

        clearForm();

        grid_pane.setVisible(false);
        show_add.setVisible(true);
    }


    private List<List<String>> buffer(String path) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                values = Arrays.stream(values).map(s -> s.replaceAll("^\"|\"$", "")).toArray(String[]::new);
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }



}