package com.example.demo.api;

import com.example.demo.model.Book;
import com.example.demo.model.BookCheckout;
import com.example.demo.model.User;
import com.example.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/")
@RestController
public class LibraryController {

	private final LibraryService libraryService;

	@Autowired
	public LibraryController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	@GetMapping
	public List<Book> getAllBooks() {
		return libraryService.getAllBooks();
	}

	@GetMapping(path = "/book/{id}")
	public Book getBookById(@PathVariable("id") UUID id) {
		return libraryService.getBookById(id);
	}

	@GetMapping(path = "/getAllUsers")
	public List<User> getAllUsers() {
		return libraryService.getAllUsers();
	}

	@GetMapping(path = "/user/{id}")
	public User getUserById(@PathVariable("id") UUID id) {
		return libraryService.getUserById(id);
	}

	@PostMapping
	public void addBook(@Valid @NonNull @RequestBody Book book) {
		libraryService.addBook(book);
	}

	@PostMapping(path = "addUser")
	public void addUser(@Valid @NonNull @RequestBody User user) {
		libraryService.addUser(user);
	}

	@PatchMapping(path = "/checkoutBook")
	public void checkoutBookById(@Valid @NonNull @RequestBody BookCheckout body) {
		libraryService.checkoutBookById(body.getBookId(), body.getUserId());
	}

	@PatchMapping(path = "/returnBook")
	public void returnBookById(@Valid @NonNull @RequestBody BookCheckout body) {
		libraryService.returnBookById(body.getBookId(), body.getUserId());
	}

	@DeleteMapping(path = "/deleteBook/{id}")
	public void deleteBookById(@PathVariable("id") UUID id) {
		libraryService.deleteBookById(id);
	}

	@DeleteMapping(path = "/deleteUser/{id}")
	public void deleteUserById(@PathVariable("id") UUID id) {
		libraryService.deleteUserById(id);
	}
}
