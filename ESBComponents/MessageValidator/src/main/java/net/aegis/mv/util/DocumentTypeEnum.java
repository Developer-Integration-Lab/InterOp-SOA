/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.mv.util;

/**
 *
 * @author Naresh.Jaganathan
 */

/*  Enum for query/retrieve document types (dynamic/static)
    dynamicDocInd is used to verify whether it's dynamic or static doc type
    id is used to specify the number of documents received - only used for dynamic doc type (set to one currently)
*/
public enum DocumentTypeEnum {

    DYNAMIC_DOCUMENT(1L, "S"),
    STATIC_DOCUMENT(2L, "M");

    private Long id;
    private String dynamicDocInd;

    private DocumentTypeEnum (Long id, String dynamicDocInd){
        this.id = id;
        this.dynamicDocInd = dynamicDocInd;
    }

    public String getDynamicDocInd() {
        return dynamicDocInd;
    }

    public void setDynamicDocInd(String dynamicDocInd) {
        this.dynamicDocInd = dynamicDocInd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static DocumentTypeEnum valueOf(Long id){
        for(DocumentTypeEnum d: DocumentTypeEnum.values()){
            if( d.getId().equals(id) )
                return d;
        }
        /* none found; throw error */
        throw new RuntimeException("Invalid dynamic document type");
    }


}