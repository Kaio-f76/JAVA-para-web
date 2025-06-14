
package model.dao;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import javax.persistence.Query;
import model.dadosFinanceirosUsuario;

public class DadosFinanceirosDaoJpa implements InterfaceDao<dadosFinanceirosUsuario> {

    @Override
    @Transactional
    public void incluir(dadosFinanceirosUsuario entidade) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidade);  // Persistir a entidade de dados financeiros
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    @Transactional
    public void editar(dadosFinanceirosUsuario entidade) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entidade);  // Atualizar os dados financeiros no banco
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    @Transactional
    public void excluir(dadosFinanceirosUsuario entidade) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();

            dadosFinanceirosUsuario df = em.find(dadosFinanceirosUsuario.class, entidade.getId());
            em.remove(df);  // Remover os dados financeiros do banco

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public dadosFinanceirosUsuario pesquisarPorId(int id) throws Exception {
        dadosFinanceirosUsuario df = null;
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            df = em.find(dadosFinanceirosUsuario.class, id);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return df;
    }

    @Override
    public List<dadosFinanceirosUsuario> listar() throws Exception {
        List<dadosFinanceirosUsuario> lista = null;
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            lista = em.createQuery("FROM dadosFinanceirosUsuario df", dadosFinanceirosUsuario.class).getResultList();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return lista;
    }

    /**
     * Método para buscar os dados financeiros do usuário logado com base no ID
     * do loginUsuario
     *
     * @param usuarioId ID do usuário logado
     * @return Lista de dados financeiros do usuário
     * @throws Exception
     */
    public List<dadosFinanceirosUsuario> listarPorUsuarioId(int usuarioId) throws Exception {
        List<dadosFinanceirosUsuario> lista = null;
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("FROM dadosFinanceirosUsuario df WHERE df.loginUsuario.id = :usuarioId", dadosFinanceirosUsuario.class);
            query.setParameter("usuarioId", usuarioId);
            lista = query.getResultList();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return lista;
    }

    @Override
    public List<dadosFinanceirosUsuario> filtrarPorNomeESenha(String nome, String senha) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();

        Query query = em.createNamedQuery("dadosFinanceirosUsuario.filtrarPorNomeESenha");
        query.setParameter("nome", nome);
        query.setParameter("senha", senha);
        List<dadosFinanceirosUsuario> resultado = query.getResultList();
        return resultado;
    }

    @Override
    public List<dadosFinanceirosUsuario> filtrarPorNome(String nome, int loginUsuario) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();

        Query query = em.createNamedQuery("dadosFinanceirosUsuario.filtrarPorMesEUsuario");
        query.setParameter("mes", nome);
        query.setParameter("loginUsuario", loginUsuario);
        List<dadosFinanceirosUsuario> resultado = query.getResultList();
        return resultado;
    }

}
