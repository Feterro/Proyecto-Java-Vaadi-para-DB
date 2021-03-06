ALTER PROCEDURE [dbo].[SP_MO_ObtenerMovimientos] @inFechaIni date, @inFechaFin date, @inCuenta int, @outResultcode varchar(50) output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY

		SET @outResultCode = 0
		IF NOT EXISTS(SELECT ID FROM dbo.cuentaAhorro WHERE numeroCuenta = @inCuenta)
		BEGIN
			SELECT @outResultCode = 50002
		END
		
		DECLARE @cuenId int

		SET @cuenId = ( SELECT ID 
		FROM dbo.cuentaAhorro
		WHERE numeroCuenta = @inCuenta)

		SELECT fechaMovimiento, monto, descripcion, nombre 
		FROM dbo.movimiento 
		INNER JOIN dbo.TipoMovimiento
		ON tipoMovimientoId = dbo.TipoMovimiento.Id
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