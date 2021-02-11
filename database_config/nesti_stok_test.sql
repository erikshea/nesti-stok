-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 11 fév. 2021 à 11:18
-- Version du serveur :  10.4.17-MariaDB
-- Version de PHP : 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `nesti_stok_test`;
--

USE `nesti_stok_test`;

-- --------------------------------------------------------

--
-- Structure de la table `allows`
--

CREATE TABLE `allows` (
  `id_product` int(11) NOT NULL,
  `id_unit` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `allows`
--

INSERT INTO `allows` (`id_product`, `id_unit`) VALUES
(245, 121),
(246, 121),
(247, 118),
(247, 119),
(248, 118),
(248, 119),
(249, 120),
(250, 118),
(250, 119),
(251, 120);

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

CREATE TABLE `article` (
  `id_article` int(11) NOT NULL,
  `code` varchar(30) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `ean` varchar(30) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `stock` int(11) NOT NULL,
  `id_packaging` int(11) NOT NULL,
  `id_unit` int(11) NOT NULL,
  `id_product` int(11) NOT NULL,
  `id_default_supplier` int(11) DEFAULT NULL,
  `flag` enum('a','w','b') DEFAULT 'a'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `article`
--

INSERT INTO `article` (`id_article`, `code`, `name`, `ean`, `weight`, `quantity`, `stock`, `id_packaging`, `id_unit`, `id_product`, `id_default_supplier`, `flag`) VALUES
(194, '321654987452', 'Casserole en inox', '3262154569874', 550, 1, 25, 153, 121, 241, 100, 'a'),
(195, 'LOUCH45', 'louche inox rouge à pois vert', '3354874123456', 225, 1, 18, 156, 121, 243, NULL, 'a'),
(196, 'OEUF6', 'une boite de six oeufs', '3354654123401', 132, 6, 4, 153, 121, 245, NULL, 'b'),
(197, 'OEUF12', 'une boite de douze oeufs', '3354654123457', 256, 12, 6, 153, 121, 245, NULL, 'a'),
(198, 'LAI85', 'bouteille de lait de chèvre', '3359654124557', 125, 1, 32, 154, 120, 249, NULL, 'a'),
(199, 'CASSF300', 'casserole en fonte 300mm', '3262154569785', 1500, 1, 8, 153, 121, 242, NULL, 'a'),
(200, 'LOUCH50B', 'louche en bois courte', '33548741556', 225, 1, 180, 156, 121, 244, NULL, 'a'),
(201, 'CHOC94', 'plaquette de chocolat noir du togo', '3354631231284', 220, 200, 223, 153, 118, 247, NULL, 'a'),
(202, 'OEUFC10', 'une boite de dix oeufs de caille', '45645623401', 420, 10, 44, 153, 121, 246, NULL, 'a'),
(203, 'OEUFC20', 'une boite de vingt oeufs de caille', '36455623457', 886, 20, 66, 153, 121, 246, NULL, 'a'),
(204, 'ORA150', 'bouteille de jus d\'orange', '335966545546124557', 1600, 1.5, 432, 154, 120, 251, NULL, 'a');

-- --------------------------------------------------------

--
-- Structure de la table `ingredient`
--

CREATE TABLE `ingredient` (
  `id_product` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `ingredient`
--

INSERT INTO `ingredient` (`id_product`) VALUES
(245),
(246),
(247),
(248),
(249),
(250),
(251);

-- --------------------------------------------------------

--
-- Structure de la table `offers`
--

CREATE TABLE `offers` (
  `id_article` int(11) NOT NULL,
  `id_supplier` int(11) NOT NULL,
  `price` double DEFAULT NULL,
  `start_date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `offers`
--

INSERT INTO `offers` (`id_article`, `id_supplier`, `price`, `start_date`) VALUES
(194, 100, 5, '2021-02-02 23:04:47'),
(194, 102, 20, '2021-01-12 01:15:55'),
(194, 102, 18.9, '2021-01-13 01:15:55'),
(194, 102, 15, '2021-01-14 01:15:55'),
(194, 102, 30.9, '2021-01-15 01:15:55'),
(194, 102, 25.5, '2021-02-02 23:04:36'),
(194, 103, 3, '2021-02-02 23:04:40'),
(194, 104, 17, '2021-01-12 05:15:55'),
(194, 104, 15.9, '2021-01-13 05:15:55'),
(194, 104, 14, '2021-01-14 05:15:55'),
(194, 104, 17.9, '2021-01-15 05:15:55'),
(194, 104, 5, '2021-02-02 23:04:44'),
(194, 104, 5, '2021-02-02 23:04:45'),
(195, 102, 20, '2021-01-12 01:15:55'),
(195, 102, 18.9, '2021-01-13 01:15:55'),
(195, 102, 15, '2021-01-14 01:15:55'),
(195, 102, 30.9, '2021-01-15 01:15:55'),
(195, 104, 14, '2021-01-12 01:15:55'),
(195, 104, 16.6, '2021-01-13 01:15:55'),
(195, 104, 14.1, '2021-01-14 01:15:55'),
(195, 104, 13.9, '2021-01-15 01:15:55'),
(196, 101, 2.55, '2021-01-05 19:15:55'),
(196, 101, 3.55, '2021-01-13 19:15:55'),
(196, 101, 4.55, '2021-01-15 19:15:55'),
(196, 101, 3.04, '2021-01-18 19:15:55'),
(196, 103, 3.45, '2021-01-05 11:15:55'),
(196, 103, 4.15, '2021-01-13 11:15:55'),
(196, 103, 3.55, '2021-01-15 11:15:55'),
(196, 103, 5.04, '2021-01-18 11:15:55'),
(196, 103, NULL, '2021-03-18 11:15:55'),
(196, 103, 3.92, '2021-07-18 11:15:55'),
(197, 100, 4.28, '2021-01-12 10:15:55'),
(197, 100, 3.28, '2021-01-13 10:15:55'),
(197, 100, 5.28, '2021-01-14 10:15:55'),
(197, 100, 8.28, '2021-01-15 10:15:55'),
(197, 101, 4.99, '2021-01-12 19:15:55'),
(197, 101, 5.99, '2021-01-13 19:15:55'),
(197, 101, 3.99, '2021-01-14 19:15:55'),
(197, 101, 2.8, '2021-01-15 19:15:55'),
(198, 100, 3.25, '2021-01-12 01:15:55'),
(198, 100, 4.72, '2021-01-13 01:15:55'),
(198, 100, 3.25, '2021-01-14 01:15:55'),
(198, 100, 8.72, '2021-01-15 01:15:55'),
(198, 102, 6.28, '2021-01-12 10:15:55'),
(198, 102, 5.28, '2021-01-13 10:15:55'),
(198, 102, 4.28, '2021-01-14 10:15:55'),
(198, 102, 3.28, '2021-01-15 10:15:55'),
(200, 101, 12.99, '2021-01-12 11:15:55'),
(200, 101, 10.9, '2021-01-13 11:15:55'),
(200, 101, 12.1, '2021-01-14 11:15:55'),
(200, 101, 9.87, '2021-01-15 11:15:55'),
(200, 104, 9, '2021-01-12 13:15:55'),
(200, 104, 9.2, '2021-01-13 13:15:55'),
(200, 104, 10.99, '2021-01-14 13:15:55'),
(200, 104, 11.11, '2021-01-15 13:15:55'),
(201, 100, 4.25, '2021-01-12 01:15:55'),
(201, 100, 4.52, '2021-01-13 01:15:55'),
(201, 100, 5.25, '2021-01-14 01:15:55'),
(201, 100, 3.71, '2021-01-15 01:15:55'),
(201, 103, 3.45, '2021-01-05 10:15:55'),
(201, 103, 4.15, '2021-01-13 10:15:55'),
(201, 103, 3.55, '2021-01-15 10:15:55'),
(201, 103, 5.04, '2021-01-18 10:15:55'),
(202, 101, 7.99, '2021-01-12 19:15:55'),
(202, 101, 6.99, '2021-01-13 19:15:55'),
(202, 101, 9.9, '2021-01-14 19:15:55'),
(202, 101, 7.87, '2021-01-15 19:15:55'),
(203, 101, 13.99, '2021-01-12 19:15:55'),
(203, 101, 14.99, '2021-01-13 19:15:55'),
(203, 101, 12.9, '2021-01-14 19:15:55'),
(203, 101, 18.87, '2021-01-15 19:15:55'),
(203, 104, 12, '2021-01-12 13:15:55'),
(203, 104, 14.6, '2021-01-13 13:15:55'),
(203, 104, 10.18, '2021-01-14 13:15:55'),
(203, 104, 16.9, '2021-01-15 13:15:55'),
(204, 103, 3.45, '2021-01-05 16:15:55'),
(204, 103, 4.15, '2021-01-13 16:15:55'),
(204, 103, 3.55, '2021-01-15 16:15:55'),
(204, 103, 5.04, '2021-01-18 16:15:55');

-- --------------------------------------------------------

--
-- Structure de la table `orders`
--

CREATE TABLE `orders` (
  `id_orders` int(11) NOT NULL,
  `number` varchar(10) DEFAULT NULL,
  `date_order` datetime NOT NULL DEFAULT current_timestamp(),
  `date_delivery` date DEFAULT NULL,
  `id_supplier` int(11) NOT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `orders`
--

INSERT INTO `orders` (`id_orders`, `number`, `date_order`, `date_delivery`, `id_supplier`, `id_user`) VALUES
(132, '257', '2021-01-13 01:15:55', '2021-01-17', 101, 58),
(133, '612', '2021-01-14 01:15:55', '2021-01-21', 101, 59),
(134, '546', '2021-01-15 01:15:55', '2021-01-24', 100, 59),
(135, '658', '2021-01-12 23:15:55', '2021-01-16', 100, 59),
(136, '555', '2021-01-13 23:15:55', '2021-03-02', 102, 59),
(137, '567', '2021-01-12 23:15:55', '2021-03-02', 104, 58),
(138, '7467', '2021-01-24 23:15:55', '2021-03-02', 104, 58),
(139, '10467', '2021-01-13 23:15:55', '2021-03-02', 103, 58);

-- --------------------------------------------------------

--
-- Structure de la table `orders_article`
--

CREATE TABLE `orders_article` (
  `id_article` int(11) NOT NULL,
  `id_orders` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `orders_article`
--

INSERT INTO `orders_article` (`id_article`, `id_orders`, `quantity`) VALUES
(194, 137, 3),
(194, 138, 3),
(195, 136, 1),
(195, 137, 34),
(195, 138, 3),
(196, 132, 5),
(196, 139, 30),
(197, 132, 3),
(197, 133, 8),
(197, 134, 12),
(198, 134, 2),
(198, 135, 24),
(198, 136, 12),
(200, 137, 5),
(200, 138, 3),
(201, 139, 4),
(203, 137, 10),
(203, 138, 3),
(204, 139, 31);

-- --------------------------------------------------------

--
-- Structure de la table `packaging`
--

CREATE TABLE `packaging` (
  `id_packaging` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `flag` enum('a','w','b') DEFAULT 'a'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `packaging`
--

INSERT INTO `packaging` (`id_packaging`, `name`, `flag`) VALUES
(153, 'boite', 'a'),
(154, 'bouteille', 'a'),
(155, 'carton', 'a'),
(156, 'sac', 'a'),
(157, 'film plastique', 'a');

-- --------------------------------------------------------

--
-- Structure de la table `product`
--

CREATE TABLE `product` (
  `id_product` int(11) NOT NULL,
  `reference` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `flag` enum('a','w','b') DEFAULT 'a'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `product`
--

INSERT INTO `product` (`id_product`, `reference`, `name`, `flag`) VALUES
(240, 'FOU54', 'Fouet en bois', 'a'),
(241, 'CASS154', 'Casserole anti adhérente', 'a'),
(242, 'CASS300', 'Casserole en fonte', 'a'),
(243, 'LOU', 'Louche en inox', 'a'),
(244, 'LOUB', 'Louche en bois', 'a'),
(245, 'OEUF', 'Oeuf de poule', 'a'),
(246, 'OEUFC', 'Oeuf de caille', 'a'),
(247, 'CHOCN', 'Chocolat noir', 'a'),
(248, 'CHOCHOC', 'Chocolat au lait', 'a'),
(249, 'LAIT', 'Lait', 'a'),
(250, 'SUC', 'Sucre en poudre', 'a'),
(251, 'ORA', 'Jus d\'orange', 'a');

-- --------------------------------------------------------

--
-- Structure de la table `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `address1` varchar(50) DEFAULT NULL,
  `address2` varchar(50) DEFAULT NULL,
  `zip_code` char(5) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `contact_name` varchar(50) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `flag` enum('a','w','b') DEFAULT 'a'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `name`, `address1`, `address2`, `zip_code`, `city`, `contact_name`, `country`, `phone_number`, `flag`) VALUES
(100, 'O\'Sel Fin', '12 rue des lilas', 'Bat E', '34000', 'Montpellier', 'Jean Jacques', 'FRANCE', '0492546547', 'a'),
(101, 'Oeufs en folie', '125 avenue Martin', '', '13013', 'Marseille', 'Martine Martin', 'FRANCE', '0491546978', 'a'),
(102, 'Perfect cooking', 'Eight Avenue', '', '35240', 'NYC', 'Edward', 'USA', '0084 564 874', 'a'),
(103, 'L\'empire des fruits', '4 rue des chats', '', '34040', 'Montpellier', 'Gaston', 'France', '0604040550', 'a'),
(104, 'Pacher Distribution', '7 avenue du roi', '', '80810', 'Alès', 'Charles Durant', 'France', '0404557440', 'a');

-- --------------------------------------------------------

--
-- Structure de la table `unit`
--

CREATE TABLE `unit` (
  `id_unit` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `flag` enum('a','w','b') DEFAULT 'a'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `unit`
--

INSERT INTO `unit` (`id_unit`, `name`, `flag`) VALUES
(118, 'gramme', 'a'),
(119, 'kilos', 'a'),
(120, 'litre', 'a'),
(121, 'pièce', 'a'),
(122, 'livre', 'a');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password_hash` varchar(200) NOT NULL,
  `date_creation` datetime DEFAULT current_timestamp(),
  `role` enum('super-administrator','administrator') DEFAULT NULL,
  `flag` enum('a','w','b') DEFAULT 'a'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id_user`, `login`, `name`, `password_hash`, `date_creation`, `role`, `flag`) VALUES
(58, 'james', 'James Bond', '$2a$06$FgmdW0NzPic9G2RH3.3mjelKu9zBxD4qIE/cMr0FprF7LqNcsA3CS', '2018-01-15 01:15:55', 'super-administrator', 'a'),
(59, 'erik', 'Erik Shea', '$2a$06$bLER/BU4SPi.POHwEs5...eEd6UKJ4FzsKUhinVnc4bpD2KGXUxuK', '2017-01-15 01:15:55', 'administrator', 'a');

-- --------------------------------------------------------

--
-- Structure de la table `utensil`
--

CREATE TABLE `utensil` (
  `id_product` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `utensil`
--

INSERT INTO `utensil` (`id_product`) VALUES
(240),
(241),
(242),
(243),
(244);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `allows`
--
ALTER TABLE `allows`
  ADD PRIMARY KEY (`id_product`,`id_unit`),
  ADD KEY `id_unit` (`id_unit`);

--
-- Index pour la table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`id_article`),
  ADD UNIQUE KEY `code` (`code`),
  ADD UNIQUE KEY `name` (`name`),
  ADD UNIQUE KEY `ean` (`ean`),
  ADD KEY `id_packaging` (`id_packaging`),
  ADD KEY `id_unit` (`id_unit`),
  ADD KEY `id_product` (`id_product`),
  ADD KEY `FK_article_id_default_supplier` (`id_default_supplier`);

--
-- Index pour la table `ingredient`
--
ALTER TABLE `ingredient`
  ADD PRIMARY KEY (`id_product`);

--
-- Index pour la table `offers`
--
ALTER TABLE `offers`
  ADD PRIMARY KEY (`id_article`,`id_supplier`,`start_date`),
  ADD KEY `id_supplier` (`id_supplier`);

--
-- Index pour la table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id_orders`),
  ADD KEY `id_supplier` (`id_supplier`),
  ADD KEY `id_user` (`id_user`);

--
-- Index pour la table `orders_article`
--
ALTER TABLE `orders_article`
  ADD PRIMARY KEY (`id_article`,`id_orders`),
  ADD KEY `id_orders` (`id_orders`);

--
-- Index pour la table `packaging`
--
ALTER TABLE `packaging`
  ADD PRIMARY KEY (`id_packaging`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Index pour la table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id_product`);

--
-- Index pour la table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- Index pour la table `unit`
--
ALTER TABLE `unit`
  ADD PRIMARY KEY (`id_unit`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `login` (`login`);

--
-- Index pour la table `utensil`
--
ALTER TABLE `utensil`
  ADD PRIMARY KEY (`id_product`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `article`
--
ALTER TABLE `article`
  MODIFY `id_article` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=205;

--
-- AUTO_INCREMENT pour la table `orders`
--
ALTER TABLE `orders`
  MODIFY `id_orders` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=140;

--
-- AUTO_INCREMENT pour la table `packaging`
--
ALTER TABLE `packaging`
  MODIFY `id_packaging` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=159;

--
-- AUTO_INCREMENT pour la table `product`
--
ALTER TABLE `product`
  MODIFY `id_product` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=252;

--
-- AUTO_INCREMENT pour la table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id_supplier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT pour la table `unit`
--
ALTER TABLE `unit`
  MODIFY `id_unit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=125;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

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
  ADD CONSTRAINT `FK_article_id_default_supplier` FOREIGN KEY (`id_default_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE NO ACTION,
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
