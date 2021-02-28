create database seckill;
use seckill;

CREATE TABLE users (
  id int(11) not null auto_increment,
  user_name varchar(255) character set utf8mb4 not null COMMENT '用户名',
  user_password varchar(255) character set utf8mb4 not null COMMENT '密码',
  user_email varchar(100) character set utf8mb4 not null COMMENT '邮箱',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE item (
  id int(11) not null auto_increment,
  item_title varchar(255) DEFAULT NULL  COMMENT '商品名',
  item_img varchar(255) DEFAULT NULL COMMENT '图片文件名',
  item_stock int(11) DEFAULT NULL COMMENT '库存',
  item_price float DEFAULT NULL COMMENT '单价',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商品表';

CREATE TABLE item_kill (
  id int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  item_id int(11) DEFAULT NULL COMMENT '商品id',
  item_kill_seckillStartTime datetime DEFAULT NULL COMMENT '秒杀开始时间',
  item_kill_seckillEndTime datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='待秒杀商品表';

CREATE TABLE orders (
  id int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  orders_ocode varchar(50) NOT NULL COMMENT '订单编号',
  orders_number int(11) DEFAULT NULL COMMENT '数量',
  item_id int(11) DEFAULT NULL COMMENT '商品id',
  item_kill_id int(11) DEFAULT NULL COMMENT '秒杀id',
  user_id int(11) DEFAULT NULL COMMENT '用户id',
  orders_status tinyint(4) DEFAULT '-1' COMMENT '秒杀结果: -1无效  0成功(未付款)  1已付款  2已取消',
  orders_create_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功订单表';

INSERT INTO `seckill`.`item` (`id`, `item_title`, `item_img`, `item_stock`, `item_price`) VALUES ('1', '瑞典钓鱼', 'autumnclouds.jpg', '20', '3424.00');
INSERT INTO `seckill`.`item` (`id`, `item_title`, `item_img`, `item_stock`, `item_price`) VALUES ('2', '春季巴黎', 'christmas.jpg', '20', '2124.00');
INSERT INTO `seckill`.`item` (`id`, `item_title`, `item_img`, `item_stock`, `item_price`) VALUES ('3', '烟火晚宴', 'july4.jpg', '20', '899.00');
INSERT INTO `seckill`.`item` (`id`, `item_title`, `item_img`, `item_stock`, `item_price`) VALUES ('4', '户外野炊', 'firepots.jpg', '20', '654.00');
INSERT INTO `seckill`.`item` (`id`, `item_title`, `item_img`, `item_stock`, `item_price`) VALUES ('5', '水底冒险', 'jellyfish.jpg', '20', '664.00');
INSERT INTO `seckill`.`item` (`id`, `item_title`, `item_img`, `item_stock`, `item_price`) VALUES ('6', '山壁小屋', 'mountaincabin.jpg', '20', '678.00');

INSERT INTO `seckill`.`item_kill` (`id`, `item_id`, `item_kill_seckillStartTime`, `item_kill_seckillEndTime`) VALUES ('1', '1', '2021-02-01 01:13:13', '2021-03-11 01:13:13');
INSERT INTO `seckill`.`item_kill` (`id`, `item_id`, `item_kill_seckillStartTime`, `item_kill_seckillEndTime`) VALUES ('2', '2', '2021-02-27 01:13:13', '2021-03-01 01:13:13');
INSERT INTO `seckill`.`item_kill` (`id`, `item_id`, `item_kill_seckillStartTime`, `item_kill_seckillEndTime`) VALUES ('3', '3', '2021-02-22 01:13:13', '2021-03-07 01:13:13');
INSERT INTO `seckill`.`item_kill` (`id`, `item_id`, `item_kill_seckillStartTime`, `item_kill_seckillEndTime`) VALUES ('4', '4', '2021-02-05 01:13:13', '2021-03-17 01:13:13');
INSERT INTO `seckill`.`item_kill` (`id`, `item_id`, `item_kill_seckillStartTime`, `item_kill_seckillEndTime`) VALUES ('5', '5', '2021-02-02 01:13:13', '2021-03-09 01:13:13');
INSERT INTO `seckill`.`item_kill` (`id`, `item_id`, `item_kill_seckillStartTime`, `item_kill_seckillEndTime`) VALUES ('6', '6', '2021-02-01 01:13:13', '2021-02-02 01:13:13');

INSERT INTO `seckill`.`users` (`id`, `user_name`, `user_password`, `user_email`) VALUES ('1', 'zjj', '11111', '1049593374@qq.com');

ALTER TABLE item ADD COLUMN version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁，版本号' AFTER item_price;
