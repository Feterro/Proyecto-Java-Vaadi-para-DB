CREATE PROCEDURE SP_PA_SolicitaParentezcos @outResultCode INT output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY

		SELECT @outResultCode = 0 --Set de codigo en respuesta sin errores
	
		RETURN SELECT (nombre) FROM dbo.parentesco --Retorno de la consulta deseada

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
		SET @outResultCode=50001;

	END CATCH

SET NOCOUNT OFF
END

DECLARE @ResultCode INT
EXECUTE SP_PA_SolicitaParentezcos @ResultCode OUTPUT
PRINT @ResultCode