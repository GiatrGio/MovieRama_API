package com.example.movierama_api.services;

import com.example.movierama_api.models.Movie;
import com.example.movierama_api.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllMovies() {
        List<Movie> mockMovies = new ArrayList<>();
        mockMovies.add(new Movie(1L, "Movie 1", "Description 1", 1L));
        mockMovies.add(new Movie(2L, "Movie 2", "Description 2", 1L));
        when(movieRepository.findAll()).thenReturn(mockMovies);

        List<Movie> result = movieService.getAllMovies();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
     void testGetAllMoviesByUser() {
        Long userId = 1L;
        List<Movie> mockMovies = new ArrayList<>();
        mockMovies.add(new Movie(1L, "Movie 1", "Description 1", userId));
        mockMovies.add(new Movie(2L, "Movie 2", "Description 2", userId));
        when(movieRepository.findAllByUserId(userId)).thenReturn(mockMovies);

        List<Movie> result = movieService.getAllMoviesByUser(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testCreateMovie() {
        Movie newMovie = new Movie(null, "New Movie", "New Description", 1L);

        when(movieRepository.save(newMovie)).thenReturn(
                new Movie(1L, "New Movie", "New Description", 1L));

        Movie result = movieService.createMovie(newMovie);

        assertNotNull(result);
        assertEquals("New Movie", result.getTitle());
        assertEquals("New Description", result.getDescription());
    }
}
