package com.jwt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "USER_DETAILS", uniqueConstraints = { @UniqueConstraint(columnNames = "USER_NAME"), @UniqueConstraint(columnNames = "email") })
public class User {

    public User(User userDetail) {
        // TODO Auto-generated constructor stub
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;
    @Column(name = "USER_NAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // @JsonManagedReference
    @JsonBackReference
    // @JoinColumn(name = "USER_ID")
    // @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    // @JoinTable(name = "BIDDER_CREDIT_CARDS", joinColumns = { @JoinColumn(name = "BIDDER_ID") }, inverseJoinColumns = { @JoinColumn(name = "CREDIT_CARD_ID") })
    private List<Role> roles;

}
