package org.example.movierentalsjpa.common.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "movies")
public class Movie extends BaseEntity<Long> implements Serializable {
    private String title;

    private int year;

    @Enumerated(EnumType.STRING)
    private MovieGenres genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_restriction")
    private AgeRestrictions ageRestrictions;

    @Column(name = "rental_price")
    private float rentalPrice;

    private boolean available;

    public Movie() {
    }

    public Movie(String title, int year, MovieGenres genre, AgeRestrictions ageRestrictions, float rentalPrice, boolean available) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.ageRestrictions = ageRestrictions;
        this.rentalPrice = rentalPrice;
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public MovieGenres getGenre() {
        return genre;
    }

    public void setGenre(MovieGenres genre) {
        this.genre = genre;
    }

    public AgeRestrictions getAgeRestrictions() {
        return ageRestrictions;
    }

    public void setAgeRestrictions(AgeRestrictions ageRestrictions) {
        this.ageRestrictions = ageRestrictions;
    }

    public float getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(float rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (year != movie.year) return false;
        return title.equals(movie.title);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + this.getId() + ", " +
                "title='" + title + '\'' +
                ", year=" + year +
                ", genre=" + genre +
                ", ageRestrictions=" + ageRestrictions +
                ", rentalPrice=" + rentalPrice +
                ", available=" + available +
                '}';
    }
}
