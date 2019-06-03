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

	@NotEmpty(message = "{msg.lang.nameNotEmpty}")
	@Size(min = 3, max = 20, message = "{msg.lang.nameLength}")
	private String name;

	@NotNull(message = "{msg.lang.amountNotNull}")
	@DecimalMin(value = "10", message = "{msg.lang.amountSize}")
	private Double amount;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "{msg.lang.typeNotNull}")
	private Type type;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="walletid")
	private Wallet wallet;

	@JsonIgnore
	private LocalDateTime createdate;
	
	public enum Type {
		FOOD,
		RENT,
		FUEL,
		ENTERTAINMENT
	}
}
