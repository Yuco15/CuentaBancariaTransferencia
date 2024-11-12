package cajeroweb.modelo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cajeroweb.modelo.entidades.Movimiento;

/**
 * Repositorio JPA para la entidad Movimiento.
 * Extiende JpaRepository proporcionando métodos CRUD y funcionalidades adicionales
 * para interactuar con la base de datos.
 *
 * Esta interfaz incluye un método personalizado para buscar movimientos
 * basados en el identificador de la cuenta, utilizando una consulta JPQL.
 *
 * @author Andres
 * @version 1.0
 * @since 2024
 */
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    /**
     * Busca todos los movimientos asociados a una cuenta específica.
     * Utiliza una consulta JPQL para obtener los movimientos filtrados por el ID de la cuenta.
     *
     * @param idCuenta el identificador de la cuenta cuyos movimientos se desean obtener
     * @return una lista de objetos Movimiento correspondientes a la cuenta especificada
     */
    @Query("select m from Movimiento m where m.cuenta.idCuenta = ?1")
    public List<Movimiento> buscarPorNumeroCuenta(int idCuenta);
}
