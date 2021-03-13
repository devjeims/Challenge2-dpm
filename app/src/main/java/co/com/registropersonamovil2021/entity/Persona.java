package co.com.registropersonamovil2021.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import co.com.registropersonamovil2021.persistencia.RoomConfig;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Synchronized;

@Data
@Entity(tableName = RoomConfig.Tabla.PERSONA)
@NoArgsConstructor
public class Persona implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="idPersona")
    private Integer idPersona;
    @ColumnInfo(name="numeroDocumentoIdentidad")
    private String numeroDocumentoIdentidad;
    @ColumnInfo(name="nombrePersona")
    private String nombrePersona;
    @ColumnInfo(name="apellidoPersona")
    private String apellidoPersona;

}
