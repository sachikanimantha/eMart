
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 09, 2016 at 02:59 AM
-- Server version: 10.0.22-MariaDB
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `u444750285_shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(50) NOT NULL,
  `cat_img` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `cat_name`, `cat_img`) VALUES
(1, 'All Categories', 'all.png'),
(2, 'Reviews', 'review.png'),
(3, 'Coins & Coupons', 'coupons.png'),
(4, 'Coins & Coupons', 'coupons.png'),
(5, 'All Categories', 'all.png');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `sub_cat_id` int(11) NOT NULL,
  `pname` varchar(100) NOT NULL,
  `brand` int(11) NOT NULL,
  `description` text NOT NULL,
  `qty` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `pimg` varchar(50) NOT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=66 ;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`pid`, `sub_cat_id`, `pname`, `brand`, `description`, `qty`, `price`, `pimg`) VALUES
(1, 1, 'Hooded Jacket', 1, 'Sisjuly solid color hooded jacket long sleeve women''s hoodies sweatshirts black zipper autumn winter outerwear coats fashion', 20, '3499.00', 'phj.jpg'),
(2, 1, 'Parkas Jacket', 4, 'Parkas For Women Winter 2016 Army Green Coat Real Large Raccoon Fur Collar Thicken Cotton Padded Jacket Outerwear Female Brand', 10, '8700.00', 'ppj.jpg'),
(3, 1, 'Vogue Lozenge Jacket', 2, 'HYH HAOYIHUI 2016 Brand New Spring Style Vogue Lozenge Women Gold Sequins Jackets Three quater sleeve Fashion Coats Outwears', 5, '6700.00', 'pvlj.jpg'),
(4, 1, 'Hooded Warm Jacke', 1, 'MIEGOFCE New Winter Collection 2016 Bio Fluff Coat Medium Length Fashionable Down coat Women''s Hooded Warm Jacket', 8, '8000.00', 'phwj.jpg'),
(5, 1, 'Lining Winter Jacket', 3, '2016 New Fashion women''s army green Large raccoon fur collar hooded long coat parkas outwear rabbit fur lining winter jacket', 3, '7000.00', 'pwlj.jpg'),
(6, 1, 'Embroidery Vintage  jacket ', 2, 'women vintage Boho embroidery jacket vintage loose retro pleated coat long sleeve color fur balls casual outwear tops ', 4, '2000.00', 'pevj.jpg'),
(7, 1, 'Autumn Bomber Jacket', 1, 'ELF SACK Hot Sale Women Winter Autumn Bomber Jacket Printed Jacket Women Fashion Streetwear Female Down Coats Women Basic Coat', 10, '1500.00', 'pabj.jpg'),
(8, 1, 'Women Fur Coat', 1, '2016 Women fur coats,Genuine Leather,Three colors styles mink coat ,Fashion Slim Winter coats of fur,sell well natural fur', 11, '6370.00', 'pwfc.jpg'),
(9, 1, 'Artka Coat', 1, 'Artka Women''s Winter New Vintage Warm Woolen Hoodie Cloak Coat Embroidered Drop-Shoulder Sleeve Wool Cape Outerwear', 8, '890.00', 'pac.jpg'),
(10, 1, 'Foxqueen Jacket', 3, 'Foxqueen 2016 Warm Women Winter Thick Cotton Jacket Slim Fashion Hooded Coat Fur Collar Fashion Brand High Quality', 20, '2390.00', 'pfj.jpg'),
(11, 1, 'Army Green Coat', 4, 'Fashion Ladies Coats Army Green 2016 Winter Coat Women Parka Long Thick Warm Down Cotton Jacket Women Jackets And Coats Black', 12, '9000.00', 'pagc.jpg'),
(12, 1, 'Shirts Jacket Coat', 4, '2016 Warm Winter New Hot Fashion Multicolor Women Tops Shirts jacket coat Plus Size Blusas Leisure young Blouses', 5, '2000.00', 'psjs.jpg'),
(13, 1, 'Thicken Warm Jacket', 4, 'Thicken Warm 2016 New Winter Jacket Women''s Parkas Coats Large Raccoon Real Fur Winter Jacket Collar Hooded Fashion Quality TOP', 8, '1200.00', 'ptwj.jpg'),
(14, 1, 'ICE Bear Jacket', 3, 'ICEbear 2016 Winter Fashion Removable Collar With Real Silver Fur Thickening Parka Women''s Jacket For Women Coat', 9, '1990.00', 'pibj.jpg'),
(15, 1, 'Denim Jacket', 2, 'autumn 2016 new fashion plus size S-5XL Vintage Hole denim street clothing for women female long Outerwear jacket', 10, '5000.00', 'pdj.jpg'),
(16, 2, 'GAREMAY Warm Jeans', 3, 'GAREMAY Warm Jeans For Women Thicken Pants Winter Jeans Female Stretch Straight Fashion High Waist Jeans Femme Denim Pants', 12, '1200.00', 'bgwj.jpg'),
(17, 2, 'Leggings', 6, 'Hot !2016 New Fashion Women''s Autumn And Winter High Elasticity And Good Quality Warm Leggings Thick Velvet Pants', 10, '3000.00', 'bl.jpg'),
(18, 2, 'Spring Trousers', 2, 'FinalFit Pencil Casual Pants Women, Spring Summer&Autumn Trousers With Belt', 10, '780.00', 'bst.jpg'),
(19, 2, 'Patchwork Leggings', 1, 'Leggings Women 2016 Autumn Winter Fashion PU Leather Patchwork High Waist Stretchable Elastic Workout Leggins', 10, '2000.00', 'bpl.jpg'),
(20, 2, 'Scale leggings ', 1, 'wholelsales Summer style women''s Scale leggings 12 color S-XL size Simulation mermaid sexy pants Digital print colorful leggings', 6, '1390.00', 'bsl.jpg'),
(21, 2, 'Mujer Leggins ', 2, 'Zohra 2016 Women Fashion Legging Aztec Round Ombre Printed calzas deportivas mujer leggins Slim High Waist Legins Pants', 5, '1500.00', 'bml.jpg'),
(22, 2, 'Pencil Denim', 3, 'Autumn Fashion S- 6XL High Waist jeans High Elastic plus size Women Jeans woman femme washed casual skinny pencil Denim pants', 3, '4000.00', 'bppl.jpg'),
(23, 3, 'Sweaters Dress', 1, 'CHANGLA 2016 Autumn Women''s Fashion Sweaters Dresses A-line Deep V Neck Belted Pleated Vintage Dress Long Sleeve Knitting Dress', 4, '1244.00', 'dsd.jpg'),
(24, 3, 'Autumn Dress', 2, 'Autumn Dresses Green Color Long Sleeve Casual Loose Plus Size Dresses Turn Down Collar Corduroy Cotton Dress A Line Lolita Dress', 7, '4567.00', 'dad.jpg'),
(25, 3, 'Benuynffy Dress', 3, 'Tonval Half Sleeve Vintage Tunic Dress Women Gorgeous Floral Retro Audrey Hepburn Style Plus Size Autumn Winter Swing Dress', 15, '1200.00', 'dbd.jpg'),
(26, 3, 'Vancey Dress', 4, 'Dear Lover Plus Size XXL Women Fashion Half Sleeve Work Wear Sugar and Spice Dress cozy vestidos autumn dress big sizes ', 5, '500.00', 'dvd.jpg'),
(27, 3, 'Changla Dress', 4, 'New Women Summer Dress Party Short Mini Dresses Loose Casual Chiffon Solid Slash neck summer vestidos', 8, '800.00', 'dcd.jpg'),
(28, 3, 'Corduroy Cotton Dress', 6, 'Hot Summer Fashin Women Dress Bandage Bodycon Short Sleeve Sexy Party Cocktail Pencil Mini Casual Office Dress Dresses Vestidos', 10, '2000.00', 'dccd.jpg'),
(29, 3, 'Winter Swing Dress', 3, 'COLROVE Long Sleeve Dress Womens Clothing Winter Dresses Women Sexy Dresses Burgundy Off The Shoulder Ruffle Bodycon Dress', 12, '1000.00', 'dwsd.jpg'),
(30, 3, 'Cozy Vezidy Dress', 2, 'New Summer Women Floral Lace Dress Short Sleeve O-Neck Casual Mini Dresses ', 5, '950.00', 'dcvd.jpg'),
(31, 3, 'Summer Vestidos', 5, 'Autumn Women Dress 2016 Vestido De Festa Hollow Out Bodycon Dress Bandage Slim Nignt Club Party Dresses Sexy Dress', 5, '850.00', 'dsv.jpg'),
(32, 3, 'Casual Office Dress', 4, 'Women green totem print Dress Vestido Feminino De Festa vintage short sleeve casual slim brand dress', 10, '760.00', 'dcod.jpg'),
(33, 3, 'Ruffle Bodycon Dress', 1, 'Fashion Women Floral Lace Dress Short Sleeve Summer Party Mini Dresses Lady vestidos', 8, '990.00', 'drbd.jpg'),
(34, 3, 'Floral Lace Dress', 3, 'Summer Style Women Dress Casual Mini O-Neck Sleeveless Short A Line Dress Printed Party Evening 2015 Plus Size Elegant Dress', 20, '1100.00', 'dfld.jpg'),
(35, 3, 'Bandage Slim Dress', 6, 'HYH HAOYIHUI 2016 Brand New Autumn Fashion Women High Waist Ruffles Bow Tie Dress Long Sleeve Deep V-neck Sexy Slim Dress', 3, '1090.00', 'dbsd.jpg'),
(36, 3, 'Festa Vintage Dress', 5, 'Vfemage Women Embroidered Floral See Through Lace Party Evening Bridemaid Mother of Bride Special Occasion Embroidery Dress', 5, '1495.00', 'dfvd.jpg'),
(37, 3, 'Lady vestidos Dress', 2, 'Nice-forever Off-Shoulder Gorgeous Vintage Dress Sexy Slash Neck Lace Top Long Sleeve Zipper Club wear Casual Pencil dress ', 8, '1500.00', 'dlvd.jpg'),
(38, 3, 'Summer Sheath Dress', 3, 'Women Dress Elegant Floral Print Work Business Casual Party Summer Sheath Vestidos', 7, '790.00', 'dssd.jpg'),
(39, 4, 'Tops Shirt Casual ', 1, 'New Summer Strapless Blouse Women Female Patchwork Chiffon Tops Shirt Casual Women Blouse Off Shoulder ', 7, '2000.00', 'btsc.jpg'),
(40, 4, 'Lady Shirts', 2, 'Chiffon Sleeveless Blouse 2016 Women Tops Elia Cher Brand Plus Size Causal Blouses Chic Elegant Lady Shirts Summer Tops Blusas', 10, '1800.00', 'bld.jpg'),
(41, 4, 'Vest Chiffon Blusas ', 3, '2016 New Fashion Tops Chiffon Shirts Plus Size Women blouse shirt Sleeveless Vest Chiffon Blusas Roupas Femininas Blusa Camisas', 15, '500.00', 'bvcb.jpg'),
(42, 4, 'Blusas Feminino', 4, 'New Womens Tops Fashion 2016 Women Summer Chiffon Blouse Plus Size Long Sleeve Casual Shirt White Ropa Mujer Blusas Feminino', 10, '700.00', 'bbf.jpg'),
(43, 4, 'Chiffon Blouse', 5, 'Women White Lace Off shoulder Blouse 2016 Autumn Chiffon Blouses and Shirts Long Sleeve remeras mujer Casual Tops Blusas', 5, '900.00', 'bcb.jpg'),
(44, 4, 'Asymmetric  Blouse', 6, 'GCAROL Women Embroidered Floral Striped Blouse OL Fashion Shirt High Quality Asymmetric Length Tops For 4 Season', 5, '5000.00', 'bab.jpg'),
(45, 4, 'Spring White Blouse', 5, 'Benuynffy 2016 Fashion Spring White Cotton Shirt Women Tops Autumn Slash Neck Long Sleeve Slim Blouse Shirt Casual Blusas', 11, '3000.00', 'bswb.jpg'),
(46, 5, 'Scarves & Wraps', 1, 'Jacquard scarves & Wraps for Women 2016 Brand Cashew leopard Long Shawls 180*70cm Cotton Fringes Pashmina Winter Silk Scarf', 10, '600.00', 'asw.jpg'),
(47, 5, 'Scarf Pashmina', 2, 'Luxury Brand Scarf Pashmina Echarp Cashmere Scarf Wrap Shawl Winter Scarf Women Men Scarves Long Tassel Wool Cachecol Foulard', 5, '800.00', 'asp.jpg'),
(48, 5, 'Rabbit Fur Scarf', 1, 'Good Quality Faux Rabbit Fur Scarf Collar Ring Women Winter Neck Dirl Circle Scarves Apparel Accessories ', 5, '500.00', 'arfs.jpg'),
(49, 5, 'Hair Band ', 3, 'Fashion style lady cotton absorbing sweat Yoga headband candy color sport sweat hair band popular hair accessories for women', 5, '350.00', 'ahb.jpg'),
(50, 5, 'Pashmina Scarf ', 4, 'Fashion Stole long Plaid Poncho Pashmina scarf High Neck Striped Tasseled Sweater warm Shawl Woman Winter Spain scarves', 5, '260.00', 'aps.jpg'),
(51, 5, 'Gloves Unisex', 5, 'Hot Accessories Adult Girls F Gloves Unisex Stretch Male Knitted Gloves For Women Heart Snowflake Mittens Female Gloves', 7, '190.00', 'agu.jpg'),
(52, 5, 'Chirstmas Gloves', 6, '2016 Classical Women Thick Wool Warm Ladies Winter Knitted Twist Gloves Mittens Women''s Accessories Chirstmas Gift ', 8, '500.00', 'acg.jpg'),
(53, 7, 'Assassins Creed Hoodie', 1, 'Assassins Creed Hoodie Free shipping Assassins Creed - Ezio Brotherhood Hoodie Jacket ', 8, '2000.00', 'bhach.jpg'),
(54, 7, 'Hip Hop WANG', 2, '2016 New Arrival Mens Hoodies Sweatshirts Hip Hop WANG Hoodie Black Jacket Hiphop Men Clothes Fashion Chandal Hombre Marca', 9, '1900.00', 'bhhhhw.jpg'),
(55, 7, 'Winter Coat Hoodie', 1, 'New Arrival 2016 Men Winter Coat Hoodie Jacket Outwear Men''s Winter Hood Warm Coat Jackets ', 8, '1450.00', 'bhwch.jpg'),
(56, 7, 'Moleton Masculino', 1, '2016 Autunm Winter Fur Lining Thicken Hoodies Men Casual Zipper Solid Warm Moleton Masculino ', 10, '1600.00', 'bhmm.jpg'),
(57, 7, 'Game of Thrones Hoodies', 2, 'Thick Material Top quality cotton blend game of thrones hoodies casual winter is coming sweatshirt with hat 2016', 5, '2800.00', 'bhgoth.jpg'),
(58, 7, 'Tracksuits Hoodie', 3, '2016 Men''s Brand Tracksuits Set Jacket+Pants Plus Size 4XL 5XL 6XL 7XL 8XL Casual Autumn&Spring Fitness Clothing', 7, '3000.00', 'bhth.jpg'),
(59, 15, 'Cargo Shorts', 4, 'New 2016 brand men''s casual camouflage loose cargo shorts men large size multi-pocket military short pants overalls', 7, '2300.00', 'bs1.jpg'),
(60, 15, 'Sea Trousers', 5, 'Men Beach Shorts Brand Quick Drying Men Shorts Sea Trousers Classic Soft Homme Breathable Men''s Clothing Comfortable Tops', 5, '8000.00', 'bs2.jpg'),
(61, 15, 'Military Styled', 2, '2016 New Brand Male Cargo Shorts Plus Size XXXL 4XL 5XL 6XL 7XL Short Shorts Men Military Styled Loose Fitting Pockets Shorts', 5, '1000.00', 'bs3.jpg'),
(62, 15, 'Solid Multi-pocket', 4, '2016 Top Fashion Calf-length Mens Cargo Shorts Solid Multi-pocket Men Short Pants', 5, '5000.00', 'bs4.jpg'),
(63, 15, 'Bermuda Beach', 4, '2016 Newest Summer Casual Shorts Men cotton Fashion Style Mens Shorts bermuda beach Black Shorts Plus Size M-5XL short For Male', 8, '8000.00', 'bs5.jpg'),
(64, 15, 'Swimsuit Beach', 3, 'Men Swimsuit beach mens shorts/ brand Swimwears boardshort quick drying bermudas masculinas 2016 mens Man XXXL Plus Size', 8, '2000.00', 'bs6.jpg'),
(65, 0, '', 0, '', 5, '3000.00', '');

-- --------------------------------------------------------

--
-- Table structure for table `product_category`
--

CREATE TABLE IF NOT EXISTS `product_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_cat_name` varchar(50) NOT NULL,
  `pro_cat_img` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `product_category`
--

INSERT INTO `product_category` (`id`, `pro_cat_name`, `pro_cat_img`) VALUES
(1, 'Women''s Clothing', 'fashion.jpg'),
(2, 'Men''s Clothing', 'mens.jpg'),
(3, 'Wedding & Events', 'phones.jpg'),
(4, 'Mobile Phones', 'jewelries.jpg'),
(5, 'Shoes', 'shoes.jpg'),
(6, 'Luggage & Bags', 'bags.jpg'),
(7, 'Jewelry & Accessories', 'jewelry.jpg'),
(8, 'Watches', 'watches.jpg'),
(9, 'Mother & Kids', 'mother.jpg'),
(10, 'Toy & Hobies', 'toy.jpg'),
(11, 'Laptops', 'laptops.jpg'),
(12, 'Tablet PCs', 'Tablets.jpg'),
(13, 'Health & Beauty', 'Health.jpg'),
(14, 'Fashion & Accessories', 'f.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `product_sub_category`
--

CREATE TABLE IF NOT EXISTS `product_sub_category` (
  `sub_cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_id` int(11) NOT NULL,
  `sub_cat_name` varchar(100) NOT NULL,
  `sub_cat_img` varchar(50) NOT NULL,
  PRIMARY KEY (`sub_cat_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=40 ;

--
-- Dumping data for table `product_sub_category`
--

INSERT INTO `product_sub_category` (`sub_cat_id`, `cat_id`, `sub_cat_name`, `sub_cat_img`) VALUES
(1, 1, 'Jackets & Coats', 'wjackets.png'),
(2, 1, 'Bottoms', 'bottoms.png'),
(3, 1, 'Dresses', 'dresses.png'),
(4, 1, 'Blouses & Shirts', 'blouse.png'),
(5, 1, 'Accessories', 'accessories.png'),
(6, 1, 'Top & Tees', 'tops.png'),
(7, 2, 'Hoodies & Sweatshirts', 'hoodies.jpg'),
(8, 2, 'Sweeters', 'sweeters.jpg'),
(9, 2, 'Tops & Tees', 'mtops.jpg'),
(10, 2, 'Jackets & Coats', 'jackets.jpg'),
(11, 2, 'Shirts', 'shirts.jpg'),
(12, 2, 'Accesspries', 'maccessories.jpg'),
(13, 2, 'Bottoms', 'bottoms.jpg'),
(14, 2, 'Jeans', 'jeans.jpg'),
(15, 2, 'Shorts', 'shorts.jpg'),
(16, 3, 'Wedding Dresses', 'wd.jpg'),
(17, 3, 'Evinning Dresses', 'ed.jpg'),
(18, 3, 'Wedding Party Dresses', 'wpd.jpg'),
(19, 3, 'Wedding Accesories', 'wa.jpg'),
(20, 3, 'Home Coming Dresses', 'hcd.jpg'),
(21, 3, 'Cocktail Dresses', 'cd.jpg'),
(22, 4, 'Mobile Phone Accessories', 'mpa.jpg'),
(23, 4, 'Mobile Phone LCDs', 'mpl.jpg'),
(24, 4, 'Mobile Phone Parts', 'mpp.jpg'),
(25, 4, 'Mobile Phones', 'mp.jpg'),
(26, 4, 'Phone Bags & Cases', 'pbc.jpg'),
(27, 5, 'Women''s Shoes', 'sws.jpg'),
(28, 5, 'Women''s Flats', 'swf.jpg'),
(29, 5, 'Women''s Boots', 'swb.jpg'),
(30, 5, 'Shoe Accessorie', 'sas.jpg'),
(31, 5, 'Men''s Shoes', 'sms.jpg'),
(32, 5, 'Men''s Flats', 'smf.jpg'),
(33, 5, 'Men''s Boots', 'smb.jpg'),
(35, 6, 'Bag Parts & Accessories', 'bbpa.jpg'),
(36, 6, 'Kids & Baby''s Bags', 'bkbb.jpg'),
(37, 6, 'Luggage & Travel Bags', 'bltb.jpg'),
(38, 6, 'Men''s Bags', 'bmb.jpg'),
(39, 6, 'Women''s Bags', 'bwb.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `nic` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `nic`, `password`) VALUES
(1, 'sachika', '921581360V', 'sachika'),
(2, 'sachika', '12345', 'nimantha'),
(3, 'asn', 'asn', 'asn');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
