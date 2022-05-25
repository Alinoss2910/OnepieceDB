/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alinoss.onepiecedb.entities;

import alinoss.onepiecedb.entities.Pirata;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author airam_fckw728
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "BARCO")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Barco.findAll", query = "SELECT b FROM Barco b"),
    @javax.persistence.NamedQuery(name = "Barco.findById", query = "SELECT b FROM Barco b WHERE b.id = :id"),
    @javax.persistence.NamedQuery(name = "Barco.findByCodigo", query = "SELECT b FROM Barco b WHERE b.codigo = :codigo"),
    @javax.persistence.NamedQuery(name = "Barco.findByNombre", query = "SELECT b FROM Barco b WHERE b.nombre = :nombre")})
public class Barco implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Column(name = "CODIGO")
    private String codigo;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE")
    private String nombre;
    @javax.persistence.OneToMany(mappedBy = "barco")
    private Collection<Pirata> pirataCollection;

    public Barco() {
    }

    public Barco(Integer id) {
        this.id = id;
    }

    public Barco(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Pirata> getPirataCollection() {
        return pirataCollection;
    }

    public void setPirataCollection(Collection<Pirata> pirataCollection) {
        this.pirataCollection = pirataCollection;
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
        if (!(object instanceof Barco)) {
            return false;
        }
        Barco other = (Barco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alinoss.onepiecedb.entities.Barco[ id=" + id + " ]";
    }
    
}
