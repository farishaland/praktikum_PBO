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

class LaporanPenjualan extends Laporan {
    public LaporanPenjualan(String id, String namaItem, double harga) {
        super(id, namaItem, harga);
    }

    @Override
    public String getJenis() {
        return "Penjualan";
    }
}

final class LaporanPengeluaran extends Laporan {
    public LaporanPengeluaran(String id, String namaItem, double harga) {
        super(id, namaItem, harga);
    }

    @Override
    public String getJenis() {
        return "Pengeluaran";
    }
}
public class LaporanList {
    private static ArrayList<Laporan> laporanList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            tampilkanMenu();
            int pilihan = scanner.nextInt();
            scanner.nextLine();
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
                    System.out.println("Keluar dari program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Coba lagi.");
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\n=== Menu Sistem Laporan Finansial Cafe Syafar ===");
        System.out.println("1. Tambah Data");
        System.out.println("2. Tampilkan Data");
        System.out.println("3. Ubah Data");
        System.out.println("4. Hapus Data");
        System.out.println("5. Keluar");
        System.out.print("Pilih menu (1-5): ");
    }

    private static void tambahData() {
        System.out.println("\n=== Tambah Data ===");
        System.out.print("Pilih jenis data (1. Penjualan, 2. Pengeluaran): ");
        int jenis = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Masukkan ID: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Nama Item: ");
        String namaItem = scanner.nextLine();
        System.out.print("Masukkan Harga: Rp ");
        double harga = scanner.nextDouble();
        scanner.nextLine();

        Laporan laporanBaru;
        if (jenis == 1) {
            laporanBaru = new LaporanPenjualan(id, namaItem, harga);
        } else {
            laporanBaru = new LaporanPengeluaran(id, namaItem, harga);
        }

        laporanList.add(laporanBaru);
        laporanBaru.info();  
        System.out.println("Data berhasil ditambahkan.");
    }

    private static void tampilkanData() {
        System.out.println("\n=== Tampilkan Data ===");
        if (laporanList.isEmpty()) {
            System.out.println("Tidak ada data untuk ditampilkan.");
        } else {
            for (Laporan laporan : laporanList) {
                System.out.println(laporan);
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
                System.out.print("Masukkan Nama Item Baru: ");
                String namaItemBaru = scanner.nextLine();
                System.out.print("Masukkan Harga Baru: Rp ");
                double hargaBaru = scanner.nextDouble();
                scanner.nextLine();

                laporan.setNamaItem(namaItemBaru);
                laporan.setHarga(hargaBaru);
                System.out.println("Data berhasil diubah.");
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
