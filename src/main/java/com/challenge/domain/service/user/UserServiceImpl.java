package com.challenge.domain.service.user;

import com.challenge.domain.model.User;
import com.challenge.domain.repository.UserRepository;
import com.challenge.domain.shared.exceptions.UserNotFoundException;
import java.util.Optional;
import javax.inject.Inject;
import org.modelmapper.ModelMapper;

public final class UserServiceImpl implements UserService {

  @Inject
  private UserRepository userRepository;
  @Inject
  private ModelMapper modelMapper;

  @Override
  public UserDetail create(final UserCreationRequest userDetail) {
    User newUser = new User();
    modelMapper.map(userDetail, newUser);
    Long id = userRepository.create(newUser);
    UserDetail returnValue = new UserDetail();
    modelMapper.map(newUser, returnValue);
    returnValue.setId(id);
    return returnValue;
  }

  @Override
  public UserDetail get(final Long id) {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent()) {
      throw new UserNotFoundException();
    }
    UserDetail returnValue = new UserDetail();
    modelMapper.map(user.get(), returnValue);
    return returnValue;
  }
}
