package br.com.higor.vendas.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
    
	@Column(name = "descricao")
	@NotEmpty(message = "Campo descricao e obrigatorio. ")
	private String descricao;
	
	@Column(name = "preco", precision = 4, scale = 2)
	@NotNull(message = "Campo preco e obrigatorio. ")
    private BigDecimal preco;

	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private Set<Pedido> pedidos;
	
 }
