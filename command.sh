#!/bin/bash

if [ -n "$1" ]
then
  pathFrom=$1
else
  pathFrom="/home/kali/storage/Music/music_source/"
fi

if [ -n "$1" ]
then
  pathTo=$2
else
  pathTo="/home/kali/storage/Music/test/"
fi


java -jar RenameMusic.jar $pathFrom $pathTo