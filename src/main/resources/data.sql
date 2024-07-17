create schema function_instance collate utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`    VARCHAR(64)     NOT NULL COMMENT '名称',
    `points`  INT             NOT NULL DEFAULT 0 COMMENT '积分  ',
    `version` INT             NOT NULL DEFAULT 0 COMMENT '版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT '用户表';

insert into user(id, name)
values (1, '测试用户');