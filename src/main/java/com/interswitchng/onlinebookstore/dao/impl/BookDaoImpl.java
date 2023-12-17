package com.interswitchng.onlinebookstore.dao.impl;

import com.interswitchng.onlinebookstore.dao.BaseDao;
import com.interswitchng.onlinebookstore.dao.BookDao;
import com.interswitchng.onlinebookstore.dao.util.BookRowMapper;
import com.interswitchng.onlinebookstore.dao.util.RowCountMapper;
import com.interswitchng.onlinebookstore.dto.BookResponse;
import com.interswitchng.onlinebookstore.dto.BookSearchParams;
import com.interswitchng.onlinebookstore.exceptions.NotFoundException;
import com.interswitchng.onlinebookstore.model.Book;
import com.interswitchng.onlinebookstore.model.Page;
import com.interswitchng.onlinebookstore.utils.OnlineBookStoreResponseCode;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class BookDaoImpl extends BaseDao<BookResponse> implements BookDao {

  private SimpleJdbcCall searchBookByIsbn;

  private SimpleJdbcCall searchJdbc;


  private static final String ISBN = "isbn";

  private static final String TITLE = "title";
  private static final String AUTHOR = "author_name";
  private static final String GENRE = "genre_name";
  private static final String YEAR_OF_PUBLICATION = "year_of_publication";
  private static final String PAGE_NUM = "page_num";
  private static final String PAGE_SIZE = "page_size";


  @Autowired
  @Override
  public void setDataSource(@Qualifier(value = "onlineBookStoreDs") DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);



    createJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_create_book")
        .returningResultSet(SINGLE_RESULT, BeanPropertyRowMapper.newInstance(Book.class));

    searchBookByIsbn = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_search_books_by_isbn")
        .returningResultSet(MULTIPLE_RESULT, new BookRowMapper())
        .returningResultSet(RESULT_COUNT, new RowCountMapper());


    searchJdbc = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_search_books")
        .returningResultSet(MULTIPLE_RESULT, new BookRowMapper())
        .returningResultSet(RESULT_COUNT, new RowCountMapper());

    retrieveOneJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("psp_retrieve_book_by_id")
        .returningResultSet(SINGLE_RESULT, new BookRowMapper());

  }

  public Book create(Book model) {
    SqlParameterSource in = new MapSqlParameterSource()
        .addValue("book_id", 100)
        .addValue(TITLE, model.getTitle())
        .addValue("genre_id", model.getGenreId())
        .addValue("author_id", model.getAuthorId())
        .addValue(ISBN, model.getIsbn())
        .addValue("available_book_count",model.getAvailableBookCount())
        .addValue(YEAR_OF_PUBLICATION, model.getYearOfPublication());

    Map<String, Object> m = createJdbcCall.execute(in);
    Long id = (Long) m.get("book_id");
    model.setId(id.intValue());

    return model;
  }

  public Page<BookResponse> searchBooksByIsbn(BookSearchParams params) {
    SqlParameterSource in = new MapSqlParameterSource()
        .addValue("isbn", params.getIsbn());
    Map<String, Object> m = searchBookByIsbn.execute(in);
    List<BookResponse> content = (List<BookResponse>) m.get(MULTIPLE_RESULT);
    if (content.isEmpty()) {
      throw new NotFoundException(OnlineBookStoreResponseCode.ENTITY_NOT_FOUND.getCode(),
          "Records not found");
    }
    Long count = Long.valueOf(content.size());
    return new Page(count, content);
  }


  public Page<BookResponse> searchBooks(BookSearchParams params) {
    SqlParameterSource in = new MapSqlParameterSource()

        .addValue(YEAR_OF_PUBLICATION, params.getYearOfPublication() != null ? params.getYearOfPublication() : "")
        .addValue(TITLE,
            params.getTitle() != null ? "%" + params.getTitle() + "%" : "")
        .addValue(AUTHOR,
            params.getAuthorName() != null ? "%" + params.getAuthorName() + "%" : "")
        .addValue(GENRE,
            params.getGenreName() != null ? "%" + params.getAuthorName() + "%" : "")
        .addValue(PAGE_NUM, params.getPageNum())
        .addValue(PAGE_SIZE, params.getPageSize());
    Map<String, Object> m = searchJdbc.execute(in);
    List<BookResponse> content = (List<BookResponse>) m.get(MULTIPLE_RESULT);
    if (content.isEmpty()) {
      throw new NotFoundException(OnlineBookStoreResponseCode.ENTITY_NOT_FOUND.getCode(),
          "Records not found");
    }
    Long count = (Long) ((List) m.get(RESULT_COUNT)).get(0);
    return new Page(count, content);
  }



  public BookResponse retrieveBookById(Integer id) {
    SqlParameterSource in = new MapSqlParameterSource().addValue("id", id);
    Map<String, Object> m = retrieveOneJdbcCall.execute(in);
    List<BookResponse> result = (List<BookResponse>) m.get(SINGLE_RESULT);
    return result.isEmpty() ? null : result.get(0);
  }
}
