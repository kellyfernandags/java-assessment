INSERT INTO ROLE(name) VALUES('Developer');
INSERT INTO ROLE(name) VALUES('Product Owner');
INSERT INTO ROLE(name) VALUES('Tester');

INSERT INTO USER(id,display_name,role_id) VALUES('52ebd64f-d017-4eba-b382-9e3c44dc87c5','arturoLarkin',1);
INSERT INTO USER(id,display_name,role_id) VALUES('62e63266-f8fd-40f0-8424-783b934223fa','emmettFrami',2);
INSERT INTO USER(id,display_name,role_id) VALUES('a8c0837d-e667-4930-9838-49f9295beec9','jadynFriesen',3);
INSERT INTO USER(id,display_name,role_id) VALUES('c52590d1-24e7-4b99-8c0e-7ec1437d39dd','marquisBergstrom',1);
INSERT INTO USER(id,display_name,role_id) VALUES('f696a605-41c9-43a3-a7be-9e818483dfe4','carmellaKemmer',1);

INSERT INTO TEAM(id,name) VALUES('cf1edf6a-62cb-454d-b943-97973032d851','Deafening Aqua Harrier');
INSERT INTO TEAM(id,name) VALUES('53d153d3-b005-4e4a-a33e-99b4169e1bff','Patient Lavender Lamprey');

INSERT INTO MEMBERSHIP(user_id,team_id) VALUES('52ebd64f-d017-4eba-b382-9e3c44dc87c5','cf1edf6a-62cb-454d-b943-97973032d851');
INSERT INTO MEMBERSHIP(user_id,team_id) VALUES('62e63266-f8fd-40f0-8424-783b934223fa','cf1edf6a-62cb-454d-b943-97973032d851');
INSERT INTO MEMBERSHIP(user_id,team_id) VALUES('c52590d1-24e7-4b99-8c0e-7ec1437d39dd','cf1edf6a-62cb-454d-b943-97973032d851');
INSERT INTO MEMBERSHIP(user_id,team_id) VALUES('f696a605-41c9-43a3-a7be-9e818483dfe4','cf1edf6a-62cb-454d-b943-97973032d851');
INSERT INTO MEMBERSHIP(user_id,team_id) VALUES('f696a605-41c9-43a3-a7be-9e818483dfe4','53d153d3-b005-4e4a-a33e-99b4169e1bff');