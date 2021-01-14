USE BDProyecto;

GO

SET NOCOUNT ON

DECLARE 
@fechasSimulacion date = '12-31-2020',
@fechaActual date = '08-01-2020',
@fechaConvertida varchar(12) 


DECLARE 
@lo1 int = 1,
@hi1 int = 1,
@lo2 int = 1,
@hi2 int = 1,
@lo3 int = 1,
@hi3 int = 1,
@lo4 int = 1,
@hi4 int = 1,
@lo5 int = 1,
@hi5 int = 1,
@lo6 int = 1,
@hi6 int = 1,
@lo7 int = 1,
@hi7 int = 1


WHILE(@fechaActual <= @fechasSimulacion)
BEGIN

SET @fechaConvertida = CONVERT(varchar, @fechaActual, 110)
	

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
	OPENROWSET(BULK 'E:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea3.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaConvertida")]/Persona') AS A(Persona) 

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
	OPENROWSET(BULK 'E:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea3.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaConvertida")]/Cuenta') AS A(Cuenta)

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

	--INSERT XML en cuentaObjetivo-----------------------------------------------------------

	--DECLARE @fechaConvertida varchar(12) = '08-01-2020',
	--@fechaActual date = '08-01-2020'

	DECLARE @cuentaObjTemp TABLE
	(ID int identity,
	numCuentProv int,
	fechaIni date,
	fechaFinal date,
	numCuenta int,
	monto decimal(19,4),
	descripcion varchar(50),
	diaAhorro int,
	tasaInt int,
	insertAt date,
	insertBy varchar(50))

	DECLARE
	@intereses int,
	@fechIniCalc date,
	@fechFinCalc date

	INSERT INTO @cuentaObjTemp(
	numCuentProv,
	fechaIni,
	fechaFinal,
	numCuenta,
	monto,
	descripcion,
	diaAhorro,
	tasaInt,
	insertAt,
	insertBy)

	SELECT
		A.Cuenta.value('@NumeroCuentaPrimaria', 'int') AS tipoCuentaId,
		@fechaActual,
		A.Cuenta.value('@FechaFinal', 'date') AS tipoCuentaId,
		A.Cuenta.value('@NumeroCuentaAhorro', 'int') AS tipoCuentaId,
		A.Cuenta.value('@MontoAhorro', 'decimal(19,4)') AS tipoCuentaId,
		A.Cuenta.value('@Descripcion', 'varchar(50)') AS tempDocIdent,
		A.Cuenta.value('@DiaAhorro', 'int') AS numCuenta,
		0,
		@fechaActual,
		SUSER_NAME()
	FROM(
	SELECT CAST(c AS XML) FROM 
	OPENROWSET(BULK 'E:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea3.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaConvertida")]/CuentaAhorro') AS A(Cuenta)

	UPDATE @cuentaObjTemp
	SET numCuentProv = dbo.cuentaAhorro.ID
	FROM  @cuentaObjTemp
	INNER JOIN dbo.cuentaAhorro ON numCuentProv = dbo.cuentaAhorro.numeroCuenta

	SET @hi4 = (SELECT COUNT(*) FROM @cuentaObjTemp) + @lo4 - 1

	WHILE (@lo4 <= @hi4)
	BEGIN

		SET @fechIniCalc = (SELECT fechaIni FROM @cuentaObjTemp WHERE ID = @lo4)
		SET @fechFinCalc = (SELECT fechaFinal FROM @cuentaObjTemp WHERE ID = @lo4)
		SET @intereses = (SELECT DATEDIFF(month, @fechIniCalc, @fechFinCalc))

		UPDATE @cuentaObjTemp
		SET tasaInt = 0.5 * @intereses
		WHERE ID = @lo4

		SET @lo4 = @lo4 + 1

	END
	SET @lo4 = @hi4+1

	INSERT INTO dbo.CuentaObjetivo(
	objetivo,
	fechaIn,
	cuentaAhorroId,
	cuota,
	numeroCuenta,
	fechaFin,
	diaAhorro,
	tasaInteres,
	insertAt,
	insertBy)

	SELECT descripcion,
	fechaIni, 
	numCuentProv, 
	monto, 
	numCuenta,
	fechaFinal,
	diaAhorro,
	tasaInt,
	insertAt,
	insertBy
	FROM @cuentaObjTemp 

	DELETE FROM @cuentaObjTemp

	--SELECT * FROM dbo.@cuentaObjTemp

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
	OPENROWSET(BULK 'E:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea3.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaConvertida")]/Beneficiario') AS A(Benef)
	
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
	OPENROWSET(BULK 'E:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea3.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaConvertida")]/Usuario') AS A(Usu)

	INSERT INTO dbo.usuario(nombreUsuario,
	contrasenna,
	personaId,
	tipoUsuario)

	SELECT nomUsu, pass, dbo.persona.ID, esAdmin FROM @userTemp
	INNER JOIN dbo.persona ON docId = dbo.persona.valorDocIdent

	DELETE FROM @userTemp

	--SELECT * FROM dbo.usuario

--INSERT XML en puedeVer-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-08-02',
	--@fechaConvertida varchar(12) = '08-02-2020'

	--DECLARE @verTemp TABLE
	--(ID int identity,
	--usu varchar(50),
	--cuen int)
	
	--INSERT INTO @verTemp(
	--usu,
	--cuen)
	--SELECT
	--	A.Usu.value('@User', 'varchar(50)') AS ParId,
	--	A.Usu.value('@Cuenta', 'int') AS tempDocIden
	--FROM(
	--SELECT CAST(c AS XML) FROM 
	--OPENROWSET(BULK 'E:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea3.xml', SINGLE_BLOB) AS T(c)
	--) AS S(c)

	--cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaConvertida")]/UsuarioPuedeVer') AS A(Usu)

	--UPDATE @verTemp
	--SET cuen = dbo.cuentaAhorro.ID
	--FROM  @verTemp
	--INNER JOIN dbo.cuentaAhorro ON cuen = dbo.cuentaAhorro.numeroCuenta

	--UPDATE @verTemp
	--SET usu = dbo.persona.ID
	--FROM  @verTemp
	--INNER JOIN dbo.persona ON usu = valorDocIdent

	--SELECT * FROM @verTemp

	--DECLARE @usuAux int, @cuenAux int

	--SET @hi2 = (SELECT COUNT(*) FROM @verTemp) + @lo2 - 1
	--WHILE (@lo2 <= @hi2)
	--BEGIN
		
	--	SET @usuAux = (SELECT usu from @verTemp WHERE ID = @lo2)
	--	SET @cuenAux = (SELECT cuen from @verTemp WHERE ID = @lo2)

	--	IF (NOT EXISTS (SELECT usuarioId FROM dbo.puedeVer WHERE usuarioId = @usuAux AND cuentaAhorroId = @cuenAux))
	--	BEGIN

	--		INSERT INTO dbo.puedeVer(usuarioId, cuentaAhorroId)
	--		SELECT usu, cuen FROM  @verTemp
	--		WHERE ID = @lo2
	--	END

	--	SET @lo2 = @lo2 + 1
	--END

	--SET @lo2 = @hi2+1

	--DELETE FROM @verTemp

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
	OPENROWSET(BULK 'E:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea3.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaConvertida")]/Movimientos') AS A(Mov)

	UPDATE @movTemp
	SET cuentaAhorrotemp = dbo.cuentaAhorro.ID
	FROM  @movTemp
	INNER JOIN dbo.cuentaAhorro ON cuentaAhorrotemp = dbo.cuentaAhorro.numeroCuenta

	UPDATE @movTemp
	SET debito = tipo
	FROM  @movTemp
	INNER JOIN dbo.TipoMovimiento ON tipoMov = dbo.TipoMovimiento.Id
	
	DECLARE   
	@monto decimal(19,4),
	@esDebito varchar(30),
	@tipoMov int,
	@cuentaPertenece int,
	@saldoIncumplido decimal(19,4),
	@tipCue int,
	@cantMov int = 0,
	@minEstado decimal(19,4),
	@descr varchar(50)

	SET @hi1 = (SELECT COUNT(*) FROM @movTemp) + @lo1-1

	WHILE(@lo1<=@hi1)
	BEGIN

		SET @monto = (SELECT monto FROM @movTemp WHERE ID = @lo1)
		SET @cuentaPertenece = (SELECT cuentaAhorrotemp FROM @movTemp WHERE ID = @lo1)
		SET @esDebito = (SELECT debito FROM @movTemp WHERE ID =  @lo1)
		SET @tipoMov = (SELECT tipoMov FROM @movTemp WHERE ID =  @lo1)
		SET @descr = (SELECT descripcion FROM @movTemp WHERE ID =  @lo1)


		IF @esDebito = 'Debito'
		BEGIN
			UPDATE dbo.cuentaAhorro
			SET saldo = saldo - @monto
			WHERE ID = @cuentaPertenece;
		END

		ELSE
		BEGIN
			UPDATE dbo.cuentaAhorro
			SET saldo = saldo + @monto
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
		SET @cantMov = (SELECT COUNT(ID) FROM dbo.movimiento WHERE cuentaAhorroId = @cuentaPertenece);
		SET @minEstado = (SELECT saldoMin FROM dbo.estadoCuenta WHERE cuentaAhorroId = @cuentaPertenece AND fechaFin IS NULL);


		IF (@saldoIncumplido < @minEstado) OR @cantMov = 0
		BEGIN
			UPDATE dbo.estadoCuenta
			SET saldoMin = @saldoIncumplido
			WHERE cuentaAhorroId = @cuentaPertenece 
			AND fechaFin IS NULL;
		END

		SET @lo1 = @lo1 + 1

		INSERT INTO dbo.movimiento(tipoMovimientoId,
		cuentaAhorroId,
		monto,
		descripcion,
		fechaMovimiento)

		VALUES(
		@tipoMov,
		@cuentaPertenece,
		@monto,
		@descr,
		@fechaActual)

	END

	SET @lo1 = @hi1+1

	

	DELETE FROM @movTemp

	--SELECT * FROM dbo.movimiento

--Procesamiento de Cuentas Objetivo---------------------------------------------

--Depositos en Cuentas----------------------------------------------------------

	--DECLARE @fechaActual date = '08-16-2020',
	--@lo5 int = 1,
	--@hi5 int = 1

	DECLARE @saldoCue decimal(19,4),
	@cuentaPadre int,
	@montoParaAhorro decimal(19,4),
	@numeroCuenta int,
	@esActivo bit,
	@IDCuenta int
	

	DECLARE @tablaDeposito TABLE 
	(ID int identity,
	cuentaAhorroId int,
	numeroCuentaOb int,
	monto decimal(19,4),
	esActivo bit)

	INSERT INTO @tablaDeposito(
	cuentaAhorroId,
	numeroCuentaOb,
	monto,
	esActivo)
	(SELECT cuentaAhorroId,
	numeroCuenta,
	cuota,
	activo
	FROM dbo.CuentaObjetivo
	WHERE diaAhorro = DAY(@fechaActual))

	SET @hi5 = (SELECT COUNT(*) FROM @tablaDeposito) + @lo5-1

	WHILE(@lo5<=@hi5)
	BEGIN

		SET @numeroCuenta = (SELECT numeroCuentaOb FROM @tablaDeposito WHERE ID = @lo5)
		SET @montoParaAhorro = (SELECT monto FROM @tablaDeposito WHERE ID = @lo5)
		SET @cuentaPadre = (SELECT cuentaAhorroId FROM @tablaDeposito WHERE ID = @lo5)
		SET @saldoCue = (SELECT saldo FROM dbo.cuentaAhorro WHERE ID = @cuentaPadre)
		SET @esActivo = (SELECT esActivo FROM @tablaDeposito WHERE ID = @lo5)
		SET @IDCuenta = (SELECT ID FROM dbo.CuentaObjetivo WHERE numeroCuenta = @numeroCuenta)

		IF @saldoCue > @montoParaAhorro AND @esActivo = 1
		BEGIN

			INSERT INTO dbo.movimientoCuenOb
			(fecha,
			monto,
			cuentaObjetivoID,
			tipoMovimientoCoId)
			VALUES (
			@fechaActual,
			@montoParaAhorro,
			@IDCuenta,
			1)
			
			UPDATE dbo.cuentaAhorro
			SET saldo = saldo - @montoParaAhorro
			WHERE ID = @cuentaPadre

			UPDATE dbo.CuentaObjetivo
			SET saldo = saldo + @montoParaAhorro
			WHERE numeroCuenta = @numeroCuenta

		END
		ELSE
		BEGIN
				
			UPDATE dbo.CuentaObjetivo
			SET fallo = 1
			WHERE numeroCuenta = @numeroCuenta

		END

		SET @lo5 = @lo5 + 1
	END

	SET @lo5 = @hi5+1
	DELETE FROM @tablaDeposito

--Calculo de intereses-----------------------------------------------------------

	DECLARE @cuenProce int,
	@saldocuent decimal(19,4),
	@tasa decimal(19,4),
	@acumulacion decimal(19,4)

	DECLARE @tablaIntereses TABLE(
	ID int IDENTITY,
	Idcuenta int,
	saldo decimal(19,4),
	tasaInteres int)

	INSERT INTO @tablaIntereses(
	Idcuenta,
	saldo,
	tasaInteres)
	(SELECT
	ID,
	saldo,
	tasaInteres
	FROM dbo.cuentaObjetivo
	WHERE activo = 1)


	SET @hi7 = (SELECT COUNT(*) FROM @tablaIntereses) + @lo7 - 1

	WHILE @lo7 <= @hi7
	BEGIN

		SET @cuenProce = (SELECT IdCuenta FROM @tablaIntereses WHERE ID = @lo7)
		SET @saldocuent = (SELECT saldo FROM @tablaIntereses WHERE ID = @lo7)
		SET @tasa = (SELECT tasaInteres FROM @tablaIntereses WHERE ID = @lo7)
		SET @acumulacion = ((@tasa/365) * @saldocuent) / 100

		INSERT INTO dbo.movCoInt
		(fecha,
		monto,
		cuentaObjetivoID)
		VALUES (
		@fechaActual,
		@acumulacion,
		@cuenProce)

		UPDATE dbo.cuentaObjetivo
		SET interesAcumulado = interesAcumulado + @acumulacion
		WHERE @cuenProce = ID

	SET @lo7 = @lo7+1
	END

	SET @lo7 = @hi7+1
	DELETE FROM @tablaIntereses

--Rendecion de Cuentas-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-10-05',
	--@lo6 int = 1,
	--@hi6 int = 1

	DECLARE @saldCuen decimal(19,4),
	@cuentPad int,
	@interesesAcu decimal(19,4),
	@cuent int,
	@cuentaRende int
	

	DECLARE @tablaRendecion TABLE 
	(ID int identity,
	idCuenOb int,
	cuentaAhorroId int,
	saldo decimal(19,4),
	acumulacionInte decimal(19,4))

	INSERT INTO @tablaRendecion(
	cuentaAhorroId,
	idCuenOb,
	saldo,
	acumulacionInte)
	(SELECT cuentaAhorroId,
	id,
	saldo,
	interesAcumulado
	FROM dbo.CuentaObjetivo
	WHERE fechaFin = @fechaActual)

	SET @hi6 = (SELECT COUNT(*) FROM @tablaRendecion) + @lo6-1

	WHILE(@lo6<=@hi6)
	BEGIN

		SET @cuent = (SELECT idCuenOb FROM @tablaRendecion WHERE ID = @lo6)
		SET @interesesAcu = (SELECT acumulacionInte FROM @tablaRendecion WHERE ID = @lo6)
		SET @cuentPad = (SELECT cuentaAhorroId FROM @tablaRendecion WHERE ID = @lo6)
		SET @saldCuen = (SELECT saldo FROM @tablaRendecion WHERE ID = @lo6)

		INSERT INTO dbo.movimientoCuenOb
		(fecha,
		monto,
		cuentaObjetivoID,
		tipoMovimientoCoId)
		VALUES (
		@fechaActual,
		@interesesAcu,
		@cuent,
		2)


		INSERT INTO dbo.movimientoCuenOb
		(fecha,
		monto,
		cuentaObjetivoID,
		tipoMovimientoCoId)
		VALUES (
		@fechaActual,
		@saldCuen + @interesesAcu,
		@cuent,
		3)

		
		UPDATE dbo.CuentaObjetivo
		SET interesAcumulado = 0
		WHERE ID = @cuent

			
		UPDATE dbo.cuentaAhorro
		SET saldo = saldo + @saldCuen + @interesesAcu
		WHERE ID = @cuentPad

		UPDATE dbo.CuentaObjetivo
		SET saldo = 0,
		interesAcumulado = 0,
		activo = 0,
		saldoQueHubo = @saldCuen,
		interesQueHubo = @interesesAcu
		WHERE ID = @cuent

		SET @lo6 = @lo6 + 1
	END

	SET @lo6 = @hi6+1
	DELETE FROM @tablaRendecion



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
		multHum decimal(19,4),
		multAuto decimal(19,4),
		multMin decimal(19,4),
		inter decimal(19,4),
		mensMontServ decimal(19,4))


		INSERT INTO @cuentasCierre(
		cuentaId,
		tipoCue,
		hizoHum,
		hizoAuto)

		SELECT ID, tipoCuentaId,cantHumano,cantAuto FROM dbo.cuentaAhorro
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
		@saldoEstado decimal(19,4),
		@salMini decimal(19,4),
		@multHum decimal(19,4),
		@multAuto decimal(19,4),
		@multIncum decimal(19,4),
		@inter decimal(19,4),
		@mensCargServ decimal(19,4),
		@maximoOpeHum int,
		@maximoOpeAuto int

		SET @hi3 = (SELECT COUNT(*) FROM @cuentasCierre) + @lo3 - 1

		WHILE (@lo3 <= @hi3)
		BEGIN

			SET @IdBus = (SELECT cuentaId FROM @cuentasCierre WHERE ID = @lo3)
			SET @salFin = (SELECT saldo FROM dbo.cuentaAhorro WHERE ID = @IdBus)
			SET @tipoCuent = (SELECT tipoCuentaId FROM dbo.cuentaAhorro WHERE ID = @IdBus)
			SET @hizoHum = (SELECT hizoHum FROM @cuentasCierre WHERE ID = @lo3)
			SET @hizoAuto = (SELECT hizoAuto FROM @cuentasCierre WHERE ID = @lo3)
			SET @salMini = (SELECT saldMin FROM dbo.tipoCuentaAhorro WHERE ID = @tipoCuent)
			SET @saldoEstado = (SELECT saldoMin FROM dbo.estadoCuenta WHERE cuentaAhorroId = @IdBus AND fechaFin IS NULL)
			SET @multHum = (SELECT multHum FROM @cuentasCierre WHERE ID = @lo3)
			SET @multAuto = (SELECT multAuto FROM @cuentasCierre WHERE ID = @lo3)
			SET @multIncum = (SELECT multMin FROM @cuentasCierre WHERE ID = @lo3)
			SET @mensCargServ = (SELECT mensMontServ FROM @cuentasCierre WHERE ID = @lo3)
			SET @inter = (SELECT inter FROM @cuentasCierre WHERE ID = @lo3)
			SET @maximoOpeAuto = (SELECT maxOpeAuto FROM dbo.tipoCuentaAhorro WHERE ID = @tipoCuent)
			SET @maximoOpeHum = (SELECT maxOpeHum FROM dbo.tipoCuentaAhorro WHERE ID = @tipoCuent)

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
				saldo = saldo - @multAuto,
				multado = 1 WHERE
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

			IF @saldoEstado < @salMini
			BEGIN
				UPDATE dbo.cuentaAhorro SET
				saldo = saldo - @multIncum WHERE
				ID = @IdBus
			END

			UPDATE dbo.cuentaAhorro SET
			saldo = saldo - @mensCargServ WHERE
			ID = @IdBus

			UPDATE dbo.cuentaAhorro SET
			saldo = saldo + (((@inter * saldo )/100)/12) WHERE
			ID = @IdBus

			UPDATE dbo.cuentaAhorro SET
			cantAuto = @maximoOpeAuto,
			cantHumano = @maximoOpeHum
			WHERE
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
			(((@inter * @salFin )/100)/12))

			UPDATE dbo.estadoCuenta SET
			fechaFin = @fechaActual,
			saldoFin = @salFin WHERE
			cuentaAhorroId = @IdBus AND fechaFin IS NULL

			INSERT INTO dbo.estadoCuenta(
			fechaIni,
			cuentaAhorroId,
			saldoIni,
			saldoFin,
			saldoMin)
			VALUES(
			DATEADD(DAY,1,@fechaActual),
			@IdBus,
			@salFin,
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
--Delete FROM dbo.movCoInt
--DBCC checkident('dbo.movCoInt',reseed,0)
--Delete FROM dbo.movimientoCuenOb
--DBCC checkident('dbo.movimientoCuenOb',reseed,0)
--Delete FROM dbo.cuentaObjetivo
--DBCC checkident('dbo.cuentaObjetivo',reseed,0)
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