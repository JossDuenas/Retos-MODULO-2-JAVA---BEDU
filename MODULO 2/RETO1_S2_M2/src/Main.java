import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Simulación de misión espacial iniciada...");

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<String> futuroNav = executor.submit(new SistemaNavegacion());
        Future<String> futuroVida = executor.submit(new SistemaSoporteVital());
        Future<String> futuroTermico = executor.submit(new SistemaControlTermico());
        Future<String> futuroCom = executor.submit(new SistemaComunicaciones());

        try {
            // Mostrar los resultados sin importar el orden de ejecución
            System.out.println(futuroCom.get());
            System.out.println(futuroVida.get());
            System.out.println(futuroTermico.get());
            System.out.println(futuroNav.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        System.out.println("✅ Todos los sistemas reportan estado operativo.");
    }
}
