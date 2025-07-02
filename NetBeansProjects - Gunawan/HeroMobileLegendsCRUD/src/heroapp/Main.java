package heroapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

class MainFrame extends JFrame {
    private JTextField txtId, txtNama;
    private JComboBox<String> cmbKategori, cmbGender;
    private JTable tblHero;
    private DefaultTableModel tableModel;

    MainFrame() {
        setTitle("CRUD Hero Mobile Legends");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 540);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---------- HEADER ----------
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);
        JLabel lblLogo = new JLabel(new ImageIcon("src/heroapp/logo.png")); // path logo
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblLogo, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // ---------- FORM INPUT ----------
        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setBackground(Color.BLACK);

        JLabel lblId = new JLabel("ID:");
        lblId.setForeground(Color.WHITE);
        txtId = new JTextField(5);

        JLabel lblKategori = new JLabel("Kategori:");
        lblKategori.setForeground(Color.WHITE);
        cmbKategori = new JComboBox<>(new String[]{
            "MAGE", "ASSASIN", "FIGHTER", "TANK", "MARKSMAN", "SUPPORT"
        });

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setForeground(Color.WHITE);
        cmbGender = new JComboBox<>(new String[]{"MALE", "FEMALE"});

        JLabel lblNama = new JLabel("Nama Hero:");
        lblNama.setForeground(Color.WHITE);
        txtNama = new JTextField(10);

        formPanel.add(lblId);
        formPanel.add(txtId);
        formPanel.add(lblKategori);
        formPanel.add(cmbKategori);
        formPanel.add(lblGender);
        formPanel.add(cmbGender);
        formPanel.add(lblNama);
        formPanel.add(txtNama);

        add(formPanel, BorderLayout.BEFORE_FIRST_LINE);

        // ---------- TOMBOL ----------
        JPanel panelButton = new JPanel(new FlowLayout());

        JButton btnTambah = new JButton("Tambahkan");
        JButton btnUpdate = new JButton("Update");
        JButton btnHapus = new JButton("Hapus");

        panelButton.add(btnTambah);
        panelButton.add(btnUpdate);
        panelButton.add(btnHapus);

        add(panelButton, BorderLayout.CENTER);

        // ---------- TABEL ----------
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Kategori", "Gender"}, 0);
        tblHero = new JTable(tableModel);
        tblHero.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(tblHero);
        scrollPane.setBorder(BorderFactory.createTitledBorder(""));
        add(scrollPane, BorderLayout.SOUTH);

        // ---------- AKSI TOMBOL ----------
        btnTambah.addActionListener(e -> tambahData());
        btnUpdate.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> hapusData());

        tblHero.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblHero.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtNama.setText(tableModel.getValueAt(row, 1).toString());
                cmbKategori.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                cmbGender.setSelectedItem(tableModel.getValueAt(row, 3).toString());
            }
        });

        loadTable();
        setVisible(true);
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/db_mobilelegend", "root", "");
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tm_hero")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id_hero"),
                    rs.getString("nama_hero"),
                    rs.getString("kategori"),
                    rs.getString("gender")
                });
            }
        } catch (SQLException e) {
            showError("Load data gagal:\n" + e.getMessage());
        }
    }

    private void tambahData() {
        try (Connection conn = connect()) {
            int id = Integer.parseInt(txtId.getText());
            if (txtNama.getText().trim().isEmpty()) {
                showError("Nama tidak boleh kosong!");
                return;
            }
            String sql = "INSERT INTO tm_hero(id_hero, nama_hero, kategori, gender) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, txtNama.getText());
            stmt.setString(3, cmbKategori.getSelectedItem().toString());
            stmt.setString(4, cmbGender.getSelectedItem().toString());
            stmt.executeUpdate();
            loadTable();
            clearForm();
        } catch (NumberFormatException e) {
            showError("ID harus berupa angka!");
        } catch (SQLException e) {
            showError("Tambah data gagal:\n" + e.getMessage());
        }
    }

    private void updateData() {
        int row = tblHero.getSelectedRow();
        if (row == -1) return;
        int id = Integer.parseInt(txtId.getText());

        try (Connection conn = connect()) {
            String sql = "UPDATE tm_hero SET nama_hero=?, kategori=?, gender=? WHERE id_hero=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, txtNama.getText());
            stmt.setString(2, cmbKategori.getSelectedItem().toString());
            stmt.setString(3, cmbGender.getSelectedItem().toString());
            stmt.setInt(4, id);
            stmt.executeUpdate();
            loadTable();
            clearForm();
        } catch (SQLException e) {
            showError("Update data gagal:\n" + e.getMessage());
        }
    }

    private void hapusData() {
        int row = tblHero.getSelectedRow();
        if (row == -1) return;
        int id = Integer.parseInt(txtId.getText());

        try (Connection conn = connect()) {
            String sql = "DELETE FROM tm_hero WHERE id_hero=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            loadTable();
            clearForm();
        } catch (SQLException e) {
            showError("Hapus data gagal:\n" + e.getMessage());
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        cmbKategori.setSelectedIndex(0);
        cmbGender.setSelectedIndex(0);
        tblHero.clearSelection();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
