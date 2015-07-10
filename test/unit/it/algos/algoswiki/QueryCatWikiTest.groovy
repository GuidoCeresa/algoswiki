package it.algos.algoswiki

/**
 * Created by gac on 10 lug 2015.
 * Using specific Templates (Entity, Domain, Modulo)
 */
public class QueryCatWikiTest extends GroovyTestCase {

    private static String TITOLO_CAT_BREVE = 'Eventi del 1902'
    private static String TITOLO_CAT_MEDIA = 'Nati nel 1420'
    private static String TITOLO_CAT_LUNGA = 'BioBot'

    // Setup logic here
    protected void setUp() throws Exception {
    } // fine del metodo iniziale

    // Tear down logic here
    protected void tearDown() throws Exception {
    } // fine del metodo iniziale

    void testBreve() {
        String titoloCategoria = TITOLO_CAT_BREVE
        QueryCatWiki query
        ArrayList lista
        String txtLista

        query = new QueryCatWiki(titoloCategoria)
        lista = query.getListaPageids()
        txtLista = query.getTxtPageids()
    }//end of single test

    void testMedia() {
        String titoloCategoria = TITOLO_CAT_MEDIA
        QueryCatWiki query
        ArrayList lista
        String txtLista

        query = new QueryCatWiki(titoloCategoria)
        lista = query.getListaPageids()
        txtLista = query.getTxtPageids()
    }//end of single test

    void testLunga() {
        String titoloCategoria = TITOLO_CAT_LUNGA
        QueryCatWiki query
        ArrayList lista
        String txtLista

        query = new QueryCatWiki(titoloCategoria)
        lista = query.getListaPageids()
        txtLista = query.getTxtPageids()
        def step
    }//end of single test

}// end of test class
