package com.yxuo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.yxuo.util.Config;
import com.yxuo.util.DBConnector;

import java.awt.GridLayout;

public class Main {

    public static final Config config = Config.getInstance();

    public static void testConnection() throws SQLException {
        try {
            DBConnector.getConnection();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static void main(String[] args) throws SQLException {

        testConnection();

        // Cria a janela principal
        JFrame frame = new JFrame("Acesso ao Banco de Dados");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Cria um painel para adicionar componentes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        // Cria um botão para buscar dados do banco
        JButton fetchButton = new JButton("Buscar Dados");
        fetchButton.addActionListener(e -> {
            try {
                Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM nomedatabela");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String result = resultSet.getString("nome_coluna");
                    JOptionPane.showMessageDialog(null, result);
                }

                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton createTableButton = new JButton("Criar tabela");
        createTableButton.addActionListener(e -> {
            System.out.println("Criar tabela");
            try {
                Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS teste(id int);");
                ResultSet resultSet = statement.executeQuery();

                System.out.println(resultSet);

                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Adiciona o botão ao painel
        panel.add(fetchButton);
        panel.add(createTableButton);

        // Adiciona o painel à janela
        frame.add(panel);

        // Exibe a janela
        frame.setVisible(true);

    }
}
