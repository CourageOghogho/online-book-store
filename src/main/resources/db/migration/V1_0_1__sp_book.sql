IF EXISTS(SELECT *
          FROM sys.objects
          WHERE object_id = OBJECT_ID(N'psp_create_book') AND type IN (N'P', N'PC'))
DROP PROCEDURE psp_create_book
    GO

CREATE PROCEDURE psp_create_book
  @book_id                         BIGINT OUTPUT,
  @title                           VARCHAR(255),
  @isbn                            VARCHAR(255),
  @year_of_publication             INT,
  @genre_id                        BIGINT,
  @author_id                       BIGINT,
  @available_book_count            BIGINT,
  @price                           DECIMAL(10, 2)
AS
  INSERT INTO tbl_book (title, isbn, year_of_publication, genre_id, author_id,date_created,available_book_count,price)
  VALUES (@title, @isbn, @year_of_publication, @genre_id, @author_id, GETDATE(),@available_book_count,@price)

SELECT * from tbl_book where book_id = SCOPE_IDENTITY();
RETURN @@Error
    GO


GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_search_books_by_isbn]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_search_books_by_isbn] AS'
END
GO
ALTER PROCEDURE [dbo].[psp_search_books_by_isbn]
 @isbn VARCHAR(100) = NULL
AS

SELECT b.*, a.*, g.* FROM tbl_book b (NOLOCK)
LEFT JOIN tbl_author a ON b.author_id = a.author_id
LEFT JOIN tbl_genre g ON b.genre_id =g.genre_id

Where b.isbn=@isbn;
GO



GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_retrieve_book_by_id]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_retrieve_book_by_id] AS'
END
GO
ALTER PROCEDURE [dbo].[psp_retrieve_book_by_id]
    @id    BIGINT
AS

SELECT b.*, a.*, g.* FROM tbl_book b (NOLOCK)
                              LEFT JOIN tbl_author a ON b.author_id = a.author_id
                              LEFT JOIN tbl_genre g ON b.genre_id =g.genre_id

Where b.book_id=@id;
    GO



IF EXISTS(SELECT *
          FROM sys.objects
          WHERE object_id = OBJECT_ID(N'psp_search_books') AND type IN (N'P', N'PC'))
DROP PROCEDURE psp_search_books
    GO
CREATE PROCEDURE [dbo].[psp_search_books]
     @title VARCHAR(100) = NULL,
	 @author_name VARCHAR(100) = NULL,
	 @genre_name VARCHAR(100) = NULL,
	 @year_of_publication INT = NULL,
	 @page_num INT = NULL,
	 @page_size INT = NULL
AS

DECLARE @offset INT
SET @offset =  (@page_num - 1) * @page_size

DECLARE @select varchar(max) = 'SELECT  b.book_id,
                                  b.book_id AS book_id,
                                  b.title AS title,
                                  b.isbn AS isbn,
                                  b.year_of_publication AS year_of_publication,
                                  b.genre_id AS genre_id,
                                  b.author_id AS author_id,
                                  b.price AS price,
                                  b.available_book_count AS available_book_count,
                                  b.date_created AS date_created,
                                  a.author_id AS author_id,
                                  a.author_name AS author_name,                                  g.genre_id AS genre_id,
                                  g.genre_name AS genre_name
          FROM tbl_book b (NOLOCK)
          LEFT JOIN tbl_author a ON b.author_id = a.author_id
          LEFT JOIN tbl_genre g ON b.genre_id = g.genre_id'

DECLARE @count_query varchar(max) = 'SELECT COUNT(*) AS cnt
           FROM tbl_book b (NOLOCK)
          LEFT JOIN tbl_author a ON b.author_id = a.author_id
          LEFT JOIN tbl_genre g ON b.genre_id = g.genre_id'

DECLARE @order_by varchar(max) = ' ORDER BY b.date_created DESC, b.book_id OFFSET ' +  CONVERT(VARCHAR, @offset) + ' ROWS FETCH NEXT ' + CONVERT(VARCHAR, @page_size)  + ' ROWS ONLY'
DECLARE @where varchar(max) = ' WHERE'
DECLARE @query varchar(max) = NULL

IF (COALESCE(@title, '') != '')
	IF (@where = ' WHERE') SET @where = @where + ' b.title  LIKE ''%' + @title + '%'''
	ELSE SET @where = @where + ' AND b.title LIKE ''%' + @title + '%'''

IF (COALESCE(@author_name, '') != '')
	IF (@where = ' WHERE') SET @where = @where + ' a.author_name = ''' + @author_name + ''''
	ELSE SET @where = @where + ' AND a.author_name = ''' + @author_name + ''''

IF (COALESCE(@genre_name, '') != '')
	IF (@where = ' WHERE') SET @where = @where + ' g.genre_name = ''' + @genre_name + ''''
	ELSE SET @where = @where + ' AND g.genre_name = ''' + @genre_name + ''''

IF (COALESCE(@year_of_publication, '') != '')
	IF (@where = ' WHERE') SET @where = @where + ' b.year_of_publication = ''' + @year_of_publication + ''''
	ELSE SET @where = @where + ' AND b.year_of_publication = ''' + @year_of_publication + ''''
IF (@where = ' WHERE')
	SET @query = @select + @order_by
ELSE
	SET @query = @select + @where + @order_by

IF (@where = ' WHERE')
	SET @count_query = @count_query
ELSE
	SET @count_query = @count_query  + @where

PRINT @query

EXEC (@query)

EXEC (@count_query)

RETURN @@Error

GO




IF EXISTS(SELECT
            *
          FROM sys.objects
          WHERE object_id = OBJECT_ID(N'psp_update_book') AND type IN (N'P', N'PC'))
DROP PROCEDURE psp_update_book
    GO
CREATE PROCEDURE psp_update_book
  @book_id INT = 0  OUTPUT,
  @title                           VARCHAR(255),
  @isbn                            VARCHAR(255),
  @year_of_publication             INT,
  @genre_id                        BIGINT,
  @author_id                       BIGINT,
  @price                           DECIMAL(10, 2)

AS

UPDATE tbl_book
SET
    title = @title,
    isbn = @isbn,
    year_of_publication = @year_of_publication,
    genre_id = @genre_id,
    author_id = @author_id,
    price=@price
WHERE book_id = @book_id

SELECT * FROM tbl_book WHERE book_id = @book_id
    RETURN @@Error
GO



IF EXISTS(SELECT *
          FROM sys.objects
          WHERE object_id = OBJECT_ID(N'psp_delete_book') AND type IN (N'P', N'PC'))
DROP PROCEDURE psp_delete_book
    GO
CREATE PROCEDURE psp_delete_book
    @book_id    BIGINT
AS
DELETE FROM tbl_book WHERE book_id = @book_id
    GO