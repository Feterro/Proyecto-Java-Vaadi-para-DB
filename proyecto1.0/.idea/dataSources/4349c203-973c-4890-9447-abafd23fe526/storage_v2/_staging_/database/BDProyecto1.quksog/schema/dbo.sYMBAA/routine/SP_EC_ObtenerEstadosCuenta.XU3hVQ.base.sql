CREATE PROCEDURE [dbo].[SP_EC_ObtenerEstadosCuenta] @inCuenta int, @outResultcode varchar(50) output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY

		
		SELECT fechaIni, fechaFin FROM dbo.estadoCuenta 
		WHERE cuentaAhorroId = @inCuenta AND fechaFin 
		IS NOT NULL --Retorno de la consulta deseada
	

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
		SELECT @outResultcode='50001';

	END CATCH

SET NOCOUNT OFF
END
go

