USE [BDProyecto]
GO
/****** Object:  StoredProcedure [dbo].[SP_BE_SolicitarPersonas]    Script Date: 12/01/2021 06:28:24 a. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_AD_Consulta2] @incantDias int, @outResultCode int

AS
BEGIN
SET NOCOUNT ON
	BEGIN TRY
		
		DECLARE
		@cuentasMultadas TABLE(
		ID int IDENTITY,
		cuentaId int,
		valida bit,
		cantidadMov decimal(4, 2),
		promedio decimal(4, 2),
		mayorCantR int, 
		mes int, 
		ano int)

		DECLARE @movPorCuenta TABLE(
		ID int IDENTITY,
		fechaOpe date,
		idCuen int)

		DECLARE
		@incantiDias int = 5,
		@fechaActual date,
		@idCuentaActual int,
		@numeroMov int = 0,
		@lo int = 1,
		@hi int = 1,
		@lo2 int = 1,
		@hi2 int = 1,
		@lo3 int = 1,
		@hi3 int = 1,
		@mesRev int = 13,
		@mesNuev int,
		@cantMeses decimal(4, 2) = 0,
		@cuentProm int,
		@activoProm bit,
		@cantMov decimal(4, 2),
		@cantMovMes int = 0,
		@anoMayor int,
		@mesMayor int

		INSERT INTO @cuentasMultadas
		(cuentaId)
		SELECT ID FROM dbo.cuentaAhorro 
		WHERE multado = 1

		UPDATE @cuentasMultadas SET valida = 1,
		mayorCantR = 0, 
		mes = 0,
		ano = 0

		SET @hi = (SELECT COUNT(*) FROM @cuentasMultadas)

		WHILE @lo <= @hi
		BEGIN
			
			SET @numeroMov = 0
			SET @fechaActual = '1-1-2021'
			SET @incantiDias = 5
			SET @idCuentaActual = (SELECT cuentaId FROM @cuentasMultadas WHERE ID = @lo)

			WHILE @incantiDias > 0
			BEGIN
				SET @fechaActual = DATEADD(DAY, -1, @fechaActual)
				SET @numeroMov = @numeroMov + (SELECT COUNT(*) FROM movimiento WHERE cuentaAhorroId = @idCuentaActual AND tipoMovimientoId = 2 AND fechaMovimiento = @fechaActual)
				SET @incantiDias = @incantiDias - 1
			END

			
			IF(@numeroMov < 5)
			BEGIN

				UPDATE @cuentasMultadas
				SET valida = 0
				WHERE ID = @lo

			END
			SET @lo = @lo+1
		END

		SET @hi2 = (SELECT COUNT(*) FROM @cuentasMultadas)

		WHILE @lo2 <= @hi2
		BEGIN

			SET @activoProm = (SELECT valida FROM @cuentasMultadas WHERE ID = @lo2)

			IF @activoProm = 1
			BEGIN

				SET @cuentProm = (SELECT cuentaId FROM @cuentasMultadas WHERE ID = @lo2)

				INSERT INTO @movPorCuenta
				SELECT fechaMovimiento, cuentaAhorroId FROM dbo.movimiento
				WHERE cuentaAhorroId = @cuentProm AND tipoMovimientoId = 2


				UPDATE @cuentasMultadas
				SET cantidadMov = (SELECT COUNT(*) FROM @movPorCuenta)
				WHERE ID = @lo2

				SET @cantMov = (SELECT cantidadMov FROM @cuentasMultadas WHERE ID = @lo2)

				SET @hi3 = (SELECT COUNT(*) FROM @movPorCuenta) + @lo3 - 1

				WHILE @lo3 <= @hi3
				BEGIN
					SET @cantMovMes = @cantMovMes + 1
					PRINT @cantMovMes
					
					SET @mesNuev = MONTH((SELECT fechaOpe FROM @movPorCuenta WHERE ID = @lo3))
					SET @mesMayor = @mesRev
					SET @anoMayor = YEAR((SELECT fechaOpe FROM @movPorCuenta WHERE ID = @lo3))

			
					IF @mesNuev != @mesRev
					BEGIN
			
						IF @mesRev != 13
						BEGIN
							IF ((SELECT mayorCantR FROM @cuentasMultadas WHERE ID = @lo2) > @cantMovMes-1)
							BEGIN
								SET @cantMovMes = (SELECT mayorCantR FROM @cuentasMultadas WHERE ID = @lo2)+1
								SET @mesMayor = (SELECT mes FROM @cuentasMultadas WHERE ID = @lo2)
								SET @anoMayor = (SELECT ano FROM @cuentasMultadas WHERE ID = @lo2)
							END

							UPDATE @cuentasMultadas
							SET mayorCantR = @cantMovMes-1,
							mes = @mesMayor,
							ano = @anoMayor
							WHERE ID  = @lo2
						END

						SET @cantMovMes = 1
						SET @mesRev = @mesNuev
						SET @cantMeses = @cantMeses + 1

					END

					SET @lo3 = @lo3 + 1
				END
				SET @lo3 = @hi3+1

				IF ((SELECT mayorCantR FROM @cuentasMultadas WHERE ID = @lo2) > @cantMovMes)
					BEGIN
						SET @cantMovMes = (SELECT mayorCantR FROM @cuentasMultadas WHERE ID = @lo2)
						SET @mesMayor = (SELECT mes FROM @cuentasMultadas WHERE ID = @lo2)
						SET @anoMayor = (SELECT ano FROM @cuentasMultadas WHERE ID = @lo2)
					END

				UPDATE @cuentasMultadas
				SET mayorCantR = @cantMovMes,
				mes = @mesMayor,
				ano = @anoMayor
				WHERE ID  = @lo2

				UPDATE @cuentasMultadas
				SET promedio = @cantMov/@cantMeses
				WHERE ID = @lo2

				SET @mesRev = 13
				SET @cantMeses = 0
				SET @cantMovMes = 0
				DELETE FROM @movPorCuenta

			END

			SET @lo2 = @lo2+1
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
		SET @outResultCode = 50001

	END CATCH

	SELECT @outResultCode AS N

SET NOCOUNT OFF
END

--INSERT INTO dbo.cuentaAhorro VALUES(1, '2020-08-01', 1, 7777777, -1, -1, 100, 1)
--INSERT INTO dbo.cuentaAhorro VALUES(2, '2020-08-01', 1, 8888888, -1, -1, 200, 1)
--INSERT INTO dbo.cuentaAhorro VALUES(3, '2020-08-01', 1, 9999999, -1, -1, 300, 1)
--INSERT INTO dbo.cuentaAhorro VALUES(1, '2020-08-01', 1, 6666666, -1, -1, 100, 1)
--INSERT INTO dbo.cuentaAhorro VALUES(2, '2020-08-01', 1, 1111111, -1, -1, 200, 1)
--INSERT INTO dbo.cuentaAhorro VALUES(3, '2020-08-01', 1, 2222222, -1, -1, 300, 1)
--INSERT INTO dbo.cuentaAhorro VALUES(1, '2020-08-01', 1, 3333333, -1, -1, 100, 1)
--INSERT INTO dbo.cuentaAhorro VALUES(2, '2020-08-01', 1, 4444444, -1, -1, 200, 1)
--INSERT INTO dbo.cuentaAhorro VALUES(3, '2020-08-01', 1, 5555555, -1, -1, 300, 1)
--INSERT INTO dbo.cuentaAhorro VALUES(1, '2020-08-01', 1, 1212121, -1, -1, 300, 1)

--INSERT INTO dbo.movimiento VALUES (2, 32, '12-31-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 32, '12-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 32, '12-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 32, '12-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 32, '12-27-2020', 'prueba', 0)

--INSERT INTO dbo.movimiento VALUES (2, 33, '12-31-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 33, '12-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 33, '12-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 33, '12-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 33, '12-27-2020', 'prueba', 0)

--INSERT INTO dbo.movimiento VALUES (2, 34, '12-31-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 34, '12-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 34, '12-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 34, '12-28-2020', 'prueba', 0)

--INSERT INTO dbo.movimiento VALUES (2, 35, '9-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '9-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '10-26-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '10-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '10-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '11-25-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '11-26-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '11-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '11-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '11-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '11-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '12-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '12-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '12-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '12-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 35, '12-31-2020', 'prueba', 0)

--INSERT INTO dbo.movimiento VALUES (2, 36, '8-25-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '8-26-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '8-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '8-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '8-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '8-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '9-25-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '10-26-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '10-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '11-24-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '11-25-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '11-26-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '11-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '12-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '12-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '12-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '12-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 36, '12-31-2020', 'prueba', 0)

--INSERT INTO dbo.movimiento VALUES (2, 37, '12-31-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 37, '12-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 37, '12-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 37, '12-28-2020', 'prueba', 0)

--INSERT INTO dbo.movimiento VALUES (2, 38, '9-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '10-25-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '11-26-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '12-22-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '12-23-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '12-24-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '12-25-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '12-26-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '12-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '12-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '12-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '12-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 38, '12-31-2020', 'prueba', 0)

--INSERT INTO dbo.movimiento VALUES (2, 39, '10-20-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '10-21-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '10-22-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '10-23-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '10-24-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '10-25-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '10-26-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '10-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '12-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '12-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '12-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '12-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 39, '12-31-2020', 'prueba', 0)

--INSERT INTO dbo.movimiento VALUES (2, 40, '9-25-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '9-26-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '9-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '9-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '9-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '9-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '10-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '11-25-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '11-26-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '11-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '12-27-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '12-28-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '12-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '12-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 40, '12-31-2020', 'prueba', 0)

--INSERT INTO dbo.movimiento VALUES (2, 41, '12-31-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 41, '12-30-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 41, '12-29-2020', 'prueba', 0)
--INSERT INTO dbo.movimiento VALUES (2, 41, '12-28-2020', 'prueba', 0)

