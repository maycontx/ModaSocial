/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import dao.UsuarioJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;

/**
 *
 * @author Tiago
 */
public class Session {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");

    public static Usuario login(String email, String senha, HttpServletRequest request) {

        Usuario user = new UsuarioJpaController(emf).checkEmailAndPassword(email, senha);
        if (user != null) {
            request.getSession().setAttribute("user", user);
        }
        return user;
    }

    public static void logout(HttpServletRequest request) {

        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("ModaSocialLogin")) {
                request.getSession().removeAttribute("ModaSocialLogin");
            }
        }

    }

}
