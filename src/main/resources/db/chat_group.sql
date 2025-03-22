DROP TABLE IF EXISTS `groupMember`;
CREATE TABLE `groupMember` (
  `gid` VARCHAR(255) NOT NULL,
  `cid` varchar(255) NOT NULL,
  CONSTRAINT gid FOREIGN KEY(gid) REFERENCES groupinfo(gid),
	CONSTRAINT ccid FOREIGN KEY(cid) REFERENCES user(uid) 
) DEFAULT CHARSET=utf8;
INSERT INTO `groupMember` VALUES ('g1', 'c1');
INSERT INTO `groupMember` VALUES ('g1', 'ctest');
INSERT INTO `groupMember` VALUES ('g1', 'c2');
INSERT INTO `groupMember` VALUES ('g2', 'c1');
INSERT INTO `groupMember` VALUES ('g2', 'c2');