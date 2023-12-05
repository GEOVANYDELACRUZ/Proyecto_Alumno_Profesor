-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 05-12-2023 a las 04:29:28
-- Versión del servidor: 10.5.20-MariaDB
-- Versión de PHP: 7.3.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `id21315300_project`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `actualizarProfesor` (IN `p_id` INT, IN `p_nombre` VARCHAR(255), IN `p_apellidos` VARCHAR(255))   BEGIN
    UPDATE tblProfesor
    SET
        profNombre = p_nombre,
        profApellidos = p_apellidos
    WHERE
        IDProfesor = p_id;
END$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `actualizarProfesorPass` (IN `p_id` INT, IN `p_pass` VARCHAR(255))   BEGIN
    UPDATE tblProfesor
    SET
        profContra = p_pass
    WHERE
        IDProfesor= p_id;
END$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `actualizar_comentario` (IN `p_codigo` INT, IN `p_Resp` VARCHAR(100))   update tblCurs_Nota set cursNRespuesta = p_Resp where IDCursNota = p_codigo$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `actualizar_nota_curso` (IN `p_dni` VARCHAR(9), IN `p_curso` INT, IN `p_criterio` VARCHAR(100), IN `p_nota` DECIMAL(4,2))   update tblCurs_Nota set cursNota = p_nota where IDCursNota = 
(select obtener_id_curso_nota (p_dni, p_curso,p_criterio))$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `ingresar_alumno` (IN `dni` INT, IN `pass` VARCHAR(50))   select * from tblAlumno where DNI like dni && alumPass like pass$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `ingresar_profesor` (IN `correo` VARCHAR(250), IN `pass` VARCHAR(250))   select * from tblProfesor where profCorreo like correo && profContra like pass$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `insertar_alumno` (IN `DNI` VARCHAR(9), IN `Nombres` VARCHAR(50), IN `aPaterno` VARCHAR(50), IN `aMaterno` VARCHAR(50))  NO SQL INSERT into tblAlumno VALUES (DNI, Nombres,aPaterno,aMaterno,DNI)$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `insertar_alum_curso` (IN `DNI` VARCHAR(9), IN `Curso` VARCHAR(100))  NO SQL insert into tblAlumCurs values ((select crear_id_alumno_curso()),DNI, (obtener_id_curso(Curso)))$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `insertar_curso` (IN `nombres` VARCHAR(100))  NO SQL insert into tblCurso values ((select crear_id_curso()),nombres)$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `insertar_nota` (IN `Criterio` VARCHAR(100), IN `Valor` FLOAT, IN `Descrip` VARCHAR(300))  NO SQL insert into tblNotas values ((select crear_id_nota()), Criterio,Valor,Descrip)$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `insertar_nota_alum_curso` (IN `p_dni` VARCHAR(9), IN `p_curso` VARCHAR(100), IN `p_criterio` VARCHAR(100))  DETERMINISTIC NO SQL INSERT INTO tblCurs_Nota (IDCursNota, IDAlumC,cursNota, cursCriterio)
VALUES (
    (SELECT crear_id_nota_curso()),
    (SELECT obtener_id_alumn_curso(p_dni, p_curso)),
0.00,
p_criterio
)$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `insertar_profesor` (IN `nombres` VARCHAR(100), IN `apellidos` VARCHAR(100), IN `correo` VARCHAR(150), IN `contra` VARCHAR(15))  NO SQL insert into tblProfesor values ((select crear_id_profesor()),nombres,apellidos,correo,contra)$$

CREATE DEFINER=`id21315300_gdelac`@`%` PROCEDURE `insertar_prof_curso` (IN `codigo` INT, IN `curso` VARCHAR(100))  NO SQL insert into tblCursProf VALUES ((SELECT crear_id_profesor_curso()),codigo,(SELECT obtener_id_curso(curso)))$$

--
-- Funciones
--
CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `crear_id_alumno_curso` () RETURNS INT(11)  BEGIN
    DECLARE numero INT;
    SELECT COALESCE(MAX(IDAlumC), 0) + 1 INTO numero FROM tblAlumCurs;
    RETURN numero;
END$$

CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `crear_id_curso` () RETURNS INT(11) DETERMINISTIC NO SQL begin 
declare numero int;
select COALESCE(MAX(IDCurso), 0) + 1 into numero from tblCurso;
return numero;
end$$

CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `crear_id_nota` () RETURNS INT(11) DETERMINISTIC NO SQL begin 
declare numero int;
select count(IDNota)+1 into numero from tblNotas;
return numero;
end$$

CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `crear_id_nota_curso` () RETURNS INT(11) NO SQL begin 
declare numero int;
select COALESCE(MAX(IDCursNota), 0) + 1 into numero from tblCurs_Nota;
return numero;
end$$

CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `crear_id_profesor` () RETURNS INT(11) DETERMINISTIC NO SQL begin 
declare numero int;
select count(IDProfesor)+1 into numero from tblProfesor;
return numero;
end$$

CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `crear_id_profesor_curso` () RETURNS INT(11) DETERMINISTIC NO SQL begin 
declare numero int;
select COALESCE(MAX(IDCursP), 0) + 1 into numero from tblCursProf;
return numero;
end$$

CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `obtenerIDProfesor` (`correo` VARCHAR(255)) RETURNS INT(11)  BEGIN
    DECLARE id_profesor INT;
    
    SELECT IDProfesor INTO id_profesor
    FROM tblProfesor
    WHERE profCorreo = correo;
    
    IF id_profesor IS NULL THEN
        RETURN 0;
    ELSE
        RETURN id_profesor;
    END IF;
END$$

CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `obtener_id_alumn_curso` (`p_dni` VARCHAR(9), `p_curso` INT) RETURNS INT(11) DETERMINISTIC NO SQL begin 
declare numero int;
SELECT max(IDAlumC) into numero FROM tblAlumCurs AC where AC.DNI = p_dni and AC.IDCurso = p_curso;
return numero;
END$$

CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `obtener_id_curso` (`curso` VARCHAR(100)) RETURNS INT(11) DETERMINISTIC NO SQL begin 
declare numero int;
SELECT max(IDCurso) into numero FROM `tblCurso` WHERE curNombre like curso;
return numero;
end$$

CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `obtener_id_curso_nota` (`p_dni` VARCHAR(9), `p_curso` INT, `p_criterio` VARCHAR(100)) RETURNS INT(11)  begin 
declare numero int;
SELECT max(IDCursNota) into numero FROM tblCurs_Nota CN
inner join tblAlumCurs AC on CN.IDAlumC = AC.IDAlumC
INNER JOIN tblCurso C on C.IDCurso = AC.IDCurso
where AC.DNI = p_dni and CN.cursCriterio = p_criterio and C.IDCurso = p_curso;
return numero;
END$$

CREATE DEFINER=`id21315300_gdelac`@`%` FUNCTION `obtener_id_nota` (`Criterio` VARCHAR(100), `Valor` FLOAT, `Descrip` VARCHAR(300)) RETURNS INT(11) DETERMINISTIC NO SQL begin 
declare numero int;
SELECT max(IDNota) into numero FROM tblNotas WHERE notCriterio like Criterio && notValor = Valor && notDescrip like Descrip;
return numero;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblAlumCurs`
--

CREATE TABLE `tblAlumCurs` (
  `IDAlumC` int(11) NOT NULL,
  `DNI` varchar(9) NOT NULL,
  `IDCurso` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tblAlumCurs`
--

INSERT INTO `tblAlumCurs` (`IDAlumC`, `DNI`, `IDCurso`) VALUES
(1, '75465000', 1),
(2, '43522388', 1),
(3, '60088568', 1),
(4, '82131664', 1),
(5, '21729574', 1),
(6, '57443988', 1),
(7, '94533996', 1),
(8, '87009559', 1),
(9, '37137887', 1),
(10, '50951229', 1),
(11, '47567722', 1),
(12, '83624686', 1),
(13, '75465000', 2),
(14, '43522388', 2),
(15, '60088568', 2),
(16, '82131664', 2),
(17, '21729574', 2),
(18, '57443988', 2),
(19, '94533996', 2),
(20, '87009559', 2),
(21, '37137887', 2),
(22, '50951229', 2),
(23, '47567722', 2),
(24, '75465000', 3),
(25, '60088568', 3),
(26, '43522388', 3),
(27, '82131664', 3),
(28, '21729574', 3),
(29, '57443988', 3),
(30, '94533996', 3),
(31, '87009559', 3),
(32, '37137887', 3),
(33, '50951229', 3),
(34, '47567722', 3),
(35, '71424261', 4),
(36, '76905898', 4),
(37, '72113166', 4),
(38, '43522388', 5),
(39, '75465000', 5),
(40, '94533996', 5),
(41, '21729574', 5),
(42, '57443988', 5),
(43, '50951229', 5),
(44, '87009559', 5),
(45, '47567722', 5),
(46, '86858170', 5),
(47, '71739619', 5),
(48, '15486615', 5),
(49, '90168821', 5),
(50, '17715432', 5),
(51, '46442804', 5),
(52, '55447715', 5),
(53, '17523310', 5),
(54, '49922010', 5),
(55, '60923866', 5),
(56, '38305054', 5),
(57, '63263080', 5),
(58, '42120187', 5),
(59, '90414136', 5),
(60, '65350627', 5),
(61, '20146169', 5),
(62, '82131664', 5),
(63, '82131664', 6),
(64, '60088568', 6),
(65, '57443988', 6),
(66, '43522388', 6),
(67, '21729574', 6),
(68, '94533996', 6),
(69, '87009559', 6),
(70, '50951229', 6),
(71, '47567722', 6),
(72, '86858170', 6),
(73, '59900170', 6),
(74, '71739619', 6),
(75, '17715432', 6),
(76, '46442804', 6),
(77, '92028941', 6),
(78, '42442870', 6),
(79, '11131813', 6),
(80, '17523310', 6),
(81, '46500938', 6),
(82, '38305054', 6),
(83, '34877431', 6),
(84, '42120187', 6),
(85, '49954426', 6),
(86, '90414136', 6),
(87, '65350627', 6),
(88, '78626360', 6),
(89, '20146169', 6),
(90, '90733697', 6),
(91, '70684595', 6),
(92, '75022232', 6),
(93, '69554991', 6),
(94, '86759869', 6),
(95, '95472894', 6),
(96, '42174000', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblAlumno`
--

CREATE TABLE `tblAlumno` (
  `DNI` varchar(9) NOT NULL,
  `alumNombres` varchar(50) NOT NULL,
  `alumAPaterno` varchar(50) NOT NULL,
  `alumAPMaterno` varchar(50) NOT NULL,
  `alumPass` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tblAlumno`
--

INSERT INTO `tblAlumno` (`DNI`, `alumNombres`, `alumAPaterno`, `alumAPMaterno`, `alumPass`) VALUES
('10104100', ' Chev', ' Everex', ' Sheridan', '10104100'),
('10179524', ' Zaneta', ' Heball', ' Rossbrook', '10179524'),
('11131813', ' Damita', ' Kinge', ' Anstiss', '11131813'),
('13108788', ' Matthew', ' Flowerdew', ' Scarlan', '13108788'),
('15486615', ' Shanta', ' Gummer', ' Crosfeld', '15486615'),
('16871520', ' Karyn', ' Blankett', ' Georgeon', '16871520'),
('17410557', ' Virgil', ' Phillippo', ' Parr', '17410557'),
('17523310', ' Van', ' Palleske', ' Chesswas', '17523310'),
('17715432', ' Konrad', ' Attoc', ' Brunel', '17715432'),
('17747676', ' Meir', ' Chaplain', ' Bossom', '17747676'),
('19050707', ' Evania', ' Savory', ' Willmore', '19050707'),
('20122238', ' Craggie', ' Dashper', ' Kuzma', '20122238'),
('20146169', ' Lorianna', ' Cherrington', ' Haime', '20146169'),
('20277917', ' Ive', ' Davion', ' Ficken', '20277917'),
('21652181', ' Bernetta', ' Broadberrie', ' Vasilchenko', '21652181'),
('21729574', ' Beverlee', ' Meredith', ' Fahy', '21729574'),
('22154727', ' Penelopa', ' Whitticks', ' Cianni', '22154727'),
('23884855', ' Nita', ' Clearley', ' Tyer', '23884855'),
('24525736', ' Bennie', ' Weben', ' Kippins', '24525736'),
('24816345', ' Merrick', ' Guichard', ' Longfut', '24816345'),
('25346666', ' Kenyon', ' Durrance', ' Tuttle', '25346666'),
('27033914', ' Alidia', ' Grasha', ' Leuren', '27033914'),
('27603915', ' Latrina', ' Millership', ' Greenhall', '27603915'),
('33175989', ' Grace', ' Drover', ' Goaks', '33175989'),
('33858695', ' Borg', ' Yardy', ' Garlic', '33858695'),
('34221027', ' Bevon', ' Kelwick', ' Vickar', '34221027'),
('34877431', ' Melony', ' Smyley', ' Allchorne', '34877431'),
('36853583', ' Mureil', ' Barbary', ' Hunt', '36853583'),
('37137887', ' Reagan', ' Maharry', ' Philcox', '37137887'),
('37400478', ' Desi', ' Pavyer', ' Oris', '37400478'),
('38305054', ' Mirabel', ' Weaben', ' Lightwing', '38305054'),
('38558250', ' Jobey', ' Castellani', ' Hiorn', '38558250'),
('38613370', ' Filberte', ' Fillery', ' Taree', '38613370'),
('38859646', ' Minne', ' Knight', ' Toffanini', '38859646'),
('39076687', ' Allene', ' Skinner', ' Mayow', '39076687'),
('40470563', ' Cristin', ' Soppitt', ' Dolligon', '40470563'),
('40766183', ' Giordano', ' Barnard', ' Bonifas', '40766183'),
('41059470', ' Karie', ' Izat', ' Parmiter', '41059470'),
('41365778', ' Joby', ' Perigoe', ' MacVean', '41365778'),
('42120187', ' Shaina', ' Laming', ' Scotchbrook', '42120187'),
('42174000', ' Caron', ' Blandford', ' Pomphrey', '42174000'),
('42442870', ' Korella', ' Carslaw', ' Sterley', '42442870'),
('43125940', ' Dietrich', ' Caws', ' Tabord', '43125940'),
('43146443', ' Nat', ' Kahane', ' Shout', '43146443'),
('43522388', ' Dwayne', ' Gillions', ' Keegan', '43522388'),
('44556472', ' Glori', ' Pattlel', ' Wordsworth', '44556472'),
('44974657', ' Stephani', ' Thompson', ' Copestick', '44974657'),
('46442804', ' Kiele', ' Goodlife', ' Saiz', '46442804'),
('46453260', ' Shandee', ' Grimston', ' Thackham', '46453260'),
('46500938', ' Verene', ' Reiglar', ' Tooher', '46500938'),
('47067534', ' Agata', ' Brade', ' Blune', '47067534'),
('47567722', ' Nancey', ' Blown', ' Skellington', '47567722'),
('48107015', ' Winnie', ' Mendes', ' Sheather', '48107015'),
('49922010', ' Cos', ' Dorrins', ' Matus', '49922010'),
('49954426', ' Lurlene', ' Kamien', ' Diamond', '49954426'),
('50729109', ' Holmes', ' Pasque', ' Duncanson', '50729109'),
('50951229', ' Chrystal', ' Silburn', ' Davidai', '50951229'),
('55447715', ' Christabella', ' Tipling', ' Chadburn', '55447715'),
('56522688', ' Simonette', ' Jarrette', ' Garbar', '56522688'),
('56581292', ' Kaia', ' Vasiljevic', ' Southorn', '56581292'),
('56996666', ' Krystle', ' Pencott', ' Baptie', '56996666'),
('57443988', ' Marilee', ' Kruschev', ' De Beauchamp', '57443988'),
('57815502', ' Fax', ' Jeannesson', ' Absalom', '57815502'),
('58124878', ' Elsbeth', ' Hammerberg', ' McRory', '58124878'),
('58904504', ' Pearla', ' Spencelayh', ' Kundt', '58904504'),
('59154720', ' Kevan', ' Verdy', ' Withull', '59154720'),
('59227007', ' Merci', ' Lorne', ' Surgenor', '59227007'),
('59476223', ' Brucie', ' Keynes', ' Barczynski', '59476223'),
('59719064', ' Vally', ' Champagne', ' Carlisle', '59719064'),
('59897533', ' Marci', ' Marchington', ' Filippucci', '59897533'),
('59900170', ' Kara-lynn', ' Handaside', ' Kordovani', '59900170'),
('60067616', ' Arvin', ' Beton', ' Manifield', '60067616'),
('60088568', ' Welby', ' Scarasbrick', ' Jarrel', '60088568'),
('60923866', ' Palmer', ' Aird', ' Chapellow', '60923866'),
('61350574', ' Luther', ' Prestie', ' Thoms', '61350574'),
('61456485', ' Shannon', ' Loude', ' Rothery', '61456485'),
('63037263', ' Ansel', ' Blaxall', ' Storck', '63037263'),
('63263080', ' Nicholle', ' Emburey', ' Holt', '63263080'),
('65350627', ' Dael', ' Russan', ' Banfill', '65350627'),
('65568020', ' Myrah', ' Moverley', ' Mc Carroll', '65568020'),
('66904614', ' Carmelle', ' Le Brom', ' Winthrop', '66904614'),
('67548585', ' Damon', ' Mapholm', ' Seer', '67548585'),
('69554991', ' Marvin', ' Grass', ' Georgeson', '69554991'),
('69756095', ' Carmella', ' Tidswell', ' Aizikowitz', '69756095'),
('70619646', ' Flossie', ' Lumb', ' Ciepluch', '70619646'),
('70684595', ' Ferrel', ' Augar', ' Blei', '70684595'),
('71026358', ' Demeter', ' Enbury', ' Seden', '71026358'),
('71424261', ' Carlos', ' De la calle', ' Coz', '71424261'),
('71739619', ' Diannne', ' Verecker', ' Foldes', '71739619'),
('71773066', ' Georgetta', ' Haggith', ' Brabyn', '71773066'),
('71952829', ' Marta', ' Wodham', ' Kefford', '71952829'),
('72113166', ' Michael', ' Galvan', ' Durand', '72113166'),
('73352199', ' Salli', ' Edridge', ' Pigeram', '73352199'),
('73646229', ' Cammy', ' Enrigo', ' Bohman', '73646229'),
('74980648', ' Tim', ' Liver', ' Barnby', '74980648'),
('75022232', ' Colette', ' Rubi', ' Middleweek', '75022232'),
('75465000', ' Patin', ' Blundon', ' Greenhead', '75465000'),
('76905898', ' Geovany', ' De la cruz', ' Taza', '76905898'),
('76938862', ' Noach', ' Hughes', ' Brunone', '76938862'),
('78626360', ' Lynnelle', ' Josephoff', ' Youson', '78626360'),
('78987828', ' Artus', ' Stops', ' Ruckman', '78987828'),
('79111359', ' Cletis', ' Olcot', ' Clawley', '79111359'),
('80105309', ' Gizela', ' Hamsley', ' Douche', '80105309'),
('80414129', ' Katinka', ' Battman', ' Tattershaw', '80414129'),
('82131664', ' Maddy', ' Shippam', ' Barca', '82131664'),
('83624686', ' Jere', ' Yanez', ' Sweetzer', '83624686'),
('83633147', ' Tobiah', ' Flowith', ' Southam', '83633147'),
('86631015', ' Ingeberg', ' Poston', ' Chantrell', '86631015'),
('86759869', ' Dominga', ' Lumby', ' Piecha', '86759869'),
('86858170', ' Thibaut', ' Stockford', ' Prangley', '86858170'),
('87009559', ' Carlo', ' Tiffney', ' Wackley', '87009559'),
('87591062', ' Cos', ' Accomb', ' Capelin', '87591062'),
('89377547', ' Larisa', ' Childerhouse', ' Seater', '89377547'),
('90107617', ' Shelley', ' Redmell', ' Ternent', '90107617'),
('90168821', ' Wendall', ' Pittel', ' Barajas', '90168821'),
('90414136', ' Alexa', ' Donoher', ' Sutliff', '90414136'),
('90733697', ' Standford', ' Gundrey', ' Odger', '90733697'),
('90831269', ' Billi', ' Mainstone', ' Reichhardt', '90831269'),
('92028941', ' Vanessa', ' Bruna', ' Rosenhaus', '92028941'),
('92107313', ' Sandro', ' Sowrah', ' Curnick', '92107313'),
('92482967', ' Kassey', ' Choudhury', ' Eltun', '92482967'),
('92802581', ' Wolfie', ' Bachs', ' Lehemann', '92802581'),
('93517561', ' Nora', ' Cerro', ' Wishkar', '93517561'),
('94533996', ' Laurella', ' McCall', ' MacMeekan', '94533996'),
('95472894', ' Royal', ' Orteaux', ' Matskiv', '95472894'),
('96534430', ' Avrit', ' Van Der Weedenburg', ' Colenutt', '96534430'),
('98569379', ' Eric', ' Wolfer', ' Firby', '98569379');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblCurso`
--

CREATE TABLE `tblCurso` (
  `IDCurso` int(11) NOT NULL,
  `curNombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tblCurso`
--

INSERT INTO `tblCurso` (`IDCurso`, `curNombre`) VALUES
(1, 'Biología I'),
(2, 'Anatomía I'),
(3, 'Álgebra Matricial'),
(4, 'Proyectos II'),
(5, 'Geometría analítica I'),
(6, 'Anatomía');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblCursProf`
--

CREATE TABLE `tblCursProf` (
  `IDCursP` int(11) NOT NULL,
  `IDProfesor` int(11) NOT NULL,
  `IDCurso` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tblCursProf`
--

INSERT INTO `tblCursProf` (`IDCursP`, `IDProfesor`, `IDCurso`) VALUES
(1, 3, 1),
(2, 3, 2),
(3, 5, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblCurs_Nota`
--

CREATE TABLE `tblCurs_Nota` (
  `IDCursNota` int(11) NOT NULL,
  `IDAlumC` int(11) NOT NULL,
  `cursNComment` varchar(255) DEFAULT NULL,
  `cursNRespuesta` varchar(255) DEFAULT NULL,
  `cursNota` decimal(4,2) NOT NULL,
  `cursCriterio` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tblCurs_Nota`
--

INSERT INTO `tblCurs_Nota` (`IDCursNota`, `IDAlumC`, `cursNComment`, `cursNRespuesta`, `cursNota`, `cursCriterio`) VALUES
(1, 35, NULL, NULL, 18.00, 'Consolidado 1'),
(2, 37, NULL, NULL, 0.00, 'prueba'),
(3, 35, NULL, NULL, 15.00, 'prueba'),
(4, 36, 'prueba', 'prueba 100', 0.00, 'prueba'),
(5, 37, NULL, NULL, 0.00, 'prueba 2'),
(6, 35, NULL, NULL, 12.00, 'prueba 2'),
(7, 36, 'la nota se confundió ', 'la nota es correcta dado que no se presentó a tiempo', 0.00, 'prueba 2'),
(8, 37, NULL, NULL, 0.00, 'Sustitutorio'),
(9, 35, NULL, NULL, 0.00, 'Sustitutorio'),
(10, 36, 'Creo que hubo una equivocacion profesor', NULL, 0.00, 'Sustitutorio');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblProfesor`
--

CREATE TABLE `tblProfesor` (
  `IDProfesor` int(11) NOT NULL,
  `profNombre` varchar(50) NOT NULL,
  `profApellidos` varchar(100) NOT NULL,
  `profCorreo` varchar(150) NOT NULL,
  `profContra` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tblProfesor`
--

INSERT INTO `tblProfesor` (`IDProfesor`, `profNombre`, `profApellidos`, `profCorreo`, `profContra`) VALUES
(1, 'Geovany', 'De la Cruz', 'geovaDT', '1000'),
(2, 'Juan', 'Pérez Nuñez', 'jperezn@continental.edu.pe', 'juanpe123'),
(3, 'Pedro', 'Paulet Jiménez', 'quesadag@continental.edu.pe', 'quesada1234'),
(4, 'Daniel', 'De la calle Coz', '71424261@continental.edu.pe', 'dan12345'),
(5, 'Maria', 'Buenaventura Feliz', 'menendezr@continental.edu.pe', 'menendez1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tblAlumCurs`
--
ALTER TABLE `tblAlumCurs`
  ADD PRIMARY KEY (`IDAlumC`),
  ADD KEY `fk_AlumDNI` (`DNI`),
  ADD KEY `fk_AlumCurs` (`IDCurso`);

--
-- Indices de la tabla `tblAlumno`
--
ALTER TABLE `tblAlumno`
  ADD PRIMARY KEY (`DNI`);

--
-- Indices de la tabla `tblCurso`
--
ALTER TABLE `tblCurso`
  ADD PRIMARY KEY (`IDCurso`);

--
-- Indices de la tabla `tblCursProf`
--
ALTER TABLE `tblCursProf`
  ADD PRIMARY KEY (`IDCursP`),
  ADD KEY `fk_CursProf` (`IDProfesor`),
  ADD KEY `fk_CursProf2` (`IDCurso`);

--
-- Indices de la tabla `tblCurs_Nota`
--
ALTER TABLE `tblCurs_Nota`
  ADD KEY `fk_Curso` (`IDAlumC`);

--
-- Indices de la tabla `tblProfesor`
--
ALTER TABLE `tblProfesor`
  ADD PRIMARY KEY (`IDProfesor`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tblAlumCurs`
--
ALTER TABLE `tblAlumCurs`
  ADD CONSTRAINT `fk_AlumCurs` FOREIGN KEY (`IDCurso`) REFERENCES `tblCurso` (`IDCurso`),
  ADD CONSTRAINT `fk_AlumDNI` FOREIGN KEY (`DNI`) REFERENCES `tblAlumno` (`DNI`);

--
-- Filtros para la tabla `tblCursProf`
--
ALTER TABLE `tblCursProf`
  ADD CONSTRAINT `fk_CursProf` FOREIGN KEY (`IDProfesor`) REFERENCES `tblProfesor` (`IDProfesor`),
  ADD CONSTRAINT `fk_CursProf2` FOREIGN KEY (`IDCurso`) REFERENCES `tblCurso` (`IDCurso`);

--
-- Filtros para la tabla `tblCurs_Nota`
--
ALTER TABLE `tblCurs_Nota`
  ADD CONSTRAINT `fk_Curso` FOREIGN KEY (`IDAlumC`) REFERENCES `tblAlumCurs` (`IDAlumC`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
