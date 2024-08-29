package org.example.springbootproject.testService;
import org.example.springbootproject.dto.AuthorDto;
import org.example.springbootproject.model.Author;
import org.example.springbootproject.model.Book;
import org.example.springbootproject.repository.AuthorRepository;
import org.example.springbootproject.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @InjectMocks
    AuthorService authorService;

    @Mock
    AuthorRepository authorRepository;
    @Mock
    ModelMapper modelMapper;

    private Author author;
    private AuthorDto authorDto;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        author = new Author(1L,"inal", null);
        authorDto = new AuthorDto("inal");
        book1 = new Book(1L,"java",15,1,author);
        book2 = new Book(1L,"phyton",20,2,author);
        author.setBooks(Set.of(book1,book2));
    }


    @Test
    public void createAuthor_returnAuthorDto(){
        when( modelMapper.map(authorDto, Author.class) ).thenReturn(author);
        when(authorRepository.save(author) ).thenReturn(author);
        when(modelMapper.map(author, AuthorDto.class) ).thenReturn(authorDto);
        
        Assertions.assertNotNull(authorDto);
        Assertions.assertEquals(authorDto, authorService.createAuthor(authorDto));

    }

    @Test
    public void getAllAuthors_returnListAuthorDto(){
        List<Author> authors = List.of(author);
        when(authorRepository.findAll()).thenReturn(authors);
        List<AuthorDto> authorDtos = authorService.getAllAuthors();

        Assertions.assertNotNull(authorDtos);
        Assertions.assertEquals(authorDtos.size(), authors.size());
    }

    @Test
    public void getAuthorById_returnAuthorDto(){
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.ofNullable(author));
        when(modelMapper.map(author, AuthorDto.class) ).thenReturn(authorDto);

        Assertions.assertNotNull(authorDto);
        Assertions.assertEquals(authorDto, authorService.getAuthorById(authorId));

    }

    @Test
    public void deleteAuthorById_returnAuthorDto(){
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.ofNullable(author));
        when(modelMapper.map(author, AuthorDto.class) ).thenReturn(authorDto);

        Assertions.assertNotNull(author);
        Assertions.assertEquals(authorDto, authorService.deleteAuthorById(authorId));
    }

    @Test
    public void updateAuthorIfNullTest(){
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        AuthorDto serviceAuthorDto = authorService.updateAuthor(1L, authorDto);
        Assertions.assertEquals(serviceAuthorDto,new AuthorDto());
    }










}
