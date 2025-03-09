package com.example.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.database.generatedclasses.tables.pojos.Author;
import com.example.repository.AuthorRepository;

@Service
public class AuthorService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository repository;
    @Autowired
    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }
    public List<Author> get(String firstName, String lastName){
        if(firstName != null && lastName != null){
            return repository.getByName(firstName, lastName);
        }
        else if(firstName != null){
            return repository.fetchByFirstName(firstName);
        }
        else if(lastName != null){
            return repository.fetchByLastName(lastName);
        }
        else {
            return repository.getAuthors();
        }
    }
    public List<String> getFirstNames() {
        return repository.getFirstNames();
    }
    public Author insert(String firstName, String lastName){
        return repository.insert(firstName, lastName);
    }
    public void selectIntoMultipleObjects(String firstName, String lastName){
        repository.selectIntoMultipleObjects(firstName, lastName);
    }
}