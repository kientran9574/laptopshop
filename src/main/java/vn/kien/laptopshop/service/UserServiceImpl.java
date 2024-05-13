package vn.kien.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kien.laptopshop.model.Role;
import vn.kien.laptopshop.model.User;
import vn.kien.laptopshop.model.dto.RegisterDTO;
import vn.kien.laptopshop.repository.RoleRepository;
import vn.kien.laptopshop.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getByALL() {
        // TODO Auto-generated method stub
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findById(long id) {
        // TODO Auto-generated method stub
        return this.userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        // TODO Auto-generated method stub
        return this.userRepository.save(user);
    }

    @Override
    public void deleteSv(long id) {
        this.userRepository.deleteById(id);

    }

    @Override
    public Role getByName(String name) {
        // TODO Auto-generated method stub
        return this.roleRepository.findByName(name);
    }

    @Override
    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullname(registerDTO.getFirstName() + "" + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPasword(registerDTO.getPassword());
        return user;
    }

    @Override
    public boolean existsByEmails(String email) {

        return this.userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmailUser(String email) {
        return this.userRepository.findByEmail(email);
    }
    
}
