package it.algos.algoswiki

/**
 * Created by gac on 25/02/15.
 */
class WikiLibTest extends GroovyTestCase {

    def wikiService = new WikiService()

    // Setup logic here
    void setUp() {
    } // fine del metodo iniziale

    // Tear down logic here
    void tearDown() {
    } // fine del metodo iniziale

    void testCreaTabellaSortable() {
        String titoloPagina = 'Utente:Biobot/2'
        String summary = 'test'
        Login login
        HashMap mappa = new HashMap()
        ArrayList titoliDue = new ArrayList()
        ArrayList titoliTre = new ArrayList()
        ArrayList listaDue = new ArrayList()
        ArrayList listaTre = new ArrayList()
        ArrayList righeDoppie = new ArrayList()
        ArrayList righeTriple = new ArrayList()
        ArrayList righeTriple2 = new ArrayList()
        ArrayList righeTriple3 = new ArrayList()
        ArrayList righeTriple4 = new ArrayList()
        String testoPagina

        titoliDue.add('alfa')
        titoliDue.add('beta')

        titoliTre.add('alfa largo')
        titoliTre.add('be largo')
        titoliTre.add('ga')

        righeDoppie.add(21)
        righeDoppie.add(37)
        righeTriple.add(18560)
        righeTriple.add('alfa')
        righeTriple.add(876543)
        righeTriple2.add(18560)
        righeTriple2.add('uno')
        righeTriple2.add(1245003)
        righeTriple3.add(321)
        righeTriple3.add('si')
        righeTriple3.add(67000)
        righeTriple4.add(2)
        righeTriple4.add('no')
        righeTriple4.add(4567)

        listaDue.add(righeDoppie)
        listaDue.add(righeDoppie)
        listaDue.add(righeDoppie)

        listaTre.add(righeTriple)
        listaTre.add(righeTriple2)
        listaTre.add(righeTriple3)
        listaTre.add(righeTriple4)

        testoPagina = WikiLib.creaTable()
        assert testoPagina.equals('')

        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina.equals('')

        mappa.put(WikiLib.MAPPA_TITOLI, null)
        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina.equals('')

        mappa.put(WikiLib.MAPPA_TITOLI, titoliDue)
        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina.equals('')

        mappa.put(WikiLib.MAPPA_LISTA, null)
        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina.equals('')

        mappa.put(WikiLib.MAPPA_LISTA, listaTre)
        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina.equals('')

        mappa = new HashMap()
        mappa.put(WikiLib.MAPPA_TITOLI, titoliTre)
        mappa.put(WikiLib.MAPPA_LISTA, listaDue)
        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina.equals('')

        login = new Login('Biobot', 'fulvia')
        assert login.isValido()
        assert login.isBot()

        mappa = new HashMap()
        mappa.put(WikiLib.MAPPA_TITOLI, titoliDue)
        mappa.put(WikiLib.MAPPA_LISTA, listaDue)
        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina

        mappa = new HashMap()
        mappa.put(WikiLib.MAPPA_TITOLI, titoliTre)
        mappa.put(WikiLib.MAPPA_LISTA, listaTre)
        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina

        mappa.put(WikiLib.MAPPA_SORTABLE, false)
        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina

        mappa.put(WikiLib.MAPPA_NUMERI_FORMATTATI, true)
        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina

        mappa.put(WikiLib.MAPPA_NUMERAZIONE_PROGRESSIVA, true)
        testoPagina = WikiLib.creaTable(mappa)
        assert testoPagina

        new Edit(login, titoloPagina, testoPagina, summary)
    }// fine metodo test


} // fine della classe di test

