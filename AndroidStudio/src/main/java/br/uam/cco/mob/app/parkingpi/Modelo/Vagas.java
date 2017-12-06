package br.uam.cco.mob.app.parkingpi.Modelo;

/**
 * Created by Marcos Rocha on 03/12/2017.
 */

public class Vagas {

    public int id;
    public String nome;
    public int status;
    public String estacionamento;

    public Vagas() { }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) { this.status = status; }

    public String getEstacionamento() {
        return estacionamento;
    }

    public void setEstacionamento(String estacionamento) {
        this.estacionamento = estacionamento;
    }

    public String toString() {

        String strStatus;

        if (status == 1) {
            strStatus = " (Em uso)";
        } else {
            strStatus = "";
        }

        return id + ". " + nome + strStatus;
    }
}