import groovy.xml.XmlParser
import static groovy.json.JsonOutput.toJson
import static groovy.json.JsonOutput.prettyPrint

class MadridInstance {
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
        "$item"
    }
}


def newInstance(){
    new MadridInstance()
}