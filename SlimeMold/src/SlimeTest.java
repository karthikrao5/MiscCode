import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class SlimeTest {

    private Organism org;

    @Before
    public void setup() {
        org = new Organism();
    }

    @Test
    public void testConst() {
        System.out.println(org.toString());
    }

}