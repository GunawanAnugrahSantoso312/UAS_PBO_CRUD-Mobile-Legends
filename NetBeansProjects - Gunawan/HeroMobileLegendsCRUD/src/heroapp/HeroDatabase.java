import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HeroDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/ml_hero_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn;

    public HeroDatabase() throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void tambahHero(Hero hero) throws SQLException {
        String sql = "INSERT INTO tm_hero (id_hero, nama_hero, kategori, gender) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, hero.getIdHero());
        stmt.setString(2, hero.getNamaHero());
        stmt.setString(3, hero.getKategori());
        stmt.setString(4, hero.getGender());
        stmt.executeUpdate();
        stmt.close();
    }

    public List<Hero> ambilSemuaHero() throws SQLException {
        List<Hero> daftar = new ArrayList<>();
        String sql = "SELECT * FROM tm_hero";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Hero hero = new Hero(
                rs.getInt("id_hero"),
                rs.getString("nama_hero"),
                rs.getString("kategori"),
                rs.getString("gender")
            );
            daftar.add(hero);
        }
        rs.close();
        stmt.close();
        return daftar;
    }

    public void updateHero(Hero hero) throws SQLException {
        String sql = "UPDATE tm_hero SET nama_hero=?, kategori=?, gender=? WHERE id_hero=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, hero.getNamaHero());
        stmt.setString(2, hero.getKategori());
        stmt.setString(3, hero.getGender());
        stmt.setInt(4, hero.getIdHero());
        stmt.executeUpdate();
        stmt.close();
    }

    public void hapusHero(int idHero) throws SQLException {
        String sql = "DELETE FROM tm_hero WHERE id_hero=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idHero);
        stmt.executeUpdate();
        stmt.close();
    }

    public void close() throws SQLException {
        if (conn != null) conn.close();
    }
}
