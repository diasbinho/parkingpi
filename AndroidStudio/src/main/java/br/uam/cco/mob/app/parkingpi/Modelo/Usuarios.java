package br.uam.cco.mob.app.parkingpi.Modelo;

/**
 * Created by Marcos Rocha on 05/12/2017.
 */

public class Usuarios {
    private int id;
    private String nome;
    private String senha;
    private int perfil;

    public Usuarios() { }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() { return senha; }

    public void setSenha(String senha) { this.senha = senha; }

    public int getPerfil() { return perfil; }

    public void setPerfil(int perfil) { this.perfil = perfil; }

    public String toString() {

        return id + ". " + nome;
    }
}
