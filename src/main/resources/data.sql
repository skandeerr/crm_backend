INSERT IGNORE INTO `t_role`(`id`, `creation_date`, `update_date`, `name`,`feature`) VALUES
(1, NOW(), NULL, 'add_prospect','prospect'),
(2, NOW(), NULL, 'edit_prospect','prospect'),
(3, NOW(), NULL, 'delete_prospect','prospect'),
(4, NOW(), NULL, 'call_prospect','prospect'),
(5, NOW(), NULL, 'send_mail_prospect','prospect'),
(6, NOW(), NULL, 'add_prospect_client','prospect'),
(7, NOW(), NULL, 'add_contact','contact'),
(8, NOW(), NULL, 'edit_contact','contact'),
(9, NOW(), NULL, 'delete_contact','contact'),
(10, NOW(), NULL, 'call_contact','contact'),
(11, NOW(), NULL, 'send_mail_contact','contact'),
(12, NOW(), NULL, 'add_task','task'),
(13, NOW(), NULL, 'edit_task','task'),
(14, NOW(), NULL, 'delete_task','task');


INSERT IGNORE INTO `t_permission`(`id`, `name`) VALUES
(1, 'Responsable Commercial'),
(2, 'Responsable Marketing');
