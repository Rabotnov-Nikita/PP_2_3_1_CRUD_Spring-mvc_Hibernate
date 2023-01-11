package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao{

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<User> getAllUsers() {
        List<User> users = em.createQuery("SELECT u FROM User u").getResultList();
        return users;
    }

    @Override
    public User getUserById(Long id) {
        User user = (User) em.createQuery("SELECT u FROM User u WHERE  u.id =?1").setParameter(1, id).getSingleResult();
        return user;
    }

    @Override
    public void addUser(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    @Override
    public void updateUser(Long id, User editedUser) {
        User userToBeeUpdated = getUserById(id);
        userToBeeUpdated.setFirstName(editedUser.getFirstName());
        userToBeeUpdated.setLastName(editedUser.getLastName());
        userToBeeUpdated.setEmail(editedUser.getEmail());
        addUser(userToBeeUpdated);
    }

    @Override
    public void deleteUser(Long id) {
        User userToBeeDeleted = getUserById(id);
        em.remove(userToBeeDeleted);
    }
}
