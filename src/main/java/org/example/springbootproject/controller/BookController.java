package org.example.springbootproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootproject.dto.BookDto;
import org.example.springbootproject.model.Author;
import org.example.springbootproject.model.Book;
import org.example.springbootproject.service.AuthorService;
import org.example.springbootproject.service.BookService;
import org.example.springbootproject.service.ScheduledService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")

public class BookController {
    private final BookService bookService;
    private final ScheduledService scheduledService;

    @PostMapping("/create")
    public BookDto createBook(@RequestBody BookDto bookDto){
        return bookService.createBook(bookDto);
    }

    @GetMapping("getBooksByAuthor")
    public List<BookDto> getBooksByAuthor(@RequestParam String author){
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("getBookById")
    public BookDto getBookById(Long id){
        return bookService.getBookById(id);
    }

    @GetMapping("getAllBooks")
    public List<BookDto> getAllBooks(){
        return bookService.getAllBooks();
    }

    @DeleteMapping("deleteBook")
    public BookDto deleteBook(@RequestParam Long id){
        return bookService.deleteBook(id);
    }

    @PutMapping("updateBook")
    public BookDto updateBook(Long id, BookDto bookDto){
        return bookService.updateBook(id, bookDto);
    }

    @GetMapping("sendBook")
    public BookDto sendBook(){
        return scheduledService.sendBook();
    }





}
