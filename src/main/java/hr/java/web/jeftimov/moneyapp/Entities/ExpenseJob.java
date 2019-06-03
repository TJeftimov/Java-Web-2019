package hr.java.web.jeftimov.moneyapp.Entities;

import com.google.common.collect.Lists;
import hr.java.web.jeftimov.moneyapp.Repositories.ExpenseRepository;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

// napisati novi trigger svake subote u 2 ujutro

public class ExpenseJob extends QuartzJobBean {

    @Autowired
    private final ExpenseRepository expenseRepository;

    public ExpenseJob(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<Expense> expenses = Lists.newArrayList(expenseRepository.findAll());
        double sumEntertainment = 0, sumFood = 0, sumRent = 0, sumFuel = 0, maxEntertainment = 0, maxFood = 0, maxRent = 0, maxFuel = 0;
        double minEntertainment = 10000000, minFood = 10000000, minRent = 10000000, minFuel = 10000000;
        for(Expense ex : expenses) {
            if (ex.getType() == Expense.Type.ENTERTAINMENT) {
                sumEntertainment += ex.getAmount();
                if (ex.getAmount() > maxEntertainment)
                    maxEntertainment = ex.getAmount();
                if (ex.getAmount() < minEntertainment)
                    minEntertainment = ex.getAmount();
            } else if (ex.getType() == Expense.Type.FOOD) {
                sumFood += ex.getAmount();
                if (ex.getAmount() > maxFood)
                    maxFood = ex.getAmount();
                if (ex.getAmount() < minFood)
                    minFood = ex.getAmount();
            } else if (ex.getType() == Expense.Type.FUEL) {
                sumFuel += ex.getAmount();
                if (ex.getAmount() > maxFuel)
                    maxFuel = ex.getAmount();
                if (ex.getAmount() < minFuel)
                    minFuel = ex.getAmount();
            } else if (ex.getType() == Expense.Type.RENT) {
                sumRent += ex.getAmount();
                if (ex.getAmount() > maxRent)
                    maxRent = ex.getAmount();
                if (ex.getAmount() < minRent)
                    minRent = ex.getAmount();
            }
        }

        System.out.println("\t SUM \t MIN \t MAX");
        System.out.println("ENTERTAINMENT \t" + sumEntertainment + "\t" + minEntertainment + "\t" + maxEntertainment);
        System.out.println("FOOD \t" + sumFood + "\t" + minFood + "\t" + maxFood);
        System.out.println("FUEL \t" + sumFuel + "\t" + minFuel + "\t" + maxFuel);
        System.out.println("RENT \t" + sumRent + "\t" + minRent + "\t" + maxRent);
    }
}
