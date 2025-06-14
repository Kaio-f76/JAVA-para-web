package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author kaio
 */

/*
@NamedQuery(
        name="loginUsuario.filtrarPorNome", 
        query="SELECT c FROM loginUsuario c WHERE c.nome like CONCAT('%',:nome,'%')")
*/

@NamedQuery(
        name = "loginUsuario.filtrarPorNomeESenha",
        query = "SELECT c FROM loginUsuario c WHERE c.nome LIKE CONCAT('%', :nome, '%') AND c.senha = :senha"
)


@Entity
public class loginUsuario{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String senha;
    private String email;
    
    @OneToMany(mappedBy="loginUsuario", orphanRemoval=true)
    private List<dadosFinanceirosUsuario> dadosFinanceiros;// Lista de dados financeiros

    public loginUsuario() {
    }

    public loginUsuario(String nome, String email, String senha ) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<dadosFinanceirosUsuario> getDadosFinanceiros() {
        return dadosFinanceiros;
    }

    public void setDadosFinanceiros(List<dadosFinanceirosUsuario> dadosFinanceiros) {
        this.dadosFinanceiros = dadosFinanceiros;
    }

    @Override
    public String toString() {
        return "Classe loginUsuario" + "\n" +
                "Nome Usuario: " + nome + "\n" +
                "Email Usuario: " + email + "\n" +
                "Senha Usuario: " + senha + "\n";
    }
        
}
