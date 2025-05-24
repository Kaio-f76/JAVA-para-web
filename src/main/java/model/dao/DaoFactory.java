package model.dao;

/**
 *
 * @author kaio
 */
public class DaoFactory {
    
     public static UsuarioDaojpa novoLoginDAO() throws Exception {
        return new UsuarioDaojpa();
    }
    
}
