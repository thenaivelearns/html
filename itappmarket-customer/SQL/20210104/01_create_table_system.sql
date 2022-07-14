-- 年度账单
insert into system_menu_info (pk_serial,system_id,menu_id,menu_name,menu_level,menu_url,menu_pre,valid_flag,created_user,created_date)
values (seq_system_menu_pk.nextval,'ItAppMarket-customer','88','微信用户管理','1','','88','0','system',sysdate);
insert into system_menu_info (pk_serial,system_id,menu_id,menu_name,menu_level,menu_url,menu_pre,valid_flag,created_user,created_date)
values (seq_system_menu_pk.nextval,'ItAppMarket-customer','8801','用户年度账单','2','/static/views/page/annual/bill.html','88','0','system',sysdate);
