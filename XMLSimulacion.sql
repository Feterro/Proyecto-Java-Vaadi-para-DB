USE BDProyecto;

GO

SET NOCOUNT ON

DECLARE 
@fechasSimulacion date = '2020-11-29',
@fechaActual date = '2020-07-01'

WHILE (@fechaActual <= @fechasSimulacion)
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
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2 _Pruebas.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaActual")]/Persona') AS A(Persona) 

	--SELECT * FROM dbo.persona
	--DELETE FROM [dbo].[persona]
	--DBCC checkident('dbo.persona',reseed,0)

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

	INSERT INTO @cuentaTemp(tipoCuenta,
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
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2 _Pruebas.xml', SINGLE_BLOB) AS T(c)
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

	DECLARE @lo2 int = 1, 
	@hi2 int = (SELECT COUNT(*) FROM @cuentaTemp)

	WHILE (@lo2 <= @hi2)
	BEGIN

		INSERT INTO dbo.cuentaAhorro (tipoCuentaId,
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
		WHERE @lo2 = ID

		SET @lo2 = @lo2 + 1
	END

	--SELECT * FROM dbo.cuentaAhorro
	--DELETE FROM dbo.cuentaAhorro
	--DBCC checkident('dbo.cuentaAhorro',reseed,0)

--INSERT XML en beneficiario-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-07-01'

	DECLARE @benefTemp TABLE
	(ID int identity,
	ParId int,
	tempdocIdent int,
	numCuentatemp int,
	porcentaje int)
	
	INSERT INTO @benefTemp(ParId,
	tempdocIdent,
	numCuentatemp,
	porcentaje)
	SELECT
		A.Benef.value('@ParentezcoId', 'int') AS ParId,
		A.Benef.value('@ValorDocumentoIdentidadBeneficiario', 'int') AS tempDocIdent,
		A.Benef.value('@NumeroCuenta', 'int') AS numCuenta,
		A.Benef.value('@Porcentaje', 'int') AS porcentaje
	FROM(
	SELECT CAST(c AS XML) FROM 
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2 _Pruebas.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaActual")]/Beneficiario') AS A(Benef)

	DECLARE @benefTemp1 TABLE
	(ID int identity,
	ParId int,
	tempdocIdent int,
	numCuentatemp int,
	porcentaje int,
	insertBy varchar(50),
	insertAt date)

	INSERT INTO @benefTemp1(ParId,
	tempdocIdent,
	numCuentatemp,
	porcentaje)

	SELECT ParId, dbo.persona.ID, numCuentatemp, porcentaje FROM @benefTemp
	INNER JOIN dbo.persona ON tempdocIdent = dbo.persona.valorDocIdent

	INSERT INTO dbo.beneficiario(parentescoId,
	personaId,
	cuentaId,
	porcentaje)

	SELECT ParId, tempdocIdent, dbo.cuentaAhorro.ID, porcentaje FROM @benefTemp1
	INNER JOIN dbo.cuentaAhorro ON numCuentatemp = dbo.cuentaAhorro.numeroCuenta

	--SELECT * FROM dbo.beneficiario
	--Delete FROM dbo.beneficiario
	--DBCC checkident('dbo.beneficiario',reseed,0)

--INSERT XML en Usuarios-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-07-04'

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

	SELECT nomUsu, pass, valorDocIdent, esAdmin FROM @userTemp
	INNER JOIN dbo.persona ON docId = dbo.persona.valorDocIdent


	--SELECT * FROM dbo.beneficiario
	--Delete FROM dbo.beneficiario
	--DBCC checkident('dbo.beneficiario',reseed,0)

--INSERT XML en puedeVer-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-07-04'

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

	INSERT INTO dbo.usuario(nombreUsuario,
	contrasenna,
	personaId,
	tipoUsuario)

	SELECT dbo.usuario.nombreUsuario, cuen FROM  @verTemp
	INNER JOIN dbo.usuario ON usu = dbo.usuario.nombreUsuario


	--SELECT * FROM dbo.beneficiario
	--Delete FROM dbo.beneficiario
	--DBCC checkident('dbo.beneficiario',reseed,0)

--Procesamiento de Movimientos-----------------------------------------------------------
	
	--DECLARE @fechaActual date = '2020-07-01'

	DECLARE @movTemp TABLE
	(ID int identity,
	tipoMov int,
	cuentaAhorrotemp int,
	monto decimal(12,4),
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
		A.Mov.value('@Monto', 'decimal(12,4)') AS numCuenta,
		A.Mov.value('@Descripcion', 'varchar(100)') AS porcentaje,
		@fechaActual
	FROM(
	SELECT CAST(c AS XML) FROM 
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2 _Pruebas.xml', SINGLE_BLOB) AS T(c)
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
	@lo1 int = 1, 
	@hi1 int = (SELECT COUNT(*) FROM @movTemp),
	@montoDepo decimal(12,4),
	@esDebito varchar(30),
	@tipoMov int,
	@cuentaPertenece int,
	@saldoIncumplido decimal(12,4),
	@tipCue int,
	@salMin decimal(12,4)


	WHILE(@lo1<=@hi1)
	BEGIN

		SET @montoDepo = (SELECT monto FROM @movTemp WHERE ID = @lo1)
		SET @cuentaPertenece = (SELECT cuentaAhorrotemp FROM @movTemp WHERE ID = @lo1)
		SET @esDebito = (SELECT debito FROM @movTemp WHERE ID =  @lo1)
		SET @tipoMov = (SELECT tipoMov FROM @movTemp WHERE ID =  @lo1)


		IF @esDebito = 'Debito'
			UPDATE dbo.cuentaAhorro
			SET saldo = saldo - @montoDepo
			WHERE ID = @cuentaPertenece;

		ELSE
			UPDATE dbo.cuentaAhorro
			SET saldo = saldo + @montoDepo
			WHERE ID = @cuentaPertenece;

		IF @tipoMov = 2
			UPDATE dbo.cuentaAhorro
			SET cantAuto = cantAuto - 1
			WHERE ID = @cuentaPertenece;
		
		IF @tipoMov = 3
			UPDATE dbo.cuentaAhorro
			SET cantHumano = cantHumano - 1
			WHERE ID = @cuentaPertenece;

		SET @saldoIncumplido = (SELECT saldo FROM dbo.cuentaAhorro WHERE ID = @cuentaPertenece);
		SET @tipCue = (SELECT tipoCuentaId FROM dbo.cuentaAhorro WHERE ID = @cuentaPertenece);
		SET @salMin = (SELECT saldMin FROM dbo.tipoCuentaAhorro WHERE ID = @tipCue);

		IF @saldoIncumplido < @salMin
			UPDATE dbo.cuentaAhorro
			SET incumpleSalMin = 1
			WHERE ID = @cuentaPertenece;
	

		SET @lo1 = @lo1 + 1
	END

	INSERT INTO dbo.movimiento(tipoMovimientoId,
	cuentaAhorroId,
	monto,
	descripcion,
	fechaMovimiento)

	SELECT tipoMov, cuentaAhorrotemp, monto, descripcion, fechaMov FROM @movTemp

	--SELECT * FROM dbo.movimiento
	--Delete FROM dbo.movimiento
	--DBCC checkident('dbo.movimiento',reseed,0)

--Procesamiento de Estados de Cuenta---------------------------------------------
	
	--DECLARE @fechaActual date = '2020-08-31'

	DECLARE @cuentasCierre TABLE (
	ID INT IDENTITY NOT NULL,
	cuentaId INT NOT NULL,
	tipoCue int NOT NULL,
	hizoHum int,
	hizoAuto int,
	incumplio bit,
	multHum decimal(12,4),
	multAuto decimal(12,4),
	multMin decimal(12,4),
	inter decimal(12,4),
	mensMontServ decimal(12,4))


	INSERT INTO @cuentasCierre(
	cuentaId,
	tipoCue)

	SELECT ID, tipoCuentaId FROM dbo.cuentaAhorro
	WHERE DAY(fechaApertura) = (DAY(DATEADD(DAY,1,@fechaActual))) 

	UPDATE @cuentasCierre
	SET hizoHum = cantHumano,
	hizoAuto = cantAuto,
	incumplio = incumpleSalMin
	FROM  @cuentasCierre
	INNER JOIN dbo.cuentaAhorro ON cuentaId = dbo.cuentaAhorro.ID

	UPDATE @cuentasCierre
	SET multHum = montoComHum,
	multAuto = montoComAuto,
	multMin = multSalMin,
	inter = tasaIntAnu,
	mensMontServ = montMensCargServ
	FROM  @cuentasCierre
	INNER JOIN dbo.tipoCuentaAhorro ON tipoCue = dbo.tipoCuentaAhorro.ID

	
	DECLARE @lo3 int = 1, 
	@hi3 int = (SELECT COUNT(*) FROM @cuentaTemp),
	@IdBus int,
	@salFin decimal(12, 4),
	@tipoCuent int,
	@hizoHum int,
	@hizoAuto int,
	@incumplio bit,
	@multHum decimal(12,4),
	@multAuto decimal(12,4),
	@multIncum decimal(12,4),
	@inter decimal(12,4),
	@mensCargServ decimal(12,4)


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
			UPDATE dbo.cuentaAhorro SET
			saldo = saldo - @multAuto WHERE
			ID = @IdBus

		IF @hizoHum < 0 
			UPDATE dbo.cuentaAhorro SET
			saldo = saldo - @multHum WHERE
			ID = @IdBus

		IF @incumplio = 1
			UPDATE dbo.cuentaAhorro SET
			saldo = saldo - @multIncum WHERE
			ID = @IdBus

		UPDATE dbo.cuentaAhorro SET
		saldo = saldo + (@inter/12) WHERE
		ID = @IdBus

		UPDATE dbo.cuentaAhorro SET
		saldo = saldo - @mensCargServ WHERE
		ID = @IdBus

		UPDATE dbo.estadoCuenta SET
		fechaFin = @fechaActual,
		saldoFin = @salFin WHERE
		cuentaAhorroId = @IdBus AND fechaFin = NULL

		INSERT INTO dbo.estadoCuenta(
		fechaIni,
		cuentaAhorroId,
		saldoIni,
		saldoFin)
		VALUES(
		@fechaActual,
		@IdBus,
		@salFin,
		@salFin)

	END

	--SELECT * FROM dbo.estadoCuenta
	--Delete FROM dbo.estadoCuenta
	--DBCC checkident('dbo.estadoCuenta',reseed,0)

	SET @fechaActual = (DATEADD(DAY,1,@fechaActual))
END

