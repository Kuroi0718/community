# community
請先於MSSQLServer建立DataBase：CREATE DATABASE Community COLLATE Chinese_Taiwan_Stroke_CS_AS
再把專案中applications.properties修改資料庫登入資料
################ DataSource config #################
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=Community;trustServerCertificate=true
spring.datasource.username=
spring.datasource.password=
