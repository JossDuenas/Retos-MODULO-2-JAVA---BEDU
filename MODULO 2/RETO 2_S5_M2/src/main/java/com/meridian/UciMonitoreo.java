import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Random;

public class UciMonitoreo {

    static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        Flux<String> paciente1 = generarPaciente(1);
        Flux<String> paciente2 = generarPaciente(2);
        Flux<String> paciente3 = generarPaciente(3);

        // Fusionamos todos los flujos de pacientes
        Flux<String> flujoFusionado = Flux.merge(paciente1, paciente2, paciente3)
                .delayElements(Duration.ofSeconds(1)) // ✅ Backpressure para no saturar
                .subscribeOn(Schedulers.parallel());

        flujoFusionado
                .doOnNext(System.out::println)
                .subscribe();

        // Mantener la app viva durante la simulación
        Thread.sleep(20000); // 20 segundos
    }

    static Flux<String> generarPaciente(int id) {
        return Flux.interval(Duration.ofMillis(300))
                .onBackpressureBuffer()
                .map(tick -> {
                    int fc = random.nextInt(71) + 40; // FC: 40–110 bpm
                    int sistolica = random.nextInt(81) + 80; // Sistólica: 80–160
                    int diastolica = random.nextInt(51) + 50; // Diastólica: 50–100
                    int spo2 = random.nextInt(21) + 80; // SpO2: 80–100%

                    // ⚠️ Prioridad: FC > SpO2 > PA
                    if (fc < 50 || fc > 120) {
                        return "⚠️ Paciente " + id + " - FC crítica: " + fc + " bpm";
                    } else if (spo2 < 90) {
                        return "⚠️ Paciente " + id + " - SpO2 baja: " + spo2 + "%";
                    } else if (sistolica < 90 || sistolica > 140 || diastolica < 60 || diastolica > 90) {
                        return "⚠️ Paciente " + id + " - PA crítica: " + sistolica + "/" + diastolica + " mmHg";
                    } else {
                        return ""; // ✅ No null, pero se filtra después
                    }
                })
                .filter(msg -> !msg.isEmpty()) // ✅ Eliminar eventos normales
                .subscribeOn(Schedulers.parallel());
    }
}
