#!/bin/bash
# get the data from datahub.io and transform it to the expected links

rapper -i turtle http://lobid.org/download/dumps/DE-605/enrich/2de-dbpedia.ttl | perl -pe 's|(<.*?>).*(<.*?>).*|\2 <http://rdvocab.info/RDARelationshipsWEMI/manifestationOfWork> \1 .|' | grep '^<http://de.dbpedia.org/'
