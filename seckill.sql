use seckill;

CREATE TABLE users (
  id int(11) not null auto_increment,
  uname varchar(255) character set utf8mb4 not null COMMENT '用户名',
  upassword varchar(255) character set utf8mb4 not null COMMENT '密码',
  phone varchar(50) not null COMMENT '手机号',
  email varchar(100) character set utf8mb4 not null COMMENT '邮箱',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_time timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE product (
  id int(11) not null auto_increment,
  pname varchar(255) DEFAULT NULL  COMMENT '商品名',
  pcode varchar(255) DEFAULT NULL COMMENT '编号',
  stock bigint(20) DEFAULT NULL COMMENT '库存',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_time timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商品表';

CREATE TABLE product_kill (
  id int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  pid int(11) DEFAULT NULL COMMENT '商品id',
  start_time datetime DEFAULT NULL COMMENT '秒杀开始时间',
  end_time datetime DEFAULT NULL COMMENT '秒杀结束时间',
  create_time timestamp NULL DEFAULT NULL COMMENT '创建的时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='待秒杀商品表';

CREATE TABLE k_order (
  id int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  ocode varchar(50) NOT NULL COMMENT '订单编号',
  kid int(11) DEFAULT NULL COMMENT '秒杀id',
  uid varchar(20) DEFAULT NULL COMMENT '用户id',
  ostatus tinyint(4) DEFAULT '-1' COMMENT '秒杀结果: -1无效  0成功(未付款)  1已付款  2已取消',
  create_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功订单表';

INSERT INTO product VALUES ('1', 'Java编程思想', 'book10010', '1000', '2020-12-6 21:11:23', '2020-12-6 21:11:23');

INSERT INTO product_kill VALUES ('1', '1', '2020-12-06 11:59:07', '2020-12-06 21:59:11', '2020-12-6 21:11:23');

INSERT INTO users VALUES ('1', 'admin', 'admin', '18819253075', '1049593374@qq.com', null, null);
