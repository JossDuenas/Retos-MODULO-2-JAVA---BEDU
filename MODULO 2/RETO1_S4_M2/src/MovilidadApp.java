import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MovilidadApp {

    public CompletableFuture<String> calcularRuta() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("🚦 Calculando ruta...");
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(2, 4));
                return "Centro -> Norte";
            } catch (InterruptedException e) {
                throw new RuntimeException("❌ Error al calcular la ruta");
            }
        });
    }

    public CompletableFuture<Double> estimarTarifa() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("💰 Estimando tarifa...");
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 3));

                // Simulación de error opcional (descomenta para probar)
                // if (ThreadLocalRandom.current().nextBoolean()) throw new RuntimeException("Falla al estimar tarifa");

                return 75.50;
            } catch (InterruptedException e) {
                throw new RuntimeException("❌ Error al estimar la tarifa");
            }
        });
    }

    public void procesarSolicitud() {
        calcularRuta()
                .thenCombine(estimarTarifa(), (ruta, tarifa) ->
                        "✅ 🚗 Ruta calculada: " + ruta + " | Tarifa estimada: $" + tarifa
                )
                .exceptionally(e -> "⚠️ Ocurrió un error: " + e.getMessage())
                .thenAccept(System.out::println);
    }
}
