import com.vertafore.common.Global_Common;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Global_CommonTest {

    @Test
    public void wheelsOnTheBus() throws Exception{
        Boolean available = Global_Common.availabilityCheck("http://www.google.com");
        assertEquals("Availability Check Test", Boolean.TRUE, available);
    }
}
