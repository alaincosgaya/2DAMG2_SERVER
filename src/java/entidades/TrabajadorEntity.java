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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Alain Cosgaya
 */
@Entity
public class TrabajadorEntity extends UserEntity implements Serializable {

    private static final Long serialVersionUID = 1L;
    
    
    @Column
    private Long salario;
    @Column
    @OneToMany(cascade=ALL, mappedBy="")
    private List<ContratoEntity> contratos;
    @Column
    @ManyToMany()
    private List<ZonaEntity> zonas;

    public Long getSalario() {
        return salario;
    }

    public void setSalario(Long salario) {
        this.salario = salario;
    }

    public List<ContratoEntity> getContratos() {
        return contratos;
    }
    
    public void setGranjas(List<ContratoEntity> contratos) {
        this.contratos = contratos;
    }

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
        hash = 59 * hash + Objects.hashCode(this.salario);
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
        if (!Objects.equals(this.salario, other.salario)) {
            return false;
        }
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
        return "TrabajadorEntity{" + "salario=" + salario + ", contratos=" + contratos + ", zonas=" + zonas + '}';
    }
    
    

   
}
