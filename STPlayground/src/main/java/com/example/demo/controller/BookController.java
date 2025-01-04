package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.generatedclasses.tables.pojos.Book;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService){
		this.bookService = bookService;
	}

    @GetMapping
	public List<Book> getBooks() {
		return bookService.getBooks();
	}

    // Use Validated to ensure authorId is a int, currently use Integer.parseInt within the service
	@PostMapping
	public Book insert(
		@RequestBody Map<String, String> body) {
		return bookService.insert(body.get("authorId"), body.get("title"));
	}

}
