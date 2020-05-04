package br.com.higor.vendas.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name ="cliente")
public class Cliente {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id")
	private Integer id;
    
	@Column(name = "nome")
	private String nome;

	@Column(name = "cpf", length = 11)
	private String cpf;
	
	@JsonIgnore // annotation para nao aparecer na estrutura json 
	@OneToMany( mappedBy = "cliente" , fetch = FetchType.LAZY)
	private Set<Pedido> pedidos;
}
