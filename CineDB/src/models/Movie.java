package models;

public class Movie {
    private int id;
    private String name, genre, duration, format, language;
    
    public Movie() {
        id = 0;
        name = "";
        genre = "";
        duration = "";
        format = "";
        language = "";
    }
    
    //Constructor con id (para mostrar los registros en la tabla)
    public Movie(int id, String name, String genre, String duration, String format, String language) {
        this.id = id;
        setName(name);
        setGenre(genre);
        setDuration(duration);
        setFormat(format);
        setLanguage(language);
    }
    
    // Constructor sin id (para cuando se registra una nueva pelicula)
    public Movie(String name, String genre, String duration, String format, String language) {
        setName(name);
        setGenre(genre);
        setDuration(duration);
        setFormat(format);
        setLanguage(language);
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    
}
