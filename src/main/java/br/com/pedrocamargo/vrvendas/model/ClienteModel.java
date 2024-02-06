package br.com.pedrocamargo.vrvendas.model;

public class ClienteModel {
    private Integer id;
    private String nome;
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;

    public ClienteModel(Integer id, String nome, String nomeFantasia, String razaoSocial, String cnpj) {
        this.id = id;
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }
    
}
