INSERT INTO SYSTEM_USER (user_id, company_id, user_name, PASSWORD)
VALUES ('88','b4c91ad7147a491c87766278176237b7','admin', '$2a$10$o09E0DsmFyRFWc8lUtab3eFc/uf2zz9QjUx/eal.Aa1gWyu06cJ0i');
INSERT INTO SYSTEM_ROLE (role_id, company_id, role_name, role_code, role_sort)
VALUES ('88','b4c91ad7147a491c87766278176237b7','管理员','ADMIN','0');
INSERT INTO SYSTEM_USER_ROLE (user_id, role_id, company_id)
VALUES ('88', '88', 'b4c91ad7147a491c87766278176237b7');
INSERT INTO SYSTEM_POST (post_id, company_id, post_code, post_name, post_sort, status)
VALUES ('88', 'b4c91ad7147a491c87766278176237b7', 'ceo', '董事长', '1', '0');
INSERT INTO SYSTEM_USER_POST (user_id, post_id, company_id)
VALUES ('88', '88', 'b4c91ad7147a491c87766278176237b7');
INSERT INTO SYSTEM_MENU (menu_id, company_id, menu_name)
VALUES('88', 'b4c91ad7147a491c87766278176237b7', '系统管理');
INSERT INTO SYSTEM_ROLE_MENU (role_id, company_id, menu_id)
VALUES('88', 'b4c91ad7147a491c87766278176237b7', '88');
INSERT INTO SYSTEM_DEPT (dept_id, company_id)
VALUES('88', 'b4c91ad7147a491c87766278176237b7');
