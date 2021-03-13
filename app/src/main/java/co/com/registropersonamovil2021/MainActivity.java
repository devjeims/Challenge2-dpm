package co.com.registropersonamovil2021;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.registropersonamovil2021.adapter.PersonaAdapter;
import co.com.registropersonamovil2021.entity.Persona;
import co.com.registropersonamovil2021.persistencia.Connection;
import co.com.registropersonamovil2021.util.ActionBarUtil;


public class MainActivity extends AppCompatActivity {

   private Connection connection;
   private List<Persona> listaPersonas;
   @BindView(R.id.listViewPersonas)
   ListView listViewPersonas;
   private PersonaAdapter personaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActionBarUtil.getInstance(this, false).setToolBar(getString(R.string.listado_persona));
        listaPersonas = new ArrayList<>();
        loadInformation();
    }

    private void loadInformation() {
        listaPersonas =  Connection.getDbMainThread(this).getPersonaDao().findAll();
        if(listaPersonas.isEmpty()){
            Toast.makeText(getApplicationContext(),R.string.sin_infomacion,Toast.LENGTH_LONG).show();
        }else{
            personaAdapter = new PersonaAdapter(this,listaPersonas);
            listViewPersonas.setAdapter(personaAdapter);
        }
    }

    public void goToRegistroPersona(View view) {
        Intent intent = new Intent(this,RegistroPersonaActivity.class);
        startActivity(intent);
    }

    public void eliminarListItemPersona(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.eliminar);
        builder.setMessage(R.string.confirm_message_delete_person);
        builder.setPositiveButton(R.string.confirm_action, (dialog, which) ->  eliminarPersona());
        builder.setNegativeButton(R.string.cancelar, (dialog, which) ->  dialog.cancel() );
        AlertDialog dialog = builder.create();
        dialog.show();
        loadInformation();
    }

    public void eliminarPersona() {
        try {
            Connection.getDb(this).getPersonaDao().delete(personaAdapter.getCurrentPerson());
            for (int i = 0; i < listaPersonas.size(); i++) {
                if (listaPersonas.get(i).getIdPersona() == personaAdapter.getCurrentPerson().getIdPersona()) {
                    listaPersonas.remove(i);
                }
            }
            Toast.makeText(this, R.string.persona_eliminada_exitosamente, Toast.LENGTH_LONG).show();
            personaAdapter.notifyDataSetChanged();
        } catch (Exception exception) {
            Toast.makeText(this, R.string.error_inesperado_eliminando_persona, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        loadInformation();
    }

}