USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_BE_EliminarBeneficiario]    Script Date: 14/11/2020 15:05:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_BE_EliminarBeneficiario] @BeneficiarioDocIdent int,  @outResultCode int output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		DECLARE 
		@PersonaId INT	

		SET @outResultCode = 0

		SET @PersonaId = (SELECT ID FROM dbo.persona WHERE valorDocIdent = @BeneficiarioDocIdent)
		

		UPDATE dbo.beneficiario
		SET activo = 0, fechaDesactivacion = GETDATE()
		FROM dbo.beneficiario WHERE personaId = @PersonaId; 



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