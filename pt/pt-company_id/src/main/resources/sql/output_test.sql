INSERT INTO SYSTEM_USER (user_id, company_id, user_name, PASSWORD)
VALUES ('1','23fff09668fe41edacd9db7c558b757a','admin', '$2a$10$IURQUl375ziYBdbu6Hwb2..g3FzLS6lTCsCzFiFlrf8aG/s7xfc12');
INSERT INTO SYSTEM_ROLE (role_id, company_id, role_name, role_code, role_sort)
VALUES ('1','23fff09668fe41edacd9db7c558b757a','管理员','ADMIN','0');
INSERT INTO SYSTEM_USER_ROLE (user_id, role_id, company_id)
VALUES ('1', '1', '23fff09668fe41edacd9db7c558b757a');
INSERT INTO SYSTEM_POST (post_id, company_id, post_code, post_name, post_sort, status)
VALUES ('1', '23fff09668fe41edacd9db7c558b757a', 'ceo', '董事长', '0', '0');
INSERT INTO SYSTEM_USER_POST (user_id, post_id, company_id)
VALUES ('1', '1', '23fff09668fe41edacd9db7c558b757a');
INSERT INTO SYSTEM_ROLE_MENU (role_id, company_id, menu_id)
select '1' as 'role_id', '23fff09668fe41edacd9db7c558b757a' as 'company_id', menu_id from system_menu where type = '0' and client_type != '8';
INSERT INTO SYSTEM_DEPT (dept_id, company_id)
VALUES('1', '23fff09668fe41edacd9db7c558b757a');
