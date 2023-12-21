package com.interswitchng.onlinebookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author extends BaseModel{
  private String name;
}
