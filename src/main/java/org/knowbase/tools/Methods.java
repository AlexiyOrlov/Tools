package org.knowbase.tools;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 7/12/18 by alexiy.
 */
public class Methods {

    public static Path createFile(Path path, LinkOption... linkOptions)
    {
        if(!Files.exists(path,linkOptions))
        {
            if(path.getParent()!=null)
            {
                try {
                    Files.createDirectories(path.getParent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                path=Files.createFile(path);
                return path;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return path;
    }

    public static List<String> readFile(Path path)
    {
        try {
            return Files.readAllLines(path,Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Path write(Path path,Iterable<? extends CharSequence> charSequences)
    {
        try {
            return Files.write(path,charSequences);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recursively retrieves all files from the specified path
     * @param from supposed to be a directory
     * @param list list to populate with files
     * @return file list
     */

    public static List<Path> getFiles(Path from,List<Path> list)
    {

        try {
            Stream<Path> pathStream=Files.list(from);
            List<Path> paths=pathStream.collect(Collectors.toList());
            paths.forEach(path -> {
                if(Files.isDirectory(path,LinkOption.NOFOLLOW_LINKS))
                {
                    getFiles(path,list);
                }
                else{
                    list.add(path);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static double getVisualTextWidth(String string)
    {
        Text text=new Text(string);
        new Scene(new Group(text));
        text.applyCss();
        return text.getLayoutBounds().getWidth();
    }

    /**
     * Creates a map from strings in format [key:value]
     */
    public static HashMap<String,String> createMapFrom(String... strings)
    {
        HashMap<String,String> hashMap=new HashMap<>(strings.length);
        for (String string : strings) {
            if(string.contains(":"))
            {
                String[] pair=string.split(":");
                if(pair.length==2)
                {
                    hashMap.put(pair[0],pair[1]);
                }
            }
        }
        return hashMap;
    }

    /**
     * Removes given characters from given string. Any of the strings with length > 1 will be ignored
     * @param toRemove array of characters in string form
     */
    public static String removeAllCharactersFrom(String target, String... toRemove)
    {
        for (String s : toRemove) {
            if(s.length()>1)
                continue;
            target=target.replaceAll(Pattern.quote(s),"");
        }
        return target;
    }
}
