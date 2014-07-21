#!/usr/bin/env python
# -*- coding: utf-8 -*-

import codecs
import inflection
import json
import sys
import mysql.connector
from collections import defaultdict

CNX = mysql.connector.connect(user='kasun', database='kasun', password='kasun_perrera_kk')
CURSOR = CNX.cursor()
NODE_SET = set()


def singularize(head):
    return inflection.singularize(head)


def scan_category_links():
    parents = defaultdict(list)
    query = 'SELECT cl_from, cl_to FROM categorylinks'
    CURSOR.execute(query)
    for row in CURSOR:
        parents[row[0]].append(row[1])
    return parents


def scan_category_pages():
    categories = {}
    query = 'SELECT page_id, page_title FROM page WHERE page_namespace=14'
    CURSOR.execute(query)
    for row in CURSOR:
        categories[row[1]] = row[0]
    return categories

def scan_category_heads():
    heads = {}
    query = 'SELECT category_name, head_of_name FROM node'
    CURSOR.execute(query)
    for row in CURSOR:
        heads[row[0]] = row[1]
    return heads


def load_leaf_heads(heads_file='../resources/leaf_heads'):
    """Load prominent leaf heads from file"""
    with open(heads_file) as i:
        leaf_heads = [head.strip() for head in i.readlines()]
        leaf_heads.sort()
        return leaf_heads


def lookup_id(category_name, all_categories):
    return all_categories.get(category_name)


def get_categories(head):
    """Get the list of categories containing a given head"""
    # MySQL lib takes care of escaping quotes (in a weird way)
    query = "SELECT category_name FROM node WHERE is_prominent=true AND head_of_name=%s"
    CURSOR.execute(query, (head,))
    # TODO Exhaustive skip filter
    return [n[0] for n in CURSOR if n[0].find('Wikidata') == -1]


def lookup_parents(node_name, parents, categories):
    node_id = lookup_id(node_name, categories)
    # Remember to cast to string for proper lookup
    return parents.get(str(node_id))


def lookup_head(category_name, all_heads):
    return all_heads.get(category_name)


def store(parent, node, filehandle):
    filehandle.write('%s\t%s\n' % (parent, node))
    return 0


def build_full_hierarchy(node_name, all_parents, all_categories, filehandle, root='Contents'):
    print 'Current node = "%s"' % node_name
    node_parents = lookup_parents(node_name, all_parents, all_categories)
    if not node_parents:
        print 'Strange! "%s" parents not found!' % node_name
        return 1
    if root in node_parents:
        print 'Great! Reached top-level category "%s"' % root
        return 0
    for parent in node_parents:
        if node_name in NODE_SET:
            continue
        NODE_SET.add(node_name)
        store(parent, node_name, filehandle)
        build_full_hierarchy(parent, all_parents, all_categories, filehandle)
    return 0


def build_heads_hierarchy(node_name, all_parents, all_categories, all_heads, filehandle, root='Contents'):
    print 'Current node = "%s"' % node_name
    node_head = lookup_head(node_name, all_heads)
    if not node_head:
        print 'Strange! "%s" HEAD not found!' % node_name
        return 1
    # Normalize
    node_head = singularize(node_head).lower()
    print 'Current singular head = "%s"' % node_head
    node_parents = lookup_parents(node_name, all_parents, all_categories)
    if not node_parents:
        print 'Strange! "%s" PARENTS not found!' % node_name
        return 1
    if root in node_parents:
        print 'Great! Reached top-level category "%s"' % root
        return 0
    for parent in node_parents:
        if node_head in NODE_SET:
            continue
        NODE_SET.add(node_head)
        parent_head = lookup_head(parent, all_heads)
        if not parent_head:
            print 'Strange! "%s" HEAD not found!' % parent
            continue
        # Normalize
        parent_head = singularize(parent_head).lower()
        # Skip duplicates
        if parent_head != node_head:
            store(parent_head, node_head, filehandle)
        build_heads_hierarchy(parent, all_parents, all_categories, all_heads, filehandle)
    return 0


if __name__ == '__main__':
#    all_parents = scan_category_links()
#    with open('../parents.json', 'wb') as o:
#        json.dump(all_parents, o, indent=2)
#    all_categories = scan_category_pages()
#    with open('../categories.json', 'wb') as o:
#        json.dump(all_categories, o, indent=2)
#    all_heads = scan_category_heads()
#    with open ('../resources/heads.json', 'wb') as o:
#        json.dump(all_heads, o, indent=2)
    all_parents = json.load(open('../resources/parents.json'))
    all_categories = json.load(open('../resources/categories.json'))
    all_heads = json.load(open('../resources/heads.json'))
    leaf_heads = load_leaf_heads()
    leaf_nodes = set()
    for head in leaf_heads:
        print 'Current head = %s' % head
        leaves = get_categories(head)
        for leaf in leaves:
            leaf_nodes.add(leaf)
    with codecs.open(sys.argv[1], 'wb', 'utf-8') as fh:
        fh.write('parent\tnode\n')
        for leaf in leaf_nodes:
            build_heads_hierarchy(leaf, all_parents, all_categories, all_heads, fh)

