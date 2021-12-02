package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Jonathan Camacho
 */
@Entity
@Table(name = "animal", schema = "G2Lauserri")
public class AnimalEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAnimal;
    @Column
    private String nombreAnimal;
    @Column
    @Enumerated(EnumType.STRING)
    private TipoAnimal tipo;
    @Column
    @Enumerated(EnumType.STRING)
    private EstadoAnimal estado;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column
    @Enumerated(EnumType.STRING)
    private SexoAnimal sexo;
    @Column
    @ManyToOne
    private ZonaEntity zona;

    //constructor publico vacio
    public AnimalEntity() {
    }

    //constructor con parametros
    public AnimalEntity(Long idAnimal, String nombreAnimal, TipoAnimal tipo, EstadoAnimal estado, Date fechaNacimiento, SexoAnimal sexo, ZonaEntity zona) {
        this.idAnimal = idAnimal;
        this.nombreAnimal = nombreAnimal;
        this.tipo = tipo;
        this.estado = estado;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.zona = zona;
    }

    //getters and setters
    public Long getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNombreAnimal() {
        return nombreAnimal;
    }

    public void setNombreAnimal(String nombreAnimal) {
        this.nombreAnimal = nombreAnimal;
    }

    public TipoAnimal getTipo() {
        return tipo;
    }

    public void setTipo(TipoAnimal tipo) {
        this.tipo = tipo;
    }

    public EstadoAnimal getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnimal estado) {
        this.estado = estado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public SexoAnimal getSexo() {
        return sexo;
    }

    public void setSexo(SexoAnimal sexo) {
        this.sexo = sexo;
    }

    public ZonaEntity getZona() {
        return zona;
    }

    public void setZona(ZonaEntity zona) {
        this.zona = zona;
    }

    //metodos
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idAnimal);
        hash = 67 * hash + Objects.hashCode(this.nombreAnimal);
        hash = 67 * hash + Objects.hashCode(this.tipo);
        hash = 67 * hash + Objects.hashCode(this.estado);
        hash = 67 * hash + Objects.hashCode(this.fechaNacimiento);
        hash = 67 * hash + Objects.hashCode(this.sexo);
        hash = 67 * hash + Objects.hashCode(this.zona);
        return hash;
    }

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
        final AnimalEntity other = (AnimalEntity) obj;
        if (!Objects.equals(this.nombreAnimal, other.nombreAnimal)) {
            return false;
        }
        if (!Objects.equals(this.idAnimal, other.idAnimal)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.fechaNacimiento, other.fechaNacimiento)) {
            return false;
        }
        if (!Objects.equals(this.sexo, other.sexo)) {
            return false;
        }
        if (!Objects.equals(this.zona, other.zona)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Animal{" + "idAnimal=" + idAnimal + ", nombreAnimel=" + nombreAnimal + ", tipo=" + tipo + ", estado=" + estado + ", fechaNacimiento=" + fechaNacimiento + ", sexoAnimal=" + sexo + ", zona=" + zona + '}';
    }

}
