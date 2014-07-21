#!/usr/bin/env python
# -*- coding: utf-8 -*-

class Node(object):
    def __init__(self, name, depth):
        self.name = name
        self.depth = depth
        self.children = []


    def add_child(self, child):
        self.children.append(child)


    def add_children(self, children):
        self.children = children


    def prune_child(self, child):
        self.children.remove(child)


    def to_dict(self):
        """Serialize the node into a flat dictionary"""
        return { self.name: self.depth }