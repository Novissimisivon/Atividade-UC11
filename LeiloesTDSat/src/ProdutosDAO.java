import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {
    private Connection conn;

    public ProdutosDAO(Connection conn) {
        this.conn = conn;
    }

    // Adicionar um novo produto ao banco de dados
    public void adicionarProduto(ProdutosDTO produto) throws SQLException {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());
            stmt.executeUpdate();
        }
    }

    // Listar todos os produtos cadastrados
    public List<ProdutosDTO> listarProdutos() throws SQLException {
        List<ProdutosDTO> listaProdutos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                listaProdutos.add(produto);
            }
        }
        return listaProdutos;
    }

    // Atualizar informações de um produto (nome e valor)
    public void atualizarProduto(ProdutosDTO produto) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, valor = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setInt(3, produto.getId());
            stmt.executeUpdate();
        }
    }

    // Marcar um produto como vendido
    public void venderProduto(int id) throws SQLException {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Remover um produto pelo ID
    public void removerProduto(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
