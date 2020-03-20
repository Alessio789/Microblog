package it.marconivr.microblog.entities;

import java.util.Date;

import javax.persistence.*;

import lombok.*;

/**
 * 
 * Classe Entity Commento
 * 
 * @author Alessio Trentin - 5^EI
 * @version 2.1.0 - 19/03/2020
 */
@Entity
public class Commento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter 
    private Long id;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter 
    @Column(nullable = false)
    private Date dataOra;

    @Basic
    @Getter @Setter 
    @Column(nullable = false)
    private String contenuto;

    @OneToOne(targetEntity = Post.class)
    @Getter @Setter
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

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
     * @param contenuto
     * @param post
     * @param utente
     */
    public Commento(Long id, Date dataOra, String contenuto, Post post, Utente utente) {
        this.id = id;
        this.dataOra = dataOra;
        this.contenuto = contenuto;
        this.post = post;
        this.utente = utente;
    }

    /**
     * 
     * Constructor
     */
    public Commento() {
    }
}
