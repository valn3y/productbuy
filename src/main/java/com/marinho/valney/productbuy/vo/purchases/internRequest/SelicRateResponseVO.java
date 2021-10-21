package com.marinho.valney.productbuy.vo.purchases.internRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelicRateResponseVO {
    private String data;
    private Double valor;
}
