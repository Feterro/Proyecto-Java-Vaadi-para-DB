USE BDProyecto;
GO

SET NOCOUNT ON
GO

ALTER TRIGGER tr_primerEstadoCuenta
ON dbo.cuentaAhorro
FOR INSERT 
AS 
BEGIN

	DECLARE 
	@fechaIni date = (SELECT fechaApertura FROM dbo.cuentaAhorro WHERE ID = (SELECT COUNT(*) FROM cuentaAhorro))

	INSERT INTO dbo.estadoCuenta
	(fechaIni,
	cuentaAhorroId,
	saldoIni,
	saldoFin)
	
	VALUES
	(@fechaIni,
	(SELECT COUNT(*) FROM cuentaAhorro),
	0,
	0)


END