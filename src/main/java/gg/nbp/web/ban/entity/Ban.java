package gg.nbp.web.ban.entity;

import java.time.LocalDateTime;

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
@Table (name = "ban_list")
public class Ban extends Core{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer ban_id;
	@Column
	private Integer member_id;
	@Column 
	private Integer manager_id;
	@Column 
	private String ban_reason;
	@Column
	private LocalDateTime startTime;
	@Column
	private LocalDateTime endTime;
	
	
}
