package com.marinho.valney.productbuy.controller.purchases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marinho.valney.productbuy.service.purchases.PurchasesServiceImpl;
import com.marinho.valney.productbuy.vo.purchases.PaymentTermsVO;
import com.marinho.valney.productbuy.vo.purchases.ProductVO;
import com.marinho.valney.productbuy.vo.purchases.PurchaseRequestVO;
import com.marinho.valney.productbuy.vo.purchases.PurchaseResponseVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PurchasesControllerIT {
    protected static ResultActions result;
    private PurchaseRequestVO purchaseRequestVOTest;
    private ProductVO productVOTest;
    private PaymentTermsVO paymentTermsVOTest;
    private List<PurchaseResponseVO> purchaseResponseVOListTest = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PurchasesServiceImpl service;

    @Test
    public void shouldSimulateWithInputEqualValue() throws Exception {
        double valueProduct = 10.0;
        double inputValue = 10.0;
        int installments = 0;

        givenProductVO(valueProduct);
        givenPaymentTermsVO(installments, inputValue);
        givenPurchaseRequestVO();
        givenPurchaseResponseVOList(valueProduct, installments, inputValue);
        whenCreatePurchase();
        thenReturnPurchaseResponseVOListOneItem();
    }

    @Test
    public void shouldSimulateWithInstallments() throws Exception {
        double valueProduct = 10.0;
        double inputValue = 0.0;
        int installments = 5;

        givenProductVO(valueProduct);
        givenPaymentTermsVO(installments, inputValue);
        givenPurchaseRequestVO();
        givenPurchaseResponseVOList(valueProduct, installments, inputValue);
        whenCreatePurchase();
        thenReturnPurchaseResponseVOList();
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

    private void givenPurchaseRequestVOEmpty() {
        productVOTest = ProductVO.builder().build();
        paymentTermsVOTest = PaymentTermsVO.builder().build();
        purchaseRequestVOTest = PurchaseRequestVO.builder()
                .produto(productVOTest)
                .condicaoPagamento(paymentTermsVOTest)
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

    // Request region
    private void whenCreatePurchase() throws Exception {
        result = mockMvc.perform(MockMvcRequestBuilders.post("/api/purchases")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(purchaseRequestVOTest)));
    }

    // Then region
    private void thenReturnPurchaseResponseVOListOneItem() throws Exception {
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].numeroParcela").value(purchaseResponseVOListTest.get(0).getNumeroParcela()))
                .andExpect(jsonPath("$[0].valor").value(purchaseResponseVOListTest.get(0).getValor()))
                .andExpect(jsonPath("$[0].troco").value(purchaseResponseVOListTest.get(0).getTroco()))
                .andExpect(jsonPath("$[0].taxaJurosAoMes").value(purchaseResponseVOListTest.get(0).getTaxaJurosAoMes()));
    }

    private void thenReturnPurchaseResponseVOList() throws Exception {
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(purchaseResponseVOListTest.size())));
    }
}
