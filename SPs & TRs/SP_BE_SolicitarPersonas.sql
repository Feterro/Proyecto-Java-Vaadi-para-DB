USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_BE_SolicitarPersonas]    Script Date: 11/29/2020 8:29:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_BE_SolicitarPersonas] @DocIdent int, @outResultCode int output

AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		DECLARE
		@JoinPersonasBeneficiarios TABLE( 
		nombre int,
		correo varchar(50),
		fechaNacimiento date,
		telefono1 int,
		telefono2 int,
		valorDoc int,
		porcentaje int)

		DECLARE
		@ParentescoId INT,
		@TipoDocId INT,
		@PersonaId INT

		SET @outResultCode = 0

		SELECT nombre, email, nacimiento, telefono1, telefono2, valorDocIdent, porcentaje FROM dbo.persona
		INNER JOIN dbo.beneficiario ON dbo.persona.ID = dbo.beneficiario.personaId WHERE valorDocIdent = @DocIdent; 
		



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
