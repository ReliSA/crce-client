# crce-client
Client library for connection to CRCE WS-API

Initial implementation of the project done by students of [The Faculty of Applied Sciences](http://fav.zcu.cz) in Pilsen
during KIV/ASWI course.

## Current State

Currently the project is in the state in which it was handed over by the students at the end of the course.
Requires cleaning up, proper documentation and standarization. At the current state the libraries are not expected 
to be used.


## Old Doc - to be translated

Projekt je rozdìlej na 3 èásti:

CoreLibrary - obsahuje nìkolik tøíd, které tvoøí základní knihovnu
- Constants       - obsahuje v<9a>echny konstanty pou<9e>ité v této knihovnì
	- CoreLibrary     - obsahuje interface pro v<9a>echny dále vyu<9e>ívané metody
		- CoreLibraryImpl - obsahuje implementace v<9a>ech metod vyu<9e>ívaných v knihovnì

			- dále obsahuje slo<9e>ku "imlp", v ní<9e> se nachází JavaBeany, které vyu<9e>ívají je<9a>tì vyu<9e>ívají
				JavaBeanu umístìnou ve slo<9e>ce "type"

ExtendedLibrary - obsahuje tøídy, které tvoøí roz<9a>iøující knihovnu vý<9a>e zmínìné základní knihovny
	- ExtendedLibrary       - obsahuje interface pro v<9a>echny dále pou<9e>ívané metody
	- ExtendedLibryryImpl       - obsahuje implementace v<9a>ech metod vyu<9e>ívaných v knihovnì
		- JavaMetadataParser        - obsahuje interface pro tøídu RecursiveJavaMetadataParser
			- RecursiveJavaMetadataParser   - tato metoda má za úkol ze zadaných tøíd vytvoøit XML dotaz, který je
		poté mo<9e>né odeslat na server RELISA

		TestApp - obsahuje testovací aplikaci, která testuje obì z knihoven pomocí tzv. smoke testù



Pou<9e>ítí knihovny:
- knihovnu je nutné naimportovat do projektu, kde má být pou<9e>ívána
- dále u<9e> je mo<9e>né vyu<9e>ívat jejích veøejných metod
- napø. chci stáhnotu resource ze serveru:
- staèí zavolat metodu getResources(), které jako parametr pøedáme ID resource, kterou chceme stáhnout

