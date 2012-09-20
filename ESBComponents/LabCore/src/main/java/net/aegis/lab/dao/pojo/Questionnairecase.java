package net.aegis.lab.dao.pojo;

import java.io.Serializable;

/**
 * <p>Pojo mapping table questionnairecase</p>
 * <p></p>
 *
 * <p>Generated at Tue Jun 14 13:43:50 EDT 2011</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Questionnairecase implements Serializable {

    /**
     * Attribute questionnaire
     */
    private Questionnaire questionnaire;
    /**
     * Attribute testcase
     */
    private Testcase testcase;
    /**
     * Attribute questionnairecasePK
     */
    private QuestionnairecasePK questionnairecasePK;

    /**
     * get questionnaire
     */
    public Questionnaire getQuestionnaire() {
        return this.questionnaire;
    }

    /**
     * set questionnaire
     */
    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    /**
     * get testcase
     */
    public Testcase getTestcase() {
        return this.testcase;
    }

    /**
     * set testcase
     */
    public void setTestcase(Testcase testcase) {
        this.testcase = testcase;
    }

    public QuestionnairecasePK getQuestionnairecasePK() {
        return questionnairecasePK;
    }

    public void setQuestionnairecasePK(QuestionnairecasePK questionnairecasePK) {
        this.questionnairecasePK = questionnairecasePK;
    }

    /**
     * <p>Composite primary key for table questionnairecase</p>
     *
     * <p>Generated at Tue Jun 14 13:43:50 EDT 2011</p>
     * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
     */
    public static class QuestionnairecasePK implements Serializable {

        /**
         * Attribute questionId
         */
        private int questionId;
        /**
         * Attribute caseId
         */
        private int caseId;

        /**
         * Return questionId
         */
        public int getQuestionId() {
            return questionId;
        }

        /**
         * @param questionId new value for questionId
         */
        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        /**
         * Return caseId
         */
        public int getCaseId() {
            return caseId;
        }

        /**
         * @param caseId new value for caseId
         */
        public void setCaseId(int caseId) {
            this.caseId = caseId;
        }

        /**
         * calculate hashcode
         */
        public int hashCode() {
            //TODO : implement this method
            return super.hashCode();
        }

        /**
         * equals method
         */
        public boolean equals(Object object) {
            //TODO : implement this method
            return super.equals(object);
        }
    }
}
