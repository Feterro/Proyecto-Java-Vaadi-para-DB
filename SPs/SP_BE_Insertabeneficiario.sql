USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_PA_SolicitaParentezcos]    Script Date: 11/8/2020 6:36:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_BE_InsertaBeneficiario] @PersonaDoc int, @CuentaNum int, @ParentescoNom varchar(30), @Porcentaje int, @InsertIn varchar(30), @outResultCode int output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		DECLARE 
		@PersonaId INT,
		@CuentaId INT,
		@ParentescoId INT


		IF NOT EXISTS (SELECT ID FROM dbo.persona WHERE valorDocIdent = @PersonaDoc) 
			BEGIN
				SET @outResultCode = 50002
			END
		IF NOT EXISTS (SELECT ID FROM dbo.cuentaAhorro WHERE numeroCuenta = @CuentaNum)
			BEGIN
				SET @outResultCode = 50003
			END

		SET @PersonaId = (SELECT ID FROM dbo.persona WHERE valorDocIdent = @PersonaDoc)
		SET @CuentaId = (SELECT ID FROM dbo.cuentaAhorro WHERE numeroCuenta = @CuentaNum)
		SET @ParentescoId = (SELECT ID FROM dbo.parentesco WHERE nombre = @ParentescoNom)

		INSERT INTO dbo.beneficiario(porcentaje, personaId, cuentaId, parentescoId, insertBy, insertAt, insertIn)
		VALUES(
		@Porcentaje,
		@PersonaId,
		@CuentaId,
		@ParentescoId,
		SUSER_NAME(),
		GETDATE(),
		@InsertIn)

		SELECT @outResultCode = 0

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
		SELECT @outResultCode = 50001

	END CATCH

SET NOCOUNT OFF
END
