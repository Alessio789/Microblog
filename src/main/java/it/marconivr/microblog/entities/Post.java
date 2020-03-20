package it.marconivr.microblog.entities;

import java.util.Date;

import javax.persistence.*;

import lombok.*;

/**
 * 
 * Classe Entity Post
 * 
 * @author Alessio Trentin - 5^EI
 * @version 2.1.0 - 19/03/2020
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    @Column(nullable = false)
    public Date dataOra;

    @Basic
    @Getter @Setter
    @Column(unique = true, nullable = false)
    public String titolo;

    @Lob
    @Getter @Setter
    @Column(nullable = false)
    public String contenuto;

    @ManyToOne(targetEntity = Utente.class)
    @Getter @Setter
    @JoinColumn(name = "UTENTE_ID", nullable = false)
    private Utente utente;

    /**
     * 
     * Constructor 
     * 
     * @param id
     * @param dataOra
     * @param titolo
     * @param contenuto
     * @param utente
     */
    public Post(long id, Date dataOra, String titolo, String contenuto, Utente utente) {
        this.id = id;
        this.dataOra = dataOra;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.utente = utente;
    }

    /**
     * 
     * Constructor
     */
    public Post() {
    }
}