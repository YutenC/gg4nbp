package gg.nbp.web.Manager.entity;

import gg.nbp.core.pojo.Core;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Manager extends Core {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer manager_id; 	// int 			NN, PK, AI
	@Column
	private String account; 		// varchar(40) 	NN
	@Column
	private String password; 		// varchar(40) 	NN
	@Column
	private String name; 			// varchar(40) 	NN
	@Column
	private String email; 			// varchar(100)	NN
	@Column
	private String phone; 			// char(10) 	NN
	@Column
	private String address; 		// varchar(100)
	@Column 
	private Integer is_working; 	// tinyInt		NN
	
	
	
}
