import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

abstract class Laporan {
    private String id;
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

    @Override
    public String toString() {
        return "ID: " + id + " | Jenis: " + getJenis() + " | Item: " + namaItem + " | Harga: Rp " + harga;
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

class LaporanPengeluaran extends Laporan {
    public LaporanPengeluaran(String id, String namaItem, double harga) {
        super(id, namaItem, harga);
    }

    @Override
    public String getJenis() {
        return "Pengeluaran";
    }
}

public class MainLaporan {
    private static ArrayList<Laporan> laporanList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            tampilkanMenu();
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 -> tambahData();
                case 2 -> tampilkanData();
                case 3 -> ubahData();
                case 4 -> hapusData();
                case 5 -> {
                    System.out.println("Keluar dari program...");
                    System.exit(0);
                }
                default -> System.out.println("Pilihan tidak valid!");
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

    public static void tambahData(String id, String namaItem, double harga, int jenis) {
        Laporan laporan;
        if (jenis == 1) {
            laporan = new LaporanPenjualan(id, namaItem, harga);
        } else if (jenis == 2) {
            laporan = new LaporanPengeluaran(id, namaItem, harga);
        } else {
            System.out.println("Jenis laporan tidak valid!");
            return;
        }

        laporanList.add(laporan);
        System.out.println("Data berhasil ditambahkan (via parameter).");
    }

    private static void tambahData() {
        System.out.println("\n=== Tambah Data ===");
        System.out.print("Pilih jenis (1. Penjualan, 2. Pengeluaran): ");
        int jenis = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Masukkan ID: ");
        String id = scanner.nextLine();

        boolean duplikat = laporanList.stream().anyMatch(l -> l.getId().equals(id));
        if (duplikat) {
            System.out.println("❌ ID sudah digunakan! Silakan pakai ID lain.");
            return;
        }

        System.out.print("Masukkan Nama Item: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Harga: Rp ");
        double harga = scanner.nextDouble();
        scanner.nextLine();

        tambahData(id, nama, harga, jenis);
    }

    private static void tampilkanData() {
        System.out.println("\n=== Tampilkan Data ===");
        if (laporanList.isEmpty()) {
            System.out.println("Belum ada data laporan.");
        } else {
            for (Laporan l : laporanList) {
                System.out.println(l);
            }
        }
    }

    private static void ubahData() {
        System.out.println("\n=== Ubah Data ===");
        System.out.print("Masukkan ID yang ingin diubah: ");
        String id = scanner.nextLine();

        for (Laporan l : laporanList) {
            if (l.getId().equals(id)) {
                System.out.print("Nama Item baru: ");
                String namaBaru = scanner.nextLine();
                System.out.print("Harga baru: Rp ");
                double hargaBaru = scanner.nextDouble();
                scanner.nextLine();

                l.setNamaItem(namaBaru);
                l.setHarga(hargaBaru);
                System.out.println("✅ Data berhasil diubah.");
                return;
            }
        }

        System.out.println("❌ ID tidak ditemukan.");
    }

    private static void hapusData() {
        System.out.println("\n=== Hapus Data ===");
        System.out.print("Masukkan ID yang ingin dihapus: ");
        String id = scanner.nextLine();

        Iterator<Laporan> it = laporanList.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(id)) {
                it.remove();
                System.out.println("✅ Data berhasil dihapus.");
                return;
            }
        }

        System.out.println("❌ Data dengan ID " + id + " tidak ditemukan.");
    }
}
