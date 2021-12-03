/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Idoia Ormaetxea
 */
@Entity
@Table(name="granjero", schema="G2Lauserri")
public class GranjeroEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<GranjaEntity> granjas;
    
    public GranjeroEntity (){
    
    }

    public List<GranjaEntity> getGranjas() {
        return granjas;
    }

    public void setGranjas(List<GranjaEntity> granjas) {
        this.granjas = granjas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.granjas);
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
        final GranjeroEntity other = (GranjeroEntity) obj;
        if (!Objects.equals(this.granjas, other.granjas)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GranjeroEntity{" + "granjas=" + granjas + '}';
    }
    
}
