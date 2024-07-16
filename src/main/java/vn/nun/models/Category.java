package vn.nun.models;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "categoryName")
	private String categoryName;
	
	@Column(name = "categoryStatus")
	private Boolean categoryStatus;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private List<Product> products;

}
