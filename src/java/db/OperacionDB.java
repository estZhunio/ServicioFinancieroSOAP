/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import clases.Cliente;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

/**
 *
 * @author migue
 */
abstract public class OperacionDB {

    static String ruta = "C:\\Users\\migue\\OneDrive\\Documentos\\NetBeansProjects\\ServicioFinancieroSOAP\\src\\java\\db\\baseDatos.yap";

    public static String generarNuevoId() {
        ObjectContainer db = null;
        try {
            // Abrir la base de datos
            db = Db4oEmbedded.openFile(ruta);

            // Crear la consulta para obtener el último cliente
            Query query = db.query();
            query.constrain(Cliente.class);
            query.descend("id_cli").orderDescending();

            Cliente ultimoUsuario = (Cliente) query.execute().next();

            // Generar el nuevo ID
            if (ultimoUsuario == null) {
                // Si no hay usuarios, empezamos con USER-001
                return "USER-001";
            } else {
                // Extraemos el número del último ID y lo incrementamos
                String ultimoId = ultimoUsuario.getId_cli();
                int numero = Integer.parseInt(ultimoId.split("-")[1]);
                numero++;  // Incrementamos el número

                // Formateamos el nuevo ID con ceros al frente si es necesario
                return String.format("USER-%03d", numero);
            }
        } catch (Exception e) {
            System.out.println("Error al generar el nuevo ID: " + e.getMessage());
            return null;
        } finally {
            // Cerrar la base de datos
            if (db != null) {
                db.close();  // Aseguramos que la base de datos se cierra
                System.out.println("Base de datos cerrada.");
            }
        }
    }

    public static Cliente buscarClientePorNombre(String nombre) {
        ObjectContainer db = null;
        try {
            db = Db4oEmbedded.openFile(ruta);
            // Crear un objeto de ejemplo con el nombre
            Cliente ejemplo = new Cliente();
            ejemplo.setUsuario_Cli(nombre);  // Establece solo el atributo de búsqueda

            // Hacer la consulta
            ObjectSet<Cliente> resultado = db.queryByExample(ejemplo);

            if (resultado.hasNext()) {
                return resultado.next();  // Retorna el primer resultado
            } else {
                System.out.println("Cliente no encontrado.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el cliente: " + e.getMessage());
            return null;
        } finally {
            if (db != null) {
                db.close();  // Aseguramos que la base de datos se cierra
                System.out.println("Base de datos cerrada.");
            }
        }
    }

    public static Cliente login(String usuario, String password) {
        // Abre la base de datos db4o
        ObjectContainer db = Db4oEmbedded.openFile(ruta);

        try {
            // Crear un objeto de ejemplo con los atributos usuario y contraseña
            Cliente ejemplo = new Cliente();
            ejemplo.setUsuario_Cli(usuario);  // Establece el nombre de usuario
            ejemplo.setPassword_cli(password);  // Establece la contraseña para la búsqueda

            // Hacer la consulta
            ObjectSet<Cliente> resultado = db.queryByExample(ejemplo);

            // Si se encuentra un cliente que coincida
            if (resultado.hasNext()) {
                Cliente clienteEncontrado = resultado.next();

                // Verifica si el cliente encontrado tiene la contraseña correcta
                if (clienteEncontrado.getPassword_cli().equals(password)) {
                    return clienteEncontrado;  // Si la contraseña coincide, retorna el cliente
                } else {
                    System.out.println("Contraseña incorrecta.");
                    return null;
                }
            } else {
                System.out.println("Cliente no encontrado.");
                return null;  // Si no se encuentra el cliente
            }
        } finally {
            // Cierra la base de datos para liberar recursos
            db.close();
        }
    }

    public static boolean guardarCliente(Cliente cliente) {
        cliente.setId_cli(generarNuevoId());
        ObjectContainer db = Db4oEmbedded.openFile(ruta);

        try {
            db.store(cliente);  // Guarda el objeto Cliente

            // Realiza un commit para asegurar que los datos se guarden de manera permanente
            db.commit();  // Esto asegura que los cambios sean persistentes

            System.out.println("Cliente guardado correctamente.");
            return true;  // Retorna true si se guardó correctamente
        } catch (Exception e) {
            System.out.println("Error al guardar el cliente: " + e.getMessage());
            return false;  // Retorna false si ocurre algún error
        } finally {
            db.close();
        }
    }

    public static Cliente modificarSaldoCliente(Cliente cliente) {
    ObjectContainer db = null;
    
    
    try {
        // Abrir la base de datos
        db = Db4oEmbedded.openFile(ruta);

        db.store(cliente); 

        System.out.println("Cliente actualizado con nuevo saldo: " + cliente.getSaldo_cli());
        
        return cliente;
        
    } catch (Exception e) {
        System.out.println("Error al modificar el cliente: " + e.getMessage());
        return null;
    } finally {
        // Cerrar la base de datos
        if (db != null) {
            db.close();  // Aseguramos que la base de datos se cierra
            System.out.println("Base de datos cerrada.");
        }
    }
}


}
