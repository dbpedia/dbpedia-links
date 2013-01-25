dbpedia-links
=============
A repo that contains outgoing links in ntriples format for DBpedia

About
=====
This readme defines the workflow to add your links to this repo. It is still being discussed at the DBpedia discussion mailing list and subject to change at any moment. Also the repo structure might still change a lot.
Current version 0.2
**Note: anything anybody uploads to the dbpedia-links repo will be considered as public domain and you will loose all rights on it. Then it will be relicenced under the same licence as DBpedia is under at the moment**

Usage and Workflow
==================
Please improve the links and add new files and then do a pull request.  Data will be loaded into the DBpedia endpoint in the future. 
We don't know at which point in the future this will happen. We also plan to update links in http://live.dbpedia.org on a weekly basis. 

**Please honor the conventions below**

In case you are not a Git expert, GitHub allows you to upload links with their GUI:

1. create your own GitHub account
2. fork this repo https://github.com/dbpedia/dbpedia-links into yur github space
3. make you edits: 
    - Option a) add links to a previous unlinked data set
        1. create a folder with the dataset domain e.g. datasets/transparency.270a.info
        2. add your .nt file 
        3. create and adjust the metadata.ttl file
    - Option b) modify an existing data set 
        1. modify an existing .nt file or create a separate .nt file
        2. modify metadata.ttl
4. Finalize your edits by sending a "pull request" via GitHub

Conventions / Rules
===================
Please honor these conventions strictly:

1. All NTriples files must be alphabetically sorted without duplicate triples for better diffs. This is in accordance with the Unix command: sort -u .
2. Recommended predicates (might be extended easily, write to list): owl:sameAs, umbel:isLike, skos:{exact|close|...}Match
2. (future work) metadata.ttl must be provided completely
3. (future work) There will be some quality control; not everybody will be able to include any links he wants to include. We are open to ideas how to manage this. Consider "pull requests" as "application for inclusion"
4. (future work) Links will into a graph named specified in the metadata.ttl and made subgraph of http://dbpedia.org
5. (future work) all metadata.ttl files will be loaded into the graph http://dbpedia-links.dbpedia.org/metadata

Feedback
========
goes (as always) to the DBpedia Discussion mailinglist: https://lists.sourceforge.net/lists/listinfo/dbpedia-discussion


