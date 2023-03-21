package com.hanghae.hanghaebnb.book.service;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hanghae.hanghaebnb.book.dto.RequestBook;
import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.book.facade.LettuceRockBookFacade;
import com.hanghae.hanghaebnb.book.mapper.BookMapper;
import com.hanghae.hanghaebnb.book.repository.BookRepository;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import com.hanghae.hanghaebnb.users.entity.Users;
import com.hanghae.hanghaebnb.users.entity.UsersRoleEnum;
import com.hanghae.hanghaebnb.users.mapper.UsersMapper;
import com.hanghae.hanghaebnb.users.repository.UserRepository;

@SpringBootTest
@DisplayName("Lettuce Lock 테스트")
public class BookServiceTest {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private LettuceRockBookFacade lettuceRockBookFacade;


	@Test
	public void bookAtSameTime() throws InterruptedException {

		Users user = usersMapper.toUsers("host@naver.com", "dPwls12!", "철수", UsersRoleEnum.USER);
		userRepository.save(user);

		Room room = new Room("테스트룸", "테스트테스트", 100000L, 100000L, "test", 0, 0, "이미지", 0, user);
		roomRepository.save(room);

		RequestBook requestBook = new RequestBook(1L, LocalDate.parse("2023-02-01"), LocalDate.parse("2023-02-03"), 2L, 200000L);

		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);

		CountDownLatch latch = new CountDownLatch(threadCount);


		for(int i=0; i<threadCount; i++){
			int finalI = i;
			executorService.submit(() -> {
				try {
					lettuceRockBookFacade.addBook(1L, requestBook, user);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();


		System.out.println(bookRepository.count());

	}

	@Test
	public void bookDeleteAtSameTime() throws InterruptedException {

		Users user = usersMapper.toUsers("host@naver.com", "dPwls12!", "철수", UsersRoleEnum.USER);
		userRepository.save(user);

		Room room = new Room("테스트룸", "테스트테스트", 100000L, 100000L, "test", 0, 0, "이미지", 0, user);
		roomRepository.save(room);

		RequestBook requestBook = new RequestBook(1L, LocalDate.parse("2023-02-01"), LocalDate.parse("2023-02-03"), 2L, 200000L);

		Book book = new Book(51L, 2L, 200000L, LocalDate.parse("2023-02-01"), LocalDate.parse("2023-02-03"), room, user);
		bookRepository.save(book);

		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);

		CountDownLatch latch = new CountDownLatch(threadCount);


		for(int i=0; i<threadCount; i++){
			int finalI = i;
			executorService.submit(() -> {
				try {
					lettuceRockBookFacade.deleteBook(book.getBookId(), user);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		System.out.println(bookRepository.count());

	}

}
