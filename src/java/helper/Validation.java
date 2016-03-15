/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import dao.UsuarioJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Usuario;

/**
 *
 * @author Tiago
 */
public class Validation {
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");

    public static boolean validationRegister(Usuario user) {

        boolean error = false;

        if (user.getNome().equals("")
                || user.getNome().trim().length() < 3
                || user.getNome().trim().length() > 20) {
            error = true;
        }

        if (user.getSobrenome().equals("")
                || user.getSobrenome().trim().length() < 3
                || user.getSobrenome().trim().length() > 20) {
            error = true;
        }

        if (user.getEmail().equals("")) {
            error = true;
        } else {
            String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            boolean ok = user.getEmail().matches(regex);
            if (!ok) {
                error = true;
            }
        }

        if (user.getSenha().trim().length() < 6) {
            error = true;
        }
        return error;
    }
    
    public static boolean checksUserRegister(Usuario usuario){
        
        Usuario user = new UsuarioJpaController(emf).findUsuarioByEmail(usuario);
        
        if(user == null){
            return false;
        }
        return true;
    }
}
