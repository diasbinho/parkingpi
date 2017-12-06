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

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.uam.cco.mob.app.parkingpi.Modelo.Estacionamentos;
import br.uam.cco.mob.app.parkingpi.Modelo.Vagas;
import br.uam.cco.mob.app.parkingpi.R;

public class InsertVacancy extends AppCompatActivity {

    private int lastItem = 1;

    CheckBox statusVacancyCheckBox;
    EditText nameVacancyEditText;
    Spinner parkingsSpinnerList;

    TextView insertTitleTextView;

    Button insertVacancyItemButton;
    Button returnToPrincipalButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Estacionamentos> listEstacionamentos = new ArrayList<>();
    private ArrayAdapter<Estacionamentos> arrayAdapterEstacionamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_vacancy);

        inicializarFirebase();

        processaLastId();

        insertTitleTextView = (TextView) findViewById(R.id.insertTitleTextView);

        nameVacancyEditText = (EditText) findViewById(R.id.nameVacancyEditText);
        statusVacancyCheckBox = (CheckBox) findViewById(R.id.statusVacancyCheckBox);
        parkingsSpinnerList = (Spinner) findViewById(R.id.parkingsSpinnerList);

        eventoDatabase_PopularSpinnerParkings();

        insertVacancyItemButton = (Button) findViewById(R.id.insertVacancyItemButton);
        returnToPrincipalButton = (Button) findViewById(R.id.returnToPrincipalButton);

        insertVacancyItemButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                insertNewItem();
            }
        });
        returnToPrincipalButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                returnToMainActivity();
            }
        });

    }

    /*
    * Método para popular Spinner de Estacionamentos
    *
    */
    private void eventoDatabase_PopularSpinnerParkings() {
        databaseReference.child("Estacionamentos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listEstacionamentos.clear();

                for (DataSnapshot objDataSnapshot:dataSnapshot.getChildren()){
                    Estacionamentos e = objDataSnapshot.getValue(Estacionamentos.class);
                    listEstacionamentos.add(e);
                }

                arrayAdapterEstacionamentos = new ArrayAdapter<Estacionamentos>(InsertVacancy.this,
                        android.R.layout.simple_list_item_1, listEstacionamentos);
                parkingsSpinnerList.setAdapter(arrayAdapterEstacionamentos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
     * Método para inicialização com a database
     */
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(InsertVacancy.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /*
    * Método de processamento de inserção de vaga
    *
    */
    private void insertNewItem() {

        try {

        Vagas v = new Vagas();

        //vaga.setId(UUID.randomUUID().toString());
        v.setId(lastItem);
        v.setNome(nameVacancyEditText.getText().toString());
        v.setEstacionamento(parkingsSpinnerList.getSelectedItem().toString());

        if (statusVacancyCheckBox.isChecked() == true){
            v.setStatus(1);
        } else {
            v.setStatus(0);
        }

        databaseReference.child("Vagas").child(String.valueOf(v.getId())).setValue(v);

        clearActivity();

        notificaUsuario_Toast("Vaga cadastrada com sucesso!");

        } catch(Exception e) {
            notificaUsuario_Toast(e.getMessage());
        }
    }

    /*
    * Método de retorno do ultimo id cadastrado
    *
    */
    private void processaLastId() {

        Query query = null;

        query = databaseReference.child("Vagas");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    lastItem = (int) dataSnapshot.getChildrenCount() + 1;

                } catch (Exception e) {
                    notificaUsuario_Toast(e.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                lastItem = 1;
            }
        });
    }

    /*
    * Método para notifcar usuario via Toast
    *
    * @param: String e: texto de mensagem de alerta
    *
    */
    public void notificaUsuario_Toast(String e) {
        Context context = getApplicationContext();
        CharSequence text = e;
        int duration = Toast.LENGTH_SHORT ;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /*
    * Metodo de retorno ao menu principal
    *
    */
    private void returnToMainActivity() {
        Intent intent = new Intent(InsertVacancy.this, MenuAdmin.class);
        startActivity(intent);
        finish();
    }

    /*
    * Método para limpar campos da Activitie
    */
    private void clearActivity() {
        nameVacancyEditText.setText("");
        statusVacancyCheckBox.setChecked(false);
    }
}
