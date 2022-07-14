-- 委托人菜单
insert into system_menu_info (pk_serial,system_id,menu_id,menu_name,menu_level,menu_url,menu_pre,valid_flag,created_user,created_date)
values (seq_system_menu_pk.nextval,'ItAppMarket-customer','77','委托人管理','1','/static/views/page/market/home.html','77','0','system',sysdate);

-- 委托人序列
create sequence market_zh_info_PK
minvalue 10000
maxvalue 9999999999999999
start with 10000
increment by 1
nocache;

alter table market_zh_info add report_name VARCHAR2(200); 
comment on column market_zh_info.report_name is '导出文件名称';

alter table market_zh_info add email VARCHAR2(200); 
comment on column market_zh_info.email is '邮箱';