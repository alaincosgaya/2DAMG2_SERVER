/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;
    
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alain Cosgaya
 */
@NamedQueries({
    @NamedQuery(
            name="trabajadoresParaContratar", query="SELECT t FROM TrabajadorEntity t WHERE t.id not in "
            + "(SELECT c.trabajador.id FROM ContratoEntity c WHERE c.idContrato.granjaId=:granjaId)"
    ),
    @NamedQuery(
            name="trabajadoresGranja", query="SELECT t FROM TrabajadorEntity t WHERE t.id in "
            + "(SELECT c.trabajador.id FROM ContratoEntity c WHERE c.idContrato.granjaId=:granjaId)"
    ),
    @NamedQuery(
            name="trabajadoresZona", query="SELECT t FROM TrabajadorEntity t WHERE t.id in "
            + "(SELECT t2 FROM ZonaEntity z JOIN z.trabajadores t2 WHERE z.idZona=:zonaId)"
    ),
    @NamedQuery(
            name="trabajadoresPorAsignarZona",query="SELECT t FROM TrabajadorEntity t WHERE t.id NOT IN "
            + "(SELECT t2 FROM ZonaEntity z JOIN z.trabajadores t2 WHERE z.idZona=:zonaId) AND t.id IN "
            + "(SELECT c.trabajador.id FROM ContratoEntity c WHERE c.idContrato.granjaId=:granjaId)"
    )
    
   
        
    
           
})
@Entity
@Table(name="trabajador",schema="G2Lauserri")
@DiscriminatorValue(value="TRABAJADOR")
@XmlRootElement
public class TrabajadorEntity extends UserEntity implements Serializable {

    private static final Long serialVersionUID = 1L;
    
    
    @OneToMany(cascade=ALL, mappedBy="trabajador")
    private List<ContratoEntity> contratos; 
    
    @ManyToMany(mappedBy="trabajadores",fetch = FetchType.EAGER, cascade=ALL)
    private List<ZonaEntity> zonas;


    public List<ContratoEntity> getContratos() {
        return contratos;
    }
    
    public void setGranjas(List<ContratoEntity> contratos) {
        this.contratos = contratos;
    }

    @XmlTransient
    public List<ZonaEntity> getZonas() {
        return zonas;
    }

    public void setZonas(List<ZonaEntity> zonas) {
        this.zonas = zonas;
    }

    public TrabajadorEntity() {
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.contratos);
        hash = 59 * hash + Objects.hashCode(this.zonas);
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
        final TrabajadorEntity other = (TrabajadorEntity) obj;
        
        if (!Objects.equals(this.contratos, other.contratos)) {
            return false;
        }
        if (!Objects.equals(this.zonas, other.zonas)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TrabajadorEntity{" + "contratos=" + contratos + ", zonas=" + zonas + '}';
    }
    
    

   
}
