USE [master]
GO
/****** Object:  Database [BDProyecto]    Script Date: 11/4/2020 11:11:08 PM ******/
CREATE DATABASE [BDProyecto]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BDProyecto_Data', FILENAME = N'G:\Sql\MSSQL15.MSSQLSERVER\MSSQL\DATA\BDProyecto.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'BDProyecto_Log', FILENAME = N'G:\Sql\MSSQL15.MSSQLSERVER\MSSQL\DATA\BDProyecto.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [BDProyecto] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BDProyecto].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BDProyecto] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BDProyecto] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BDProyecto] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BDProyecto] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BDProyecto] SET ARITHABORT OFF 
GO
ALTER DATABASE [BDProyecto] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [BDProyecto] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BDProyecto] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BDProyecto] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BDProyecto] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BDProyecto] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BDProyecto] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BDProyecto] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BDProyecto] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BDProyecto] SET  ENABLE_BROKER 
GO
ALTER DATABASE [BDProyecto] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BDProyecto] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BDProyecto] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BDProyecto] SET ALLOW_SNAPSHOT_ISOLATION ON 
GO
ALTER DATABASE [BDProyecto] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BDProyecto] SET READ_COMMITTED_SNAPSHOT ON 
GO
ALTER DATABASE [BDProyecto] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BDProyecto] SET RECOVERY FULL 
GO
ALTER DATABASE [BDProyecto] SET  MULTI_USER 
GO
ALTER DATABASE [BDProyecto] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BDProyecto] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BDProyecto] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BDProyecto] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [BDProyecto] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'BDProyecto', N'ON'
GO
ALTER DATABASE [BDProyecto] SET QUERY_STORE = ON
GO
ALTER DATABASE [BDProyecto] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 100, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [BDProyecto]
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 8;
GO
USE [BDProyecto]
GO
/****** Object:  Table [dbo].[beneficiario]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[beneficiario](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[porcentaje] [int] NOT NULL,
	[personaId] [int] NOT NULL,
	[cuentaId] [int] NOT NULL,
	[activo] [bit] NOT NULL,
	[parentescoId] [int] NOT NULL,
 CONSTRAINT [PK_beneficiario] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cuentaAhorro]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cuentaAhorro](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[tipoCuentaId] [int] NOT NULL,
	[fechaApertura] [date] NOT NULL,
	[personaId] [int] NOT NULL,
	[numeroCuenta] [int] NOT NULL,
	[saldo] [decimal](12, 4) NOT NULL,
 CONSTRAINT [PK_cuentaAhorro] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cuentaObjetivo]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cuentaObjetivo](
	[id] [int] NOT NULL,
	[objetivo] [varchar](100) NOT NULL,
	[fechaIn] [date] NOT NULL,
	[fechaFin] [date] NOT NULL,
	[saldo] [decimal](10, 4) NOT NULL,
	[cuota] [decimal](10, 4) NOT NULL,
	[interesAcumulado] [decimal](10, 4) NOT NULL,
	[cuentaAhorroId] [int] NOT NULL,
	[movimientoCuenObId] [int] NOT NULL,
	[movimentoCuenObIntId] [int] NOT NULL,
 CONSTRAINT [PK_cuentaObjetivo] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[estadoCuenta]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[estadoCuenta](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[fechaIni] [date] NOT NULL,
	[fechaFin] [date] NOT NULL,
	[cuentaAhorroId] [int] NOT NULL,
	[saldoIni] [decimal](12, 4) NOT NULL,
	[saldoFin] [decimal](12, 4) NOT NULL,
 CONSTRAINT [PK_estadoCuentas] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[movCoInt]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[movCoInt](
	[id] [int] NOT NULL,
	[fecha] [date] NOT NULL,
	[monto] [decimal](10, 4) NOT NULL,
	[tipoMovCoIntId] [int] NOT NULL,
 CONSTRAINT [PK_movCoInt] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[movimiento]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[movimiento](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[tipoMovimientoId] [int] NOT NULL,
	[cuentaAhorroId] [int] NOT NULL,
	[fechaMovimiento] [date] NOT NULL,
	[nuevoSaldo] [decimal](10, 4) NOT NULL,
	[estadoCuentaId] [int] NOT NULL,
	[monto] [decimal](10, 4) NOT NULL,
 CONSTRAINT [PK_movimiento] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[movimientoCuenOb]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[movimientoCuenOb](
	[Id] [int] NOT NULL,
	[fecha] [date] NOT NULL,
	[monto] [decimal](10, 4) NOT NULL,
	[tipoMovimientoCoId] [int] NOT NULL,
 CONSTRAINT [PK_MovimientoCuenOb] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[parentesco]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[parentesco](
	[ID] [int] NOT NULL,
	[nombre] [varchar](30) NOT NULL,
 CONSTRAINT [PK_parentescos] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[persona]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[persona](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[email] [varchar](50) NOT NULL,
	[nacimiento] [date] NOT NULL,
	[telefono1] [int] NOT NULL,
	[telefono2] [int] NOT NULL,
	[tipoDocIdent] [int] NOT NULL,
	[valorDocIdent] [int] NOT NULL,
 CONSTRAINT [PK_Persona] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[puedeVer]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[puedeVer](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[usuarioId] [int] NOT NULL,
	[cuentaAhorroId] [int] NOT NULL,
 CONSTRAINT [PK_puedeVer] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipoCuentaAhorro]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipoCuentaAhorro](
	[ID] [int] NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[saldMin] [decimal](10, 4) NOT NULL,
	[multSalMin] [decimal](10, 4) NOT NULL,
	[montMensCargServ] [decimal](10, 4) NOT NULL,
	[maxOpeHum] [int] NOT NULL,
	[maxOpeAuto] [int] NOT NULL,
	[tasaIntAnu] [int] NOT NULL,
	[montoComHum] [decimal](10, 4) NOT NULL,
	[montoComAuto] [decimal](10, 4) NOT NULL,
	[tipoMonedaId] [int] NOT NULL,
 CONSTRAINT [PK_tipoCuentaAhorros] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipoDocIdent]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipoDocIdent](
	[ID] [int] NOT NULL,
	[tipoDoc] [varchar](50) NOT NULL,
 CONSTRAINT [PK_tipoDocIdents] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipoMoneda]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipoMoneda](
	[ID] [int] NOT NULL,
	[nombre] [varchar](30) NOT NULL,
	[simbolo] [varchar](10) NOT NULL,
 CONSTRAINT [PK_tipoMoneda] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipoMovCo]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipoMovCo](
	[id] [int] NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[tipo] [bit] NOT NULL,
 CONSTRAINT [PK_tipoMovCo] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TipoMovimiento]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TipoMovimiento](
	[Id] [int] NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[tipo] [bit] NOT NULL,
 CONSTRAINT [PK_TipoMovimiento] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipoMovimientoCoInt]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipoMovimientoCoInt](
	[id] [int] NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[tipo] [bit] NOT NULL,
 CONSTRAINT [PK_tipoMovimientoCoInt] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usuario]    Script Date: 11/4/2020 11:11:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usuario](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[personaId] [int] NOT NULL,
	[tipoUsuario] [bit] NOT NULL,
	[nombreUsuario] [varchar](50) NOT NULL,
	[contrasenna] [varchar](50) NOT NULL,
 CONSTRAINT [PK_usuario] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[cuentaAhorro] ADD  CONSTRAINT [default_tipoCuenta1]  DEFAULT ((3)) FOR [tipoCuentaId]
GO
ALTER TABLE [dbo].[beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_beneficiario_cuentaAhorro] FOREIGN KEY([cuentaId])
REFERENCES [dbo].[cuentaAhorro] ([ID])
GO
ALTER TABLE [dbo].[beneficiario] CHECK CONSTRAINT [FK_beneficiario_cuentaAhorro]
GO
ALTER TABLE [dbo].[beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_beneficiario_parentesco] FOREIGN KEY([parentescoId])
REFERENCES [dbo].[parentesco] ([ID])
GO
ALTER TABLE [dbo].[beneficiario] CHECK CONSTRAINT [FK_beneficiario_parentesco]
GO
ALTER TABLE [dbo].[beneficiario]  WITH CHECK ADD  CONSTRAINT [FK_beneficiario_persona] FOREIGN KEY([personaId])
REFERENCES [dbo].[persona] ([ID])
GO
ALTER TABLE [dbo].[beneficiario] CHECK CONSTRAINT [FK_beneficiario_persona]
GO
ALTER TABLE [dbo].[cuentaAhorro]  WITH CHECK ADD  CONSTRAINT [FK_cuentaAhorro_persona] FOREIGN KEY([personaId])
REFERENCES [dbo].[persona] ([ID])
GO
ALTER TABLE [dbo].[cuentaAhorro] CHECK CONSTRAINT [FK_cuentaAhorro_persona]
GO
ALTER TABLE [dbo].[cuentaAhorro]  WITH CHECK ADD  CONSTRAINT [FK_cuentaAhorro_tipoCuentaAhorro] FOREIGN KEY([tipoCuentaId])
REFERENCES [dbo].[tipoCuentaAhorro] ([ID])
GO
ALTER TABLE [dbo].[cuentaAhorro] CHECK CONSTRAINT [FK_cuentaAhorro_tipoCuentaAhorro]
GO
ALTER TABLE [dbo].[cuentaObjetivo]  WITH CHECK ADD  CONSTRAINT [FK_cuentaObjetivo_cuentaAhorro] FOREIGN KEY([cuentaAhorroId])
REFERENCES [dbo].[cuentaAhorro] ([ID])
GO
ALTER TABLE [dbo].[cuentaObjetivo] CHECK CONSTRAINT [FK_cuentaObjetivo_cuentaAhorro]
GO
ALTER TABLE [dbo].[cuentaObjetivo]  WITH CHECK ADD  CONSTRAINT [FK_cuentaObjetivo_movCoInt] FOREIGN KEY([movimentoCuenObIntId])
REFERENCES [dbo].[movCoInt] ([id])
GO
ALTER TABLE [dbo].[cuentaObjetivo] CHECK CONSTRAINT [FK_cuentaObjetivo_movCoInt]
GO
ALTER TABLE [dbo].[cuentaObjetivo]  WITH CHECK ADD  CONSTRAINT [FK_cuentaObjetivo_movimientoCuenOb] FOREIGN KEY([movimentoCuenObIntId])
REFERENCES [dbo].[movimientoCuenOb] ([Id])
GO
ALTER TABLE [dbo].[cuentaObjetivo] CHECK CONSTRAINT [FK_cuentaObjetivo_movimientoCuenOb]
GO
ALTER TABLE [dbo].[estadoCuenta]  WITH CHECK ADD  CONSTRAINT [FK_estadoCuentas_cuentaAhorro] FOREIGN KEY([cuentaAhorroId])
REFERENCES [dbo].[cuentaAhorro] ([ID])
GO
ALTER TABLE [dbo].[estadoCuenta] CHECK CONSTRAINT [FK_estadoCuentas_cuentaAhorro]
GO
ALTER TABLE [dbo].[movCoInt]  WITH CHECK ADD  CONSTRAINT [FK_movCoInt_tipoMovimientoCoInt] FOREIGN KEY([tipoMovCoIntId])
REFERENCES [dbo].[tipoMovimientoCoInt] ([id])
GO
ALTER TABLE [dbo].[movCoInt] CHECK CONSTRAINT [FK_movCoInt_tipoMovimientoCoInt]
GO
ALTER TABLE [dbo].[movimiento]  WITH CHECK ADD  CONSTRAINT [FK_movimiento_cuentaAhorro] FOREIGN KEY([tipoMovimientoId])
REFERENCES [dbo].[TipoMovimiento] ([Id])
GO
ALTER TABLE [dbo].[movimiento] CHECK CONSTRAINT [FK_movimiento_cuentaAhorro]
GO
ALTER TABLE [dbo].[movimiento]  WITH CHECK ADD  CONSTRAINT [FK_movimiento_cuentaAhorro1] FOREIGN KEY([cuentaAhorroId])
REFERENCES [dbo].[cuentaAhorro] ([ID])
GO
ALTER TABLE [dbo].[movimiento] CHECK CONSTRAINT [FK_movimiento_cuentaAhorro1]
GO
ALTER TABLE [dbo].[movimiento]  WITH CHECK ADD  CONSTRAINT [FK_movimiento_estadoCuenta] FOREIGN KEY([estadoCuentaId])
REFERENCES [dbo].[estadoCuenta] ([ID])
GO
ALTER TABLE [dbo].[movimiento] CHECK CONSTRAINT [FK_movimiento_estadoCuenta]
GO
ALTER TABLE [dbo].[movimientoCuenOb]  WITH CHECK ADD  CONSTRAINT [FK_MovimientoCuenOb_tipoMovCo] FOREIGN KEY([tipoMovimientoCoId])
REFERENCES [dbo].[tipoMovCo] ([id])
GO
ALTER TABLE [dbo].[movimientoCuenOb] CHECK CONSTRAINT [FK_MovimientoCuenOb_tipoMovCo]
GO
ALTER TABLE [dbo].[persona]  WITH CHECK ADD  CONSTRAINT [FK_Persona_tipoDocIdent] FOREIGN KEY([tipoDocIdent])
REFERENCES [dbo].[tipoDocIdent] ([ID])
GO
ALTER TABLE [dbo].[persona] CHECK CONSTRAINT [FK_Persona_tipoDocIdent]
GO
ALTER TABLE [dbo].[puedeVer]  WITH CHECK ADD  CONSTRAINT [FK_puedeVer_cuentaAhorro] FOREIGN KEY([cuentaAhorroId])
REFERENCES [dbo].[cuentaAhorro] ([ID])
GO
ALTER TABLE [dbo].[puedeVer] CHECK CONSTRAINT [FK_puedeVer_cuentaAhorro]
GO
ALTER TABLE [dbo].[puedeVer]  WITH CHECK ADD  CONSTRAINT [FK_puedeVer_usuario] FOREIGN KEY([usuarioId])
REFERENCES [dbo].[usuario] ([ID])
GO
ALTER TABLE [dbo].[puedeVer] CHECK CONSTRAINT [FK_puedeVer_usuario]
GO
ALTER TABLE [dbo].[tipoCuentaAhorro]  WITH CHECK ADD  CONSTRAINT [FK_tipoCuentaAhorro_tipoMoneda] FOREIGN KEY([tipoMonedaId])
REFERENCES [dbo].[tipoMoneda] ([ID])
GO
ALTER TABLE [dbo].[tipoCuentaAhorro] CHECK CONSTRAINT [FK_tipoCuentaAhorro_tipoMoneda]
GO
ALTER TABLE [dbo].[usuario]  WITH CHECK ADD  CONSTRAINT [FK_usuario_persona] FOREIGN KEY([personaId])
REFERENCES [dbo].[persona] ([ID])
GO
ALTER TABLE [dbo].[usuario] CHECK CONSTRAINT [FK_usuario_persona]
GO
USE [master]
GO
ALTER DATABASE [BDProyecto] SET  READ_WRITE 
GO
