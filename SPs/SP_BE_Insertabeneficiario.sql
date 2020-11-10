USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_PA_SolicitaParentezcos]    Script Date: 11/8/2020 6:36:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_BE_InsertaBeneficiario] @PersonaId int, @CuentaId int, @ParentescoId int, @Porcentaje int, @InsertAt date, @outResultCode varchar output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		SET @outResultCode = 0
		IF NOT EXISTS (SELECT ID FROM dbo.persona WHERE valorDocIdent = @PersonaId) 
			BEGIN
				SET @outResultCode = 50002
			END
		IF NOT EXISTS (SELECT ID FROM dbo.cuentaAhorro WHERE numeroCuenta = @CuentaId)
			BEGIN
				SET @outResultCode = 50002;
			END

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
		SET @outResultCode='50001';

	END CATCH

SET NOCOUNT OFF
END