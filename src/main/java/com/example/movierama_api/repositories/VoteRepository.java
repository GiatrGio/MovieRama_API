package com.example.movierama_api.repositories;

import com.example.movierama_api.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Transactional
    @Query("SELECT v FROM Vote v WHERE v.user.userId = :userId AND v.movie.movieId = :movieId")
    Vote findVoteByUserIdAndMovieId(Long userId, Long movieId);
    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.likes = m.likes + 1 WHERE m.movieId = :movieId")
    void incrementLikes(Long movieId);

    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.likes = m.likes - 1 WHERE m.movieId = :movieId")
    void decrementLikes(Long movieId);


    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.hates = m.hates + 1 WHERE m.movieId = :movieId")
    void incrementHates(Long movieId);

    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.hates = m.hates - 1 WHERE m.movieId = :movieId")
    void decrementHates(Long movieId);
}
