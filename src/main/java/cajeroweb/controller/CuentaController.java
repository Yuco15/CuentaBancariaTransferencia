package cajeroweb.controller;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cajeroweb.modelo.dao.CuentaDao;
import cajeroweb.modelo.dao.MovimientoDao;
import cajeroweb.modelo.entidades.Cuenta;
import cajeroweb.modelo.entidades.Movimiento;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador que maneja las rutas y solicitudes relacionadas con la gestión de cuentas bancarias.
 * Proporciona funcionalidades para operaciones como inicio de sesión, ingreso, extracción y visualización de movimientos.
 * Este controlador utiliza inyección de dependencias para interactuar con los DAO de Cuenta y Movimiento.
 *
 * @author Andres Matabuena
 * @version 1.0
 * @since 2024
 */
@Controller
public class CuentaController {

    @Autowired
    private CuentaDao cdao; // Inyección del DAO de Cuenta para gestionar los datos de la cuenta

    @Autowired
    private MovimientoDao mdao; // Inyección del DAO de Movimiento para gestionar los datos de los movimientos

    /**
     * Maneja las solicitudes GET para la página de inicio.
     * Soporta varias rutas: `""`, `"/"`, y `"/home"`.
     * Verifica si hay una sesión activa con una cuenta logueada.
     *
     * @param sesion la sesión HTTP actual
     * @return el nombre de la vista "home" si la cuenta está en sesión,
     *         o "FormLogin" si no hay una cuenta activa
     */
    @GetMapping({(""),("/"),("/home")})
    public String home(HttpSession sesion) {
    	if (sesion.getAttribute("cuenta") != null) {
            return "home";
        }
    	return "FormLogin"; 
    }
    
    /**
     * Maneja la solicitud GET para la página de inicio de sesión.
     * Si la sesión ya contiene una cuenta activa, redirige al usuario a la página principal (home).
     *
     * @param sesion la sesión HTTP actual
     * @return el nombre de la vista a renderizar (home o FormLogin)
     */
    @GetMapping("/login")
    public String inicio() {
        
        return "FormLogin";
    }

    /**
     * Maneja la solicitud POST para la confirmación de cuenta en el inicio de sesión.
     * Verifica si el número de cuenta es válido y establece la cuenta en la sesión.
     *
     * @param numeroCuenta número de la cuenta ingresado por el usuario
     * @param sesion la sesión HTTP actual
     * @param ratt atributos para redirección con mensajes flash
     * @return redirección a la página principal si la cuenta es válida, o a la página de login si no lo es
     */
    @PostMapping("/login")
    public String confirmacionCuenta(@RequestParam int numeroCuenta, HttpSession sesion, RedirectAttributes ratt) {
        Cuenta cuenta = cdao.buscarUno(numeroCuenta);

        if (cuenta != null) {
            sesion.setAttribute("cuenta", cuenta);
            return "home";
        } else {
            ratt.addFlashAttribute("mensaje", "CUENTA INCORRECTA");
            return "redirect:/login";
        }
    }

    /**
     * Maneja la solicitud GET para cerrar sesión.
     * Elimina la cuenta de la sesión y la invalida.
     *
     * @param sesion la sesión HTTP actual
     * @return redirección a la página de login
     */
    @GetMapping("/logout")
    public String cerrar(HttpSession sesion) {
        sesion.removeAttribute("cuenta");
        sesion.invalidate();
        return "forward:/login";
    }

    /**
     * Maneja la solicitud GET para mostrar el formulario de ingreso de dinero.
     *
     * @return el nombre de la vista 'ingresar'
     */
    @GetMapping("/ingresar")
    public String ingreso() {
        return "ingresar";
    }

    /**
     * Maneja la solicitud POST para procesar el ingreso de dinero en la cuenta.
     * Valida el monto y la cuenta, luego actualiza el saldo y registra el movimiento.
     *
     * @param ingreso cantidad de dinero a ingresar
     * @param ratt atributos para redirección con mensajes flash
     * @param sesion la sesión HTTP actual
     * @return redirección a la página principal si el ingreso es exitoso, o al formulario si hay errores
     */
    @PostMapping("/ingresar")
    public String procIngreso(@RequestParam double ingreso, RedirectAttributes ratt, HttpSession sesion) {
        Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");
        
        //Verificamos si la cuenta existe y si el valor a extraer es positivo
        if (cuenta == null || ingreso <= 0) {
            ratt.addFlashAttribute("mensaje", "Operación incorrecta: cantidad incorrecta.");
            return "redirect:/ingresar";
        }

        Movimiento movimiento = new Movimiento(0, cuenta, new Date(), ingreso, "Ingreso");
        mdao.insertUno(movimiento);
        cdao.ingreso(cuenta, ingreso);
        ratt.addFlashAttribute("mensaje", "Ingreso realizado con éxito");

        return "redirect:/";
    }

    /**
     * Maneja la solicitud GET para mostrar el formulario de extracción de dinero.
     *
     * @return el nombre de la vista 'extraer'
     */
    @GetMapping("/extraer")
    public String retirarDinero() {
        return "extraer";
    }

    /**
     * Maneja la solicitud POST para procesar la extracción de dinero de la cuenta.
     * Valida el monto, la cuenta y comprueba si hay saldo suficiente antes de proceder.
     *
     * @param extraer cantidad de dinero a extraer
     * @param ratt atributos para redirección con mensajes flash
     * @param sesion la sesión HTTP actual
     * @return redirección a la página principal si la extracción es exitosa, o al formulario si hay errores
     */
    @PostMapping("/extraer")
    public String procExtraer(@RequestParam double extraer, RedirectAttributes ratt, HttpSession sesion) {
        Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");

        // Verificamos si la cuenta existe y si el valor a extraer es positivo
        if (cuenta == null || extraer <= 0) {
            ratt.addFlashAttribute("mensaje", "Operación incorrecta: cantidad incorrecta.");
            return "redirect:/extraer";
        }

        // Comprobamos si el saldo es suficiente para realizar la extracción
        if (cdao.extraer(cuenta, extraer) == 1) {
            ratt.addFlashAttribute("mensaje", "Extracción realizada con éxito");
            Movimiento movimiento = new Movimiento(0, cuenta, new Date(), -extraer, "Extracción");
            mdao.insertUno(movimiento);
            return "redirect:/";
        } else {
            ratt.addFlashAttribute("mensaje", "Operación incorrecta: saldo insuficiente");
            return "redirect:/extraer";
        }
    }

    /**
     * Maneja la solicitud GET para mostrar el historial de movimientos de la cuenta.
     * Obtiene los movimientos del DAO y los agrega al modelo.
     *
     * @param model el modelo para pasar datos a la vista
     * @param sesion la sesión HTTP actual
     * @return el nombre de la vista 'movimientos'
     */
    @GetMapping("/movimientos")
    public String movimientos(Model model, HttpSession sesion) {
        model.addAttribute("movimientos", mdao.movimientos(((Cuenta) sesion.getAttribute("cuenta")).getIdCuenta()));
        return "movimientos";
    }
    
    /**
     * Maneja la solicitud GET para mostrar el formulario de transferencia.
     * Presenta al usuario una vista donde puede ingresar los detalles de la transferencia,
     * como la cuenta de destino y la cantidad a transferir.
     *
     * @return el nombre de la vista "transferencia" para renderizar el formulario de transferencia
     */
    @GetMapping("/transferencia")
    public String transferencia() {
        return "transferencia";
    }
    
    /**
     * Maneja la solicitud POST para realizar una transferencia de fondos entre dos cuentas.
     * Verifica la validez de la cuenta origen, la cuenta destino y la cantidad a transferir
     * antes de realizar la operación. Registra los movimientos de transferencia para ambas cuentas.
     *
     * @param cantidad la cantidad de dinero a transferir
     * @param idCuentaDestino el identificador de la cuenta de destino
     * @param ratt atributos para redirección con mensajes flash
     * @param sesion la sesión HTTP actual
     * @return redirección a la página de transferencia en caso de error, o a la página principal si la operación es exitosa
     */
    @PostMapping("/transferencia")
    public String procTransferencia(@RequestParam double cantidad, @RequestParam int idCuentaDestino, RedirectAttributes ratt, HttpSession sesion) {
    	final String redirectTransferencia =  "redirect:/transferencia"; 
    	
    	Cuenta cuentaDestino = cdao.buscarUno(idCuentaDestino);
    	Cuenta cuentaOrigen = (Cuenta) sesion.getAttribute("cuenta");
    	
    	// Validar cuenta origen en la sesión, cuenta de destino, y si cuenta de destino es igual a la de origen
    	if(cuentaOrigen == null || cuentaDestino == null || cuentaOrigen.getIdCuenta() == idCuentaDestino) {
    		ratt.addFlashAttribute("mensaje", "Operación incorrecta: Cuenta incorrecta");
    		return redirectTransferencia; 
    	}
    	// Validar cantidad a transferir
    	if(cantidad <= 0) {
    		ratt.addFlashAttribute("mensaje", "Operación incorrecta: Cantidad incorrecta");
    		return redirectTransferencia; 
    	}
    	
    	// Realizar la transferencia y validar saldo suficiente
    	if(cdao.transferencia(cuentaOrigen, cuentaDestino, cantidad)!= 1) {
    		ratt.addFlashAttribute("mensaje", "Operación incorrecta: Saldo insuficiente");
    		return redirectTransferencia; 
    	}
    	// Registrar movimientos para cuenta origen y destino
    	Movimiento movimientoOrigen = new Movimiento(0, cuentaOrigen, new Date(), -cantidad, "Transferencia");
		Movimiento movimientoDestino = new Movimiento(0, cuentaDestino, new Date(), cantidad, "Transferencia");
		mdao.insertUno(movimientoDestino);
		mdao.insertUno(movimientoOrigen);
		// Mensaje de éxito y redirección a la página principal
		ratt.addFlashAttribute("mensaje", "Transferencia realizada con éxito");
		return "redirect:/";
    }
    
}

