package it.algos.algoswiki

/**
 * Created by gac on 11/09/14.
 */
class WikiServiceTest extends GroovyTestCase {

    def wikiService = new WikiService()

    // Setup logic here
    void setUp() {
    } // fine del metodo iniziale

    // Tear down logic here
    void tearDown() {
    } // fine del metodo iniziale

    void testDownloadModuloSemplice() {
        String titoloModulo = 'Modulo:Bio/Plurale attività'
        String testoModulo
        String testoUtile
        def mappa
        def primoElemento
        String richiesto = 'abati e badesse'

        testoModulo = wikiService.leggeTesto(titoloModulo)
        testoUtile = wikiService.leggeModuloTesto(testoModulo)

        mappa = wikiService.leggeModuloMappa(titoloModulo)
        assert mappa

        primoElemento = mappa.get(mappa.keySet()[0])
        assert primoElemento
        assert primoElemento instanceof String
        assert primoElemento == richiesto
    }// fine metodo test

    void testDownloadModuloDoppio() {
        String titoloModulo = 'Modulo:Bio/Plurale attività genere'
        String testoModulo
        String testoUtile
        def mappa
        def primoElemento
        def richiesto = ['abati', 'M']

        testoModulo = wikiService.leggeTesto(titoloModulo)
        testoUtile = wikiService.leggeModuloTesto(testoModulo)

        mappa = wikiService.leggeModuloMappa(titoloModulo)
        assert mappa

        primoElemento = mappa.get(mappa.keySet()[0])
        assert primoElemento
        assert primoElemento instanceof ArrayList
        assert primoElemento == richiesto
    }// fine metodo test

} // fine della classe di test
