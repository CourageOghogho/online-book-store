package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.impl.AuthorDao;
import com.interswitchng.onlinebookstore.dto.AuthorRequest;
import com.interswitchng.onlinebookstore.exceptions.ServiceLayerException;
import com.interswitchng.onlinebookstore.model.Author;
import com.interswitchng.onlinebookstore.service.AuthorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorDao authorDao;

  @Override
  public Author createAuthor(AuthorRequest request) {
    var model=Author.builder()
        .name(request.getAuthorName())
        .build();

    var response=authorDao.create(model);

    if(response==null) throw new ServiceLayerException("Something went wrong");
    return response;
  }

  @Override
  public Author retrieveAuthorById(Integer authorId) {
    var response=authorDao.retrieveBookById(authorId);

    if(response==null) throw new ServiceLayerException("Something went wrong");
    return response;
  }

  @Override
  public List<Author> retrieveAllAuthor() {
    var response=authorDao.getAllAuthors();

    if(response==null) throw new ServiceLayerException("Something went wrong");
    return response;
  }
}
