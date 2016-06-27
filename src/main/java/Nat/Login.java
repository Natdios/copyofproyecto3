package Nat;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.post;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * Clase controladora para registrar un usuario e iniciar sesión.
 *
 * @author Omar Martinez basado en el trabajo que realizó 
 * Ing. Luis Antonio Ramirez Martinez
 * 
 */
public class Login {

    private static final Logger LOGGER = LogManager.getLogger("Login");
    
    
    public static void main(String[] args) {
       
        /**
         * Ruta inicial de la app muestra el formulario de registro de la app.
         */
        get("/", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String validador = "";
            String nombre = "";
            String mensaje = "";
            
            attributes.put("validador", validador);
            attributes.put("nombre", nombre);
            attributes.put("mensaje", mensaje);
            
            return new ModelAndView(attributes, "registrar.ftl");
        }, new FreeMarkerEngine());

        /**
         * Ruta para procesar el registro enviado desde la ruta inicial.
         */
        post("/registrar", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            System.out.println("Ha llegado");
            String nombre = req.queryParams("nombre");
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            String validador = "";
            String mensaje = "";
            
            if(nombre.equals("")){
                //Falta el usuario
                validador = "Por favor complete el campo nombre";
            }else if (email.equals("")){
                validador = "Por favor complete el campo email";
            }else if (password.equals("")){
                validador = "Por favor complete el campo password";
            }
            
            attributes.put("validador", validador);
            attributes.put("nombre", nombre);
            attributes.put("mensaje", mensaje);
            //TODO Guardar en Base de datos.
            return new ModelAndView(attributes, "registrar.ftl");
        }, new FreeMarkerEngine());
        
        /**
         * Ruta para verificar el inicio de sesión.
         */
        post("/login", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            
            String validador = "";
            String nombre = "";
            String mensaje = "";
            
            String usuario = "n/a";
            
            attributes.put("validador", validador);
            attributes.put("nombre", nombre);
            
            //implementación de mecanismo de registro de actividad de inicio de sesión 
            if(email.equals("e@mail.com") && password.equals("12345")){
                LOGGER.error(String.format("El usuario %s ha iniciado sesión.", email));
                usuario = email;
                attributes.put("mensaje", mensaje);
                attributes.put("usuario", usuario);
                return new ModelAndView(attributes, "home.ftl");
            }else{
                mensaje = "Usuario y/o Password son incorrectos.";
            }
            
            attributes.put("mensaje", mensaje);
            
            return new ModelAndView(attributes, "registrar.ftl");
        }, new FreeMarkerEngine());
        
        
    }

}
