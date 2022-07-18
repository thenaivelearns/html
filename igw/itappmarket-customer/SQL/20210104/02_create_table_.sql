-- 微信年度账单
-- Create table
create table wx_annual_bill 
(
  pk_id                  VARCHAR2(50) not null,
  custno                 VARCHAR2(100),
  tDate                 VARCHAR2(10),
  cstate                 VARCHAR2(10),
  bill                   CLOB,
  msgdesc                VARCHAR2(2000),
  valid_flag             VARCHAR2(1)   not null,
  created_user           VARCHAR2(100) not null,
  created_date           DATE          not null,
  updated_user           VARCHAR2(100),
  updated_date           DATE
);

-- Add comments to the table 
comment on table wx_annual_bill 
  is '微信年度账单';
-- Add comments to the columns 
comment on column wx_annual_bill.pk_id
  is '主键ID';
comment on column wx_annual_bill.custno
  is '身份证';
comment on column wx_annual_bill.tDate
  is '年份 YYYY';  
comment on column wx_annual_bill.cstate
  is '状态 Y 成功 N 失败'; 
comment on column wx_annual_bill.bill
  is '账单信息';   
comment on column wx_annual_bill.msgdesc
  is '描述';   
comment on column wx_annual_bill.valid_flag
  is '有效标记(0:有效 1:无效)';
comment on column wx_annual_bill.created_user
  is '创建人';
comment on column wx_annual_bill.created_date
  is '创建日期';
comment on column wx_annual_bill.updated_user
  is '修改人';
comment on column wx_annual_bill.updated_date
  is '修改日期';

-- Create/Recreate primary, unique and foreign key constraints 
alter table wx_annual_bill
  add constraint PK_wx_annual_bill primary key (pk_id);

create sequence wx_annual_bill_PK
minvalue 10000
maxvalue 9999999999999999
start with 10000
increment by 1
nocache;