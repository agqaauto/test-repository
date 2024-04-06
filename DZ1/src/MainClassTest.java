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

}
