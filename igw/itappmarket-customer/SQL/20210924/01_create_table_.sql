-- 微信投资黄历
-- Create table
create table wx_investment_almanac
(
  pk_id                  VARCHAR2(50) not null,
  information_date       VARCHAR2(10),
  information_time       VARCHAR2(10),
  title                  VARCHAR2(100),
  content                VARCHAR2(2048),
  fundCode               VARCHAR2(100),
  fundName               VARCHAR2(100),
  noticeCode             VARCHAR2(100),
  noticeName             VARCHAR2(100),
  valid_flag             VARCHAR2(1)   not null,
  created_user           VARCHAR2(100) not null,
  created_date           DATE          not null,
  updated_user           VARCHAR2(100),
  updated_date           DATE
);

-- Add comments to the table 
comment on table wx_investment_almanac
  is '微信投资黄历';
-- Add comments to the columns 
comment on column wx_investment_almanac.pk_id
  is '主键ID';
comment on column wx_investment_almanac.information_date
  is '公告日期 yyyyMMdd';
comment on column wx_investment_almanac.information_time
  is '公告时间 hh:mm';
comment on column wx_investment_almanac.title
  is '标题';
comment on column wx_investment_almanac.content
  is '内容';
comment on column wx_investment_almanac.fundCode
  is '基金编码';
comment on column wx_investment_almanac.fundName
  is '基金名称';
comment on column wx_investment_almanac.noticeCode
  is '公告类别编码';
comment on column wx_investment_almanac.noticeName
  is '公告类别名称';
comment on column wx_investment_almanac.valid_flag
  is '有效标记(0:有效 1:无效)';
comment on column wx_investment_almanac.created_user
  is '创建人';
comment on column wx_investment_almanac.created_date
  is '创建日期';
comment on column wx_investment_almanac.updated_user
  is '修改人';
comment on column wx_investment_almanac.updated_date
  is '修改日期';

-- Create/Recreate primary, unique and foreign key constraints 
alter table wx_investment_almanac
  add constraint PK_wx_investment_almanac primary key (pk_id);

create sequence wx_investment_almanac_PK
minvalue 10000
maxvalue 9999999999999999
start with 10000
increment by 1
nocache;