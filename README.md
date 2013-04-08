dbpedia-links
=============
A repo that contains outgoing links in ntriples format for DBpedia

#About

This readme defines the workflow to add your links to this repo. It is still being discussed at the DBpedia discussion mailing list and subject to change at any moment. Also the repo structure might still change a lot.
Current version 0.3

**Note: anything anybody uploads to the dbpedia-links repo will be considered as public domain and you will lose all rights on it. Then it will be relicensed under the same licence as DBpedia is under at the moment**

# Linking Committee
## Current members 
- Sebastian Hellmann
- Sarven Capadisli (Csarven)

 
## How to join & responsibilities
Sign up and send an email to DBpedia developers list or to hellmann _ at _ informatik.uni-leipzig.de . If you are accepted, you will get write access to the repository.

Please also help to (1) merge pull request (2) keep the repository clean 


# Usage and Workflow

Please improve the links and add new files and then do a pull request.  Data will be loaded into the DBpedia endpoint in the future. 
We don't know at which point in the future this will happen. We also plan to update links in http://live.dbpedia.org on a weekly basis. 

We are currently looking for volunteers who wish to join the DBpedia Linking Commitee and discuss how links are curated in DBpedia.

**Please honor the conventions below**

In case you are not a Git expert, GitHub allows you to upload links with their GUI:

1. create your own GitHub account
2. fork this repo https://github.com/dbpedia/dbpedia-links into yur github space
3. make you edits: 
    - Note: to add links to a previous unlinked data set create a folder with the dataset domain e.g. datasets/transparency.270a.info (if necessary create subfolders, e.g. www4.wiwiss.fu-berlin.de/bookmashup/books)
    - Please see below on how the data in the folder should be structured
4. Finalize your edits by sending a "pull request" via GitHub

# Conventions / Rules

Please honor these conventions strictly:

## Files & Folders
/datasets/$foldername where $foldername should be the dataset domain (e.g. transparency.270a.info )

The following files **must** be in this folder:
* one or more files in NTriples format with the links
* a metadata.ttl file with infos (see below)

The following optional files **should** be in these folders (if applicable):
* /datasets/$foldername/link-specs -> put all SILK link spec xml files in this folder
* /datasets/$foldername/scripts -> put any scripts you used for linking into this folder, please do not forget a readme.

Other conventions:

1. All NTriples files must be alphabetically sorted without duplicate triples for better diffs. This is in accordance with the Unix command: sort -u .
2. Recommended predicates (might be extended easily, write to list): owl:sameAs, umbel:isLike, skos:{exact|close|...}Match
2. (future work) metadata.ttl must be provided completely
3. (future work) There will be some quality control; not everybody will be able to include any links he wants to include. We are open to ideas how to manage this. Consider "pull requests" as "application for inclusion"
4. (future work) Links will into a graph named specified in the metadata.ttl and made subgraph of http://dbpedia.org
5. (future work) all metadata.ttl files will be loaded into the graph http://dbpedia-links.dbpedia.org/metadata

## Prelimenary metadata.ttl
TODO

1. add the names of the used nt files.


	@prefix dcterms: <http://purl.org/dc/terms/> .
	@prefix dc: <http://purl.org/dc/elements/1.1/> .
	@prefix void: <http://rdfs.org/ns/void#> .

	<http://dbpedia.org/links/transparency.270a.info> a void:Linkset ;
		void:objectsTarget <http://example.org/target/dataset> ;
		dc:author "Sarven Capadisli" ;
		dc:description "Please write a lot here!" .


# Feedback


goes (as always) to the DBpedia Discussion mailinglist: https://lists.sourceforge.net/lists/listinfo/dbpedia-discussion


