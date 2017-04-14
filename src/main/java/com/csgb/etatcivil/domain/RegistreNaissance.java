package com.csgb.etatcivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A RegistreNaissance.
 */
@Entity
@Table(name = "registre_naissance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "registrenaissance")
public class RegistreNaissance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @NotNull
    @Column(name = "annee", nullable = false)
    private LocalDate annee;

    @ManyToOne(optional = false)
    @NotNull
    private Naissance naissance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public RegistreNaissance numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getAnnee() {
        return annee;
    }

    public RegistreNaissance annee(LocalDate annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(LocalDate annee) {
        this.annee = annee;
    }

    public Naissance getNaissance() {
        return naissance;
    }

    public RegistreNaissance naissance(Naissance naissance) {
        this.naissance = naissance;
        return this;
    }

    public void setNaissance(Naissance naissance) {
        this.naissance = naissance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistreNaissance registreNaissance = (RegistreNaissance) o;
        if (registreNaissance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, registreNaissance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RegistreNaissance{" +
            "id=" + id +
            ", numero='" + numero + "'" +
            ", annee='" + annee + "'" +
            '}';
    }
}
