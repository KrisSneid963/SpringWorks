package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    //get movies http://localhost:8080/api/movies
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    //http://localhost:8080/api/movies/search?id=7
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //http://localhost:8080/api/movies/search?title=Inception
    @GetMapping("/search")
    public ResponseEntity<?> searchMovies(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title) {

        //GET http://localhost:8080/api/movies/search?id=1
        if (id != null) {
            return movieService.getMovieById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        //  GET http://localhost:8080/api/movies/search?title=Batman
        if (title != null) {
            List<Movie> movies = movieService.searchMoviesByTitle(title);
            return movies.isEmpty()
                    ? ResponseEntity.notFound().build()
                    : ResponseEntity.ok(movies);
        }
//GET http://localhost:8080/api/movies/search?title=Supper
        return ResponseEntity.badRequest().body("Please provide id or title");
    }

    // POST http://localhost:8080/api/movies
    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isEmpty() ||
                movie.getDirector() == null || movie.getDirector().isEmpty()) {
            return ResponseEntity.badRequest().body("Title and director cannot be empty!");
        }

        //  Save
        Movie savedMovie = movieService.saveMovie(movie);

        //   http://localhost:8080/api/movies  /api/movies/10)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMovie.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedMovie); //save
    }
}
