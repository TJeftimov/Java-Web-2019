package hr.java.web.jeftimov.moneyapp.Repositories;

import hr.java.web.jeftimov.moneyapp.Entities.Wallet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Primary
@Repository
@Transactional
public class HibernateWalletRepository implements WalletRepository {

    private final EntityManager em;
    public HibernateWalletRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Wallet findUsersWallet(String userName) {
        Session session = (Session) em.getDelegate();
        Query query =
                session.createQuery(
                        "from Wallet where userName=?1", Wallet.class);
        query.setParameter(1, userName);
        return (Wallet) query.list().get(0);
    }

    @Override
    public List<Wallet> findAll() {
        Session session = (Session) em.getDelegate();
        return session.createQuery("SELECT w FROM Wallet w", Wallet.class).getResultList();
    }

    @Override
    public void updateWallet(Wallet wallet) {
        Session session = (Session) em.getDelegate();
        session.update(wallet);
    }

    @Override
    public void deleteWallet(Long walletId) {

    }

}
