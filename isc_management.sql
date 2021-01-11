-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 11, 2021 at 07:51 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `isc_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts_admin`
--

CREATE TABLE `accounts_admin` (
  `id` bigint(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `image` text NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `clazzs`
--

CREATE TABLE `clazzs` (
  `id` bigint(20) NOT NULL,
  `clazz_status` int(11) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `num_of_stu` bigint(20) DEFAULT NULL,
  `point_graduate` double NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `companies`
--

CREATE TABLE `companies` (
  `id` bigint(20) NOT NULL,
  `addres_com` varchar(255) NOT NULL,
  `contact_person` varchar(100) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `name_com` varchar(255) NOT NULL,
  `note_com` varchar(2000) DEFAULT NULL,
  `status_com` varchar(100) NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `website_com` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `companies`
--

INSERT INTO `companies` (`id`, `addres_com`, `contact_person`, `created_by`, `created_date`, `name_com`, `note_com`, `status_com`, `updated_by`, `updated_date`, `website_com`) VALUES
(2, ' Quang Khải, Phường Tân Định, Quận 1, Thành phố Hồ Chí Minh', 'Sang', 'Admin', '2021-01-11 00:49:29', 'TOPICA', 'Oke', '1', 'Admin', '2021-01-11 00:49:29', 'https://topica.edu.vn/'),
(3, 'Văn phòng Cogo, số 3 Lương Yên, Phường Bạch Đằng, Quận Hai Bà Trưng, Thành phố Hà Nội', 'Huy', 'Admin', '2021-01-11 00:49:29', 'Vinsoftware', 'Oke', '1', 'Admin', '2021-01-11 00:49:29', 'https://vingroup.net/'),
(4, 'Tầng 1 - Toà nhà CT Building Số 56 Đường Yên Thế, Phường 02, Quận Tân Bình', 'Minh', 'Admin', '2021-01-11 00:49:29', 'SOFTWORLD VIETNAM LTD.', 'Oke', '1', 'Admin', '2021-01-11 00:49:29', 'https://softworldvietnam.com/'),
(5, '37A Phan Xích Long, Phường 03, Quận Phú Nhuận, Thành phố Hồ Chí Minh', 'Tuấn', 'Admin', '2021-01-11 00:49:29', 'ALTTEKGLOBAL - ATG CORPORATION', 'Oke', '1', 'Admin', '2021-01-11 00:49:29', 'https://www.alttekglobal.com'),
(6, '56/5/28 TX25, Phường Thạnh Xuân, Quận 12, Thành phố Hồ Chí Minh', 'Tuấn', 'Admin', '2021-01-11 00:49:29', 'Công ty cổ phần SOC', 'Oke', '1', 'Admin', '2021-01-11 00:49:29', 'https://www.alttekglobal.com'),
(7, 'Tòa nhà Saigon ICT, Công Viên Phần Mềm Quang Trung, Phường Tân Chánh Hiệp, Quận 12, Thành phố Hồ Chí Minh', 'Minh', 'Admin', '2021-01-11 00:49:29', 'Swiss Post Solutions (SPS)', 'Oke', '1', 'Admin', '2021-01-11 00:49:29', 'https://www.swisspostsolutions.com/vi/microsite/sps-viet-nam');

-- --------------------------------------------------------

--
-- Table structure for table `intakes`
--

CREATE TABLE `intakes` (
  `id` bigint(20) NOT NULL,
  `code_intake` varchar(50) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `end_day` date NOT NULL,
  `name_intake` varchar(200) NOT NULL,
  `start_day` date NOT NULL,
  `status_intake` int(11) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `major_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `intakes`
--

INSERT INTO `intakes` (`id`, `code_intake`, `created_by`, `created_date`, `end_day`, `name_intake`, `start_day`, `status_intake`, `updated_by`, `updated_date`, `major_id`) VALUES
(1, 'ISC13', NULL, NULL, '2021-01-29', 'ISC 13', '2021-01-06', 2, 'shinAdmin', '2021-01-11 18:10:44', 1),
(2, 'ISC12', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 12', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(8, 'ISC14', 'Admin', '2021-01-09 11:20:47', '2021-04-24', 'ISC 14', '2021-01-09', 1, 'shinAdmin', '2021-01-11 18:10:46', 1),
(12, 'ISC11', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 11', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(13, 'ISC10', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 10', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(14, 'ISC9', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 9\r\n', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(15, 'ISC8', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 8\r\n', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(16, 'ISC7', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 7\r\n', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(17, 'ISC6', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 6\r\n', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(18, 'ISC5', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 5\r\n', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(19, 'ISC4', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 4', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(20, 'ISC3', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 3', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(21, 'ISC2', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 2', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1),
(22, 'ISC1', 'Admin', '2021-01-09 09:53:03', '2021-07-23', 'ISC 1', '2021-03-26', 2, 'shinAdmin', '2021-01-11 18:10:38', 1);

-- --------------------------------------------------------

--
-- Table structure for table `intake_student`
--

CREATE TABLE `intake_student` (
  `intake_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `intake_student`
--

INSERT INTO `intake_student` (`intake_id`, `student_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(8, 4),
(8, 6);

-- --------------------------------------------------------

--
-- Table structure for table `intake_subject`
--

CREATE TABLE `intake_subject` (
  `intake_id` bigint(20) NOT NULL,
  `subject_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `job_titles`
--

CREATE TABLE `job_titles` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `job_status` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `job_titles`
--

INSERT INTO `job_titles` (`id`, `created_by`, `created_date`, `job_status`, `name`, `updated_by`, `updated_date`) VALUES
(1, 'Admin', '2021-01-11 01:26:05', 1, 'Front End ReactJS', 'Admin', '2021-01-11 01:26:05');

-- --------------------------------------------------------

--
-- Table structure for table `lecturers`
--

CREATE TABLE `lecturers` (
  `id` bigint(20) NOT NULL,
  `address_lec` varchar(100) DEFAULT NULL,
  `code_lec` varchar(50) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `degree` varchar(50) NOT NULL,
  `email_lec` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `image` text NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `note_lec` varchar(2000) DEFAULT NULL,
  `phone_lec` varchar(50) NOT NULL,
  `status_lec` int(11) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `lecturers`
--

INSERT INTO `lecturers` (`id`, `address_lec`, `code_lec`, `created_by`, `created_date`, `degree`, `email_lec`, `first_name`, `image`, `last_name`, `note_lec`, `phone_lec`, `status_lec`, `updated_by`, `updated_date`) VALUES
(2, 'Minus nulla ea expli', 'AAAEE', 'admin', '2021-01-09 19:33:11', 'Cum facilis totam in', 'gizonurex@mailinator.com', 'Brynn', 'AAAEE_avatar (5).jpg', 'Dalton', 'Quae et tempor sit ', '+1 (404) 185-5206', 1, 'admin', '2021-01-11 18:42:05'),
(3, 'Gò Vấp', 'AAAEEc', 'admin', '2021-01-10 17:20:39', 'Thạc sĩ', 'dungdqps08542@fpt.edu.vn', 'Anh Tuấn', 'AAAEEc_avatar (6).jpg', 'Lê', '', '0398022720', 1, 'admin', '2021-01-11 18:33:05'),
(4, 'Gò Vấp', 'AAAEEd', 'admin', '2021-01-10 17:23:15', 'Voluptate amet illo', 'dungdqps08542@fpt.edu.vnn', 'Ngọc Sơn', 'AAAEEd_avatar (9).jpg', 'Trần', '', '0398022720', 0, 'admin', '2021-01-11 18:33:30'),
(7, 'Gò Vấp', 'AAAEEe', 'admin', '2021-01-11 17:53:22', 'Thạc sĩ', 'dungdqpes08542@fpt.edu.vn', 'Minh Anh', 'AAAEEe_avatar (2).jpg', 'Trần', '', '0398022720', 0, 'admin', '2021-01-11 18:33:18'),
(8, 'Tempora pariatur Ne', 'LEC09', 'admin', '2021-01-11 18:33:51', 'Enim ex voluptas vol', 'rotatas@mailinator.com', 'Barbara', 'LEC09_avatar (8).jpg', 'Everett', 'Consectetur id ani', '+1 (625) 618-4514', 0, 'admin', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `majors`
--

CREATE TABLE `majors` (
  `id` bigint(20) NOT NULL,
  `code_major` varchar(50) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `description_major` varchar(2000) NOT NULL,
  `name_major` varchar(50) NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `majors`
--

INSERT INTO `majors` (`id`, `code_major`, `created_by`, `created_date`, `description_major`, `name_major`, `updated_by`, `updated_date`) VALUES
(1, 'CNTT', NULL, NULL, 'Oke', 'Java & React', NULL, NULL),
(2, 'CNTT2', NULL, NULL, 'Oke', 'Java & Angular', NULL, NULL),
(3, 'CNTT3', NULL, NULL, 'Oke', 'Java & Vue', NULL, NULL),
(5, 'CNTT4', NULL, NULL, 'Oke', 'Node JS & Vue JS', NULL, NULL),
(7, 'CNTT5', NULL, NULL, 'Oke', 'Node JS & React JS', NULL, NULL),
(8, 'CNTT6', NULL, NULL, 'Oke', 'Node JS & Angular JS', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_MODERATOR'),
(3, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `id` bigint(20) NOT NULL,
  `code_room` varchar(50) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `name_room` varchar(50) NOT NULL,
  `note_room` varchar(1000) DEFAULT NULL,
  `status_room` int(11) NOT NULL,
  `type_room` int(11) NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`id`, `code_room`, `created_by`, `created_date`, `name_room`, `note_room`, `status_room`, `type_room`, `updated_by`, `updated_date`) VALUES
(1, 'ROOM1', 'Admin', '2021-01-11 00:52:20', 'ROOM 01', 'Oke', 1, 1, 'Admin', '2021-01-11 00:52:20'),
(2, 'ROOM2', 'Admin', '2021-01-11 00:52:20', 'ROOM 02', 'Oke', 1, 1, 'Admin', '2021-01-11 00:52:20'),
(3, 'ROOM3', 'Admin', '2021-01-11 00:52:20', 'ROOM 03', 'Oke', 1, 1, 'Admin', '2021-01-11 00:52:20'),
(4, 'ROOM4', 'Admin', '2021-01-11 00:52:20', 'ROOM 04', 'Oke', 1, 1, 'Admin', '2021-01-11 00:52:20'),
(5, 'ROOM5', 'Admin', '2021-01-11 00:52:20', 'ROOM 05', 'Oke', 1, 1, 'Admin', '2021-01-11 00:52:20'),
(6, 'ROOM6', 'Admin', '2021-01-11 00:52:20', 'ROOM 06', 'Oke', 1, 1, 'Admin', '2021-01-11 00:52:20'),
(7, 'ROOM7', 'Admin', '2021-01-11 00:52:20', 'ROOM 07', 'Oke', 1, 1, 'Admin', '2021-01-11 00:52:20'),
(8, 'ROOM8', 'Admin', '2021-01-11 00:52:20', 'ROOM 08', 'Oke', 1, 1, 'Admin', '2021-01-11 00:52:20'),
(9, 'ROOM9', 'Admin', '2021-01-11 00:52:20', 'ROOM 09', 'Oke', 1, 1, 'Admin', '2021-01-11 00:52:20'),
(10, 'ROOM10', 'Admin', '2021-01-11 00:52:20', 'ROOM 10', 'Oke', 1, 1, 'Admin', '2021-01-11 00:52:20');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` bigint(20) NOT NULL,
  `address_stu` varchar(100) NOT NULL,
  `code_stu` varchar(50) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `email_stu` varchar(100) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `gpa` double NOT NULL,
  `image` text NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `note_stu` varchar(1000) DEFAULT NULL,
  `phone_stu` varchar(50) NOT NULL,
  `type_stu` int(11) NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `working_status` int(11) NOT NULL,
  `university_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `address_stu`, `code_stu`, `created_by`, `created_date`, `email_stu`, `first_name`, `gpa`, `image`, `last_name`, `note_stu`, `phone_stu`, `type_stu`, `updated_by`, `updated_date`, `working_status`, `university_id`) VALUES
(1, 'Gò Vấp', 'PS08542', 'Admin', '2021-01-09 17:25:09', 'dungdqps08542@fpt.edu.vn', 'Đoàn', 10, 'PS08542_avatar (18).jpg', 'Dũng', 'Oke', '0398022720', 1, 'admin', '2021-01-11 18:27:31', 1, 1),
(2, 'Gò Vấp', 'PS085422', 'Admin', '2021-01-10 12:59:52', 'dungdqps08542@fpt.edu.vnn', 'Đoàn', 123, 'PS085422_avatar (3).jpg', 'Dũng', '123123', '0398022720', 2, 'admin', '2021-01-11 18:27:25', 1, 2),
(3, 'Gò Vấp', 'STD01', 'Admin', '2021-01-10 13:02:14', 'tigefizop@mailinator.com', 'Haviva', 9, 'STD01_avatar (15).jpg', 'Sharpe', 'Oke', '0328050542', 0, 'admin', '2021-01-11 18:27:37', 1, 1),
(4, 'AAAAAAAAA', '123', 'Admin', '2021-01-10 16:37:53', 'doanquocdung55@gmail.com', 'HEHE', 10, '123_avatar (12).jpg', 'HAHHA', '', '0328050520', 0, 'admin', '2021-01-11 18:27:42', 1, 4),
(6, 'Quis sed laboriosam', 'HS012', 'admin', '2021-01-11 18:28:20', 'vaxuxesex@mailinator.com', 'Wynter', 8, 'HS012_avatar (16).jpg', 'Castaneda', 'Itaque odit consequa', '+1 (712) 848-5019', 0, NULL, NULL, 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `student_company`
--

CREATE TABLE `student_company` (
  `company_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `subjects`
--

CREATE TABLE `subjects` (
  `id` bigint(20) NOT NULL,
  `code_sub` varchar(50) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `credit_sub` double NOT NULL,
  `name_sub` varchar(50) NOT NULL,
  `note_sub` varchar(1000) DEFAULT NULL,
  `pass_core` double NOT NULL,
  `status_sub` int(11) NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `subjects`
--

INSERT INTO `subjects` (`id`, `code_sub`, `created_by`, `created_date`, `credit_sub`, `name_sub`, `note_sub`, `pass_core`, `status_sub`, `updated_by`, `updated_date`) VALUES
(1, 'JAVA', 'Admin', '2021-01-11 00:03:50', 10, 'Java Spring Boot', 'Oke', 6, 1, 'Admin', '2021-01-11 00:03:50'),
(2, 'REACT', 'Admin', '2021-01-11 00:03:50', 10, 'REACT JS', 'Oke', 6, 1, 'Admin', '2021-01-11 00:03:50'),
(3, 'Database', 'Admin', '2021-01-11 00:03:50', 10, 'Database', 'Oke', 6, 1, 'Admin', '2021-01-11 00:03:50'),
(4, 'OT', 'Admin', '2021-01-11 00:03:50', 10, 'On-job Training', 'Oke', 6, 1, 'Admin', '2021-01-11 00:03:50'),
(5, 'Back-end', 'Admin', '2021-01-11 00:03:50', 10, 'Back-end', 'Oke', 6, 1, 'Admin', '2021-01-11 00:03:50'),
(6, 'WP', 'Admin', '2021-01-11 00:03:50', 10, 'Working Process', 'Oke', 6, 1, 'Admin', '2021-01-11 00:03:50'),
(7, 'LAN', 'Admin', '2021-01-11 00:03:50', 10, 'English', 'Oke', 6, 1, 'Admin', '2021-01-11 00:03:50');

-- --------------------------------------------------------

--
-- Table structure for table `university`
--

CREATE TABLE `university` (
  `id` bigint(20) NOT NULL,
  `address_uni` varchar(255) NOT NULL,
  `contact_person` varchar(150) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `name_uni` varchar(100) NOT NULL,
  `note_uni` varchar(1000) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `website_uni` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `university`
--

INSERT INTO `university` (`id`, `address_uni`, `contact_person`, `created_by`, `created_date`, `name_uni`, `note_uni`, `updated_by`, `updated_date`, `website_uni`) VALUES
(1, '391A Nam Kỳ Khởi Nghĩa, Q. 3, TP. Hồ Chí Minh', 'HT', 'Admin', '2021-01-10 22:45:52', 'FPT Polytechnic HCM', 'OKe', 'Admin', '2021-01-11 23:22:47', 'caodang.fpt.edu.vn'),
(2, '268 Lý Thường Kiệt, phường 14, quận 10, TP.Hồ Chí Minh', 'HT', 'Admin', '2021-01-10 22:45:57', 'Trường Đại học Bách Khoa - ĐHQG TP.HCM', 'OKe', 'Admin', '2021-01-11 23:22:51', 'hcmut.edu.vn'),
(4, '01 Võ Văn Ngân, P. Linh Chiểu, Q. Thủ Đức, TP. Hồ Chí Minh;', 'Minh', 'Admin', '2021-01-10 22:46:00', 'Trường Đại học Sư phạm Kỹ thuật TP.HCM', 'OKe', 'Admin', '2021-01-11 23:22:55', 'hcmute.edu.vn'),
(5, 'TP. Hồ Chí Minh', 'HT', 'Admin', '2021-01-10 22:46:02', ' Trường Đại học Sài Gòn', 'OKe', 'Admin', '2021-01-11 23:22:56', 'sgu.edu.vn'),
(8, '391A Nam Kỳ Khởi Nghĩa, Q. 3, TP. Hồ Chí Minh', 'HT', 'Admin', '2021-01-10 22:45:52', 'Sài Gòn Tech', 'OKe', 'Admin', '2021-01-11 23:23:03', 'saigontech.edu.vn');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `username`) VALUES
(1, 'shinAdmin@gmail.com', '$2a$10$nToYTU1/DErcYJVyZHM0b.2GzYD0VPTDZ2qX9ZGPmyMLK5KusQShW', 'shinAdmin'),
(2, 'adminn@gmail.com', '$2a$10$zmYCYSFCjgLN7rcyzekLceUItUe0r2lZT0BUgi1BJdzXBnpVZU2iS', 'admin'),
(3, 'dungdq@gmail.com', '$2a$10$anoESPpT8ntUAMrFzOB1PuriPpS5YbihqToS6PWMc50wLXj6IKyaq', 'dungdq');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 3),
(2, 3),
(3, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts_admin`
--
ALTER TABLE `accounts_admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_omri4nj9b00e3iofnde6ytk3t` (`email`);

--
-- Indexes for table `clazzs`
--
ALTER TABLE `clazzs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_icillvvhaykdaeplbul8fhhd7` (`name`);

--
-- Indexes for table `companies`
--
ALTER TABLE `companies`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `intakes`
--
ALTER TABLE `intakes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_tatrhpsp8e1uul9gbej3ntp8x` (`code_intake`),
  ADD KEY `FKf3o82srcm1edot08tkd30rmvt` (`major_id`);

--
-- Indexes for table `intake_student`
--
ALTER TABLE `intake_student`
  ADD PRIMARY KEY (`intake_id`,`student_id`),
  ADD KEY `FKoq17y8kcp6psjhijhb3385dtl` (`student_id`);

--
-- Indexes for table `intake_subject`
--
ALTER TABLE `intake_subject`
  ADD PRIMARY KEY (`intake_id`,`subject_id`),
  ADD KEY `FKl6sqtdq6t4yvm65gl9tg7rti9` (`subject_id`);

--
-- Indexes for table `job_titles`
--
ALTER TABLE `job_titles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_mq7g55vh6unug2wb17ajk0q9q` (`name`);

--
-- Indexes for table `lecturers`
--
ALTER TABLE `lecturers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_o032revwlw7jm22j5frgmhseo` (`code_lec`),
  ADD UNIQUE KEY `UK_bltjlp5ir6iv5oaxgo5bvq6sk` (`email_lec`);

--
-- Indexes for table `majors`
--
ALTER TABLE `majors`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_55rud77lgalv5ywghxt9lhgoo` (`code_major`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_76a7f3cl1evph68tkgfivirbk` (`code_stu`),
  ADD UNIQUE KEY `UK_8hyd9vyx1ttnb2kdpog7bljf8` (`email_stu`),
  ADD KEY `FKdyktoy09emaoa93ps4do0rrw5` (`university_id`);

--
-- Indexes for table `student_company`
--
ALTER TABLE `student_company`
  ADD PRIMARY KEY (`company_id`,`student_id`),
  ADD KEY `FKdapdqnb4hf0hbgcyt2yicmp5` (`student_id`);

--
-- Indexes for table `subjects`
--
ALTER TABLE `subjects`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_npkscsjhso4n5vbep0qbg8n1u` (`code_sub`);

--
-- Indexes for table `university`
--
ALTER TABLE `university`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts_admin`
--
ALTER TABLE `accounts_admin`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clazzs`
--
ALTER TABLE `clazzs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `companies`
--
ALTER TABLE `companies`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `intakes`
--
ALTER TABLE `intakes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `job_titles`
--
ALTER TABLE `job_titles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `lecturers`
--
ALTER TABLE `lecturers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `majors`
--
ALTER TABLE `majors`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `subjects`
--
ALTER TABLE `subjects`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `university`
--
ALTER TABLE `university`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `intakes`
--
ALTER TABLE `intakes`
  ADD CONSTRAINT `FKf3o82srcm1edot08tkd30rmvt` FOREIGN KEY (`major_id`) REFERENCES `majors` (`id`);

--
-- Constraints for table `intake_student`
--
ALTER TABLE `intake_student`
  ADD CONSTRAINT `FK52g7obhp4sp530goacf8nnbnj` FOREIGN KEY (`intake_id`) REFERENCES `intakes` (`id`),
  ADD CONSTRAINT `FKoq17y8kcp6psjhijhb3385dtl` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`);

--
-- Constraints for table `intake_subject`
--
ALTER TABLE `intake_subject`
  ADD CONSTRAINT `FKgxb0rqngpwism7w1ie8uftn05` FOREIGN KEY (`intake_id`) REFERENCES `intakes` (`id`),
  ADD CONSTRAINT `FKl6sqtdq6t4yvm65gl9tg7rti9` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`);

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `FKdyktoy09emaoa93ps4do0rrw5` FOREIGN KEY (`university_id`) REFERENCES `university` (`id`);

--
-- Constraints for table `student_company`
--
ALTER TABLE `student_company`
  ADD CONSTRAINT `FKdapdqnb4hf0hbgcyt2yicmp5` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`),
  ADD CONSTRAINT `FKdxjsh2tk9hm93p0jxak7n4e1u` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
