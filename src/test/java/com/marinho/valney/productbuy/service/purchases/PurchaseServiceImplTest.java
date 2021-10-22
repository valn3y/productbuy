package com.marinho.valney.productbuy.service.purchases;

import com.marinho.valney.productbuy.vo.purchases.PaymentTermsVO;
import com.marinho.valney.productbuy.vo.purchases.ProductVO;
import com.marinho.valney.productbuy.vo.purchases.PurchaseRequestVO;
import com.marinho.valney.productbuy.vo.purchases.PurchaseResponseVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceImplTest {
    private PurchaseRequestVO purchaseRequestVOTest;
    private ProductVO productVOTest;
    private PaymentTermsVO paymentTermsVOTest;
    private List<PurchaseResponseVO> purchaseResponseVOListTest = new ArrayList<>();
    private List<PurchaseResponseVO> purchaseResponseVOListResult = new ArrayList<>();

    @InjectMocks
    private PurchasesServiceImpl service;

    @Test
    public void shouldSimulateWithInputEqualValue() {
        double valueProduct = 10.0;
        double inputValue = 10.0;
        int installments = 0;

        givenProductVO(valueProduct);
        givenPaymentTermsVO(installments, inputValue);
        givenPurchaseRequestVO();
        givenPurchaseResponseVOList(valueProduct, installments, inputValue);
        whenCalled_purchaseSimulation();
        thenReturnPurchaseResponseVO();
    }

    @Test
    public void shouldSimulateWithInstallments() {
        double valueProduct = 10.0;
        double inputValue = 0.0;
        int installments = 5;

        givenProductVO(valueProduct);
        givenPaymentTermsVO(installments, inputValue);
        givenPurchaseRequestVO();
        givenPurchaseResponseVOList(valueProduct, installments, inputValue);
        whenCalled_purchaseSimulation();
        thenReturnPurchaseResponseVO();
    }

    @Test
    public void shouldSimulateWithInputMoreThanValue() {
        double valueProduct = 10.0;
        double inputValue = 15.0;
        int installments = 0;

        givenProductVO(valueProduct);
        givenPaymentTermsVO(installments, inputValue);
        givenPurchaseRequestVO();
        givenPurchaseResponseVOList(valueProduct, installments, inputValue);
        whenCalled_purchaseSimulation();
        thenReturnPurchaseResponseVO();
    }

    @Test
    public void shouldSimulateWithAll() {
        double valueProduct = 120.0;
        double inputValue = 20.0;
        int installments = 5;

        givenProductVO(valueProduct);
        givenPaymentTermsVO(installments, inputValue);
        givenPurchaseRequestVO();
        givenPurchaseResponseVOList(valueProduct, installments, inputValue);
        whenCalled_purchaseSimulation();
        thenReturnPurchaseResponseVO();
    }

    // Given region
    private void givenProductVO(double valueProduct) {
        productVOTest = ProductVO.builder()
                .codigo(1L)
                .nome("PRODUCT TEST")
                .valor(valueProduct)
                .build();
    }

    private void givenPaymentTermsVO(int installments, double inputValue) {
        paymentTermsVOTest = PaymentTermsVO.builder()
                .qtdeParcelas(installments)
                .valorEntrada(inputValue)
                .build();
    }

    private void givenPurchaseRequestVO() {
        purchaseRequestVOTest = PurchaseRequestVO.builder()
                .condicaoPagamento(paymentTermsVOTest)
                .produto(productVOTest)
                .build();
    }

    private void givenPurchaseResponseVOList(double valueProduct, int installments, double inputValue) {
        double restPayment = valueProduct - inputValue;
        if(installments > 0 && restPayment > 0){
            purchaseResponseVOListTest = service.installments(restPayment, installments);
        } else {
            purchaseResponseVOListTest = service.common(restPayment);
        }
    }

    // Called region
    private void whenCalled_purchaseSimulation() {
        purchaseResponseVOListResult = service.purchaseSimulation(purchaseRequestVOTest);
    }

    // Then region
    private void thenReturnPurchaseResponseVO() {
        assertAll(() -> assertEquals(purchaseResponseVOListResult, purchaseResponseVOListTest));
    }
}
