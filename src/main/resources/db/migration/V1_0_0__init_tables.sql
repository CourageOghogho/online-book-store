
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_genre') AND type IN (N'U'))
CREATE TABLE tbl_genre
(
    genre_id INT IDENTITY (1, 1) PRIMARY KEY,
    genre_name VARCHAR(50) NOT NULL,
    date_created   DATE
)
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_author') AND type IN (N'U'))
CREATE TABLE tbl_author
(
    author_id INT IDENTITY (1, 1) PRIMARY KEY,
    author_name VARCHAR(100) NOT NULL,
    date_created    DATE
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
    available_book_count INT NOT NULL DEFAULT 0,
    price DECIMAL(10, 2) NOT NULL,
    date_created   DATE
)
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_book_title' AND object_id = OBJECT_ID(N'tbl_book'))
CREATE INDEX idx_book_title ON tbl_book(title);
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_book_isbn' AND object_id = OBJECT_ID(N'tbl_book'))
CREATE INDEX idx_book_isbn ON tbl_book(isbn);
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_user') AND type IN (N'U'))
CREATE TABLE tbl_user
(
    user_id INT IDENTITY (1, 1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    created_on DATE
)
GO

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_shopping_cart') AND type IN (N'U'))
CREATE TABLE tbl_shopping_cart
(
    cart_id  INT IDENTITY (1, 1) PRIMARY KEY,
    user_id INT,
    total_price DECIMAL(10, 2) NOT NULL DEFAULT 0,
    created_at DATE
    FOREIGN KEY (user_id) REFERENCES tbl_user(user_id)
)
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'tbl_shopping_cart_item') AND type IN (N'U'))
CREATE TABLE tbl_shopping_cart_item
(
    item_id  INT IDENTITY (1, 1) PRIMARY KEY,
    cart_id INT,
    book_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    created_at DATE
    FOREIGN KEY (cart_id) REFERENCES tbl_shopping_cart(cart_id),
    FOREIGN KEY (book_id) REFERENCES tbl_book(book_id)
)
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_cart_user' AND object_id = OBJECT_ID(N'tbl_shopping_cart'))
CREATE INDEX idx_cart_user ON tbl_shopping_cart(user_id);
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_cart_item_book' AND object_id = OBJECT_ID(N'tbl_shopping_cart_item'))
CREATE INDEX idx_cart_item_book ON tbl_shopping_cart_item(book_id);
GO


IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'tbl_order')
BEGIN
CREATE TABLE tbl_order (
    order_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT FOREIGN KEY REFERENCES tbl_user(user_id),
    reference_id VARCHAR(50) UNIQUE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_date DATE,
    order_status INT NOT NULL,
    payment_method INT NULL
);

CREATE INDEX idx_user_id ON tbl_order(user_id);
CREATE INDEX idx_reference_id ON tbl_order(reference_id);
END



IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'tbl_order_item')
BEGIN
CREATE TABLE tbl_order_item (
    order_item_id INT PRIMARY KEY IDENTITY(1,1),
    order_id INT FOREIGN KEY REFERENCES tbl_order(order_id),
    book_id INT FOREIGN KEY REFERENCES tbl_book(book_id),
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE INDEX idx_order_id ON tbl_order_item(order_id);
CREATE INDEX idx_book_id ON tbl_order_item(book_id);
END



IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'tbl_order_history')
BEGIN
CREATE TABLE tbl_order_history (
    order_history_id INT PRIMARY KEY IDENTITY(1,1),
    order_id INT,
    order_reference_id VARCHAR(50),
    status VARCHAR(50) NOT NULL,
    update_date DATE,
    book_id INT,
    title VARCHAR(255),
    author_name VARCHAR(100),
    user_id INT,
    total_amount DECIMAL(10, 2)
);

CREATE INDEX idx_order_history_id ON tbl_order_history(order_id);
CREATE INDEX idx_book_id ON tbl_order_history(book_id);
CREATE INDEX idx_user_id ON tbl_order_history(user_id);
CREATE INDEX idx_order_reference_id ON tbl_order_history(order_reference_id);
END
