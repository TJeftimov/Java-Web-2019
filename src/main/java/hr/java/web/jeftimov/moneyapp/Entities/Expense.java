package hr.java.web.jeftimov.moneyapp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="expenses")
public class Expense implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@NotEmpty(message = "You must enter a name!")
	@Size(min = 3, max = 20, message = "Name must be between 5 and 20 characters!")
	private String name;

	@NotNull(message = "You must enter an amount!")
	@DecimalMin(value = "10", message = "Amount must be at least 10")
	private Double amount;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "You must select and expense type!")
	private Type type;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="walletId")
	private Wallet wallet;

	@JsonIgnore
	private LocalDateTime createDate;
	
	public enum Type {
		FOOD,
		RENT,
		FUEL,
		ENTERTAINMENT
	}
}
