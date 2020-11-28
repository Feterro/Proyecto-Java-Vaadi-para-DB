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
OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2_Catalogos.xml', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Catalogos/Tipo_Moneda/TipoMoneda') AS A(Tipo_Moneda)

--SELECT * FROM dbo.tipoMoneda
--Delete FROM dbo.tipoMoneda



--INSERT XML en tipoDocIdent-----------------------------------------------------------

INSERT INTO dbo.tipoDocIdent

SELECT 
A.Tipo_Doc.value('@Id', 'int') AS ID,
A.Tipo_Doc.value('@Nombre', 'varchar(50)') AS tipoDoc

FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2_Catalogos.xml', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Catalogos/Tipo_Doc/TipoDocuIdentidad') AS A(Tipo_Doc)

--SELECT * FROM dbo.tipoDocIdent
--Delete FROM dbo.tipoDocIdent


--INSERT XML en parentesco-----------------------------------------------------------

INSERT INTO dbo.parentesco

SELECT 
A.Parentezco.value('@Id', 'int') AS ID,
A.Parentezco.value('@Nombre', 'varchar(30)') AS nombre

FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2_Catalogos.xml', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Catalogos/Parentezcos/Parentezco') AS A(Parentezco)

--SELECT * FROM dbo.parentesco
--Delete FROM dbo.parentesco


--INSERT XML en tipoCuentaAhorro-----------------------------------------------------------

INSERT INTO dbo.tipoCuentaAhorro

SELECT 
A.TipoCuentaAhorro.value('@Id', 'int') AS ID,
A.TipoCuentaAhorro.value('@Nombre', 'varchar(50)') AS nombre,
A.TipoCuentaAhorro.value('@SaldoMinimo', 'decimal(10,4)') AS saldoMinimo,
A.TipoCuentaAhorro.value('@MultaSaldoMin', 'decimal(10,4)') AS multASaldoMinimo,
A.TipoCuentaAhorro.value('@CargoMensual', 'decimal(10,4)') AS cargoMens,
A.TipoCuentaAhorro.value('@NumRetirosHumano', 'int') AS numRetirosHum,
A.TipoCuentaAhorro.value('@NumRetirosAutomatico', 'int') AS numRetirosAuto,
A.TipoCuentaAhorro.value('@Interes', 'int') AS intereses,
A.TipoCuentaAhorro.value('@ComisionHumano', 'decimal(10,4)') AS comisionRetHum,
A.TipoCuentaAhorro.value('@ComisionAutomatico', 'decimal(10,4)') AS comisionRetAuto,
A.TipoCuentaAhorro.value('@IdTipoMoneda', 'int') AS tipoMonedaId
FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2_Catalogos.xml', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Catalogos/Tipo_Cuenta_Ahorros/TipoCuentaAhorro') AS A(TipoCuentaAhorro)

--SELECT * FROM dbo.tipoCuentaAhorro
--Delete FROM dbo.tipoCuentaAhorro