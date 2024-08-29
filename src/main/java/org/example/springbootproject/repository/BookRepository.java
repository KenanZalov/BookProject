package org.example.springbootproject.repository;

import org.example.springbootproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByisbn(int isbn);

    List<Book> findBookByAuthor_FullName(String fullName);

}