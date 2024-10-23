package org.demo.parcialmagneto.services;

import org.demo.parcialmagneto.entities.Dna;
import org.demo.parcialmagneto.repositories.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DnaService {

    private final DnaRepository dnaRepository;
    private static final int LONGITUD = 4;

    @Autowired
    public DnaService(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    public static boolean isMutant(String[] dna) {

        int n = dna.length;
        int contador = 0;

        contador = contador + revisarFilas(dna, n);
        if (contador > 1) return true;

        contador = contador + revisarColumnas(dna, n);
        if (contador > 1) return true;

        contador = contador + revisarDiagonales(dna, n);
        return contador > 1;

    }

    private static int revisarFilas(String[] dna, int n) {
        int contador = 0;

        for (int i = 0; i < n; i++) {
            int cuenta = 1;
            for (int j = 1; j < n; j++) {
                if (dna[i].charAt(j) == dna[i].charAt(j - 1)) {
                    cuenta++;
                    if (cuenta == LONGITUD) {
                        contador++;
                        if (contador > 1) return contador;
                    }
                } else {
                    cuenta = 1;
                }
            }
        }
        return contador;
    }

    private static int revisarColumnas(String[] dna, int n) {
        int contador = 0;

        for (int j = 0; j < n; j++) {
            int cuenta = 1;
            for (int i = 1; i < n; i++) {
                if (dna[i].charAt(j) == dna[i - 1].charAt(j)) {
                    cuenta++;
                    if (cuenta == LONGITUD) {
                        contador++;
                        if (contador > 1) return contador;
                    }
                } else {
                    cuenta = 1;
                }
            }
        }
        return contador;
    }

    private static int revisarDiagonales(String[] dna, int n) {
        int contador = 0;

        for (int i = 0; i <= n - LONGITUD; i++) {
            for (int j = 0; j <= n - LONGITUD; j++) {
                if (revisarDiagonal(dna, i, j, 1, 1, n)) {
                    contador++;
                    if (contador > 1) return contador; // Early exit
                }
            }
        }

        for (int i = 0; i <= n - LONGITUD; i++) {
            for (int j = LONGITUD - 1; j < n; j++) {
                if (revisarDiagonal(dna, i, j, 1, -1, n)) {
                    contador++;
                    if (contador > 1) return contador;
                }
            }
        }
        return contador;
    }

    private static boolean revisarDiagonal(String[] dna, int x, int y, int dx, int dy, int n) {
        char primero = dna[x].charAt(y);
        for (int i = 1; i < LONGITUD; i++) {
            if (x + i * dx >= n || y + i * dy >= n || y + i * dy < 0 || dna[x + i * dx].charAt(y + i * dy) != primero) {
                return false;
            }
        }
        return true;
    }

    public boolean analyzeDna(String[] dna) {
        String secuenciaDNA = String.join(",", dna);

        Optional<Dna> existingDna = dnaRepository.findByDna(secuenciaDNA);

        if (existingDna.isPresent()) {
            return existingDna.get().isMutant();
        }

        boolean isMutant = isMutant(dna);

        Dna dnaEntity = Dna.builder()
                           .dna(secuenciaDNA)
                           .isMutant(isMutant)
                           .build();

        dnaRepository.save(dnaEntity);
        return isMutant(dna);

    }
}
