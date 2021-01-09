USE [BDProyecto]
GO
/****** Object:  Trigger [dbo].[tr_BE_InsercionBeneficiario]    Script Date: 08/01/2021 04:57:55 a. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [dbo].[tr_CO_InsercionCuentaObjetivo]
ON [dbo].[CuentaObjetivo]
FOR INSERT 
AS 
BEGIN

	DECLARE 
	@lineaInsert XML = (SELECT objetivo, fechaIn, cuentaAhorroId, cuota, numeroCuenta, fechaFin, diaAhorro, tasaInteres
	FROM dbo.CuentaObjetivo
	WHERE ID = (SELECT COUNT(*) FROM dbo.CuentaObjetivo) 
	FOR XML AUTO),

	@InsertIn varchar(100) = (SELECT insertIn
	FROM dbo.CuentaObjetivo 
	WHERE ID = (SELECT COUNT(*) FROM dbo.CuentaObjetivo)),

	@InsertAt date = (SELECT insertAt
	FROM dbo.CuentaObjetivo 
	WHERE ID = (SELECT COUNT(*) FROM dbo.CuentaObjetivo)),

	@InsertBy varchar(50) = (SELECT insertBy
	FROM dbo.CuentaObjetivo 
	WHERE ID = (SELECT COUNT(*) FROM dbo.CuentaObjetivo))

	INSERT INTO dbo.evento 
	(tipoEventoId,
	usuario,
	fecha,
	direccionIp,
	xmlDespues)
	VALUES(
	4,
	@InsertBy,
	@InsertAt,
	@InsertIn,
	@lineaInsert)

END