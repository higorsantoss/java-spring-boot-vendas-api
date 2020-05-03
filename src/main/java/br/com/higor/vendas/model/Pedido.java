package br.com.higor.vendas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	    
		@Column(name = "total", length = 20, precision = 2)
		private BigDecimal total;

		@OneToMany(mappedBy = "pedido")
		private List<ItemPedido> itemPedido; 
		
	    public Pedido() {
		}

		public Pedido(Cliente cliente, LocalDate dataPedido, BigDecimal total, List<ItemPedido> itemPedido) {
			this.cliente = cliente;
			this.dataPedido = dataPedido;
			this.total = total;
			this.itemPedido = itemPedido;
		}

		public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public Cliente getCliente() {
	        return cliente;
	    }

	    public void setCliente(Cliente cliente) {
	        this.cliente = cliente;
	    }

	    public LocalDate getDataPedido() {
	        return dataPedido;
	    }

	    public void setDataPedido(LocalDate dataPedido) {
	        this.dataPedido = dataPedido;
	    }

	    public BigDecimal getTotal() {
	        return total;
	    }

	    public void setTotal(BigDecimal total) {
	        this.total = total;
	    }

		public List<ItemPedido> getItemPedido() {
			return itemPedido;
		}

		public void setItemPedido(List<ItemPedido> itemPedido) {
			this.itemPedido = itemPedido;
		}

		@Override
		public String toString() {
			return "Pedido [id=" + id + ", cliente=" + cliente + ", dataPedido=" + dataPedido + ", total=" + total
					+ ", itemPedido=" + itemPedido + "]";
		}
		
		
	    
}
