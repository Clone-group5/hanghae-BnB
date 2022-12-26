package com.hanghae.hanghaebnb.book.repository;

import com.hanghae.hanghaebnb.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
