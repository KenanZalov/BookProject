package org.example.springbootproject.testService;

import org.example.springbootproject.dto.AuthorDto;
import org.example.springbootproject.dto.BookDto;
import org.example.springbootproject.model.Author;
import org.example.springbootproject.model.Book;
import org.example.springbootproject.repository.AuthorRepository;
import org.example.springbootproject.repository.BookRepository;
import org.example.springbootproject.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    AuthorRepository authorRepository;

    private Author author1;
    private Author author2;
    private Author author;
    private AuthorDto authorDto;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book;
    private BookDto bookDto;
    private BookDto bookDto1;
    private BookDto bookDto2;

    @BeforeEach
    void init() {
        author1 = new Author(1L,"Inal", new HashSet<>());
        author2 = new Author(2L,"Sevinc",new HashSet<>());
        authorDto = new AuthorDto("Inal");
        book1 = new Book(1L,"Java",10,1,author1);
        book2 = new Book(2L,"Phyton",15,2,author2);
        book3 = new Book();
        bookDto1 = new BookDto("Java",authorDto,1,10);
        bookDto2 = new BookDto();
        bookDto2.setTitle("Java");
        bookDto2.setAuthor(authorDto);
    }

    @Test
    public void createBook_returnBookDto() {
        var spy = Mockito.spy(bookService);
        when(spy.getOrAddAuthor(bookDto2)).thenReturn(new Author());
        when(bookRepository.findByisbn(bookDto2.getIsbn())).thenReturn(null);
        when(modelMapper.map(bookDto2, Book.class)).thenReturn(book3);
        book3.setAuthor(author1);
        when(bookRepository.save(book3)).thenReturn(book1);
        when(modelMapper.map(book1, BookDto.class)).thenReturn(bookDto1);
        BookDto book = spy.createBook(bookDto2);
        Assertions.assertNotNull(book);
        Assertions.assertEquals(bookDto1, book);

    }

    @Test
    public void getAllBooks_returnBookDto() {
        List<Book> books = new ArrayList<>(List.of(book1,book2,book3));
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDto> bookDtos = bookService.getAllBooks();
        Assertions.assertNotNull(bookDtos);
        Assertions.assertEquals(bookDtos.size(), books.size());

    }

    @Test
    public void getBookById_returnBookDto() {
        when(bookRepository.findById(book1.getId())).thenReturn(Optional.ofNullable(book1));
        when(modelMapper.map(book1, BookDto.class)).thenReturn(bookDto1);
        BookDto bookDto = bookService.getBookById(book1.getId());
        Assertions.assertNotNull(bookDto);
        Assertions.assertEquals(bookDto1, bookDto);
    }

    @Test
    public void deleteBook_returnBookDto() {
        when(bookRepository.findById(book2.getId())).thenReturn(Optional.ofNullable(book2));
        when(modelMapper.map(book2, BookDto.class)).thenReturn(bookDto2);
        bookService.deleteBook(book2.getId());
        Assertions.assertNotNull(bookDto2);
        Assertions.assertEquals(bookDto2, bookDto2);
    }

    @Test
    public void getBooksByAuthor_returnBookDto() {
        List<Book> books = new ArrayList<>(List.of(book1,book2,book3));
        when(bookRepository.findBookByAuthor_FullName("Inal")).thenReturn(books);
        List<BookDto> bookDtos = bookService.getBooksByAuthor("Inal");
        Assertions.assertNotNull(bookDtos);
        Assertions.assertEquals(bookDtos.size(), books.size());
    }

    @Test
    public void updateBookTest(){
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        BookDto bookDto4 = bookService.updateBook(1L, bookDto1);
        Assertions.assertEquals(bookDto4,new BookDto());
    }













}
