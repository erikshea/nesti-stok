-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  jeu. 14 jan. 2021 à 15:15
-- Version du serveur :  8.0.18
-- Version de PHP :  7.4.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `nesti stock`
--

-- --------------------------------------------------------

--
-- Structure de la table `allows`
--

DROP TABLE IF EXISTS `allows`;
CREATE TABLE IF NOT EXISTS `allows` (
  `id_product` int(11) NOT NULL,
  `id_unit` int(11) NOT NULL,
  PRIMARY KEY (`id_product`,`id_unit`),
  KEY `id_unit` (`id_unit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `allows`
--

INSERT INTO `allows` (`id_product`, `id_unit`) VALUES
(1, 1),
(2, 2),
(5, 2),
(6, 2),
(2, 3),
(5, 3),
(6, 3);

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `id_article` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(30) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `ean` varchar(30) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `stock` int(11) NOT NULL,
  `id_packaging` int(11) NOT NULL,
  `id_unit` int(11) NOT NULL,
  `id_product` int(11) NOT NULL,
  PRIMARY KEY (`id_article`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `ean` (`ean`),
  KEY `id_packaging` (`id_packaging`),
  KEY `id_unit` (`id_unit`),
  KEY `id_product` (`id_product`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `article`
--

INSERT INTO `article` (`id_article`, `code`, `name`, `ean`, `weight`, `quantity`, `stock`, `id_packaging`, `id_unit`, `id_product`) VALUES
(2, 'CASS125', 'Casserole anti rouille parfaite', '3387964587452', 155.25, 1, 25, 1, 1, 4),
(3, 'FARIN152', '1 sac de farine bio écolo', '3896547852364', 1, 1, 6, 3, 2, 2),
(4, 'OEUF6', 'boite de 6 oeufs de poule élevée a l\'air', '3621458745695', 25, 6, 19, 1, 1, 1),
(5, 'OEUF12', 'boite de 12 oeufs de poule élevée a l\'air', '396458762147', 50, 12, 3, 1, 1, 1),
(6, 'CHOCHOCV1', 'tablette de chocolat au lait extra fin', '3215469874562', 645.3, 1, 12, 5, 1, 6),
(7, 'FOUE152', 'fouet en bois ', '3365478965412', 1.26, 1, 55, 5, 1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE IF NOT EXISTS `ingredient` (
  `id_product` int(11) NOT NULL,
  PRIMARY KEY (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ingredient`
--

INSERT INTO `ingredient` (`id_product`) VALUES
(1),
(2),
(5),
(6);

-- --------------------------------------------------------

--
-- Structure de la table `offers`
--

DROP TABLE IF EXISTS `offers`;
CREATE TABLE IF NOT EXISTS `offers` (
  `id_article` int(11) NOT NULL,
  `id_supplier` int(11) NOT NULL,
  `price` double DEFAULT NULL,
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_article`,`id_supplier`),
  KEY `id_supplier` (`id_supplier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `offers`
--

INSERT INTO `offers` (`id_article`, `id_supplier`, `price`, `start_date`) VALUES
(2, 1, 12.5, '2021-01-01 00:00:00'),
(3, 2, 4.75, '2021-01-14 15:36:12'),
(4, 2, 2.5, '2021-01-14 15:36:12'),
(4, 3, 2.8, '2021-01-14 15:36:12'),
(5, 3, 4.65, '2021-01-14 15:36:12'),
(6, 2, 3.25, '2021-01-14 15:36:12'),
(7, 1, 8.5, '2021-01-14 15:36:12');

-- --------------------------------------------------------

--
-- Structure de la table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `id_orders` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(10) NOT NULL,
  `date_order` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_delivery` date DEFAULT NULL,
  `id_supplier` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id_orders`),
  KEY `id_supplier` (`id_supplier`),
  KEY `id_user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `orders`
--

INSERT INTO `orders` (`id_orders`, `number`, `date_order`, `date_delivery`, `id_supplier`, `id_user`) VALUES
(4, '2AR5', '2021-01-14 15:45:01', '2021-01-28', 1, 2),
(5, '006R', '2021-01-14 15:45:01', '2021-02-25', 2, 2),
(6, '524P', '2021-01-14 15:45:01', '2021-01-15', 3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `orders_article`
--

DROP TABLE IF EXISTS `orders_article`;
CREATE TABLE IF NOT EXISTS `orders_article` (
  `id_article` int(11) NOT NULL,
  `id_orders` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_article`,`id_orders`),
  KEY `id_orders` (`id_orders`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `orders_article`
--

INSERT INTO `orders_article` (`id_article`, `id_orders`, `quantity`) VALUES
(2, 4, 3),
(3, 5, 3),
(4, 6, 6),
(5, 6, 6),
(6, 5, 13),
(7, 4, 1);

-- --------------------------------------------------------

--
-- Structure de la table `packaging`
--

DROP TABLE IF EXISTS `packaging`;
CREATE TABLE IF NOT EXISTS `packaging` (
  `id_packaging` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_packaging`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `packaging`
--

INSERT INTO `packaging` (`id_packaging`, `name`) VALUES
(1, 'boite'),
(2, 'bouteille'),
(4, 'carton'),
(5, 'film plastique'),
(3, 'sac');

-- --------------------------------------------------------

--
-- Structure de la table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id_product` int(11) NOT NULL AUTO_INCREMENT,
  `reference` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_product`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `product`
--

INSERT INTO `product` (`id_product`, `reference`, `name`) VALUES
(1, 'OEUF125F', 'oeuf de poule bio'),
(2, 'FARI12', 'Farine'),
(3, 'FOU54', 'Fouet en bois'),
(4, 'CASS14SU', 'Casserole anti adhérente'),
(5, 'SUC8', 'sucre en poudre'),
(6, 'CHOCHOC', 'chocolat au lait extra fin');

-- --------------------------------------------------------

--
-- Structure de la table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE IF NOT EXISTS `supplier` (
  `id_supplier` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `address1` varchar(50) DEFAULT NULL,
  `address2` varchar(50) DEFAULT NULL,
  `zip_code` char(5) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `contact_name` varchar(50) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_supplier`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `name`, `address1`, `address2`, `zip_code`, `city`, `contact_name`, `country`, `phone_number`) VALUES
(1, 'TOUT POUR LA CUISINE', '1 rue des couteaux', 'bat E', '34000', 'Montpellier', 'jean Charles', 'FRANCE', '0645879487'),
(2, 'ACME', 'avenue des lilas', NULL, '13013', 'Marseille', 'Martine', 'france', '0697854645'),
(3, 'O\'Kuistot', 'Seven Avenue', NULL, '15F21', 'New York', 'Kathy', 'USA', '001-212-324-415');

-- --------------------------------------------------------

--
-- Structure de la table `unit`
--

DROP TABLE IF EXISTS `unit`;
CREATE TABLE IF NOT EXISTS `unit` (
  `id_unit` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_unit`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `unit`
--

INSERT INTO `unit` (`id_unit`, `name`) VALUES
(3, 'gramme'),
(2, 'kilos'),
(4, 'litre'),
(1, 'pièce');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password_hash` varchar(50) NOT NULL,
  `date_creation` datetime DEFAULT CURRENT_TIMESTAMP,
  `role` enum('super_administrator','administrator') DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id_user`, `login`, `name`, `password_hash`, `date_creation`, `role`) VALUES
(1, 'james', 'James Bond', '123', '2021-01-14 15:39:37', 'super_administrator'),
(2, 'polo', 'Jean Paul', '123', '2021-01-14 15:39:37', 'administrator');

-- --------------------------------------------------------

--
-- Structure de la table `utensil`
--

DROP TABLE IF EXISTS `utensil`;
CREATE TABLE IF NOT EXISTS `utensil` (
  `id_product` int(11) NOT NULL,
  PRIMARY KEY (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utensil`
--

INSERT INTO `utensil` (`id_product`) VALUES
(3),
(4);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `allows`
--
ALTER TABLE `allows`
  ADD CONSTRAINT `allows_ibfk_1` FOREIGN KEY (`id_product`) REFERENCES `ingredient` (`id_product`),
  ADD CONSTRAINT `allows_ibfk_2` FOREIGN KEY (`id_unit`) REFERENCES `unit` (`id_unit`);

--
-- Contraintes pour la table `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `article_ibfk_1` FOREIGN KEY (`id_packaging`) REFERENCES `packaging` (`id_packaging`),
  ADD CONSTRAINT `article_ibfk_2` FOREIGN KEY (`id_unit`) REFERENCES `unit` (`id_unit`),
  ADD CONSTRAINT `article_ibfk_3` FOREIGN KEY (`id_product`) REFERENCES `product` (`id_product`);

--
-- Contraintes pour la table `ingredient`
--
ALTER TABLE `ingredient`
  ADD CONSTRAINT `ingredient_ibfk_1` FOREIGN KEY (`id_product`) REFERENCES `product` (`id_product`);

--
-- Contraintes pour la table `offers`
--
ALTER TABLE `offers`
  ADD CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`id_article`) REFERENCES `article` (`id_article`),
  ADD CONSTRAINT `offers_ibfk_2` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`);

--
-- Contraintes pour la table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);

--
-- Contraintes pour la table `orders_article`
--
ALTER TABLE `orders_article`
  ADD CONSTRAINT `orders_article_ibfk_1` FOREIGN KEY (`id_article`) REFERENCES `article` (`id_article`),
  ADD CONSTRAINT `orders_article_ibfk_2` FOREIGN KEY (`id_orders`) REFERENCES `orders` (`id_orders`);

--
-- Contraintes pour la table `utensil`
--
ALTER TABLE `utensil`
  ADD CONSTRAINT `utensil_ibfk_1` FOREIGN KEY (`id_product`) REFERENCES `product` (`id_product`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
