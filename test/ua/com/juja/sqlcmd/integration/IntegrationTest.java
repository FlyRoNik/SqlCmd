package ua.com.juja.sqlcmd.integration;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * Created by FlyRoNik on 21.04.2016.
 */
public class IntegrationTest {

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @Before
    public void setup(){
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testHelp(){
        //given
        in.add("help");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //help
                "Существующие команды:\r\n" +
                "\tconnect|databaseName|userName|password\r\n" +
                "\t\tдля подключения к базе данных, с которой будем работать\r\n" +
                "\tlist\r\n" +
                "\t\tдля получения списка всех таблиц базы, к которой подключились\r\n" +
                "\tclear|tableName\r\n" +
                "\t\tдля очистки всей таблицы\r\n" +
                "\tcreate|tableName|column1|value1|column2|value2|...|columnN|valueN|\r\n" +
                "\t\tдля создания записи в таблице таблице\r\n" +
                "\tfind|tableName\r\n" +
                "\t\tдля получения содержимого таблицы 'tableName'\r\n" +
                "\thelp\r\n" +
                "\t\tдля вывода этого списка на экран\r\n" +
                "\texit\r\n" +
                "\t\tдля выхода из программы\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
     public void testExit() {
        //given
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testListWithoutConnect() {
        //given
        in.add("list");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //list
                "Вы не можете пользоватся командой 'list' пока не подключитесь с помощью " +
                "комманды connect|databaseName|userName|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testFindWithoutConnect() {
        //given
        in.add("find|people");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //find|people
                "Вы не можете пользоватся командой 'find|people' пока не подключитесь с помощью " +
                "комманды connect|databaseName|userName|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testUnsupported() {
        //given
        in.add("unsupported");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //unsupported
                "Вы не можете пользоватся командой 'unsupported' пока не подключитесь с помощью " +
                "комманды connect|databaseName|userName|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
     public void testUnsupportedAfterConnect() {
        //given
        in.add("connect|mysqlcmd|nikita|1234");
        in.add("unsupported");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //connect|mysqlcmd|nikita|1234
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //unsupported
                "Несуществующая команда: unsupported\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testListAfterConnect() {
        //given
        in.add("connect|mysqlcmd|nikita|1234");
        in.add("list");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //connect|mysqlcmd|nikita|1234
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //list
                "[people, test2, test]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testFindAfterConnect() {
        //given
        in.add("connect|mysqlcmd|nikita|1234");
        in.add("find|people");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //connect|mysqlcmd|nikita|1234
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //find|people
                "--------------------\r\n" +
                "|id|name|surname|age|\r\n" +
                "--------------------\r\n" +
                "|1|Nikita|Frolov|20|\r\n" +
                "|2|Julia|Norkina|19|\r\n" +
                "|3|ddas|null|2|\r\n" +
                "|4|null|dasd|null|\r\n" +
                "--------------------\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testConnectAfterConnect() {
        //given
        in.add("connect|mysqlcmd|nikita|1234");
        in.add("list");
        in.add("connect|mysqlcmd|nikita|1234");
        in.add("list");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //connect|mysqlcmd|nikita|1234
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //list
                "[people, test2, test]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //connect|mysqlcmd|nikita|1234
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //list
                "[people, test2, test]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testConnectWithError() {
        //given
        in.add("connect|mysqlcmd");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //connect|mysqlcmd
                "Неудача! по причине: Неверно количество параметров " +
                "разделенных знаком '|', ожидается 4, но есть: 2\r\n" +
                "Повтори попытку!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testFindAfterConnect_whitData() {
        //given
        in.add("connect|mysqlcmd|nikita|1234");
        in.add("clear|test");
        in.add("create|test|id|23|name|Jim|surname|Pupkin|age|23");
        in.add("create|test|id|20|name|Eva|surname|Mali|age|21");
        in.add("find|test");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //connect|mysqlcmd|nikita|1234
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //clear|test
                "Таблица test была успешно очищена.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //create|test|id|23|name|Jim|surname|Pupkin|age|23
                "Запись {names:[id, name, surname, age], values:[23, Jim, Pupkin, 23]} " +
                "была успешно созданна в таблице 'test'.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //create|test|id|20|name|Eva|surname|Mali|age|21
                "Запись {names:[id, name, surname, age], values:[20, Eva, Mali, 21]} " +
                "была успешно созданна в таблице 'test'.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //find|test
                "--------------------\r\n" +
                "|id|name|surname|age|\r\n" +
                "--------------------\r\n" +
                "|23|Jim|Pupkin|23|\r\n" +
                "|20|Eva|Mali|21|\r\n" +
                "--------------------\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testClearWithError() {
        //given
        in.add("connect|mysqlcmd|nikita|1234");
        in.add("clear|asdas|gfhn");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //connect|mysqlcmd|nikita|1234
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //clear|asdas|gfhn
                "Неудача! по причине: Формат команды 'clear|tableName', а ты ввел: clear|asdas|gfhn\r\n" +
                "Повтори попытку!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testCreateWithErrors() {
        //given
        in.add("connect|mysqlcmd|nikita|1234");
        in.add("create|tasdest|isadd|");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //connect|mysqlcmd|nikita|1234
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //create|tasdest|isadd|
                "Неудача! по причине: Должно быть четное количество параметров в формате " +
                "'create|tableName|column1|value1|column2|value2|...|columnN|valueN|' , " +
                "а ты преслал: 'create|tasdest|isadd|'\r\n" +
                "Повтори попытку!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void testFindWithErrorAfterConnect() {
        //given
        in.add("connect|mysqlcmd|nikita|1234");
        in.add("find|nonexistst");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет юзер!\r\n" +
                "Введите, пожалуйста имя базы данных, имя пользователя и пароль в формате:" +
                " connect|database|userName|password\r\n" +
                //connect|mysqlcmd|nikita|1234
                "Успех!\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //unsupported
                "[people, test, test2]\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                //exit
                "До скорой встречи!\r\n", getData());
    }
}
