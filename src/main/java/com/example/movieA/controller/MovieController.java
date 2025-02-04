package com.example.movieA.controller;

import com.example.movieA.model.Movie;
import com.example.movieA.model.Actor;
import com.example.movieA.repository.ActorRepository;
import com.example.movieA.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final ActorRepository actorRepository;

    @Autowired
    public MovieController(MovieService movieService, ActorRepository actorRepository) {
        this.movieService = movieService;
        this.actorRepository = actorRepository;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    // id with actors and screenings
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);
        return movieOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    //   movie with actors by one
    @GetMapping("/{id}/actors")
    public ResponseEntity<List<Actor>> getActorsByMovieId(@PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);
        return movieOptional.map(movie -> ResponseEntity.ok((List<Actor>) movie.getActors())).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/{id}/actors")
    public ResponseEntity<Movie> addActorsToMovie(@PathVariable Long id, @RequestBody List<Long> actorIds) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);
        if (movieOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Movie movie = movieOptional.get();
        List<Actor> actors = actorRepository.findAllById(actorIds);
        movie.getActors().addAll(actors);
        movieService.saveMovie(movie);

        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (movieService.deleteMovie(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
