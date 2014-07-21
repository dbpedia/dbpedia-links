package org.dbpedia.categories.evaluation;

/**
 * Description
 *
 * @author Dimitris Kontokostas
 * @since 7/15/14 10:43 AM
 */
public class PathEvaluation {

    public String getEvaluator() {
        return evaluator;
    }

    public VALID getValid() {
        return valid;
    }

    public SPECIFIC getSpecific() {
        return specific;
    }

    public BROAD getBroad() {
        return broad;
    }

    public EvaluationItem getEvaluationItem() {
        return evaluationItem;
    }

    public enum VALID { VALID, NOT_VALID}
    public enum SPECIFIC { SPECIFIC, NOT_SPECIFIC, UNDEFINED}
    public enum BROAD { BROAD, NOT_BROAD, UNDEFINED}

    private final String evaluator;
    private final VALID valid;
    private final SPECIFIC specific;
    private final BROAD broad;
    private final EvaluationItem evaluationItem;


    public PathEvaluation(String evaluator, VALID valid, SPECIFIC specific, BROAD broad, EvaluationItem evaluationItem) {
        this.evaluator = evaluator;
        this.valid = valid;
        this.specific = specific;
        this.broad = broad;
        this.evaluationItem = evaluationItem;
    }

    public PathEvaluation(String evaluator, String pa, String pb, String pc, EvaluationItem evaluationItem) {
        this.evaluator = evaluator;
        this.evaluationItem = evaluationItem;

        assert (pa !=  null && !pa.isEmpty()) ;

        this.valid = (pa.toLowerCase().trim().contains("not")) ? VALID.NOT_VALID: VALID.VALID;
        if (this.valid == VALID.VALID) {
            this.specific = (pb.toLowerCase().trim().contains("not") || pb.trim().isEmpty()) ? SPECIFIC.NOT_SPECIFIC : SPECIFIC.SPECIFIC;
            this.broad = (pc.toLowerCase().trim().contains("not")  || pc.trim().isEmpty()) ? BROAD.NOT_BROAD : BROAD.BROAD;
        } else {
            this.specific = SPECIFIC.UNDEFINED;
            this.broad = BROAD.UNDEFINED;
        }
    }



}
