#!/usr/bin/env python
# -*- coding: utf-8 -*-

import mysql.connector
import random

CNX = mysql.connector.connect(user='kasun', database='kasun', password='kasun_perrera_kk')
CURSOR = CNX.cursor()


def load_leaf_heads(heads_file='../resources/leaf_heads'):
    """Load prominent leaf heads from file"""
    with open(heads_file) as i:
        leaf_heads = [head.strip() for head in i.readlines()]
        # Randomize
        random.shuffle(leaf_heads)
        return leaf_heads


def get_categories(head):
    """Get the list of categories containing a given head"""
    # MySQL lib takes care of escaping quotes (in a weird way)
    query = "SELECT category_name FROM node WHERE is_prominent=true AND head_of_name=%s"
    CURSOR.execute(query, (head,))
    # TODO Exhaustive skip filter
    return [n for n in CURSOR if n[0].find('Wikidata') == -1]


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
    return [n for n in CURSOR if n[0].find('Wikidata') == -1]


def build_random_hierarchy(leaf_heads):
    """Build 5 random hierarchies from a subset of 100 random heads"""
    # Try with a subset of 100 random heads, 5 times each
    for head in leaf_heads[666:766]:
        # MySQL lib takes care of escaping quotes (in a weird way)
        q1 = "select category_name from node where is_prominent=true AND head_of_name=%s"
        CURSOR.execute(q1, (head,))
        # TODO Exhaustive skip filter
        leaves = [n for n in CURSOR if n[0].find('Wikidata') == -1]
        # Try 5 times
        for i in xrange(0,5):
            with open(head + '-' + str(i), 'wb') as o:
                leaf_category = random.choice(leaves)[0]
                o.write('Leaf category = ' + leaf_category + '\n')
                parent = leaf_category
                # Loop until the top-level category 'Contents'
                while parent != 'Contents':
                   q2 = "select page_id from page where page_title=%s and page_namespace=14"
                   CURSOR.execute(q2, (parent,))
                   ids = [n for n in CURSOR]
                   # We only have 1 result id
                   id = int(ids[0][0])
                   q3 = 'select cl_to from categorylinks where cl_from=%d' % id
                   CURSOR.execute(q3)
                   parents = [n for n in CURSOR if n[0].find('Wikidata') == -1]
                   parent = random.choice(parents)[0]
                   o.write(parent + '\n')
    return 0
