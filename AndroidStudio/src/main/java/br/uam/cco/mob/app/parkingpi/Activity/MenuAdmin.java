/*
 * Projeto Semestral:
 *  Computação Móvel Prof. Ricardo de Souza Jacomini
 *
 * Turma: Ciências da computação - 8º Semestre
 *
 * Grupo de trabalho:
 *  20283049 Ana Carolina de Branco
 *  20584180 Fernanda Liviero Fernandes Polo
 *  20558102 Leandro Forcemo de Oliveira
 *  20574011 Marcos Antonio Leite da Rocha
 *  20548777 Rubens Dias Neto
 *
 */

package br.uam.cco.mob.app.parkingpi.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.uam.cco.mob.app.parkingpi.R;

public class MenuAdmin extends AppCompatActivity {

    Button insertParkingButton;
    Button insertVacancyButton;
    Button listViewParkingButton;
    Button insertUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        insertParkingButton = (Button) findViewById(R.id.insertParkingButton);
        insertVacancyButton = (Button) findViewById(R.id.insertVacancyButton);
        listViewParkingButton = (Button) findViewById(R.id.listViewParkingButton);
        insertUserButton = (Button) findViewById(R.id.insertUserButton);

        insertUserButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                insertuser_Click();
            }
        });
        insertParkingButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                insertParking_Click();
            }
        });
        insertVacancyButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                insertVacancy_Click();
            }
        });
        listViewParkingButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                listViewParking_Click();
            }
        });
    }

    /*
    * Método para acessar Actitivie se inserção de novo usuario
    */
    private void insertuser_Click() {
        Intent intent = new Intent(MenuAdmin.this, InsertUser.class);
        startActivity(intent);
        finish();
    }

    /*
    * Método para acessar Actitivie se inserção de novo estacionamento
    */
    private void insertParking_Click() {
        Intent intent = new Intent(MenuAdmin.this, InsertParking.class);
        startActivity(intent);
        finish();
    }

    /*
    * Método para acessar Actitivie se inserção de nova vaga
    */
    private void insertVacancy_Click() {
        Intent intent = new Intent(MenuAdmin.this, InsertVacancy.class);
        startActivity(intent);
        finish();
    }

    /*
    * Método para acessar Actitivie relatório de estacionamentos e ocupações
    */
    private void listViewParking_Click() {
        Intent intent = new Intent(MenuAdmin.this, ViewParking_Admin.class);
        startActivity(intent);
        finish();
    }
}
