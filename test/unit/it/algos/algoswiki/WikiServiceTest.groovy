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
        def mappa
        def primoElemento
        String richiesto = 'abati e badesse'

        mappa = wikiService.leggeModuloMappa(titoloModulo)
        assert mappa

        primoElemento = mappa.get(mappa.keySet()[0])
        assert primoElemento
        assert primoElemento instanceof String
        assert primoElemento == richiesto
    }// fine metodo test

    void testDownloadModuloDoppio() {
        String titoloModulo = 'Modulo:Bio/Plurale attività genere'
        def mappa
        def primoElemento
        def richiesto = ['abati', 'M']

        mappa = wikiService.leggeModuloMappa(titoloModulo)
        assert mappa

        primoElemento = mappa.get(mappa.keySet()[0])
        assert primoElemento
        assert primoElemento instanceof ArrayList
        assert primoElemento.size() == 2
        assert primoElemento == richiesto
    }// fine metodo test

    void testDownloadModuloQuadruplo() {
        String titoloModulo = 'Modulo:Bio/Plurale attività genere'
        def mappa
        def quintoElemento
        def richiesto = ['aforisti', 'M', 'aforiste', 'F']

        mappa = wikiService.leggeModuloMappa(titoloModulo)
        assert mappa

        quintoElemento = mappa.get(mappa.keySet()[4])
        assert quintoElemento
        assert quintoElemento instanceof ArrayList
        assert quintoElemento.size() == 4
        assert quintoElemento == richiesto
    }// fine metodo test

    void testDownloadModuloQuadruploAncora() {
        String titoloModulo = 'Modulo:Bio/Plurale attività genere'
        def mappa
        def decimoElemento
        def richiesto = ['alchimisti', 'M', 'alchimiste', 'F']

        mappa = wikiService.leggeModuloMappa(titoloModulo)
        assert mappa

        decimoElemento = mappa.get(mappa.keySet()[9])
        assert decimoElemento
        assert decimoElemento instanceof ArrayList
        assert decimoElemento.size() == 4
        assert decimoElemento == richiesto
    }// fine metodo test

} // fine della classe di test
