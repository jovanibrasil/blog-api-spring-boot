package com.blog.services;

import com.blog.dtos.UserDetailsDTO;

public interface AuthService {
    UserDetailsDTO checkToken(String token);
    String getServiceToken();
}
