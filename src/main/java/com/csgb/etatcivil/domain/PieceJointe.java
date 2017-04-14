package com.csgb.etatcivil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PieceJointe.
 */
@Entity
@Table(name = "piece_jointe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "piecejointe")
public class PieceJointe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    @Column(name = "nom", nullable = false)
    private byte[] nom;

    @Column(name = "nom_content_type", nullable = false)
    private String nomContentType;

    @ManyToOne(optional = false)
    @NotNull
    private Naissance naissance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getNom() {
        return nom;
    }

    public PieceJointe nom(byte[] nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(byte[] nom) {
        this.nom = nom;
    }

    public String getNomContentType() {
        return nomContentType;
    }

    public PieceJointe nomContentType(String nomContentType) {
        this.nomContentType = nomContentType;
        return this;
    }

    public void setNomContentType(String nomContentType) {
        this.nomContentType = nomContentType;
    }

    public Naissance getNaissance() {
        return naissance;
    }

    public PieceJointe naissance(Naissance naissance) {
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
        PieceJointe pieceJointe = (PieceJointe) o;
        if (pieceJointe.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pieceJointe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PieceJointe{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", nomContentType='" + nomContentType + "'" +
            '}';
    }
}
