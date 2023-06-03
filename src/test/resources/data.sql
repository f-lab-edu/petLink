insert into manager
    (email, password, name, phone_number, office_number)
values ('manager@gmail.com', 'password0123', '대표매니저', '010-1234-5678', '010-1234-5678');
values ('rlagusrl928@gmail.com', 'password0123', '매니저', '010-1234-5678', '010-1234-5678');

INSERT INTO funding
(manager_id, title, mini_title, content, state, category, start_date, end_date, target_donation, success_donation,
 created_by, last_modified_by)
VALUES (1, '최고의 고양이 사료 제작', '우리 고양이에게 최고의 사료를!', '우리의 프로젝트는 최고의 고양이 사료를 제작하는 것입니다. 모든 고양이가 건강하고 행복하게 사는 것이 우리의 목표입니다.',
        'PROGRESS', 'FOOD', '2023-01-01 00:00:00', '2023-06-30 23:59:59', 10000000, 8000000, 'manager1', 'manager1'),
       (1, '펫을 위한 영양제 개발', '펫의 건강을 책임지는 영양제', '우리의 프로젝트는 애완동물의 건강을 위해 필요한 영양소를 공급하는 영양제를 개발하는 것입니다.', 'PROGRESS',
        'NUTRIENTS', '2023-02-01 00:00:00', '2023-07-31 23:59:59', 5000000, 4000000, 'manager2', 'manager2'),
       (1, '재미있는 개용 장난감 제작', '개를 위한 재미있는 장난감',
        '우리의 프로젝트는 개의 행복을 위한 재미있는 장난감을 제작하는 것입니다. 모든 개가 즐거움을 느끼며 생활하는 것이 우리의 목표입니다.', 'END', 'TOY',
        '2023-03-01 00:00:00', '2023-08-31 23:59:59', 3000000, 2400000, 'manager3', 'manager3'),
       (1, '펫을 위한 편안한 옷 제작', '펫의 편안함을 위한 옷', '우리의 프로젝트는 애완동물의 편안함을 위한 옷을 제작하는 것입니다. 모든 애완동물이 편안하게 생활하는 것이 우리의 목표입니다.',
        'CANCEL', 'CLOTHES', '2023-04-01 00:00:00', '2023-09-30 23:59:59', 2000000, 1600000, 'manager4', 'manager4');
