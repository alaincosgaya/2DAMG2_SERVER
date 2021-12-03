package entidades;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Idoia Ormaetxea
 */
@Entity
@Table(name="zona", schema = "G2Lauserri")
public class ZonaEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idZona;
    private String nombreZona;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacionZona;
    @ManyToOne
    private GranjaEntity granja;
    @OneToMany(cascade=ALL, mappedBy="zona")
    private List<AnimalEntity> animales;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="trabajador-zona", schema="G2Lauserri", 
            joinColumns = @JoinColumn(name="idZona", referencedColumnName="idZona"),
            inverseJoinColumns = @JoinColumn(name="idTrabajador", referencedColumnName="id"))
    private List<TrabajadorEntity> trabajadores;
    
    public ZonaEntity(){
        
    }

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public Date getFechaCreacionZona() {
        return fechaCreacionZona;
    }

    public void setFechaCreacionZona(Date fechaCreacionZona) {
        this.fechaCreacionZona = fechaCreacionZona;
    }

    public GranjaEntity getGranja() {
        return granja;
    }

    public void setGranja(GranjaEntity granja) {
        this.granja = granja;
    }

    public List<AnimalEntity> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalEntity> animales) {
        this.animales = animales;
    }

    public List<TrabajadorEntity> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(List<TrabajadorEntity> trabajadores) {
        this.trabajadores = trabajadores;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.idZona);
        hash = 29 * hash + Objects.hashCode(this.nombreZona);
        hash = 29 * hash + Objects.hashCode(this.fechaCreacionZona);
        hash = 29 * hash + Objects.hashCode(this.granja);
        hash = 29 * hash + Objects.hashCode(this.animales);
        hash = 29 * hash + Objects.hashCode(this.trabajadores);
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
        final ZonaEntity other = (ZonaEntity) obj;
        if (!Objects.equals(this.nombreZona, other.nombreZona)) {
            return false;
        }
        if (!Objects.equals(this.idZona, other.idZona)) {
            return false;
        }
        if (!Objects.equals(this.fechaCreacionZona, other.fechaCreacionZona)) {
            return false;
        }
        if (!Objects.equals(this.granja, other.granja)) {
            return false;
        }
        if (!Objects.equals(this.animales, other.animales)) {
            return false;
        }
        if (!Objects.equals(this.trabajadores, other.trabajadores)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Zona{" + "idZona=" + idZona + ", nombreZona=" + nombreZona + ", fechaCreacionZona=" + fechaCreacionZona + ", granja=" + granja + ", animales=" + animales + ", trabajadores=" + trabajadores + '}';
    }
    
    
}
