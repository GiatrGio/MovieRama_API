package com.example.movierama_api.controllers;

import com.example.movierama_api.dto.CreateMovieDTO;
import com.example.movierama_api.dto.MovieDTO;
import com.example.movierama_api.models.Movie;
import com.example.movierama_api.models.User;
import com.example.movierama_api.services.MovieService;
import com.example.movierama_api.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final UserService userService;

    public MovieController(MovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping
    public List<MovieDTO> getAllMovies() {

        List<Movie> movieList = movieService.getAllMovies();

        return mapMovieListToMovieDTOList(movieList);
    }

    @GetMapping("/{userId}")
    public List<Movie> getMoviesByUserId(@PathVariable Long userId) {

        return movieService.getAllMoviesByUser(userId);
    }

    @PostMapping
    public ResponseEntity<String> createMovie(@RequestBody CreateMovieDTO createMovieDTO)
    {
        User user = userService.getUserByUserId(createMovieDTO.getUserId());

        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User doesn't exist");
        }

        Movie movie = new Movie(createMovieDTO.getTitle(), createMovieDTO.getDescription(), user);
        Movie newMovieObject = movieService.createMovie(movie);

        if (Objects.nonNull(newMovieObject)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New movie created successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while creating the new movie.");
        }
    }

    private static List<MovieDTO> mapMovieListToMovieDTOList(List<Movie> movieList) {
        return movieList.stream().map(movie -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setMovieId(movie.getMovieId());
            movieDTO.setTitle(movie.getTitle());
            movieDTO.setDescription(movie.getDescription());
            movieDTO.setDateAdded(movie.getDateAdded());
            movieDTO.setLikes(movie.getLikes());
            movieDTO.setHates(movie.getHates());
            movieDTO.setUsername(movie.getUser().getUsername());
            movieDTO.setUserId(movie.getUser().getUserId());
            return movieDTO;
        }).collect(Collectors.toList());
    }
}
