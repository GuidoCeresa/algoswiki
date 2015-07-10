package it.algos.algoswiki

import groovy.json.JsonSlurper
import groovy.json.internal.LazyMap

/**
 * Created by gac on 10 lug 2015.
 * Using specific Templates (Entity, Domain, Modulo) 
 */
public class QueryCatWiki {

    private static String QUERY = 'query'
    private static String PAGEID = 'pageid'
    private static String PAGES = 'pages'
    private static String REVISIONS = 'revisions'
    private static String CATEGORY_MEMBERS = 'categorymembers'
    private static String QUERY_CONTINUE = 'query-continue'

    /* codifica dei caratteri */
    public static String ENC = 'UTF-8'

    /* Formato dati selezionato per la risposta alla Request */
    public static Format FORMAT = Format.json

    /* prefisso URL base */
    public static String API_HTTP = 'https://'

    //--language selezionato (per adesso solo questo)
    protected static String LANGUAGE = 'it'

    /* prefisso iniziale (prima del progetto) */
    public static String API_WIKI = '.'

    /* azione API generica */
    public static String API_ACTION = '.org/w/api.php?action='

    //--progetto selezionato (per adesso solo questo)
    protected static String PROJECT = 'wikipedia'

    /* azione API delle query */
    public static String API_QUERY = API_ACTION + 'query'

    /* suffisso per il formato della risposta */
    public static String API_FORMAT = '&format=' + FORMAT.toString()

    //--stringa per la lista di categoria
    private static String CAT = '&list=categorymembers'

    //--stringa selezionare il namespace (0=principale - 14=sottocategorie) (per adesso solo il principale)
    private static String NS = '&cmnamespace=0'

    //--stringa selezionare il tipo di categoria (page, subcat, file) (per adesso solo page)
    private static String TYPE = '&cmtype=page'

    //--stringa per ottenere il codice di continuazione
    private static String CONT = '&rawcontinue'

    //--stringa per selezionare il numero di valori in risposta
    private static String LIMIT = '&cmlimit=500'

    //--stringa per indicare il titolo della pagina
    private static String TITLE = '&cmtitle=Category:'

    //--stringa iniziale (sempre valida) del DOMAIN a cui aggiungere le ulteriori specifiche
    protected static String API_BASE = API_HTTP + LANGUAGE + API_WIKI + PROJECT + API_QUERY + API_FORMAT

    //--stringa iniziale (sempre valida) del DOMAIN a cui aggiungere le ulteriori specifiche
    private static String API_BASE_CAT = API_BASE + CAT + NS + TYPE + CONT + LIMIT + TITLE

    //--stringa per il successivo inizio della lista
    private static String CONTINUE = '&cmcontinue='

    // lista di pagine della categoria (namespace=0)
    private ArrayList<Integer> listaPageids

    //--tipo di ricerca della pagina
    //--di default il titolo
    private TipoRicerca tipoRicerca = TipoRicerca.title

    //--tipo di request - solo una per leggere - due per scrivere
    //--di default solo lettura (per la scrittura serve il login)
    private TypoRequest tipoRequest = TypoRequest.read


    protected String title
    private String pageid

    // risultato della pagina
    // risultato grezzo della query nel formato prescelto
    protected String risultato

    //--token per la continuazione della query
    protected String continua = ''

    /**
     * Costruttore completo
     */
    public QueryCatWiki(String title) {
        this(title, TipoRicerca.title, TypoRequest.read)
    }// fine del metodo costruttore

    /**
     * Costruttore completo
     */
    public QueryCatWiki(String titlepageid, TipoRicerca tipoRicerca, TypoRequest tipoRequest) {
        this.tipoRicerca = tipoRicerca
        this.tipoRequest = tipoRequest
        this.inizializza(titlepageid)
    }// fine del metodo costruttore



    protected void inizializza(String titlepageid) {
        String testo

        switch (tipoRicerca) {
            case TipoRicerca.title:
                title = titlepageid
                break;
            case TipoRicerca.pageid:
                pageid = titlepageid
                break;
            case TipoRicerca.listaPageids:
                break;
            default: // caso non definito
                break;
        } // fine del blocco switch

        firstRequest()
        while (!continua.equals('')) {
            firstRequest()
        } // fine del blocco while
        def step
    } // fine del metodo

    /**
     * This module only accepts POST requests.
     * Parameters first request:
     *      action  = query
     *      format  = json
     *      prop    = info|revision
     *      intoken = edit
     *      titles  = xxx
     * Return:
     *      "pageid": "22958",
     *      "ns": "2",
     *      "title": "Utente:Gac/Sandbox4",
     *      "contentmodel": "wikitext",
     *      "pagelanguage": "it",
     *      "touched": "2012-11-05T09:32:37Z",
     *      "lastrevid": 53714557,
     *      "counter": "",
     *      "length": 10,
     *      "starttimestamp": "2013-09-15T05:54:35Z",
     *      "edittoken": "c3c28fbdf02b792bbcd367377d6ed6d5+\\",
     *      "revid": 53714557,
     *      "parentid": 53714550,
     *      "minor": "",
     *      "user": "Gac",
     *      "timestamp": "2012-11-05T09:32:37Z",
     *      "comment": "test"
     */
    //--legge la pagina per ottenere il token
    //--recupera informazioni sulla pagina
    //--recupera il risultato della pagina
    private firstRequest() {
        // variabili e costanti locali di lavoro
        boolean continua = true
        String domain
        URLConnection connection;
        InputStream input = null
        InputStreamReader inputReader = null
        BufferedReader readBuffer = null
        StringBuffer textBuffer = new StringBuffer()
        String stringa

        // find the target
        domain = this.getDomain()
        connection = new URL(domain).openConnection()
        connection = regolaConnessione(connection)

        // regola l'entrata
        // regola l'entrata
        try { // prova ad eseguire il codice
            input = connection.getInputStream();
            inputReader = new InputStreamReader(input, 'UTF8');
        } catch (Exception unErrore) { // intercetta l'errore
            println('timeout')
            continua = false
        }// fine del blocco try-catch

        // legge la risposta
        if (continua) {
            readBuffer = new BufferedReader(inputReader)
            while ((stringa = readBuffer.readLine()) != null) {
                textBuffer.append(stringa)
            }// fine del blocco while
        }// fine del blocco if

        // chiude
        if (readBuffer) {
            readBuffer.close()
        }// fine del blocco if
        if (inputReader) {
            inputReader.close()
        }// fine del blocco if
        if (input) {
            input.close()
        }// fine del blocco if

        // valore di ritorno della request
        risultato = textBuffer.toString()
        this.regolaRisultato()
    } // fine del metodo


    /**
     * Regola i parametri della connessione
     * Recupera i cookies dal Login di registrazione
     *
     * @param urlConn connessione
     */
    protected static URLConnection regolaConnessione(URLConnection urlConn) {
        // variabili e costanti locali di lavoro
        String txtCookies

        urlConn.setDoOutput(true)

        // regolo i cookies e le property
        urlConn.setRequestProperty('Accept-Encoding', 'GZIP');
        urlConn.setRequestProperty('Content-Encoding', 'GZIP');
        urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
        urlConn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; it-it) AppleWebKit/418.9 (KHTML, like Gecko) Safari/419.3");

        // valore di ritorno
        return urlConn
    } // fine del metodo

    /**
     * Costruisce il domain per l'URL dal titolo
     *
     * @param titolo
     * @return domain
     */
    protected String getDomain() {
        String domain = ''
        String titolo
        String startDomain = API_BASE_CAT

        titolo = URLEncoder.encode(title, ENC)

        if (titolo) {
            domain = startDomain + titolo
        }// fine del blocco if

        if (continua) {
            domain += CONTINUE + continua
        }// fine del blocco if

        return domain
    } // fine del metodo

    /**
     * Informazioni, risultato e validita della risposta
     * Controllo del risultato (testo) ricevuto
     * Estrae i valori e costruisce una mappa
     *
     * Sovrascritto nelle sottoclassi
     */
    protected void regolaRisultato() {
        ArrayList<Integer> lista
        String txtContinua
        String risultatoRequest = this.getRisultato()

        lista = creaListaCatJson(risultatoRequest)
        if (lista) {
            this.addLista(lista)
        }// fine del blocco if

        txtContinua = creaCatContinue(risultatoRequest)
        this.continua = txtContinua
    } // fine del metodo

    /**
     * Crea una lista di pagine (valori pageids) dal testo JSON di una pagina
     *
     * @param text in ingresso
     * @return lista pageid (valori Integer)
     */
    public static ArrayList<Integer> creaListaCatJson(String textJSON) {
        ArrayList<Integer> lista = null
        ArrayList listaTmp = null
        def result
        JsonSlurper slurper = new JsonSlurper()
        result = slurper.parseText(textJSON)

        if (result."${QUERY}") {
            if (result."${QUERY}"."${CATEGORY_MEMBERS}") {
                listaTmp = result."${QUERY}"."${CATEGORY_MEMBERS}"
                lista = converteListaCat(listaTmp)
            }// fine del blocco if
        }// fine del blocco if-else

        return lista
    } // fine del metodo


    /**
     * Estrae il valore del parametro continue dal testo JSON di una pagina
     *
     * @param text in ingresso
     * @return parametro continue
     */
    public static String creaCatContinue(String textJSON) {
        String textContinue = ''
        LazyMap lazyMap
        def result
        JsonSlurper slurper = new JsonSlurper()
        result = slurper.parseText(textJSON)

        if (result."${QUERY_CONTINUE}") {
            if (result."${QUERY_CONTINUE}"."${CATEGORY_MEMBERS}") {
                lazyMap = result."${QUERY_CONTINUE}"."${CATEGORY_MEMBERS}"
                textContinue = estraeContinue(lazyMap)
            }// fine del blocco if
        }// fine del blocco if-else

        return textContinue
    } // fine del metodo


    /**
     * Estrae il valore del parametro continue dalla mappa
     *
     * @param text in ingresso
     * @return parametro continue
     */
    private static String estraeContinue(LazyMap lazyMap) {
        String textContinue = ''
        def mappaValori
        String valore
        String sep = '\\|'
        def parti

        mappaValori = lazyMap.values()
        valore = mappaValori[0]
        parti = valore.split(sep)
        textContinue = parti[1]

        //@todo rimetto il valore intero (e non le parti) perché così adesso funziona
        return valore
    } // fine del metodo


    void addLista(ArrayList<Integer> listaNew) {
        ArrayList<Integer> lista

        lista = this.getListaPageids()
        if (!lista) {
            lista = new ArrayList<Integer>()
        }// fine del blocco if

        lista = lista + listaNew
        this.setListaPageids(lista)
    } // fine del metodo

    /**
     * Converte i typi di una mappa secondo i parametri PagePar
     *
     * La mappa in ingresso contiene ns, pageid e title
     * Utilizzo solo il pageid (Integer)
     *
     * @param mappa standard (valori String) in ingresso
     * @return mappa typizzata secondo PagePar
     */
    private static ArrayList<Integer> converteListaCat(ArrayList listaIn) {
        ArrayList<Integer> lista = new ArrayList<Integer>()
        int value

        listaIn?.each {
            value = (int) it[PAGEID]
            lista.add(value)
        } // fine del ciclo each

        return lista
    } // fine del metodo


    void setListaPageids(ArrayList<Integer> listaPageids) {
        this.listaPageids = listaPageids
    } // fine del metodo

    public ArrayList<Integer> getListaPageids() {
        return listaPageids
    } // fine del metodo

    public String getTxtPageids() {
        return creaListaPageids(getListaPageids())
    } // fine del metodo

    /**
     * Crea una stringa di testo, con tutti i valori della lista, separati dal pipe
     *
     * @param lista (valori Integer) in ingresso
     * @return stringa di valori
     */
    public static String creaListaPageids(ArrayList<Integer> lista) {
        String testo = ''
        String sep = '|'

        lista?.each {
            testo += it
            testo += sep
        } // fine del ciclo each
        testo = levaCoda(testo, sep)

        return testo
    } // fine del metodo

    /**
     * Elimina la coda terminale della stringa, se esiste.
     * <p>
     * Esegue solo se la stringa è valida. <br>
     * Se manca la coda, restituisce la stringa. <br>
     * Elimina spazi vuoti iniziali e finali. <br>
     *
     * @param entrata stringa in ingresso
     * @param coda da eliminare
     * @return uscita stringa convertita
     */
    public static String levaCoda(String entrata, String coda) {
        String uscita = entrata;

        if (entrata != null) {
            uscita = entrata.trim();
            if (coda != null) {
                coda = coda.trim();
                if (uscita.endsWith(coda)) {
                    uscita = uscita.substring(0, uscita.length() - coda.length());
                    uscita = uscita.trim();
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        return uscita;
    } // fine del metodo

    String getRisultato() {
        return risultato
    }

    /**
     * Enumeration per il flag del tipo di query
     */
    public enum TipoRicerca {
        title, pageid, listaPageids, generator
    }// fine della classe Enumeration

    /**
     * Enumeration locale per il flag del tipo di query
     */
    public enum TypoRequest {
        read, write
    }// fine della classe Enumeration

}// end of class
