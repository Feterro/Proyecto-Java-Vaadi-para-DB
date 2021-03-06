USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_AD_Consulta3]    Script Date: 19/01/2021 15:50:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_AD_Consulta3] @outResultCode int output
AS

BEGIN

SET NOCOUNT ON

	BEGIN TRY

		DECLARE
			@contador int,
			@totalBen  int,
			@nombreB varchar(50),
			@ced int,
			@canDinero decimal(19,4),
			@masPor decimal(19,4),
			@cantCuentas int,
			@totalCuenta decimal(19,4),
			@porcentaje int,
			@dineroBen decimal(19,4),
			@porceComparar decimal(5,4),
			@cedulaActual int,
			@cuentaID int,
			@personaID int
		
		DECLARE @consulta TABLE(
			ID int IDENTITY,
			nombre varchar(50),
			cedula int,
			cantDineroRec decimal(19,4),
			numCuentaMas int,
			porcentajeMayor int,
			cantCuentasBen int
		)

		SET @contador = 1
		SET @totalBen = (SELECT COUNT(*) FROM dbo.beneficiario)

		WHILE(@contador <= @totalBen)
		BEGIN

			SET @personaID = (SELECT personaId FROM dbo.beneficiario WHERE ID = @contador)
			SET @cedulaActual = (SELECT valorDocIdent FROM dbo.persona WHERE ID = @personaID)	
			SET @cuentaID = (SELECT cuentaId FROM dbo.beneficiario WHERE ID = @contador)
			SET @totalCuenta = (SELECT saldo FROM dbo.cuentaAhorro WHERE ID = @cuentaID)
			SET @porcentaje = (SELECT porcentaje FROM dbo.beneficiario WHERE ID = @contador)
			SET @dineroBen = (@totalCuenta*@porcentaje)/100

			IF ((SELECT COUNT(*) FROM @consulta)= 0)
			BEGIN 
				INSERT INTO @consulta(
					nombre, 
					cedula
				)

				SELECT nombre, valorDocIdent FROM dbo.persona WHERE ID = @personaID

				UPDATE @consulta
				SET cantCuentasBen = 1,
				cantDineroRec = @dineroBen,
				numCuentaMas = (SELECT numeroCuenta FROM dbo.cuentaAhorro WHERE ID = @cuentaID),
				porcentajeMayor = @porcentaje
				WHERE cedula = @cedulaActual

			END
			ELSE 
			BEGIN
			
				IF NOT EXISTS(SELECT cedula FROM @consulta WHERE cedula = @cedulaActual) 
				BEGIN
					
					INSERT INTO @consulta(
						nombre, 
						cedula
					)

					SELECT nombre, valorDocIdent FROM dbo.persona WHERE ID = @personaID

					UPDATE @consulta
					SET cantCuentasBen = 1,
					cantDineroRec = @dineroBen,
					numCuentaMas = (SELECT numeroCuenta FROM dbo.cuentaAhorro WHERE ID = @cuentaID), 
					porcentajeMayor = @porcentaje
					WHERE cedula = @cedulaActual

				END

				ELSE
				BEGIN
					DECLARE 
						@actual int,
						@numCuentaMas int

					SET @actual = (SELECT porcentajeMayor FROM @consulta WHERE cedula = @cedulaActual)
					
					IF ( @actual > @porcentaje)
					BEGIN
						SET @porcentaje = @actual
						SET @numCuentaMas = (SELECT numCuentaMas FROM @consulta WHERE cedula = @cedulaActual)
					END
					ELSE
					BEGIN
						SET @numCuentaMas = (SELECT numeroCuenta FROM dbo.cuentaAhorro WHERE ID = @cuentaID)
					END
					
					UPDATE @consulta
					SET
					cantCuentasBen = cantCuentasBen + 1,
					cantDineroRec = cantDineroRec + @dineroBen,
					porcentajeMayor = @porcentaje,
					numCuentaMas = @numCuentaMas
					WHERE cedula = @cedulaActual

				END
			END

			SET @contador = @contador + 1
		
		END

		SELECT * FROM @consulta
		
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
