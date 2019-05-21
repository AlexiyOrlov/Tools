package org.knowbase.tools;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilePathTools {
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
                Files.createFile(path);
                return path;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return path;
    }

    /**
     * Copies the file, creating parent directories in the process
     */
    public static Path copyFile(Path from, Path to, CopyOption... copyOptions)
    {
        try {
            if(to.getParent()!=null)
            {
                Files.createDirectories(to.getParent());
            }
            return Files.copy(from,to,copyOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return to;
    }

    public static List<String> readFile(Path path)
    {
        try {
            return Files.readAllLines(path, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Path write(Path path, Iterable<? extends CharSequence> charSequences)
    {
        try {
            return Files.write(path,charSequences);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recursively deletes files and folders
     * @param path file or dir
     * @return true on success
     */
    public static boolean delete(Path path)
    {
        if(Files.isDirectory(path))
        {
            List<Path> files=getFiles(path,new ArrayList<>());
            for (Path file : files) {
                delete(file);
            }

            List<Path> folders= getDirectories(path,new ArrayList<>());
            Collections.reverse(folders);
            for (Path directory : folders) {
                try {
                    if(Files.isWritable(directory))
                        Files.deleteIfExists(directory);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            try {
                return Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        else{
            try {
                if(Files.isWritable(path))
                    return Files.deleteIfExists(path);
                else
                    return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
//        return true;
    }

    /**
     * Recursively retrieves all files from the specified path
     * @param from a directory
     * @param list list to populate with files
     * @return file list
     */
    public static List<Path> getFiles(Path from, List<Path> list)
    {
        if(Files.isDirectory(from)) {
            if(Files.isReadable(from)) {
                try {
                    Stream<Path> pathStream = Files.list(from);
                    List<Path> paths = pathStream.collect(Collectors.toList());
                    paths.forEach(path -> {
                        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                            getFiles(path, list);
                        } else {
                            list.add(path);
                        }
                    });
                    pathStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            throw new IllegalArgumentException(from+" is not a directory");
        }
        return list;
    }

    /**
     * Recursively gets all directories
     */
    public static List<Path> getDirectories(Path from, List<Path> list)
    {
        if(Files.isDirectory(from)) {
            if(Files.isReadable(from)) {
                list.add(from);
                try {
                    Stream<Path> pathStream = Files.list(from);
                    List<Path> paths = pathStream.collect(Collectors.toList());
                    paths.forEach(path -> {
                        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                            getDirectories(path, list);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            throw new IllegalArgumentException(from+" is not a directory");
        }
        return list;
    }
}
