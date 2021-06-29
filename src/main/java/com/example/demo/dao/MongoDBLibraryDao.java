package com.example.demo.dao;

import com.example.demo.model.Book;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("MongoDBDao")
public class MongoDBLibraryDao implements LibraryDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Book> selectAllBooks() {
		return mongoTemplate.findAll(Book.class);
	}

	@Override
	public Optional<Book> selectBookById(UUID id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Book book = mongoTemplate.findOne(query, Book.class);

		if (book != null) {
			return Optional.of(book);
		}

		return Optional.empty();
	}

	@Override
	public int insertBook(UUID id, Book book) {
		mongoTemplate.save(new Book(id, book.getTitle(), book.getAuthor(), book.isCheckedOut()));
		return 1;
	}

	@Override
	public List<User> selectAllUsers() {
		return mongoTemplate.findAll(User.class);
	}

	@Override
	public Optional<User> selectUserById(UUID id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		User user = mongoTemplate.findOne(query, User.class);

		if (user != null) {
			return Optional.of(user);
		}

		return Optional.empty();
	}

	@Override
	public int insertUser(UUID id, User user) {
		mongoTemplate.save(new User(id, user.getUsername(), user.getBooksCheckedOut()));
		return 1;
	}

	@Override
	public int checkoutBookById(UUID bookId, UUID userId) {
		Optional<Book> bookOptional = this.selectBookById(bookId);
		Optional<User> userOptional = this.selectUserById(userId);

		if (bookOptional.isPresent() && userOptional.isPresent()) {
			Book book = bookOptional.get();
			User user = userOptional.get();

			if (!book.isCheckedOut()) {
				book.checkOut();
				user.addBook(book);

				mongoTemplate.save(book);
				mongoTemplate.save(user);
				return 1;
			}
		}
		return 0;
	}

	@Override
	public int returnBookById(UUID bookId, UUID userId) {
		Optional<Book> bookOptional = this.selectBookById(bookId);
		Optional<User> userOptional = this.selectUserById(userId);

		if (bookOptional.isPresent() && userOptional.isPresent()) {
			Book book = bookOptional.get();
			User user = userOptional.get();

			if (book.isCheckedOut() && user.hasCheckedOut(bookId)) {
				book.returnBook();
				user.removeBook(book);

				mongoTemplate.save(book);
				mongoTemplate.save(user);
				return 1;
			}
		}
		return 0;
	}

	@Override
	public int deleteBookById(UUID bookId) {
		Optional<Book> bookOptional = this.selectBookById(bookId);
		if (bookOptional.isPresent()) {
			Book book = bookOptional.get();

			if (book.isCheckedOut()) {
				// remove book from user checked out list
				List<User> users = this.selectAllUsers();
				for (User user : users) {
					if (user.hasCheckedOut(book.getId())) {
						user.removeBook(book);
						mongoTemplate.save(user);
						break;
					}
				}
			}

			// remove book from db
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(bookId));
			mongoTemplate.remove(query, Book.class);
			return 1;
		}
		return 0;
	}

	@Override
	public int deleteUserById(UUID userId) {
		Optional<User> userOptional = this.selectUserById(userId);
		if (userOptional.isPresent()) {
			User user = userOptional.get();

			// return books in checked out list and save
			for (Book book : user.getBooksCheckedOut()) {
				book.returnBook();
				mongoTemplate.save(book);
			}

			// remove user from db
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(userId));
			mongoTemplate.remove(query, User.class);
			return 1;
		}
		return 0;
	}
}
