package models;

public class Movie {
    private String name, genre, duration, format, language;
    
    public Movie() {
        name = "";
        genre = "";
        duration = "";
        format = "";
        language = "";
    }
    
    public Movie(String name, String genre, String duration, String format, String language) {
        setName(name);
        setGenre(genre);
        setDuration(duration);
        setFormat(format);
        setLanguage(language);
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
