set ansi_padding on
go
set ansi_nulls on
go
Set quoted_identifier on
go

create database CryptoWatch
go
use CryptoWatch
go

drop database CryptoWatch1
go


create table [dbo].[Users]
(
	[ID_Users] [int] not null identity(1,1),
	[Login_Users] [varchar] (500) not null,
	[Password_Users] [varchar] (500) not null,
	[Nickname_Users] [varchar] (500) not null,
	[Email_Users] [varchar] (500) not null
	constraint [PK_Users] primary key clustered ([ID_Users] ASC) on [PRIMARY],
	constraint [UQ_Email_Users] unique ([Email_Users]),
	constraint [UQ_Nickname_Users] unique ([Nickname_Users]),
	constraint [UQ_Login_Users] unique ([Login_Users]),
	constraint [UQ_Password_Users] unique ([Password_Users])
)
go

insert into [dbo].[Users] ([Login_Users], [Password_Users], [Nickname_Users], [Email_Users]) values ('test', 'test123', 'anonymous', 'test@mail.ru')
go

select [Login_Users], [Password_Users], [Nickname_Users], [Email_Users] from [dbo].[Users]
go

create table [dbo].[TrackingList]
(
	[ID_TrackingList] [int] not null identity(1,1),
	[Name_Crypto] [varchar] (500) not null,
	[Users_ID] [int] not null
	constraint [PK_TrackingList] primary key clustered ([ID_TrackingList] ASC) on [PRIMARY],
	constraint [FK_Users_TrackingList] foreign key ([Users_ID]) references [dbo].[Users] ([ID_Users])
)
go

insert into [dbo].[TrackingList] ([Name_Crypto], [Users_ID]) values ('BTC',1)
go

select [Name_Crypto], [Users_ID] from [dbo].[TrackingList]
inner join [dbo].[Users] on [Users_ID] = [ID_Users]
go

create table [dbo].[Comment]
(
	[ID_Comment] [int] not null identity(1,1),
	[Com] [varchar] (1000) not null,
	[Users_ID] [int] not null
	constraint [PK_Comment] primary key clustered ([ID_Comment] ASC) on [PRIMARY],
	constraint [FK_Users_Com] foreign key ([Users_ID]) references [dbo].[Users] ([ID_Users])
)
go

insert into [dbo].[Comment] ([Com], [Users_ID]) values ('ѕокупайте биткоин, он завтра будет стоить 100000000$',1)
go

select [Com], [Users_ID] from [dbo].[Comment]
inner join [dbo].[Users] on [Users_ID] = [ID_Users]
go