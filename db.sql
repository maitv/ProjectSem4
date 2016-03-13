Use master
CREATE DATABASE BookingRoom
GO
USE [BookingRoom]
GO
/****** Object:  Table [dbo].[Booking]    Script Date: 3/13/2016 4:31:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Booking](
	[bookingId] [nvarchar](50) NOT NULL,
	[roomNumber] [nvarchar](20) NULL,
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
/****** Object:  Table [dbo].[BookingRoom]    Script Date: 3/13/2016 4:31:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BookingRoom](
	[bookingId] [nvarchar](50) NULL,
	[roomNumber] [nvarchar](20) NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[BookingService]    Script Date: 3/13/2016 4:31:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BookingService](
	[serviceId] [nvarchar](50) NULL,
	[bookingId] [nvarchar](50) NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Customer]    Script Date: 3/13/2016 4:31:28 PM ******/
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
/****** Object:  Table [dbo].[Payments]    Script Date: 3/13/2016 4:31:28 PM ******/
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
/****** Object:  Table [dbo].[Receipts]    Script Date: 3/13/2016 4:31:28 PM ******/
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
/****** Object:  Table [dbo].[Room]    Script Date: 3/13/2016 4:31:28 PM ******/
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
/****** Object:  Table [dbo].[RoomType]    Script Date: 3/13/2016 4:31:28 PM ******/
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
/****** Object:  Table [dbo].[Service]    Script Date: 3/13/2016 4:31:28 PM ******/
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
GO
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'102', 6, NULL, 0)
GO
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'103', 5, NULL, 0)
GO
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'201', 4, NULL, 0)
GO
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'202', 4, NULL, 0)
GO
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'301', 3, NULL, 0)
GO
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'401', 2, NULL, 0)
GO
INSERT [dbo].[Room] ([roomNumber], [roomTypeId], [roomDesc], [roomStatus]) VALUES (N'501', 1, NULL, 0)
GO
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (1, N'VIP', 1000, NULL, NULL, NULL)
GO
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (2, N'Premium 4', 500, NULL, NULL, NULL)
GO
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (3, N'Premium 2', 300, NULL, NULL, NULL)
GO
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (4, N'Normal 4', 200, NULL, NULL, NULL)
GO
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (5, N'Normal 2', 100, NULL, NULL, NULL)
GO
INSERT [dbo].[RoomType] ([roomTypeId], [roomType], [currentPrice], [imageSmall], [imageLarge], [roomTypeDesc]) VALUES (6, N'Normal 1', 50, NULL, NULL, NULL)
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD FOREIGN KEY([customerId])
REFERENCES [dbo].[Customer] ([customerId])
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD FOREIGN KEY([roomNumber])
REFERENCES [dbo].[Room] ([roomNumber])
GO
ALTER TABLE [dbo].[BookingRoom]  WITH CHECK ADD FOREIGN KEY([bookingId])
REFERENCES [dbo].[Booking] ([bookingId])
GO
ALTER TABLE [dbo].[BookingRoom]  WITH CHECK ADD FOREIGN KEY([roomNumber])
REFERENCES [dbo].[Room] ([roomNumber])
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
