USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_PV_SolicitaVisibles]    Script Date: 11/14/2020 7:02:07 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[SP_US_SolicitaContrasenna] @NomUser varchar(50), @outResultCode varchar output
AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY

		DECLARE 
		@nomUsuario varchar(50),
		@numCuenta int


		SET @outResultCode = '0'

		IF NOT EXISTS (SELECT ID FROM dbo.usuario WHERE nombreUsuario = @NomUser)
			BEGIN
				SET @outResultCode = 50003
			END

		SELECT contrasenna FROM dbo.usuario where nombreUsuario = @NomUser

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
		SELECT @outResultCode='50001';

	END CATCH

SET NOCOUNT OFF
END