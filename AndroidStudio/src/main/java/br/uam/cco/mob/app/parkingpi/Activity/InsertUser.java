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
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.UUID;

import br.uam.cco.mob.app.parkingpi.Modelo.Usuarios;
import br.uam.cco.mob.app.parkingpi.R;

public class InsertUser extends AppCompatActivity {

    private int lastItem = 1;

    EditText nameUserEditText;

    ListView recordsUsersListView;

    Button insertUserItemButton;
    Button returnToPrincipalButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Usuarios> listUsuario = new ArrayList<Usuarios>();
    private ArrayAdapter<Usuarios> arrayAdapterUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_user);

        inicializarFirebase();

        processaLastId();

        eventoDatabase_PopularListViewUsers();

        nameUserEditText = (EditText) findViewById(R.id.nameUserEditText);

        recordsUsersListView = (ListView) findViewById(R.id.recordsUsersListView);

        insertUserItemButton = (Button) findViewById(R.id.insertUserItemButton);
        returnToPrincipalButton = (Button) findViewById(R.id.returnToPrincipalButton);

        insertUserItemButton.setOnClickListener(new Button.OnClickListener(){
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
    * Método para popular listView de Usuarios
    *
    */
    private void eventoDatabase_PopularListViewUsers() {
        try {
            databaseReference.child("Usuarios").orderByChild("nome").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listUsuario.clear();

                    for (DataSnapshot objDataSnapshot : dataSnapshot.getChildren()) {
                        Usuarios u = objDataSnapshot.getValue(Usuarios.class);

                        listUsuario.add(u);
                    }

                    arrayAdapterUsuario = new ArrayAdapter<Usuarios>(InsertUser.this,
                            android.R.layout.simple_list_item_1, listUsuario);
                    recordsUsersListView.setAdapter(arrayAdapterUsuario);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            notificaUsuario_Toast(e.getMessage());
        }
    }

    /*
    * Método de processamento de inserção de usuario
    *
    */
    private void insertNewItem() {

        try {

            Usuarios u = new Usuarios();

            u.setId(lastItem);
            u.setNome(nameUserEditText.getText().toString());
            u.setSenha("123");
            u.setPerfil(2);

            databaseReference.child("Usuarios").child(String.valueOf(u.getId())).setValue(u);

            clearActivity();

            notificaUsuario_Toast("Usuario cadastrado com sucesso!");

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

        query = databaseReference.child("Usuarios");

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
     * Método para inicialização com a database
     */
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(InsertUser.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
        Intent intent = new Intent(InsertUser.this, MenuAdmin.class);
        startActivity(intent);
        finish();
    }

    /*
    * Método para limpar campos da Activitie
    */
    private void clearActivity() {
        nameUserEditText.setText("");
    }
}
