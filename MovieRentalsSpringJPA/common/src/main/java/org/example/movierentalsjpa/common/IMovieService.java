package org.example.movierentalsjpa.common;

import org.example.movierentalsjpa.common.model.Movie;


public interface IMovieService {

    Iterable<Movie> getAllMovies();

    void addMovie(Movie movie);

    Movie getMovieById(Long id);

    void updateMovie(Movie movie);

    void deleteMovieById(Long id);

    Iterable<Movie> filterMoviesByKeyword(String keyword);

    Iterable<Movie> orderMoviesByPriceAsc();

    Iterable<Movie> orderMoviesByPriceDesc();

}
