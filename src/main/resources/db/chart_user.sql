DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) UNIQUE,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) DEFAULT CHARSET=utf8;
INSERT INTO `user` VALUES ('c1', 'c1@c1','aa', '1');
INSERT INTO `user` VALUES ('c2', 'c2@c2','bb', '1');
INSERT INTO `user` VALUES ('c3', 'c3@c3','cc', '1');
INSERT INTO `user` VALUES ('ctest', 'ct@ct','testname', 'test');