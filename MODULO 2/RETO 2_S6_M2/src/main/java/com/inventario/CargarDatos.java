package com.inventario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargarDatos implements CommandLineRunner {

    private final ProductoRepository productoRepo;
    private final MarcaRepository marcaRepo;

    public CargarDatos(ProductoRepository productoRepo, MarcaRepository marcaRepo) {
        this.productoRepo = productoRepo;
        this.marcaRepo = marcaRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear marcas
        Marca apple = new Marca("Apple");
        Marca samsung = new Marca("Samsung");

        marcaRepo.save(apple);
        marcaRepo.save(samsung);

        // Crear productos asociados a marcas
        productoRepo.save(new Producto("iPhone 15", "Smartphone potente", 22000, apple));
        productoRepo.save(new Producto("iPad Pro", "Tablet profesional", 18000, apple));
        productoRepo.save(new Producto("Galaxy S23", "Smartphone Android", 19000, samsung));
        productoRepo.save(new Producto("Smart TV", "Televisión 4K", 9500, samsung));

        // Mostrar productos por marca
        System.out.println("📚 Productos por marca:");
        marcaRepo.findAll().forEach(marca -> {
            System.out.println("🏷️ " + marca.getNombre() + ":");
            productoRepo.findAll().stream()
                    .filter(p -> p.getMarca().getId().equals(marca.getId()))
                    .forEach(p -> System.out.println("   - " + p.getNombre()));
        });
    }
}
