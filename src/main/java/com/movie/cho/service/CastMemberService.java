package com.movie.cho.service;

import com.movie.cho.model.CastMember;
import com.movie.cho.repository.CastMemberRepository;
import com.movie.cho.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CastMemberService {
    private final MovieRepository movieRepository;
    private final CastMemberRepository castMemberRepository;

    public CastMember createDirectorAndAddRelations(CastMember newCastMember, String movieTitle){
        CastMember castMember = castMemberRepository.save(newCastMember);
        movieRepository.addDirectorToMovie(movieTitle, castMember.getName());

        return castMember;
    }

    public CastMember createActorAndAddRelations(CastMember newCastMember, String movieTitle){
        CastMember castMember = castMemberRepository.save(newCastMember);
        movieRepository.addActorToMovie(movieTitle, castMember.getName());

        return castMember;
    }
}
