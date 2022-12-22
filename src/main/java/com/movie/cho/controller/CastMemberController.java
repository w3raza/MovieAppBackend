package com.movie.cho.controller;

import com.movie.cho.model.CastMember;
import com.movie.cho.repository.CastMemberRepository;
import com.movie.cho.service.CastMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/cast")
public class CastMemberController {
    private final CastMemberRepository castMemberRepository;
    private final CastMemberService castMemberService;

    @GetMapping("/")
    List<CastMember> getCastMembers() {
        return castMemberRepository.findAll();
    }

    @GetMapping("/{name}")
    List<String> allByName(@PathVariable String name){
        return castMemberRepository.findSearchResults(name);
    }

    @GetMapping("/by-name/{name}")
    CastMember getCastMemberByName(@PathVariable String name) {
        return castMemberRepository.findById(name).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CastMember with name: " + name + "not found"));
    }

    @GetMapping("/director/{movieTitle}")
    List<CastMember> getDirectors(@PathVariable String movieTitle) {
        return castMemberRepository.findAllDirectorsByTitle(movieTitle);
    }

    @GetMapping("/actor/{movieTitle}")
    List<CastMember> getActors(@PathVariable String movieTitle) {
        return castMemberRepository.findAllActorsByTitle(movieTitle);
    }

    @PutMapping("/director/{movieTitle}")
    CastMember createDirector(@RequestBody CastMember newCastMember, @PathVariable String movieTitle) {
        return castMemberService.createDirectorAndAddRelations(newCastMember, movieTitle);
    }

    @PutMapping("/actor/{movieTitle}")
    CastMember createActor(@RequestBody CastMember newCastMember, @PathVariable String movieTitle) {
        return castMemberService.createActorAndAddRelations(newCastMember, movieTitle);
    }

    @PutMapping("/update/{name}")
    void updateCastMember(@PathVariable String name, @RequestBody Map<String,Object> updatedMovie) {
        castMemberRepository.updateCastMember(name, updatedMovie);
    }

    @DeleteMapping("/delete/{name}")
    ResponseEntity delete(@PathVariable String name) {
        try {
            castMemberRepository.deleteById(name);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
