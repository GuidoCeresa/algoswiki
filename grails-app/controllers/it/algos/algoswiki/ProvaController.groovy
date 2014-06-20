package it.algos.algoswiki

import it.algos.algoslib.Lib

class ProvaController {

    def grailsApplication
    def wikiService

    def index() {
        render(view: 'testLogin', model: [targetUri: 'NonServe', nickname: 'Biobot'], params: params)
    }

    def wikiLoginTest() {
        params.returnController = 'nomeDelControllerDiRitorno'
        params.returnAction = 'nomeDelMetodoDiRitorno'
        redirect(controller: 'LoginWiki', action: 'login', params: params)
    }// fine del metodo


    def testLogin() {
        String returnAction = ''
        [returnAction: returnAction, targetUri: 'NonServe', nickname: 'Biobot']
    }// fine del metodo

    def testLeggeHtml() {
        [targetUri: 'NonServe', nomePagina: '']
    }// fine del metodo

    def testLeggeWiki() {
//        String returnAction = 'testLeggeWiki'
//        returnAction=''
//        if (grailsApplication.config.login == null) {
//            render(view: 'testLogin', model: [returnAction: returnAction, targetUri: 'NonServe', nickname: 'Biobot'], params: params)
//        }// fine del blocco if

        [targetUri: 'NonServe', nickname: 'Biobot']
    }// fine del metodo

    def testCategoria() {
        [targetUri: 'NonServe', nomeCategoria: 'Comuni della provincia di Modena']
    }// fine del metodo

    def returnLogin() {
        String nickname = ''
        String password = ''
        Login login
        String returnAction

        if (params.nickname) {
            nickname = params.nickname
        }// fine del blocco if
        if (params.password) {
            password = params.password
        }// fine del blocco if
        if (params.returnAction) {
            returnAction = params.returnAction
        }// fine del blocco if

        if (nickname && password) {
            login = new Login(nickname, password)
            if (login && login.isValido()) {
                assert login.isValido()
                assert login.getFirstResult() == ErrLogin.needToken
                assert login.getRisultato() == ErrLogin.success
                assert login.getToken().size() > 20
                assert login.getCookiePrefix() == 'itwiki'
                assert login.getSessionId().size() > 20

                //--registra il login nella property globale
                grailsApplication.config.login = login

                flash.message = 'Collegato come ' + login.getUserName()
            } else {
                grailsApplication.config.login = null
                flash.error = 'Non collegato'
            }// fine del blocco if-else
        }// fine del blocco if

        if (returnAction) {
            redirect(action: returnAction)
        } else {
            redirect(uri: '/')
        }// fine del blocco if-else
    }// fine del metodo

    def returnHtml() {
        String titolo = ''
        Pagina pagina = null
        String testo = ''

        if (params.nomePagina) {
            titolo = params.nomePagina
        }// fine del blocco if

        if (wikiService) {
            pagina = wikiService.leggePagina(titolo)
        }// fine del blocco if

        if (pagina) {
            testo = pagina.getTesto()
        }// fine del blocco if

        render testo
    }// fine del metodo

    def returnWiki() {
        String titoloWiki = ''
        Pagina pagina = null
        String testo = ''

        if (params.nomePagina) {
            titoloWiki = params.nomePagina
        }// fine del blocco if

        if (titoloWiki) {
            testo = Lib.Web.leggeItWiki(titoloWiki)
        }// fine del blocco if

        render testo
    }// fine del metodo

    def returnCategoria() {
        String titoloWiki = ''
        Categoria cat = null
        String testo = 'Non sono riuscito a leggere la categoria'
        ArrayList listaVoci
        ArrayList listaCat
        String aCapo = '<br>'
        int pos

        if (params.nomeCategoria) {
            titoloWiki = params.nomeCategoria
        }// fine del blocco if

        if (titoloWiki) {
            cat = new Categoria(titoloWiki, (Login) grailsApplication.config.login)
        }// fine del blocco if

        if (cat) {
            if (cat.risultato == Risultato.noLogin) {
                testo = 'Non funziona il login'
            } else {
                testo = ''
                listaVoci = cat.listaNomiVoci
                if (listaVoci && listaVoci.size() > 0) {
                    pos = 0
                    testo += aCapo + "Elenco delle ${cat.numVoci} pagine:" + aCapo
                    listaVoci.each {
                        pos++
                        testo += pos + ' ' + it + aCapo
                    } // fine del ciclo each
                } else {
                    testo += 'Non ci sono pagine:' + aCapo + aCapo
                }// fine del blocco if-else
                listaCat = cat.listaNomiCat
                if (listaCat && listaCat.size() > 0) {
                    pos = 0
                    testo += aCapo + "Elenco delle ${cat.numCat} sottocategorie:" + aCapo
                    listaCat.each {
                        pos++
                        testo += pos + ' ' + it + aCapo
                    } // fine del ciclo each
                } else {
                    testo += 'Non ci sono sottocategorie:' + aCapo + aCapo
                }// fine del blocco if-else
            }// fine del blocco if-else
        }// fine del blocco if

        render testo
    }// fine del metodo


}// fine del controller
