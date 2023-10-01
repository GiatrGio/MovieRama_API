package com.example.movierama_api.repositories;

import com.example.movierama_api.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT movie FROM Movie movie WHERE movie.user.userId = :userId")
    List<Movie> findAllByUserId(@Param("userId") Long userId);

}
