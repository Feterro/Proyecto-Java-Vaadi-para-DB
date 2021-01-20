USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_CO_VerDetallesCuentaObjetivo]    Script Date: 19/01/2021 16:44:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_CO_VerDetallesCuentaObjetivo] @inCuentaObID varchar(10), @inEsAdmin bit, @outResultCode int output

AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		SET @outResultCode = 0
		IF NOT EXISTS(SELECT objetivo FROM dbo.cuentaObjetivo WHERE numeroCuenta = @inCuentaObID)
		BEGIN
			SELECT @outResultCode = 50002
		END
		IF(@inEsAdmin = 1)
			SELECT objetivo, fechaIn, fechaFin, saldoQueHubo, cuota, numeroCuenta FROM dbo.cuentaObjetivo WHERE numeroCuenta = @inCuentaObID
		ELSE
			SELECT objetivo, fechaIn, fechaFin, saldo, cuota, numeroCuenta FROM dbo.cuentaObjetivo WHERE numeroCuenta = @inCuentaObID

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