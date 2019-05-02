package hr.java.web.jeftimov.moneyapp.Repositories;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import hr.java.web.jeftimov.moneyapp.Entities.Wallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Primary
@Repository
@Transactional
public class HibernateExpenseRepository implements ExpenseRepository {

    private final EntityManager em;
    public HibernateExpenseRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Expense> findAll() {
        Session session = (Session) em.getDelegate();
        return session.createQuery("SELECT e FROM Expense e", Expense.class).getResultList();
    }

    @Override
    public Expense findOne(Long id) {
        Session session = (Session) em.getDelegate();
        return session.find(Expense.class, id);
    }

    @Override
    public Expense save(Expense expense, Wallet wallet) {
        expense.setCreateDate(LocalDateTime.now());

        expense.setWallet(wallet);

        Session session = (Session) em.getDelegate();
        Serializable id = session.save(expense);

        expense.setId((Long) id);

        return expense;
    }

    @Override
    public Expense update(Expense expense) {
        Session session = (Session) em.getDelegate();
        return (Expense) session.merge(expense);
    }

    @Override
    public void delete(Long id) {
        Expense expense = em.find(Expense.class, id);
        em.remove(expense);
    }
}
