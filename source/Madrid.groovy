import groovy.xml.XmlParser
import static groovy.json.JsonOutput.toJson
import static groovy.json.JsonOutput.prettyPrint
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

class MadridInstance {

    def descriptiveStatistics = new DescriptiveStatistics()

    def downloadXml() {
        def url = "https://informo.madrid.es/informo/tmadrid/pm.xml"
        def xml = new XmlParser().parse(url)
        def fechaHora = xml.fecha_hora.text()
        def ret = xml.pm.collect { item ->
            [idelem        : item.idelem.text(),
             fechahora     : fechaHora,
             descripcion   : item.descripcion.text(),
             accesoAsociado: item.accesoAsociado.text(),
             intensidad    : item.intensidad.text(),
             ocupacion     : item.ocupacion.text(),
             carga         : item.carga.text(),
             nivelServicio : item.nivelServicio.text(),
             intensidadSat : item.intensidadSat.text(),
             error         : item.error.text(),
             subarea       : item.subarea.text(),
             st_x          : item.st_x.text(),
             st_y          : item.st_y.text(),
            ]
        }
        ret
    }

    def transformItem(item) {
        prettyPrint(
                toJson([
                        id   : item.idelem,
                        nivel: item.nivelServicio
                ])
        )
    }

    def estacionToString(item){
        item.target.toFile().text
    }

    def measureItem( Map item ){
        descriptiveStatistics.addValue( ("0"+item.intensidadSat) as double)
    }

    def getMean(){
        descriptiveStatistics.mean
    }
}


def newInstance(){
    new MadridInstance()
}