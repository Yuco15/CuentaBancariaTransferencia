package cajeroweb.modelo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cajeroweb.modelo.entidades.Cuenta;
import cajeroweb.modelo.repository.CuentaRepository;

/**
 * Implementación del DAO para la entidad Cuenta utilizando Spring Data JPA.
 * Esta clase interactúa con la base de datos a través del repositorio CuentaRepository,
 * ofreciendo métodos para buscar, insertar, actualizar y realizar operaciones bancarias
 * como ingresos y extracciones.
 *
 * @author Andres
 * @version 1.0
 * @since 2024
 */
@Repository
public class CuentaDaoImplJpaMy8 implements CuentaDao {

    @Autowired
    private CuentaRepository crepo; // Inyección del repositorio de Cuenta

    /**
     * Busca una cuenta por su clave primaria (ID).
     *
     * @param clavePk la clave primaria de la cuenta
     * @return la entidad Cuenta si se encuentra, o null si no existe
     */
    @Override
    public Cuenta buscarUno(int clavePk) {
        return crepo.findById(clavePk).orElse(null);
    }

    /**
     * Actualiza una cuenta existente en la base de datos.
     * Comprueba si la cuenta existe antes de actualizar.
     *
     * @param entidad la entidad Cuenta a actualizar
     * @return 1 si la operación es exitosa, 0 si no se encuentra la cuenta o ocurre un error
     */
    @Override
    public int updateUno(Cuenta entidad) {
        try {
            if (crepo.existsById(entidad.getIdCuenta())) {
                crepo.save(entidad);
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Realiza un ingreso en la cuenta especificada.
     * Incrementa el saldo de la cuenta y actualiza la entidad en la base de datos.
     *
     * @param cuenta la cuenta en la que se realizará el ingreso
     * @param saldo la cantidad a ingresar
     * @return 1 si la operación es exitosa, 0 si ocurre un error
     */
    @Override
    public int ingreso(Cuenta cuenta, int saldo) {
        cuenta.ingresar(saldo);
        return updateUno(cuenta);
    }

    /**
     * Realiza una extracción de la cuenta especificada.
     * Comprueba si hay saldo suficiente antes de realizar la operación y actualiza la entidad en la base de datos.
     *
     * @param cuenta la cuenta de la que se realizará la extracción
     * @param saldo la cantidad a extraer
     * @return 1 si la operación es exitosa, 0 si el saldo es insuficiente o ocurre un error
     */
    @Override
    public int extraer(Cuenta cuenta, int saldo) {
        if (cuenta.extraer(saldo)) {
            return updateUno(cuenta);
        } else {
            return 0;
        }
    }
}
