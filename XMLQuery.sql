USE BDProyecto;

GO

SET NOCOUNT ON

--INSERT XML en tipoMoneda-----------------------------------------------------------

INSERT INTO dbo.tipoMoneda

SELECT 
A.Tipo_Moneda.value('@Id', 'int') AS ID,
A.Tipo_Moneda.value('@Nombre', 'varchar(30)') AS nombre,
A.Tipo_Moneda.value('@Simbolo', 'varchar(10)') AS simbolo


FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\Archivos Tec\Cuarto semestre\BASes\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Datos/Tipo_Moneda/TipoMoneda') AS A(Tipo_Moneda)

--SELECT * FROM dbo.tipoMoneda
--Delete FROM dbo.tipoMoneda



--INSERT XML en tipoDocIdent-----------------------------------------------------------

INSERT INTO dbo.tipoDocIdent

SELECT 
A.Tipo_Doc.value('@Id', 'int') AS ID,
A.Tipo_Doc.value('@Nombre', 'varchar(50)') AS tipoDoc

FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\Archivos Tec\Cuarto semestre\BASes\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Datos/Tipo_Doc/TipoDocuIdentidad') AS A(Tipo_Doc)

--SELECT * FROM dbo.tipoDocIdent
--Delete FROM dbo.tipoDocIdent


--INSERT XML en parentesco-----------------------------------------------------------

INSERT INTO dbo.parentesco

SELECT 
A.Parentezco.value('@Id', 'int') AS ID,
A.Parentezco.value('@Nombre', 'varchar(30)') AS nombre

FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\Archivos Tec\Cuarto semestre\BASes\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Datos/Parentezcos/Parentezco') AS A(Parentezco)

--SELECT * FROM dbo.parentesco
--Delete FROM dbo.parentesco


--INSERT XML en tipoCuentaAhorro-----------------------------------------------------------

INSERT INTO dbo.tipoCuentaAhorro

SELECT 
A.TipoCuentaAhorro.value('@Id', 'int') AS ID,
A.TipoCuentaAhorro.value('@Nombre', 'varchar(50)') AS nombre,
A.TipoCuentaAhorro.value('@SaldoMinimo', 'decimal(10,4)') AS saldoMinimo,
A.TipoCuentaAhorro.value('@MultaSaldoMin', 'decimal(10,4)') AS multASaldoMinimo,
A.TipoCuentaAhorro.value('@CargoAnual', 'decimal(10,4)') AS cargoMens,
A.TipoCuentaAhorro.value('@NumRetirosHumano', 'int') AS numRetirosHum,
A.TipoCuentaAhorro.value('@NumRetirosAutomatico', 'int') AS numRetirosAuto,
A.TipoCuentaAhorro.value('@Interes', 'int') AS intereses,
A.TipoCuentaAhorro.value('@ComisionHumano', 'decimal(10,4)') AS comisionRetHum,
A.TipoCuentaAhorro.value('@ComisionAutomatico', 'decimal(10,4)') AS comisionRetAuto,
A.TipoCuentaAhorro.value('@IdTipoMoneda', 'int') AS tipoMonedaId
FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\Archivos Tec\Cuarto semestre\BASes\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Datos/Tipo_Cuenta_Ahorros/TipoCuentaAhorro') AS A(TipoCuentaAhorro)

--SELECT * FROM dbo.tipoCuentaAhorro
--Delete FROM dbo.tipoCuentaAhorro

--INSERT XML en persona-----------------------------------------------------------

INSERT INTO dbo.persona

SELECT 
A.Persona.value('@Nombre', 'varchar(100)') AS nombre,
A.Persona.value('@Email', 'varchar(50)') AS email,
A.Persona.value('@FechaNacimiento', 'date') AS nacimiento,
A.Persona.value('@Telefono1', 'int') AS tel1,
A.Persona.value('@Telefono2', 'int') AS tel2,
A.Persona.value('@TipoDocuIdentidad', 'int') AS tipoDocuIdentidad,
A.Persona.value('@ValorDocumentoIdentidad', 'int') AS valorDocumentoIdentidad
FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Datos/Personas/Persona') AS A(Persona)


--SELECT * FROM dbo.persona
--DELETE FROM [dbo].[persona]
--DBCC checkident('dbo.persona',reseed,0)



--INSERT XML en cuentaAhorro-----------------------------------------------------------

DECLARE @cuentaTemp TABLE
(ID int identity,
tipoCuenta int,
fechaApretura date,
tempdocIdent int,
numCuenta int,
saldo decimal(12,4))

INSERT INTO @cuentaTemp
SELECT
	A.Cuenta.value('@TipoCuentaId', 'int') AS tipoCuentaId,
	A.Cuenta.value('@FechaCreacion', 'date') AS fechaInicio,
	A.Cuenta.value('@ValorDocumentoIdentidadDelCliente', 'int') AS tempDocIdent,
	A.Cuenta.value('@NumeroCuenta', 'int') AS numCuenta,
	A.Cuenta.value('@Saldo', 'decimal(12,4)') AS saldo
FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\Archivos Tec\Cuarto semestre\BASes\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Datos/Cuentas/Cuenta') AS A(Cuenta)

DECLARE @Lo int = 1
DECLARE @Hi int
SET @Hi = (SELECT COUNT(*) FROM @cuentaTemp)

DECLARE @TipoCuentaId int
DECLARE @FechaInicio date
DECLARE @TempDocIdent int
DECLARE @NumCuenta int
DECLARE @Saldo decimal(12,4)
DECLARE @PersonaId int

WHILE (@Lo <= @Hi)
BEGIN

SET @TipoCuentaId = (SELECT tipoCuenta FROM @cuentaTemp where ID = @Lo)
SET @FechaInicio = (SELECT fechaApretura FROM @cuentaTemp where ID = @Lo)
SET @TempDocIdent = (SELECT tempdocIdent FROM @cuentaTemp where ID = @Lo)
SET @NumCuenta = (SELECT numCuenta FROM @cuentaTemp where ID = @Lo)
SET @Saldo = (SELECT saldo FROM @cuentaTemp where ID = @Lo)
SET @PersonaId = (SELECT ID FROM dbo.persona where valorDocIdent = @TempDocIdent)

INSERT INTO dbo.cuentaAhorro (tipoCuentaId, fechaApertura, personaId, numeroCuenta, saldo)
values(@TipoCuentaId, @FechaInicio, @PersonaId, @NumCuenta, @Saldo)

SET @Lo = @Lo+1
END;

--SELECT * FROM dbo.cuentaAhorro
--DELETE FROM dbo.cuentaAhorro
--DBCC checkident('dbo.cuentaAhorro',reseed,0)



--INSERT XML en beneficiario-------------------------
DECLARE @benefTemp TABLE
(ID int identity,
ParId int,
tempdocIdent int,
numCuenta int,
porcentaje int)

INSERT INTO @benefTemp
SELECT
	A.Benef.value('@ParentezcoId', 'int') AS ParId,
	A.Benef.value('@ValorDocumentoIdentidadBeneficiario', 'int') AS tempDocIdent,
	A.Benef.value('@NumeroCuenta', 'int') AS numCuenta,
	A.Benef.value('@Porcentaje', 'int') AS porcentaje
FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\Archivos Tec\Cuarto semestre\BASes\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Datos/Beneficiarios/Beneficiario') AS A(Benef)

DECLARE @Lo2 int = 1
DECLARE @Hi2 int
SET @Hi2 = (SELECT COUNT(*) FROM @benefTemp)

DECLARE @Parentezco int
DECLARE @TempDocIden int
DECLARE @NumCuent int
DECLARE @Porcentaje int
DECLARE @CuentaId int
DECLARE @PersonaId2 int


WHILE (@Lo2 <= @Hi2)
BEGIN

SET @TempDocIden = (SELECT tempDocIdent FROM @benefTemp where ID = @Lo2)
SET @NumCuent = (SELECT numCuenta FROM @benefTemp where ID = @Lo2)
SET @Porcentaje = (SELECT porcentaje FROM @benefTemp where ID = @Lo2)
SET @Parentezco = (SELECT ParId FROM @benefTemp where ID = @Lo2)
SET @PersonaId2 = (SELECT ID FROM dbo.persona where valorDocIdent = @TempDocIden)
SET @CuentaId = (SELECT ID FROM dbo.cuentaAhorro where numeroCuenta = @NumCuent)

INSERT INTO dbo.beneficiario(porcentaje, personaId, cuentaId,parentescoId, insertBy)
values(@Porcentaje, @PersonaId2, @CuentaId, @Parentezco, 'Script')

SET @Lo2 = @Lo2+1
END;

--SELECT * FROM dbo.beneficiario
--Delete FROM dbo.beneficiario
--DBCC checkident('dbo.beneficiario',reseed,0)


--INSERT XML en estadoCuenta-------------------------
DECLARE @estadoCuenta TABLE
(ID int identity,
numCuenta int,
fechaIni date,
fechaFin date,
saldoIni decimal(12,4),
saldoFin decimal(12,4))

INSERT INTO @estadoCuenta
SELECT
	A.Estado.value('@NumeroCuenta', 'int') AS numCuenta,
	A.Estado.value('@fechaInicio', 'date') AS fechaIni,
	A.Estado.value('@fechaFin', 'date') AS fechaFin,
	A.Estado.value('@saldoInicial', 'decimal(12,4)') AS saldIni,
	A.Estado.value('@saldoFinal', 'decimal(12,4)') AS saldFin
FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\Archivos Tec\Cuarto semestre\BASes\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Datos/Estados_de_Cuenta/Estado_de_Cuenta') AS A(Estado)

DECLARE @Lo3 int = 1
DECLARE @Hi3 int
SET @Hi3 = (SELECT COUNT(*) FROM @estadoCuenta)

DECLARE @FechIn date
DECLARE @NumCuen int
DECLARE @FechFin date
DECLARE @SaldoIni decimal(12,4)
DECLARE @SaldoFin decimal(12,4)
DECLARE @numCuenId int
WHILE (@Lo3 <= @Hi3)
BEGIN

SET @FechIn = (SELECT fechaIni FROM @estadoCuenta where ID = @Lo3)
SET @FechFin = (SELECT fechaFin FROM @estadoCuenta where ID = @Lo3)
SET @NumCuen = (SELECT numCuenta FROM @estadoCuenta where ID = @Lo3)
SET @SaldoIni = (SELECT saldoIni FROM @estadoCuenta where ID = @Lo3)
SET @SaldoFin  = (SELECT saldoFin FROM @estadoCuenta where ID = @Lo3)
SET @numCuenId = (SELECT ID FROM dbo.cuentaAhorro where numeroCuenta = @NumCuen)

INSERT INTO dbo.estadoCuenta(fechaIni, fechaFin,cuentaAhorroId, saldoIni, saldoFin)
values(@FechIn, @FechFin, @numCuenId, @SaldoIni, @SaldoFin)

SET @Lo3 = @Lo3+1
END;

--SELECT * FROM dbo.estadoCuenta
--Delete FROM dbo.estadoCuenta
--DBCC checkident('dbo.estadoCuenta',reseed,0)


--INSERT XML en usuario-------------------------

DECLARE @usuario TABLE
(ID int identity,
nomUsuario varchar(50),
contra varchar(50),
esAdmin bit,
tempDoc int)

INSERT INTO @usuario
SELECT
	A.Usuario.value('@User', 'varchar(50)') AS username,
	A.Usuario.value('@Pass', 'varchar(50)') AS pASs,
	A.Usuario.value('@EsAdministrador', 'bit') AS EsAdmin,
	A.Usuario.value('@ValorDocumentoIdentidad', 'int') AS docide
	

FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\Archivos Tec\Cuarto semestre\BASes\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Datos/Usuarios/Usuario') AS A(Usuario)

DECLARE @Lo4 int = 1
DECLARE @Hi4 int
SET @Hi4 = (SELECT COUNT(*) FROM @usuario)

DECLARE @NomUsuario varchar(50)
DECLARE @Contra varchar(50)
DECLARE @EsAdmin bit
DECLARE @TempDoc int
DECLARE @tipDocId int
WHILE (@Lo4 <= @Hi4)
BEGIN

SET @NomUsuario = (SELECT nomUsuario FROM @usuario where ID = @Lo4)
SET @Contra = (SELECT contra FROM @usuario where ID = @Lo4)
SET @EsAdmin = (SELECT esAdmin FROM @usuario where ID = @Lo4)
SET @TempDoc = (SELECT tempDoc FROM @usuario where ID = @Lo4)
SET @tipDocId = (SELECT ID FROM dbo.persona where valorDocIdent = @TempDoc)

INSERT INTO dbo.usuario(personaId, tipoUsuario, nombreUsuario, contrASenna)
values(@tipDocId, @EsAdmin, @NomUsuario, @Contra)

SET @Lo4 = @Lo4+1
END;

--SELECT * FROM dbo.usuario
--Delete FROM dbo.usuario
--DBCC checkident('dbo.usuario',reseed,0)


--INSERT XML en puedeVer-------------------------

DECLARE @visibilidad TABLE
(ID int identity,
nomUsuario varchar(50),
tempCuent int)

INSERT INTO @visibilidad
SELECT
	A.ver.value('@User', 'varchar(50)') AS username,
	A.ver.value('@NumeroCuenta', 'int') AS numcue
	

FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\Archivos Tec\Cuarto semestre\BASes\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Datos/Usuarios_Ver/UsuarioPuedeVer') AS A(ver)

DECLARE @Lo5 int = 1
DECLARE @Hi5 int
SET @Hi5 = (SELECT COUNT(*) FROM @visibilidad)

DECLARE @NomUsuarioTemp varchar(50)
DECLARE @tempCuenta int
DECLARE @CuenId int
DECLARE @UsuarioId int
WHILE (@Lo5 <= @Hi5)
BEGIN

SET @NomUsuarioTemp = (SELECT nomUsuario FROM @visibilidad where ID = @Lo5)
SET @tempCuenta = (SELECT tempCuent FROM @visibilidad where ID = @Lo5)
SET @CuenId = (SELECT ID FROM dbo.cuentaAhorro where numeroCuenta = @tempCuenta)
SET @UsuarioId = (SELECT ID FROM dbo.usuario where nombreUsuario = @NomUsuarioTemp)

INSERT INTO dbo.puedeVer(usuarioId, cuentaAhorroId)
VALUES(@UsuarioId, @CuenId)

SET @Lo5 = @Lo5+1
END;

--SELECT * FROM dbo.puedeVer
--Delete FROM dbo.puedeVer
--DBCC checkident('dbo.puedeVer',reseed,0)

SET NOCOUNT OFF
