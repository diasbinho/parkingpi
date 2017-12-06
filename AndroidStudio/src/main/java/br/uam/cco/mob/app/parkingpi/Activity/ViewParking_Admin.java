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
import android.widget.ListView;
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

public class ViewParking_Admin extends AppCompatActivity {

    private int contador;
    private int vagasDisponiveis;
    private int vagasOcupadas;

    Spinner parkingsSpinnerList;
    Button parkingsSpinnerListButton;
    ListView recordsParkingListView;

    TextView vagasDisponiveisTextView;
    TextView vagasOcupadasTextView;

    Button returnToPrincipalButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Vagas> listVagas = new ArrayList<Vagas>();
    private ArrayAdapter<Vagas> arrayAdapterVagas;

    private List<Estacionamentos> listEstacionamentos = new ArrayList<>();
    private ArrayAdapter<Estacionamentos> arrayAdapterEstacionamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parking_admin);

        parkingsSpinnerList = (Spinner) findViewById(R.id.parkingsSpinnerList);
        parkingsSpinnerListButton = (Button) findViewById(R.id.parkingsSpinnerListButton);

        recordsParkingListView = (ListView) findViewById(R.id.recordsParkingListView);

        vagasDisponiveisTextView = (TextView) findViewById(R.id.vagasDisponiveisTextView);
        vagasOcupadasTextView = (TextView) findViewById(R.id.vagasOcupadasTextView);

        returnToPrincipalButton = (Button) findViewById(R.id.returnToPrincipalButton);

        inicializarFirebase();

        popularSpinner_Parkings();

        parkingsSpinnerListButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                popularListView_Vacancies();
            }
        });

        returnToPrincipalButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMainActivity();
            }
        });
    }

    /*
     * Método para para pouplar Spinner de Estacionamentos
     *
     */
    private void popularSpinner_Parkings() {
        databaseReference.child("Estacionamentos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listEstacionamentos.clear();

                for (DataSnapshot objDataSnapshot : dataSnapshot.getChildren()) {
                    Estacionamentos e = objDataSnapshot.getValue(Estacionamentos.class);
                    listEstacionamentos.add(e);
                }

                arrayAdapterEstacionamentos = new ArrayAdapter<Estacionamentos>(ViewParking_Admin.this,
                        android.R.layout.simple_list_item_1, listEstacionamentos);
                parkingsSpinnerList.setAdapter(arrayAdapterEstacionamentos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
     * Método para para pouplar ListView de Vagas
     *
     */
    private void popularListView_Vacancies() {
        databaseReference.child("Vagas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listVagas.clear();

                String itemSelecionado = parkingsSpinnerList.getSelectedItem().toString();

                searchItemsByParking(itemSelecionado);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
     * Método para popuplar item conforme filtro do Spinner
     *
     * @parram: String e: recebe valor a ser filtrado.
     */
    private void searchItemsByParking(String e) {

        contador = 0;
        vagasOcupadas = 0;
        vagasDisponiveis = 0;

        Query query = null;

        if (e.equals("")) {
            query = databaseReference.child("Vagas").orderByChild("estacionamento");
        } else {
            query = databaseReference.child("Vagas").orderByChild("estacionamento").equalTo(e);//.startAt(e).endAt(e+"\uf8ff");
        }

        listVagas.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objDataSnapshot : dataSnapshot.getChildren()) {
                    Vagas v = objDataSnapshot.getValue(Vagas.class);

                    if (v.getStatus() == 1) {
                        vagasOcupadas++;
                    } else {
                        vagasDisponiveis++;
                    }

                    listVagas.add(v);

                    contador++;
                }

                arrayAdapterVagas = new ArrayAdapter<Vagas>(ViewParking_Admin.this,
                        android.R.layout.simple_list_item_1, listVagas);
                recordsParkingListView.setAdapter(arrayAdapterVagas);

                if (contador == 0) {
                    notificaUsuario_Toast("0 registros.");
                    echoContador();
                } else {
                    notificaUsuario_Toast(contador + " registro(s).");
                    echoContador();
                }
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
        FirebaseApp.initializeApp(ViewParking_Admin.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /*
    * Método para imprimir em tela os contadores de ocupação
    */
    private void echoContador() {
        vagasOcupadasTextView.setText("" + vagasOcupadas);
        vagasDisponiveisTextView.setText("" + vagasDisponiveis);
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
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /*
    * Metodo de retorno ao menu principal
    *
    */
    private void returnToMainActivity() {
        Intent intent = new Intent(ViewParking_Admin.this, MenuAdmin.class);
        startActivity(intent);
        finish();
    }
}
