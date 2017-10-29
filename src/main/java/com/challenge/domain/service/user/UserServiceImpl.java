package com.challenge.domain.service.user;

import com.challenge.domain.model.User;
import com.challenge.domain.repository.UserRepository;
import javax.inject.Inject;
import org.modelmapper.ModelMapper;

public class UserServiceImpl implements UserService {

  @Inject
  private UserRepository userRepository;
  @Inject
  private ModelMapper modelMapper;

  @Override
  public UserDetail create(UserCreationRequest userDetail) {
    User newUser = new User();
    modelMapper.map(userDetail, newUser);
    Long id = userRepository.create(newUser);
    UserDetail returnValue = new UserDetail();
    modelMapper.map(newUser, returnValue);
    returnValue.setId(id);
    return returnValue;
  }

  @Override
  public UserDetail get(Long id) {
    User user = userRepository.findById(id);
    UserDetail returnValue = new UserDetail();
    modelMapper.map(user, returnValue);
    return returnValue;
  }
}
