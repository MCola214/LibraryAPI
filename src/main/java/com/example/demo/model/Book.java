package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Book {

	@Id
	@JsonProperty("id")
	private final UUID id;

	@JsonProperty("title")
	private final String title;

	@JsonProperty("author")
	private final String author;

	@JsonProperty("checkedOut")
	private boolean checkedOut;

	public Book(UUID id, String title, String author, boolean checkedOut) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.checkedOut = checkedOut;
	}

	public void checkOut() {
		this.checkedOut = true;
	}

	public void returnBook() {
		this.checkedOut = false;
	}

	public UUID getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public boolean isCheckedOut() {
		return checkedOut;
	}

	@Override
	public String toString() {
		return "Title: " + this.getTitle() + "\nAuthor: " + this.getAuthor() + "\nChecked out: " + this.checkedOut;
	}
}
