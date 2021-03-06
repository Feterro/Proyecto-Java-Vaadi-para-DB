USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_PE_BE_ActualizarPersona]    Script Date: 11/13/2020 11:15:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_PE_BE_ActualizarPersona] @Nombre varchar(100), @PersonaDocOri int, @PersonaDoc int, @FechaNacimiento date, @Telefono1 int,
@Telefono2 int, @TipoDocIdent varchar(30), @Correo varchar(50), @ParentescoNom varchar(30), @Porcentaje int,@InsertIn varchar(30), @outResultCode int output

AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		DECLARE 
		@ParentescoId INT,
		@TipoDocId INT,
		@PersonaId INT
		

		SET @outResultCode = 0

		IF NOT EXISTS (SELECT ID FROM dbo.persona WHERE valorDocIdent = @PersonaDocOri)
			BEGIN
				SET @outResultCode = 50003
			END
		IF NOT EXISTS (SELECT ID FROM dbo.tipoDocIdent WHERE tipoDoc = @TipoDocIdent)
			BEGIN
				SET @outResultCode = 50003
			END
		IF NOT EXISTS (SELECT ID FROM dbo.parentesco WHERE nombre = @ParentescoNom)
			BEGIN
				SET @outResultCode = 50003
			END


		SET @TipoDocId = (SELECT ID FROM dbo.tipoDocIdent WHERE tipoDoc = @TipoDocIdent)

		UPDATE dbo.persona SET 
		nombre = @Nombre,
		email = @Correo,
		nacimiento = @FechaNacimiento,
		telefono1 = @Telefono1,
		telefono2 = @Telefono2,
		tipoDocIdent = @TipoDocId,
		valorDocIdent = @PersonaDoc,
		InsertBy = SUSER_NAME(),
		InsertAt = GETDATE(),
		InsertIn = @InsertIn
		WHERE valorDocIdent = @PersonaDocOri

		SET @ParentescoId = (SELECT ID FROM dbo.parentesco WHERE nombre = @ParentescoNom)
		SET @PersonaId = (SELECT ID FROM dbo.persona WHERE valorDocIdent = @PersonaDoc)

		UPDATE dbo.beneficiario SET 
		porcentaje = @Porcentaje,
		parentescoId = @ParentescoId,
		insertBy = SUSER_NAME(),
		insertAt = GETDATE(),
		insertIn = @InsertIn
		WHERE personaId = @PersonaId


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
