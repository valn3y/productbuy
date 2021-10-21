package com.marinho.valney.productbuy.vo.purchases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVO {
    private Long codigo;
    private String nome;
    private Double valor;
}
