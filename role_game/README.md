## Пошаговая стратегия - "Герои".
Задание взято на сайте [job4j.ru](https://job4j.ru/posts/heroes.html)

Создать прототип игры-консольного приложения на языке Java. При выполнении задания пользоваться системами контроля версий GitHub.

В игре создается отряд определенной расы, состоящий из одного мага, трех лучников и четырех бойцов.
Предусмотрено четыре расы: эльфы, люди, орки, нежить. Эльфы и люди играют против орков и нежити.

В начале игры случайным образом создаются два враждующих отряда той или иной расы.
Все персонажи отряда делятся на две группы: обычные и привилегированные (с улучшенными показателями). Персонаж при наложении на него улучшения перемещается в привилегированную группу. Величина наносимого урона для персонажей привилегированной группы увеличивается в полтора раза. 

Порядок ходов для рас определяется случайным образом. За один ход каждый персонаж отряда может выполнить действие (действие определяется случайным образом): сначала из привилегированной группы, а если она пуста, тогда из общего списка персонажей отряда рандомно. Персонаж из привилегированной группы после выполнения одного действия перемещается в общую группу. 

Возможности персонажей:

<div>Раса эльфов:
    <div>маг:
        <div>наложение улучшения на персонажа своего отряда</div>
        <div>нанесение урона персонажу противника магией на 10 HP</div>
    </div>
    <div>лучник:
        <div>стрелять из лука (нанесение урона 7 HP)</div>
        <div>атаковать противника (нанесение урона 3 HP)</div>
    </div>
    <div>воин:
        <div>атаковать мечом (нанесение урона 15 HP)</div>
    </div>
</div>
<div>Раса людей:
    <div>маг:
        <div>наложение улучшения на персонажа своего отряда.</div>
        <div>атаковать магией (нанесение урона 4 HP)</div>
    </div>
    <div>арбалетчик:
        <div>стрелять из арбалета (нанесение урона 5 HP)</div>
        <div>атаковать (нанесение урона 3 HP)</div>
    </div>
    <div>воин:
        <div>атаковать мечом (нанесение урона 18 HP)</div>
    </div>
</div>
<div>Раса орков:
    <div>шаман:
        <div>наложение улучшения на персонажа своего отряда.</div>
        <div>наложение проклятия (снятие улучшения с персонажа противника для следующего хода)</div>
    </div>
    <div>лучник:
        <div>стрелять из лука (нанесение урона 3 HP)</div>
        <div>удар клинком (нанесение урона 2 HP)</div>
    </div>
    <div>гоблин:
        <div>атака дубиной (нанесение урона 20 HP)</div>
    </div>
</div>
<div>Раса нежити:
    <div>некромант:
        <div>наслать недуг (уменьшение силы урона персонажа противника на 50% на один ход)</div>
        <div>атака (нанесение урона 5 HP)</div>
    </div>
    <div>охотник:
        <div>стрелять из лука (нанесение урона 4 HP)</div>
        <div>атаковать (нанесение урона 2 HP)</div>
    </div>
    <div>зомби:
        <div>удар копьем (нанесение урона 18 HP)</div>
    </div>
</div>

С начала игры каждый персонаж имеет уровень жизни равный 100 HP.
Выводить ход игры в консоль: вести статистику ходов с порядком ходов (кто, кого, нанесенный урон, умер). По завершении игры сохранять лог в файл.

Сборка maven.

Должны быть junit тесты и проверка checkstyle плагином.