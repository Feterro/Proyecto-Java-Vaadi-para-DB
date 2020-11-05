USE BDProyecto;

GO

set nocount off

--insert xml en tipoMoneda-----------------------------------------------------------

insert into dbo.tipoMoneda

select 
A.Tipo_Moneda.value('@Id', 'Int') as ID,
A.Tipo_Moneda.value('@Nombre', 'varchar(30)') as nombre,
A.Tipo_Moneda.value('@Simbolo', 'varchar(10)') as simbolo


from(
select cast(c as xml) from 
openrowset(bulk 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.xml', single_blob) as T(c)
) as S(c)

cross apply c.nodes('Datos/Tipo_Moneda/TipoMoneda') as A(Tipo_Moneda)

--select * from dbo.tipoMoneda
--Delete from dbo.tipoMoneda



--insert xml en tipoDocIdent-----------------------------------------------------------

insert into dbo.tipoDocIdent

select 
A.Tipo_Doc.value('@Id', 'Int') as ID,
A.Tipo_Doc.value('@Nombre', 'varchar(50)') as tipoDoc

from(
select cast(c as xml) from 
openrowset(bulk 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.xml', single_blob) as T(c)
) as S(c)

cross apply c.nodes('Datos/Tipo_Doc/TipoDocuIdentidad') as A(Tipo_Doc)

--select * from dbo.tipoDocIdent
--Delete from dbo.tipoDocIdent


--insert xml en parentesco-----------------------------------------------------------

insert into dbo.parentesco

select 
A.Parentezco.value('@Id', 'Int') as ID,
A.Parentezco.value('@Nombre', 'varchar(30)') as nombre

from(
select cast(c as xml) from 
openrowset(bulk 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.xml', single_blob) as T(c)
) as S(c)

cross apply c.nodes('Datos/Parentezcos/Parentezco') as A(Parentezco)

--select * from dbo.parentesco
--Delete from dbo.parentesco


--insert xml en tipoCuentaAhorro-----------------------------------------------------------

insert into dbo.tipoCuentaAhorro

select 
A.TipoCuentaAhorro.value('@Id', 'Int') as ID,
A.TipoCuentaAhorro.value('@Nombre', 'varchar(50)') as nombre,
A.TipoCuentaAhorro.value('@SaldoMinimo', 'decimal(10,4)') as saldoMinimo,
A.TipoCuentaAhorro.value('@MultaSaldoMin', 'decimal(10,4)') as multaSaldoMinimo,
A.TipoCuentaAhorro.value('@CargoAnual', 'decimal(10,4)') as cargoMens,
A.TipoCuentaAhorro.value('@NumRetirosHumano', 'int') as numRetirosHum,
A.TipoCuentaAhorro.value('@NumRetirosAutomatico', 'int') as numRetirosAuto,
A.TipoCuentaAhorro.value('@Interes', 'int') as Intereses,
A.TipoCuentaAhorro.value('@ComisionHumano', 'decimal(10,4)') as comisionRetHum,
A.TipoCuentaAhorro.value('@ComisionAutomatico', 'decimal(10,4)') as comisionRetAuto,
A.TipoCuentaAhorro.value('@IdTipoMoneda', 'int') as tipoMonedaId
from(
select cast(c as xml) from 
openrowset(bulk 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.xml', single_blob) as T(c)
) as S(c)

cross apply c.nodes('Datos/Tipo_Cuenta_Ahorros/TipoCuentaAhorro') as A(TipoCuentaAhorro)

--select * from dbo.tipoCuentaAhorro
--Delete from dbo.tipoCuentaAhorro

--insert xml en persona-----------------------------------------------------------

insert into dbo.persona

select 
A.Persona.value('@Nombre', 'varchar(100)') as nombre,
A.Persona.value('@Email', 'varchar(50)') as email,
A.Persona.value('@FechaNacimiento', 'date') as nacimiento,
A.Persona.value('@Telefono1', 'int') as tel1,
A.Persona.value('@Telefono2', 'int') as tel2,
A.Persona.value('@TipoDocuIdentidad', 'int') as tipoDocuIdentidad,
A.Persona.value('@ValorDocumentoIdentidad', 'int') as valorDocumentoIdentidad
from(
select cast(c as xml) from 
openrowset(bulk 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.xml', single_blob) as T(c)
) as S(c)

cross apply c.nodes('Datos/Personas/Persona') as A(Persona)


--select * from dbo.persona
--DELETE FROM [dbo].[persona]
--DBCC checkident('dbo.persona',reseed,0)



--insert xml en cuentaAhorro-----------------------------------------------------------

declare @cuentaTemp Table
(ID int identity, tipoCuenta int, fechaApretura date, tempdocIdent int, numCuenta int, saldo decimal(12,4))

insert into @cuentaTemp
select
	A.Cuenta.value('@TipoCuentaId', 'int') as tipoCuentaId,
	A.Cuenta.value('@FechaCreacion', 'date') as fechaInicio,
	A.Cuenta.value('@ValorDocumentoIdentidadDelCliente', 'int') as tempDocIdent,
	A.Cuenta.value('@NumeroCuenta', 'int') as numCuenta,
	A.Cuenta.value('@Saldo', 'decimal(12,4)') as saldo
from(
select cast(c as xml) from 
openrowset(bulk 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.xml', single_blob) as T(c)
) as S(c)

cross apply c.nodes('Datos/Cuentas/Cuenta') as A(Cuenta)

declare @Lo int = 1
declare @Hi int
set @Hi = (select COUNT(*) from @cuentaTemp)

declare @TipoCuentaId int
declare @FechaInicio date
declare @TempDocIdent int
declare @NumCuenta int
declare @Saldo decimal(12,4)
declare @PersonaId int

While (@Lo <= @Hi)
Begin

set @TipoCuentaId = (select tipoCuenta from @cuentaTemp where ID = @Lo)
set @FechaInicio = (select fechaApretura from @cuentaTemp where ID = @Lo)
set @TempDocIdent = (select tempdocIdent from @cuentaTemp where ID = @Lo)
set @NumCuenta = (select numCuenta from @cuentaTemp where ID = @Lo)
set @Saldo = (select saldo from @cuentaTemp where ID = @Lo)
set @PersonaId = (select ID from dbo.persona where valorDocIdent = @TempDocIdent)

insert into dbo.cuentaAhorro (tipoCuentaId, fechaApertura, personaId, numeroCuenta, saldo)
values(@TipoCuentaId, @FechaInicio, @PersonaId, @NumCuenta, @Saldo)

set @Lo = @Lo+1
End;

--select * from dbo.cuentaAhorro
--DELETE FROM dbo.cuentaAhorro
--DBCC checkident('dbo.cuentaAhorro',reseed,0)



--insert xml en beneficiario-------------------------
declare @benefTemp Table
(ID int identity, ParId int, tempdocIdent int, numCuenta int, porcentaje int)

insert into @benefTemp
select
	A.Benef.value('@ParentezcoId', 'int') as ParId,
	A.Benef.value('@ValorDocumentoIdentidadBeneficiario', 'int') as tempDocIdent,
	A.Benef.value('@NumeroCuenta', 'int') as numCuenta,
	A.Benef.value('@Porcentaje', 'int') as porcentaje
from(
select cast(c as xml) from 
openrowset(bulk 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.xml', single_blob) as T(c)
) as S(c)

cross apply c.nodes('Datos/Beneficiarios/Beneficiario') as A(Benef)

declare @Lo2 int = 1
declare @Hi2 int
set @Hi2 = (select COUNT(*) from @benefTemp)

declare @Parentezco int
declare @TempDocIden int
declare @NumCuent int
declare @Porcentaje int
declare @CuentaId int
declare @PersonaId2 int
declare @Activo bit = 1

While (@Lo2 <= @Hi2)
Begin

set @TempDocIden = (select tempDocIdent from @benefTemp where ID = @Lo2)
set @NumCuent = (select numCuenta from @benefTemp where ID = @Lo2)
set @Porcentaje = (select porcentaje from @benefTemp where ID = @Lo2)
set @Parentezco = (select ParId from @benefTemp where ID = @Lo2)
set @PersonaId2 = (select ID from dbo.persona where valorDocIdent = @TempDocIden)
set @CuentaId = (select ID from dbo.cuentaAhorro where numeroCuenta = @NumCuent)

insert into dbo.beneficiario(porcentaje, personaId, cuentaId, activo,parentescoId)
values(@Porcentaje, @PersonaId2, @CuentaId, @Activo, @Parentezco)

set @Lo2 = @Lo2+1
End;

--select * from dbo.beneficiario
--Delete from dbo.beneficiario
--DBCC checkident('dbo.beneficiario',reseed,0)


--insert xml en estadoCuenta-------------------------
declare @estadoCuenta Table
(ID int identity, numCuenta int, fechaIni date, fechaFin date, saldoIni decimal(12,4), saldoFin decimal(12,4))

insert into @estadoCuenta
select
	A.Estado.value('@NumeroCuenta', 'int') as numCuenta,
	A.Estado.value('@fechaInicio', 'date') as fechaIni,
	A.Estado.value('@fechaFin', 'date') as fechaFin,
	A.Estado.value('@saldoInicial', 'decimal(12,4)') as saldIni,
	A.Estado.value('@saldoFinal', 'decimal(12,4)') as saldFin
from(
select cast(c as xml) from 
openrowset(bulk 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.xml', single_blob) as T(c)
) as S(c)

cross apply c.nodes('Datos/Estados_de_Cuenta/Estado_de_Cuenta') as A(Estado)

declare @Lo3 int = 1
declare @Hi3 int
set @Hi3 = (select COUNT(*) from @estadoCuenta)

declare @FechIn date
declare @NumCuen int
declare @FechFin date
declare @SaldoIni decimal(12,4)
declare @SaldoFin decimal(12,4)
declare @numCuenId int
While (@Lo3 <= @Hi3)
Begin

set @FechIn = (select fechaIni from @estadoCuenta where ID = @Lo3)
set @FechFin = (select fechaFin from @estadoCuenta where ID = @Lo3)
set @NumCuen = (select numCuenta from @estadoCuenta where ID = @Lo3)
set @SaldoIni = (select saldoIni from @estadoCuenta where ID = @Lo3)
set @SaldoFin  = (select saldoFin from @estadoCuenta where ID = @Lo3)
set @numCuenId = (select ID from dbo.cuentaAhorro where numeroCuenta = @NumCuen)

insert into dbo.estadoCuenta(fechaIni, fechaFin,cuentaAhorroId, saldoIni, saldoFin)
values(@FechIn, @FechFin, @numCuenId, @SaldoIni, @SaldoFin)

set @Lo3 = @Lo3+1
End;

--select * from dbo.estadoCuenta
--Delete from dbo.estadoCuenta
--DBCC checkident('dbo.estadoCuenta',reseed,0)


--insert xml en usuario-------------------------

declare @usuario Table
(ID int identity, nomUsuario varchar(50), contra varchar(50), esAdmin bit, tempDoc int)

insert into @usuario
select
	A.Usuario.value('@User', 'varchar(50)') as username,
	A.Usuario.value('@Pass', 'varchar(50)') as pass,
	A.Usuario.value('@EsAdministrador', 'bit') as EsAdmin,
	A.Usuario.value('@ValorDocumentoIdentidad', 'int') as docide
	

from(
select cast(c as xml) from 
openrowset(bulk 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.xml', single_blob) as T(c)
) as S(c)

cross apply c.nodes('Datos/Usuarios/Usuario') as A(Usuario)

declare @Lo4 int = 1
declare @Hi4 int
set @Hi4 = (select COUNT(*) from @usuario)

declare @NomUsuario varchar(50)
declare @Contra varchar(50)
declare @EsAdmin bit
declare @TempDoc int
declare @tipDocId int
While (@Lo4 <= @Hi4)
Begin

set @NomUsuario = (select nomUsuario from @usuario where ID = @Lo4)
set @Contra = (select contra from @usuario where ID = @Lo4)
set @EsAdmin = (select esAdmin from @usuario where ID = @Lo4)
set @TempDoc = (select tempDoc from @usuario where ID = @Lo4)
set @tipDocId = (select ID from dbo.persona where valorDocIdent = @TempDoc)

insert into dbo.usuario(personaId, tipoUsuario, nombreUsuario, contrasenna)
values(@tipDocId, @EsAdmin, @NomUsuario, @Contra)

set @Lo4 = @Lo4+1
End;

--select * from dbo.usuario
--Delete from dbo.usuario
--DBCC checkident('dbo.usuario',reseed,0)


--insert xml en puedeVer-------------------------

declare @visibilidad Table
(ID int identity, nomUsuario varchar(50), tempCuent int)

insert into @visibilidad
select
	A.ver.value('@User', 'varchar(50)') as username,
	A.ver.value('@NumeroCuenta', 'int') as numcue
	

from(
select cast(c as xml) from 
openrowset(bulk 'F:\Archivos Tec\Cuarto semestre\Bases\Datos_Tarea1.xml', single_blob) as T(c)
) as S(c)

cross apply c.nodes('Datos/Usuarios_Ver/UsuarioPuedeVer') as A(ver)

declare @Lo5 int = 1
declare @Hi5 int
set @Hi5 = (select COUNT(*) from @visibilidad)

declare @NomUsuarioTemp varchar(50)
declare @tempCuenta int
declare @CuenId int
declare @UsuarioId int
While (@Lo5 <= @Hi5)
Begin

set @NomUsuarioTemp = (select nomUsuario from @visibilidad where ID = @Lo5)
set @tempCuenta = (select tempCuent from @visibilidad where ID = @Lo5)
set @CuenId = (select ID from dbo.cuentaAhorro where numeroCuenta = @tempCuenta)
set @UsuarioId = (select ID from dbo.usuario where nombreUsuario = @NomUsuarioTemp)

insert into dbo.puedeVer(usuarioId, cuentaAhorroId)
values(@UsuarioId, @CuenId)

set @Lo5 = @Lo5+1
End;

--select * from dbo.puedeVer
--Delete from dbo.puedeVer
--DBCC checkident('dbo.puedeVer',reseed,0)
