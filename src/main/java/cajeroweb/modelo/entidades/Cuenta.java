package cajeroweb.modelo.entidades;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity 
@Table(name="cuentas")
public class Cuenta implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name="id_cuenta")
	private int idCuenta;
	private double saldo;
	@Column(name="tipo_cuenta")
	private String tipoCuenta;
	
	public void ingresar(int saldo) {
		this.saldo += saldo; 
	}
	
	public boolean extraer(int saldo) {
		if (saldo <= this.saldo ) {
			this.saldo -= saldo; 
			return true; 
		}
		return false; 
	}
}
