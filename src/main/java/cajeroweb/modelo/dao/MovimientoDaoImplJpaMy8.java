package cajeroweb.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cajeroweb.modelo.entidades.Movimiento;
import cajeroweb.modelo.repository.MovimientoRepository;

/**
 * Implementación del DAO para la entidad Movimiento utilizando Spring Data JPA.
 * Esta clase interactúa con la base de datos a través del repositorio MovimientoRepository,
 * proporcionando métodos para insertar movimientos y obtener el historial de movimientos de una cuenta
 *
 * @author Andres
 * @version 1.0
 * @since 2024
 */
@Repository
public class MovimientoDaoImplJpaMy8 implements MovimientoDao {

    @Autowired
    private MovimientoRepository mrepo; // Inyección del repositorio de Movimiento

    /**
     * Inserta un nuevo movimiento en la base de datos.
     * Utiliza el repositorio para guardar la entidad Movimiento.
     *
     * @param entidad la entidad Movimiento a insertar
     * @return la entidad guardada si la operación es exitosa, o null si ocurre un error
     */
    @Override
    public Movimiento insertUno(Movimiento entidad) {
        try {
            return mrepo.save(entidad);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene la lista de movimientos asociados a una cuenta específica.
     * Utiliza el método personalizado del repositorio para buscar los movimientos por el ID de la cuenta.
     *
     * @param idCuenta el identificador de la cuenta
     * @return una lista de movimientos asociados a la cuenta especificada
     */
    @Override
    public List<Movimiento> movimientos(int idCuenta) {
        return mrepo.buscarPorNumeroCuenta(idCuenta);
    }
}
