package hr.java.web.jeftimov.moneyapp.Configs;

import hr.java.web.jeftimov.moneyapp.Entities.ExpenseJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Configuration
public class SchedulerConfig {

    @Bean
    public JobDetail expenseJobDetail() {
        return JobBuilder.newJob(ExpenseJob.class).withIdentity("expenseJob")
                .storeDurably().build();
    }
    @Bean
    public Trigger expenseJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10).repeatForever();
        // return TriggerBuilder.newTrigger().forJob(expenseJobDetail())
               // .withIdentity("expenseJob").withSchedule(scheduleBuilder).build();
        return TriggerBuilder.newTrigger().forJob(expenseJobDetail()).withIdentity("expenseJob").withSchedule(cronSchedule("0 0 2 ? * SAT")).build();
    }

}
