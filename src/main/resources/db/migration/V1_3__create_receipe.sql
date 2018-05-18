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

Insert into Catalog values ('2', 'Простые рецепты приготовления рыбы', 'Рыба');
Insert into Receipe values ('2', 'Нежнейший лосось в соусе', true, false, true, 'Лосось с горчично-сливочным соусом', 'losossoys', '2');

Insert into Resources values ('20', 'ingredient', 'г', 'Филе лосося', null, null, 'losos');
Insert into Resources values ('21', 'ingredient', 'ст.л.', 'Оливковое масло', null, null, 'oloil');
Insert into Resources values ('22', 'ingredient', 'ст.л.', 'Лимонный сок', null, null, 'lsok');
Insert into Resources values ('23', 'ingredient', 'г', 'Белый перец', null, null, 'pbel');
Insert into Resources values ('24', 'ingredient', 'ст.л.', 'Горчица', null, null, 'gorch');
Insert into Resources values ('25', 'ingredient', 'мл', 'Сливки', null, null, 'slivki');
Insert into Resources values ('26', 'ingredient', 'г', 'Мускатный орех', null, null, 'orex');
Insert into Resources values ('27', 'ingredient', 'г', 'Перец черный', null, null, 'pepper');
Insert into Resources values ('28', 'resource', 'шт', 'Сотейник', null, null, 'sot');

Insert into ReceipeVersion values ('2', true, false, '2', '2');

Insert into Node  values('20',  'Смешайте в сотейнике сливки, желтки и горчицу. Часто помешивая, доведите до кипения.','Смешайте в сотейнике сливки, желтки и горчицу.', 'preparesoys', '2');
Insert into Node  values('21', 'Просейте муку', 'Просейте муку', 'prosmyka', '2');
Insert into Node  values('22', 'Добавьте соль и специи к муку и добавьте в кипящий соус', 'Добавьте соль и специи к муку и добавьте в кипящий соус', 'sauce', '2');
Insert into Node  values('23', 'Нарежьте филе лосося на стейки бабочки', 'Нарежьте филе лосося', 'lososnarez', '2');
Insert into Node  values('24', 'Натрите солью и белым перцем, сбрызните лимонным соком, оставьте на 15 минут.', 'Натрите  лосось солью и белым перцем, сбрызните лимонным соком', 'lososspecii', '2');
Insert into Node  values('25', 'Смажьте кусочки рыбы оливковым маслом и обжарьте на сковороде-гриль до золотистой корочки.', 'Обжарьте филе до золотистой корочки', 'steyk', '2');
Insert into Node  values('26', 'Нарежьте филе лосося на стейки бабочки', 'Полейте готовые стейки лосося соусом', 'losossoys', '2');

Insert into Resources values ('29', 'ingredient', 'г', 'Просеянная мука', null, null, null);
Insert into Resources values ('30', 'ingredient', 'г', 'Нарезанное филе лосося', null, '23',null);
Insert into Resources values ('31', 'ingredient', 'г', 'Филе со специями', null, '24', null);
Insert into Resources values ('32', 'ingredient', 'г', 'Стейки', null, '25', null);
Insert into Resources values ('33', 'ingredient', 'г', 'Горчичный соус', null, '22', null);

Insert into Edges values ('11', '20', '21');
Insert into Edges values ('12', '21', '22');
Insert into Edges values ('13', '22', '23');
Insert into Edges values ('14', '23', '24');
Insert into Edges values ('15', '24', '25');
Insert into Edges values('16', '25', '26');

Insert into NodeResources values('40', null,  800, '20', null, '2', null);
Insert into NodeResources values('41', null,  1, '21', null, '2', null);
Insert into NodeResources values('42', null,  2, '22', null, '2', null);
Insert into NodeResources values('43', null,  3, '6', null, '2', null);
Insert into NodeResources values('44', null,  3, '23', null, '2', null);
Insert into NodeResources values('45', null,  4, '24', null, '2', null);
Insert into NodeResources values('46', null,  200, '25', null, '2', null);
Insert into NodeResources values('47', null,  3, '3', null, '2', null);
Insert into NodeResources values('48', null,  30, '5', null, '2', null);
Insert into NodeResources values('49', null,  3, '26', null, '2', null);
Insert into NodeResources values('50', null,  3, '27', null, '2', null);
Insert into NodeResources values('51', null,  1, '28', null, '2', null);


Insert into NodeResources values('52', 'input',  200, '25', '20', null, null);
Insert into NodeResources values('53', 'input',  2, '3', '20', null, null);
Insert into NodeResources values('54', 'input',  4, '24', '20', null, null);


Insert into NodeResources values('71', 'input',  30, '5', '21', null, null);
Insert into NodeResources values('55', 'output',  30, '29', '21', null, null);

Insert into NodeResources values('56', 'input',  30, '29', '22', null, null);
Insert into NodeResources values('57', 'input',  3, '6', '22', null, null);
Insert into NodeResources values('58', 'input',  3, '27', '22', null, null);
Insert into NodeResources values('59', 'input',  1, null, '22', null, '20');
Insert into NodeResources values('60', 'output',  1, '33', '22', null, null);

Insert into NodeResources values('61', 'input',  800, '20', '23', null, null);
Insert into NodeResources values('62', 'output',  800, '30', '23', null, null);

Insert into NodeResources values('63', 'input',  800, '30', '24', null, null);
Insert into NodeResources values('64', 'input',  3, '23', '24', null, null);
Insert into NodeResources values('72', 'input',  3, '22', '24', null, null);
Insert into NodeResources values('65', 'output',  800, '31', '24', null, null);

Insert into NodeResources values('66', 'input',  800, '31', '25', null, null);
Insert into NodeResources values('67', 'input',  1, '21', '25', null, null);
Insert into NodeResources values('68', 'output',  800, '32', '25', null, null);

Insert into NodeResources values('69', 'input',  800, '32', '26', null, null);
Insert into NodeResources values('70', 'input',  300, '33', '26', null, null);

