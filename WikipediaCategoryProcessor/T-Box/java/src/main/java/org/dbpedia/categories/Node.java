package org.dbpedia.categories;

import java.util.HashSet;
import java.util.Set;

/**
 * Description
 *
 * @author Dimitris Kontokostas
 * @since 6/23/14 10:27 AM
 */
public class Node {
    private final String name;
    private final Set<Node> children;

    private int level = -1;
    private Node parent = null;

    public Node(String name) {
        if (name != null)
            this.name = name;
        else
            this.name = "";
        children = new HashSet<Node>();
    }

    public String getName() {
        return name;
    }


    public Set<Node> getChildren() {
        return children;
    }

    public void addChildren(Node node) {

        // No identity
        if (this == node) {
            return;
        }

        // No direct cycles
        if (node.getChildren().contains(this)) {
            return;
        }

        // no A->B->A
        for (Node n: node.getChildren()) {
            if (n.getChildren().contains(this)) {
                return;
            }
        }

        this.children.add(node);
        node.setParent(this);
    }

    public int getLevel() {
        return this.level;
    }

    public boolean hasAssignedLevel() {
        return this.level >= 0;
    }

    public void setLevel(int level ) {
        this.level = level;
    }

    /* Check equality only with name */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof String) return name.equals(o);
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (!name.equals(node.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
