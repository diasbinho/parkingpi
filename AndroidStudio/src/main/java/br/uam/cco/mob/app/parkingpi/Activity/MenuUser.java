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

public class MenuUser extends AppCompatActivity {

    Button listViewParkingButton;
    Button editPassUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);

        listViewParkingButton = (Button) findViewById(R.id.listViewParkingButton);
        editPassUserButton = (Button) findViewById(R.id.editPassUserButton);

        listViewParkingButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                listViewParking_Click();
            }
        });

        editPassUserButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMyPass_Click();
            }
        });
    }

    /*
    * Método para acessar Actitivie de edição de senha de usuario
    */
    private void editMyPass_Click() {
        Intent intent = new Intent(MenuUser.this, EditUserPass.class);
        startActivity(intent);
        finish();
    }

    /*
    * Método para acessar Actitivie relatório de estacionamentos e ocupações
    */
    private void listViewParking_Click() {
        Intent intent = new Intent(MenuUser.this, ViewParking_User.class);
        startActivity(intent);
        finish();
    }
}
