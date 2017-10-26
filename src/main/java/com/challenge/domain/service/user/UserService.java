package com.challenge.domain.service.user;

public interface UserService {

  UserDetail create(UserCreationRequest userDetail);

  UserDetail get(Long id);
}
