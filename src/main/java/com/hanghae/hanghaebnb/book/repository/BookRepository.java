package com.hanghae.hanghaebnb.book.repository;

import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByUsers(Users users);


    @Query(value = "select room_id from book where  check_in <= :checkOut and check_in >= :checkIn", nativeQuery = true)
    Long findByCheckIn(
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut
    );

    @Query(value = "select room_id from book where  book.check_out <= :checkOut and book.check_out >= :checkIn", nativeQuery = true)
    Long findByCheckOut(
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut
    );



}
