USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_BE_InsertaBeneficiario]    Script Date: 11/11/2020 6:12:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Alter PROCEDURE [dbo].[SP_BE_InsertaBeneficiarioComplejo] @Nombre varchar(100), @PersonaDoc int, @FechaNacimiento date, @Telefono1 int,
@Telefono2 int, @TipoDocIdent varchar(30), @Correo varchar(50), @CuentaNum int, @ParentescoNom varchar(30), @Porcentaje int,
@InsertIn varchar(30), @outResultCode int output

AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		DECLARE 
		@PersonaId INT,
		@CuentaId INT,
		@ParentescoId INT,
		@TipoDocId INT

		SET @outResultCode = 0

		IF NOT EXISTS (SELECT ID FROM dbo.cuentaAhorro WHERE numeroCuenta = @CuentaNum)
			BEGIN
				SET @outResultCode = 50003
			END

		SET @TipoDocId = (SELECT ID FROM dbo.tipoDocIdent WHERE tipoDoc = @TipoDocIdent)

		INSERT INTO dbo.persona(
		nombre,
		email,
		nacimiento,
		telefono1,
		telefono2,
		tipoDocIdent,
		valorDocIdent,
		InsertBy,
		InsertAt,
		InsertIn)
		VALUES(
		@Nombre,
		@Correo,
		@FechaNacimiento,
		@Telefono1,
		@Telefono2,
		@TipoDocId,
		@PersonaDoc,
		SUSER_NAME(),
		GETDATE(),
		@InsertIn)

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
