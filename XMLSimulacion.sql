USE BDProyecto;

GO

SET NOCOUNT ON

declare @genre date
 set @genre = '2020-07-01'
DECLARE @fechasSimulacion TABLE
(ID INT IDENTITY (1,1),
fecha DATE)

INSERT INTO @fechasSimulacion (fecha)
SELECT
	A.Fecha.value('@FechaNacimiento', 'date') AS fechaInicio
FROM(
SELECT CAST(c AS XML) FROM 
OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Proyecto-Java-Vaadi-para-DB\Datos_Tarea_2.xml', SINGLE_BLOB) AS T(c)
) AS S(c)

cross apply c.nodes('Operaciones/FechaOperacion[@Fecha=sql:variable("@genre")]/Persona') AS A(Fecha)-- WHERE cross apply XmlData.nodes('library/books/book[@type=sql:variable("@genre")]') || A.Fecha.value('@Fecha', 'date') = '2020-07-01'

Select * from @fechasSimulacion 

DECLARE  
@lo int = 1, 
@hi int = (SELECT COUNT(*) FROM @fechasSimulacion),
@fechaActual date 

WHILE (@lo <= @hi)
BEGIN

	SELECT @fechaActual = fecha FROM @fechasSimulacion WHERE ID = @lo
	
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
	OPENROWSET(BULK 'F:\ArchivosTec\Cuartosemestre\Bases\Datos_Tarea1.XML', SINGLE_BLOB) AS T(c)
	) AS S(c)

	cross apply c.nodes('Operaciones/FechaOperacion/Fecha/Persona') AS A(Persona) 


	SELECT * FROM dbo.persona
----DELETE FROM [dbo].[persona]
----DBCC checkident('dbo.persona',reseed,0)

END
