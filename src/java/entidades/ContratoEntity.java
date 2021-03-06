/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad de la clase de Contrato
 * @author Jonathan Camacho y Alain Cosgaya
 */


// Queries de Contratos
@NamedQueries({
    @NamedQuery(
            name="despedirTrabajador", query="DELETE FROM ContratoEntity c "
                    + "WHERE c.idContrato.trabajadorId=:idTrabajador AND c.idContrato.granjaId=:idGranja"
    ),
    @NamedQuery(
	name="cambiarSueldo", query="UPDATE ContratoEntity c SET c.salario=:salario "
                + "WHERE c.idContrato.trabajadorId=:idTrabajador AND c.idContrato.granjaId=:idGranja"
    ),
    @NamedQuery(
            name="contratoTrabajador", query="SELECT c FROM ContratoEntity c "
                    + "WHERE c.idContrato.granjaId=:idGranja AND c.idContrato.trabajadorId=:idTrabajador"
    ),
    @NamedQuery(
            name = "contratosGranjero", query = "SELECT c From ContratoEntity c "
            + "WHERE c.idContrato.granjaId IN (SELECT g2 From GranjeroEntity g JOIN g.granjas g2 WHERE g.id=:idGranjero)"
    ),
    @NamedQuery(
            name="contratosTrabajador", query="SELECT c FROM ContratoEntity c "
                    + "WHERE c.idContrato.trabajadorId=:idTrabajador"
    ),
    @NamedQuery(
            name="contratosGranja", query="SELECT c FROM ContratoEntity c WHERE "
                    + " c.idContrato.granjaId=:idGranja"
    )
   
})
@Entity
@Table(name="contrato", schema="G2Lauserri")
@XmlRootElement
public class ContratoEntity implements Serializable {
  
    private static final long serialVersionUID = 1L;
    // Atributos de la entidad
    @EmbeddedId
    private ContratoId idContrato;
    @MapsId("trabajadorId")
    @ManyToOne
    private TrabajadorEntity trabajador; 
    @MapsId("granjaId")
    @ManyToOne
    private GranjaEntity granja;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaContratacion;
    
    private Long salario;
    // Getters y Setters
    public Long getSalario() {
        return salario;
    }

    public void setSalario(Long salario) {
        this.salario = salario;
    }
    
    public ContratoEntity(){
        
    }

    public ContratoId getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratoId idContrato) {
        this.idContrato = idContrato;
    }

    public TrabajadorEntity getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(TrabajadorEntity trabajador) {
        this.trabajador = trabajador;
    }

    public GranjaEntity getGranja() {
        return granja;
    }

    public void setGranja(GranjaEntity granja) {
        this.granja = granja;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
    
    // HashCode
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.idContrato);
        hash = 67 * hash + Objects.hashCode(this.trabajador);
        hash = 67 * hash + Objects.hashCode(this.granja);
        hash = 67 * hash + Objects.hashCode(this.fechaContratacion);
        hash = 67 * hash + Objects.hashCode(this.salario);
        return hash;
    }
    
    // Metodo equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContratoEntity other = (ContratoEntity) obj;
        if (!Objects.equals(this.idContrato, other.idContrato)) {
            return false;
        }
        if (!Objects.equals(this.trabajador, other.trabajador)) {
            return false;
        }
        if (!Objects.equals(this.granja, other.granja)) {
            return false;
        }
        if (!Objects.equals(this.fechaContratacion, other.fechaContratacion)) {
            return false;
        }
        if (!Objects.equals(this.salario, other.salario)) {
            return false;
        }
        return true;
    }
    
    // Metodo toString
    @Override
    public String toString() {
        return "ContratoEntity{" + "idContrato=" + idContrato + ", trabajador=" + trabajador + ", granja=" + granja + ", fechaContratacion=" + fechaContratacion + ", salario=" + salario + '}';
    }

   

    
    
}