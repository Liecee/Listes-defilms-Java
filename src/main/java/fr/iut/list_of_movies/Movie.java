package fr.iut.list_of_movies;

public class Movie {
    private Integer _rang;
    private String _id;
    private String _name;
    private String _year;

    public Movie(Integer rang, String id, String name, String year) {
        this._rang = rang;
        this._id = id;
        this._name = name;
        this._year = year;
    }

    public Integer get_rang() {
        return _rang;
    }

    public void set_rang(Integer rang) {
        this._rang = rang;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String id) {
        this._id = id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        this._name = name;
    }

    public String get_year() {
        return _year;
    }

    public void set_year(String year) {
        this._year = year;
    }
}
