package org.dbpedia.categories;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import org.aksw.jena_sparql_api.core.QueryExecutionFactory;
import org.aksw.jena_sparql_api.http.QueryExecutionFactoryHttp;

import java.io.*;
import java.util.*;

/**
 * Gets a list of leaf nodes and generates the hierarchy up to owl:Thing
 *
 * @author Dimitris Kontokostas
 * @since 7/11/14 9:25 AM
 */
public class LeafToRoot {

    public static void main(String[] args) {

        /**
         * arg0 => leafs file
         * arg1 => graph
         * */

        List<String> leafs = getLeafsFromFile(args[0]);


        String graph = args[1];
        String sparqlEndpoint = "http://localhost:8890/sparql";

        //String relation = "<http://www.w3.org/2000/01/rdf-schema#subClassOf>";
        String relation = "<http://www.w3.org/2004/02/skos/core#broader>";

        QueryExecutionFactory qef;
        qef = new QueryExecutionFactoryHttp(sparqlEndpoint, graph);


        List<List<String>> path_list = new ArrayList<>();

        for (String leaf : leafs) {
            List<String> current_path = new ArrayList<>();
            current_path.add(leaf);

            String current_resource = leaf;
            String current_parent = getParentResource(qef, current_resource, relation);
            while (!current_parent.isEmpty()) {
                current_path.add(current_parent);
                current_resource = current_parent;
                current_parent = getParentResource(qef, current_resource, relation);

            }

            path_list.add(current_path);
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[0] + ".paths"), "UTF8"));

            for (List<String> path: path_list) {
                for (String path_item: path) {
                    writer.write(path_item + " < ");
                }
                writer.write(" \n ");
            }
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static String getParentQuery(String resource, String relation) {
        return "SELECT DISTINCT ?parent WHERE { <" + resource + "> " + relation + " ?parent . }";
    }

    /* returns the resource or empty String */
    static String getParentResource(QueryExecutionFactory qef, String resource, String relation) {
        String query = getParentQuery(resource, relation);
        String result = "";

        QueryExecution qe = null;
        ResultSet rs = null;
        try {
            qe = qef.createQueryExecution(query);
            rs = qe.execSelect();


            if (rs.hasNext()) {
                QuerySolution row = rs.next();

                result = row.get("parent").toString();
            }
        } finally {
            if (qe != null)
                qe.close();
        }

        return result;
    }

    /* one leaf URI per line */
    public static List<String> getLeafsFromFile(String filename) {
        List<String> leafs = new ArrayList<>();

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

                leafs.add(line.trim());

            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return leafs;
    }
}
