package com.DyVert.DyVert.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

/**
 *
 * @author sergioguerra
 */
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String accountID;
    private String accountType;
    private int lastCardViewed;
    private String password;
    
    public User(String accountID, String accountType, String password) {
        this.accountID = accountID;
        this.accountType = accountType;
        this.password = password;
    }
}