package com.marinho.valney.productbuy.vo.purchases;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVO {
    private Long codigo;
    private String nome;
    private Double valor;
}
