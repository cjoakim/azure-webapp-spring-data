DROP TABLE IF EXISTS dbo.Cats;
GO

CREATE TABLE dbo.Cats(
  id    INT  IDENTITY  PRIMARY KEY,
  name  varchar(128)
);

INSERT INTO dbo.Cats (name) VALUES ('Maybelline');
INSERT INTO dbo.Cats (name) VALUES ('Elsa');
INSERT INTO dbo.Cats (name) VALUES ('Miles');

select * from dbo.Cats;
