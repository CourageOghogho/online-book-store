package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.impl.PurchaseHistoryDao;
import com.interswitchng.onlinebookstore.dto.PageParams;
import com.interswitchng.onlinebookstore.model.OrderHistory;
import com.interswitchng.onlinebookstore.model.Page;
import com.interswitchng.onlinebookstore.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {


  private final PurchaseHistoryDao purchaseHistoryDao;
  @Override
  public Page<OrderHistory> history(PageParams params, Integer id) {
    return purchaseHistoryDao.historyForUser(params,id);
  }
}
