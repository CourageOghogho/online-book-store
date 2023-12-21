package com.interswitchng.onlinebookstore.service;

import com.interswitchng.onlinebookstore.dto.PageParams;
import com.interswitchng.onlinebookstore.model.OrderHistory;
import com.interswitchng.onlinebookstore.model.Page;


public interface PurchaseHistoryService {
  Page<OrderHistory> history(PageParams params, Integer id);

}
