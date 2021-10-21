package com.marinho.valney.productbuy.controller.purchases;

import com.marinho.valney.productbuy.service.purchases.PurchaseService;
import com.marinho.valney.productbuy.vo.purchases.PurchaseRequestVO;
import com.marinho.valney.productbuy.vo.purchases.PurchaseResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchasesController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<List<PurchaseResponseVO>> createAccount(@RequestBody PurchaseRequestVO request) {
        List<PurchaseResponseVO> data = purchaseService.purchaseSimulation(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(data);
    }
}
