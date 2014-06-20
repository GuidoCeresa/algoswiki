/*
 * Main script to setup algoslib on installation
 */

def sourceFile
def targetFile

// copy readme into project
sourceFile = "${pluginBasedir}/grails-app/readme"
targetFile = "${basedir}/README-Lib"
ant.copy(file: sourceFile, tofile: targetFile, overwrite: true)
ant.delete(file: sourceFile)

print('------------')
print('Algoslib - creato (o sovrascritto) README-Lib')
print('------------')
