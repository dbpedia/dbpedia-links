package org.dbpedia.categories;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description
 *
 * @author Dimitris Kontokostas
 * @since 7/15/14 11:24 AM
 */
public class Utils {

    public static List<String> getFileLines(String filename) {
        List<String> lines = new ArrayList<>();

        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File " + filename + " not fount!", e);

        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UnsupportedEncodingException: ", e);
        }

        String line = null;

        try {
            /* Fill the existing tree */
            while ((line = in.readLine()) != null) {

                lines.add(line.trim());

            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return lines;

    }

    public static <E> E getSetItem(Set<E> set, E item) {
        for (E e: set) {
            if (e.equals(item))
                return e;
        }
        return null;
    }
}
