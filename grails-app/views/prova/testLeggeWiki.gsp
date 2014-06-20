<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Legge in formato HTML</title>
</head>
<body>
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<g:form action="returnWiki">
    <input type="hidden" name="targetUri" value="${targetUri}" />
    <table>
        <tbody>
        <h>Contenuto</h>
        <tr>
            <td>Nome della pagina sulla wikipedia in italiano:</td>
            <td><input type="text" name="nomePagina" value="${nomePagina}" /></td>
        </tr>
        <tr>
            <td />
            <td><input type="submit" value="Download" /></td>
        </tr>
        </tbody>
    </table>
</g:form>
</body>
</html>
