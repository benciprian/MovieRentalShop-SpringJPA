package org.example.movierentalsjpa.common;

import org.example.movierentalsjpa.common.model.*;


public interface IRentalService {

    Iterable<Rental> getAllRentals();

    Rental getRentalById(Long id);

    void rentAMovie(Rental rental);

    void updateRentalTransaction(Rental rental);

    void deleteMovieRental(Long rentalId);

    Iterable<MovieRentalsDTO> moviesByRentNumber();

    Iterable<ClientRentalsDTO> clientsByRentNumber();

    ClientRentReportDTO generateReportByClient(Long id);

    MovieRentReportDTO generateReportByMovie(Long id);

}
