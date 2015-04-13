dbpedia-links
=============
A repo that contains links and type for DBpedia

#About
Current version 0.7
This README specifies how to contribute links to the DBpedia+ Data Stack.
Please read carefully. In case of questions, please use the GitHub Issue Tracker at https://github.com/dbpedia/dbpedia-links/issues
Further feedback can go the DBpedia Discussion mailinglist: https://lists.sourceforge.net/lists/listinfo/dbpedia-discussion

# Disclaimer
In order to allow widest possible dissemination, all data and code in this repository is to be treated as **public domain** or CC-0.
We assume that you are aware of this when contributing to the repository, your links and scripts will be re-used, hosted and mixed with other data. 

We expect that anybody using data from this repository will give proper attribution to the work of:
* DBpedia as a community
* This repository and its contributors as a whole
* The individual contributions

However, we will send friendly emails instead of lawyers, if we think attribution is not given properly. 


# How to contribute links
Please do a GitHub pull request to allow us to check your contribution.

1. Choose an appropriate folder:
    * links/dbpedia.org - for links from the main DBpedia namespace http://dbpedia.org/resource
    * links/xxx.dbpedia.org - for links from a subdomain of DBpedia, e.g. http://nl.dbpedia.org
    * links/other - for other links

2. We are linking by domain and subdomain, so please have a look whether your domain/subdomain already exists
Examples are:
    * viaf.org - links/dbpedia.org/viaf.org
    * lobid.org - links/dbpedia.org/lobid.org
    * lobid.org - links/xxx.dbpedia.org/de/lobid.org

3. Submit links
**Note** in this repo you can submit one or several of: 
    - a link file (N-Triples, one triple per line, DBpedia URL as subject, if larger than 200k triples ~20MB, bzip2 compressed)
    - a script generating above-mentioned link file
    - configuration files for SILK or LIMES
    - patches, i.e. white and blacklists for links 

within the folder mentioned in 2, please adhere to the following structure:

* README.md - documentation for the links 
* links.nt or links.nt.bz2 - the link file
* link-specs/ - SILK and LIMES config files
* scripts/ - any script that produces a link file
* patches/ - black or whitelist 

Please see the next section for details.

## Conventions

### README.md
The README.md file is very important and should document, who created the links and how the links were created. 

### links.nt
If you just have the link file, you can submit it to the appropriate folder. 
The file must:
* be in N-Triples format http://www.w3.org/TR/n-triples/
* have the DBpedia URI as subject
* use either
    * owl:sameAs
    * skos:{exact|close|...}Match
    * domain-specific properties such as http://rdvocab.info/RDARelationshipsWEMI/manifestationOfWork
    * you can submit types (using rdf:type) separately in the "types" folder

If the file is larger than 200k triples or 20MB please compress it using bzip2
#### Example
https://github.com/dbpedia/dbpedia-links/tree/master/links/dbpedia.org/eunis.eea.europa.eu


### link-specs/
You can submit XML configurations for SILK or LIMES, see the example
#### Example
https://github.com/dbpedia/dbpedia-links/tree/master/links/dbpedia.org/www.geonames.org

### scripts/
A simple script that generates the link file. We are using command-line linux to run it. 
#### Example 1
Java program started with a shell script
https://github.com/dbpedia/dbpedia-links/blob/master/links/dbpedia.org/gadm.geovocab.org/scripts/makeLinks.sh
#### Example 2
Shell script downloading the links
https://github.com/dbpedia/dbpedia-links/blob/master/links/dbpedia.org/lobid.org/manifestation/scripts/makeLinks.sh
#### Example 3
Shell script doing a SPARQL Construct query to retrieve links
https://github.com/dbpedia/dbpedia-links/blob/master/links/dbpedia.org/lobid.org/organisation/scripts/makeLinks.sh

### patches/







# Link Contributors

Please take care and try to maintain the links that you submitted. Please fix errors in other parts of the repo as well, if you happen to find any. 

- Sarven Capadisli, AKSW, Uni Leipzig (Csarven)
- Pascal Christoph, (dr0i)
- Sebastian Hellmann, AKSW, Uni Leipzig (kurzum)
- Anja Jentzsch, HPI Potsdam (ajeve)
- Barry Norton (BarryNorton)
- Søren Roug, (sorenroug)
- Christopher Gutteridge (cgutteridge)
- Heiko Paulheim, Uni Mannheim (HeikoPaulheim)
- Petar Ristoski, Uni Mannheim (petarR)
- Amy Guy, BBC / Uni Edinburgh (rhiaro)
 


# Conventions

**Please try honor these conventions**

2. For a list of currently used predicates (**might be extended easily, write to list**), see the file predicate-count.csv
    - For 1:1 mappings we recommend to use these: owl:sameAs, umbel:isLike, skos:{exact|close|...}Match
    - For 1:m, n:1 or n:m mappings it seems to make sense to use 
	Additionally, you can include types, which result from inference of the usage of the domain-specific linking property, e.g. the rdfs:domain of the property. E.g. rdrel:manifestationOfWork is rdfs:domain rdafrbr:Work, which entails that DBpedia entries should be of rdf:type rdafrbr:Work.
3. Note that we also count links to other classes as links, so if you want to add an external classification using rdf:type as linking property, that is fine as well. 

## Basic Folder Structure
/datasets/$fromDomain/$toDomain/$givenName where:

    - lobid.org: manifestation (because it links DBpedia to manifestations)
    - www4.wiwiss.fu-berlin.de: bookmashup  diseasome (links to two different datasets, but same domain)
- add the nt files with the links. If there is only one link set file, we suggest you just name it $givenName_links.nt
- if you are adding any types, please name them like the linkset files, but append "-types". E.g. if the linkset ist manifestation-links.nt the types should be manifestation_types.nt


### Subfolders in /datasets/$fromDomain/$toDomain/$givenName
* link-specs -> put all SILK link spec xml files in this folder
* scripts -> put any scripts you used for linking into this folder, 
* please do not forget a small readme.


### Rationale
We believe these three parts are really necessary for linksets:
The from and to is obvious. The name given to the linkset is an arbitrary distinction. Contributors can choose their own separation criteria:
So for each from to pair, you can have linksets from different contributors, about different domains , or created by different scripts.
Distinction criteria is therefore not fixed, but can be chosen according to what is practical. 

Example:  

https://github.com/dbpedia/dbpedia-links/tree/master/datasets/dbpedia.org/www4.wiwiss.fu-berlin.de/bookmashup

	datasets/dbpedia.org/www4.wiwiss.fu-berlin.de/bookmashup
	<http://dbpedia.org/resource/Neuromancer> <http://www.w3.org/2002/07/owl#sameAs> <http://www4.wiwiss.fu-berlin.de/bookmashup/books/0441569560> .

https://github.com/dbpedia/dbpedia-links/tree/master/datasets/dbpedia.org/www4.wiwiss.fu-berlin.de/diseasome
    
    datasets/dbpedia.org/www4.wiwiss.fu-berlin.de/diseasome
    <http://dbpedia.org/resource/Exostosis> <http://www.w3.org/2002/07/owl#sameAs> <http://www4.wiwiss.fu-berlin.de/diseasome/resource/diseases/386> 

https://github.com/dbpedia/dbpedia-links/blob/master/datasets/dbpedia.org/umbel.org/umbel-classification
	datasets/dbpedia.org/umbel.org/umbel-classification
	<http://dbpedia.org/resource/Aglaodiaptomus> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://umbel.org/umbel/rc/Crustacean> .
	<http://dbpedia.org/resource/Aglaodorum> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://umbel.org/umbel/rc/Plant> .


# Ideas:

2. (future work) A file metadata.ttl with useful information??
3. (future work) There will be some quality control; not everybody will be able to include any links he wants to include. We are open to ideas how to manage this. Consider "pull requests" as "application for inclusion"
4. (future work) Links will go into a graph named specified in the metadata.ttl and made subgraph of http://dbpedia.org, if the license is right
5. (future work) all metadata.ttl files will be loaded into the graph http://dbpedia-links.dbpedia.org/metadata

## Preliminary metadata.ttl
TODO add the names of the used nt files.


	@prefix dcterms: <http://purl.org/dc/terms/> .
	@prefix dc: <http://purl.org/dc/elements/1.1/> .
	@prefix void: <http://rdfs.org/ns/void#> .

	<http://dbpedia.org/links/transparency.270a.info> a void:Linkset ;
		void:objectsTarget <http://example.org/target/dataset> ;
		dc:author "Sarven Capadisli" ;
		dc:description "Please write your comment here!" .





