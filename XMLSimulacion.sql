USE BDProyecto;

GO

SET NOCOUNT ON

DECLARE @fechasSimulacion TABLE
(ID INT IDENTITY (1,1),
fecha DATE)

INSERT INTO @fechasSimulacion (fecha)
SELECT
	A.Fecha.value('@Fecha', 'date') AS fechaInicio
FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2.xml', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Operaciones/FechaOperacion') AS A(Fecha)
Select * from @fechasSimulacion 

DECLARE  
@lo int = 1, 
@hi int = (SELECT COUNT(*) FROM @fechasSimulacion),
@fechaActual date 

WHILE (@lo <= @hi)
BEGIN

	SELECT @fechaActual = fecha FROM @fechasSimulacion WHERE ID = @lo
	
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

	SELECT * FROM dbo.persona
	--DELETE FROM [dbo].[persona]
	--DBCC checkident('dbo.persona',reseed,0)

	----INSERT XML en cuentaAhorro-----------------------------------------------------------

	--DECLARE @fechaActual date = '2020-07-01'

	DECLARE @cuentaTemp TABLE
	(ID int identity,
	tipoCuenta int,
	fechaApretura date,
	tempdocIdent int,
	numCuenta int)

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
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2.xml', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@fechaActual")]/Cuenta') AS A(Cuenta)

	select * from @cuentaTemp

	INSERT INTO dbo.cuentaAhorro (tipoCuentaId,
	fechaApertura,
	personaId,
	numeroCuenta)

	SELECT tipoCuenta,fechaApretura, dbo.persona.ID, numCuenta FROM @cuentaTemp
	INNER JOIN dbo.persona ON tempdocIdent = dbo.persona.valorDocIdent 

--	SELECT * FROM dbo.cuentaAhorro
--	DELETE FROM dbo.cuentaAhorro
--	DBCC checkident('dbo.cuentaAhorro',reseed,0)


--INSERT XML en beneficiario-------------------------

	DECLARE @fechaActual date = '2020-07-01'

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
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2.xml', SINGLE_BLOB) AS T(c)
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

	SELECT * FROM dbo.beneficiario
	--Delete FROM dbo.beneficiario
	--DBCC checkident('dbo.beneficiario',reseed,0)

END
