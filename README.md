# Madrid Nextflow

## Run

`nextflow run main.nf`

## Intellij

Mark `source` directory as `Generated Sources Root`

## Debug

in a terminal execute:

`NXF_JVM_ARGS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=5000,suspend=y" nextflow run main.nf --max=3`

create a remote debug session (port 5000), put breakpoints at `source/Madrid.groovy` and attach to the remote session