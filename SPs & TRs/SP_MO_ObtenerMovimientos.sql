USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_MO_ObtenerMovimientos]    Script Date: 12/5/2020 7:27:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_MO_ObtenerMovimientos] @inFechaIni date, @inFechaFin date, @inCuenta int, @outResultcode varchar(50) output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		DECLARE @cuenId int

		SET @cuenId = ( SELECT ID 
		FROM dbo.cuentaAhorro
		WHERE numeroCuenta = @inCuenta)

		SELECT fechaMovimiento, monto, descripcion, nombre 
		FROM dbo.movimiento 
		INNER JOIN dbo.tipoCuentaAhorro
		ON tipoMovimientoId = dbo.tipoCuentaAhorro.ID
		WHERE cuentaAhorroId = @cuenId AND 
		fechaMovimiento >= @inFechaIni AND
		fechaMovimiento <= @inFechaFin
	
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