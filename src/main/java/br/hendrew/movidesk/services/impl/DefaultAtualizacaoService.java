package br.hendrew.movidesk.services.impl;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.google.inject.Inject;
import java.util.List;

import br.hendrew.movidesk.entity.Update;
import br.hendrew.movidesk.exception.MenssageNotFoundException;
import br.hendrew.movidesk.repository.AtualizacaoRepository;
import br.hendrew.movidesk.services.AtualizacaoService;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class DefaultAtualizacaoService implements AtualizacaoService {

    private final AtualizacaoRepository atualizacaoRepository;

    @Inject
    public DefaultAtualizacaoService(AtualizacaoRepository atualizacaoRepository) {
        this.atualizacaoRepository = atualizacaoRepository;

    }

    @Transactional
    @Override
    public Update updateAtualizacao(long id, Update update) throws MenssageNotFoundException {
        Update existing = getAtualizacaoById(id);

        existing.setDataFimAtualizacao(update.getDataFimAtualizacao());
        existing.setDataInicioAtualizacao(update.getDataInicioAtualizacao());
        existing.setErrorAtualizacao(update.getErrorAtualizacao());
        existing.setHoraFimAtualizacao(update.getHoraFimAtualizacao());
        existing.setHoraInicioAtualizacao(update.getHoraInicioAtualizacao());
        existing.setTipoAtualizacao(update.getTipoAtualizacao());

        return existing;
    }

    @Transactional
    @Override
    public Update saveAtualizacao(Update update) {
        atualizacaoRepository.persistAndFlush(update);
        return update;
    }

    @Override
    public Update getAtualizacaoById(long id) throws MenssageNotFoundException {
        return atualizacaoRepository.findByIdOptional(id)
                .orElseThrow(() -> new MenssageNotFoundException("There Atualizacao doesn't exist"));
    }

    @Override
    public Update getEofAtualizacao() {
        Update up = new Update();
        List<Update> list = new ArrayList<Update>();
        list = atualizacaoRepository.listAll(Sort.descending("id"));
        if (list.size() > 0) {
            for (int x = 0; x < list.size(); x++) {
                if ((list.get(x).getDataFimAtualizacao() != null) && (list.get(x).getErrorAtualizacao().equals("Não"))) {
                    up = list.get(x);
                    x = list.size();
                }
            }
        }
        return up;
    }
}
