package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class BookCheckout {

	@JsonProperty("bookId")
	private final UUID bookId;

	@JsonProperty("userId")
	private final UUID userId;

	public BookCheckout(UUID bookId, UUID userId) {
		this.bookId = bookId;
		this.userId = userId;
	}

	public UUID getBookId() {
		return this.bookId;
	}

	public UUID getUserId() {
		return this.userId;
	}
}
