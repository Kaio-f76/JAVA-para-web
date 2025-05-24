/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author kaio
 */
public class metasFinanceirasUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomeDaMeta;
    private double valorEconomizado;
    private double valorMeta;

    @ManyToOne
    @JoinColumn(name = "dados_financeiros_usuario_id")
    private dadosFinanceirosUsuario dadosFinanceirosUsuario; // Relacionamento com dadosFinanceirosUsuario

    // Construtor padrão
    public metasFinanceirasUsuario() {
    }

    // Construtor com parâmetros
    public metasFinanceirasUsuario(String nomeDaMeta, double valorEconomizado, double valorMeta, dadosFinanceirosUsuario dadosFinanceirosUsuario) {
        this.nomeDaMeta = nomeDaMeta;
        this.valorEconomizado = valorEconomizado;
        this.valorMeta = valorMeta;
        this.dadosFinanceirosUsuario = dadosFinanceirosUsuario;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDaMeta() {
        return nomeDaMeta;
    }

    public void setNomeDaMeta(String nomeDaMeta) {
        this.nomeDaMeta = nomeDaMeta;
    }

    public double getValorEconomizado() {
        return valorEconomizado;
    }

    public void setValorEconomizado(double valorEconomizado) {
        this.valorEconomizado = valorEconomizado;
    }

    public double getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(double valorMeta) {
        this.valorMeta = valorMeta;
    }

    public dadosFinanceirosUsuario getDadosFinanceirosUsuario() {
        return dadosFinanceirosUsuario;
    }

    public void setDadosFinanceirosUsuario(dadosFinanceirosUsuario dadosFinanceirosUsuario) {
        this.dadosFinanceirosUsuario = dadosFinanceirosUsuario;
    }
}
