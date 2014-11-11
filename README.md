This repository contains tools, intermediate results and the benchmark of our code clone study. The folder structure is as follows:

* 1_study-objects - the source code analysed by the clone detection tools we tested
* 2_detection-parameter - parameters for the clone detection tools we used
* 3_cloning-reports - reports generated by the clone detection tools
* 4_toolchain - our helper tool "CloneListCruncher" that analyses the cloning reports
* 5_toolchain-output - detailied reports about the clones generated by the CloneListCruncher
* 6_sample-analysis - results of a manual inspection of undetected clone pairs
* 7_statistical-analysis - statistical analysis of the study results with R
* 8_benchmark - a set of typical source file pairs that are clones but remained undetected

The data and software is published at ZENODO:
[![DOI](https://zenodo.org/badge/7107/SE-Stuttgart/clone-study.png)](http://dx.doi.org/10.5281/zenodo.12646)
