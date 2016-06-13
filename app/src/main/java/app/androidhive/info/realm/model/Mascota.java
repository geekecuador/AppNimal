package app.androidhive.info.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by davidpulloquinga on 8/6/16.
 */

public class Mascota extends RealmObject {
    @PrimaryKey
    private String nombre;
    private String especie;
    private String raza;
    private String fecha_nacimiento;
    private String color;
    private String peso;
    private String ImageUrl;

    private String fechaVacuna;
    private String imunizacion;
    private String medico_veterinario;
    private String proximaVacuna;

    private String fechaDesparacitacion;
    private String tratamiento;
    private String proximaDesparacitacion;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getFechaVacuna() {
        return fechaVacuna;
    }

    public void setFechaVacuna(String fechaVacuna) {
        this.fechaVacuna = fechaVacuna;
    }

    public String getImunizacion() {
        return imunizacion;
    }

    public void setImunizacion(String imunizacion) {
        this.imunizacion = imunizacion;
    }

    public String getMedico_veterinario() {
        return medico_veterinario;
    }

    public void setMedico_veterinario(String medico_veterinario) {
        this.medico_veterinario = medico_veterinario;
    }

    public String getProximaVacuna() {
        return proximaVacuna;
    }

    public void setProximaVacuna(String proximaVacuna) {
        this.proximaVacuna = proximaVacuna;
    }

    public String getFechaDesparacitacion() {
        return fechaDesparacitacion;
    }

    public void setFechaDesparacitacion(String fechaDesparacitacion) {
        this.fechaDesparacitacion = fechaDesparacitacion;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getProximaDesparacitacion() {
        return proximaDesparacitacion;
    }

    public void setProximaDesparacitacion(String proximaDesparacitacion) {
        this.proximaDesparacitacion = proximaDesparacitacion;
    }
}
