IF EXISTS(SELECT *
          FROM sys.objects
          WHERE object_id = OBJECT_ID(N'psp_create_shopping_cart') AND type IN (N'P', N'PC'))
DROP PROCEDURE psp_create_shopping_cart;
GO

CREATE PROCEDURE psp_create_shopping_cart
    @cart_id BIGINT OUTPUT,
    @user_id BIGINT
AS
BEGIN
    SET NOCOUNT ON;
INSERT INTO tbl_shopping_cart (user_id, created_at)
VALUES (@user_id, GETDATE());
SET @cart_id = SCOPE_IDENTITY();

RETURN 0;
END;
GO
IF EXISTS (SELECT *
           FROM sys.objects
           WHERE object_id = OBJECT_ID(N'psp_add_item_to_cart') AND type IN (N'P', N'PC'))
DROP PROCEDURE psp_add_item_to_cart;
GO

CREATE PROCEDURE psp_add_item_to_cart
    @item_id BIGINT OUTPUT,
    @cart_id BIGINT,
    @book_id BIGINT,
    @quantity BIGINT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @existingItem_id BIGINT;

    -- Check if the book already exists in the cart
SELECT @existingItem_id = item_id
FROM tbl_shopping_cart_item
WHERE cart_id = @cart_id AND book_id = @book_id;

IF (@existingItem_id IS NOT NULL)
BEGIN
UPDATE tbl_shopping_cart_item
SET quantity = quantity + @quantity
WHERE item_id = @existingItem_id;

SET @item_id = @existingItem_id;
END
ELSE
BEGIN
INSERT INTO tbl_shopping_cart_item (cart_id, book_id, quantity, created_at)
VALUES (@cart_id, @book_id, @quantity, GETDATE());

SET @item_id = SCOPE_IDENTITY();
END;

    IF @@ERROR = 0
        SET @item_id = @item_id;
ELSE
        SET @item_id = 0;
END;
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_retrieve_cart_by_user_id]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_retrieve_cart_by_user_id] AS'
END
GO

ALTER PROCEDURE [dbo].[psp_retrieve_cart_by_user_id]
    @user_id BIGINT
AS
BEGIN
    SET NOCOUNT ON;

SELECT
    sc.cart_id,
    sc.user_id,
    sc.created_at
FROM
    tbl_shopping_cart sc WITH (NOLOCK)
WHERE
    sc.user_id = @user_id;
END;
GO
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_retrieve_cart_items_by_user_id]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_retrieve_cart_items_by_user_id] AS'
END
GO

ALTER PROCEDURE [dbo].[psp_retrieve_cart_items_by_user_id]
    @userId    BIGINT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @cartId INT;

SELECT @cartId = cart_id FROM tbl_shopping_cart WHERE user_id = @userId;

IF (@cartId IS NOT NULL)
BEGIN
SELECT
    sci.item_id,
    sci.cart_id,
    sci.book_id,
    sci.quantity,
    sci.created_at,
    b.title AS book_title,
    b.price AS price,
    b.isbn AS book_isbn,
    b.year_of_publication AS book_year_of_publication,
    a.author_name AS author_name,
    g.genre_name AS genre_name,
    (SELECT SUM(b.price * sci.quantity)
     FROM tbl_shopping_cart_item sci
              INNER JOIN tbl_book b ON sci.book_id = b.book_id
     WHERE sci.cart_id = @cartId) AS total_price
FROM tbl_shopping_cart_item sci
         INNER JOIN tbl_book b ON sci.book_id = b.book_id
         INNER JOIN tbl_author a ON b.author_id = a.author_id
         INNER JOIN tbl_genre g ON b.genre_id = g.genre_id
WHERE sci.cart_id = @cartId;
END
ELSE
BEGIN
        DECLARE @errorMessage NVARCHAR(255);
        SET @errorMessage = 'User does not have a shopping cart.';
        THROW 50000, @errorMessage, 1;
END
END;



GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_retrieve_item_by_item_id]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_retrieve_item_by_item_id] AS'
END
GO
ALTER PROCEDURE [dbo].[psp_retrieve_item_by_item_id]
    @item_id    BIGINT
AS

SELECT  * FROM tbl_shopping_cart_item (NOLOCK)
Where item_id=@item_id;
GO
