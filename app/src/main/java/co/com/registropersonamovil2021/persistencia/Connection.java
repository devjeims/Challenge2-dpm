package co.com.registropersonamovil2021.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import co.com.registropersonamovil2021.entity.Persona;
import co.com.registropersonamovil2021.persistencia.dao.PersonaDAO;

@Database(entities = {Persona.class}, version = RoomConfig.Parametro.VERSION, exportSchema = false)
public abstract class Connection extends RoomDatabase {

    private static Connection instance;

    public static Connection getDb(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,Connection.class,RoomConfig.Parametro.NOMBRE_BASE_DATOS).build();
        }
        return instance;
    }

    public static Connection getDbMainThread(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,Connection.class,RoomConfig.Parametro.NOMBRE_BASE_DATOS).allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract PersonaDAO getPersonaDao();
}
