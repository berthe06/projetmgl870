CREATE TABLE t_inventory (
id bigint(20) NOT NULL AUTO_INCREMENT,
sku_code varchar(255),
price decimal(19, 2),
quantity int(11),
PRIMARY KEY  (id)
);