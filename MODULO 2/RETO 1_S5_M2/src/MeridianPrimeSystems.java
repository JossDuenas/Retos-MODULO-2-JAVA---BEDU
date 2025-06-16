import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

public class MeridianPrimeSystems {

    public static void main(String[] args) throws InterruptedException {
        // Contador para eventos críticos simultáneos
        Flux<String> trafico = Flux.interval(Duration.ofMillis(500))
                .map(i -> (int) (Math.random() * 100))
                .filter(nivel -> nivel > 70)
                .map(nivel -> "🚗 Alerta: Congestión del " + nivel + "% en Avenida Solar")
                .doOnNext(System.out::println)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.parallel());

        Flux<String> contaminacion = Flux.interval(Duration.ofMillis(600))
                .map(i -> (int) (Math.random() * 100))
                .filter(pm -> pm > 50)
                .map(pm -> "🌫️ Alerta: Contaminación alta (PM2.5: " + pm + " ug/m3)")
                .doOnNext(System.out::println)
                .subscribeOn(Schedulers.parallel());

        Flux<String> accidentes = Flux.interval(Duration.ofMillis(800))
                .map(i -> List.of("Baja", "Media", "Alta").get((int) (Math.random() * 3)))
                .filter(p -> p.equals("Alta"))
                .map(p -> "🚑 Emergencia vial: Accidente con prioridad Alta")
                .doOnNext(System.out::println)
                .subscribeOn(Schedulers.parallel());

        Flux<String> trenes = Flux.interval(Duration.ofMillis(700))
                .map(i -> (int) (Math.random() * 11))
                .filter(min -> min > 5)
                .map(min -> "🚝 Tren maglev con retraso crítico: " + min + " minutos")
                .delayElements(Duration.ofMillis(100)) // Simula backpressure
                .doOnNext(System.out::println)
                .subscribeOn(Schedulers.parallel());

        AtomicInteger rojoCount = new AtomicInteger(0);
        Flux<String> semaforos = Flux.interval(Duration.ofMillis(400))
                .map(i -> List.of("Verde", "Amarillo", "Rojo").get((int) (Math.random() * 3)))
                .map(color -> {
                    if (color.equals("Rojo")) {
                        if (rojoCount.incrementAndGet() >= 3) {
                            rojoCount.set(0);
                            return "🚦 Semáforo en Rojo detectado 3 veces seguidas en cruce Norte";
                        }
                    } else {
                        rojoCount.set(0);
                    }
                    return null;
                })
                .filter(msg -> msg != null)
                .doOnNext(System.out::println)
                .subscribeOn(Schedulers.parallel());

        // Unir todos los flujos y detectar alerta global
        Flux<String> eventosCriticos = Flux.merge(trafico, contaminacion, accidentes, trenes, semaforos);

        eventosCriticos.bufferTimeout(10, Duration.ofSeconds(1))
                .filter(lista -> lista.size() >= 3)
                .map(lista -> "\n🚨 Alerta global: Múltiples eventos críticos detectados en Meridian Prime")
                .doOnNext(System.out::println)
                .subscribe();

        // Mantener la app corriendo
        Thread.sleep(15000); // Ejecuta 15 segundos
    }
}
