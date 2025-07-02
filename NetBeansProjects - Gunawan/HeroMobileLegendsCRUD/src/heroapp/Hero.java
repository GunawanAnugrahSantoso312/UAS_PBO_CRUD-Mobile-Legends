public class Hero {
    private int idHero;
    private String namaHero;
    private String kategori;
    private String gender;

    public Hero(int idHero, String namaHero, String kategori, String gender) {
        this.idHero = idHero;
        this.namaHero = namaHero;
        this.kategori = kategori;
        this.gender = gender;
    }

    public int getIdHero() {
        return idHero;
    }

    public String getNamaHero() {
        return namaHero;
    }

    public String getKategori() {
        return kategori;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return idHero + " | " + namaHero + " | " + kategori + " | " + gender;
    }
}
