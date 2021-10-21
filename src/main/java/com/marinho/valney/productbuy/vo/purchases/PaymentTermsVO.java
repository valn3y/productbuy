package com.marinho.valney.productbuy.vo.purchases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentTermsVO {
    private Double valorEntrada;
    private Integer qtdeParcelas;
}
