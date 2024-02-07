CREATE TABLE orders (
		id INT AUTO_INCREMENT PRIMARY KEY,
        cart_id INT,
        order_status ENUM('PLACED', 'SHIPPED', 'DELIVERED'),
        FOREIGN KEY (cart_id) REFERENCES cart(id)
);