Proyecto: Cajero Web
Este proyecto implementa un sistema de cajero automático basado en una aplicación web utilizando Spring Boot y Spring Data JPA. Permite gestionar cuentas bancarias y realizar operaciones comunes como iniciar sesión, ingresar dinero, extraer dinero y consultar movimientos.

Tecnologías utilizadas
Java 17
Spring Boot
Spring Data JPA
Jakarta Persistence API
Lombok
H2 Database (puede reemplazarse por otras bases de datos)
Estructura del proyecto
El proyecto sigue una estructura típica de Spring Boot con los siguientes paquetes clave:

modelo.entidades: Contiene las clases de entidad que representan las tablas de la base de datos.
modelo.dao: Define las interfaces y las implementaciones DAO para manejar la persistencia de datos.
modelo.repository: Extiende JpaRepository para facilitar el acceso a datos con métodos personalizados.
controlador: Contiene los controladores que manejan las solicitudes HTTP y coordinan la lógica de negocio.
Clases principales
1. Entidad Cuenta
Representa una cuenta bancaria. Incluye atributos como idCuenta, saldo y tipoCuenta. La clase utiliza Lombok para generar métodos automáticamente y JPA para el mapeo a la base de datos.

Métodos principales:
ingresar(int saldo): Incrementa el saldo de la cuenta.
extraer(int saldo): Verifica si hay saldo suficiente y realiza una extracción.
2. Entidad Movimiento
Representa un registro de transacción (ingreso o extracción) asociado a una cuenta. Incluye atributos como idMovimiento, fecha, cantidad y tipoMovimiento.

3. Interfaz CuentaRepository
Extiende JpaRepository<Cuenta, Integer> para proporcionar operaciones CRUD automáticas para la entidad Cuenta.

4. Interfaz MovimientoRepository
Extiende JpaRepository<Movimiento, Integer>. Define un método personalizado:

buscarPorNumeroCuenta(int idCuenta): Busca movimientos filtrados por el ID de la cuenta.
5. DAO CuentaDaoImplJpaMy8
Implementa la interfaz CuentaDao utilizando JPA. Proporciona métodos para:

Buscar, insertar y actualizar cuentas.
Realizar operaciones de ingreso y extracción de saldo.
6. DAO MovimientoDaoImplJpaMy8
Implementa la interfaz MovimientoDao. Proporciona métodos para:

Insertar un movimiento en la base de datos.
Obtener movimientos asociados a una cuenta específica.
7. Controlador CuentaController
Maneja las solicitudes HTTP para la gestión de cuentas bancarias:

/login: Inicia sesión para una cuenta.
/logout: Cierra la sesión actual.
/ingresar: Muestra el formulario de ingreso y procesa la transacción.
/extraer: Muestra el formulario de extracción y procesa la transacción.
/movimientos: Muestra el historial de movimientos de la cuenta.
