alter table weave.user add column kakao_id varchar(255);
alter table weave.user add unique (kakao_id);
