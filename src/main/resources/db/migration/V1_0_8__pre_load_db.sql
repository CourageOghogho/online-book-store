IF NOT EXISTS (SELECT 1 FROM tbl_genre)
BEGIN

INSERT INTO tbl_genre (genre_name, date_created)
VALUES
    ('Friction', GETDATE()),
    ('Thriller', GETDATE()),
    ('Mystery', GETDATE()),
    ('Poetry', GETDATE()),
    ('Horror', GETDATE()),
    ('Satire', GETDATE());
END


IF (SELECT COUNT(*) FROM tbl_book) < 3
BEGIN

INSERT INTO tbl_author (author_name, date_created)
VALUES
    ('Robert C. Martin', GETDATE()),
    ('Dave Thomas', GETDATE()),
    ('Erich Gamma', GETDATE());


DECLARE @genreId1 INT, @authorId1 INT;

SELECT @genreId1 = genre_id FROM tbl_genre WHERE genre_name = 'Friction';
SELECT @authorId1 = author_id FROM tbl_author WHERE author_name = 'Robert C. Martin';

INSERT INTO tbl_book (title, isbn, year_of_publication, genre_id, author_id, available_book_count, price, date_created)
VALUES
    ('Clean Code', '978-0-13-235088-4', 2008, @genreId1, @authorId1, 0, 0.00, GETDATE());

DECLARE @genreId2 INT, @authorId2 INT;

SELECT @genreId2 = genre_id FROM tbl_genre WHERE genre_name = 'Thriller';
SELECT @authorId2 = author_id FROM tbl_author WHERE author_name = 'Dave Thomas';

INSERT INTO tbl_book (title, isbn, year_of_publication, genre_id, author_id, available_book_count, price, date_created)
VALUES
    ('The Pragmatic Programmer', '978-0-13-595705-9', 1999, @genreId2, @authorId2, 0, 0.00, GETDATE());

DECLARE @genreId3 INT;

SELECT @genreId3 = genre_id FROM tbl_genre WHERE genre_name = 'Thriller';

INSERT INTO tbl_book (title, isbn, year_of_publication, genre_id, author_id, available_book_count, price, date_created)
VALUES
    ('Design Patterns', '978-0-201-63361-0', 1994, @genreId3, @authorId2, 0, 0.00, GETDATE());
END