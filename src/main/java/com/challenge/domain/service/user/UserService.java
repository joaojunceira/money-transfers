package com.challenge.domain.service.user;

public interface UserService {

  UserDetail create(final UserCreationRequest userDetail);

  UserDetail get(final Long id);
}
