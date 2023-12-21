package com.interswitchng.onlinebookstore.service;

import com.interswitchng.onlinebookstore.dto.AuthorRequest;
import com.interswitchng.onlinebookstore.model.Author;
import java.util.List;

public interface AuthorService {

  Author createAuthor(AuthorRequest request);

  Author retrieveAuthorById(Integer authorId);

  List<Author> retrieveAllAuthor();
}
