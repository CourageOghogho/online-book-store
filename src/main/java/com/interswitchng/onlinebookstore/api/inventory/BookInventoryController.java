package com.interswitchng.onlinebookstore.api.inventory;

import com.interswitchng.onlinebookstore.dto.AuthorRequest;
import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.BookSearchParams;
import com.interswitchng.onlinebookstore.dto.GenreRequest;
import com.interswitchng.onlinebookstore.model.Author;
import com.interswitchng.onlinebookstore.model.Genre;
import com.interswitchng.onlinebookstore.model.Page;
import com.interswitchng.onlinebookstore.service.AuthorService;
import com.interswitchng.onlinebookstore.service.BookService;
import com.interswitchng.onlinebookstore.service.GenreService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/inventory-service/books")
public class BookInventoryController {

  private final BookService bookService;

  private final AuthorService authorService;

  private final GenreService genreService;

  @GetMapping("/search")
  public ResponseEntity<Page<BookResponse>> search(BookSearchParams params){
    return ResponseEntity.ok(bookService.searchBooks(params));
  }

  @GetMapping("/{bookId}")
  public ResponseEntity<BookResponse> retrieveByBookId(@PathVariable(required = true) Integer bookId) {
    return ResponseEntity.ok(bookService.retrieveByBookId(bookId));
  }

  @PostMapping("authors")
  public ResponseEntity<Author> createAuthor(@Valid @RequestBody AuthorRequest request) {
    return ResponseEntity.ok(authorService.createAuthor(request));
  }

  @GetMapping("authors/{authorId}")
  public ResponseEntity<Author> retrieveAuthorById(@PathVariable(required = true) Integer authorId) {
    return ResponseEntity.ok(authorService.retrieveAuthorById(authorId));
  }
  @GetMapping("authors")
  public ResponseEntity<List<Author>> retrieveAllAuthor() {
    return ResponseEntity.ok(authorService.retrieveAllAuthor());
  }

  @PostMapping("genres")
  public ResponseEntity<Genre> createGenre(@Valid @RequestBody GenreRequest request) {
    return ResponseEntity.ok(genreService.createGenre(request));
  }

  @GetMapping("genres/{genreId}")
  public ResponseEntity<Genre> retrieveGenreById(@PathVariable(required = true) Integer genreId) {
    return ResponseEntity.ok(genreService.retrieveGenreById(genreId));
  }
  @GetMapping("genres")
  public ResponseEntity<List<Genre>> retrieveAllGenres() {
    return ResponseEntity.ok(genreService.retrieveAllGenres());
  }






}
