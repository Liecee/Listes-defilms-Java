module fr.iut.list_of_movies {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.iut.list_of_movies to javafx.fxml;
    exports fr.iut.list_of_movies;
}