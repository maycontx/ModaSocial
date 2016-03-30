package helper;

import dao.UsuarioJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Produto;
import model.Usuario;

/**
 *
 * @author Tiago
 */
public class Session {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");

    private static Usuario login(Usuario user, HttpServletRequest request) {

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

    public static Usuario createCookie(boolean keep, String email, String senha,
            HttpServletResponse response, HttpServletRequest request) {

        Usuario user = null;

        if (Validation.validateAdministratorPassword(senha)) {
            user = new UsuarioJpaController(emf).findUsuarioByEmailAdmin(email);
        } else {
            user = new UsuarioJpaController(emf).checkEmailAndPassword(email, senha);
        }

        if (user != null) {
            Cookie cookieLogin = new Cookie("ModaSocialLogin", user.getEmail());
            if (keep) {
                cookieLogin.setMaxAge(60 * 60 * 24 * 360);
            } else {
                cookieLogin.setMaxAge(-1);
            }
            response.addCookie(cookieLogin);
        }
        return login(user, request);
    }

    public static String findCookie(HttpServletRequest request) {

        String login = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("ModaSocialLogin")) {
                    login = c.getValue();
                }
            }
        }
        return login;
    }
    
    public static void addProductInShoppingCart(Produto produto, HttpServletRequest request){
        List<Produto> shoppingCart = (List<Produto>) request.getSession().getAttribute("shoppingCart");
        
        if(shoppingCart == null){
            shoppingCart = new ArrayList<Produto>();
        }
        shoppingCart.add(produto);
        request.getSession().setAttribute("shoppingCart", shoppingCart);
    }

}
