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
import android.widget.Button;
import android.widget.EditText;
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

public class EditUserPass extends AppCompatActivity {

    private String uName;
    private String uPass;
    private String nPass;

    Button editUserItemButton;
    Button returnToPrincipalButton;

    EditText nameUserEditText;
    EditText passUserEditText;
    EditText newPassUserEditText;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_pass);

        nameUserEditText = (EditText) findViewById(R.id.nameUserEditText);
        passUserEditText = (EditText) findViewById(R.id.passUserEditText);
        newPassUserEditText = (EditText) findViewById(R.id.newPassUserEditText);

        editUserItemButton = (Button) findViewById(R.id.editUserItemButton);
        returnToPrincipalButton = (Button) findViewById(R.id.returnToPrincipalButton);

        inicializarFirebase();

        editUserItemButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarPreenchimento_TextView();
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
     * Método para inicialização com a database
     */
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(EditUserPass.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /*
     * Método de verificação dos campos TextView da activitie
     */
    private void validarPreenchimento_TextView() {

        String user = nameUserEditText.getText().toString();
        String pass = passUserEditText.getText().toString();
        String newPass = newPassUserEditText.getText().toString();

        if (user.trim().equals("")) {
            notificaUsuario_Toast("Um 'Usuário' se faz necessário.");
        } else {
            if (pass.trim().equals("")) {
                notificaUsuario_Toast("Uma 'Senha' se faz necessária.");
            } else {
                if (newPass.trim().equals("")) {
                    notificaUsuario_Toast("Uma nova 'Senha' se faz necessária.");
                } else {
                    processaAtualizacaoDeSenha(user, pass, newPass);
                }
            }
        }
    }

    /*
    * Método de processamento da autenticação do usuario
    *
    * @param: String usuario: recebe o nome do usuario
    *         String senha: recebe o valor da senha de usuario a ser validada
    *         String novasenha: recebe como entrada a nova senha
    */
    private void processaAtualizacaoDeSenha(String usuario, String senha, String novasenha) {

        Query query = null;

        uName = usuario;
        uPass = senha;
        nPass = novasenha;

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

                                    int _Id = u.getId();
                                    String _Name = u.getNome();
                                    int _Profile = u.getPerfil();

                                    Usuarios nu = new Usuarios();

                                    nu.setId(_Id);
                                    nu.setNome(_Name);
                                    nu.setPerfil(_Profile);
                                    nu.setSenha(nPass);

                                    databaseReference.child("Usuarios").child(String.valueOf(nu.getId())).setValue(nu);

                                    notificaUsuario_Toast("Senha atualizada com sucesso!");

                                    returnToMainActivity();
                                } else {
                                    notificaUsuario_Toast("Senha atual inválida. Favor verificar!");

                                    break;
                                }
                            } else {
                                notificaUsuario_Toast("Usuario inválidos. Favor verificar!");

                                break;
                            }
                        }
                    } else {
                        notificaUsuario_Toast("Usuario e/ou senha inválidos. Favor verificar!");
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
        Intent intent = new Intent(EditUserPass.this, MenuUser.class);
        startActivity(intent);
        finish();
    }
}
