USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_BE_SolicitarPersonas]    Script Date: 04/12/2020 23:07:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_CO_VerDetallesCuentaObjetivo] @inCuentaObID varchar(10), @outResultCode int output

AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
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