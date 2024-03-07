package org.example.movierentalsjpa.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
public class Rental extends BaseEntity<Long> implements Serializable {

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "rental_charge")
    private float rentalCharge;

    @Column(name = "rental_date")
    private LocalDateTime rentalDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    public Rental() {
    }

    public Rental(Long movieId, Long clientId, float rentalCharge, LocalDateTime rentalDate, LocalDateTime dueDate) {
        this.movieId = movieId;
        this.clientId = clientId;
        this.rentalCharge = rentalCharge;
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public float getRentalCharge() {
        return rentalCharge;
    }

    public void setRentalCharge(float rentalCharge) {
        this.rentalCharge = rentalCharge;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "rentalId=" + this.getId() +
                ", movieId=" + movieId +
                ", clientId=" + clientId +
                ", rentalCharge=" + rentalCharge +
                ", rentalDate=" + rentalDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
