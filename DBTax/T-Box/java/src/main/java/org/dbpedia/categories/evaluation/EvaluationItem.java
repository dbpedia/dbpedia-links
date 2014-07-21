package org.dbpedia.categories.evaluation;

/**
 * Description
 *
 * @author Dimitris Kontokostas
 * @since 7/15/14 10:41 AM
 */
public class EvaluationItem <E> {
    public String getEvalID() {
        return evalID;
    }

    public String getName() {
        return name;
    }

    public EVALTYPE getType() {
        return type;
    }

    public EVALSOURCE getSource() {
        return source;
    }

    public enum EVALTYPE { CLASS, PATH }
    public enum EVALSOURCE {DBPEDIA, YAGO, WIKIPEDIA, DBTAX}

    private final String evalID;
    private final String name;
    private final EVALTYPE type;
    private final EVALSOURCE source;




    public EvaluationItem(String evalID, String name) {
        this.evalID = evalID.toLowerCase().replace("ci", "c");
        this.name = name;

        Character p = this.evalID.toLowerCase().charAt(0);
        switch (p) {
            case 'p': this.type = EVALTYPE.PATH; break;
            case 'c': this.type = EVALTYPE.CLASS; break;
            default: throw new IllegalArgumentException("Invalid source type: " + evalID);
        }

        Character c = this.evalID.toLowerCase().charAt(1);
        switch (c) {
            case 'd': this.source = EVALSOURCE.DBPEDIA; break;
            case 'y': this.source = EVALSOURCE.YAGO; break;
            case 's': this.source = EVALSOURCE.WIKIPEDIA; break;
            case 'c': this.source = EVALSOURCE.DBTAX; break;
            default: throw new IllegalArgumentException("Invalid source type: " + evalID);
        }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvaluationItem)) return false;

        EvaluationItem that = (EvaluationItem) o;

        if (!evalID.equals(that.evalID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return evalID.hashCode();
    }
}
