package com.example.movierama_api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    public Vote() {

    }

    public Vote(User user, Movie movie, VoteType voteType) {
        this.user = user;
        this.movie = movie;
        this.voteType = voteType;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public enum VoteType {
        LIKE,
        HATE
    }

    public boolean hasSameUserIdMovieIdAndVoteType(Vote otherVote) {
        if (otherVote == null) {
            return false;
        }

        return this.user.getUserId().equals(otherVote.getUser().getUserId())
                && this.movie.getMovieId().equals(otherVote.getMovie().getMovieId())
                && this.getVoteType() == otherVote.getVoteType();
    }
}


