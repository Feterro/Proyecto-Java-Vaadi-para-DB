USE [BDProyecto]
GO
/****** Object:  Trigger [dbo].[tr_BE_CambiosBeneficiario]    Script Date: 08/01/2021 03:37:08 a. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER TRIGGER [dbo].[tr_BE_CambiosBeneficiario]
ON [dbo].[beneficiario]
FOR UPDATE 
AS 
BEGIN


	DECLARE 
	@lineaAntes XML = (SELECT personaId, activo, porcentaje, cuentaId, parentescoId 
	FROM deleted ORDER BY ID DESC
	FOR XML AUTO),

	@lineaDespues XML = NULL, 

	@InsertIn varchar(100) = (SELECT insertIn
	FROM deleted ),

	@InsertAt date = (SELECT insertAt
	FROM deleted),

	@InsertBy varchar(100) = (SELECT insertBy
	FROM deleted),

	@desactivacion bit = (SELECT activo FROM inserted),

	@tipoEnvento int = 3

	IF @desactivacion = 1
	BEGIN

		SET @tipoEnvento = 2
		SET @lineaDespues = (SELECT personaId, activo, porcentaje, cuentaId, parentescoId 
		FROM inserted ORDER BY ID DESC
		FOR XML AUTO)
	END

		INSERT INTO dbo.evento 
		(tipoEventoId,
		usuario,
		fecha,
		direccionIp,
		xmlDespues,
		xmlAntes)
		VALUES(
		@tipoEnvento,
		@InsertBy,
		@InsertAt,
		@InsertIn,
		@lineaDespues,
		@lineaAntes)
		

END
