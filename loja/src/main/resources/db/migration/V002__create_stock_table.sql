CREATE TABLE stock(
id					BIGINT PRIMARY KEY AUTO_INCREMENT,
agencia_id			BIGINT		NOT NULL,
product_id			BIGINT		NOT NULL,
quantity_in_stock	BIGINT		NOT	NULL
);