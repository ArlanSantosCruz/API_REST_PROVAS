package br.com.dbug.questlab.service;

import br.com.dbug.questlab.repository.UsuarioRepository;
import br.com.dbug.questlab.repository.UsuarioRespostaRepository;
import br.com.dbug.questlab.repository.projection.ContagemUsuariosProjection;
import br.com.dbug.questlab.repository.projection.ContagemRespostasProjection;
import br.com.dbug.questlab.rest.dto.filter.RelatorioParticipacaoFilterDTO;
import br.com.dbug.questlab.rest.dto.response.RelatorioParticipacaoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RelatorioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRespostaRepository usuarioRespostaRepository;

    @Transactional(readOnly = true)
    public RelatorioParticipacaoDTO relatorioParticipacaoPlataforma(RelatorioParticipacaoFilterDTO filter) {
        log.info("Gerando relatório de participação para {}/{}", filter.getMes(), filter.getAno());

        //Buscar dados do período atual
        ContagemUsuariosProjection usuarios = usuarioRepository.contarUsuarios();
        ContagemRespostasProjection respostasAtual = usuarioRespostaRepository
                .contarRespostasPorPeriodo(filter.getMes(), filter.getAno());

        // Buscar dados do mês anterior para calcular crescimento
        Integer mesAnterior = filter.getMes() - 1;
        Integer anoAnterior = filter.getAno();

        if (mesAnterior < 1) {
            mesAnterior = 12;
            anoAnterior = filter.getAno() - 1;
        }

        ContagemRespostasProjection respostasMesAnterior = usuarioRespostaRepository
                .contarRespostasMesAnterior(mesAnterior, anoAnterior);

        // Criar DTO com dados brutos
        RelatorioParticipacaoDTO relatorio = new RelatorioParticipacaoDTO(
                usuarios.getTotalUsuarios(),
                usuarios.getTotalUsuariosAtivos(),
                respostasAtual.getTotalQuestoesRespondidas()
        );

        //Crescimento percentual
        Double crescimento = calcularCrescimentoPercentual(
                respostasMesAnterior.getTotalQuestoesRespondidas(),
                respostasAtual.getTotalQuestoesRespondidas()
        );
        relatorio.setCrescimentoPercentual(crescimento);

        return relatorio;
    }

    //Cálculo de crescimento percentual
    private Double calcularCrescimentoPercentual(Long valorAnterior, Long valorAtual) {
        if (valorAnterior == null || valorAnterior == 0) {
            return valorAtual > 0 ? 100.0 : 0.0;
        }

        double diferenca = valorAtual - valorAnterior;
        return (diferenca / valorAnterior) * 100.0;
    }
}