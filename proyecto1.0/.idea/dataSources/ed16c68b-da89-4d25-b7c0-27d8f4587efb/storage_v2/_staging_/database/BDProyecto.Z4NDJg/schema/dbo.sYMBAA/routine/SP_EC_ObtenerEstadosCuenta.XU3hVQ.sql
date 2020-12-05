CREATE PROCEDURE [dbo].[SP_EC_ObtenerEstadosCuenta] @cuenta int, @salida varchar output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		SELECT fechaIni, fechaFin FROM dbo.estadoCuenta --Retorno de la consulta deseada

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
		SET @salida = '50001'

	END CATCH

SET NOCOUNT OFF
END
go

