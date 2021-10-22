package com.marinho.valney.productbuy.service.purchases;

import com.marinho.valney.productbuy.service.purchases.intertRequest.SelicRate;
import com.marinho.valney.productbuy.vo.purchases.PurchaseRequestVO;
import com.marinho.valney.productbuy.vo.purchases.PurchaseResponseVO;
import com.marinho.valney.productbuy.vo.purchases.internRequest.SelicRateResponseVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

@Service
@AllArgsConstructor
public class PurchasesServiceImpl implements PurchaseService{

    private final SelicRate selicRate;

    @Override
    public List<PurchaseResponseVO> purchaseSimulation(PurchaseRequestVO request) {
        List<PurchaseResponseVO> purchaseResponseVOList;
        int installments = request.getCondicaoPagamento().getQtdeParcelas();
        double restPayment = request.getProduto().getValor() - request.getCondicaoPagamento().getValorEntrada();

        if(installments > 0 && restPayment > 0) {
            purchaseResponseVOList = installments(restPayment, installments);
        } else {
            purchaseResponseVOList = common(restPayment);
        }

        return purchaseResponseVOList;
    }

    public List<PurchaseResponseVO> common(double restPayment) {
        List<PurchaseResponseVO> purchaseResponseVOList = new ArrayList<>();

        PurchaseResponseVO purchaseResponseVO = PurchaseResponseVO.builder()
                .numeroParcela(0)
                .taxaJurosAoMes(0.0)
                .valor(restPayment > 0 ? restPayment : 0.0)
                .troco(restPayment < 0 ? restPayment * -1 : 0.0)
                .build();

        purchaseResponseVOList.add(purchaseResponseVO);

        return purchaseResponseVOList;
    }

    public List<PurchaseResponseVO> installments(double restPayment, int installments) {
        List<PurchaseResponseVO> purchaseResponseVOList = new ArrayList<>();
        double restPaymentDivided = restPayment / installments;
        double rate = 0.0;

        if(installments > 6) {
            rate = getSelicRate();
        }

        for(int i = 1; i <= installments; i++) {
            PurchaseResponseVO purchaseResponseVO = PurchaseResponseVO.builder().numeroParcela(i).build();
            if(installments > 6) {
                double interestRatePerMonth = 0.0115 * rate;
                double value = restPaymentDivided + (restPaymentDivided * interestRatePerMonth);
                purchaseResponseVO.setTaxaJurosAoMes(rateFormat(interestRatePerMonth));
                purchaseResponseVO.setTroco(0.0);
                purchaseResponseVO.setValor(cashFormat(value));
            } else {
                purchaseResponseVO.setTaxaJurosAoMes(0.0);
                purchaseResponseVO.setTroco(0.0);
                purchaseResponseVO.setValor(cashFormat(restPaymentDivided));
            }
            purchaseResponseVOList.add(purchaseResponseVO);
        }

        return purchaseResponseVOList;
    }

    private Double getSelicRate() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime old30Day = today.minus(Duration.ofDays(30));
        List<SelicRateResponseVO> response = selicRate.findSelicRate(
                old30Day.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return response.stream().map(SelicRateResponseVO::getValor).flatMapToDouble(DoubleStream::of).sum();
    }

    private double cashFormat(double value){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return Double.parseDouble(decimalFormat.format(value));
    }

    private double rateFormat(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00000");
        return Double.parseDouble(decimalFormat.format(value));
    }
}
