package com.blog.services;

import com.blog.model.dto.UserDetailsDTO;

public interface AuthService {
    UserDetailsDTO checkToken(String token);
    String getServiceToken();
}
