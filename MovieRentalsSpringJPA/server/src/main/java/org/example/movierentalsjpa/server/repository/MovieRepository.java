package org.example.movierentalsjpa.server.repository;

import org.example.movierentalsjpa.common.model.Movie;

import java.util.List;

public interface MovieRepository extends GenericRepository<Movie, Long>{
    List<Movie> findByTitle(String title);
    Iterable<Movie> findByOrderByRentalPriceAsc();
    Iterable<Movie> findByOrderByRentalPriceDesc();
}
