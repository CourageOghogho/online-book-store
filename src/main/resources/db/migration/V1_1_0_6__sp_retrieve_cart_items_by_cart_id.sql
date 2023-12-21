IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_retrieve_all_cart_items_by_cart_id]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_retrieve_all_cart_items_by_cart_id] AS'
END
GO

ALTER PROCEDURE [dbo].[psp_retrieve_all_cart_items_by_cart_id]
    @cartId INT
AS
BEGIN
    SET NOCOUNT ON;

SELECT
    sci.item_id,
    sci.cart_id,
    sci.book_id,
    sci.quantity,
    sci.created_at,
    b.title AS book_title,
    b.isbn AS book_isbn,
    b.year_of_publication AS book_year_of_publication,
    b.available_book_count,
    a.author_name AS author_name,
    a.author_id,
    g.genre_name AS genre_name,
    g.genre_id,
    b.price
FROM
    tbl_shopping_cart_item sci
        INNER JOIN
    tbl_book b ON sci.book_id = b.book_id
        INNER JOIN
    tbl_author a ON b.author_id = a.author_id
        INNER JOIN
    tbl_genre g ON b.genre_id = g.genre_id
WHERE
        sci.cart_id = @cartId;
END;
