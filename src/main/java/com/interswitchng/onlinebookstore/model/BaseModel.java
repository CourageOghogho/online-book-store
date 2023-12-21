package com.interswitchng.onlinebookstore.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public abstract class BaseModel implements Serializable {

  protected Integer id;
  protected Date createdDate;

}
