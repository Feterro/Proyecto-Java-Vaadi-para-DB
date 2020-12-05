USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_BE_EliminarBeneficiario]    Script Date: 04/12/2020 23:20:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_CO_DesactivarCuenta] @inCuentaNum varchar(10),  @outResultCode int output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		

		UPDATE dbo.cuentaObjetivo
		SET activo = 0, fechaDesactivacion = GETDATE()
		FROM dbo.cuentaObjetivo WHERE numeroCuenta = @inCuentaNum;


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