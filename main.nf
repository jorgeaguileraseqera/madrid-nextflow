
include { newInstance as Madrid } from './source/Madrid.groovy'

madrid = Madrid()

process emitStation{
    maxForks 10

    input:
    val item

    output:
    path 'estacion.txt'

    script:
    "echo '${madrid.transformItem(item)}' > estacion.txt"
}

process echoStation{
    maxForks 10

    input:
    file estacion

    output:
    stdout

    script:
    "echo ${madrid.estacionToString(estacion)}"
}

params.max = -1

workflow {
    Channel.fromList( madrid.downloadXml() ) \
      | take(params.max) \
      | filter{ it.descripcion } \
      | emitStation \
      | echoStation \
      | view
}