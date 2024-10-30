package com.example.demo.database;

import static com.example.demo.generatedclasses.Tables.AUTHOR;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JOOQExample {

    private final DSLContext create;

    @Autowired
    public JOOQExample(DSLContext dslContext) {
        this.create = dslContext;
    }

    public List<String> getFirstNames() {
        return this.create.selectFrom(AUTHOR).fetch(AUTHOR.FIRST_NAME);
    }
}