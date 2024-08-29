package org.example.springbootproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootproject.dto.AuthorDto;
import org.example.springbootproject.dto.BookDto;
import org.example.springbootproject.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/createAuthor")
    public AuthorDto createAuthor(@RequestBody AuthorDto authorDto){
        return authorService.createAuthor(authorDto);
    }

    @GetMapping("getAllAuthors")
    public List<AuthorDto> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping("getAuthorById")
    public AuthorDto getAuthorById(@RequestParam Long id){
        return authorService.getAuthorById(id);
    }

    @DeleteMapping("deleteAuthorById")
    public AuthorDto deleteAuthorById(@RequestParam Long id){
        return authorService.deleteAuthorById(id);
    }

    @PutMapping("updateAuthor")
    public AuthorDto updateAuthor(Long id, AuthorDto authorDto){
        return authorService.updateAuthor(id, authorDto);
    }




}
