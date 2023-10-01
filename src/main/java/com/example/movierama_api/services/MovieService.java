package com.example.movierama_api.services;

import com.example.movierama_api.models.Movie;
import com.example.movierama_api.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getAllMoviesByUser(Long userId) {
        return movieRepository.findAllByUserId(userId);
    }

    public Movie createMovie(Movie newMovie) {
        return movieRepository.save(newMovie);
    }

}
