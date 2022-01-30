package br.hendrew.movidesk.services;

import br.hendrew.movidesk.entity.Update;
import br.hendrew.movidesk.exception.MenssageNotFoundException;

public interface AtualizacaoService {

    Update updateAtualizacao(long id, Update update) throws MenssageNotFoundException;

    Update saveAtualizacao(Update update);

    Update getAtualizacaoById(long id) throws MenssageNotFoundException;

    Update getEofAtualizacao();
}
