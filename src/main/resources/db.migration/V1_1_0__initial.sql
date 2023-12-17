
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_genre') AND type IN (N'U'))
CREATE TABLE tbl_genre
(
    genre_id INT IDENTITY (1, 1) PRIMARY KEY,
    genre_name VARCHAR(50) NOT NULL,
    date_created   DATETIME DEFAULT CURRENT_TIMESTAMP
)
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_author') AND type IN (N'U'))
CREATE TABLE tbl_author
(
    author_id INT IDENTITY (1, 1) PRIMARY KEY,
    author_name VARCHAR(100) NOT NULL,
    date_created    DATETIME DEFAULT CURRENT_TIMESTAMP
)
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_book') AND type IN (N'U'))
CREATE TABLE tbl_book
(
    book_id INT IDENTITY (1, 1) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    year_of_publication INT NOT NULL,
    genre_id INT FOREIGN KEY REFERENCES tbl_genre(genre_id),
    author_id  INT FOREIGN KEY REFERENCES tbl_author(author_id),
    date_created   DATETIME DEFAULT CURRENT_TIMESTAMP
)
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_book_title' AND object_id = OBJECT_ID(N'tbl_book'))
CREATE INDEX idx_book_title ON tbl_book(title);
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_book_isbn' AND object_id = OBJECT_ID(N'tbl_book'))
CREATE INDEX idx_book_isbn ON tbl_book(isbn);
GO

--
-- IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_book_author') AND type IN (N'U'))
-- CREATE TABLE tbl_book_author
-- (
--     book_id INT FOREIGN KEY REFERENCES tbl_book(book_id),
--     author_id INT FOREIGN KEY REFERENCES tbl_author(author_id),
--     PRIMARY KEY (book_id, author_id)
-- )
-- GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_user') AND type IN (N'U'))
CREATE TABLE tbl_user
(
    user_id INT IDENTITY (1, 1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_on DATE DEFAULT CURRENT_TIMESTAMP
)
GO

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_shopping_cart') AND type IN (N'U'))
CREATE TABLE tbl_shopping_cart
(
    cart_id INT IDENTITY (1, 1) PRIMARY KEY,
    user_id INT FOREIGN KEY REFERENCES tbl_user(user_id)
)
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_shopping_cart_item') AND type IN (N'U'))
CREATE TABLE tbl_shopping_cart_item
(
    item_id INT IDENTITY (1, 1) PRIMARY KEY,
    cart_id INT FOREIGN KEY REFERENCES tbl_shopping_cart(cart_id),
    book_id INT FOREIGN KEY REFERENCES tbl_book(book_id),
    quantity INT NOT NULL
)
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_cart_user' AND object_id = OBJECT_ID(N'tbl_shopping_cart'))
CREATE INDEX idx_cart_user ON tbl_shopping_cart(user_id);
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_cart_item_book' AND object_id = OBJECT_ID(N'tbl_shopping_cart_item'))
CREATE INDEX idx_cart_item_book ON tbl_shopping_cart_item(book_id);
GO
