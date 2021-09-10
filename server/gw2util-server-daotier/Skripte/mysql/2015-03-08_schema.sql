CREATE TABLE `prod-guildwars2`.`GemsStatistic` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CreateDate` DATETIME NOT NULL,
  `UpdateDate` DATETIME NOT NULL,
  `CoinsToGem` BIGINT(20) NOT NULL,
  `GemToCoins` BIGINT(20) NOT NULL,
  `UpdateUser` BIGINT(20) NOT NULL,
  `CreateUser` BIGINT(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  INDEX `UpdateUserGemsStatistic_idx` (`UpdateUser` ASC),
  INDEX `CreateUserGemsStatistic_idx` (`CreateUser` ASC),
  CONSTRAINT `UpdateUserGemsStatistic`
    FOREIGN KEY (`UpdateUser`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`),
  CONSTRAINT `CreateUserGemsStatistic`
    FOREIGN KEY (`CreateUser`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE `prod-guildwars2`.`Weapon` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  
  `WeaponType` VARCHAR(255) NOT NULL,
  `DamageType` VARCHAR(5000) NOT NULL,
  `MinPower` INT NOT NULL,
  `MaxPower` INT NOT NULL,
  `Defense` INT NOT NULL,
  `InfusionSlots` VARCHAR(255) NOT NULL,

  `SuffixItemID` BIGINT(20) NULL,
  `SecondarySuffixItemID` BIGINT(20) NULL,

  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  INDEX `SuffixItemItem_idx` (`SuffixItemID` ASC),
  INDEX `SecondarySuffixItemItem_idx` (`SecondarySuffixItemID` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `prod-guildwars2`.`Item` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CreateDate` DATETIME NOT NULL,
  `CreateUser` BIGINT(20) NOT NULL,
  `UpdateDate` DATETIME NOT NULL,
  `UpdateUser` BIGINT(20) NOT NULL,
  
  `ExternalID` BIGINT(20) NOT NULL,
  `Name` VARCHAR(255) NOT NULL,
  `Description` VARCHAR(5000) NOT NULL,
  `ItemType` VARCHAR(255) NOT NULL,
  `Level` INT NOT NULL,
  `ItemRarity` VARCHAR(255) NOT NULL,
  `VendorValue` BIGINT(20) NOT NULL,
  `IconFileID` BIGINT(20) NOT NULL,
  `IconFileSignature` VARCHAR(255) NOT NULL,
  `DefaultSkin` BIGINT(20) NOT NULL,
  `Language` VARCHAR(255) NOT NULL,

  `WeaponID` BIGINT(20) NULL,

  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  INDEX `UpdateUserItem_idx` (`UpdateUser` ASC),
  INDEX `CreateUserItem_idx` (`CreateUser` ASC),
  INDEX `WeaponItem_idx` (`WeaponID` ASC),
  CONSTRAINT `ItemUpdateUserItem`
    FOREIGN KEY (`UpdateUser`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`),
  CONSTRAINT `ItemCreateUserItem`
    FOREIGN KEY (`CreateUser`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`),
  CONSTRAINT `ItemWeaponItem`
    FOREIGN KEY (`WeaponID`)
    REFERENCES `prod-guildwars2`.`Weapon` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `prod-guildwars2`.`Recipe` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CreateDate` DATETIME NOT NULL,
  `CreateUser` BIGINT(20) NOT NULL,
  `UpdateDate` DATETIME NOT NULL,
  `UpdateUser` BIGINT(20) NOT NULL,
  
  `ExternalID` BIGINT(20) NOT NULL,
  `Name` VARCHAR(255) NOT NULL,
  `Description` VARCHAR(5000) NOT NULL,
  `ItemType` VARCHAR(255) NOT NULL,
  `Level` INT NOT NULL,
  `ItemRarity` VARCHAR(255) NOT NULL,
  `VendorValue` BIGINT(20) NOT NULL,
  `IconFileID` BIGINT(20) NOT NULL,
  `IconFileSignature` VARCHAR(255) NOT NULL,
  `DefaultSkin` BIGINT(20) NOT NULL,
  `Language` VARCHAR(255) NOT NULL,

  `WeaponID` BIGINT(20) NULL,

  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  INDEX `UpdateUserItem_idx` (`UpdateUser` ASC),
  INDEX `CreateUserItem_idx` (`CreateUser` ASC),
  INDEX `WeaponItem_idx` (`WeaponID` ASC),
  CONSTRAINT `RecipeUpdateUserItem`
    FOREIGN KEY (`UpdateUser`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`),
  CONSTRAINT `RecipeCreateUserItem`
    FOREIGN KEY (`CreateUser`)
    REFERENCES `prod-guildwars2`.`Users` (`ID`),
  CONSTRAINT `RecipeWeaponItem`
    FOREIGN KEY (`WeaponID`)
    REFERENCES `prod-guildwars2`.`Weapon` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `prod-guildwars2`.`Weapon`
ADD CONSTRAINT `SuffixItemItem`
FOREIGN KEY (`SuffixItemID`)
REFERENCES  `prod-guildwars2`.`Item` (`ID`);

ALTER TABLE `prod-guildwars2`.`Weapon`
ADD CONSTRAINT `SecondarySuffixItemItem`
FOREIGN KEY (`SecondarySuffixItemID`)
REFERENCES `prod-guildwars2`.`Item` (`ID`);

CREATE TABLE `prod-guildwars2`.`ItemFlagRel` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  
  `Flag` VARCHAR(255) NOT NULL,
  `ItemID` BIGINT(20) NOT NULL,

  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  INDEX `ifrItemID_idx` (`ItemID` ASC),
  CONSTRAINT `ifrItemIDItem`
    FOREIGN KEY (`ItemID`)
    REFERENCES `prod-guildwars2`.`Item` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
COMMIT;

CREATE TABLE `prod-guildwars2`.`ItemGameTypeRel` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  
  `GameType` VARCHAR(255) NOT NULL,
  `ItemID` BIGINT(20) NOT NULL,

  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  INDEX `igrItemID_idx` (`ItemID` ASC),
  CONSTRAINT `igrItemIDItem`
    FOREIGN KEY (`ItemID`)
    REFERENCES `prod-guildwars2`.`Item` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
COMMIT;