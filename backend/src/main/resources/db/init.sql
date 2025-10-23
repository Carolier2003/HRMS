-- 创建数据库
CREATE DATABASE IF NOT EXISTS `hr` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `hr`;

-- 用户表
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码（BCrypt加密）',
  `real_name` VARCHAR(50) COMMENT '真实姓名',
  `email` VARCHAR(100) COMMENT '邮箱',
  `phone` VARCHAR(20) COMMENT '手机号',
  `employee_id` BIGINT COMMENT '关联员工ID',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
  `role_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色代码',
  `description` VARCHAR(200) COMMENT '角色描述',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户角色关联表
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 员工表
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `employee_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '工号',
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `gender` VARCHAR(10) COMMENT '性别',
  `birth_date` DATE COMMENT '出生日期',
  `id_card` VARCHAR(18) COMMENT '身份证号',
  `phone` VARCHAR(20) COMMENT '联系电话',
  `email` VARCHAR(100) COMMENT '邮箱',
  `address` VARCHAR(200) COMMENT '家庭住址',
  
  `education` VARCHAR(20) COMMENT '学历：小学/初中/高中/专科/本科/硕士/博士',
  `major` VARCHAR(50) COMMENT '专业',
  `graduate_school` VARCHAR(100) COMMENT '毕业院校',
  `graduate_date` DATE COMMENT '毕业时间',
  
  `marital_status` VARCHAR(20) COMMENT '婚姻状况：未婚/已婚/离异/丧偶',
  
  `department` VARCHAR(50) COMMENT '部门',
  `position` VARCHAR(50) COMMENT '岗位',
  `job_title` VARCHAR(50) COMMENT '职称',
  `entry_date` DATE COMMENT '入职时间',
  `work_years` INT COMMENT '工作年限',
  
  `status` VARCHAR(20) DEFAULT '在职' COMMENT '员工状态：在职/离职/退休/辞退',
  `leave_date` DATE COMMENT '离职时间',
  `leave_reason` VARCHAR(200) COMMENT '离职原因',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

-- 工资项目表
DROP TABLE IF EXISTS `salary_items`;
CREATE TABLE `salary_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '工资项ID',
  `item_name` VARCHAR(50) NOT NULL COMMENT '项目名称',
  `item_type` VARCHAR(20) NOT NULL COMMENT '项目类型：BASE-基本工资/BONUS-奖金/DEDUCTION-扣款',
  `description` VARCHAR(200) COMMENT '说明',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否启用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工资项目表';

-- 工资记录表
DROP TABLE IF EXISTS `salary_records`;
CREATE TABLE `salary_records` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '工资记录ID',
  `employee_id` BIGINT NOT NULL COMMENT '员工ID',
  `year_month` VARCHAR(7) NOT NULL COMMENT '年月：YYYY-MM',
  `base_salary` DECIMAL(10,2) DEFAULT 0 COMMENT '基本工资',
  `bonus` DECIMAL(10,2) DEFAULT 0 COMMENT '奖金',
  `deduction` DECIMAL(10,2) DEFAULT 0 COMMENT '扣款',
  `actual_salary` DECIMAL(10,2) NOT NULL COMMENT '实发工资',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` BIGINT COMMENT '录入人',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_emp_month` (`employee_id`, `year_month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工资记录表';

-- 工资明细表
DROP TABLE IF EXISTS `salary_details`;
CREATE TABLE `salary_details` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `salary_record_id` BIGINT NOT NULL COMMENT '工资记录ID',
  `item_id` BIGINT NOT NULL COMMENT '工资项ID',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工资明细表';

-- 初始化角色数据
INSERT INTO `roles` (`role_name`, `role_code`, `description`) VALUES
('管理员', 'ADMIN', '系统管理员，拥有所有权限'),
('人事专员', 'HR', '人事管理员，可管理员工和工资'),
('普通员工', 'EMPLOYEE', '普通员工，只能查看个人信息');

-- 初始化管理员账号（密码：123456，BCrypt加密）
-- 密码由 Spring Security BCryptPasswordEncoder 生成
INSERT INTO `users` (`username`, `password`, `real_name`, `email`, `status`) VALUES
('admin', '$2a$10$nEMX0j5vkX5NyGcCAmfuledH6sZA/iOWEX41QORv86uCUoITGVyKq', '系统管理员', 'admin@hr.com', 1),
('hr001', '$2a$10$nEMX0j5vkX5NyGcCAmfuledH6sZA/iOWEX41QORv86uCUoITGVyKq', '张人事', 'hr001@hr.com', 1),
('emp001', '$2a$10$nEMX0j5vkX5NyGcCAmfuledH6sZA/iOWEX41QORv86uCUoITGVyKq', '李员工', 'emp001@hr.com', 1);

-- 分配角色
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),  -- admin -> ADMIN
(2, 2),  -- hr001 -> HR
(3, 3);  -- emp001 -> EMPLOYEE

-- 初始化工资项目
INSERT INTO `salary_items` (`item_name`, `item_type`, `description`) VALUES
('基本工资', 'BASE', '每月固定基本工资'),
('绩效奖金', 'BONUS', '根据绩效考核发放'),
('全勤奖', 'BONUS', '全勤员工奖励'),
('加班费', 'BONUS', '加班补贴'),
('五险一金', 'DEDUCTION', '社保公积金个人部分'),
('个人所得税', 'DEDUCTION', '个税代扣'),
('迟到扣款', 'DEDUCTION', '迟到罚款');

-- 初始化示例员工数据
INSERT INTO `employees` (`employee_no`, `name`, `gender`, `birth_date`, `phone`, `email`, `education`, `marital_status`, `department`, `position`, `entry_date`, `status`) VALUES
('E001', '张三', '男', '1990-05-15', '13800138001', 'zhangsan@hr.com', '本科', '已婚', '技术部', 'Java工程师', '2020-01-10', '在职'),
('E002', '李四', '女', '1992-08-20', '13800138002', 'lisi@hr.com', '硕士', '未婚', '技术部', '前端工程师', '2021-03-15', '在职'),
('E003', '王五', '男', '1988-12-10', '13800138003', 'wangwu@hr.com', '本科', '已婚', '人事部', '人事专员', '2019-06-01', '在职'),
('E004', '赵六', '女', '1995-03-25', '13800138004', 'zhaoliu@hr.com', '专科', '未婚', '市场部', '市场专员', '2022-09-01', '在职'),
('E005', '钱七', '男', '1985-07-30', '13800138005', 'qianqi@hr.com', '博士', '已婚', '技术部', '技术总监', '2018-01-01', '在职');

-- 关联员工和用户
UPDATE `users` SET `employee_id` = 3 WHERE `username` = 'emp001';

-- 初始化示例工资数据
INSERT INTO `salary_records` (`employee_id`, `year_month`, `base_salary`, `bonus`, `deduction`, `actual_salary`, `created_by`) VALUES
(1, '2025-09', 12000.00, 3000.00, 2500.00, 12500.00, 1),
(2, '2025-09', 10000.00, 2000.00, 2000.00, 10000.00, 1),
(3, '2025-09', 8000.00, 1000.00, 1500.00, 7500.00, 1),
(1, '2025-10', 12000.00, 3500.00, 2500.00, 13000.00, 1),
(2, '2025-10', 10000.00, 2500.00, 2000.00, 10500.00, 1);

