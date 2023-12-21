IF EXISTS(SELECT *
          FROM sys.objects
          WHERE object_id = OBJECT_ID(N'psp_create_author') AND type IN (N'P', N'PC'))
DROP PROCEDURE psp_create_author
    GO

CREATE PROCEDURE psp_create_author
    @author_id                         BIGINT OUTPUT,
  @author_name                           VARCHAR(255)

AS
  INSERT INTO tbl_author (author_name, date_created)
  VALUES (@author_name, GETDATE())

SELECT * FROM tbl_author where author_id=SCOPE_IDENTITY();
RETURN @@Error
    GO



GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_retrieve_author_by_id]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_retrieve_author_by_id] AS'
END
GO
ALTER PROCEDURE [dbo].[psp_retrieve_author_by_id]
    @id    BIGINT
AS

SELECT  a.* FROM tbl_author a (NOLOCK)
                Where a.author_id=@id;
GO




GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[psp_retrieve_all_author]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[psp_retrieve_all_author] AS'
END
GO
ALTER PROCEDURE [dbo].[psp_retrieve_all_author]
AS

SELECT  * FROM tbl_author  (NOLOCK)
GO
