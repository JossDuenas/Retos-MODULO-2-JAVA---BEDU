package com.inventario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargarDatos implements CommandLineRunner {

    private final ProductoRepository repo;

    public CargarDatos(ProductoRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        repo.save(new Producto(null, "Laptop Lenovo", "Portátil potente", 12500));
        repo.save(new Producto(null, "Mouse Logitech", "Mouse inalámbrico", 350));
        repo.save(new Producto(null, "Teclado Mecánico", "RGB", 950));
        repo.save(new Producto(null, "Monitor", "Full HD", 3200));

        System.out.println("📦 Productos con precio mayor a 500:");
        repo.findByPrecioGreaterThan(500).forEach(System.out::println);

        System.out.println("🔍 Productos que contienen 'lap':");
        repo.findByNombreContainingIgnoreCase("lap").forEach(System.out::println);

        System.out.println("🎯 Productos con precio entre 400 y 1000:");
        repo.findByPrecioBetween(400, 1000).forEach(System.out::println);

        System.out.println("📘 Productos cuyo nombre empieza con 'm':");
        repo.findByNombreStartingWithIgnoreCase("m").forEach(System.out::println);
    }
}
