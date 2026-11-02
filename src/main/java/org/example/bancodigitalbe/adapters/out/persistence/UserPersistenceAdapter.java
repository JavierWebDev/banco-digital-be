package org.example.bancodigitalbe.adapters.out.persistence;

import org.example.bancodigitalbe.domain.exceptions.UsernameAlreadyExistsException;
import org.example.bancodigitalbe.domain.model.NewUser;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.out.SaveNewUserPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements SaveNewUserPort {

    private final UserJpaRepository userJpaRepository;

    public UserPersistenceAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(NewUser newUser) throws UsernameAlreadyExistsException {
        try {
            UserJpaEntity userJpaEntity = new UserJpaEntity();

            userJpaEntity.setUser(newUser.getUser());
            userJpaEntity.setPassword(newUser.getPassword());
            userJpaEntity.setName(newUser.getName());
            userJpaEntity.setLastName(newUser.getLastName());
            userJpaEntity.setBirthDate(newUser.getBirthDate());

            return userJpaEntityToDomainUser(userJpaRepository.save(userJpaEntity));
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException(newUser.getUser(), e);
        }
   }

    private User userJpaEntityToDomainUser(UserJpaEntity userJpaEntity) {
        return new User(
                userJpaEntity.getUserId(),
                userJpaEntity.getUser(),
                userJpaEntity.getPassword(),
                userJpaEntity.getName(),
                userJpaEntity.getLastName(),
                userJpaEntity.getBirthDate()
        );
    }
}
