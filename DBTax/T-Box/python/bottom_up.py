#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import mysql.connector

CNX = mysql.connector.connect(user='kasun', database='kasun', password='kasun_perrera_kk')
CURSOR = CNX.cursor()


def load_leaf_heads(heads_file='../resources/leaf_heads'):
    """Load prominent leaf heads from file"""
    with open(heads_file) as i:
        leaf_heads = [head.strip() for head in i.readlines()]
        leaf_heads.sort()
        return leaf_heads


def get_categories(head):
    """Get the list of categories containing a given head"""
    # MySQL lib takes care of escaping quotes (in a weird way)
    query = "SELECT category_name FROM node WHERE is_prominent=true AND head_of_name=%s"
    CURSOR.execute(query, (head,))
    # TODO Exhaustive skip filter
    return [n[0] for n in CURSOR if n[0].find('Wikidata') == -1]


def get_id(category_name):
    """Retrieve the ID of a given category"""
    query = "SELECT page_id FROM page WHERE page_title=%s AND page_namespace=14"
    CURSOR.execute(query, (category_name,))
    ids = [n for n in CURSOR]
    # We only have 1 result ID
    return int(ids[0][0])


def get_parents(category):
    """Get the list of parents given a category"""
    id = get_id(category)
    query = 'SELECT cl_to FROM categorylinks WHERE cl_from=%d' % id
    CURSOR.execute(query)
    # TODO Exhaustive skip filter
    return [n[0] for n in CURSOR if n[0].find('Wikidata') == -1]


def build_hierarchy(node, fp, top=['Cognition','Container_categories']):
    sys.stderr.write('Current node = "%s" %s\n' % (node, type(node)))
    fp.write(' ' * build_hierarchy.depth + node + '\n')
    parents = get_parents(node)
    if top[0] in parents or top[1] in parents:
        sys.stderr.write('Great! Reached top-level category "%s"\n' % top)
        return 0
    for parent in parents:
        build_hierarchy.depth += 1
        build_hierarchy(parent, fp)
        build_hierarchy.depth -= 1
    return 0


if __name__ == '__main__':
    for head in load_leaf_heads():
        with open('../01-top-down/' + head, 'wb') as o:
            leaves = get_categories(head)
            sys.stderr.write('Leaves = %s\n' % str(leaves))
            for node in leaves:
                o.write('\n\n----------------------------\n\n')
                build_hierarchy.depth = 0
                build_hierarchy(node, o)
