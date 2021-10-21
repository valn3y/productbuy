package com.marinho.valney.productbuy.service.purchases;

import com.marinho.valney.productbuy.vo.purchases.PurchaseRequestVO;
import com.marinho.valney.productbuy.vo.purchases.PurchaseResponseVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PurchasesServiceImpl implements PurchaseService{

    @Override
    public List<PurchaseResponseVO> purchaseSimulation(PurchaseRequestVO request) {
        List<PurchaseResponseVO> purchaseResponseVOList = new ArrayList<>();
        Integer installments = request.getCondicaoPagamento().getQtdeParcelas();
        double restPayment = (request.getProduto().getValor() - request.getCondicaoPagamento().getValorEntrada()) / installments;
        double selicRate = 0.0;

        if(request.getCondicaoPagamento().getQtdeParcelas() > 6) {
            selicRate = getSelicRate();
        }

        for(int i = 1; i <= installments; i++) {
            PurchaseResponseVO purchaseResponseVO = PurchaseResponseVO.builder().numeroParcela(i).build();
            if(i > 6) {
                double interestRatePerMonth = 0.0115 * selicRate;
                purchaseResponseVO.setTaxaJurosAoMes(interestRatePerMonth);
                purchaseResponseVO.setValor(restPayment + interestRatePerMonth);
            } else {
                purchaseResponseVO.setTaxaJurosAoMes(0.0);
                purchaseResponseVO.setValor(restPayment);
            }
            purchaseResponseVOList.add(purchaseResponseVO);
        }

        return purchaseResponseVOList;
    }

    private Double getSelicRate() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime old30Day = today.minus(Duration.ofDays(30));
        return 0.5;
    }
}
