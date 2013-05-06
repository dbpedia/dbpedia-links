dbpedia-links
=============
A repo that contains outgoing links in N-Triples format for DBpedia

#About
Current version 0.6

**This readme defines the workflow to add your links to this repo and to DBpedia. It is still being discussed at the DBpedia discussion mailing list and subject to change at any moment. Also the repo structure might still change a lot.**

# Open Issues 
Actually, it seems that more things are yet unclear than fixed. Please don't be slowed by formalities, just submit your links please, we will clean it up, after a while, if necessary.


- contributions can be done either via pull request or we will add you to the repo
- folder structure seems quite sensible already 
- metadata and licence is still a very open issue, please just provide a readme.
- internationalisation: Please create folders for de.dbpedia.org and other
- lots of links are still under datasets/todo and need to be moved

# Feedback

goes (as always) to the DBpedia Discussion mailinglist: https://lists.sourceforge.net/lists/listinfo/dbpedia-discussion

# License
It seems, that we require that any uploaded links are at CC-BY-SA (or less restrictive e.g. CC-BY) like the rest of DBpedia. 
That is the requirement to be hosted uniformly with DBpedia. 
We assume that you know this and that you acknowledge this by pushing your links. 
If you want to publish your links under a different license, please add a LICENCE.txt file in your linkset folder.
Note that your links will be hosted in a separate RDF graph then and not be accessible via Linked Data, but just via SPARQL. 


# Link Contributors

Please take care and try to maintain the links that you submitted. Please fix errors in other parts of the repo as well, if you happen to find any. 

- Sarven Capadisli, AKSW, Uni Leipzig (Csarven)
- Pascal Christoph, (dr0i)
- Sebastian Hellmann, AKSW, Uni Leipzig (kurzum)
- Anja Jentzsch, HPI Potsdam (ajeve)
- Barry Norton (BarryNorton)
- Søren Roug, (sorenroug)
- Christopher Gutteridge (cgutteridge)
 
## Get Access

Sign up and send an email to DBpedia developers list or to hellmann _ at _ informatik.uni-leipzig.de . If you are accepted, you will get write access to the repository.

Please also help to (1) merge pull request (2) keep the repository clean 

# Usage and Workflow

Please improve the links and add new files and then do a pull request.  Data will be loaded into the DBpedia endpoint in the future. 
We don't know at which point in the future this will happen. We also plan to update links in http://live.dbpedia.org on a weekly basis. 

In case you are not a Git expert, GitHub allows you to upload links with their GUI:

1. create your own GitHub account
2. fork this repo https://github.com/dbpedia/dbpedia-links into your GitHub space
3. make your edits by either improving previous links or adding new folders
    - Please see below on how the data in the folder should be structured
4. Finalize your edits by sending a "pull request" via GitHub

# Conventions

**Please try honor these conventions**

1. (strict) All N-Triples files must be alphabetically sorted without duplicate triples for better diffs. This is in accordance with the Unix command: sort -u .
2. For a list of currently used predicates (**might be extended easily, write to list**), see the file predicate-count.csv
    - For 1:1 mappings we recommend to use these: owl:sameAs, umbel:isLike, skos:{exact|close|...}Match
    - For 1:m, n:1 or n:m mappings it seems to make sense to use domain-specific properties such as http://rdvocab.info/RDARelationshipsWEMI/manifestationOfWork
	Additionally, you can include types, which result from inference of the usage of the domain-specific linking property, e.g. the rdfs:domain of the property. E.g. rdrel:manifestationOfWork is rdfs:domain rdafrbr:Work, which entails that DBpedia entries should be of rdf:type rdafrbr:Work.
3. Note that we also count links to other classes as links, so if you want to add an external classification using rdf:type as linking property, that is fine as well. 

## Basic Folder Structure
/datasets/$fromDomain/$toDomain/$givenName where:

- $fromDomain should be the domain of the subject of the outgoing triples (e.g. dbpedia.org or de.dbpedia.org )
- $toDomain should be the domain of the object (e.g. transparency.270a.info )
- $givenName should be an arbitrary name for the linkset, either what it is about (person-links) or who created the linkset (submitted-by-peter) or both. Some examples follow:
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





