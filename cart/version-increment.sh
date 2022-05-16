#!/bin/bash
cat VERSION.txt  | awk -F'[.]'  '{
    major=$1;
    minor=$2;
    patch=$3;
    patch += 1;
    minor += patch / 100;
    patch = patch % 100
    major += minor / 10;
    minor = minor % 10;
    printf( "%01d.%01d.%02d\n", major, minor, patch );
    }'