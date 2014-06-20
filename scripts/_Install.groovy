/*
 * Main script to setup algosvers on installation
 */

def sourceFile
def targetFile

// copy WikiBootStrap into project
sourceFile = "${pluginBasedir}/grails-app/conf/WikiBootStrap.groovy"
targetFile = "${basedir}/grails-app/conf/WikiBootStrap.groovy"
ant.copy(file: sourceFile, tofile: targetFile, overwrite: false)
ant.delete(file: sourceFile)

print('------------')
print('Algoswiki - creato (NON sovrascritto) WikiBootStrap')
print('------------')

// copy Readme into project
sourceFile = "${pluginBasedir}/README"
targetFile = "${basedir}/README-Wiki"
ant.copy(file: sourceFile, tofile: targetFile, overwrite: true)
ant.delete(file: sourceFile)

print('------------')
print('Algoswiki - creato (o sovrascritto) README-Wiki')
print('------------')
