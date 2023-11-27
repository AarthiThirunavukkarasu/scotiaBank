package com.scotiaBank.ScotiaBank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpRequest {
	
	

  
  String firstName;
  String lastName;
  String email;
  String password;
  String username;
  String role;
  
  

  
}