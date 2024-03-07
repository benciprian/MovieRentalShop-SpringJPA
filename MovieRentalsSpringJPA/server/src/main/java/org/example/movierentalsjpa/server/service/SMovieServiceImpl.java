package org.example.movierentalsjpa.server.service;

import org.example.movierentalsjpa.common.IMovieService;
import org.example.movierentalsjpa.common.model.Movie;
import org.example.movierentalsjpa.common.model.exceptions.MovieRentalsException;
import org.example.movierentalsjpa.server.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SMovieServiceImpl implements IMovieService {
    private static final Logger log = LoggerFactory.getLogger(SMovieServiceImpl.class);

    @Autowired
    MovieRepository movieRepository;

    @Override
    public Iterable<Movie> getAllMovies() {
        log.trace("getAllMovies --- method entered");

        List<Movie> result = movieRepository.findAll();

        log.trace("getAllClients: result={}", result);

        return result;
    }

    @Override
    public void addMovie(Movie movie) {
        log.trace("addMovie: movie={}", movie);

        Movie result = movieRepository.save(movie);

        log.trace("addMovie: movie={}", result);
    }

    @Override
    public Movie getMovieById(Long id) {
        log.trace("getMovieById --- method entered");

        Movie result = movieRepository.findById(id).orElseThrow(() -> new MovieRentalsException("Movie not found with ID: " + id));

        log.trace("getMovieById: movie={}", result);

        return result;
    }

    @Override
    public void updateMovie(Movie movie) {
        log.trace("updateMovie --- method entered");
        if(!movieRepository.existsById(movie.getId())){
            log.error("movie with ID {} not found", movie.getId());
            throw new MovieRentalsException("Movie not found with ID " + movie.getId());
        }
        Movie result = movieRepository.save(movie);
        log.trace("updateMovie: movie{}", result);
    }

    @Override
    public void deleteMovieById(Long id) {
        log.trace("deleteMovieById --- method entered");
        if(!movieRepository.existsById(id)){
            log.error("movie with ID {} not found", id);
            throw new MovieRentalsException("Movie not found with ID " + id);
        }
        try {
            movieRepository.deleteById(id);
            log.trace("movie{} successfully deleted", id);
        } catch (DataAccessException e){
            throw new MovieRentalsException("Cannot delete Movie ID{" + id + "} because it appears in the Rental Reports!!!");
        }
    }

//    @Override
//    public Iterable<Movie> filterMoviesByKeyword(String keyword) {
//        log.trace("filterMoviesByKeyword --- method entered");
//        if (keyword == null) {
//            throw new IllegalArgumentException("Keyword must not be null. ");
//        }
//        Iterable<Movie> movies = movieRepository.findAll();
//        Set<Movie> result = StreamSupport.stream(movies.spliterator(), false)
//                .filter(movie -> movie.getTitle()
//                        .toLowerCase()
//                        .contains(keyword.toLowerCase()))
//                .collect(Collectors.toSet());
//        log.trace("filterMoviesByKeyword : result{}", result);
//        return result;
//    }

    @Override
    public Set<Movie> filterMoviesByKeyword(String title){
        log.trace("filterMoviesByKeyword --- method entered");
        List<Movie> resultList = movieRepository.findByTitle(title);
        Set<Movie> result = new HashSet<>(resultList);
        log.trace("filterMoviesByKeyword: result{}", result);
        return result;
    }

    @Override
    public Iterable<Movie> orderMoviesByPriceAsc() {
        log.trace("orderMoviesByPriceAsc --- method entered");
        Iterable<Movie> result = movieRepository.findByOrderByRentalPriceAsc();
        log.trace("orderMoviesByPriceAsc: result{}", result);
        return result;
    }

    @Override
    public Iterable<Movie> orderMoviesByPriceDesc() {
        log.trace("orderMoviesByPriceDesc --- method entered");
        Iterable<Movie> result = movieRepository.findByOrderByRentalPriceDesc();
        log.trace("orderMoviesByPriceDesc: result{}", result);
        return result;
    }
}
