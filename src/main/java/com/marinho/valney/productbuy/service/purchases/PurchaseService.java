package com.marinho.valney.productbuy.service.purchases;

import com.marinho.valney.productbuy.vo.purchases.PurchaseRequestVO;
import com.marinho.valney.productbuy.vo.purchases.PurchaseResponseVO;

import java.util.List;

public interface PurchaseService {
    List<PurchaseResponseVO> purchaseSimulation(PurchaseRequestVO request);
}
