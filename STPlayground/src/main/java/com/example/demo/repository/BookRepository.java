package com.example.demo.repository;

import static com.example.demo.generatedclasses.Tables.BOOK;

import java.util.List;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.demo.generatedclasses.tables.pojos.Book;
import com.example.demo.generatedclasses.tables.records.BookRecord;

@Repository
public class BookRepository { 
	private static final Logger logger = LoggerFactory.getLogger(AuthorRepository.class);

    private final DSLContext create;

    public BookRepository(DSLContext dslContext) {
        this.create = dslContext;
    }

    public List<Book> getBooks(){
        return create.select().from(BOOK).fetch().into(Book.class); // Will this work with a List?
    }

    public Book insert(int authorId, String title) {
        BookRecord book = create.newRecord(BOOK);
        book.setAuthorId(authorId);
        book.setTitle(title);
        book.store();
        Book result = book.into(Book.class);
        return result;
    }
}