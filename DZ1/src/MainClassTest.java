import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetLocalNumber()
    {
        MainClass mainClass = new MainClass();
        Assert.assertEquals("Values are not equals." ,14, mainClass.getLocalNumber());
    }

    @Test
    public void testGetClassNumber()
    {
        MainClass mainClass1 = new MainClass();
        Assert.assertTrue("Value is not greater that 45.", mainClass1.getClassNumber() > 45);
    }

    @Test
    public void testGetClassString()
    {
        MainClass mainClass2 = new MainClass();
        String str = mainClass2.getClassString();

        boolean contains = str.contains ("hello");
        boolean contains2 = str.contains ("Hello");
        Assert.assertTrue("Test is not contains 'hello' or 'Hello' words", contains || contains2);
    }

}
