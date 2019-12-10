#!/bin/sh
exec sn "$0" "$@"
!#

import java.io.File

val f = new File(".")

f.listFiles().foreach(println)


