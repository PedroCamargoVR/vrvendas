package br.com.pedrocamargo.vrvendas.vo;

import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class VendaVO {
    private Integer id;
    private Integer id_status;
    private ClienteModel cliente;
    private BigDecimal valortotal;
    private Timestamp created_at;
    private Timestamp updated_at;
    
    public VendaVO(Integer id, Integer id_status, ClienteModel cliente,  BigDecimal valortotal, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.id_status = id_status;
        this.cliente = cliente;
        this.valortotal = valortotal;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Integer getId() {
        return id;
    }

    public Integer getId_status() {
        return id_status;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }
}
