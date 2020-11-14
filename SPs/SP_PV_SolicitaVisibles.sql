USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_PA_SolicitaParentezcos]    Script Date: 11/13/2020 5:58:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_PV_SolicitaVisibles] @NomUser varchar(50), @outResultCode varchar output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY

		DECLARE 
		@nomUsuario varchar(50),
		@numCuenta int


		SET @nomUsuario = (SELECT(ID) FROM dbo.usuario WHERE nombreUsuario = @NomUser)

		SELECT (numeroCuenta) FROM dbo.cuentaAhorro WHERE ID in (

		SELECT(cuentaAhorroId) FROM dbo.puedeVer WHERE usuarioId = @nomUsuario)

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