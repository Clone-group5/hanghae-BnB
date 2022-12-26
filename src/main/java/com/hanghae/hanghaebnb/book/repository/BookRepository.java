package com.hanghae.hanghaebnb.book.repository;

import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByUsers(Users users);
}
