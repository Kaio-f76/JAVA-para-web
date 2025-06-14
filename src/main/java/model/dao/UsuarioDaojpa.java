package model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.loginUsuario;

/**
 *
 * @author kaio
 */
public class UsuarioDaojpa implements InterfaceDao<loginUsuario> {

    @Override
    public void incluir(loginUsuario entidade) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void editar(loginUsuario entidade) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entidade);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void excluir(loginUsuario entidade) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();

            loginUsuario c1 = em.find(loginUsuario.class, entidade.getId());
            em.remove(c1);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public loginUsuario pesquisarPorId(int id) throws Exception {
        loginUsuario c = null;
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();

            c = em.find(loginUsuario.class, id);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return c;
    }

    @Override
    public List<loginUsuario> listar() throws Exception {
        List<loginUsuario> lista = null;
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            lista = em.createQuery("FROM loginUsuario c").getResultList();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return lista;
    }

    @Override
    public List<loginUsuario> filtrarPorNomeESenha(String nome, String senha) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();

        Query query = em.createNamedQuery("loginUsuario.filtrarPorNomeESenha");
        query.setParameter("nome", nome);
        query.setParameter("senha", senha);
        List<loginUsuario> resultado = query.getResultList();
        return resultado;
    }
    
    @Override
    public List<loginUsuario> filtrarPorNome(String nome, int loginUsuario) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();

        Query query = em.createNamedQuery("loginUsuario.filtrarPorNome");
        query.setParameter("nome", nome);
        query.setParameter("loginUsuarioId", loginUsuario);
        List<loginUsuario> resultado = query.getResultList();
        return resultado;
    }

}
