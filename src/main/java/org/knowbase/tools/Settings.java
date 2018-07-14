package org.knowbase.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 7/12/18 by alexiy.
 */
public class Settings {
    private HashMap<String,String> settings;
    private Path storage;

    public Settings(Path saveIn)
    {
        if(!Files.exists(saveIn,LinkOption.NOFOLLOW_LINKS))
        {
            if(saveIn.getParent()!=null)
            {
                try {
                    Files.createDirectories(saveIn.getParent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                storage=Files.createFile(saveIn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            storage=saveIn;
        try {
            List<String> list=Files.readAllLines(storage);
            settings=new HashMap<>(list.size());
            list.forEach(s -> {
                if(!s.startsWith("#"))
                {
                    String[] parts=s.split("=");
                    if(parts.length==2)
                    {
                        settings.put(parts[0],parts[1]);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOrDefault(String property, String defaultValue)
    {
        String value=settings.get(property);
        if(value!=null)
            return value;
        else {
            put(property, defaultValue);
            return defaultValue;
        }
    }

    /**
     * Sets a property to be saved
     */
    public String put(String property, String value)
    {
        return settings.put(property, value);
    }

    public void save()
    {
        List<String> entries=new ArrayList<>(settings.size());
        settings.forEach((s, s2) -> entries.add(s+"="+s2+System.lineSeparator()));
        try {
            Files.write(storage,entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
