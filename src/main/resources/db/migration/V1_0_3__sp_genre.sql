
IF EXISTS(SELECT *
          FROM sys.objects
          WHERE object_id = OBJECT_ID(N'psp_create_genre') AND type IN (N'P', N'PC'))
DROP PROCEDURE psp_create_genre
    GO

CREATE PROCEDURE psp_create_genre
    @genre_id                         BIGINT OUTPUT,
  @genre_name                           VARCHAR(255)

AS
  INSERT INTO tbl_genre (genre_name, date_created)
  VALUES (@genre_name, GETDATE())

SELECT * from tbl_genre where genre_id = SCOPE_IDENTITY();
RETURN @@Error
    GO



GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_retrieve_genre_by_id]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_retrieve_genre_by_id] AS'
END
GO
ALTER PROCEDURE [dbo].[psp_retrieve_genre_by_id]
    @id    BIGINT
AS

SELECT  * FROM tbl_genre (NOLOCK)
Where genre_id=@id;
GO



GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_retrieve_all_genre]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_retrieve_all_genre] AS'
END
GO
ALTER PROCEDURE [dbo].[psp_retrieve_all_genre]
AS

SELECT  * FROM tbl_genre  (NOLOCK)
    GO
