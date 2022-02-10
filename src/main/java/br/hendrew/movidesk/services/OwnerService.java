package br.hendrew.movidesk.services;

import java.util.List;

import br.hendrew.movidesk.entity.Owner;
import br.hendrew.movidesk.exception.MenssageNotFoundException;

public interface OwnerService {
    
    Owner getOwnerById(long id) throws MenssageNotFoundException;

    Owner getOwnerByStringId(String id) throws MenssageNotFoundException;

    //List<Tickets> getOwnerByNome(String nome) throws MenssageNotFoundException;

    List<Owner> getAllOwner();

    Owner updateOwner(String id, Owner owner) throws MenssageNotFoundException;

    Owner saveOwner(Owner owner);

    void deleteOwner(String id) throws MenssageNotFoundException;

    long countOwner();

    List<Owner> getOwnerPage(int pag, int quant) throws MenssageNotFoundException; 
}
