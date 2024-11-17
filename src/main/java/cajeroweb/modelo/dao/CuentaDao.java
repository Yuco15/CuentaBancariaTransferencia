package cajeroweb.modelo.dao;

import cajeroweb.modelo.entidades.Cuenta;

/**
 * Interfaz DAO para la entidad Cuenta.
 * Define los métodos para realizar operaciones CRUD y transacciones bancarias
 * relacionadas con las cuentas, proporcionando una abstracción para la capa de persistencia.
 * Las implementaciones de esta interfaz interactuarán con el repositorio de datos para gestionar
 * la entidad Cuenta.
 *
 * @author Andres
 * @version 1.0
 * @since 2024
 */
public interface CuentaDao {

    /**
     * Busca una cuenta en la base de datos utilizando la clave primaria (ID).
     *
     * @param clavePk la clave primaria de la cuenta
     * @return la entidad Cuenta si se encuentra, o null si no existe
     */
    Cuenta buscarUno(int clavePk);

    /**
     * Actualiza una cuenta existente en la base de datos.
     *
     * @param entidad la entidad Cuenta a actualizar
     * @return 1 si la actualización es exitosa, 0 si no se encuentra la cuenta o si ocurre un error
     */
    int updateUno(Cuenta entidad);

    /**
     * Realiza un ingreso de dinero en la cuenta especificada.
     * Incrementa el saldo de la cuenta y actualiza la entidad en la base de datos.
     *
     * @param cuenta la cuenta en la que se realizará el ingreso
     * @param saldo la cantidad de dinero a ingresar
     * @return 1 si el ingreso es exitoso, 0 si ocurre un error
     */
    int ingreso(Cuenta cuenta, int saldo);

    /**
     * Realiza una extracción de dinero de la cuenta especificada.
     * Comprueba si hay saldo suficiente antes de realizar la operación y actualiza la entidad en la base de datos.
     *
     * @param cuenta la cuenta de la que se realizará la extracción
     * @param saldo la cantidad de dinero a extraer
     * @return 1 si la extracción es exitosa, 0 si el saldo es insuficiente o si ocurre un error
     */
    int extraer(Cuenta cuenta, int saldo);
}
