Scala Server
============

A hopefully lightweight, distributed server for space physics calculation of particular interest for running OCESS software in future.

Installation and Usage
======================

First download the repository (through `git clone`, the ZIP download, etc.). Next, you will need a working installation of Scala version 2.12.0 or higher, and SBT. I leave you in the hands of Google for this step.

From the root of the project directory, simply run SBT, and then `compile` and `run` from the SBT terminal. In the future this will have a nicer way of setting up the server location and such, but for now it's just hardcoded in `src/main/scala/Server.scala`. Sorry.

The server sets up a listener on the provided port. It will wait and accept TCP connections, containing information requests and/or commands; processing them as necessary.