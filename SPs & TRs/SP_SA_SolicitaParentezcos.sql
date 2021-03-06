USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_PA_SolicitaParentezcos]    Script Date: 11/8/2020 6:36:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_PA_SolicitaParentezcos] @parentezco varchar output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		SELECT (nombre) FROM dbo.parentesco --Retorno de la consulta deseada

	END TRY
	BEGIN CATCH

		INSERT INTO dbo.errores VALUES(
			SUSER_NAME(),
			ERROR_NUMBER(),
			ERROR_STATE(),
			ERROR_SEVERITY(),
			ERROR_LINE(),
			ERROR_PROCEDURE(),
			ERROR_MESSAGE(),
			GETDATE()
		);
		SET @parentezco='50001';

	END CATCH

SET NOCOUNT OFF
END