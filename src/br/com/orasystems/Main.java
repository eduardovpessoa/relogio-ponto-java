/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.orasystems;

import br.com.orasystems.model.Suporte;
import br.com.orasystems.util.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Eduardo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Insira o Código de Barras do Funcionário: ");
            Long codigoBarras = sc.nextLong();

            if (codigoBarras != null && codigoBarras > 0) {
                if (registraPonto(codigoBarras)) {
                    System.out.println("Ponto Cadastrado com Sucesso!");
                } else {
                    System.out.println("Funcionário Não Cadastrado!");
                }
            } else {
                System.out.println("Código Inválido!");
            }
        }
    }

    public static boolean registraPonto(Long codigo) {

        ResultSet rs = null;
        PreparedStatement stmt = null;
        ConnectionFactory connectionFactory = null;

        try {
            Suporte suporte = new Suporte();

            connectionFactory = new ConnectionFactory();
            stmt = connectionFactory.getConnection().prepareStatement("SELECT S.COD_SUPORTE, S.NOME_SUPORTE FROM SUPORTE S WHERE S.COD_BARRAS = ?");
            stmt.setLong(1, codigo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                suporte.setCodigoSuporte(rs.getLong("COD_SUPORTE"));
                suporte.setNome(rs.getString("NOME_SUPORTE"));

                stmt = null;
                rs = null;
                stmt = connectionFactory.getConnection().prepareStatement(
                        "SELECT TIPO "
                        + "FROM REGISTRO_PONTO "
                        + "WHERE COD_SUPORTE = ? "
                        + "AND TRUNC(DATA_HORA) = TRUNC(SYSDATE) "
                        + "AND COD_REGISTRO_PONTO = (SELECT MAX(COD_REGISTRO_PONTO) "
                        + "FROM REGISTRO_PONTO "
                        + "WHERE COD_SUPORTE = ?)");
                stmt.setLong(1, suporte.getCodigoSuporte());
                stmt.setLong(2, suporte.getCodigoSuporte());
                rs = stmt.executeQuery();

                Integer tipo = null;

                if (rs.next()) {
                    tipo = rs.getInt("TIPO");
                    if (tipo == null || tipo.equals(0)) {
                        tipo = 1;
                    } else {
                        tipo = 0;
                    }
                } else {
                    tipo = 1;
                }

                stmt = null;
                stmt = connectionFactory.getConnection().prepareStatement("INSERT INTO REGISTRO_PONTO (COD_SUPORTE, TIPO) VALUES (?, ?)");
                stmt.setLong(1, suporte.getCodigoSuporte());
                stmt.setInt(2, tipo);
                stmt.executeUpdate();

                return true;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionFactory.closeConnection(rs, stmt, connectionFactory.getConnection());
        }
    }

}
