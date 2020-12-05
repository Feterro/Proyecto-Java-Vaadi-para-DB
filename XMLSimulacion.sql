USE BDProyecto;

GO

SET NOCOUNT ON

DECLARE 
@fechasSimulacion date = '2020-11-29',
@fechaActual date = '2020-07-01'

DECLARE 
@lo1 int = 1,
@hi1 int = 1,
@lo3 int = 1,
@hi3 int = 1

WHILE(@fechaActual <= @fechasSimulacion)
BEGIN
	

--INSERT XML en persona-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-07-01'
	INSERT INTO dbo.persona 
	(nombre, 
	email,
	nacimiento,
	telefono1,
	telefono2,
	tipoDocIdent,
	valorDocIdent,
	insertBy,
	insertAt)

	SELECT 
	A.Persona.value('@Nombre', 'varchar(100)') AS nombre,
	A.Persona.value('@Email', 'varchar(50)') AS email,
	A.Persona.value('@FechaNacimiento', 'date') AS nacimiento,
	A.Persona.value('@Telefono1', 'int') AS tel1,
	A.Persona.value('@Telefono2', 'int') AS tel2,
	A.Persona.value('@TipoDocuIdentidad', 'int') AS tipoDocuIdentidad,
	A.Persona.value('@ValorDocumentoIdentidad', 'int') AS valorDocumentoIdentidad,
	SUSER_NAME(),
	@fechaActual
	FROM(
	SELECT CAST(c AS XML) FROM 
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaActual")]/Persona') AS A(Persona) 

	--SELECT * FROM dbo.persona

--INSERT XML en cuentaAhorro-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-07-01'

	DECLARE @cuentaTemp TABLE
	(ID int identity,
	tipoCuenta int,
	fechaApretura date,
	tempdocIdent int,
	numCuenta int,
	cantHum int,
	cantAuto int)

	INSERT INTO @cuentaTemp(
	tipoCuenta,
	fechaApretura,
	tempdocIdent,
	numCuenta)

	SELECT
		A.Cuenta.value('@TipoCuentaId', 'int') AS tipoCuentaId,
		@fechaActual,
		A.Cuenta.value('@ValorDocumentoIdentidadDelCliente', 'int') AS tempDocIdent,
		A.Cuenta.value('@NumeroCuenta', 'int') AS numCuenta
	FROM(
	SELECT CAST(c AS XML) FROM 
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaActual")]/Cuenta') AS A(Cuenta)

	UPDATE @cuentaTemp
	SET cantHum = dbo.tipoCuentaAhorro.maxOpeHum,
	cantAuto = dbo.tipoCuentaAhorro.maxOpeAuto
	FROM  @cuentaTemp
	INNER JOIN dbo.tipoCuentaAhorro ON tipoCuenta = dbo.tipoCuentaAhorro.ID

	UPDATE @cuentaTemp
	SET tempdocIdent = dbo.persona.ID
	FROM  @cuentaTemp
	INNER JOIN dbo.persona ON tempdocIdent = dbo.persona.valorDocIdent 

	INSERT INTO dbo.cuentaAhorro(
	tipoCuentaId,
	fechaApertura,
	personaId,
	numeroCuenta,
	cantHumano,
	cantAuto)

	SELECT tipoCuenta,
	fechaApretura, 
	tempdocIdent, 
	numCuenta, 
	cantHum, 
	cantAuto 
	FROM @cuentaTemp 

	DELETE FROM @cuentaTemp

	--SELECT * FROM dbo.cuentaAhorro

--INSERT XML en beneficiario-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-07-01'

	DECLARE @benefTemp TABLE
	(ID int identity,
	ParId int,
	tempdocIdent int,
	numCuentatemp int,
	porcentaje int,
	insertAt date, 
	insertBy varchar(50))
	
	INSERT INTO @benefTemp(ParId,
	tempdocIdent,
	numCuentatemp,
	porcentaje,
	insertAt,
	insertBy)
	SELECT
		A.Benef.value('@ParentezcoId', 'int') AS ParId,
		A.Benef.value('@ValorDocumentoIdentidadBeneficiario', 'int') AS tempDocIdent,
		A.Benef.value('@NumeroCuenta', 'int') AS numCuenta,
		A.Benef.value('@Porcentaje', 'int') AS porcentaje,
		@fechaActual,
		SUSER_NAME()
	FROM(
	SELECT CAST(c AS XML) FROM 
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaActual")]/Beneficiario') AS A(Benef)
	
	UPDATE @benefTemp
	SET tempdocIdent = dbo.persona.ID
	FROM  @benefTemp
	INNER JOIN dbo.persona ON tempdocIdent = dbo.persona.valorDocIdent

	INSERT INTO dbo.beneficiario(parentescoId,
	personaId,
	cuentaId,
	porcentaje,
	insertAt,
	insertBy)

	SELECT ParId, tempdocIdent, dbo.cuentaAhorro.ID, porcentaje, insertAt, insertBy FROM @benefTemp
	INNER JOIN dbo.cuentaAhorro ON numCuentatemp = dbo.cuentaAhorro.numeroCuenta

	DELETE FROM @benefTemp

	--SELECT * FROM dbo.beneficiario

--INSERT XML en Usuarios-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-07-01'

	DECLARE @userTemp TABLE
	(ID int identity,
	nomUsu varchar(50),
	pass varchar(50),
	docId int,
	esAdmin bit)
	
	INSERT INTO @userTemp(nomUsu,
	pass,
	docId,
	esAdmin)
	SELECT
		A.Usu.value('@User', 'varchar(50)') AS ParId,
		A.Usu.value('@Pass', 'varchar(50)') AS tempDocIdent,
		A.Usu.value('@ValorDocumentoIdentidad', 'int') AS numCuenta,
		A.Usu.value('@EsAdministrador', 'bit') AS porcentaje
	FROM(
	SELECT CAST(c AS XML) FROM 
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaActual")]/Usuario') AS A(Usu)

	INSERT INTO dbo.usuario(nombreUsuario,
	contrasenna,
	personaId,
	tipoUsuario)

	SELECT nomUsu, pass, dbo.persona.ID, esAdmin FROM @userTemp
	INNER JOIN dbo.persona ON docId = dbo.persona.valorDocIdent

	DELETE FROM @userTemp

	--SELECT * FROM dbo.usuario

--INSERT XML en puedeVer-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-07-01'

	DECLARE @verTemp TABLE
	(ID int identity,
	usu varchar(50),
	cuen int)
	
	INSERT INTO @verTemp(
	usu,
	cuen)
	SELECT
		A.Usu.value('@User', 'varchar(50)') AS ParId,
		A.Usu.value('@NumeroCuenta', 'int') AS tempDocIden
	FROM(
	SELECT CAST(c AS XML) FROM 
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaActual")]/UsuarioPuedeVer') AS A(Usu)

	UPDATE @verTemp
	SET cuen = dbo.cuentaAhorro.ID
	FROM  @verTemp
	INNER JOIN dbo.cuentaAhorro ON cuen = dbo.cuentaAhorro.numeroCuenta

	INSERT INTO dbo.puedeVer(usuarioId,
	cuentaAhorroId)

	SELECT dbo.usuario.ID, cuen FROM  @verTemp
	INNER JOIN dbo.usuario ON usu = dbo.usuario.nombreUsuario

	DELETE FROM @verTemp

	--SELECT * FROM dbo.puedeVer

--Procesamiento de Movimientos-----------------------------------------------------------
	
	--DECLARE @fechaActual date = '2020-07-01'

	DECLARE @movTemp TABLE
	(ID int identity,
	tipoMov int,
	cuentaAhorrotemp int,
	monto decimal(19,4),
	descripcion varchar(100),
	debito varchar(20),
	fechaMov date)

	INSERT INTO @movTemp(tipoMov,
	cuentaAhorrotemp,
	monto,
	descripcion,
	fechaMov)
	SELECT
		A.Mov.value('@Tipo', 'int') AS ParId,
		A.Mov.value('@CodigoCuenta', 'int') AS tempDocIdent,
		A.Mov.value('@Monto', 'decimal(19,4)') AS numCuenta,
		A.Mov.value('@Descripcion', 'varchar(100)') AS porcentaje,
		@fechaActual
	FROM(
	SELECT CAST(c AS XML) FROM 
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaActual")]/Movimientos') AS A(Mov)

	UPDATE @movTemp
	SET cuentaAhorrotemp = dbo.cuentaAhorro.ID
	FROM  @movTemp
	INNER JOIN dbo.cuentaAhorro ON cuentaAhorrotemp = dbo.cuentaAhorro.numeroCuenta

	UPDATE @movTemp
	SET debito = tipo
	FROM  @movTemp
	INNER JOIN dbo.TipoMovimiento ON tipoMov = dbo.TipoMovimiento.Id
	
	DECLARE   
	@montoDepo decimal(19,4),
	@esDebito varchar(30),
	@tipoMov int,
	@cuentaPertenece int,
	@saldoIncumplido decimal(19,4),
	@tipCue int,
	@salMin decimal(19,4),
	@multaIncum decimal(19,4),
	@incum bit = 0

	SET @hi1 = (SELECT COUNT(*) FROM @movTemp) + @lo1-1

	WHILE(@lo1<=@hi1)
	BEGIN

		SET @montoDepo = (SELECT monto FROM @movTemp WHERE ID = @lo1)
		SET @cuentaPertenece = (SELECT cuentaAhorrotemp FROM @movTemp WHERE ID = @lo1)
		SET @esDebito = (SELECT debito FROM @movTemp WHERE ID =  @lo1)
		SET @tipoMov = (SELECT tipoMov FROM @movTemp WHERE ID =  @lo1)


		IF @esDebito = 'Debito'
		BEGIN
			UPDATE dbo.cuentaAhorro
			SET saldo = saldo - @montoDepo
			WHERE ID = @cuentaPertenece;
		END

		ELSE
		BEGIN
			UPDATE dbo.cuentaAhorro
			SET saldo = saldo + @montoDepo
			WHERE ID = @cuentaPertenece;
		END

		IF @tipoMov = 2
		BEGIN
			UPDATE dbo.cuentaAhorro
			SET cantAuto = cantAuto - 1
			WHERE ID = @cuentaPertenece;
		END
		
		IF @tipoMov = 3
		BEGIN
			UPDATE dbo.cuentaAhorro
			SET cantHumano = cantHumano - 1
			WHERE ID = @cuentaPertenece;
		END

		SET @saldoIncumplido = (SELECT saldo FROM dbo.cuentaAhorro WHERE ID = @cuentaPertenece);
		SET @tipCue = (SELECT tipoCuentaId FROM dbo.cuentaAhorro WHERE ID = @cuentaPertenece);
		SET @salMin = (SELECT saldMin FROM dbo.tipoCuentaAhorro WHERE ID = @tipCue);
		SET @multaIncum = (SELECT saldMin FROM dbo.tipoCuentaAhorro WHERE ID = @tipCue);
		SET @incum = (SELECT incumpleSalMin FROM dbo.cuentaAhorro WHERE ID = @cuentaPertenece)


		IF (@saldoIncumplido < @salMin) AND (@incum = 0)
		BEGIN
			UPDATE dbo.cuentaAhorro
			SET incumpleSalMin = 1
			WHERE ID = @cuentaPertenece;
		END
	

		SET @lo1 = @lo1 + 1
	END

	SET @lo1 = @hi1+1

	INSERT INTO dbo.movimiento(tipoMovimientoId,
	cuentaAhorroId,
	monto,
	descripcion,
	fechaMovimiento)

	SELECT tipoMov, cuentaAhorrotemp, monto, descripcion, fechaMov FROM @movTemp

	DELETE FROM @movTemp

	--SELECT * FROM dbo.movimiento

--Procesamiento de Estados de Cuenta---------------------------------------------

	IF EXISTS(SELECT ID, tipoCuentaId FROM dbo.cuentaAhorro
	WHERE DAY(fechaApertura) = (DAY(DATEADD(DAY,1,@fechaActual))))
	BEGIN

		DECLARE @cuentasCierre TABLE (
		ID INT IDENTITY NOT NULL,
		cuentaId INT NOT NULL,
		tipoCue int NOT NULL,
		hizoHum int,
		hizoAuto int,
		incumplio bit,
		multHum decimal(19,4),
		multAuto decimal(19,4),
		multMin decimal(19,4),
		inter decimal(19,4),
		mensMontServ decimal(19,4))


		INSERT INTO @cuentasCierre(
		cuentaId,
		tipoCue,
		hizoHum,
		hizoAuto,
		incumplio)

		SELECT ID, tipoCuentaId,cantHumano,cantAuto,incumpleSalMin FROM dbo.cuentaAhorro
		WHERE DAY(fechaApertura) = (DAY(DATEADD(DAY,1,@fechaActual))) 

		UPDATE @cuentasCierre
		SET multHum = montoComHum,
		multAuto = montoComAuto,
		multMin = multSalMin,
		inter = tasaIntAnu,
		mensMontServ = montMensCargServ
		FROM  @cuentasCierre
		INNER JOIN dbo.tipoCuentaAhorro ON tipoCue = dbo.tipoCuentaAhorro.ID


		DECLARE 
		@IdBus int,
		@salFin decimal(19, 4),
		@tipoCuent int,
		@hizoHum int,
		@hizoAuto int,
		@incumplio bit,
		@multHum decimal(19,4),
		@multAuto decimal(19,4),
		@multIncum decimal(19,4),
		@inter decimal(19,4),
		@mensCargServ decimal(19,4)

		SET @hi3 = (SELECT COUNT(*) FROM @cuentasCierre) + @lo3 - 1

		WHILE (@lo3 <= @hi3)
		BEGIN

			SET @IdBus = (SELECT cuentaId FROM @cuentasCierre WHERE ID = @lo3)
			SET @salFin = (SELECT saldo FROM dbo.cuentaAhorro WHERE ID = @IdBus)
			SET @hizoHum = (SELECT hizoHum FROM @cuentasCierre WHERE ID = @lo3)
			SET @hizoAuto = (SELECT hizoAuto FROM @cuentasCierre WHERE ID = @lo3)
			SET @incumplio = (SELECT incumplio FROM @cuentasCierre WHERE ID = @lo3)
			SET @multHum = (SELECT multHum FROM @cuentasCierre WHERE ID = @lo3)
			SET @multAuto = (SELECT multAuto FROM @cuentasCierre WHERE ID = @lo3)
			SET @multIncum = (SELECT multMin FROM @cuentasCierre WHERE ID = @lo3)
			SET @mensCargServ = (SELECT mensMontServ FROM @cuentasCierre WHERE ID = @lo3)
			SET @inter = (SELECT inter FROM @cuentasCierre WHERE ID = @lo3)

			IF @hizoAuto < 0
			BEGIN
				INSERT INTO dbo.movimiento(
				tipoMovimientoId,
				cuentaAhorroId,
				fechaMovimiento,
				descripcion,
				monto)
				VALUES(
				9,
				@IdBus,
				@fechaActual,
				'Multa por sobrepaso en cantidad de operaciones en cajero automatico',
				@multAuto)
				UPDATE dbo.cuentaAhorro SET
				saldo = saldo - @multAuto WHERE
				ID = @IdBus
			END

			IF @hizoHum < 0
			BEGIN
				INSERT INTO dbo.movimiento(
				tipoMovimientoId,
				cuentaAhorroId,
				fechaMovimiento,
				descripcion,
				monto)
				VALUES(
				8,
				@IdBus,
				@fechaActual,
				'Multa por sobrepaso en cantidad de operaciones en ventanilla',
				@multHum)
				UPDATE dbo.cuentaAhorro SET
				saldo = saldo - @multHum WHERE
				ID = @IdBus
			END

			IF @incumplio = 1
			BEGIN
				UPDATE dbo.cuentaAhorro SET
				saldo = saldo - @multIncum WHERE
				ID = @IdBus
			END

			UPDATE dbo.cuentaAhorro SET
			saldo = saldo + (@inter/12) WHERE
			ID = @IdBus
			
			INSERT INTO dbo.movimiento(
			tipoMovimientoId,
			cuentaAhorroId,
			fechaMovimiento,
			descripcion,
			monto)
			VALUES(
			7,
			@IdBus,
			@fechaActual,
			'Intereses',
			@multHum)--CAMBIAR!!!!!!!

			UPDATE dbo.cuentaAhorro SET
			saldo = saldo - @mensCargServ WHERE
			ID = @IdBus

			UPDATE dbo.estadoCuenta SET
			fechaFin = @fechaActual,
			saldoFin = @salFin WHERE
			cuentaAhorroId = @IdBus AND fechaFin IS NULL

			INSERT INTO dbo.estadoCuenta(
			fechaIni,
			cuentaAhorroId,
			saldoIni,
			saldoFin)
			VALUES(
			DATEADD(DAY,1,@fechaActual),
			@IdBus,
			@salFin,
			@salFin)

			SET @lo3 = @lo3 + 1

			IF @lo3 > @hi3
				SET @lo3 = @hi3+1
		END
		DELETE FROM @cuentasCierre
	--SELECT * FROM dbo.estadoCuenta
	END
	SET @fechaActual = (SELECT(DATEADD(DAY,1,@fechaActual)))

END


--Delete FROM dbo.estadoCuenta
--DBCC checkident('dbo.estadoCuenta',reseed,0)
--Delete FROM dbo.movimiento
--DBCC checkident('dbo.movimiento',reseed,0)
--Delete FROM dbo.puedeVer
--DBCC checkident('dbo.puedeVer',reseed,0)
--Delete FROM dbo.usuario
--DBCC checkident('dbo.usuario',reseed,0)
--Delete FROM dbo.beneficiario
--DBCC checkident('dbo.beneficiario',reseed,0)
--DELETE FROM dbo.cuentaAhorro
--DBCC checkident('dbo.cuentaAhorro',reseed,0)
--DELETE FROM dbo.persona
--DBCC checkident('dbo.persona',reseed,0)

