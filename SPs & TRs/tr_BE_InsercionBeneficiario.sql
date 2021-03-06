USE [BDProyecto]
GO
/****** Object:  Trigger [dbo].[tr_BE_InsercionBeneficiario]    Script Date: 08/01/2021 04:04:12 a. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER TRIGGER [dbo].[tr_BE_InsercionBeneficiario]
ON [dbo].[beneficiario]
FOR INSERT 
AS 
BEGIN

	DECLARE 
	@lineaInsert XML = (SELECT porcentaje, personaId, cuentaId, parentescoId 
	FROM dbo.beneficiario 
	WHERE ID = (SELECT COUNT(*) FROM dbo.beneficiario) 
	FOR XML AUTO),

	@InsertIn varchar(100) = (SELECT insertIn
	FROM dbo.beneficiario 
	WHERE ID = (SELECT COUNT(*) FROM dbo.beneficiario)),

	@InsertAt date = (SELECT insertAt
	FROM dbo.beneficiario 
	WHERE ID = (SELECT COUNT(*) FROM dbo.beneficiario)),

	@InsertBy varchar(50) = (SELECT insertBy
	FROM dbo.beneficiario 
	WHERE ID = (SELECT COUNT(*) FROM dbo.beneficiario))

	INSERT INTO dbo.evento 
	(tipoEventoId,
	usuario,
	fecha,
	direccionIp,
	xmlDespues)
	VALUES(
	1,
	@InsertBy,
	@InsertAt,
	@InsertIn,
	@lineaInsert)

END