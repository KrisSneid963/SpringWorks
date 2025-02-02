package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    //return all movies
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.isEmpty() ? List.of() : movies;
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }


    public List<Movie> searchMoviesByTitle(String title) {
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(title);
        return movies.isEmpty() ? List.of() : movies;
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
