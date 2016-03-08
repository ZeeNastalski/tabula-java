echo off

del out.csv

for %%f in (%~dp0\pdfs\*.pdf) do (
	echo processing pdfs\%%~nf.pdf
	java -jar %~dp0\tabula-0.8.0-jar-with-dependencies.jar -p all -f AuctionsCSV -c 129,271,337,405,455 %~dp0\pdfs\%%~nf.pdf >> %~dp0\data.csv
)