USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_CO_actualizarCuentaObjetivo]    Script Date: 05/12/2020 15:40:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_CO_actualizarCuentaObjetivo] @inObjetivo varchar(100), @inFechaFin date, @inCuota int, @inCuentaNum int,  @outResultCode int output

AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY

		SET @outResultCode = 0

		UPDATE dbo.cuentaObjetivo SET 
		fechaFin = @inFechaFin, 
		objetivo = @inObjetivo, 
		cuota = @inCuota
		WHERE numeroCuenta = @inCuentaNum

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
