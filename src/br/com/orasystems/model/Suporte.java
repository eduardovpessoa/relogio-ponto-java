package br.com.orasystems.model;

/**
 * @author Eduardo
 */
public class Suporte {

    private Long codigoSuporte;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCodigoSuporte() {
        return codigoSuporte;
    }

    public void setCodigoSuporte(Long codigoSuporte) {
        this.codigoSuporte = codigoSuporte;
    }

}
