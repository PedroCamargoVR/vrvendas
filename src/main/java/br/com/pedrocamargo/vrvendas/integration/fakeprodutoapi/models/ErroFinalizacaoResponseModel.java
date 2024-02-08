package br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models;

import java.util.Map;


public class ErroFinalizacaoResponseModel {
    private String message;
    private Map<Integer, ErrorDetailModel> errors;
    
    public ErroFinalizacaoResponseModel(String message,Map<Integer, ErrorDetailModel> errors){
        this.message = message;
        this.errors = errors;
    }
    
    public ErroFinalizacaoResponseModel(Map<Integer, ErrorDetailModel> errors){
        this.message = "";
        this.errors = errors;
    }
    
    public String getMessage() {
        return message;
    }

    public Map<Integer, ErrorDetailModel> getErrors() {
        return errors;
    }
    
}
