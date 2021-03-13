package co.com.registropersonamovil2021;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.registropersonamovil2021.entity.Persona;
import co.com.registropersonamovil2021.persistencia.Connection;
import co.com.registropersonamovil2021.util.ActionBarUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegistroPersonaActivity extends AppCompatActivity {
    @BindView(R.id.txt_documento)
    EditText txtDocumento;

    @BindView(R.id.txt_nombre)
    EditText txtNombre;

    @BindView(R.id.txt_apellido)
    EditText txtApellido;
    Persona persona;
    Persona personaActualizar;
    boolean esInsert = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona);
        ButterKnife.bind(this);
        personaActualizar = (Persona) getIntent().getSerializableExtra("datosPersona");
        validarTipoRequest();
        validarCargaACampos();
        persona = new Persona();
        ActionBarUtil.getInstance(this, true).setToolBar(getString(R.string.registro_persona), getString(R.string.insertar));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            cargarInformacion();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void cargarInformacion() {
        validarInformacionACargar();
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPersonaActivity.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.confirm);
        if (esInsert) {
            builder.setMessage(R.string.confirm_message_guardar_informacion);
        } else {
            builder.setMessage(R.string.confirm_message_actualizar_informacion);
        }
        builder.setPositiveButton(R.string.confirm_action, (dialog, which) ->  insertarInformacion() );
        builder.setNegativeButton(R.string.cancelar, (dialog, which) ->  dialog.cancel() );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void validarTipoRequest() {
        esInsert = personaActualizar != null ? false : true;
    }

    private void validarCargaACampos() {
        if (!esInsert) {
            txtDocumento.setText(personaActualizar.getNumeroDocumentoIdentidad());
            txtNombre.setText(personaActualizar.getNombrePersona());
            txtApellido.setText(personaActualizar.getApellidoPersona());
        }
    }

    private void validarInformacionACargar() {
        if (esInsert) {
            persona.setNumeroDocumentoIdentidad(txtDocumento.getText().toString());
            persona.setNombrePersona(txtNombre.getText().toString());
            persona.setApellidoPersona(txtApellido.getText().toString());
        } else {
            personaActualizar.setNumeroDocumentoIdentidad(txtDocumento.getText().toString());
            personaActualizar.setNombrePersona(txtNombre.getText().toString());
            personaActualizar.setApellidoPersona(txtApellido.getText().toString());
        }
    }

    private void insertarInformacion() {
        Observable.fromCallable(()-> {
            if (esInsert) {
                Connection.getDb(getApplicationContext()).getPersonaDao().insert(persona);
                finish();
                return persona;
            } else {
                Connection.getDb(getApplicationContext()).getPersonaDao().update(personaActualizar);
                finish();
                return personaActualizar;
            }
        }).subscribeOn(Schedulers.computation()).subscribe();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /*class InsertarInformacion extends AsyncTask<Persona, Void, Void>{

        @Override
        protected Void doInBackground(Persona... personas) {
            Connection.getDb(getApplicationContext()).getPersonaDao().insert(personas[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            finish();
            super.onPostExecute(aVoid);
        }
    }*/


}