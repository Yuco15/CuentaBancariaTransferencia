package cajeroweb.modelo.dao;

import java.util.List;

import cajeroweb.modelo.entidades.Movimiento;

/**
 * Interfaz DAO para la entidad Movimiento.
 * Define los métodos para realizar operaciones CRUD relacionadas con los movimientos
 * en la base de datos, proporcionando una abstracción para la capa de persistencia.
 * Implementaciones típicas de esta interfaz interactuarán con el repositorio de datos.
 *
 * @author Andres
 * @version 1.0
 * @since 2024
 */
public interface MovimientoDao {

    /**
     * Inserta un nuevo movimiento en la base de datos.
     *
     * @param entidad la entidad Movimiento a insertar
     * @return la entidad Movimiento guardada si la operación es exitosa
     */
    Movimiento insertUno(Movimiento entidad);

    /**
     * Obtiene una lista de movimientos asociados a una cuenta específica.
     *
     * @param idCuenta el identificador de la cuenta para la que se quieren obtener los movimientos
     * @return una lista de objetos Movimiento correspondientes a la cuenta especificada
     */
    List<Movimiento> movimientos(int idCuenta);
}
