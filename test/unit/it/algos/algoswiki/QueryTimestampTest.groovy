package it.algos.algoswiki

/**
 * Created by gac on 25/02/15.
 */
class QueryTimestampTest extends GroovyTestCase {

    def wikiService = new WikiService()

    // Setup logic here
    void setUp() {
    } // fine del metodo iniziale

    // Tear down logic here
    void tearDown() {
    } // fine del metodo iniziale

    void testCreaTabellaSortable() {
        String listaPageIds = '3397115|4452510|1691379|3520373|4956588|5136975|2072357|4700355|3900631|3040347'
        QueryTimestamp query

        query = new QueryTimestamp(listaPageIds)
        def stop2
    }// fine metodo test


} // fine della classe di test

