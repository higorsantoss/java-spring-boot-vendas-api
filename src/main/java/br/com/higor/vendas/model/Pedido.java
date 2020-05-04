package br.com.higor.vendas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.higor.vendas.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pedido")
public class Pedido {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id")
		private Integer id;
	    
		@ManyToOne
		@JoinColumn(name = "cliente_id")
		private Cliente cliente;
	    
		@Column(name = "dataPedido")
		private LocalDate dataPedido;
	    
		@Column(name = "total", length = 20, precision = 4, scale = 2)
		private BigDecimal total;

		@Enumerated(EnumType.STRING)
		@Column(name = "status", length = 20)
		private StatusPedido status;
		
		@OneToMany(mappedBy = "pedido")
		private List<ItemPedido> itemPedido; 
	   
}
