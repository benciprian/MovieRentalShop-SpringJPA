package org.example.movierentalsjpa.client.service;

import org.example.movierentalsjpa.common.*;
import org.example.movierentalsjpa.common.model.Movie;
import org.example.movierentalsjpa.common.model.exceptions.MovieRentalsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class CMovieServiceImpl implements IMovieService {


    private IMovieService movieService;

    @Override
    public Iterable<Movie> getAllMovies() throws MovieRentalsException {
        return movieService.getAllMovies();
    }

    @Override
    public void addMovie(Movie movie) throws MovieRentalsException {
        movieService.addMovie(movie);
    }

    @Override
    public Movie getMovieById(Long id) throws MovieRentalsException {
        return movieService.getMovieById(id);
    }

    @Override
    public void updateMovie(Movie movie) throws MovieRentalsException {
        movieService.updateMovie(movie);
    }

    @Override
    public void deleteMovieById(Long id) throws MovieRentalsException {
        movieService.deleteMovieById(id);
    }

    @Override
    public Iterable<Movie> filterMoviesByKeyword(String keyword) throws MovieRentalsException {
        return movieService.filterMoviesByKeyword(keyword);
    }

    @Override
    public Iterable<Movie> orderMoviesByPriceAsc() throws MovieRentalsException {
        return movieService.orderMoviesByPriceAsc();
    }

    @Override
    public Iterable<Movie> orderMoviesByPriceDesc() {
        return movieService.orderMoviesByPriceDesc();
    }
}