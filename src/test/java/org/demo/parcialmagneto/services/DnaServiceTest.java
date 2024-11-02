package org.demo.parcialmagneto.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.demo.parcialmagneto.entities.Dna;
import org.demo.parcialmagneto.repositories.DnaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DnaServiceTest {
    @Mock
    private DnaRepository dnaRepository;

    @InjectMocks
    private DnaService dnaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void givenExistingMutantDna_whenAnalyzeDna_thenReturnsTrue() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        String secuenciaDNA = String.join(",", dna);
        Dna mockDna = Dna.builder().dna(secuenciaDNA).isMutant(true).build();

        when(dnaRepository.findByDna(secuenciaDNA)).thenReturn(Optional.of(mockDna));

        DnaService dnaService = new DnaService(dnaRepository);
        boolean result = dnaService.analyzeDna(dna);

        assertTrue(result);
        verify(dnaRepository, never()).save(any(Dna.class));
    }



    @Test
    public void testFilas() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTTTGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }

    void testColumnas() {
        String[] dna = {
                "AAAAGG",
                "CTTGAG",
                "TTAAGG",
                "AGAAAG",
                "CCCCTA",
                "TTGCTG"
        };
        Assertions.assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testDiagonalesPrincipal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCATA",
                "TCACTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }

    @Test
    void testDiagonalesSecundariaIzq() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "TCCCTA",
                "TCACTG"
        };
        Assertions.assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testDiagonalesSecundariaDer() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "TCCCGA",
                "TCACTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testDiagonalesTerciariaIzq() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "TCCATA",
                "TCACTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testDiagonalesTerciariaDer() {
        String[] dna = {
                "CTAGTC",
                "GTATGC",
                "AGTTAC",
                "CTGGCA",
                "TAGCCA",
                "GATTGA"
        };
        assertTrue(DnaService.isMutant(dna));
    }
    @Test
    void testDiagonalesTerciariaIzqNoMutante() {
        String[] dna = {
                "ACGT",
                "CTAG",
                "GTCA",
                "TAGC"
        };
        assertFalse(DnaService.isMutant(dna));
    }

    @Test
    void testDiagonalesTerciariaDerNoMutante() {
        // Esta matriz representa un ADN que no tiene una diagonal terciaria mutante de derecha a izquierda
        String[] dna = {
                "TACG",
                "CTAG",
                "GTCA",
                "AGCT"
        };

        assertFalse(DnaService.isMutant(dna));
    }

    @Test
    public void testNoMutante() {
        String[] dna = {
                "ATCGTG",
                "GTCTGA",
                "CGTAGT",
                "ACTGCA",
                "GGATTC",
                "AATGCG"
        };
        assertFalse(DnaService.isMutant(dna));
    }
    @Test
    public void testNoMutante2() {
        String[] dna = {
                "ACGT",
                "TAGC",
                "CTAG",
                "GATC"
        };
        assertFalse(DnaService.isMutant(dna));
    }
    @Test
    public void testNoMutante3() {
        String[] dna = {
                "ACGT",
                "TGCA",
                "CTAG",
                "AGTC"
        };
        assertFalse(DnaService.isMutant(dna));
    }


    @Test
    void testMutant1() {
        String[] dna = {
                "ATCGAT",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        Assertions.assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testNonMutant1() {
        String[] dna = {
                "ATGC",
                "TACG",
                "CGTA",
                "GTAC"
        };
        assertFalse(DnaService.isMutant(dna));
    }

    @Test
    void testMutant2() {
        String[] dna = {
                "ATCGAT",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        Assertions.assertFalse(DnaService.isMutant(dna)); // Cambiar a `false` si no cumple con el umbral
    }

    @Test
    public void testMutant3() {
        String[] dna = {
                "CCCC",
                "CCCC",
                "CCCC",
                "CCCC"
        };
        assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testNonMutant2() {
        String[] dna = {
                "TGCA",
                "ATCG",
                "CGTA",
                "GCTA"
        };
        assertFalse(DnaService.isMutant(dna));
    }

    @Test
    public void testMutant4() {
        String[] dna = {
                "AAAAAAAAA",
                "TTTTTTTTT",
                "TTTTTTTTT",
                "TTTTTTTTT",
                "AAGTGGCGG",
                "GACCACTCA",
                "ACTTCTAGA",
                "CATCGGCAT",
                "CCAGTCCCA"
        };
        assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testMutant5() {
        String[] dna = {
                "TTTTTTTTT",
                "TTTTTTTTT",
                "TTTTTTTTT",
                "TTTTTTTTT",
                "AAGTGGGGG",
                "GACCACTCA",
                "ACTTCTAGA",
                "CATCGGCAT",
                "CCAGTCCCA"
        };
        assertTrue(DnaService.isMutant(dna));
    }
}
