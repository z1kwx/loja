CREATE TABLE agencia(
agencia_id 		BIGINT PRIMARY KEY AUTO_INCREMENT,
name 			VARCHAR(255)		NOT NULL,
cep				VARCHAR(255)		NOT NULL,
complemento		VARCHAR(255)				,
uf				VARCHAR(255)				,
localidade		VARCHAR(255)				,
bairro			VARCHAR(255)				,
logradouro		VARCHAR(255)				
);	