============
TinyBot v0.1
============

Summary
=======

TinyBot is a tiny web crawler that checks the cohesiveness of your website.

It implements a breadth-first-search to find all links on every page it can find 
on your site. If it finds an external url, it will report it, but not crawl it.

It outputs a csv containing the pages and the amount of times a link was found
to that page. If you are trying to SEO, you'll want a high score for the pages
you want to show off the most.

Currently, the website and output have to be specified in the main java class.
This will be changed in the next update.

What's next
===========

- Inputs and outputs specified from command line
- Different output data structure
- More 'stuff'
