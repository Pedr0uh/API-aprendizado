package com.testeProjeto.api.repository

import com.testeProjeto.api.model.IndicacaoOscarModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface IndicacaoOscarRepository : MongoRepository<IndicacaoOscarModel, String> {

    fun findByAnoCerimonia(ano: Int): List<IndicacaoOscarModel>

    // findByCategoria(categoria: String, pageable: SpringDataWebProperties.Pageable): Page<IndicacaoOscar>

    fun findByCategoria(categoria: String, pageable: PageRequest): Page<IndicacaoOscarModel>

    fun findByVencedor(vencedor: Boolean): List<IndicacaoOscarModel>

    fun findByNomeDoIndicadoContainingIgnoreCase(nome: String): List<IndicacaoOscarModel>

    fun findByNomeDoFilmeContainingIgnoreCase(filme: String): List<IndicacaoOscarModel>

}