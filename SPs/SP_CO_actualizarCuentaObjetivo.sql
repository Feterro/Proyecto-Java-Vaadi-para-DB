USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_PE_BE_ActualizarPersona]    Script Date: 04/12/2020 0:18:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_CO_actualizarCuentaObjetivo] @inObjetivo varchar(100), @inFechaFin date, @inCuota int, @inCuentaNum int,  @outResultCode int output

AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		DECLARE 
		@cuentaID int
		
		SET @cuentaID = (SELECT ID FROM dbo.cuentaAhorro WHERE numeroCuenta = @inCuentaNum)

		SET @outResultCode = 0

		UPDATE dbo.cuentaObjetivo SET 
		fechaFin = @inFechaFin, 
		objetivo = @inObjetivo, 
		cuota = @inCuota

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
