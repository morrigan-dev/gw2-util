SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `prod-guildwars2` DEFAULT CHARACTER SET utf8 ;
USE `prod-guildwars2` ;

-- -----------------------------------------------------
-- Table `prod-guildwars2`.`usergroup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prod-guildwars2`.`UserGroup` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ActiveState` VARCHAR(50) NOT NULL,
  `Name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Name` (`Name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `prod-guildwars2`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prod-guildwars2`.`Users` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ActiveState` VARCHAR(50) NOT NULL,
  `FirstName` VARCHAR(255) NOT NULL,
  `LastName` VARCHAR(255) NOT NULL,
  `Password` VARCHAR(255) NOT NULL,
  `SaltHash` VARCHAR(255) NOT NULL,
  `UserName` VARCHAR(255) NOT NULL,
  `UserGroupID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK4E39DE88412BFD3` (`UserGroupID` ASC),
  CONSTRAINT `FK4E39DE88412BFD3`
    FOREIGN KEY (`UserGroupID`)
    REFERENCES `prod-guildwars2`.`UserGroup` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `prod-guildwars2`.`clientdata`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prod-guildwars2`.`ClientData` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ActiveState` VARCHAR(50) NOT NULL,
  `CreateDate` DATETIME NOT NULL,
  `IP` VARCHAR(39) NOT NULL,
  `MAC` VARCHAR(255) NOT NULL,
  `UpdateDate` DATETIME NOT NULL,
  `CreateUser` BIGINT(20) NOT NULL,
  `UpdateUser` BIGINT(20) NOT NULL,
  `UserID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID` (`ID` ASC),
  INDEX `FKB8805635D7AA17E2` (`CreateUser` ASC),
  INDEX `FKB8805635746A012F` (`UpdateUser` ASC),
  INDEX `FKB880563554299B41` (`UserID` ASC),
  CONSTRAINT `FKB880563554299B41`
    FOREIGN KEY (`UserID`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`),
  CONSTRAINT `FKB8805635746A012F`
    FOREIGN KEY (`UpdateUser`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`),
  CONSTRAINT `FKB8805635D7AA17E2`
    FOREIGN KEY (`CreateUser`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `prod-guildwars2`.`rights`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prod-guildwars2`.`Rights` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ActiveState` VARCHAR(50) NOT NULL,
  `LabelKey` VARCHAR(255) NOT NULL,
  `RightKey` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `LabelKey` (`LabelKey` ASC),
  UNIQUE INDEX `RightKey` (`RightKey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `prod-guildwars2`.`session`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prod-guildwars2`.`Session` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `SessionKey` VARCHAR(255) NOT NULL,
  `TimeOfLastAccess` DATETIME NOT NULL,
  `TimeOfLastAuth` DATETIME NOT NULL,
  `UserID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID` (`ID` ASC),
  UNIQUE INDEX `SessionKey` (`SessionKey` ASC),
  INDEX `FKD9891A7654299B41` (`UserID` ASC),
  CONSTRAINT `FKD9891A7654299B41`
    FOREIGN KEY (`UserID`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `prod-guildwars2`.`usergrouprightrelation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prod-guildwars2`.`UserGroupRightRelation` (
  `ID` BIGINT(20) NOT NULL,
  `RightID` BIGINT(20) NOT NULL,
  `UserGroupID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`UserGroupID`, `RightID`),
  INDEX `FK31FF5B44812FA703` (`RightID` ASC),
  INDEX `FK31FF5B448412BFD3` (`UserGroupID` ASC),
  CONSTRAINT `FK31FF5B448412BFD3`
    FOREIGN KEY (`UserGroupID`)
    REFERENCES `prod-guildwars2`.`UserGroup` (`ID`),
  CONSTRAINT `FK31FF5B44812FA703`
    FOREIGN KEY (`RightID`)
    REFERENCES `prod-guildwars2`.`Rights` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `prod-guildwars2`.`waypoint`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prod-guildwars2`.`Waypoint` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ActiveState` VARCHAR(50) NOT NULL,
  `CreateDate` DATETIME NOT NULL,
  `InformationKey` VARCHAR(255) NOT NULL,
  `Latitude` DOUBLE NOT NULL,
  `Longitude` DOUBLE NOT NULL,
  `UpdateDate` DATETIME NOT NULL,
  `WPSubType` VARCHAR(50) NOT NULL,
  `WPType` VARCHAR(50) NOT NULL,
  `CreateUser` BIGINT(20) NOT NULL,
  `UpdateUser` BIGINT(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID` (`ID` ASC),
  INDEX `FK2D9B6C21D7AA17E2` (`CreateUser` ASC),
  INDEX `FK2D9B6C21746A012F` (`UpdateUser` ASC),
  CONSTRAINT `FK2D9B6C21746A012F`
    FOREIGN KEY (`UpdateUser`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`),
  CONSTRAINT `FK2D9B6C21D7AA17E2`
    FOREIGN KEY (`CreateUser`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
