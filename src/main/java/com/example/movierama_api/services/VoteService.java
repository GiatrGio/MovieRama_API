package com.example.movierama_api.services;

import com.example.movierama_api.models.Movie;
import com.example.movierama_api.models.Vote;
import com.example.movierama_api.models.Vote.VoteType;
import com.example.movierama_api.repositories.MovieRepository;
import com.example.movierama_api.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final MovieRepository movieRepository;

    public VoteService(VoteRepository voteRepository, MovieRepository movieRepository) {
        this.voteRepository = voteRepository;
        this.movieRepository = movieRepository;
    }

    public void addVoteToMovie(Vote vote) {

        Vote savedVote = voteRepository.save(vote);

        incrementLikesHatesOfTheAssociatedMovie(savedVote);

    }

    private void incrementLikesHatesOfTheAssociatedMovie(Vote savedVote) {
        Movie movie = savedVote.getMovie();
        if (savedVote.getVoteType() == VoteType.LIKE) {
            voteRepository.incrementLikes(movie.getMovieId());
        } else {
            voteRepository.incrementHates(movie.getMovieId());
        }
    }

    public void updateVote(Vote newVote) {
        Vote existingVote = voteRepository.findVoteByUserIdAndMovieId(
                newVote.getUser().getUserId(), newVote.getMovie().getMovieId());

        if (Objects.nonNull(existingVote)) {
            if (existingVote.hasSameUserIdMovieIdAndVoteType(newVote)) {
                return;
            }

            updateLikesHatesOfTheAssociatedMovie(newVote, existingVote);
            updatedVoteTypeOfExistingVote(newVote, existingVote);
        }
        else {
            addVoteToMovie(newVote);
        }
    }

    private void updateLikesHatesOfTheAssociatedMovie(Vote newVote, Vote existingVote) {
        Movie movie = existingVote.getMovie();

        if (newVote.getVoteType() == VoteType.LIKE) {
            if (existingVote.getVoteType() == VoteType.HATE) {
                voteRepository.incrementLikes(movie.getMovieId());
                voteRepository.decrementHates(movie.getMovieId());
            }
        } else {
            if (existingVote.getVoteType() == VoteType.LIKE) {
                voteRepository.decrementLikes(movie.getMovieId());
                voteRepository.incrementHates(movie.getMovieId());
            }
        }
    }

    private void updatedVoteTypeOfExistingVote(Vote newVote, Vote existingVote) {
        existingVote.setVoteType(newVote.getVoteType());
        voteRepository.save(existingVote);
    }

    public void deleteVote(Long voteId) {
        voteRepository.deleteById(voteId);
    }

    public boolean userVotesOwnMovie(Vote vote) {
        Movie movie = movieRepository.findById(vote.getMovie().getMovieId()).get();

        return vote.getUser().getUserId().equals(movie.getUser().getUserId());
    }

}
