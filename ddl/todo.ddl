DROP TABLE IF EXISTS dbo.Todo;
GO

CREATE TABLE dbo.Todo(
  id       INT  IDENTITY  PRIMARY KEY,
  priority int NOT NULL,
  status   int NOT NULL,
  name     varchar(128) NOT NULL,
  created  datetime,
  updated  datetime
);

INSERT INTO dbo.Todo(priority,status,name,created,updated) VALUES (3,9,'Learn Spring Boot',   CONVERT(datetime, '2019-08-01T06:00:00.000', 126), CONVERT(datetime, '2019-10-01T18:00:00.000', 126));
INSERT INTO dbo.Todo(priority,status,name,created,updated) VALUES (4,1,'Learn Spring Data',   CONVERT(datetime, '2019-12-01T07:00:00.000', 126), NULL);
INSERT INTO dbo.Todo(priority,status,name,created,updated) VALUES (1,1,'Deploy App to Azure', CONVERT(datetime, '2020-01-01T08:00:00.000', 126), NULL);

select * from dbo.Todo;
