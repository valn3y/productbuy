package com.marinho.valney.productbuy.vo.purchases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseResponseVO {
    private Integer numeroParcela;
    private Double valor;
    private Double taxaJurosAoMes;
}
