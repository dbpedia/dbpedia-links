package org.dbpedia.categories;

/**
 * Description
 *
 * @author Dimitris Kontokostas
 * @since 6/23/14 11:34 AM
 */
public class Relation implements Comparable {

    private final String parent;
    private final String child;

    public Relation(String parent, String child) {
        this.parent = parent;
        this.child = child;
        assert (this.parent != null);
        assert (this.child != null);
    }

    public String getParent() {
        return parent;
    }

    public String getChild() {
        return child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Relation)) return false;

        Relation relation = (Relation) o;

        if (child != null ? !child.equals(relation.child) : relation.child != null) return false;
        if (parent != null ? !parent.equals(relation.parent) : relation.parent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + (child != null ? child.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "parent='" + parent + '\'' +
                ", child='" + child + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (!(o instanceof Relation)) return -1;

        Relation relation = (Relation) o;

        int parentCmp = parent.compareTo(relation.parent);

        if (parentCmp != 0) {
            return parentCmp;
        }
        else {
            return child.compareTo(relation.child);
        }

    }
}
