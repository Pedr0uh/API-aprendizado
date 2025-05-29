package com.testeProjeto.api.controller

import com.testeProjeto.api.model.IndicacaoOscarModel
import com.testeProjeto.api.repository.IndicacaoOscarRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"]) // Permite requisições de qualquer origem
@RestController
@RequestMapping("/api/indicacoes")
data class OscarController(
    private val indicacaoOscarRepository: IndicacaoOscarRepository
) {

    // Obter todas as indicações
    @GetMapping
    fun getAllIndicacoes(): List<IndicacaoOscarModel> =
        indicacaoOscarRepository.findAll()

    // Obter indicação por ID
    @GetMapping("/{id}")
    fun getIndicacaoById(@PathVariable id: String): ResponseEntity<IndicacaoOscarModel> {
        return indicacaoOscarRepository.findById(id)
            .map { indicacao -> ResponseEntity.ok(indicacao) }
            .orElse(ResponseEntity.notFound().build())
    }

    // Obter indicações por ano da cerimônia
    @GetMapping("/ano/{ano}")
    fun getIndicacoesByAno(@PathVariable ano: Int): List<IndicacaoOscarModel> =
        indicacaoOscarRepository.findByAnoCerimonia(ano)

    // Obter vencedores ou não vencedores
    @GetMapping("/vencedores/{vencedor}")
    fun getIndicacoesByVencedor(@PathVariable vencedor: Boolean): List<IndicacaoOscarModel> =
        indicacaoOscarRepository.findByVencedor(vencedor)

    // Obter indicações por nome do indicado (busca parcial case insensitive)
    @GetMapping("/indicado/{nome}")
    fun getIndicacoesByNomeIndicado(@PathVariable nome: String): List<IndicacaoOscarModel> =
        indicacaoOscarRepository.findByNomeDoIndicadoContainingIgnoreCase(nome)

    // Obter indicações por nome do filme (busca parcial case insensitive)
    @GetMapping("/filme/{nomeFilme}")
    fun getIndicacoesByNomeFilme(@PathVariable nomeFilme: String): List<IndicacaoOscarModel> =
        indicacaoOscarRepository.findByNomeDoFilmeContainingIgnoreCase(nomeFilme)

    @GetMapping("/categoria/{categoria}")
    fun getIndicacoesByCategoria(
        @PathVariable categoria: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<IndicacaoOscarModel> {
        return indicacaoOscarRepository.findByCategoria(
            categoria,
            PageRequest.of(page, size, Sort.by("nomeDoIndicado")))
    }

    // Criar nova indicação
    @PostMapping
    fun createIndicacao(@RequestBody indicacao: IndicacaoOscarModel): IndicacaoOscarModel =
        indicacaoOscarRepository.save(indicacao)

    // Atualizar indicação
    @PutMapping("/{id}")
    fun updateIndicacao(
        @PathVariable id: String,
        @RequestBody indicacaoAtualizada: IndicacaoOscarModel
    ): ResponseEntity<IndicacaoOscarModel> {
        return indicacaoOscarRepository.findById(id)
            .map { indicacaoExistente ->
                val updatedIndicacao = indicacaoExistente.copy(
                    idRegistro = indicacaoAtualizada.idRegistro,
                    anoFilmagem = indicacaoAtualizada.anoFilmagem,
                    anoCerimonia = indicacaoAtualizada.anoCerimonia,
                    cerimonia = indicacaoAtualizada.cerimonia,
                    categoria = indicacaoAtualizada.categoria,
                    nomeDoIndicado = indicacaoAtualizada.nomeDoIndicado,
                    nomeDoFilme = indicacaoAtualizada.nomeDoFilme,
                    vencedor = indicacaoAtualizada.vencedor
                )
                ResponseEntity.ok(indicacaoOscarRepository.save(updatedIndicacao))
            }
            .orElse(ResponseEntity.notFound().build())
    }

    // Deletar indicação
    @DeleteMapping("/{id}")
    fun deleteIndicacao(@PathVariable id: String): ResponseEntity<Void> {
        return indicacaoOscarRepository.findById(id)
            .map { indicacao ->
                indicacaoOscarRepository.delete(indicacao)
                ResponseEntity.noContent().build<Void>()
            }
            .orElse(ResponseEntity.notFound().build())
    }
}