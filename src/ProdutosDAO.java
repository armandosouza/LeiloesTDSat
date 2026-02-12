/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto(ProdutosDTO produto){
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        conn = new conectaDAO().connectDB();
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch(SQLException err) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao cadastrar o produto! Erro: " + err.getMessage());
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        String sql = "SELECT * FROM produtos";
        conn = new conectaDAO().connectDB();
        
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while(resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                lista.add(produto);
            }
        } catch(SQLException err) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao listar os produtos!");
        }
        
        return lista;
    }
    
    public void venderProduto(int idProduto) {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        
        conn = new conectaDAO().connectDB();
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, idProduto);
            
            int numProdutosVendidos = prep.executeUpdate();
            
            if(numProdutosVendidos > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }
        } catch(SQLException err) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao realizar a venda! Erro: " + err.getMessage());
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        
        conn = new conectaDAO().connectDB();
        
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while(resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                lista.add(produto);
            }
        } catch(SQLException err) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao listar os produtos! Erro: " + err.getMessage());
        }
        
        return lista;
    }
}