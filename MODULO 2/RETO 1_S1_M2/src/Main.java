import java.util.ArrayList;
import java.util.List;

public class Main {

    // Método genérico con wildcard para mostrar órdenes
    public static void mostrarOrdenes(List<? extends OrdenProduccion> lista) {
        for (OrdenProduccion orden : lista) {
            orden.mostrarResumen();
        }
    }

    // Método genérico para procesar órdenes personalizadas
    public static void procesarPersonalizadas(List<? super OrdenPersonalizada> lista, int costoAdicional) {
        System.out.println("\n💰 Procesando órdenes personalizadas...");
        for (Object obj : lista) {
            if (obj instanceof OrdenPersonalizada) {
                ((OrdenPersonalizada) obj).aplicarCostoAdicional(costoAdicional);
            }
        }
    }

    // Conteo de órdenes por tipo
    public static void contarOrdenes(List<OrdenProduccion> lista) {
        int masa = 0, personalizada = 0, prototipo = 0;
        for (OrdenProduccion orden : lista) {
            if (orden instanceof OrdenMasa) masa++;
            else if (orden instanceof OrdenPersonalizada) personalizada++;
            else if (orden instanceof OrdenPrototipo) prototipo++;
        }
        System.out.println("\n📊 Resumen total de órdenes:");
        System.out.println("🔧 Producción en masa: " + masa);
        System.out.println("🛠️ Personalizadas: " + personalizada);
        System.out.println("🧪 Prototipos: " + prototipo);
    }

    public static void main(String[] args) {
        List<OrdenMasa> ordenesMasa = new ArrayList<>();
        ordenesMasa.add(new OrdenMasa("A123", 500));
        ordenesMasa.add(new OrdenMasa("A124", 750));

        List<OrdenPersonalizada> ordenesPersonalizadas = new ArrayList<>();
        ordenesPersonalizadas.add(new OrdenPersonalizada("P456", 100, "ClienteX"));
        ordenesPersonalizadas.add(new OrdenPersonalizada("P789", 150, "ClienteY"));

        List<OrdenPrototipo> ordenesPrototipo = new ArrayList<>();
        ordenesPrototipo.add(new OrdenPrototipo("T789", 10, "Diseño"));
        ordenesPrototipo.add(new OrdenPrototipo("T790", 5, "Pruebas"));

        System.out.println("📋 Órdenes registradas:");
        mostrarOrdenes(ordenesMasa);
        System.out.println("\n📋 Órdenes registradas:");
        mostrarOrdenes(ordenesPersonalizadas);
        System.out.println("\n📋 Órdenes registradas:");
        mostrarOrdenes(ordenesPrototipo);

        procesarPersonalizadas(ordenesPersonalizadas, 200);

        // Combinar todas las órdenes en una lista para el resumen
        List<OrdenProduccion> todas = new ArrayList<>();
        todas.addAll(ordenesMasa);
        todas.addAll(ordenesPersonalizadas);
        todas.addAll(ordenesPrototipo);

        contarOrdenes(todas);
    }
}