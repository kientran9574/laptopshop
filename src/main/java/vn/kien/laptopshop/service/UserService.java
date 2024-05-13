package vn.kien.laptopshop.service;

import java.util.List;
import java.util.Optional;

import vn.kien.laptopshop.model.Role;
import vn.kien.laptopshop.model.User;
import vn.kien.laptopshop.model.dto.RegisterDTO;

public interface UserService {
    List<User> getByALL();

    Optional<User> findById(long id);

    User saveUser(User user);

    void deleteSv(long id);

    Role getByName(String name);

    User registerDTOtoUser(RegisterDTO registerDTO);

    boolean existsByEmails(String email);
    User findByEmailUser(String email);
}
