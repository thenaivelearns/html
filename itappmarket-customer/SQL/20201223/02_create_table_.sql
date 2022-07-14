-- 订阅统计表
-- Create table
create table wx_subscription_statistics 
(
  pk_id                  VARCHAR2(50) not null,
  push_type              VARCHAR2(10),
  subscribe_count        VARCHAR2(200),
  unsubscribe_count      VARCHAR2(200),
  valid_flag             VARCHAR2(1)   not null,
  created_user           VARCHAR2(100) not null,
  created_date           DATE          not null,
  updated_user           VARCHAR2(100),
  updated_date           DATE
);

-- Add comments to the table 
comment on table wx_subscription_statistics 
  is '订阅统计表';
-- Add comments to the columns 
comment on column wx_subscription_statistics.pk_id
  is '主键ID';
comment on column wx_subscription_statistics.push_type
  is '订阅类型1.实时交易通知2.确认通知3.基金预约4.发售提醒5.直播提醒6.分红信息'; 
comment on column wx_subscription_statistics.subscribe_count
  is '订阅人数'; 
comment on column wx_subscription_statistics.unsubscribe_count
  is '退订人数';    
comment on column wx_subscription_statistics.valid_flag
  is '有效标记(0:有效 1:无效)';
comment on column wx_subscription_statistics.created_user
  is '创建人';
comment on column wx_subscription_statistics.created_date
  is '创建日期';
comment on column wx_subscription_statistics.updated_user
  is '修改人';
comment on column wx_subscription_statistics.updated_date
  is '修改日期';

-- Create/Recreate primary, unique and foreign key constraints 
alter table wx_subscription_statistics
  add constraint PK_wx_subscription_statistics primary key (pk_id);

create sequence wx_subscription_statistics_PK
minvalue 10000
maxvalue 9999999999999999
start with 10000
increment by 1
nocache;

alter table wx_subscription_statistics add tdate VARCHAR2(20); 
comment on column wx_subscription_statistics.tdate is '日期';