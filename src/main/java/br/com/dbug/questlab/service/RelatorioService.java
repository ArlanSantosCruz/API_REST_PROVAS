package br.com.dbug.questlab.service;

import br.com.dbug.questlab.repository.QuestaoRepository;
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
import br.com.dbug.questlab.repository.projection.*;
import br.com.dbug.questlab.rest.dto.response.RelatorioIndicadoresDTO;
import br.com.dbug.questlab.rest.dto.response.RelatorioIndicadoresDTO.*;
import java.time.LocalDate;
import br.com.dbug.questlab.repository.projection.*;
import br.com.dbug.questlab.rest.dto.response.RelatorioIndicadoresDTO.*;
import br.com.dbug.questlab.repository.QuestaoRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RelatorioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRespostaRepository usuarioRespostaRepository;
    private final QuestaoRepository questaoRepository;
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

    @Transactional(readOnly = true)
    public RelatorioIndicadoresDTO relatorioIndicadores() {
        log.info("Gerando relatório sintético de indicadores");

        // 1. Buscar indicadores de usuários
        IndicadoresUsuariosProjection usuarios = usuarioRepository.getIndicadoresUsuarios();
        IndicadoresUsuarios indicadoresUsuarios = new IndicadoresUsuarios(
                usuarios.getTotalUsuarios(),
                usuarios.getTotalUsuariosAtivos(),
                calcularPercentualAtivos(usuarios.getTotalUsuarios(), usuarios.getTotalUsuariosAtivos())
        );

        // 2. Buscar indicadores de questões
        IndicadoresQuestoesProjection questoes = questaoRepository.getIndicadoresQuestoes();

        // Calcular crescimento do banco de questões (mês atual vs mês anterior)
        LocalDate hoje = LocalDate.now();
        Long questoesMesAtual = questaoRepository.countQuestoesPorMes(hoje.getMonthValue(), hoje.getYear());
        Long questoesMesAnterior = questaoRepository.countQuestoesPorMes(
                hoje.getMonthValue() - 1,
                hoje.getYear()
        );
        Double crescimento = calcularCrescimento(questoesMesAnterior, questoesMesAtual);

        IndicadoresQuestoes indicadoresQuestoes = new IndicadoresQuestoes(
                questoes.getTotalQuestoesCadastradas(),
                questoes.getTotalQuestoesResolvidas(),
                crescimento
        );

        // 3. Buscar indicadores de aprendizado
        IndicadoresAprendizadoProjection aprendizado = usuarioRespostaRepository.getIndicadoresAprendizado();
        IndicadoresAprendizado indicadoresAprendizado = new IndicadoresAprendizado(
                calcularMediaAcertos(aprendizado.getTotalTentativas(), aprendizado.getTotalAcertos()),
                aprendizado.getTotalTentativas(),
                aprendizado.getTotalAcertos()
        );

        // 4. Montar DTO composto
        return new RelatorioIndicadoresDTO(
                indicadoresUsuarios,
                indicadoresQuestoes,
                indicadoresAprendizado
        );
    }

    // CÁLCULOS NO SERVICE (Lógica de Negócio)
    private Double calcularPercentualAtivos(Long total, Long ativos) {
        if (total == null || total == 0) return 0.0;
        return (ativos.doubleValue() / total.doubleValue()) * 100.0;
    }

    private Double calcularCrescimento(Long anterior, Long atual) {
        if (anterior == null || anterior == 0) {
            return atual > 0 ? 100.0 : 0.0;
        }
        return ((atual.doubleValue() - anterior.doubleValue()) / anterior.doubleValue()) * 100.0;
    }

    private Double calcularMediaAcertos(Long tentativas, Long acertos) {
        if (tentativas == null || tentativas == 0) return 0.0;
        return (acertos.doubleValue() / tentativas.doubleValue()) * 100.0;
    }

}