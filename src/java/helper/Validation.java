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

    public static boolean validarCadastro(Usuario usuario) {

        boolean temErro = false;

        if (usuario.getNome().equals("")
                || usuario.getNome().trim().length() < 3
                || usuario.getNome().trim().length() > 20) {
            temErro = true;
        }

        if (usuario.getSobrenome().equals("")
                || usuario.getSobrenome().trim().length() < 3
                || usuario.getSobrenome().trim().length() > 20) {
            temErro = true;
        }

        if (usuario.getEmail().equals("")) {
            temErro = true;
        } else {
            String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            boolean ok = usuario.getEmail().matches(regex);
            if (!ok) {
                temErro = true;
            }
        }

        if (usuario.getSenha().trim().length() < 6) {
            temErro = true;
        }
        return temErro;
    }
    
    public static boolean verificarUsuarioJaCadastrado(Usuario usuario){
        
        Usuario user = new UsuarioJpaController(emf).findUsuarioByEmail(usuario);
        
        if(user == null){
            return false;
        }
        return true;
    }
}
