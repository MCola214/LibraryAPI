package com.example.demo.service;

import com.example.demo.dao.LibraryDao;
import com.example.demo.model.Book;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibraryService {
	private final LibraryDao libraryDao;

	@Autowired
	public LibraryService(@Qualifier("MongoDBDao") LibraryDao libraryDao) {
		this.libraryDao = libraryDao;
	}

	public List<Book> getAllBooks() {
		return libraryDao.selectAllBooks();
	}

	public Book getBookById(UUID id) {
		Optional<Book> book = libraryDao.selectBookById(id);
		return book.orElse(null);
	}

	public int addBook(Book book) {
		return libraryDao.insertBook(book);
	}

	public List<User> getAllUsers() {
		return libraryDao.selectAllUsers();
	}

	public User getUserById(UUID id) {
		Optional<User> user = libraryDao.selectUserById(id);
		return user.orElse(null);
	}

	public int addUser(User user) {
		return libraryDao.insertUser(user);
	}

	public int checkoutBookById(UUID bookId, UUID userId) {
		return libraryDao.checkoutBookById(bookId, userId);
	}

	public int returnBookById(UUID bookId, UUID userId) {
		return libraryDao.returnBookById(bookId, userId);
	}

	public int deleteBookById(UUID bookId) {
		return libraryDao.deleteBookById(bookId);
	}

	public int deleteUserById(UUID userId) {
		return libraryDao.deleteUserById(userId);
	}
}
