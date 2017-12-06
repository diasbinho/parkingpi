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

import java.util.Objects;
import java.util.UUID;

import br.uam.cco.mob.app.parkingpi.Modelo.Estacionamentos;
import br.uam.cco.mob.app.parkingpi.R;

public class InsertParking extends AppCompatActivity {

    private int lastItem = 1;

    EditText nameParkingEditText;
    EditText addressParkingEditText;
    EditText recordParkingEditText;
    EditText hourOpeningParkingEditText;
    EditText hourClosureParkingEditText;

    Button insertParkingItemButton;
    Button returnToPrincipalButton;

    private int hora, minutos;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_parking);

        inicializarFirebase();

        processaLastId();

        nameParkingEditText = (EditText) findViewById(R.id.nameParkingEditText);
        addressParkingEditText = (EditText) findViewById(R.id.addressParkingEditText);
        recordParkingEditText = (EditText) findViewById(R.id.recordParkingEditText);
        nameParkingEditText = (EditText) findViewById(R.id.nameParkingEditText);
        hourOpeningParkingEditText = (EditText) findViewById(R.id.hourOpeningParkingEditText);
        hourClosureParkingEditText = (EditText) findViewById(R.id.hourClosureParkingEditText);

        insertParkingItemButton = (Button) findViewById(R.id.insertParkingItemButton);
        returnToPrincipalButton = (Button) findViewById(R.id.returnToPrincipalButton);

        /*
        hourOpeningParkingEditText.setOnFocusChangeListener(new EditText.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == true) {

                    final Calendar c = Calendar.getInstance();

                    hora = c.get(Calendar.HOUR_OF_DAY);
                    minutos = c.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            String msg = hourOfDay + ":" + minute;
                            //ehora.setText(hourOfDay + ":" + minute);
                            this.onTimeSet(msg.toString());
                        }
                    }, hora, minutos, false);
                    timePickerDialog.show();
                }


            }
        });
        */

        insertParkingItemButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                processaLastId();
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
     * Método para inicialização com a database
     */
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(InsertParking.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /*
    * Método de processamento de inserção de usuario
    *
    */
    private void insertNewItem() {
        try {
            Estacionamentos e = new Estacionamentos();

            //e.setId(UUID.randomUUID().toString());
            e.setId(lastItem);
            e.setNome(nameParkingEditText.getText().toString());
            e.setEndereco(addressParkingEditText.getText().toString());
            e.setCnpj(recordParkingEditText.getText().toString());
            e.setHora_abertura(hourOpeningParkingEditText.getText().toString());
            e.setHora_fechamento(hourClosureParkingEditText.getText().toString());


            databaseReference.child("Estacionamentos").child(String.valueOf(e.getId())).setValue(e);

            clearActivity();

            notificaUsuario_Toast("Estacionamento cadastrado com sucesso!");

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

        query = databaseReference.child("Estacionamentos");

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
        Intent intent = new Intent(InsertParking.this, MenuAdmin.class);
        startActivity(intent);
        finish();
    }

    /*
    * Método para limpar campos da Activitie
    */
    private void clearActivity() {
        nameParkingEditText.setText("");
        addressParkingEditText.setText("");
        recordParkingEditText.setText("");
        hourOpeningParkingEditText.setText("");
        hourClosureParkingEditText.setText("");
    }
}
