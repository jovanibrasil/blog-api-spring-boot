package com.blog.services;

import com.blog.security.TempUser;

public interface AuthService {
    TempUser checkToken(String token);
    String getServiceToken();
}
