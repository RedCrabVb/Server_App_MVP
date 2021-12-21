-- MySQL Script generated by MySQL Workbench
-- Сб 18 дек 2021 16:46:20
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema MvpDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema MvpDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MvpDB` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `MvpDB` ;

-- -----------------------------------------------------
-- Table `MvpDB`.`Accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MvpDB`.`Accounts` (
  `idAccount` INT(11) NOT NULL AUTO_INCREMENT,
  `accountActiveTime` DATE NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `qrCode` VARCHAR(255) NULL DEFAULT NULL,
  `token` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`idAccount`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `MvpDB`.`News`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MvpDB`.`News` (
  `idNews` INT(11) NOT NULL AUTO_INCREMENT,
  `body` VARCHAR(255) NULL DEFAULT NULL,
  `imgPath` VARCHAR(255) NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`idNews`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `MvpDB`.`ResetPassword`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MvpDB`.`ResetPassword` (
  `idResetPassword` INT(11) NOT NULL AUTO_INCREMENT,
  `dateActiveToken` DATE NULL DEFAULT NULL,
  `tmpPassword` VARCHAR(255) NULL DEFAULT NULL,
  `token` VARCHAR(255) NULL DEFAULT NULL,
  `idAccount` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idResetPassword`),
  INDEX `FKn8imqquajjv494bt8j9i1mf00` (`idAccount` ASC) VISIBLE,
  CONSTRAINT `FKn8imqquajjv494bt8j9i1mf00`
    FOREIGN KEY (`idAccount`)
    REFERENCES `MvpDB`.`Accounts` (`idAccount`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `MvpDB`.`question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MvpDB`.`question` (
  `idQuestion` INT(11) NOT NULL AUTO_INCREMENT,
  `answer` VARCHAR(255) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `idTest` INT(11) NOT NULL,
  `text` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`idQuestion`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `MvpDB`.`test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MvpDB`.`test` (
  `idTest` INT(11) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `test` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`idTest`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
