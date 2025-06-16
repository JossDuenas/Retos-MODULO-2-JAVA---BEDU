import java.util.*;
import java.util.function.Predicate;

public class Main {

    // Mostrar cualquier tipo de material
    public static void mostrarMateriales(List<? extends MaterialCurso> lista) {
        for (MaterialCurso m : lista) {
            m.mostrarDetalle();
        }
    }

    // Sumar duración total de videos
    public static void contarDuracionVideos(List<? extends Video> lista) {
        int total = 0;
        for (Video v : lista) {
            total += v.getDuracion();
        }
        System.out.println("\n🎥 Duración total de videos: " + total + " minutos");
    }

    // Marcar ejercicios como revisados
    public static void marcarEjerciciosRevisados(List<? super Ejercicio> lista) {
        for (Object obj : lista) {
            if (obj instanceof Ejercicio) {
                ((Ejercicio) obj).marcarRevisado();
            }
        }
    }

    // Filtrar materiales por autor
    public static <T extends MaterialCurso> void filtrarPorAutor(List<T> lista, Predicate<MaterialCurso> filtro) {
        System.out.println("\n🔍 Filtrando materiales por autor:");
        for (T material : lista) {
            if (filtro.test(material)) {
                material.mostrarDetalle();
            }
        }
    }

    public static void main(String[] args) {
        List<MaterialCurso> materiales = new ArrayList<>();

        // Videos
        Video v1 = new Video("Introducción a Java", "Mario", 15);
        Video v2 = new Video("POO en Java", "Carlos", 20);

        // Artículos
        Articulo a1 = new Articulo("Historia de Java", "Ana", 1200);
        Articulo a2 = new Articulo("Tipos de datos", "Luis", 800);

        // Ejercicios
        Ejercicio e1 = new Ejercicio("Variables y tipos", "Luis");
        Ejercicio e2 = new Ejercicio("Condicionales", "Mario");

        // Agregamos todos
        materiales.addAll(List.of(v1, v2, a1, a2, e1, e2));

        // Mostrar todos los materiales
        System.out.println("📚 Materiales del curso:");
        mostrarMateriales(materiales);

        // Contar duración total de videos
        contarDuracionVideos(List.of(v1, v2));

        // Marcar ejercicios como revisados
        marcarEjerciciosRevisados(materiales);

        // Filtrar por autor "Mario"
        filtrarPorAutor(materiales, m -> m.getAutor().equalsIgnoreCase("Mario"));
    }
}