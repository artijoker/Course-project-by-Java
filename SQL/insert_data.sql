use corporateblog;

insert into roles
values (null, 'ROLE_ADMIN'),
       (null, 'ROLE_USER');

insert into accounts
value (null, 'eupftxzounoehg@arxxwalls.com', 0, 0, 'admin',
       '$2a$10$8BwFCm86EsgAzSJiDT8tV.jLRjL8gtOIW6z5KtIyadKmgkhJZqW4O', #admin
      '2022-11-15 12:02:00', 1),
      (null, 'joker@gmail.com', 0, 0, 'joker',
       '$2a$10$6tYn/Gr/cqBPLMd17ZEUzeJGlBvx0zd2u/c00RgThc0McwyTvirBW', #joker
      '2022-11-15 12:02:00', 2);

insert into categories
values (null, 'Общее'),
       (null, 'Технологии'),
       (null, 'Разработка'),
       (null, 'Дизайн'),
       (null, 'Администрирование'),
       (null, 'Маркетинг'),
       (null, 'Научпоп');

insert into post_status
values (null, 'Draft'),
       (null, 'Pending'),
       (null, 'Publish'),
       (null, 'Rejected');

