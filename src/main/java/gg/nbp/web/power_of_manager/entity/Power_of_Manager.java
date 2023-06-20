package gg.nbp.web.power_of_manager.entity;

import static gg.nbp.web.power_of_manager.entity.Power_of_Manager.PK;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import gg.nbp.core.pojo.Core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "power_of_manager")
@IdClass(PK.class)
public class Power_of_Manager extends Core{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column (name= "manager_id")
	private Integer manager_id;
	@Id
	@Column (name= "power_id")
	private Integer power_id;
	
	public static class PK implements Serializable{
		private static final long serialVersionUID = 1L;

		public Integer manager_id;
		public Integer power_id;
		
		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
				}
				if (o == null || getClass() != o.getClass()) {
				return false;
				}
				PK pk = (PK) o;
				return Objects.equals(manager_id, pk.manager_id)
				&& Objects.equals(power_id, pk.power_id);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(manager_id, power_id);
		}
		
	}
	
}

