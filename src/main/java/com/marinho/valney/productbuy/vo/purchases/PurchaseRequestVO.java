package com.marinho.valney.productbuy.vo.purchases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PurchaseRequestVO {
    private ProductVO produto;
    private PaymentTermsVO condicaoPagamento;
}
