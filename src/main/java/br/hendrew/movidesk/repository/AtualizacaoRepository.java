package br.hendrew.movidesk.repository;

import javax.enterprise.context.ApplicationScoped;

import br.hendrew.movidesk.entity.Update;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class AtualizacaoRepository implements PanacheRepository<Update>{
   
}
