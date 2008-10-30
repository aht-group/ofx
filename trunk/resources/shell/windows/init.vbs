Call init()	
Sub init()
Dim fso, ClientProperties, PathValues, WshShell, Verzeichnis
	Set fso     = WScript.CreateObject("Scripting.FileSystemObject") 
	Set ClientProperties = fso.CreateTextFile( "simple-client.prop", true) 
	Set PathValues = fso.CreateTextFile( "config.bat", true) 
	Set WshShell = WScript.CreateObject("WScript.Shell")

	'-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
'	Schreiben der simple-client.prop Datei
'-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	'Verzeichnis = Replace(WshShell.ExpandEnvironmentStrings("%USERPROFILE%") , "\", "\\")
	Verzeichnis = Replace(WshShell.CurrentDirectory , "\", "\\")
	Verzeichnis = Replace(Verzeichnis, ":", "\:")
	ClientProperties.Write ("#openFuXML - Client - Konfigurationseinstellungen"  & Chr(13)) 
	ClientProperties.Write ("#Mon Oct 22 22:57:11 CEST 2007" & Chr(13))
	ClientProperties.Write ("Projekt=referenzprojekt" & Chr(13))
	ClientProperties.Write ("Output=" & Verzeichnis & "\\share\\output" & Chr(13))
	ClientProperties.Write ("Verzeichnis=" & Verzeichnis & "\\share\\repository" & Chr(13)) 
	ClientProperties.Write ("Host=direct-producer" & Chr(13)) 
	ClientProperties.Write ("Dokument=Kurs.xml" & Chr(13)) 
	ClientProperties.Write ("Format=latexpdf" & Chr(13)) 
	ClientProperties.Write ("Anwendung=fuxml" & Chr(13)) 
	ClientProperties.Write ("Port=4455") 
	If fso.FolderExists(WshShell.ExpandEnvironmentStrings("%USERPROFILE%") & "\.openfuxml") = False Then fso.CreateFolder(WshShell.ExpandEnvironmentStrings("%USERPROFILE%") & "\.openfuxml")
	If fso.FileExists(WshShell.ExpandEnvironmentStrings("%USERPROFILE%") & "\.openfuxml\simple-client.prop") = True Then fso.DeleteFile (WshShell.ExpandEnvironmentStrings("%USERPROFILE%") & "\.openfuxml\simple-client.prop")
	If fso.FileExists(WshShell.ExpandEnvironmentStrings("%USERPROFILE%") & "\.openfuxml\simple-client.prop") = False Then WshShell.Run "xcopy simple-client.prop " & Chr(34) & WshShell.ExpandEnvironmentStrings("%USERPROFILE%") & "\.openfuxml" & Chr(34),0,True
	
'-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
'	Schreiben der config.bat Datei
'-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
ghostscript=MsgBox("Sie benötigen das PostScript Tool GhostScript. Falls Sie es nicht installiert haben können Sie es kostenfrei herunterladen. Möchten Sie die Download Seite öffnen? ", 4, "Ghostscript herunterladen?")
url="http://pages.cs.wisc.edu/~ghost/"
if ghostscript = 6 then WshShell.Run url,1,False
	PathValues.WriteLine ("@set GHOSTSCRIPT=" & InputBox("Bitte geben Sie den Pfad zu GhostScript an:", "Pfadkonfiguration", "C:\Programme\gs\gs8.60\bin\"))
	imagemagick=MsgBox("Sie benötigen das kommandozeilen-orientierte Bildbearbeitungstool ImageMagick. Falls Sie es nicht installiert haben können Sie es kostenfrei herunterladen. Möchten Sie die Download Seite öffnen? ", 4, "ImageMagick herunterladen?")
url="http://www.imagemagick.org/"
if imagemagick = 6 then WshShell.Run url,1,False
	PathValues.WriteLine ("@set IMAGEMAGICK=" & InputBox("Bitte geben Sie den Pfad zu Imagemagick an:", "Pfadkonfiguration", "C:\Programme\ImageMagick\")) 
jdk=MsgBox("Da openFuXML zum Teil in Java geschrieben ist benötigen Sie das Java Development Kit -JDK- der Firma Sun. Falls Sie es nicht installiert haben können Sie es kostenfrei herunterladen. Möchten Sie die Download Seite öffnen? ", 4, "Java Development Kit herunterladen?")
url="http://java.sun.com/"
if jdk = 6 then WshShell.Run url,1,False
	PathValues.WriteLine ("@set JAVAJDK=" & InputBox("Bitte geben Sie den Pfad zum Java JDK von Sun an:", "Pfadkonfiguration", "C:\Programme\Java\jdk1.6.0_02")) 
miktex=MsgBox("Die Erstellung der PDF Ausgabe setzt auf ein installiertes LaTex Textsatzsystem. Falls Sie es nicht installiert haben können Sie es kostenfrei herunterladen. Möchten Sie die Download Seite öffnen? ", 4, "MikTex herunterladen?")
url="http://www.miktex.org/"
if jdk = 6 then WshShell.Run url,1,False
	PathValues.WriteLine ("@set LATEX=" & InputBox("Bitte geben Sie den Pfad zu MikTex an:", "Pfadkonfiguration", "C:\Programme\MikTex 2.6\miktex\bin"))
	MsgBox("openFuXML ist nun eingerichtet. Wenn Sie diese Prozedur wiederholen wollen starten Sie einfach die Datei init.vbs in Ihrem openFuXML Verzeichnis z.B. durch einen Doppelklick im Explorer.")
End Sub
	