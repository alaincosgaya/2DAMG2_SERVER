package entidades;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Entidad de la clase User.
 * @author Alain Cosgaya
 */
@Entity
@Table(name="user",schema="G2Lauserri")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity implements Serializable{
    
    private static final Long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String username;
    @Column
    private String email;
    @Column
    private String fullName;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private UserStatusType UserStatus;
    @Column
    @Enumerated(EnumType.STRING)
    private UserPrivilegeType UserPrivilege;
    @Column
    private LocalDateTime lastPasswordChange;
    
    /**
     * Constructor vacio de la entidad User.
     */
   
    public UserEntity() {
        
    }

    /**
     * Recoger id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * set id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * recoge username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * set email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Recoge fullName
     * @return
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * set fullName
     * @param fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Recoge password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * recoge userStatus
     * @return userStatus
     */
    public UserStatusType getUserStatus() {
        return UserStatus;
    }

    /**
     * set userStatus
     * @param UserStatus
     */
    public void setUserStatus(UserStatusType UserStatus) {
        this.UserStatus = UserStatus;
    }

    /**
     * recoge userPrivilege
     * @return
     */
    public UserPrivilegeType getUserPrivilege() {
        return UserPrivilege;
    }

    /**
     * set userPrivilege
     * @param UserPrivilege
     */
    public void setUserPrivilege(UserPrivilegeType UserPrivilege) {
        this.UserPrivilege = UserPrivilege;
    }

    /**
     * recoge lastPasswordChange
     * @return lastPasswordChange
     */
    public LocalDateTime getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * set lastPasswordChange
     * @param lastPasswordChange
     */
    public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    /**
     * HashCode
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 17 * hash + Objects.hashCode(this.username);
        hash = 17 * hash + Objects.hashCode(this.email);
        hash = 17 * hash + Objects.hashCode(this.fullName);
        hash = 17 * hash + Objects.hashCode(this.password);
        hash = 17 * hash + Objects.hashCode(this.UserStatus);
        hash = 17 * hash + Objects.hashCode(this.UserPrivilege);
        hash = 17 * hash + Objects.hashCode(this.lastPasswordChange);
        return hash;
    }

    /**
     * Metodo para comparacion de entidades
     * @param obj
     * @return boolean con si es igual o no
     */
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
        final UserEntity other = (UserEntity) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (this.UserStatus != other.UserStatus) {
            return false;
        }
        if (this.UserPrivilege != other.UserPrivilege) {
            return false;
        }
        if (!Objects.equals(this.lastPasswordChange, other.lastPasswordChange)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString con datos de la entidad
     * @return datos de la entidad
     */
    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", username=" + username + ", email=" + email + ", fullName=" + fullName + ", password=" + password + ", UserStatus=" + UserStatus + ", UserPrivilege=" + UserPrivilege + ", lastPasswordChange=" + lastPasswordChange + '}';
    }
    
}
