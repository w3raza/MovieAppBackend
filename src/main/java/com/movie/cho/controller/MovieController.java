package com.movie.cho.controller;

import com.movie.cho.model.Movie;
import com.movie.cho.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepository;

    @GetMapping("/")
    List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/{title}")
    List<Movie> allMoviesByTitle(@PathVariable String title){
        return movieRepository.findSearchResults(title);
    }

    @GetMapping("/by-title/{title}")
    Movie getMovieByTitle(@PathVariable String title) {
        return movieRepository.findById(title).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with title: " + title + "not found"));
    }

    @PutMapping("/create")
    ResponseEntity<?> createMovie(@RequestBody Movie newMovie) {
        try {
            movieRepository.save(newMovie);
            return ResponseEntity.ok("Create movie successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(newMovie);
        }
    }

    @PutMapping("/update/{title}")
    Movie updateMovie(@PathVariable String title, @RequestBody Map<String,Object> updatedMovie) {
        return movieRepository.updateMovie(title, updatedMovie);
    }

    @DeleteMapping("/delete/{title}")
    ResponseEntity<?> delete(@PathVariable String title) {
        try {
            movieRepository.deleteById(title);
            return ResponseEntity.ok("Delete movie successfully");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
