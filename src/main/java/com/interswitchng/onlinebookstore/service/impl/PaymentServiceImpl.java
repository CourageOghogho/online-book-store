package com.interswitchng.onlinebookstore.service.impl;

import com.interswitchng.onlinebookstore.dao.impl.CheckoutDao;
import com.interswitchng.onlinebookstore.dto.PaymentResponse;
import com.interswitchng.onlinebookstore.exceptions.NotFoundException;
import com.interswitchng.onlinebookstore.model.Order;
import com.interswitchng.onlinebookstore.model.PaymentMethod;
import com.interswitchng.onlinebookstore.model.PaymentStatus;
import com.interswitchng.onlinebookstore.service.PaymentService;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

  @Value("${payment.web.status}")
  private Integer webPaymentStatusSimulator;

  @Value("${payment.ussd.status}")
  private Integer ussdPaymentStatusSimulator;

  @Value("${payment.ussd.base-code}")
  private String ussdPaymentBaseCode;

  @Value("${payment.transfer.status}")
  private Integer transferPaymentStatusSimulator;

  @Value("${payment.transfer.account.details}")
  private String transferPaymentAccountDetails;

  private final CheckoutDao checkoutDao;

  @Override
  public PaymentResponse initiatePayment(Order order, Integer method) {

    var optionalPaymentMethod= PaymentMethod.get(method).orElseThrow(
        ()->new NotFoundException(50000,"Invalid Payment method")
    );
    PaymentResponse response=new PaymentResponse();

    PaymentStatus status=PaymentStatus.In_PROGRESS;
    String totalAmount=order.getTotalAmount().divide(BigDecimal.valueOf(100)).toString();

    if(Objects.equals(optionalPaymentMethod.getCode(), PaymentMethod.WEB.getCode())){

      response.setDescription(status.getDescription());
      response.setStatusCode(status.getCode());
      order.setPaymentMethod(PaymentMethod.WEB);
      response.setOrder(order);
      response.setPaymentInstruction("Kindly complete your web payment");

    }else if(Objects.equals(optionalPaymentMethod.getCode(), PaymentMethod.USSD.getCode())){
      order.setPaymentMethod(PaymentMethod.USSD);
      response.setDescription(status.getDescription());
      response.setStatusCode(status.getCode());
      response.setPaymentInstruction("Kindly dial: "+ussdPaymentBaseCode+totalAmount+"*"+order.getId()+"#");
      response.setOrder(order);

    }else {
      order.setPaymentMethod(PaymentMethod.TRANSFER);
      response.setDescription(status.getDescription());
      response.setStatusCode(status.getCode());
      order.setPaymentMethod(PaymentMethod.TRANSFER);
      response.setPaymentInstruction("Kindly transfer "+totalAmount+" to "+transferPaymentAccountDetails+" remark: "+order.getId());
      response.setOrder(order);
    }
//    update order

    return response;
  }


  @Override
  public PaymentResponse completePayment(Order order) {

    PaymentResponse response=new PaymentResponse();
    response.setOrder(order);
    PaymentStatus status;
    if(Objects.equals(order.getPaymentMethod(), PaymentMethod.WEB)){

      status = PaymentStatus.get(webPaymentStatusSimulator).orElseThrow();
      response.setDescription(status.getDescription());
      response.setStatusCode(status.getCode());


    }else if(Objects.equals(order.getPaymentMethod(), PaymentMethod.USSD)){
      status = PaymentStatus.get(ussdPaymentStatusSimulator).orElseThrow();
      response.setDescription(status.getDescription());
      response.setStatusCode(status.getCode());

    }else {
      status = PaymentStatus.get(transferPaymentStatusSimulator).orElseThrow();
      response.setDescription(status.getDescription());
      response.setStatusCode(status.getCode());
    }

    return response;
  }

}
