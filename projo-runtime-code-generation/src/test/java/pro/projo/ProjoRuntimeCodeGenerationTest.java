package pro.projo;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import pro.projo.internal.rcg.RuntimeCodeGenerationProjo;

public class ProjoRuntimeCodeGenerationTest
{
    @Test
    public void usesRuntimeCodeGeneration()
    {
        assertTrue(Projo.getImplementation() instanceof RuntimeCodeGenerationProjo);
    }
}
