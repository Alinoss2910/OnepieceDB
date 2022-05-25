/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alinoss.onepiecedb.entities;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author airam_fckw728
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "PIRATA")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Pirata.findAll", query = "SELECT p FROM Pirata p"),
    @javax.persistence.NamedQuery(name = "Pirata.findById", query = "SELECT p FROM Pirata p WHERE p.id = :id"),
    @javax.persistence.NamedQuery(name = "Pirata.findByNombre", query = "SELECT p FROM Pirata p WHERE p.nombre = :nombre"),
    @javax.persistence.NamedQuery(name = "Pirata.findByBanda", query = "SELECT p FROM Pirata p WHERE p.banda = :banda"),
    @javax.persistence.NamedQuery(name = "Pirata.findByRol", query = "SELECT p FROM Pirata p WHERE p.rol = :rol"),
    @javax.persistence.NamedQuery(name = "Pirata.findByEdad", query = "SELECT p FROM Pirata p WHERE p.edad = :edad"),
    @javax.persistence.NamedQuery(name = "Pirata.findByRecompensa", query = "SELECT p FROM Pirata p WHERE p.recompensa = :recompensa"),
    @javax.persistence.NamedQuery(name = "Pirata.findByFruta", query = "SELECT p FROM Pirata p WHERE p.fruta = :fruta"),
    @javax.persistence.NamedQuery(name = "Pirata.findByNombreFruta", query = "SELECT p FROM Pirata p WHERE p.nombreFruta = :nombreFruta"),
    @javax.persistence.NamedQuery(name = "Pirata.findByFoto", query = "SELECT p FROM Pirata p WHERE p.foto = :foto")})
public class Pirata implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE")
    private String nombre;
    @javax.persistence.Column(name = "BANDA")
    private String banda;
    @javax.persistence.Column(name = "ROL")
    private String rol;
    @javax.persistence.Column(name = "EDAD")
    private Integer edad;
    @javax.persistence.Column(name = "RECOMPENSA")
    private BigInteger recompensa;
    @javax.persistence.Column(name = "FRUTA")
    private Boolean fruta;
    @javax.persistence.Column(name = "NOMBRE_FRUTA")
    private String nombreFruta;
    @javax.persistence.Column(name = "FOTO")
    private String foto;
    @javax.persistence.JoinColumn(name = "BARCO", referencedColumnName = "ID")
    @javax.persistence.ManyToOne
    private Barco barco;

    public Pirata() {
    }

    public Pirata(Integer id) {
        this.id = id;
    }

    public Pirata(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public BigInteger getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(BigInteger recompensa) {
        this.recompensa = recompensa;
    }

    public Boolean getFruta() {
        return fruta;
    }

    public void setFruta(Boolean fruta) {
        this.fruta = fruta;
    }

    public String getNombreFruta() {
        return nombreFruta;
    }

    public void setNombreFruta(String nombreFruta) {
        this.nombreFruta = nombreFruta;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Barco getBarco() {
        return barco;
    }

    public void setBarco(Barco barco) {
        this.barco = barco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pirata)) {
            return false;
        }
        Pirata other = (Pirata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alinoss.onepiecedb.entities.Pirata[ id=" + id + " ]";
    }
    
}
