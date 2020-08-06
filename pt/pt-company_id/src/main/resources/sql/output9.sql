INSERT INTO SYSTEM_USER (user_id, company_id, user_name, PASSWORD)
VALUES ('99','4f010c739ae4456cb3879502c4af10c4','admin', '$2a$10$hb5Cn0gsQB4RsTe8LjeyGON29/6JDcH8YtJ/yuU803B/u1.AvRCIe');
INSERT INTO SYSTEM_ROLE (role_id, company_id, role_name, role_code, role_sort)
VALUES ('99','4f010c739ae4456cb3879502c4af10c4','管理员','ADMIN','0');
INSERT INTO SYSTEM_USER_ROLE (user_id, role_id, company_id)
VALUES ('99', '99', '4f010c739ae4456cb3879502c4af10c4');
INSERT INTO SYSTEM_POST (post_id, company_id, post_code, post_name, post_sort, status)
VALUES ('99', '4f010c739ae4456cb3879502c4af10c4', 'ceo', '董事长', '1', '0');
INSERT INTO SYSTEM_USER_POST (user_id, post_id, company_id)
VALUES ('99', '99', '4f010c739ae4456cb3879502c4af10c4');
INSERT INTO SYSTEM_MENU (menu_id, company_id, menu_name)
VALUES('99', '4f010c739ae4456cb3879502c4af10c4', '系统管理');
INSERT INTO SYSTEM_ROLE_MENU (role_id, company_id, menu_id)
select '99' as 'role_id', '4f010c739ae4456cb3879502c4af10c4' as 'company_id', menu_id from system_menu where menu_type = 'C';
INSERT INTO SYSTEM_DEPT (dept_id, company_id)
VALUES('99', '4f010c739ae4456cb3879502c4af10c4');
