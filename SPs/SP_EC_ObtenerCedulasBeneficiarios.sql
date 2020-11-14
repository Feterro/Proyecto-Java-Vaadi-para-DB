USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_EC_ObtenerCedulasBeneficiarios]    Script Date: 14/11/2020 15:06:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_EC_ObtenerCedulasBeneficiarios] @cuenta int, @salida varchar output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY

	DECLARE 
	@CuentaID int


		SET @CuentaID = (SELECT (ID) FROM dbo.cuentaAhorro WHERE numeroCuenta = @cuenta)
		SELECT (valorDocIdent) FROM dbo.persona WHERE ID in (SELECT (personaId) FROM dbo.beneficiario WHERE cuentaId = @CuentaID)

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
		SET @salida='50001';

	END CATCH

SET NOCOUNT OFF
END
