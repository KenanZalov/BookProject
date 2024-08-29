package org.example.springbootproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootproject.dto.BookDto;
import org.example.springbootproject.model.Author;
import org.example.springbootproject.model.Book;
import org.example.springbootproject.repository.AuthorRepository;
import org.example.springbootproject.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service

public class BookService {
    private final AuthorRepository authorRepo;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookDto createBook(BookDto bookDto) {
        Author author = getOrAddAuthor(bookDto);
        Book book = bookRepository.findByisbn(bookDto.getIsbn());
        if (book == null) {
            book = modelMapper.map(bookDto, Book.class);
            book.setAuthor(author);
            book = bookRepository.save(book);
            log.info("Book created: {}", book);
        }
        return modelMapper.map(book, BookDto.class);
    }

    public Author getOrAddAuthor(BookDto bookDto) {
        String name = bookDto.getAuthor().getFullName();
        Author author = authorRepo.findByFullName(name);
        if (author == null) {
            author = modelMapper.map(bookDto.getAuthor(), Author.class);
            author = authorRepo.save(author);
        }
        return author;
    }

    public List<BookDto> getAllBooks() {
        List<BookDto> bookDtos = new ArrayList<>();
        bookRepository.findAll().forEach(book -> {
            bookDtos.add(modelMapper.map(book, BookDto.class));
            log.info("Book found: {}", book);
        });
        return bookDtos;
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        return modelMapper.map(book, BookDto.class);
    }

    public List<BookDto> getBooksByAuthor(String author) {
        List<Book> books = bookRepository.findBookByAuthor_FullName(author);
        List<BookDto> bookDtos = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            bookDtos.add(modelMapper.map(books.get(i), BookDto.class));
        }
        return bookDtos;
    }


    public BookDto deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        bookRepository.delete(book);
        return modelMapper.map(book, BookDto.class);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Author author = getOrAddAuthor(bookDto);
            Book mapped = modelMapper.map(bookDto, Book.class);
            mapped.setId(id);
            mapped.setAuthor(author);
            log.info("Update book {} {}",id,bookDto);
            return modelMapper.map(bookRepository.save(mapped), BookDto.class);
        } else {
            log.info("Not found");
            return new BookDto();
        }


    }


}
