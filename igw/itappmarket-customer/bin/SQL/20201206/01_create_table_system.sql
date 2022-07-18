
-- 管理员
insert into system_user_info (pk_serial,system_id,account_id,account_name,password_no,valid_flag,created_user,created_date)
values (seq_system_user_pk.nextval,'igw-m-material','admin','系统管理员','c4ca4238a0b923820dcc509a6f75849b','0','system',sysdate);
insert into system_role_info (pk_serial,system_id,role_id,role_name,valid_flag,created_user,created_date)
values (seq_system_role_pk.nextval,'igw-m-material','ADMIN','管理员','0','system',sysdate);



-- 系统管理
insert into system_menu_info (pk_serial,system_id,menu_id,menu_name,menu_level,menu_url,menu_pre,valid_flag,created_user,created_date)
values (seq_system_menu_pk.nextval,'igw-m-material','99','系统管理','1','','99','0','system',sysdate);
insert into system_menu_info (pk_serial,system_id,menu_id,menu_name,menu_level,menu_url,menu_pre,valid_flag,created_user,created_date)
values (seq_system_menu_pk.nextval,'igw-m-material','9901','基础信息管理','2','/static/views/page/management/baseInfo.html','99','0','system',sysdate);
insert into system_menu_info (pk_serial,system_id,menu_id,menu_name,menu_level,menu_url,menu_pre,valid_flag,created_user,created_date)
values (seq_system_menu_pk.nextval,'igw-m-material','9902','用户权限管理','2','/static/views/page/management/permission.html','99','0','system',sysdate);

-- 系统管理员
insert into user_role_info (pk_serial,account_id,role_id,valid_flag,created_user,created_date)
values (seq_user_role_pk.nextval,(SELECT pk_serial FROM system_user_info where system_id='igw-m-material' and account_id='admin' and valid_flag='0'),(SELECT pk_serial FROM system_role_info where system_id='igw-m-material' and role_id='ADMIN' and valid_flag='0'),'0','system',sysdate);

-- 系统管理（管理员）
insert into menu_role_info (pk_serial,menu_id,role_id,valid_flag,created_user,created_date)
values (seq_menu_role_pk.nextval,(SELECT pk_serial FROM system_menu_info where system_id='igw-m-material' and menu_id='99' and valid_flag='0'),(SELECT pk_serial FROM system_role_info where system_id='igw-m-material' and role_id='ADMIN' and valid_flag='0'),'0','system',sysdate);
insert into menu_role_info (pk_serial,menu_id,role_id,valid_flag,created_user,created_date)
values (seq_menu_role_pk.nextval,(SELECT pk_serial FROM system_menu_info where system_id='igw-m-material' and menu_id='9901' and valid_flag='0'),(SELECT pk_serial FROM system_role_info where system_id='igw-m-material' and role_id='ADMIN' and valid_flag='0'),'0','system',sysdate);
insert into menu_role_info (pk_serial,menu_id,role_id,valid_flag,created_user,created_date)
values (seq_menu_role_pk.nextval,(SELECT pk_serial FROM system_menu_info where system_id='igw-m-material' and menu_id='9902' and valid_flag='0'),(SELECT pk_serial FROM system_role_info where system_id='igw-m-material' and role_id='ADMIN' and valid_flag='0'),'0','system',sysdate);
