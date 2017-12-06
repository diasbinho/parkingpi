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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import br.uam.cco.mob.app.parkingpi.Modelo.Usuarios;
import br.uam.cco.mob.app.parkingpi.R;

public class MainActivity extends AppCompatActivity {

    private String uName;
    private String uPass;

    TextView userEditText;
    TextView passEditText;
    TextView messageTextView;
    Button acessarLoginButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        userEditText = (TextView) findViewById(R.id.userEditText);
        passEditText = (TextView) findViewById(R.id.passEditText);
        messageTextView = (TextView) findViewById(R.id.messageTextView);
        acessarLoginButton = (Button) findViewById(R.id.acessarLoginButton);

        inicializarFirebase();

        acessarLoginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarPreenchimento_TextView();
            }
        });
    }

    /*
     * Método para inicialização com a database
     */
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /*
     * Método de verificação dos campos TextView da activitie
     */
    private void validarPreenchimento_TextView() {

        String user = userEditText.getText().toString();
        String pass = passEditText.getText().toString();

        if (user.trim().equals("")) {
            notificaUsuario_TextView("Um 'Usuário' se faz necessário.");
        } else {
            if (pass.trim().equals("")) {
                notificaUsuario_TextView("Uma 'Senha' se faz necessária.");
            } else {
                processaAutenticacao(user, pass);
            }
        }
    }

    /*
    * Método de processamento da autenticação do usuario
    *
    * @param: String usuario: recebe o nome do usuario
    *         String senha: recebe o valor da senha de usuario a ser validada
    */
    private void processaAutenticacao(String usuario, String senha) {

        Query query = null;

        uName = usuario;
        uPass = senha;

        query = databaseReference.child("Usuarios").orderByChild("nome").equalTo(usuario).limitToFirst(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getChildrenCount() == 1) {
                        for (DataSnapshot objDataSnapshot:dataSnapshot.getChildren()){
                            Usuarios u = objDataSnapshot.getValue(Usuarios.class);

                            if (uName.equals(u.getNome())) {
                                if (uPass.equals(u.getSenha())) {

                                    if (equalsProfile(u.getPerfil(), 1)) {
                                        Intent intent = new Intent(MainActivity.this, MenuAdmin.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, MenuUser.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    notificaUsuario_TextView("Senha e/ou usuario inválidos. Favor verificar!");

                                    break;
                                }
                            } else {
                                notificaUsuario_TextView("Usuario e/ou senha inválidos. Favor verificar!");

                                break;
                            }
                        }
                    } else {
                        notificaUsuario_TextView("Usuario e/ou senha inexistentes. Favor verificar!");
                    }

                } catch (Exception e) {
                    notificaUsuario_Toast(e.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    * Método para comparação entre objetos to tipo Profile
    *
    * @param: int a: valor do primeiro objeto
    *         int b: valor do segundo objeto
    *
    * @return: True/False
    */
    private boolean equalsProfile(int a, int b) {

        if (String.valueOf(a).equals(String.valueOf(b))){
            return true;
        } else {
            return false;
        }
    }

    /*
    * Método para notifcar usuario via TextView
    *
    * @param: String e: texto de mensagem de alerta
    *
    */
    public void notificaUsuario_TextView(String e) {
        messageTextView.setText(e);
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
}
