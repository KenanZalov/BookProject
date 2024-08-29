package org.example.springbootproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootproject.dto.BookDto;
import org.example.springbootproject.model.Book;
import org.example.springbootproject.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    private Book recBook;


    @Scheduled(fixedRate = 5000)
    public void recommendationBook() {
        List<Book> books = bookRepository.findAll();
        if (!books.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(0, books.size());
            recBook = books.get(index);
            log.info("Recomendation book : {}" , recBook);
        } else {
            recBook = null;
        }

    }

   public BookDto sendBook() {
        return modelMapper.map(recBook, BookDto.class);
   }



}
