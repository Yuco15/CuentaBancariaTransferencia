package cajeroweb.modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cajeroweb.modelo.entidades.Cuenta;

/**
 * Repositorio JPA para la entidad Cuenta.
 * Extiende JpaRepository proporcionando métodos CRUD y funcionalidades adicionales
 * para interactuar con la base de datos.
 *
 * Esta interfaz es parte del framework Spring Data JPA, lo que permite
 * el uso de consultas personalizadas mediante métodos de nomenclatura o anotaciones.
 * 
 * @author Andres
 * @version 1.0
 * @since 2024
 */
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

}
