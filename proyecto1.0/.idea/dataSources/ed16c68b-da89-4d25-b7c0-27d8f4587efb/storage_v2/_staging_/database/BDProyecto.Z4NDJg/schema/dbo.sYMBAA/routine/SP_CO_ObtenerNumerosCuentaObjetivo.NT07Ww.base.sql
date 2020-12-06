CREATE PROCEDURE [dbo].[SP_CO_ObtenerNumerosCuentaObjetivo] @inNumCuentaAso int, @outResultCode int output

AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		DECLARE
		@cuentaId int
		
		SET @cuentaId = (SELECT ID FROM dbo.cuentaAhorro WHERE numeroCuenta = @inNumCuentaAso)

		SELECT numeroCuenta FROM dbo.cuentaObjetivo WHERE cuentaAhorroId = @cuentaId



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
		SET @outResultCode = 50001


	END CATCH

	SELECT @outResultCode AS N

SET NOCOUNT OFF
END
go

