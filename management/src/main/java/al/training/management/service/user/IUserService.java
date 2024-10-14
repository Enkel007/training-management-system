package al.training.management.service.user;

import al.training.management.dto.UserDto;
import al.training.management.model.User;
import al.training.management.request.CreateUserRequest;
import al.training.management.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);

    User createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
