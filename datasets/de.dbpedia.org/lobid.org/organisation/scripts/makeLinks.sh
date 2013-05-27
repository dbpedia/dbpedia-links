#!/bin/bash
# get the data from a SPARQL endpoint of lobid. Filter to get the 
# links to de.dbpedia.org and convert file to utf8

curl -L -H "Accept: text/turtle"  --data-urlencode "query=
CONSTRUCT { ?o <http://umbel.org/umbel#isLike> ?s . }
WHERE {
  graph <http://lobid.org/organisation/> {
    ?s <http://www.w3.org/2000/01/rdf-schema#seeAlso> ?o.
  }
}

LIMIT 50000" http://aither.hbz-nrw.de:8000/sparql/ | grep "de.dbpedia" > organisation_links_ascii.nt
native2ascii -encoding UTF-8 -reverse organisation_links_ascii.nt ../organisation_links.nt
rm organisation_links_ascii.nt

