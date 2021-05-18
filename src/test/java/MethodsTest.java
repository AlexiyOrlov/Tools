import org.junit.Assert;
import org.junit.Test;
import dev.buildtool.tools.Methods;

import java.util.*;

/**
 * Created on 7/15/18 by alexiy.
 */

public class MethodsTest {

    @Test
    public void testMapCreation()
    {
        HashMap hashMap=Methods.createMapFrom("Java:*.java","Groovy:*.groovy");
        Assert.assertTrue(hashMap.size()>0);
        System.out.println(hashMap);
    }

    @Test
    public void testCharacterRemoval()
    {
        String[] removables="[]./".split("");
        String target="[pine]apple";
        String stripped=Methods.removeAllCharactersFrom(target,removables);
        Assert.assertEquals("pineapple", stripped);
    }
}
