USE [master]
GO
CREATE DATABASE [BookingRoom]
GO
ALTER DATABASE [BookingRoom] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BookingRoom] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BookingRoom] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BookingRoom] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BookingRoom] SET ARITHABORT OFF 
GO
ALTER DATABASE [BookingRoom] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [BookingRoom] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BookingRoom] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BookingRoom] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BookingRoom] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BookingRoom] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BookingRoom] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BookingRoom] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BookingRoom] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BookingRoom] SET  ENABLE_BROKER 
GO
ALTER DATABASE [BookingRoom] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BookingRoom] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BookingRoom] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BookingRoom] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BookingRoom] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BookingRoom] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BookingRoom] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BookingRoom] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [BookingRoom] SET  MULTI_USER 
GO
ALTER DATABASE [BookingRoom] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BookingRoom] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BookingRoom] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BookingRoom] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [BookingRoom]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Booking](
	[bookingId] [nvarchar](50) NOT NULL,
	[customerId] [nvarchar](30) NULL,
	[bookingDate] [date] NULL,
	[checkinDate] [date] NULL,
	[checkoutDate] [date] NULL,
	[bookingComment] [nvarchar](300) NULL,
	[status] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[bookingId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[BookingRoom]    Script Date: 3/20/2016 3:27:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BookingRoom](
	[bookingId] [nvarchar](50) NOT NULL,
	[roomNumber] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_BookingRoom] PRIMARY KEY CLUSTERED 
(
	[bookingId] ASC,
	[roomNumber] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[BookingService]    Script Date: 3/20/2016 3:27:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BookingService](
	[serviceId] [nvarchar](50) NULL,
	[bookingId] [nvarchar](50) NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Customer]    Script Date: 3/20/2016 3:27:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[customerId] [nvarchar](30) NOT NULL,
	[customerName] [nvarchar](50) NULL,
	[customerCountry] [nvarchar](50) NULL,
	[customerIdentityNo] [nvarchar](50) NULL,
	[customerDOB] [date] NULL,
	[customerAddress] [nvarchar](50) NULL,
	[customerPhone] [nvarchar](20) NULL,
	[customerEmail] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[customerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Payments]    Script Date: 3/20/2016 3:27:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Payments](
	[paymentId] [nvarchar](50) NOT NULL,
	[bookingId] [nvarchar](50) NULL,
	[paymentAmount] [float] NULL,
	[amountPaid] [float] NULL,
	[paymentComment] [nvarchar](300) NULL,
PRIMARY KEY CLUSTERED 
(
	[paymentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Receipts]    Script Date: 3/20/2016 3:27:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Receipts](
	[receiptNo] [nvarchar](50) NOT NULL,
	[paymentId] [nvarchar](50) NULL,
	[amount] [float] NULL,
	[datePaid] [date] NULL,
	[receiptComment] [nvarchar](300) NULL,
PRIMARY KEY CLUSTERED 
(
	[receiptNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Room]    Script Date: 3/20/2016 3:27:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Room](
	[roomNumber] [nvarchar](20) NOT NULL,
	[roomTypeId] [int] NULL,
	[roomDesc] [nvarchar](50) NULL,
	[roomStatus] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[roomNumber] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[RoomType]    Script Date: 3/20/2016 3:27:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RoomType](
	[roomTypeId] [int] NOT NULL,
	[roomType] [nvarchar](50) NULL,
	[currentPrice] [float] NULL,
	[imageSmall] [nvarchar](255) NULL,
	[imageLarge] [nvarchar](255) NULL,
	[roomTypeDesc] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[roomTypeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Service]    Script Date: 3/20/2016 3:27:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Service](
	[serviceId] [nvarchar](50) NOT NULL,
	[serviceName] [nvarchar](50) NULL,
	[servicePrice] [float] NULL,
	[serviceDesc] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[serviceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'101', 6, NULL, 0)
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'102', 6, NULL, 0)
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'103', 5, NULL, 0)
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'201', 4, NULL, 0)
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'202', 4, NULL, 0)
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'301', 3, NULL, 0)
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'401', 2, NULL, 0)
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'501', 1, NULL, 0)
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (1, N'VIP', 1000, NULL, NULL, NULL)
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (2, N'Premium 4', 500, NULL, NULL, NULL)
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (3, N'Premium 2', 300, NULL, NULL, NULL)
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (4, N'Normal 4', 200, NULL, NULL, NULL)
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (5, N'Normal 2', 100, NULL, NULL, NULL)
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (6, N'Normal 1', 50, NULL, NULL, NULL)
INSERT [dbo].[Service] ([serviceId], [serviceName], [servicePrice], [serviceDesc]) VALUES (N'1', N'Parking', 10, NULL)
INSERT [dbo].[Service] ([serviceId], [serviceName], [servicePrice], [serviceDesc]) VALUES (N'2', N'S', 300, NULL)
INSERT [dbo].[Service] ([serviceId], [serviceName], [servicePrice], [serviceDesc]) VALUES (N'3', N'Fun', 100, NULL)
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD FOREIGN KEY([customerId])
REFERENCES [dbo].[Customer] ([customerId])
GO
ALTER TABLE [dbo].[BookingRoom]  WITH CHECK ADD  CONSTRAINT [FK__BookingRo__booki__1FCDBCEB] FOREIGN KEY([bookingId])
REFERENCES [dbo].[Booking] ([bookingId])
GO
ALTER TABLE [dbo].[BookingRoom] CHECK CONSTRAINT [FK__BookingRo__booki__1FCDBCEB]
GO
ALTER TABLE [dbo].[BookingRoom]  WITH CHECK ADD  CONSTRAINT [FK__BookingRo__roomN__20C1E124] FOREIGN KEY([roomNumber])
REFERENCES [dbo].[Room] ([roomNumber])
GO
ALTER TABLE [dbo].[BookingRoom] CHECK CONSTRAINT [FK__BookingRo__roomN__20C1E124]
GO
ALTER TABLE [dbo].[BookingService]  WITH CHECK ADD FOREIGN KEY([bookingId])
REFERENCES [dbo].[Booking] ([bookingId])
GO
ALTER TABLE [dbo].[BookingService]  WITH CHECK ADD FOREIGN KEY([serviceId])
REFERENCES [dbo].[Service] ([serviceId])
GO
ALTER TABLE [dbo].[Payments]  WITH CHECK ADD FOREIGN KEY([bookingId])
REFERENCES [dbo].[Booking] ([bookingId])
GO
ALTER TABLE [dbo].[Receipts]  WITH CHECK ADD FOREIGN KEY([paymentId])
REFERENCES [dbo].[Payments] ([paymentId])
GO
ALTER TABLE [dbo].[Room]  WITH CHECK ADD FOREIGN KEY([roomTypeId])
REFERENCES [dbo].[RoomType] ([roomTypeId])
GO
USE [master]
GO
ALTER DATABASE [BookingRoom] SET  READ_WRITE 
GO

USE BookingRoom

CREATE TABLE PayPalAccount(
	id nvarchar(50) PRIMARY KEY,
	username nvarchar(50),
	password nvarchar(50),
	fullname nvarchar(50),
	email nvarchar(50),
	balance float
)
GO
