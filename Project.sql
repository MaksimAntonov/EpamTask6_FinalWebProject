DROP DATABASE `logistic_system`;

CREATE DATABASE IF NOT EXISTS `logistic_system`;

USE `logistic_system`;

CREATE TABLE IF NOT EXISTS `users_role` (
    `role_id` BIGINT AUTO_INCREMENT NOT NULL,
    `role_name` ENUM('GUEST', 'ADMINISTRATOR', 'SHIPPER', 'CARRIER') NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE (`role_name`)
);

CREATE TABLE IF NOT EXISTS `users_status` (
    `status_id` BIGINT AUTO_INCREMENT NOT NULL,
    `status_name` ENUM('NEW', 'VERIFIED', 'BLOCKED') NOT NULL,
    PRIMARY KEY (`status_id`),
    UNIQUE (`status_name`)
);

CREATE TABLE IF NOT EXISTS `users_list` (
    `user_id` BIGINT AUTO_INCREMENT NOT NULL,
    `user_email` VARCHAR(32) NOT NULL,
    `user_pswd_hash` VARCHAR(60) NOT NULL,
    `user_pswd_salt` VARCHAR(30) NOT NULL,
    `user_first_name` VARCHAR(64) NOT NULL,
    `user_last_name` VARCHAR(64) NOT NULL,
    `user_phone` VARCHAR(15),
    `user_registration_date` DATETIME DEFAULT current_timestamp,
    `user_role_id` BIGINT NOT NULL,
    `user_status_id` BIGINT NOT NULL DEFAULT 1,
    PRIMARY KEY (`user_id`),
    UNIQUE (`user_email`),
    FOREIGN KEY (`user_role_id`) REFERENCES users_role(`role_id`),
    FOREIGN KEY (`user_status_id`) REFERENCES users_status(`status_id`)
);

CREATE TABLE IF NOT EXISTS `orders_list` (
    `order_id` BIGINT AUTO_INCREMENT NOT NULL,
    `order_details` BLOB NOT NULL,
    `order_ready_date` DATETIME NOT NULL,
    `order_end_date` DATETIME,
    `order_shipper_id` BIGINT NOT NULL,
    `order_status` ENUM('NEW', 'FINISHED', 'EXPIRED') NOT NULL DEFAULT 'NEW',
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`order_shipper_id`) REFERENCES users_list(`user_id`)
);

CREATE TABLE IF NOT EXISTS `offers_list` (
    `offer_id` BIGINT AUTO_INCREMENT NOT NULL,
    `offer_price` DECIMAL(10) NOT NULL,
    `offer_date` DATETIME DEFAULT current_timestamp,
    `offer_order_id` BIGINT NOT NULL,
    `offer_carrier_id` BIGINT NOT NULL,
    `offer_status` ENUM('OFFERED', 'ACCEPTED', 'DENIED') NOT NULL DEFAULT 'OFFERED',
    PRIMARY KEY (`offer_id`),
    FOREIGN KEY (`offer_order_id`) REFERENCES orders_list(`order_id`),
    FOREIGN KEY (`offer_carrier_id`) REFERENCES users_list(`user_id`)
);

-- SET UTF8 for tables

ALTER DATABASE `logistic_system` CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `offers_list` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `orders_list` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `users_list` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `users_role` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `users_status` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Insert test data

INSERT INTO `users_role` (`role_name`) VALUES ('GUEST'), ('ADMINISTRATOR'), ('SHIPPER'), ('CARRIER');

INSERT INTO `users_status` (`status_name`) VALUES ('NEW'), ('VERIFIED'), ('BLOCKED');

INSERT INTO `users_list` (`user_id`, `user_email`, `user_pswd_hash`, `user_pswd_salt`, `user_first_name`, `user_last_name`, `user_role_id`, `user_status_id`) VALUES (1, 'test@gmail.com', '$2a$10$xZ5Z1E4PMARoa1nPfty8UeF6Z6frKeYwbBHaNkENKm8dJ4ON.AAGO', '$2a$10$xZ5Z1E4PMARoa1nPfty8Ue', 'Злой', 'админ', 2, 2);

INSERT INTO `orders_list` (`order_id`, `order_details`, `order_ready_date`, `order_end_date`, `order_shipper_id`) VALUES (1, 'Перевозка из А в Б.', '2021/06/18', '2021/07/18', 1), (2, 'Перевозка из Б в В.', '2021/06/20', '2021/07/20', 1);

INSERT INTO `offers_list` (`offer_id`, `offer_price`, `offer_date`, `offer_order_id`, `offer_carrier_id`) VALUES (1, 10000, '2021/06/16', 2, 1);
