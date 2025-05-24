package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author kaio
 * OS COLUMN APLICADO A FIM DE TESTE E DEMAIS PROPOSITOS, COMO NÃO FIZERAM DIFERENÇA OS MANTIVE AÍ
 */
/*
@NamedQuery(
    name = "dadosFinanceirosUsuario.filtrarPorMes",
    query = "SELECT d FROM dadosFinanceirosUsuario d WHERE d.mes = :mes"
)
*/

@NamedQuery(
    name = "dadosFinanceirosUsuario.filtrarPorMesEUsuario",
    query = "SELECT d FROM dadosFinanceirosUsuario d WHERE d.mes = :mes AND d.loginUsuario.id = :loginUsuario"
)


@Entity
public class dadosFinanceirosUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mes", columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String mes;

    @Column(name = "receita", columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String receita;

    @Column(name = "despesaFixa", columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String despesaFixa;

    @Column(name = "despesaVariavel", columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String despesaVariavel;

    @Column(name = "notas", columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String notas;

    @Column(name = "meta", columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String meta;

    @ManyToOne
    @JoinColumn(name = "loginUsuario", nullable = false)
    private loginUsuario loginUsuario; // Relacionamento com loginUsuario

    public dadosFinanceirosUsuario() {
    }

    public dadosFinanceirosUsuario(String mes, String receita, String despesaFixa, String despesaVariavel, String notas, String meta, loginUsuario loginUsuario) {
        this.id = id;
        this.mes = mes;
        this.receita = receita;
        this.despesaFixa = despesaFixa;
        this.despesaVariavel = despesaVariavel;
        this.notas = notas;
        this.meta = meta;
        this.loginUsuario = loginUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita = receita;
    }

    public String getDespesaFixa() {
        return despesaFixa;
    }

    public void setDespesaFixa(String despesaFixa) {
        this.despesaFixa = despesaFixa;
    }

    public String getDespesaVariavel() {
        return despesaVariavel;
    }

    public void setDespesaVariavel(String despesaVariavel) {
        this.despesaVariavel = despesaVariavel;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public loginUsuario getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(loginUsuario loginUsuario) {
        this.loginUsuario = loginUsuario;
    }
}
