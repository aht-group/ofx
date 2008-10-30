@for /f %%i in ('chdir') do @Set FUXML_DIR=%%i
@echo Rufe Informationsseite auf...
@%FUXML_DIR%\init.htm
@echo Lade Konfigurationsdatei 
@notepad %FUXML_DIR%\config.bat
@echo Initialisiere Clienteinstellungen
@mkdir "%USERPROFILE%\.openfuxml"

rem  Erstelle Konfigurationseinstellungen
@echo #openFuXML - Client - Konfigurationseinstellungen > simple-client.prop
@echo #Thu Oct 11 20:37:23 CEST 2007 >> simple-client.prop
@echo Anwendung=fuxml >> simple-client.prop
@echo .pdf=C:\\Programme\\Adobe\\Reader 8.0\\Reader\\AcroRd32.exe >> simple-client.prop
@echo Host=direct-producer >> simple-client.prop
@echo .html=C:\\Programme\\Internet Explorer\\iexplore.exe >> simple-client.prop
@echo .htm=C:\\Programme\\Internet Explorer\\iexplore.exe >> simple-client.prop
@echo Port=4455 >> simple-client.prop
@echo Projekt=referenzprojekt >> simple-client.prop
@echo Format=latexpdf >> simple-client.prop
@echo Dokument=Kurs.xml >> simple-client.prop
@echo Verzeichnis=%USERPROFILE%\\repository >> simple-client.prop
@echo output=%USERPROFILE%\\output >> simple-client.prop
@xcopy simple-client.prop "%USERPROFILE%\.openfuxml"\\output" >> %USERPROFILE%\.openfuxml\simple-client.prop