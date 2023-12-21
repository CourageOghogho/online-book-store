IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_create_order]') AND type IN (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_create_order] AS'
END
GO

ALTER PROCEDURE sp_create_order_v2
    @user_id INT,
    @reference_id VARCHAR(50),
    @order_status INT,
    @total_amount DECIMAL(10, 2),
    @output_order_id INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

BEGIN TRY
BEGIN TRANSACTION;

INSERT INTO tbl_order (user_id, reference_id, order_status, total_amount, order_date)
VALUES (@user_id, @reference_id, @order_status, @total_amount, GETDATE());

SET @output_order_id = SCOPE_IDENTITY();

COMMIT;
END TRY
BEGIN CATCH
ROLLBACK;
END CATCH;
END;



IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_save_order_items]') AND type IN (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[sp_save_order_items] AS'
END
GO

ALTER PROCEDURE sp_save_order_items_v2
    @order_id INT,
    @user_id INT
AS
BEGIN
    SET NOCOUNT ON;

INSERT INTO tbl_order_item (order_id, book_id, quantity, price)
SELECT
    @order_id,
    sci.book_id,
    sci.quantity,
    sci.price
FROM tbl_shopping_cart_item sci
WHERE sci.cart_id IN (SELECT cart_id FROM tbl_shopping_cart WHERE user_id = @user_id);
END;


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_create_order_history]') AND type IN (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[sp_create_order_history] AS'
END
GO

ALTER PROCEDURE sp_create_order_history_v2
    @order_id INT,
    @reference_id VARCHAR(50),
    @order_status INT,
    @user_id INT,
    @total_amount DECIMAL(10, 2)
AS
BEGIN
    SET NOCOUNT ON;

INSERT INTO tbl_order_history (order_id, order_reference_id, status, update_date, book_id, title, author_name, user_id, total_amount, quantity_ordered)
SELECT
    @order_id,
    @reference_id,
    @order_status,
    GETDATE(),
    sci.book_id,
    b.title,
    a.author_name,
    @user_id,
    @total_amount,
    sci.quantity
FROM tbl_shopping_cart_item sci
         JOIN tbl_book b ON sci.book_id = b.book_id
         JOIN tbl_author a ON b.author_id = a.author_id
WHERE sci.cart_id IN (SELECT cart_id FROM tbl_shopping_cart WHERE user_id = @user_id);
END;
