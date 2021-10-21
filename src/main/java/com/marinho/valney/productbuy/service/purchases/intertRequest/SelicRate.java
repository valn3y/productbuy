package com.marinho.valney.productbuy.service.purchases.intertRequest;

import com.marinho.valney.productbuy.vo.purchases.internRequest.SelicRateResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "selic-rate", url = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados")
public interface SelicRate {
    @GetMapping
    List<SelicRateResponseVO> findSelicRate(@RequestParam("dataInicial") String startDate, @RequestParam("dataFinal") String endDate);
}
