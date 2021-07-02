CREATE DATABASE IF NOT EXISTS `logistic_system`;

USE `logistic_system`;

CREATE TABLE IF NOT EXISTS `users_role` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `role` ENUM('GUEST', 'ADMINISTRATOR', 'SHIPPER', 'CARRIER') NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`role`)
);

CREATE TABLE IF NOT EXISTS `users_status` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `status` ENUM('NEW', 'VERIFIED', 'BLOCKED') NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`status`)
);

CREATE TABLE IF NOT EXISTS `users_list` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `email` VARCHAR(32) NOT NULL,
    `pswd_hash` VARCHAR(60) NOT NULL,
    `pswd_salt` VARCHAR(30) NOT NULL,
    `first_name` VARCHAR(64) NOT NULL,
    `last_name` VARCHAR(64) NOT NULL,
    `phone` VARCHAR(15),
    `registration_date` DATETIME DEFAULT current_timestamp,
    `role_id` BIGINT NOT NULL,
    `status_id` BIGINT NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`),
    UNIQUE (`email`),
    FOREIGN KEY (`role_id`) REFERENCES users_role(`id`),
    FOREIGN KEY (`status_id`) REFERENCES users_status(`id`)
);

CREATE TABLE IF NOT EXISTS `orders_list` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `details` BLOB NOT NULL,
    `ready_date` DATETIME NOT NULL,
    `end_date` DATETIME,
    `shipper_id` BIGINT NOT NULL,
    `status` ENUM('NEW', 'FINISHED', 'EXPIRED') NOT NULL DEFAULT 'NEW',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`shipper_id`) REFERENCES users_list(`id`)
);

CREATE TABLE IF NOT EXISTS `offers_list` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `price` DECIMAL(10) NOT NULL,
    `offer_date` DATETIME DEFAULT current_timestamp,
    `order_id` BIGINT NOT NULL,
    `carrier_id` BIGINT NOT NULL,
    `status` ENUM('OFFERED', 'ACCEPTED', 'DENIED') NOT NULL DEFAULT 'OFFERED',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`order_id`) REFERENCES orders_list(`id`),
    FOREIGN KEY (`carrier_id`) REFERENCES users_list(`id`)
);

-- SET UTF8 for tables

ALTER DATABASE `logistic_system` CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `offers_list` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `orders_list` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `users_list` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `users_role` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `users_status` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Insert test data

INSERT INTO `users_role` (`id`, `role`) VALUES (1, 'GUEST'), (2, 'ADMINISTRATOR'), (3, 'SHIPPER'), (4, 'CARRIER');

INSERT INTO `users_status` (`status`) VALUES ('NEW'), ('VERIFIED'), ('BLOCKED');

INSERT INTO `users_list` (`id`, `email`, `pswd_hash`, `pswd_salt`, `first_name`, `last_name`, `role_id`, `status_id`) VALUES (1, 'test@gmail.com', '$2a$10$xZ5Z1E4PMARoa1nPfty8UeF6Z6frKeYwbBHaNkENKm8dJ4ON.AAGO', '$2a$10$xZ5Z1E4PMARoa1nPfty8Ue', 'Злой', 'админ', 2, 2);

INSERT INTO `orders_list` (`id`, `details`, `ready_date`, `end_date`, `shipper_id`) VALUES (1, 'Перевозка из А в Б.', '2021/06/18', '2021/07/18', 1), (2, 'Перевозка из Б в В.', '2021/06/20', '2021/07/20', 1);

INSERT INTO `offers_list` (`id`, `price`, `offer_date`, `order_id`, `carrier_id`) VALUES (1, 10000, '2021/06/16', 2, 1);
