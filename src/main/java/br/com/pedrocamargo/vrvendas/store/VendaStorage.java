package br.com.pedrocamargo.vrvendas.store;

import br.com.pedrocamargo.vrvendas.model.VendaModel;
import java.math.BigDecimal;


public class VendaStorage {
    
    private VendaModel vendaAtual;
    
    public VendaStorage(){}
    
    public VendaStorage(VendaModel vendaModel){
        this.vendaAtual = vendaModel;
    }
    
    
    public void criarNovaVenda() {
        this.vendaAtual = new VendaModel(0, 0, 1, new BigDecimal(0));
    }
    
    public VendaModel getVendaAtual() {
        return vendaAtual;
    }
    
    public BigDecimal getValorTotalVenda() {
        return this.vendaAtual.getValortotal();
    }
}
