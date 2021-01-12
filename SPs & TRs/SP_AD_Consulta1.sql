USE [BDProyecto]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_AD_Consulta1] @outResultCode int output
AS

BEGIN

SET NOCOUNT ON

	BEGIN TRY
		DECLARE 
			@interes decimal(19,4), 
			@fechaInicio date,
			@fechaFinal date,
			@contador int, 
			@totalTabla int,
			@interesAcumulado decimal(19,4), 
			@cuota decimal(19,4),
			@cantRDep int

		DECLARE @consulta TABLE(
			ID int IDENTITY, --listo
			codigo int NOT NULL, --listo
			IDCO int NOT NULL,  --listo
			descripcion varchar(50) NOT NULL, --listo
			cantDepR int, -- listo
			cantDepH int, --listo
			totalR decimal(19,4), -- listo 
			totalH decimal(19,4), --listo
			interAcuR decimal(19,4), -- listo
			interAcuH decimal(19,4) --listo
		)

		INSERT INTO @consulta(
			codigo,
			IDCO,
			descripcion, 
			totalR, 
			interAcuR
		)

		SELECT numeroCuenta, id, objetivo, saldoQueHubo, interesQueHubo FROM dbo.CuentaObjetivo WHERE fallo = 1 
		
		SET @contador = 1
		SET @totalTabla = (SELECT COUNT(*) FROM @consulta)
		
		WHILE (@contador <= @totalTabla)
		BEGIN

			DECLARE 
				@cantDep int,
				@saldo decimal(19,4),
				@fechaActual date

			SET @fechaInicio = (SELECT fechaIn FROM dbo.CuentaObjetivo WHERE ID = (SELECT IDCO FROM @consulta WHERE ID = @contador))
			SET @fechaFinal = (SELECT fechaFin FROM dbo.CuentaObjetivo WHERE ID = (SELECT IDCO FROM @consulta WHERE ID = @contador))
			SET @interes = (SELECT tasaInteres FROM dbo.CuentaObjetivo WHERE ID = (SELECT IDCO FROM @consulta WHERE ID = @contador))
			SET @interes = @interes / 365
			SET @cuota = (SELECT cuota FROM dbo.CuentaObjetivo WHERE ID = (SELECT IDCO FROM @consulta WHERE ID = @contador))
			SET @cantDep = 1
			SET @interesAcumulado = 0
			SET @saldo = @cuota
			
			SET @fechaActual = @fechaInicio
			WHILE (@fechaActual <= @fechaFinal)
			BEGIN 
			
				SET @interesAcumulado = @interesAcumulado + ((@saldo*@interes)/100)
				
				SET @fechaActual = (SELECT(DATEADD(DAY,1,@fechaActual)))
				
				IF (@fechaActual = (SELECT(DATEADD(MONTH, 1, @fechaInicio))))
				BEGIN

					SET @fechaInicio = (SELECT(DATEADD(MONTH, 1, @fechaInicio)))
					SET @saldo = @saldo + @cuota
					SET @cantDep = @cantDep +1
				
				END
			
			END
			
			SET @saldo = (SELECT saldoQueHubo FROM [dbo].[CuentaObjetivo] WHERE id = (SELECT IDCO FROM @consulta WHERE ID = @contador))
			SET @cantRDep = @saldo / @cuota; 
			
			UPDATE @consulta 
			SET interAcuH = @interesAcumulado, 
			cantDepR = @cantRDep, 
			cantDepH = @cantDep, 
			totalH = @cantDep * @cuota
			WHERE ID = @contador 

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