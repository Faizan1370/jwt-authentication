package com.jwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ROLE_ID")
    private Long id;
    @Column(name = "ROLE_NAME")
    @Enumerated(EnumType.STRING)
    private ERole roleName;
    /*
     * @Column(name = "user_id") private Long userId;
     */
    @ManyToOne(fetch = FetchType.EAGER)
    // @JsonBackReference
    @JsonManagedReference
    private User user;

    /*
     * @ManyToOne(cascade = CascadeType.ALL)
     *
     * @JoinColumn(name = "USER_ID") private UserDetails userDetail;
     */

}
