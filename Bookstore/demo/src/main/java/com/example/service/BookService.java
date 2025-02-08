package com.example.demo.service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.generatedclasses.tables.pojos.Book;
import com.example.demo.repository.BookRepository;
@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository repository;
    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }
    public List<Book> getBooks(){
        return repository.getBooks();
    }
    public Book insert(String authorId, String title){
        return repository.insert(Integer.parseInt(authorId), title);
    }
}