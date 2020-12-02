USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_EC_ObtenerEstadosCuenta]    Script Date: 01/12/2020 18:04:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_EC_ObtenerEstadosCuenta] @cuenta int, @salida varchar output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		SELECT ID, fechaIni, fechaFin FROM dbo.estadoCuenta --Retorno de la consulta deseada

	

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
