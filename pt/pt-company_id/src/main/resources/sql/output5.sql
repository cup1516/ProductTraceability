INSERT INTO SYSTEM_USER (user_id, company_id, user_name, PASSWORD)
VALUES ('77','c90e5d284ffd401c9e4f001a8c0fa42a','admin', '$2a$10$pP1UR.QSQL18ZSJiYhJI5OXZW1rZsGGUHSjqzg73HYx1uSOpAyGt2');
INSERT INTO SYSTEM_ROLE (role_id, company_id, role_name, role_code, role_sort)
VALUES ('77','c90e5d284ffd401c9e4f001a8c0fa42a','管理员','ADMIN','0');
INSERT INTO SYSTEM_USER_ROLE (user_id, role_id, company_id)
VALUES ('77', '77', 'c90e5d284ffd401c9e4f001a8c0fa42a');
INSERT INTO SYSTEM_POST (post_id, company_id, post_code, post_name, post_sort, status)
VALUES ('77', 'c90e5d284ffd401c9e4f001a8c0fa42a', 'ceo', '董事长', '1', '0');
INSERT INTO SYSTEM_USER_POST (user_id, post_id, company_id)
VALUES ('77', '77', 'c90e5d284ffd401c9e4f001a8c0fa42a');
INSERT INTO SYSTEM_MENU (menu_id, company_id, menu_name)
VALUES('77', 'c90e5d284ffd401c9e4f001a8c0fa42a', '系统管理');
INSERT INTO SYSTEM_ROLE_MENU (role_id, company_id, menu_id)
VALUES('77', 'c90e5d284ffd401c9e4f001a8c0fa42a', '77');
INSERT INTO SYSTEM_DEPT (dept_id, company_id)
VALUES('77', 'c90e5d284ffd401c9e4f001a8c0fa42a');
