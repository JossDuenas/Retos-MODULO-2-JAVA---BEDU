import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.println("🏥 Iniciando acceso a la Sala de cirugía...");

        RecursoMedico salaCirugia = new RecursoMedico("Sala de cirugía");

        // Crear pool de profesionales
        ExecutorService executor = Executors.newFixedThreadPool(4);

        String[] profesionales = {
                "Dra. Sánchez",
                "Dr. Gómez",
                "Enf. Ramírez",
                "Dr. Torres",
                "Dra. Moreno",
                "Enf. Delgado"
        };

        for (String nombre : profesionales) {
            executor.submit(() -> salaCirugia.usar(nombre));
        }

        executor.shutdown(); // Finaliza después de terminar todas las tareas
    }
}
