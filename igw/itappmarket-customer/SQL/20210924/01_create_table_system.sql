-- 公告日历信息
insert into system_menu_info (pk_serial,system_id,menu_id,menu_name,menu_level,menu_url,menu_pre,valid_flag,created_user,created_date)
values (seq_system_menu_pk.nextval,'ItAppMarket-customer','66','公告日历信息','1','/static/views/page/almanac/almanac.html','66','0','system',sysdate);

insert into menu_role_info (pk_serial,menu_id,role_id,valid_flag,created_user,created_date)
values (seq_menu_role_pk.nextval,(SELECT pk_serial FROM system_menu_info where system_id='ItAppMarket-customer' and menu_id='66' and valid_flag='0'),(SELECT pk_serial FROM system_role_info where system_id='ItAppMarket-customer' and role_id='ADMIN' and valid_flag='0'),'0','system',sysdate);
