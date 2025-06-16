import java.util.concurrent.*;
import java.util.concurrent.ThreadLocalRandom;

public class AeropuertoControl {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public CompletableFuture<Boolean> verificarPista() {
        return CompletableFuture.supplyAsync(() -> {
            sleepRandom(2, 3);
            boolean disponible = Math.random() < 0.8; // 80%
            System.out.println("🛣️ Pista disponible: " + disponible);
            return disponible;
        }, executor);
    }

    public CompletableFuture<Boolean> verificarClima() {
        return CompletableFuture.supplyAsync(() -> {
            sleepRandom(2, 3);
            boolean favorable = Math.random() < 0.85; // 85%
            System.out.println("🌦️ Clima favorable: " + favorable);
            return favorable;
        }, executor);
    }

    public CompletableFuture<Boolean> verificarTraficoAereo() {
        return CompletableFuture.supplyAsync(() -> {
            sleepRandom(2, 3);
            boolean despejado = Math.random() < 0.9; // 90%
            System.out.println("🚦 Tráfico aéreo despejado: " + despejado);
            return despejado;
        }, executor);
    }

    public CompletableFuture<Boolean> verificarPersonalTierra() {
        return CompletableFuture.supplyAsync(() -> {
            sleepRandom(2, 3);
            boolean disponible = Math.random() < 0.95; // 95%
            System.out.println("👷‍♂️ Personal disponible: " + disponible);
            return disponible;
        }, executor);
    }

    public void evaluarCondicionesDeAterrizaje() {
        System.out.println("🛫 Verificando condiciones para aterrizaje...\n");

        CompletableFuture<Boolean> pista = verificarPista();
        CompletableFuture<Boolean> clima = verificarClima();
        CompletableFuture<Boolean> trafico = verificarTraficoAereo();
        CompletableFuture<Boolean> personal = verificarPersonalTierra();

        CompletableFuture<Void> todas = CompletableFuture.allOf(pista, clima, trafico, personal);

        todas.thenApply(v -> {
                    try {
                        boolean condicionesOk =
                                pista.get() &&
                                        clima.get() &&
                                        trafico.get() &&
                                        personal.get();

                        return condicionesOk;
                    } catch (Exception e) {
                        System.out.println("❌ Error al obtener resultados: " + e.getMessage());
                        return false;
                    }
                })
                .thenAccept(condicionesOk -> {
                    if (condicionesOk) {
                        System.out.println("\n🛬 Aterrizaje autorizado: todas las condiciones óptimas.");
                    } else {
                        System.out.println("\n🚫 Aterrizaje denegado: condiciones no óptimas.");
                    }
                })
                .exceptionally(e -> {
                    System.out.println("❌ Excepción general: " + e.getMessage());
                    return null;
                });
    }

    private void sleepRandom(int minSeconds, int maxSeconds) {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(minSeconds, maxSeconds + 1));
        } catch (InterruptedException e) {
            throw new RuntimeException("⛔ Simulación interrumpida.");
        }
    }
}
