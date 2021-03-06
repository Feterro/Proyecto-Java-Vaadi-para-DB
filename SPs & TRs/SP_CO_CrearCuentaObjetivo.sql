USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_CO_CrearCuentaObjetivo]    Script Date: 04/12/2020 23:03:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_CO_CrearCuentaObjetivo] @inCuentaNumAso int, @inObjetivo varchar(100), @inFechaInicio date, @inFechaFin date, @inCuota decimal(19, 4), @inNumCuenta varchar(10), @outResultCode int output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		DECLARE 
		@cuentaID int

		SET @outResultCode = 0

		SET @cuentaID = (SELECT ID FROM dbo.cuentaAhorro WHERE numeroCuenta = @inCuentaNumAso)

		INSERT INTO dbo.cuentaObjetivo(
		objetivo,
		fechaIn, 
		fechaFin, 
		cuota,
		cuentaAhorroId,
		numeroCuenta
		)
		VALUES(
		@inObjetivo,
		@inFechaInicio,
		@inFechaFin,
		@inCuota, 
		@cuentaID, 
		@inNumCuenta)


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