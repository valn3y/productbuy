package com.marinho.valney.productbuy.vo.purchases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Builder
public class PurchaseRequestVO {
    @NonNull
    private ProductVO produto;

    @NonNull
    private PaymentTermsVO condicaoPagamento;
}
