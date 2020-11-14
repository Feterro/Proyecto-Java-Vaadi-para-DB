USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_EC_ObtenerEstadosCuenta]    Script Date: 14/11/2020 15:07:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_EC_ObtenerEstadosCuenta] @cuenta int, @fechaIn date output, @fechaFin date output
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
		SET @fechaIn='50001';
		SET @fechaFin = '50002';

	END CATCH

SET NOCOUNT OFF
END
