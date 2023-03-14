package com.hanghae.hanghaebnb.book.facade;

import java.text.ParseException;

import org.springframework.stereotype.Component;

import com.hanghae.hanghaebnb.book.dto.RequestBook;
import com.hanghae.hanghaebnb.book.repository.RedisRepository;
import com.hanghae.hanghaebnb.book.service.BookService;
import com.hanghae.hanghaebnb.users.entity.Users;

@Component
public class LettuceRockBookFacade {

	private RedisRepository redisRepository;

	private BookService bookService;

	public LettuceRockBookFacade(RedisRepository redisRepository, BookService bookService){
		this.redisRepository = redisRepository;
		this.bookService = bookService;
	}

	public void addBook(Long key, RequestBook requestBook, Users usersReceive) throws InterruptedException{
		while(!redisRepository.lock(key)){
			Thread.sleep(100);
		}

		try{
			bookService.addBook(key, requestBook, usersReceive);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		} finally {
			redisRepository.unlock(key);
		}
	}

}
