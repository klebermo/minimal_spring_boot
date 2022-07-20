INSERT INTO Autorizacao (nome) VALUES ('USER');
INSERT INTO Autorizacao (nome) VALUES ('ADMIN');

INSERT INTO Credencial (nome) VALUES ('USER');
INSERT INTO Credencial (nome) VALUES ('ADMIN');

INSERT INTO Credencial_Autorizacoes VALUES (1,1);
INSERT INTO Credencial_Autorizacoes VALUES (2,2);

INSERT INTO Usuario (username, password, first_name, last_name, email) VALUES ('user', '202cb962ac59075b964b07152d234b70', 'User', 'User', 'user@mail.com');
INSERT INTO Usuario_Credenciais VALUES (1,1);

INSERT INTO Usuario (username, password, first_name, last_name, email) VALUES ('admin', '202cb962ac59075b964b07152d234b70', 'Admin', 'Admin', 'admin@mail.com');
INSERT INTO Usuario_Credenciais VALUES (2,2);

