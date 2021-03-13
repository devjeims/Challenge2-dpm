package co.com.registropersonamovil2021.persistencia.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.com.registropersonamovil2021.entity.Persona;

@Dao
public interface PersonaDAO {

    @Insert
    void insert(Persona persona);

    @Query("Select * from persona where idPersona =:idPersona ")
    Persona findByID(Integer idPersona);

    @Query("Select * from persona")
    List<Persona> findAll();

    @Update
    void update(Persona persona);

    @Delete
    void delete(Persona persona);

    @Query("delete from persona where idPersona =:idPersona ")
    void delete(Integer idPersona);

}
