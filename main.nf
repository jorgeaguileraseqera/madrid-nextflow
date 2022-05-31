
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
    "echo '${madrid.estacionToString(estacion)}'"
}

params.max = -1

workflow {
    ch = Channel.fromList( madrid.downloadXml() )

    ch.subscribe( onNext:{ item->
        madrid.measureItem(item)
        item
    }, onComplete:{
        println "The mean of intensidadSat is $madrid.mean"
    })

    ch.take(params.max) \
      | filter{ it.descripcion } \
      | emitStation \
      | echoStation
}
