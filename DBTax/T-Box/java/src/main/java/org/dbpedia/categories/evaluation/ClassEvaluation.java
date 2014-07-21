package org.dbpedia.categories.evaluation;

/**
 * Description
 *
 * @author Dimitris Kontokostas
 * @since 7/15/14 10:43 AM
 */
public class ClassEvaluation {

    public String getEvaluator() {
        return evaluator;
    }

    public TYPE getcType() {
        return cType;
    }

    public BREAKABLE getBreakable() {
        return breakable;
    }

    public EvaluationItem getEvaluationItem() {
        return evaluationItem;
    }

    public enum TYPE { CLASS, INSTANCE, UNDEFINED }
    public enum BREAKABLE { CAN_BREAK, CANNOT_BREAK, UNDEFINED }

    private final String evaluator;
    private final TYPE cType;
    private final BREAKABLE breakable;
    private final EvaluationItem evaluationItem;


    public ClassEvaluation(String evaluator, TYPE cType, BREAKABLE breakable, EvaluationItem evaluationItem) {
        this.evaluator = evaluator;
        this.cType = cType;
        this.breakable = breakable;
        this.evaluationItem = evaluationItem;
    }

    public ClassEvaluation(String evaluator, String cType, String breakable, EvaluationItem evaluationItem) {
        this.evaluator = evaluator;
        this.evaluationItem = evaluationItem;

        assert (cType !=  null && !cType.isEmpty()) ;

        this.cType = (cType.toLowerCase().trim().equals("class")) ? TYPE.CLASS : TYPE.INSTANCE;
        if (this.cType == TYPE.CLASS) {
            this.breakable = (breakable.toLowerCase().trim().contains("cannot")) ? BREAKABLE.CANNOT_BREAK : BREAKABLE.CAN_BREAK;
        } else {
            this.breakable = BREAKABLE.UNDEFINED;
        }
    }



}
