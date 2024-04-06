import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetLocalNumber()
    {
        MainClass mainClass = new MainClass();
        Assert.assertEquals("Values are not equals " ,14, mainClass.getLocalNumber());
    }

}
