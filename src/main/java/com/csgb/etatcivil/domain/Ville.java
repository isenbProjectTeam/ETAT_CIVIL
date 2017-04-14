package com.csgb.etatcivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ville.
 */
@Entity
@Table(name = "ville")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ville")
public class Ville implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "code_postal", nullable = false)
    private Integer codePostal;

    @ManyToOne(optional = false)
    @NotNull
    private Pays pays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Ville nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public Ville codePostal(Integer codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public Pays getPays() {
        return pays;
    }

    public Ville pays(Pays pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ville ville = (Ville) o;
        if (ville.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ville.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ville{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", codePostal='" + codePostal + "'" +
            '}';
    }
}
