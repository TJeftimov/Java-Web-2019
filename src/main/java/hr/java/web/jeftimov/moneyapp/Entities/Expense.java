package hr.java.web.jeftimov.moneyapp.Entities;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class Expense {

	private Long id;
	@NotEmpty(message = "You must enter a name!")
	@Size(min = 3, max = 20, message = "Name must be between 5 and 20 characters!")
	private String name;
	@NotNull(message = "You must enter an amount!")
	@DecimalMin(value = "10", message = "Amount must be at least 10")
	private Double amount;
	@NotNull(message = "You must select and expense type!")
	private Type type;

	private LocalDateTime createDate;
	
	public enum Type {
		FOOD,
		RENT,
		FUEL,
		ENTERTAINMENT
	};
}
