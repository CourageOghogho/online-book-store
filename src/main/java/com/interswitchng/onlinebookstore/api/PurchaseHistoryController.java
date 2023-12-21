package com.interswitchng.onlinebookstore.api;

import com.interswitchng.onlinebookstore.dto.PageParams;
import com.interswitchng.onlinebookstore.model.OrderHistory;
import com.interswitchng.onlinebookstore.model.Page;
import com.interswitchng.onlinebookstore.model.User;
import com.interswitchng.onlinebookstore.service.PurchaseHistoryService;
import com.interswitchng.onlinebookstore.utils.SessionUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order-history-service")
public class PurchaseHistoryController {

  private final SessionUserProvider userProvider;
  private final PurchaseHistoryService purchaseHistoryService;
  @PutMapping("/purchase")
  public ResponseEntity<Page<OrderHistory>> getHistory(PageParams params) {

    User user=userProvider.getSessionUser();

    return ResponseEntity.ok(purchaseHistoryService.history(params,user.getId()));
  }


}
