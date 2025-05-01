import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

abstract class Laporan {
    private final String id;
    private String namaItem;
    private double harga;

    public Laporan(String id, String namaItem, double harga) {
        this.id = id;
        this.namaItem = namaItem;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public abstract String getJenis();

    public final void info() {
        System.out.println("Data laporan finansial berhasil diproses.");
    }

    @Override
    public String toString() {
        return "ID: " + id +
               " | Jenis: " + getJenis() +
               " | Nama Item: " + namaItem +
               " | Harga: Rp " + harga;
    }
}

interface CetakLaporan {
    void cetakDetail();
    void cetakRingkas();
}

class Util {
    public static double totalHarga = 0;

    public static boolean validasiHarga(double harga) {
        return harga >= 0;
    }

    public static void updateTotal(double harga) {
        totalHarga += harga;
    }
}

class LaporanPenjualan extends Laporan implements CetakLaporan {
    public LaporanPenjualan(String id, String namaItem, double harga) {
        super(id, namaItem, harga);
    }

    @Override
    public String getJenis() {
        return "Penjualan";
    }

    @Override
    public void cetakDetail() {
        System.out.println("[DETAIL] " + toString());
    }

    @Override
    public void cetakRingkas() {
        System.out.println("[RINGKAS] Penjualan - " + getNamaItem() + ": Rp " + getHarga());
    }
}

final class LaporanPengeluaran extends Laporan implements CetakLaporan {
    public LaporanPengeluaran(String id, String namaItem, double harga) {
        super(id, namaItem, harga);
    }

    @Override
    public String getJenis() {
        return "Pengeluaran";
    }

    @Override
    public void cetakDetail() {
        System.out.println("[DETAIL] " + toString());
    }

    @Override
    public void cetakRingkas() {
        System.out.println("[RINGKAS] Pengeluaran - " + getNamaItem() + ": Rp " + getHarga());
    }
}

public class LaporanList {
    private static ArrayList<Laporan> laporanList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            tampilkanMenu();
            try {
                int pilihan = Integer.parseInt(scanner.nextLine());
                switch (pilihan) {
                    case 1:
                        tambahData();
                        break;
                    case 2:
                        tampilkanData();
                        break;
                    case 3:
                        ubahData();
                        break;
                    case 4:
                        hapusData();
                        break;
                    case 5:
                        System.out.println("Total Semua Laporan: Rp " + Util.totalHarga);
                        break;
                    case 6:
                        System.out.println("Keluar dari program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Pilihan tidak valid! Coba lagi.");
                }
            } catch (Exception e) {
                System.out.println("Input harus berupa angka! Coba lagi.");
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\n=== Menu Sistem Laporan Finansial Cafe Syafar ===");
        System.out.println("1. Tambah Data");
        System.out.println("2. Tampilkan Data");
        System.out.println("3. Ubah Data");
        System.out.println("4. Hapus Data");
        System.out.println("5. Tampilkan Total Harga");
        System.out.println("6. Keluar");
        System.out.print("Pilih menu (1-6): ");
    }

    private static void tambahData() {
        System.out.println("\n=== Tambah Data ===");
        try {
            System.out.print("Pilih jenis data (1. Penjualan, 2. Pengeluaran): ");
            int jenis = Integer.parseInt(scanner.nextLine());
            if (jenis != 1 && jenis != 2) {
                throw new IllegalArgumentException("Hanya boleh memilih 1 atau 2.");
            }

            System.out.print("Masukkan ID: ");
            String id = scanner.nextLine();
            System.out.print("Masukkan Nama Item: ");
            String namaItem = scanner.nextLine();
            System.out.print("Masukkan Harga: Rp ");
            double harga = Double.parseDouble(scanner.nextLine());

            if (!Util.validasiHarga(harga)) {
                throw new IllegalArgumentException("Harga tidak boleh negatif.");
            }

            Laporan laporanBaru = (jenis == 1)
                ? new LaporanPenjualan(id, namaItem, harga)
                : new LaporanPengeluaran(id, namaItem, harga);

            laporanList.add(laporanBaru);
            Util.updateTotal(harga);
            laporanBaru.info();
            System.out.println("Data berhasil ditambahkan.");

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    private static void tampilkanData() {
        System.out.println("\n=== Tampilkan Data ===");
        if (laporanList.isEmpty()) {
            System.out.println("Tidak ada data untuk ditampilkan.");
        } else {
            for (Laporan laporan : laporanList) {
                if (laporan instanceof CetakLaporan) {
                    ((CetakLaporan) laporan).cetakDetail();
                }
            }
        }
    }

    private static void ubahData() {
        System.out.println("\n=== Ubah Data ===");
        System.out.print("Masukkan ID Laporan yang ingin diubah: ");
        String id = scanner.nextLine();

        boolean ditemukan = false;
        for (Laporan laporan : laporanList) {
            if (laporan.getId().equals(id)) {
                ditemukan = true;
                try {
                    System.out.print("Masukkan Nama Item Baru: ");
                    String namaItemBaru = scanner.nextLine();
                    System.out.print("Masukkan Harga Baru: Rp ");
                    double hargaBaru = Double.parseDouble(scanner.nextLine());

                    if (!Util.validasiHarga(hargaBaru)) {
                        throw new IllegalArgumentException("Harga tidak boleh negatif.");
                    }

                    Util.totalHarga -= laporan.getHarga(); 
                    laporan.setNamaItem(namaItemBaru);
                    laporan.setHarga(hargaBaru);
                    Util.updateTotal(hargaBaru); 

                    System.out.println("Data berhasil diubah.");
                } catch (Exception e) {
                    System.out.println("Gagal mengubah data: " + e.getMessage());
                }
                break;
            }
        }

        if (!ditemukan) {
            System.out.println("Data dengan ID " + id + " tidak ditemukan.");
        }
    }

    private static void hapusData() {
        System.out.println("\n=== Hapus Data ===");
        System.out.print("Masukkan ID Laporan yang ingin dihapus: ");
        String id = scanner.nextLine();

        Iterator<Laporan> iterator = laporanList.iterator();
        boolean ditemukan = false;

        while (iterator.hasNext()) {
            Laporan laporan = iterator.next();
            if (laporan.getId().equals(id)) {
                Util.totalHarga -= laporan.getHarga(); 
                iterator.remove();
                ditemukan = true;
                System.out.println("Data berhasil dihapus.");
                break;
            }
        }

        if (!ditemukan) {
            System.out.println("Data dengan ID " + id + " tidak ditemukan.");
        }
    }
}
