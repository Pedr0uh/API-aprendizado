package com.testeProjeto.api.model

//model: resposavel por espelhar (trazer) o dados do nosso banco que queremos trabalhar

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "premiacoes") // nome da coleção que ira usar
data class IndicacaoOscarModel(

    @Id // O campo ID é um identificador único
    val id: ObjectId? = null,

    @Field("id_registros") //field = campos, campos que voce tem na sua colecao
    val idRegistro: Int, //variaveis para armazenar o valor dos campos

    @Field("ano_filmagem")
    val anoFilmagem: Int,

    @Field("ano_cerimonia")
    val anoCerimonia: Int,

    @Field("cerimonia")
    val cerimonia: Int,

    @Field("categoria")
    val categoria: String,

    @Field("nome_do_indicado")
    val nomeDoIndicado: String,

    @Field("nome_do_filme")
    val nomeDoFilme: String,

    @Field("vencedor")
    val vencedor: Boolean

) {



}