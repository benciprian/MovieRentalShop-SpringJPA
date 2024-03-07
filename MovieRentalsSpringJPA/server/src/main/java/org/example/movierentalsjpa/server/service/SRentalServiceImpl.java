package org.example.movierentalsjpa.server.service;

import org.apache.commons.logging.LogFactory;
import org.example.movierentalsjpa.common.IRentalService;
import org.example.movierentalsjpa.common.model.*;
import org.example.movierentalsjpa.common.model.exceptions.MovieRentalsException;
import org.example.movierentalsjpa.server.repository.RentalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

@Service
public class SRentalServiceImpl implements IRentalService {
    private static final Logger log = LoggerFactory.getLogger(SRentalServiceImpl.class);

    @Autowired
    RentalRepository rentalRepository;

    private final SMovieServiceImpl movieService;
    private final SClientServiceImpl clientService;

    public SRentalServiceImpl(RentalRepository rentalRepository,
                              SMovieServiceImpl movieService,
                              SClientServiceImpl clientService) {
        this.rentalRepository = rentalRepository;
        this.movieService = movieService;
        this.clientService = clientService;
    }


    @Override
    public Iterable<Rental> getAllRentals() {
        log.trace("getAllRentals --- method entered");
        List<Rental> result = rentalRepository.findAll();
        log.trace("getAllRentals: result={}", result);
        return result;
    }

    @Override
    public Rental getRentalById(Long id) {
        log.trace("getRentalById --- method entered");

        Rental result = rentalRepository.findById(id).orElseThrow(() -> new MovieRentalsException("Rental not found with ID: " + id));

        log.trace("getRentalById: rental={}", result);

        return result;
    }

    @Override
    public void rentAMovie(Rental rental) {
        log.trace("rentAMovie: rental={}", rental);

        Rental result = rentalRepository.save(rental);

        log.trace("rentAMovie: rental={}", result);
    }

    @Override
    public void updateRentalTransaction(Rental rental) {
        log.trace("updateRentalTransaction --- method entered");
        if(!rentalRepository.existsById(rental.getId())){
            log.error("rental with ID {} not found", rental.getId());
            throw new MovieRentalsException("Rental not found with ID " + rental.getId());
        }
        Rental result = rentalRepository.save(rental);
        log.trace("updateRentalTransaction: rental{}", result);
    }

    @Override
    public void deleteMovieRental(Long id) {
        log.trace("deleteMovieRental --- method entered");
        if(!rentalRepository.existsById(id)){
            log.trace("rental with ID {} not found", id);
            throw new MovieRentalsException("Rental not found with ID " + id);
        }
        rentalRepository.deleteById(id);
        log.trace("rental{} successfully deleted", id);
    }

    /**
     * Sort movies by the number of rents in descending order.
     *
     * @return an ordered list of movie DTO(Movie, counter).
     */
    @Override
    public Iterable<MovieRentalsDTO> moviesByRentNumber() {
        log.trace("moviesByRentNumber --- method entered");
        Map<Long, Integer> mapMovieIdRentCounter = new HashMap<>();
        List<MovieRentalsDTO> moviesByRentCounterDesc = new ArrayList<>();
        try {
            Iterable<Rental> rentals = getAllRentals();
            for (Rental r : rentals) {
                Integer counter = 0;
                for (Rental rental : rentals) {
                    if (rental.getMovieId().equals(r.getMovieId())) {
                        counter++;
                    }
                }
                mapMovieIdRentCounter.put(r.getMovieId(), counter);
            }
            mapMovieIdRentCounter.forEach((k, v) -> {
                MovieRentalsDTO movieDTO = new MovieRentalsDTO(movieService.getMovieById(k), v);
                if (moviesByRentCounterDesc.isEmpty()) {
                    moviesByRentCounterDesc.add(movieDTO);
                } else {
                    boolean flag = false;
                    for (MovieRentalsDTO m : moviesByRentCounterDesc) {
                        if (m.getRentCounter() <= movieDTO.getRentCounter()) {
                            moviesByRentCounterDesc.add(moviesByRentCounterDesc.indexOf(m), movieDTO);
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        moviesByRentCounterDesc.add(movieDTO);
                    }
                }
            });
            log.trace("moviesByRentNumber: result{}", moviesByRentCounterDesc);
            return moviesByRentCounterDesc;
        } catch (MovieRentalsException e) {
            throw new MovieRentalsException("Rental Service exception: " + e.getMessage());
        }
    }

    /**
     * Sort clients by the number of rented movies in descending order.
     *
     * @return an ordered list of client DTO(Client, counter).
     * @throws MovieRentalsException if there are database connection problems.
     */
    @Override
    public Iterable<ClientRentalsDTO> clientsByRentNumber() {
        log.trace("clientsByRentNumber --- method entered");
        Map<Long, Integer> mapClientIdRentedMovies = new HashMap<>();
        List<ClientRentalsDTO> orderedClientsByRentedMovies = new ArrayList<>();
        try {
            Iterable<Rental> rentals = getAllRentals();
            for (Rental r : rentals) {
                Long clientId = r.getClientId();
                Integer counter = 0;
                for (Rental rental : rentals) {
                    if (rental.getClientId().equals(clientId)) {
                        counter++;
                    }
                }
                mapClientIdRentedMovies.put(clientId, counter);
            }
            mapClientIdRentedMovies.forEach((k, v) -> {
                ClientRentalsDTO clientDTO = new ClientRentalsDTO(clientService.getClientById(k), v);
                if (orderedClientsByRentedMovies.isEmpty()) {
                    orderedClientsByRentedMovies.add(clientDTO);
                } else {
                    boolean flag = false;
                    for (ClientRentalsDTO c : orderedClientsByRentedMovies) {
                        if (c.getRentCounter() <= clientDTO.getRentCounter()) {
                            orderedClientsByRentedMovies.add(orderedClientsByRentedMovies.indexOf(c), clientDTO);
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        orderedClientsByRentedMovies.add(clientDTO);
                    }
                }
            });
            log.trace("clientsByRentNumber: result{}", orderedClientsByRentedMovies);
            return orderedClientsByRentedMovies;
        } catch (MovieRentalsException e) {
            throw new MovieRentalsException("Rental Service Exception " + e.getMessage());
        }
    }

    /**
     * Generate report of the rented movies, total charges and rent dates for a given Client.
     *
     * @param id must not be null.
     * @return the result DTO.
     */
    @Override
    public ClientRentReportDTO generateReportByClient(Long id) {
        log.trace("generateReportByClient --- method entered");
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null. ");
        }
        List<Movie> moviesList = new ArrayList<>();
        List<LocalDateTime> rentDates = new ArrayList<>();
        float totalCharges = 0.00f;
        int counter = 0;
        Client client = clientService.getClientById(id);
        try {
            Predicate<Rental> clientIdFilter = rental -> rental.getClientId().equals(id);
            rentalRepository.findAll().forEach(rental -> {
                if (clientIdFilter.test(rental)) {
                    moviesList.add(movieService.getMovieById(rental.getMovieId()));
                    rentDates.add(rental.getRentalDate());
                }
            });
            for (Rental rental :
                    rentalRepository.findAll()) {
                if (clientIdFilter.test(rental)) {
                    totalCharges += rental.getRentalCharge();
                    counter++;
                }
            }
        } catch (MovieRentalsException e) {
            throw new MovieRentalsException("Rental Service exception: " + e.getMessage());
        }
        ClientRentReportDTO result = new ClientRentReportDTO(client, moviesList, totalCharges, rentDates, counter);
        log.trace("generateReportByClient: result{}", result);
        return result;
    }

    /**
     * Generate report of the rented movies, total charges and rent dates for a given Movie.
     *
     * @param id must not be null
     * @return the result DTO.
     */
    @Override
    public MovieRentReportDTO generateReportByMovie(Long id) {
        log.trace("generateReportByMovie --- method entered");
        if (id == null) {
            throw new MovieRentalsException("Id must not be null. ");
        }
        List<Client> clientList = new ArrayList<>();
        List<LocalDateTime> rentDates = new ArrayList<>();
        float totalCharges = 0.00f;
        int counter = 0;
        Movie movie = movieService.getMovieById(id);
        try {
            Predicate<Rental> movieIdFilter = rental -> rental.getMovieId().equals(id);
            rentalRepository.findAll().forEach(rental -> {
                if (movieIdFilter.test(rental)) {
                    clientList.add(clientService.getClientById(rental.getClientId()));
                    rentDates.add(rental.getRentalDate());
                }
            });
            for (Rental rental :
                    rentalRepository.findAll()) {
                if (movieIdFilter.test(rental)) {
                    totalCharges += rental.getRentalCharge();
                    counter++;
                }
            }
        } catch (MovieRentalsException e) {
            throw new MovieRentalsException("Rental Service Exception: " + e.getMessage());
        }
        MovieRentReportDTO result = new MovieRentReportDTO(movie, clientList, totalCharges, rentDates, counter);
        log.trace("generateReportByMovie: result{}", result);
        return result;
    }
}

