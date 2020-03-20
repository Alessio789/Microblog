package it.marconivr.microblog.repos;

import org.springframework.data.repository.CrudRepository;

import it.marconivr.microblog.entities.Utente;

/**
 * 
 * Repository Utente
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 15/03/2020
 */
public interface IUtenteRepo extends CrudRepository<Utente, String> {

    Utente findByUsername(String username);
}