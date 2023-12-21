
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'psp_retrieve_order_by_reference') AND type IN (N'P', N'PC'))
BEGIN
DROP PROCEDURE psp_retrieve_order_by_reference;
END
GO

CREATE PROCEDURE psp_retrieve_order_by_reference
    @reference_id VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

SELECT
    o.order_id,
    o.user_id,
    o.reference_id,
    o.order_date,
    o.order_status,
    o.payment_method,
    oi.order_item_id,
    oi.quantity,
    oi.price,
    b.title AS book_title
FROM tbl_order o
         JOIN tbl_order_item oi ON o.order_id = oi.order_id
         JOIN tbl_book b ON oi.book_id = b.book_id
WHERE o.reference_id = @reference_id;
END
GO



IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'psp_create_order') AND type IN (N'P', N'PC'))
BEGIN
DROP PROCEDURE psp_create_order;
END
GO

CREATE PROCEDURE psp_create_order
    @user_id INT,
    @reference_id VARCHAR(50),
    @order_status INT,
    @total_amount DECIMAL(10, 2),
    @quantity_ordered INT
AS
BEGIN
    SET NOCOUNT ON;
BEGIN TRY
BEGIN TRANSACTION;

INSERT INTO tbl_order (user_id, reference_id, order_status, total_amount, order_date)
VALUES (@user_id, @reference_id, @order_status, @total_amount, GETDATE());

DECLARE @order_id INT = SCOPE_IDENTITY();

INSERT INTO tbl_order_item (order_id, book_id, quantity, price)
SELECT
    @order_id,
    sci.book_id,
    sci.quantity,
    sci.price
FROM tbl_shopping_cart_item sci
WHERE sci.cart_id IN (SELECT cart_id FROM tbl_shopping_cart WHERE user_id = @user_id);

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
    @quantity_ordered
FROM tbl_shopping_cart_item sci
         JOIN tbl_book b ON sci.book_id = b.book_id
         JOIN tbl_author a ON b.author_id = a.author_id
WHERE sci.cart_id IN (SELECT cart_id FROM tbl_shopping_cart WHERE user_id = @user_id);

DELETE FROM tbl_shopping_cart_item WHERE cart_id = (SELECT cart_id FROM tbl_shopping_cart WHERE user_id = @user_id);
EXEC psp_retrieve_order_by_reference @reference_id, @order_id OUTPUT;
COMMIT;
END TRY
BEGIN CATCH
ROLLBACK;

END CATCH;
END;

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'psp_update_order') AND type IN (N'P', N'PC'))
BEGIN
EXEC ('CREATE PROCEDURE psp_update_order
        @update_reference_id VARCHAR(50),
        @update_order_status INT
    AS
    BEGIN
        UPDATE tbl_order
        SET order_status = @update_order_status
        WHERE reference_id = @update_reference_id;
    END;');
END;


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'psp_retrieve_order_history_by_user_id') AND type IN (N'P', N'PC'))
BEGIN
EXEC ('CREATE PROCEDURE psp_retrieve_order_history_by_user_id
    @user_id INT,
    @page_size INT,
    @page_number INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @start_row INT = (@page_number - 1) * @page_size + 1;
    DECLARE @end_row INT = @page_number * @page_size;

    SELECT
        oh.order_history_id,
        oh.order_id,
        oh.status,
        oh.update_date,
        oh.order_reference_id,
        oh.book_id,
        oh.title,
        oh.author_name,
        oh.user_id,
        oh.total_amount,
        oh.quantity_ordered
    FROM (
             SELECT
                 ROW_NUMBER() OVER (ORDER BY oh.update_date DESC) AS row_num,
                     oh.order_history_id,
                 oh.order_id,
                 oh.status,
                 oh.update_date,
                 oh.order_reference_id,
                 oh.book_id,
                 oh.title,
                 oh.author_name,
                 oh.user_id,
                 oh.total_amount,
                 oh.quantity_ordered
             FROM tbl_order_history oh
             WHERE oh.user_id = @user_id
         ) AS oh
    WHERE oh.row_num BETWEEN @start_row AND @end_row;
END;');
END;