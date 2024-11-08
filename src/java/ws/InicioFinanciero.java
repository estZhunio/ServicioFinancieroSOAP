package ws;

import clases.Cliente;
import db.OperacionDB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "InicioFinanciero")
public class InicioFinanciero {

    @WebMethod(operationName = "login")
    public Cliente login(@WebParam(name = "user") String user, @WebParam(name = "password") String password) {
        return OperacionDB.login(user, password);
    }

    @WebMethod(operationName = "register")
    public Boolean register(@WebParam(name = "cliente") Cliente cliente) {
        if (OperacionDB.buscarClientePorNombre(cliente.getUsuario_Cli()) != null) {
            return false;
        }
        OperacionDB.guardarCliente(cliente);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "realizarTransaccion")
    public Cliente realizarTransaccion(@WebParam(name = "operacion") String operacion, @WebParam(name = "cliente") Cliente cliente, @WebParam(name = "valor") Double valor) {
        if (operacion.equalsIgnoreCase("r")) {
            if (cliente.getSaldo_cli() >= valor) {
                cliente.setSaldo_cli(cliente.getSaldo_cli() - valor);
                return OperacionDB.modificarSaldoCliente(cliente);
            } else {
                return null;
            }
        }

        if (operacion.equalsIgnoreCase("d")) {

            if (valor > 0) {
                cliente.setSaldo_cli(cliente.getSaldo_cli() + valor);
                return OperacionDB.modificarSaldoCliente(cliente);
            } else {
                return null;
            }
        }
        
        return null;

    }

}
