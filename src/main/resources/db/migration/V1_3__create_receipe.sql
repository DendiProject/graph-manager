Insert into Catalog values ('1', 'Самые вкусные пироги', 'Пироги');
Insert into Receipe values ('1', 'Вкуснейший яблочный пирог. Традиционная шарлотка.', true, false, true, 'Яблочный пирог', 'applePiePictureId', '1');

Insert into Resources values ('1', 'ingredient', 'шт', 'Яйцо', null, null, 'egg');
Insert into Resources values ('2', 'ingredient', 'шт', 'Белки', null, null, 'belokId');
Insert into Resources values ('3', 'ingredient', 'шт', 'Желтки', null, null, 'egg2');
Insert into Resources values  ('4', 'ingredient', 'г', 'Сахар', null, null, 'shugar');
Insert into Resources values ('5', 'ingredient', 'г', 'Мука', null, null, 'myka');
Insert into Resources values ('6', 'ingredient', 'г', 'Соль', null, null, 'salt');
Insert into Resources values ('7', 'ingredient', 'шт', 'Яблоки', null, null, 'apple');
Insert into Resources values ('8', 'resource', 'шт', 'Духовка', null, null, 'dyhovka');
Insert into Resources values ('9', 'resource', 'шт', 'Форма для выпечки', null, null, 'forma');

Insert into ReceipeVersion values ('1', true, false, '1', '1');

Insert into Node  values('1', 'Белки отделить от желтков', 'Белки отделить от желтков', 'nodepictureId1', '1');
Insert into Node values('2', 'Белки смешать с половиной сахара', 'Белки смешать с половиной сахара', 'nodepictureId2', '1');
Insert into Node values ('3', 'Желтки смешать с оставшимся сахаром', 'Желтки смешать с оставшимся сахаром', 'nodepictureId3', '1');
Insert into Node values('4', 'Все смешать и добавить муку', 'Все смешать и добавить муку', 'nodepictureId4', '1');
Insert into Node values('5', 'К предыдущему шагу добавить соль', 'К предыдущему шагу добавить соль', 'nodepictureId5', '1');
Insert into Node values('6', 'Нарезать яблоки', 'Нарезать яблоки', 'nodepictureId6', '1');
Insert into Node values('7', 'Смешать тесто с яблоками', 'Смешать тесто с яблоками', 'nodepictureId7', '1');
Insert into Node values('8', 'Разогреть духовку', 'Разогреть духовку', 'nodepictureId8', '1');
Insert into Node values('9', 'Тесто залить в форму для выпечки', 'Тесто залить в форму для выпечки', 'nodepictureId9', '1');
Insert into Node values('10', 'Выпекать 40 минут', 'Выпекать 40 минут', 'nodepictureId10', '1');
Insert into Node values('11', 'Готово!', 'Готово!', 'nodepictureId11', '1');

Insert into Resources values ('10', 'ingredient', 'шт', 'Белки с сахаром', null, '2', null);
Insert into Resources values ('11', 'ingredient', 'шт', 'Желтки с сахаром', null, '3', null);
Insert into Resources values ('13', 'ingredient', 'шт', 'Тесто', null, '4', null);
Insert into Resources values('14', 'ingredient', 'шт', 'Нарезанные яблоки', null, '6', null);
Insert into Resources values ('15', 'resource', 'шт', 'Разогретая духовка', null, '8', null);
Insert into Resources values ('16', 'ingredient', 'шт', 'Тесто с солью', null, '5', null);
Insert into Resources values ('17', 'ingredient', 'шт', 'Тесто с яблоками', null, '7', null);
Insert into Resources values ('18', 'ingredient', 'шт', 'Тесто в форме для выпечки', null, '9', null);
Insert into Resources  values ('19', 'ingredient', 'шт', 'Пирог', null, '10', null);

Insert into Edges values ('1', '1', '2');
Insert into Edges values ('2', '2', '3');
Insert into Edges values ('3', '3', '4');
Insert into Edges values ('4', '4', '5');
Insert into Edges values ('5', '5', '6');
Insert into Edges values('6', '6', '7');
Insert into Edges values('7', '7', '8');
Insert into Edges values('8', '8', '9');
Insert into Edges values('9', '9', '10');
Insert into Edges values('10', '10', '11');


Insert into NodeResources values('1', 'input',  3, '1', '1', null, null);
Insert into NodeResources values('2', 'output', 3, '2', '1', null, null);
Insert into NodeResources values('3', 'output', 3, '3', '1', null, null);

Insert into NodeResources values('4', 'input',  3, '2', '2', null, null);
Insert into NodeResources values('5', 'input', 150, '4', '2', null, null);
Insert into NodeResources values('6', 'output', 1, '10', '2', null, null);

Insert into NodeResources values('7', 'input',  3, '3', '3', null, null);
Insert into NodeResources values('8', 'intput', 150, '4', '3', null, null);
Insert into NodeResources values('9', 'output', 1, '11', '3', null, null);

Insert into NodeResources values ('10', 'input',  1, null, '4', null,'3');
Insert into NodeResources values('11', 'input', 1, null, '4', null, '2');
Insert into NodeResources values('12', 'output', 1, '13', '4', null, null);

Insert into NodeResources values('13', 'input',  1, null, '5', null, '4');
Insert into NodeResources values('14', 'output', 1, '16', '5', null, null);

Insert into NodeResources values('15', 'input',  5, '7', '6', null, null);
Insert into NodeResources values('16', 'output', 5, '14', '6', null, null);

Insert into NodeResources values('17', 'input',  1, null, '7', null, '6');
Insert into NodeResources values('18', 'input',  1, null, '7', null, '5');
Insert into NodeResources values('19', 'output', 1, '17', '7', null, null);

Insert into NodeResources values('20', 'input',  1, '8', '8', null, null);
Insert into NodeResources values('21', 'output', 1, '15', '8', null, null);

Insert into NodeResources values('22', 'input',  1, null, '9', null, '7');
Insert into NodeResources values('23', 'output', 1, '18', '9', null, null);

Insert into NodeResources values('24', 'input',  1, null, '10', null, '9');
Insert into NodeResources values('25', 'input',  1, null, '10', null, '8');
Insert into NodeResources values('26', 'output', 1, '19', '10', null, null);

Insert into NodeResources values('27', 'input',  1, null, '11', null, '10');

Insert into NodeResources values('28', 'input',  3, '1', '1', null, null);
Insert into NodeResources values('29', 'output', 3, '2', '1', null, null);
Insert into NodeResources values('30', 'output', 3, '3', '1', null, null);

Insert into NodeResources values('34', null,  3, '1', null, '1', null);
Insert into NodeResources values('35', null,  300, '5', null, '1', null);
Insert into NodeResources values('36', null,  300, '4', null, '1', null);
Insert into NodeResources values('37', null,  1,  '6', null, '1', null);
Insert into NodeResources values('31', null,  1, '8', null, '1', null);
Insert into NodeResources values('33', null,  5, '7', null, '1', null);
Insert into NodeResources values('32', null,  1, '9', null, '1', null);