package br.uam.cco.mob.app.parkingpi.Modelo;

/**
 * Created by Marcos Rocha on 04/12/2017.
 */

public class Estacionamentos {

    private int id;
    private String nome;
    private String endereco;
    private String cnpj;
    private String hora_abertura;
    private String hora_fechamento;

    public Estacionamentos() { }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCnpj() { return cnpj; }

    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getHora_abertura() { return hora_abertura; }

    public void setHora_abertura(String hora_abertura) { this.hora_abertura = hora_abertura; }

    public String getHora_fechamento() { return hora_fechamento; }

    public void setHora_fechamento(String hora_fechamento) { this.hora_fechamento = hora_fechamento; }

    public String toString() {
        return nome;
    }
}
