package App;
/**
 * Created by HOME on 04.12.2020.
 */
import org.junit.*;

public class UtilTest {
    @Test
    public void testisEmpty() {
        Assert.assertTrue(Util.isEmpty("","","0"));
        Assert.assertTrue(Util.isEmpty("1","","0"));
        Assert.assertTrue(Util.isEmpty("1","1","0"));
    }
    /** Данный тест проверяет метод isEmpty на корректную обработку непустых зна-чений ( возвращает «ложь» при получении строки, содержащей хотя бы один символ.*/			@Test
    public void testNonisEmpty() {
        Assert.assertFalse(Util.isEmpty("s","s","3"));
    }
    @Test
    public void testmakFileName() {
        Assert.assertEquals("test.txt", Util.makeFileName("test",".txt"));
        Assert.assertEquals("t.txt", Util.makeFileName("t",".txt"));
        Assert.assertEquals("test.txt", Util.makeFileName("test.txt",".txt"));
        Assert.assertNotEquals("test.txt.txt", Util.makeFileName("test.txt",".txt"));
    }
    @Test(expected = RuntimeException.class) // Проверяем на появление исключения
    public void testConnect() {
        Util.connect("1");
    }
    @BeforeClass // Фиксируем начало тестирования
    public static void allTestsStarted() {
        System.out.println("Начало тестирования");
    }
    @AfterClass // Фиксируем конец тестирования
    public static void allTestsFinished() {
        System.out.println("Конец тестирования");
    }
    @Before // Фиксируем запуск теста
    public void testStarted() {
        System.out.println("Запуск теста");
    }
    @After // Фиксируем завершение теста
    public void testFinished() {
        System.out.println("Завершение теста");
    }
}

