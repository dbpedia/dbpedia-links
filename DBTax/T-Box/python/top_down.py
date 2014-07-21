#!/usr/bin/env python
# -*- coding: utf-8 -*-

import collections
import json
import random
from random_bottom_up import get_categories, get_id, load_leaf_heads, CNX, CURSOR
from node import Node

def get_children(category):
    """Get the list of children IDs given a category"""
    query = "SELECT cl_from FROM categorylinks WHERE cl_to=%s"
    CURSOR.execute(query, (category,))
    return [n for n in CURSOR]


def get_name(id):
    """Retrieve the category name given its ID"""
    query = 'SELECT page_title FROM page WHERE page_id=%d AND page_namespace=14' % id
    CURSOR.execute(query)
    # We only have 1 result name
    names = [n for n in CURSOR]
    # There may be no name at all
    return names[0][0] if names else None


def add_children(node):
    """Populate a node with children pulled from the category DB"""
    children_ids = get_children(node.name)
    for child_id in children_ids:
        child_name = get_name(child_id)
        # Add a child node with depth = n + 1
        if child_name:
            print '"%s" child = %s (%s)' % (node.name, child_name, str(child_id))
            child_node = Node(child_name, node.depth + 1)
            node.add_child(child_node)
    return node


def populate(node, node_list):
    """Recursively populate a node"""
    node = add_children(node)
    print 'Node = %s' % node.to_dict()
    for child in node.children:
        node_list.append(child.to_dict())
        populate.depth += 1
        populate(child, node_list)
        populate.depth -= 1
    return node

def build_full_hierarchy(leaf):
    """Build the hierarchy for the given leaf category, in a top-down fashion"""
    # Start with the top-level category, depth = 0
    root_name = 'Contents'
    root_node = Node(root_name, 0)
    # Flat list of all nodes
    node_list = [root_node.to_dict()]
    # Loop until the leaf is reached
    while current_node.name != leaf:
        # Populate node
        root_node = populate(root_node, node_list)
    return root_node, node_list


def find_duplicates(node_list):
    """Return duplicates given a flat node list"""
    names = []
    for node in node_list:
        # 1 name per node
        names.append(node.keys()[0])
    return [name for name, count in collections.Counter(names).items() if count > 1]


def get_depths_to_prune(node_name, node_list):
    """Return non-minimum depth values given a (duplicate) node name"""
    depth_list = [node[node_name] for node in node_list]
    depth_list.remove(min(depth_list))
    return depth_list


def prune_cycles(root_node, name_to_prune, depths_to_prune):
    """Prune duplicate nodes, i.e. cycles"""
    for depth in depths_to_prune:
        root_node.prune_child(Node(name_to_prune, depth))
    return 0


if __name__ == '__main__':
    # heads = load_leaf_heads()
    # head = heads[666]
    # print 'Head = "%s"' % head
    # leaves = get_categories(head)
    # print 'Leaves = %s' % str(leaves)
    # leaf = leaves[0]
    # leaf = "Image_galleries"
    # print '-------------------------------'
    # print 'Leaf = "%s"' % leaf
    populate.depth = 0
    root = Node('Contents', 0)
    node_list = [root.to_dict()]
    hierarchy = populate(root, node_list)
    json.dump(node_list, open('../01-top-down/full_hierarchy.json', 'wb'), indent=2)
    # print 'Final Hierarchy:'
    # print json.dumps(hierarchy, indent=2)
