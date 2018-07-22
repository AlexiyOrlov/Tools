import org.junit.Assert;
import org.junit.Test;
import org.knowbase.tools.Methods;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Test
    public void delete() {
        Path folder= Paths.get("outer");

//        Files.createTempFile(folder,)
        Assert.assertTrue(Files.exists(folder));
        List<Path> folders=Methods.getDirectories(folder,new ArrayList<>());
        System.out.println(folders);
        Methods.delete(folder);
//        if(Methods.delete(folder))
//        {
//            System.out.println("Success");
//        }
    }

    @Test
    public void getFolders() {
    }
}
