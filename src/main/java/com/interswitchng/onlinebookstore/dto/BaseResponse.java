package com.interswitchng.onlinebookstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonIgnoreProperties(
    ignoreUnknown = true
)
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseResponse implements Serializable {

  private String responseCode;
  private String responseMessage;
}