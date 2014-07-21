package org.dbpedia.categories;

import java.io.*;
import java.util.*;

/**
 * User: Dimitris Kontokostas
 * Description
 * Created: 6/17/14 4:33 PM
 */
public class HierarchyGenerator {

    private static String normalizeName(String original) {
        if(original.length() == 0)
            return original;
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.err.println("file missing from arguments");
            System.exit(0);
        }

        Set<String> instances = generateInstances(args[1]);
        Map<String, Node> nodeMap = generateNodeMap(args[0], instances);


        Node contentNode = new Node("Content");


        /* put all parentless nodes under content */
        long nodeCount = 0;
        long parentlessNodes = 0;
        long childlessNodes = 0;
        for (Node node: nodeMap.values()) {
            if (node.getParent() == null ) {
                contentNode.addChildren(node);
                parentlessNodes++;
            }

            long childCount = node.getChildren().size();

            nodeCount += childCount;
            if (childCount == 0) {
                childlessNodes++;
            }
        }
        System.out.println("Total distinct nodes: " + nodeMap.size());
        System.out.println("Total parentless nodes: " + parentlessNodes);
        System.out.println("Total childless nodes: " + childlessNodes);
        System.out.println("Total child count: " + nodeCount);


        Set<Node> latestLevelCategories = new HashSet<Node>();
        List<Relation> hierarchy = new LinkedList<Relation>();


        latestLevelCategories.add(contentNode);
        int level = 0;

        while (true) {
            System.out.println("Level: " + level + " contains: " + latestLevelCategories.size() + " nodes");
            Set<Node> currentLevel = new HashSet<Node>();

            int levelSkippedNodes = 0;
            int levelAddedRelations = 0;
            for (Node parentNode: latestLevelCategories) {
                parentNode.setLevel(level);

                int skippedNodes = 0;
                int addedRelations = 0;
                for (Node childNode: parentNode.getChildren()) {
                    if (childNode.hasAssignedLevel()) { // means already assigned level so skip
                        skippedNodes++;

                    }
                    else {

                        currentLevel.add(childNode);
                        Relation relation = new Relation(parentNode.getName(), childNode.getName());
                        hierarchy.add(relation);
                        addedRelations++;
                    }

                }
                //
                levelAddedRelations += addedRelations;
                levelSkippedNodes += skippedNodes;
            }
            System.out.println("\tAdded " + levelAddedRelations + " Skipped " + levelSkippedNodes );

            if (currentLevel.isEmpty()) {
                break;
            }

            latestLevelCategories = currentLevel;
            level++;


        }

        System.out.println("New hierarchy contains : " + hierarchy.size());

        long unussignedNodesCount = 0;
        for (Node node: nodeMap.values()) {
            if (node.getLevel() <0) {
                unussignedNodesCount++;
            }
        }

        System.out.println("Total unussigned nodes: " + unussignedNodesCount);

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[0] + ".distinct.tsv"), "UTF8"));

            Collections.sort(hierarchy);
            for (Relation rel: hierarchy) {
                writer.write(rel.getParent() + "\t" + rel.getChild() + "\n");
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

    public static Map<String, Node> generateNodeMap(String filename, Set<String> instances) {
        Map<String, Node> nodeMap = new HashMap<String, Node>();

        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File " + filename + " not fount!", e);

        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UnsupportedEncodingException: ", e);
        }

        String line = null;
        long instancesSkipped = 0;

        try {
            /* Fill the existing tree */
            while ((line = in.readLine()) != null) {

                String[] parts = line.split("\t");
                if (parts.length != 2) {
                    System.err.println("Invalid row with parts != 2");
                }


                String parentName  = normalizeName(parts[0].trim());
                String childName   = normalizeName(parts[1].trim());

                if (parentName.toLowerCase().contains("wikidata") || childName.toLowerCase().contains("wikidata")) {
                    continue;
                }

                if (Character.isDigit(parentName.charAt(0)) || Character.isDigit(childName.charAt(0))) {
                    continue;
                }

                if (instances.contains(parentName.toLowerCase()) || instances.contains(childName.toLowerCase())) {
                    instancesSkipped++;
                    continue;
                }

                Node parentNode = nodeMap.get(parentName);
                Node childNode  = nodeMap.get(childName);
                if (childNode == null) {
                    childNode = new Node(childName);
                    nodeMap.put(childName, childNode);
                }

                if (parentName.equals(childName)) {
                    continue;
                }
                if (parentName.equals("Category") || parentName.equals("Categories")) {
                    continue;
                }



                if (parentNode == null ) {
                    parentNode = new Node(parentName);
                    nodeMap.put(parentName, parentNode);
                }

                parentNode.addChildren(childNode);
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        System.out.println("Skipped a total of " + instancesSkipped + " relations containing instances");
        return nodeMap;
    }

    public static Set<String> generateInstances(String filename) {
        Set<String> instances = new HashSet();

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

                instances.add(line.toLowerCase().trim());

            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return instances;
    }
}
