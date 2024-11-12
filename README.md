Proyecto: Cajero Web
Este proyecto implementa un sistema de cajero automático basado en una aplicación web utilizando Spring Boot y Spring Data JPA. Permite gestionar cuentas bancarias y realizar operaciones comunes como iniciar sesión, ingresar dinero, extraer dinero y consultar movimientos.

Tecnologías utilizadas<br>
Java 17<br>
Spring Boot<br>
Spring Data JPA<br>
Jakarta Persistence API<br>
Lombok<br>
H2 Database (puede reemplazarse por otras bases de datos)<br>
Estructura del proyecto<br>
El proyecto sigue una estructura típica de Spring Boot con los siguientes paquetes clave:<br>

modelo.entidades: Contiene las clases de entidad que representan las tablas de la base de datos.<br>
modelo.dao: Define las interfaces y las implementaciones DAO para manejar la persistencia de datos.<br>
modelo.repository: Extiende JpaRepository para facilitar el acceso a datos con métodos personalizados.<br>
controlador: Contiene los controladores que manejan las solicitudes HTTP y coordinan la lógica de negocio.<br>

Clases principales<br>
1. Entidad Cuenta<br>
Representa una cuenta bancaria. Incluye atributos como idCuenta, saldo y tipoCuenta. La clase utiliza Lombok para generar métodos automáticamente y JPA para el mapeo a la base de datos.<br>

Métodos principales:<br>
ingresar(int saldo): Incrementa el saldo de la cuenta.<br>
extraer(int saldo): Verifica si hay saldo suficiente y realiza una extracción.<br>

2. Entidad Movimiento<br>
Representa un registro de transacción (ingreso o extracción) asociado a una cuenta. Incluye atributos como idMovimiento, fecha, cantidad y tipoMovimiento.<br>

3. Interfaz CuentaRepository<br>
Extiende JpaRepository<Cuenta, Integer> para proporcionar operaciones CRUD automáticas para la entidad Cuenta.<br>

4. Interfaz MovimientoRepository<br>
Extiende JpaRepository<Movimiento, Integer>. Define un método personalizado:<br> 
buscarPorNumeroCuenta(int idCuenta): Busca movimientos filtrados por el ID de la cuenta.<br>

5. DAO CuentaDaoImplJpaMy8<br>
Implementa la interfaz CuentaDao utilizando JPA. Proporciona métodos para:<br>
Buscar, insertar y actualizar cuentas.<br>
Realizar operaciones de ingreso y extracción de saldo.<br>

6. DAO MovimientoDaoImplJpaMy8<br>
Implementa la interfaz MovimientoDao. Proporciona métodos para:<br>
Insertar un movimiento en la base de datos.<br>
Obtener movimientos asociados a una cuenta específica.<br>

7. Controlador CuentaController<br>
Maneja las solicitudes HTTP para la gestión de cuentas bancarias:<br>
/login: Inicia sesión para una cuenta.<br>
/logout: Cierra la sesión actual.<br>
/ingresar: Muestra el formulario de ingreso y procesa la transacción.<br>
/extraer: Muestra el formulario de extracción y procesa la transacción.<br>
/movimientos: Muestra el historial de movimientos de la cuenta.<br>
