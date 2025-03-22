DROP TABLE IF EXISTS `groupinfo`;
CREATE TABLE `groupinfo` (
  `gid`	VARCHAR(225) NOT NULL,
  `ccid` VARCHAR(225) NOT NULL,
  `groupname` varchar(255) DEFAULT NULL,
  `number` INT DEFAULT NULL,
	PRIMARY KEY (`gid`),
	CONSTRAINT cid FOREIGN KEY(ccid) REFERENCES user(uid)
) DEFAULT CHARSET=utf8;
INSERT INTO `groupinfo` VALUES ('g1', 'c1', 'group1', 3);
INSERT INTO `groupinfo` VALUES ('g2', 'c1', 'group2', 2);
