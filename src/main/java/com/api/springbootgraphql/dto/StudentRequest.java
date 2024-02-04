package com.api.springbootgraphql.dto;

/* Exemplo Extra - para criar um estudante sem está associado ao curso (cursoId) */
public record StudentRequest(Long id, String name, String lastName, Integer age) {
    
}
