dbpedia-links
=============
A repo that contains outgoing links in N-Triples format for DBpedia

#About

This readme defines the workflow to add your links to this repo. It is still being discussed at the DBpedia discussion mailing list and subject to change at any moment. Also the repo structure might still change a lot.
Current version 0.5

**Note: We require that any uploaded links are at most CC-BY-SA (or less restrictive e.g. CC-BY) like the rest of DBpedia. That is the requirement to be hostedwith DBpedia. We assume that you know this and that you acknowledge this by pushing your links. We also need to think about the future maintainence of the project, so in fact we are unable to accept anything that is not under an open licence. Please consider anything you upload as donated to the project.** 


# Link Contributors

Please take care and try to maintain the links that you submitted. Please fix errors in other parts of the repo as well, if you happen to find any. 

- Sarven Capadisli, AKSW, Uni Leipzig (Csarven)
- Sebastian Hellmann, AKSW, Uni Leipzig (kurzum)
- Anja Jentzsch, HPI Potsdam (ajeve)
- Søren Roug, (sorenroug)
 
## Get Access

Sign up and send an email to DBpedia developers list or to hellmann _ at _ informatik.uni-leipzig.de . If you are accepted, you will get write access to the repository.

Please also help to (1) merge pull request (2) keep the repository clean 


# Usage and Workflow

Please improve the links and add new files and then do a pull request.  Data will be loaded into the DBpedia endpoint in the future. 
We don't know at which point in the future this will happen. We also plan to update links in http://live.dbpedia.org on a weekly basis. 

We are currently looking for volunteers who wish to join the DBpedia Linking Commitee and discuss how links are curated in DBpedia.

**Please honor the conventions below**

In case you are not a Git expert, GitHub allows you to upload links with their GUI:

1. create your own GitHub account
2. fork this repo https://github.com/dbpedia/dbpedia-links into your GitHub space
3. make your edits: 
    - Note: to add links to a previous unlinked data set create a folder with the dataset domain e.g. datasets/transparency.270a.info (if necessary create subfolders, e.g. www4.wiwiss.fu-berlin.de/bookmashup/books)
    - Please see below on how the data in the folder should be structured
4. Finalize your edits by sending a "pull request" via GitHub

# Conventions / Rules

Please honor these conventions strictly:

## Basic Folder Structure
/datasets/$fromDomain/$toDomain/$randomName where:

- $fromDomain should be the domain of the subject of the outgoing triples (e.g. dbpedia.org or de.dbpedia.org )
- $toDomain should be the domain of the object (e.g. transparency.270a.info )
- $randomName should be a name for the linkset.

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


## Structure within one folder of type /datasets/$fromDomain/$toDomain/$randomName

The following files **must** be in this folder:
* one or more files in N-Triples format with the links
* a metadata.ttl file with infos (see below)

The following optional files **should** be in these folders (if applicable):
* link-specs -> put all SILK link spec xml files in this folder
* scripts -> put any scripts you used for linking into this folder, please do not forget a readme.

Other conventions:

1. All N-Triples files must be alphabetically sorted without duplicate triples for better diffs. This is in accordance with the Unix command: sort -u .
2. Recommended predicates (might be extended easily, write to list): owl:sameAs, umbel:isLike, skos:{exact|close|...}Match
2. (future work) metadata.ttl must be provided completely
3. (future work) There will be some quality control; not everybody will be able to include any links he wants to include. We are open to ideas how to manage this. Consider "pull requests" as "application for inclusion"
4. (future work) Links will into a graph named specified in the metadata.ttl and made subgraph of http://dbpedia.org
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


# Feedback


goes (as always) to the DBpedia Discussion mailinglist: https://lists.sourceforge.net/lists/listinfo/dbpedia-discussion


