Create database BookingRoom

go

use BookingRoom

go

Create table RoomType
(
	roomTypeId int primary key,
	roomType nvarchar(50),
	currentPrice float,
	imageSmall nvarchar(255),
	imageLarge nvarchar(255),
	roomTypeDesc nvarchar(50)

)
go

Create table Room
(
	roomNumber nvarchar(20) primary key,
	roomTypeId int,
	roomDesc nvarchar(50),
	roomStatus int,
)

alter table Room add foreign key (roomTypeId) references RoomType(roomTypeId)

Create table Customer
(
	customerId nvarchar(30) primary key,
	customerName nvarchar(50),
	customerIndentifyCard nvarchar(50),
	customerDOB date,
	customerAddress nvarchar(50),
	customerPhone nvarchar(20),
	customerEmail nvarchar(50)
)
go

Create table Booking
(
	bookingId nvarchar(50) primary key,
	roomNumber nvarchar(20),
	customerId nvarchar(30),
	bookingDate date,
	checkinDate date,
	checkoutDate date,
	bookingComment nvarchar(300),
	[status] int
)
go

alter table Booking add foreign key (customerId) references Customer(customerId)

go

alter table Booking add foreign key (roomNumber) references Room(roomNumber)

go

Create table BookingRoom
(
	bookingId nvarchar(50),
	roomNumber nvarchar(20)
)

alter table BookingRoom add foreign key (bookingId) references Booking(bookingId)

go

alter table BookingRoom add foreign key (roomNumber) references Room(roomNumber)

go

--alter table Booking add foreign key (roomNumber) references Room(roomNumber)

Create table [Service]
(
	serviceId nvarchar(50) primary key,
	serviceName nvarchar(50),
	servicePrice float,
	serviceDesc nvarchar(50)
)
go

Create table BookingService
(
	serviceId nvarchar(50),
	bookingId nvarchar(50),
)
go

alter table BookingService add foreign key (bookingId) references Booking(bookingId)

go

alter table BookingService add foreign key (serviceId) references Service(serviceId)

go

Create table Payments
(
	paymentId nvarchar(50) primary key,
	bookingId nvarchar(50),
	paymentAmount float,
	amountPaid float,
	paymentComment nvarchar(300)
)
go

alter table Payments add foreign key (bookingId) references Booking(bookingId)

go

Create table Receipts
(
	receiptNo nvarchar(50) primary key,
	paymentId nvarchar(50),
	amount float,
	datePaid date,
	receiptComment nvarchar(300)
)
go

alter table Receipts add foreign key (paymentId) references Payments(paymentId)

go

