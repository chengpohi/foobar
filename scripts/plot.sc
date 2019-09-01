#!/bin/sh
exec scala -savecompiled "$0" "$@"
!#

import java.io.File

val f = new File(".")

f.listFiles().foreach(println)

(1 to 20).foreach(println)
